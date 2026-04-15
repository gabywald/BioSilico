package gabywald.creatures.parser;

import java.io.IOException;
import java.util.List;

/**
 * Main class to run the genome parser.
 */
public class Main {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Usage: java creatures.parser.Main <genome_file.gen> [output.json]");
			System.exit(1);
		}

		try {
			String inputFile = args[0];
			// String outputFile = args.length > 1 ? args[1] : inputFile + ".txt";

			List<Gene> genes = GenomeParser.parseGenomeFile(inputFile);
			for (Gene gene : genes) {
				System.out.println(gene);
			}

			// (new ToJSONExporter()).exportTo(genes, outputFile);
			// System.out.println("Exported to " + outputFile);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
			System.exit(1);
		}
	}
}
