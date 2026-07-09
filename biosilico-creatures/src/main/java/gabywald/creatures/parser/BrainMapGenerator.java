package gabywald.creatures.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generate a brain map from Creatures genome files.
 * <br/>Call with: <genome_file.gen> [output_file.svg]
 * @author Gabriel Chandesris (2026)
 * @deprecated (tests of reimplementation)
 */
public class BrainMapGenerator {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Usage: java BrainMapGenerator <genome_file.gen> [output_file.svg]");
			System.exit(1);
		}

		try {
			String inputFile = args[0];
			String outputFile = args.length > 1 ? args[1] : "brain_map.svg";

			List<Gene> genes = BrainMapGenerator.parseGenomeFile(inputFile);
			List<Map<String, Object>> lobes = BrainMapGenerator.extractLobes(genes);
			BrainMapGenerator.generateSVG(lobes, outputFile);

			System.out.println("Brain map generated: " + outputFile);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
			System.exit(1);
		}
	}

	public static List<Gene> parseGenomeFile(String filename) throws IOException {
		byte[] content = Files.readAllBytes(Paths.get(filename));
		List<Gene> genes = new ArrayList<>();
		int offset = 0;

		while (offset < content.length) {
			if (offset + 8 > content.length) break;

			String geneMarker = new String(content, offset, 4);
			if (!geneMarker.equals("gene")) {
				offset++;
				continue;
			}

			int type = content[offset + 4] & 0xFF;
			int subtype = content[offset + 5] & 0xFF;
			int number = content[offset + 6] & 0xFF;
			int switchOn = content[offset + 7] & 0xFF;

			int sexMut = offset + 8 < content.length ? content[offset + 8] & 0xFF : 0;
			String sexDep = (sexMut & 0x10) != 0 ? "Female" : ((sexMut & 0x08) != 0 ? "Male" : "None");

			List<String> mutability = new ArrayList<>();
			if ((sexMut & 0x01) != 0) mutability.add("Mutable");
			if ((sexMut & 0x02) != 0) mutability.add("Duplicable");
			if ((sexMut & 0x04) != 0) mutability.add("Deletable");
			String mutabilityStr = String.join(",", mutability);

			int dataLength = offset + 9 < content.length ? content[offset + 9] & 0xFF : 0;
			List<Integer> data = new ArrayList<>();
			if (dataLength > 0 && offset + 10 + dataLength <= content.length) {
				for (int i = 0; i < dataLength; i++) {
					data.add(content[offset + 10 + i] & 0xFF);
				}
			}

			// Gene gene = new Gene(type, subtype, number, switchOn, data);
			Gene gene = new Gene(type, subtype, number, switchOn, sexDep, mutabilityStr, data);
			genes.add(gene);
			offset += 10 + dataLength;
		}

		return genes;
	}

	public static List<Map<String, Object>> extractLobes(List<Gene> genes) {
		List<Map<String, Object>> lobes = new ArrayList<>();
		System.out.println( genes.size() );
		for (Gene gene : genes) {
			// System.out.println( gene.toString() );
			// if (gene.type == 0 && gene.subtype == 0) {
			// if (gene.type == 0x00 && gene.subtype == 0x00) {
			if (gene.getType() == 0x00 && gene.getSubtype() == 0x00) {
				System.out.println( "\t" + gene.toString() );
				Map<String, Object> lobe = new HashMap<>();
				lobe.put("number", gene.getNumber());
				lobe.put("name", BrainMapGenerator.getLobeName(gene.getNumber()));
//				lobe.put("x", gene.data.get(0));
//				lobe.put("y", gene.data.get(1));
//				lobe.put("width", gene.data.get(2));
//				lobe.put("height", gene.data.get(3));
				lobes.add(lobe);
			}
		}
		return lobes;
	}

	public static String getLobeName(int number) {
		Map<Integer, String> lobeNames = new HashMap<>();
		lobeNames.put(0, "Perception");
		lobeNames.put(1, "Drive");
		lobeNames.put(2, "Stimulus Source");
		lobeNames.put(3, "Verb");
		lobeNames.put(4, "Noun");
		lobeNames.put(5, "General Sense");
		lobeNames.put(6, "Decision");
		lobeNames.put(7, "Attention");
		lobeNames.put(8, "Concept");
		lobeNames.put(9, "Regulator");
		return lobeNames.getOrDefault(number, "Lobe" + String.format("%02X", number));
	}

	public static void generateSVG(List<Map<String, Object>> lobes, String filename) throws IOException {
		StringBuilder svg = new StringBuilder();
		svg.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
		svg.append("<svg width=\"800\" height=\"600\" viewBox=\"0 0 64 48\" xmlns=\"http://www.w3.org/2000/svg\">\n");
		svg.append("  <rect width=\"100%\" height=\"100%\" fill=\"white\"/>\n");

		for (Map<String, Object> lobe : lobes) {
			int x = (int) lobe.get("x");
			int y = (int) lobe.get("y");
			int width = (int) lobe.get("width");
			int height = (int) lobe.get("height");
			String name = (String) lobe.get("name");

			svg.append(String.format(
				"  <rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"#%06X\" stroke=\"black\" stroke-width=\"0.1\"/>\n",
				x, y, width, height, 0x8080FF + (int) (Math.random() * 0xFFFFFF)
			));
			svg.append(String.format(
				"  <text x=\"%d\" y=\"%d\" font-size=\"0.5\" text-anchor=\"middle\">%s</text>\n",
				x + width / 2, y + height / 2, name
			));
		}

		svg.append("</svg>");
		Files.write(Paths.get(filename), svg.toString().getBytes());
	}

//	static class Gene {
//		int type;
//		int subtype; 
//		int number;
//		int switchOn;
//		List<Integer> data;
//
//		Gene(int type, int subtype, int number, int switchOn, List<Integer> data) {
//			this.type = type;
//			this.subtype = subtype;
//			this.number = number;
//			this.switchOn = switchOn;
//			this.data = data;
//		}
//	}
}
