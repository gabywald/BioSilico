package gabywald.creatures.parser2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gabywald.creatures.parser2.model.BrainLobe;
import gabywald.creatures.parser2.model.Dendrite;
import gabywald.creatures.parser2.model.Emitter;
import gabywald.creatures.parser2.model.Gene;
import gabywald.creatures.parser2.model.HalfLife;
import gabywald.creatures.parser2.model.Instinct;
import gabywald.creatures.parser2.model.Reaction;
import gabywald.creatures.parser2.model.Receptor;
import gabywald.creatures.parser2.model.Stimulus;

/**
 * Parseur de fichiers .gen pour extraire les gènes et leurs données.
 */
public class GenomeParser {
    /**
     * Parse un fichier .gen et retourne une liste de gènes.
     *
     * @param filename Chemin vers le fichier .gen
     * @return Liste de gènes parsés
     * @throws IOException Si le fichier ne peut pas être lu
     */
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
            String mutabilityStr = mutability.isEmpty() ? "None" : String.join(",", mutability);

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

    /**
     * Extrait les lobes cérébraux des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de lobes cérébraux
     */
    public static List<BrainLobe> extractLobes(List<Gene> genes) {
        List<BrainLobe> lobes = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BRAIN && gene.subtype == Gene.SUBTYPE_LOBE
                && gene.parsedData.containsKey("lobe")) {
                Map<String, Object> lobeData = (Map<String, Object>) gene.parsedData.get("lobe");
                BrainLobe lobe = new BrainLobe(
                    gene.number,
                    Gene.LOBE_NAMES.getOrDefault(gene.number, String.format("Lobe%02X", gene.number)),
                    (int) lobeData.get("x"),
                    (int) lobeData.get("y"),
                    (int) lobeData.get("width"),
                    (int) lobeData.get("height"),
                    "Yes".equals(lobeData.get("perception_link"))
                );
                lobes.add(lobe);
            }
        }
        return lobes;
    }

    /**
     * Extrait les instincts des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste d'instincts
     */
    public static List<Instinct> extractInstincts(List<Gene> genes) {
        List<Instinct> instincts = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_CREATURE && gene.subtype == Gene.SUBTYPE_INSTINCT
                && gene.parsedData.containsKey("instinct")) {
                Map<String, Object> instinctData = (Map<String, Object>) gene.parsedData.get("instinct");
                Instinct instinct = new Instinct(
                    (List<Map<String, Object>>) instinctData.get("conditions"),
                    (String) instinctData.get("action"),
                    (String) instinctData.get("reward_punish"),
                    (int) instinctData.get("amount")
                );
                instincts.add(instinct);
            }
        }
        return instincts;
    }

    /**
     * Extrait les récepteurs des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de récepteurs
     */
    public static List<Receptor> extractReceptors(List<Gene> genes) {
        List<Receptor> receptors = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BIOCHEMISTRY && gene.subtype == Gene.SUBTYPE_RECEPTOR
                && gene.parsedData.containsKey("receptor")) {
                Map<String, Object> receptorData = (Map<String, Object>) gene.parsedData.get("receptor");
                Receptor receptor = new Receptor(
                    (List<Integer>) receptorData.get("locus"),
                    (String) receptorData.get("chemical"),
                    (int) receptorData.get("threshold"),
                    (int) receptorData.get("nominal"),
                    (int) receptorData.get("gain")
                );
                receptors.add(receptor);
            }
        }
        return receptors;
    }

    /**
     * Extrait les émetteurs des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste d'émetteurs
     */
    public static List<Emitter> extractEmitters(List<Gene> genes) {
        List<Emitter> emitters = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BIOCHEMISTRY && gene.subtype == Gene.SUBTYPE_EMITTER
                && gene.parsedData.containsKey("emitter")) {
                Map<String, Object> emitterData = (Map<String, Object>) gene.parsedData.get("emitter");
                Emitter emitter = new Emitter(
                    (List<Integer>) emitterData.get("locus"),
                    (String) emitterData.get("chemical"),
                    (int) emitterData.get("threshold"),
                    (int) emitterData.get("sample_rate"),
                    (int) emitterData.get("gain")
                );
                emitters.add(emitter);
            }
        }
        return emitters;
    }

    /**
     * Extrait les stimulus des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de stimulus
     */
    public static List<Stimulus> extractStimuli(List<Gene> genes) {
        List<Stimulus> stimuli = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BIOCHEMISTRY && gene.subtype == Gene.SUBTYPE_STIMULUS
                && gene.parsedData.containsKey("stimulus")) {
                Map<String, Object> stimulusData = (Map<String, Object>) gene.parsedData.get("stimulus");
                Stimulus stimulus = new Stimulus(
                    (int) stimulusData.get("stimulus_type"),
                    (int) stimulusData.get("intensity"),
                    (List<Integer>) stimulusData.get("locus")
                );
                stimuli.add(stimulus);
            }
        }
        return stimuli;
    }

    /**
     * Extrait les dendrites des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de dendrites
     */
    public static List<Dendrite> extractDendrites(List<Gene> genes) {
        List<Dendrite> dendrites = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BRAIN && gene.subtype == 0x01
                && gene.parsedData.containsKey("dendrite")) {
                Map<String, Object> dendriteData = (Map<String, Object>) gene.parsedData.get("dendrite");
                Dendrite dendrite = new Dendrite(
                    (int) dendriteData.get("source_lobe"),
                    (int) dendriteData.get("source_cell"),
                    (int) dendriteData.get("target_lobe"),
                    (int) dendriteData.get("target_cell"),
                    (int) dendriteData.get("strength"),
                    (int) dendriteData.get("type")
                );
                dendrites.add(dendrite);
            }
        }
        return dendrites;
    }

    /**
     * Extrait les réactions chimiques des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de réactions
     */
    public static List<Reaction> extractReactions(List<Gene> genes) {
        List<Reaction> reactions = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BIOCHEMISTRY && gene.subtype == Gene.SUBTYPE_REACTION
                && gene.parsedData.containsKey("reaction")) {
                Map<String, Object> reactionData = (Map<String, Object>) gene.parsedData.get("reaction");
                Reaction reaction = new Reaction(
                    (List<Map<String, Object>>) reactionData.get("reactants"),
                    (List<Map<String, Object>>) reactionData.get("products"),
                    (int) reactionData.get("rate")
                );
                reactions.add(reaction);
            }
        }
        return reactions;
    }

    /**
     * Extrait les demi-vies des chimies des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de demi-vies
     */
    public static List<HalfLife> extractHalfLives(List<Gene> genes) {
        List<HalfLife> halfLivesList = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BIOCHEMISTRY && gene.subtype == Gene.SUBTYPE_HALF_LIVES
                && gene.parsedData.containsKey("half_lives")) {
                Map<String, Integer> halfLives = (Map<String, Integer>) gene.parsedData.get("half_lives");
                halfLivesList.add(new HalfLife(halfLives));
            }
        }
        return halfLivesList;
    }

    /**
     * Trouve un lobe par son nom.
     *
     * @param lobes Liste de lobes
     * @param name Nom du lobe
     * @return Le lobe correspondant, ou null si non trouvé
     */
    public static BrainLobe findLobeByName(List<BrainLobe> lobes, String name) {
        for (BrainLobe lobe : lobes) {
            if (lobe.name.equals(name)) {
                return lobe;
            }
        }
        return null;
    }

    /**
     * Trouve un lobe par sa position.
     *
     * @param lobes Liste de lobes
     * @param x Coordonnée X
     * @param y Coordonnée Y
     * @return Le lobe correspondant, ou null si non trouvé
     */
    public static BrainLobe findLobeByPosition(List<BrainLobe> lobes, int x, int y) {
        for (BrainLobe lobe : lobes) {
            if (lobe.x <= x && x < lobe.x + lobe.width &&
                lobe.y <= y && y < lobe.y + lobe.height) {
                return lobe;
            }
        }
        return null;
    }

    /**
     * Trouve un lobe par son numéro.
     *
     * @param lobes Liste de lobes
     * @param number Numéro du lobe
     * @return Le lobe correspondant, ou null si non trouvé
     */
    public static BrainLobe findLobeByNumber(List<BrainLobe> lobes, int number) {
        for (BrainLobe lobe : lobes) {
            if (lobe.number == number) {
                return lobe;
            }
        }
        return null;
    }
}
