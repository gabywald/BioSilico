package gabywald.creatures.parser2;

import gabywald.creatures.parser2.exporter.LatexExporter;
import gabywald.creatures.parser2.model.BrainLobe;
import gabywald.creatures.parser2.model.Dendrite;
import gabywald.creatures.parser2.model.Emitter;
import gabywald.creatures.parser2.model.Gene;
import gabywald.creatures.parser2.model.HalfLife;
import gabywald.creatures.parser2.model.Instinct;
import gabywald.creatures.parser2.model.Reaction;
import gabywald.creatures.parser2.model.Receptor;
import gabywald.creatures.parser2.model.Stimulus;

import java.io.IOException;
import java.util.List;

/**
 * Classe principale pour analyser les fichiers .gen et générer des cartographies cérébrales.
 */
public class Main {
    /**
     * Point d'entrée du programme.
     *
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java gabywald.creatures.parser.Main <genome_file.gen> [--svg <output.svg>] [--latex <output.tex>]");
            System.exit(1);
        }

        String inputFile = args[0];
        String svgOutput = null;
        String latexOutput = null;

        // Parser les arguments
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("--svg") && i + 1 < args.length) {
                svgOutput = args[++i];
            } else if (args[i].equals("--latex") && i + 1 < args.length) {
                latexOutput = args[++i];
            }
        }

        try {
            // Parser le fichier de génome
            List<Gene> genes = GenomeParser.parseGenomeFile(inputFile);
            System.out.println("Parsed " + genes.size() + " genes from " + inputFile);

            // Extraire les données
            List<BrainLobe> lobes = GenomeParser.extractLobes(genes);
            List<Instinct> instincts = GenomeParser.extractInstincts(genes);
            List<Receptor> receptors = GenomeParser.extractReceptors(genes);
            List<Emitter> emitters = GenomeParser.extractEmitters(genes);
            List<Stimulus> stimuli = GenomeParser.extractStimuli(genes);
            List<Dendrite> dendrites = GenomeParser.extractDendrites(genes);
            List<Reaction> reactions = GenomeParser.extractReactions(genes);
            List<HalfLife> halfLives = GenomeParser.extractHalfLives(genes);

            System.out.println("Found " + lobes.size() + " brain lobes");
            System.out.println("Found " + instincts.size() + " instincts");
            System.out.println("Found " + receptors.size() + " receptors");
            System.out.println("Found " + emitters.size() + " emitters");
            System.out.println("Found " + stimuli.size() + " stimuli");
            System.out.println("Found " + dendrites.size() + " dendrites");
            System.out.println("Found " + reactions.size() + " reactions");
            System.out.println("Found " + halfLives.size() + " half-lives");

//            // Générer le SVG si demandé
//            if (svgOutput != null) {
//                SVGExporter.exportBrainMap(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, svgOutput);
//                System.out.println("Brain map SVG generated: " + svgOutput);
//            }

            // Générer le LaTeX si demandé
            if (latexOutput != null) {
                LatexExporter.exportBrainMapLatex(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, latexOutput);
                System.out.println("Brain map LaTeX generated: " + latexOutput);
            }
            
            if (latexOutput == null) {
            	latexOutput = inputFile.replaceFirst("[.][^.]+$", ".tex");
                LatexExporter.exportBrainMapLatex(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, latexOutput);
                System.out.println("Brain map LaTeX generated: " + latexOutput);
            }

//            // Si aucun format n'est spécifié, générer les deux par défaut
//            if (svgOutput == null && latexOutput == null) {
//                svgOutput = inputFile.replaceFirst("[.][^.]+$", ".svg");
//                latexOutput = inputFile.replaceFirst("[.][^.]+$", ".tex");
//                SVGExporter.exportBrainMap(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, svgOutput);
//                LatexExporter.exportBrainMapLatex(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, latexOutput);
//                System.out.println("Brain map SVG generated: " + svgOutput);
//                System.out.println("Brain map LaTeX generated: " + latexOutput);
//            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
