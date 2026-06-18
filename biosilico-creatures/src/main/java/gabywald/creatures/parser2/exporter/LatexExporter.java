package gabywald.creatures.parser2.exporter;

import gabywald.creatures.parser2.GenomeParser;
import gabywald.creatures.parser2.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Exporteur de cartographie cérébrale au format LaTeX.
 */
public class LatexExporter {
    /**
     * Exporte une cartographie cérébrale complète au format LaTeX.
     *
     * @param lobes Liste des lobes cérébraux
     * @param instincts Liste des instincts
     * @param receptors Liste des récepteurs
     * @param emitters Liste des émetteurs
     * @param stimuli Liste des stimulus
     * @param dendrites Liste des dendrites
     * @param reactions Liste des réactions chimiques
     * @param halfLives Liste des demi-vies
     * @param filename Nom du fichier de sortie
     * @throws IOException Si le fichier ne peut pas être écrit
     */
    public static void exportBrainMapLatex(
        List<BrainLobe> lobes,
        List<Instinct> instincts,
        List<Receptor> receptors,
        List<Emitter> emitters,
        List<Stimulus> stimuli,
        List<Dendrite> dendrites,
        List<Reaction> reactions,
        List<HalfLife> halfLives,
        String filename
    ) throws IOException {
        StringBuilder latex = new StringBuilder();

        // En-tête LaTeX
        latex.append("\\documentclass{article}\n");
        latex.append("\\usepackage[utf8]{inputenc}\n");
        latex.append("\\usepackage{tikz}\n");
        latex.append("\\usepackage{geometry}\n");
        latex.append("\\geometry{a4paper, margin=1in}\n");
        latex.append("\\usepackage{longtable}\n");
        latex.append("\\usepackage{array}\n");
        latex.append("\\usepackage{graphicx}\n");
        latex.append("\\usepackage{amsmath}\n");
        latex.append("\\title{Brain Map of Norn}\n");
        latex.append("\\author{Creatures Genome Analyzer}\n");
        latex.append("\\date{\\today}\n");
        latex.append("\\begin{document}\n");
        latex.append("\\maketitle\n\n");

        // Section : Cartographie Cérébrale
        latex.append("\\section*{Brain Map}\n\n");
        latex.append("\\begin{center}\n");
        latex.append("\\begin{tikzpicture}[scale=0.5]\n");

        // Dessiner les lobes
        for (BrainLobe lobe : lobes) {
            String color = getLatexColor(lobes.indexOf(lobe));
            latex.append(String.format(
                "\\filldraw[%s!70] (%d,%d) rectangle (%d,%d);\n",
                color, lobe.x, lobe.y, lobe.x + lobe.width, lobe.y + lobe.height
            ));
            latex.append(String.format(
                "\\node at (%d,%d) {\\tiny %s};\n",
                lobe.x + lobe.width / 2, lobe.y + lobe.height / 2, lobe.name
            ));
        }

        // Dessiner les connexions (dendrites)
        for (Dendrite dendrite : dendrites) {
            BrainLobe sourceLobe = GenomeParser.findLobeByNumber(lobes, dendrite.sourceLobe);
            BrainLobe targetLobe = GenomeParser.findLobeByNumber(lobes, dendrite.targetLobe);

            if (sourceLobe != null && targetLobe != null) {
                int sourceX = sourceLobe.x + sourceLobe.width / 2;
                int sourceY = sourceLobe.y + sourceLobe.height / 2;
                int targetX = targetLobe.x + targetLobe.width / 2;
                int targetY = targetLobe.y + targetLobe.height / 2;

                String color = dendrite.type == 0 ? "blue" : "red";
                double opacity = Math.min(dendrite.strength / 255.0, 1.0);

                latex.append(String.format(
                    "\\draw[%s, opacity=%.2f] (%d,%d) -- (%d,%d);\n",
                    color, opacity, sourceX, sourceY, targetX, targetY
                ));
            }
        }

        // Dessiner les instincts (cercles)
        for (Instinct instinct : instincts) {
            for (Map<String, Object> condition : instinct.conditions) {
                String lobeName = (String) condition.get("lobe");
                int cell = (int) condition.get("cell");
                BrainLobe lobe = GenomeParser.findLobeByName(lobes, lobeName);
                if (lobe != null) {
                    latex.append(String.format(
                        "\\filldraw[black] (%d,%d) circle (0.2);\n",
                        lobe.x + lobe.width / 2, lobe.y + lobe.height / 2
                    ));
                    latex.append(String.format(
                        "\\node at (%d,%d) [above, font=\\tiny] {%d};\n",
                        lobe.x + lobe.width / 2, lobe.y + lobe.height / 2, cell
                    ));
                }
            }
        }

        latex.append("\\end{tikzpicture}\n");
        latex.append("\\end{center}\n\n");

        // Légende des dendrites
        latex.append("\\section*{Dendrites Legend}\n\n");
        latex.append("\\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Source} & {\\bf Target} & {\\bf Strength} & {\\bf Type} \\\\ \\hline\n");
        for (Dendrite dendrite : dendrites) {
            BrainLobe sourceLobe = GenomeParser.findLobeByNumber(lobes, dendrite.sourceLobe);
            BrainLobe targetLobe = GenomeParser.findLobeByNumber(lobes, dendrite.targetLobe);
            String sourceName = sourceLobe != null ? sourceLobe.name : "Unknown";
            String targetName = targetLobe != null ? targetLobe.name : "Unknown";
            latex.append(String.format(
                "%s[%d] & %s[%d] & %d & D%d \\\\ \\hline\n",
                sourceName, dendrite.sourceCell, targetName, dendrite.targetCell,
                dendrite.strength, dendrite.type
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Légende des Instincts
        latex.append("\\section*{Instincts Legend}\n\n");
        latex.append("\\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Action} & {\\bf Reward/Punish} & {\\bf Amount} & {\\bf Conditions} \\\\ \\hline\n");
        for (Instinct instinct : instincts) {
            String conditions = "";
            for (Map<String, Object> condition : instinct.conditions) {
                conditions += String.format("%s[%d], ",
                    condition.get("lobe"), condition.get("cell"));
            }
            if (!conditions.isEmpty()) {
                conditions = conditions.substring(0, conditions.length() - 2);
            }
            latex.append(String.format(
                "%s & %s & %d & %s \\\\ \\hline\n",
                instinct.action, instinct.rewardPunish, instinct.amount, conditions
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Récepteurs
        latex.append("\\section*{Receptors}\n\n");
        latex.append("\\begin{longtable}{|p{2cm}|p{2cm}|p{2cm}|p{2cm}|p{2cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Locus} & {\\bf Chemical} & {\\bf Threshold} & {\\bf Nominal} & {\\bf Gain} \\\\ \\hline\n");
        for (Receptor receptor : receptors) {
            latex.append(String.format(
                "(%d,%d,%d) & %s & %d & %d & %d \\\\ \\hline\n",
                receptor.locus.get(0), receptor.locus.get(1), receptor.locus.get(2),
                receptor.chemical, receptor.threshold, receptor.nominal, receptor.gain
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Émetteurs
        latex.append("\\section*{Emitters}\n\n");
        latex.append("\\begin{longtable}{|p{2cm}|p{2cm}|p{2cm}|p{2cm}|p{2cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Locus} & {\\bf Chemical} & {\\bf Threshold} & {\\bf Sample Rate} & {\\bf Gain} \\\\ \\hline\n");
        for (Emitter emitter : emitters) {
            latex.append(String.format(
                "(%d,%d,%d) & %s & %d & %d & %d \\\\ \\hline\n",
                emitter.locus.get(0), emitter.locus.get(1), emitter.locus.get(2),
                emitter.chemical, emitter.threshold, emitter.sampleRate, emitter.gain
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Stimulus
        latex.append("\\section*{Stimuli}\n\n");
        latex.append("\\begin{longtable}{|p{2cm}|p{2cm}|p{4cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Type} & {\\bf Intensity} & {\\bf Locus} \\\\ \\hline\n");
        for (Stimulus stimulus : stimuli) {
            latex.append(String.format(
                "%d & %d & (%d,%d,%d) \\\\ \\hline\n",
                stimulus.stimulusType, stimulus.intensity,
                stimulus.locus.get(0), stimulus.locus.get(1), stimulus.locus.get(2)
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Réactions Chimiques
        latex.append("\\section*{Chemical Reactions}\n\n");
        latex.append("\\begin{longtable}{|p{4cm}|p{4cm}|p{2cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Reactants} & {\\bf Products} & {\\bf Rate} \\\\ \\hline\n");
        for (Reaction reaction : reactions) {
            String reactants = reaction.reactants.stream()
                .map(r -> r.get("proportion") + " " + r.get("chem_name"))
                .collect(java.util.stream.Collectors.joining(" + "));
            String products = reaction.products.stream()
                .map(p -> p.get("proportion") + " " + p.get("chem_name"))
                .collect(java.util.stream.Collectors.joining(" + "));
            latex.append(String.format(
                "%s & %s & %d \\\\ \\hline\n",
                reactants, products, reaction.rate
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Demi-Vies
        latex.append("\\section*{Chemical Half-Lives}\n\n");
        latex.append("\\begin{longtable}{|p{4cm}|p{4cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Chemical} & {\\bf Half-Life} \\\\ \\hline\n");
        for (HalfLife halfLife : halfLives) {
            for (Map.Entry<String, Integer> entry : halfLife.chemicalHalfLives.entrySet()) {
                latex.append(String.format(
                    "%s & %d \\\\ \\hline\n",
                    entry.getKey(), entry.getValue()
                ));
            }
        }
        latex.append("\\end{longtable}\n\n");

        // Pied de page
        latex.append("\\end{document}\n");

        // Sauvegarder le fichier LaTeX
        Files.write(Paths.get(filename), latex.toString().getBytes());
    }

    /**
     * Retourne une couleur LaTeX en fonction de l'index.
     *
     * @param index Index de la couleur
     * @return Nom de la couleur LaTeX
     */
    private static String getLatexColor(int index) {
        String[] colors = {"red", "blue", "green", "yellow", "purple", "orange", "pink", "brown", "gray", "cyan"};
        return colors[index % colors.length];
    }
}
