package gabywald.creatures.parser;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses Creatures genome files.
 */
public class GenomeParser {
	public static List<Gene> parseGenomeFile(String filename) throws IOException {
		byte[] content = Files.readAllBytes(Paths.get(filename));
		List<Gene> genes = new ArrayList<>();
		int offset = 0;

		while (offset < content.length) {
			if (offset + 8 > content.length) break;

			// Parse header
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

			Gene gene = new Gene(type, subtype, number, switchOn, sexDep, mutabilityStr, data);
			genes.add(gene);
			offset += 10 + dataLength;
		}

		return genes;
	}
}

