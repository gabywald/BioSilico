package gabywald.creatures.launcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import gabywald.creatures.genetics.BioChemicalEmitterGene;
import gabywald.creatures.genetics.BioChemicalReactionGene;
import gabywald.creatures.genetics.BioChemicalReceptorGene;
import gabywald.creatures.genetics.BrainLobeGene;
import gabywald.creatures.genetics.HalfLivesGene;
import gabywald.creatures.genetics.InstinctGene;
import gabywald.creatures.genetics.StimulusGene;
import gabywald.creatures.genetics.builds.CreatureGeneListHelper;
import gabywald.creatures.genetics.simple.C1Chemical;
import gabywald.creatures.genetics.simple.C1ChemicalsHelper;
import gabywald.creatures.genetics.simple.CreaturesEnums;
import gabywald.creatures.model.UnsignedByte;
import gabywald.global.structures.PairSimple;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * Exporteur de cartographie cérébrale au format LaTeX.
 * @author Gabriel Chandesris (2026)
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
	 * -param dendrites Liste des dendrites
	 * @param reactions Liste des réactions chimiques
	 * @param halfLives Liste des demi-vies
	 * @param filename Nom du fichier de sortie
	 * @throws IOException Si le fichier ne peut pas être écrit
	 */
	public static void exportBrainMapLatex(
		List<BrainLobeGene> lobes,
		List<InstinctGene> instincts,
		List<BioChemicalReceptorGene> receptors,
		List<BioChemicalEmitterGene> emitters,
		List<StimulusGene> stimuli,
		// List<Dendrite> dendrites,
		List<BioChemicalReactionGene> reactions,
		List<HalfLivesGene> halfLives,
		String filename
	) { // throws IOException {
		StringBuilder latex = new StringBuilder();

		// En-tête LaTeX
		latex.append("\\documentclass{article}\n");
		latex.append("\\usepackage[utf8]{inputenc}\n");
		latex.append("\\usepackage{tikz}\n");
		latex.append("\\usepackage{geometry}\n");
		latex.append("\\geometry{a4paper, margin=1.5cm}\n");
		// latex.append("\\usepackage[top=1.5cm, bottom=1.5cm, left=1.5cm, right=1.5cm]{geometry}\n");
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
		latex.append("\\begin{tikzpicture}[scale=0.25]\n");

		// Dessiner les lobes
		for (BrainLobeGene lobe : lobes) {
			String color = getLatexColor(lobes.indexOf(lobe));
			latex.append(String.format(
				"\\filldraw[%s!70] (%d,%d) rectangle (%d,%d);\n",
				color, 
				lobe.getXXstartPosition(), lobe.getYYstartPosition(), 
				lobe.getXXstartPosition() + lobe.getWidth(), lobe.getYYstartPosition() + lobe.getHeight()
			));
			latex.append(String.format(
				"\\node at (%d,%d) {\\tiny %s};\n",
				lobe.getXXstartPosition() + lobe.getWidth() / 2, lobe.getYYstartPosition() + lobe.getHeight() / 2, 
					lobe.getSequenceNumber() + " " + CreaturesEnums.getLobesNames().get(lobe.getSequenceNumber().getValue())
			));
		}

//		// Dessiner les connexions (dendrites)
//		for (Dendrite dendrite : dendrites) {
//			BrainLobe sourceLobe = GenomeParser.findLobeByNumber(lobes, dendrite.sourceLobe);
//			BrainLobe targetLobe = GenomeParser.findLobeByNumber(lobes, dendrite.targetLobe);
//
//			if (sourceLobe != null && targetLobe != null) {
//				int sourceX = sourceLobe.x + sourceLobe.width / 2;
//				int sourceY = sourceLobe.y + sourceLobe.height / 2;
//				int targetX = targetLobe.x + targetLobe.width / 2;
//				int targetY = targetLobe.y + targetLobe.height / 2;
//
//				String color = dendrite.type == 0 ? "blue" : "red";
//				double opacity = Math.min(dendrite.strength / 255.0, 1.0);
//
//				latex.append(String.format(
//					"\\draw[%s, opacity=%.2f] (%d,%d) -- (%d,%d);\n",
//					color, opacity, sourceX, sourceY, targetX, targetY
//				));
//			}
//		}

		// Dessiner les instincts (cercles)
		for (InstinctGene instinct : instincts) {
			int prevlobeXXpos = 0, prevlobeYYpos = 0;
			for (PairSimple<UnsignedByte, UnsignedByte> condition : instinct.getConditions()) {
				// BrainLobe lobe = GenomeParser.findLobeByName(lobes, lobeName);
				BrainLobeGene lobe = CreatureGeneListHelper.getBrainLobeGene(lobes, condition.first.getValue());
				int cell = condition.second.getValue();
				// Logger.printlnLog(LoggerLevel.LL_INFO, condition.toString());
				if (lobe != null) {
					boolean rewardORpunish = (instinct.getReinforcementDrive() == 49);
					latex.append(String.format(
						"\\filldraw[%s] (%d,%d) circle (0.2);\n",
						// lobe.getXXstartPosition() + lobe.getWidth() / 2, lobe.getYYstartPosition() + lobe.getHeight() / 2
						rewardORpunish?"cyan":"magenta", lobe.getXXstartPosition(), lobe.getYYstartPosition() + cell
					));
					latex.append(String.format(
						"\\node at (%d,%d) [above, font=\\tiny] {%d};\n",
						// lobe.getXXstartPosition() + lobe.getWidth() / 2, lobe.getYYstartPosition() + lobe.getHeight() / 2, cell
						lobe.getXXstartPosition(), lobe.getYYstartPosition() + cell, cell
					));
					
					if (prevlobeXXpos != 0 && prevlobeYYpos != 0) {
						latex.append(String.format(
							"\\draw[thick, black] (%d,%d) -- (%d,%d) ;\n",
							lobe.getXXstartPosition(), lobe.getYYstartPosition() + cell, prevlobeXXpos, prevlobeYYpos
						));
					}
					prevlobeXXpos = lobe.getXXstartPosition();
					prevlobeYYpos = lobe.getYYstartPosition() + cell;
				}
			}
		}
		
		latex.append(String.format(
			"\\node at (%d,%d) [above] {%s};\n",
			5, 50, "Biochemistry Table"
		));
		
		latex.append(String.format(
			"\\node at (%d,%d) [above] {%s};\n",
			5, 38, "Strictly Brain Map"
		));
		
		// Dessiner "Table BioCH"
		int basesQ = 64;
		int basesN = 42;
		int cumulx = 0;
		int cumuly = 0;
		for (C1Chemical c1c : C1ChemicalsHelper.getInstance().getC1Chemicals()) {
			latex.append(String.format(
				"\\filldraw[%s] (%d,%d) circle (0.2);\n",
				"green", cumulx, basesN + cumuly
			));
			latex.append(String.format(
				"\\node at (%d,%d) [above, font=\\tiny] {%s};\n",
				cumulx, basesN + cumuly, ((cumulx == 0) || (cumulx == basesQ-1) )?c1c.getNumHEX():"" // c1c.getNumlDECasINT()
			));
			cumulx++;
			if (cumulx >= basesQ) { cumulx = 0; cumuly += 2; }
			// Logger.printlnLog(LoggerLevel.LL_INFO, String.format( "(%d,%d,%d)", c1c.getNumlDECasINT(), cumulx, basesN + cumuly ));
		}
		
		// Dessiner Receptors
		// TODO Receptors
		
		// Dessiner Emitters
		// TODO Emitters 

		latex.append("\\end{tikzpicture}\n");
		latex.append("\\end{center}\n\n");
		
		latex.append("\\newpage\n\n");

//		// Légende des dendrites
//		latex.append("\\section*{Dendrites Legend}\n\n");
//		latex.append("\\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}\n");
//		latex.append("\\hline\n");
//		latex.append("{\\bf Source} & {\\bf Target} & {\\bf Strength} & {\\bf Type} \\\\ \\hline\n");
//		for (Dendrite dendrite : dendrites) {
//			BrainLobe sourceLobe = GenomeParser.findLobeByNumber(lobes, dendrite.sourceLobe);
//			BrainLobe targetLobe = GenomeParser.findLobeByNumber(lobes, dendrite.targetLobe);
//			String sourceName = sourceLobe != null ? sourceLobe.name : "Unknown";
//			String targetName = targetLobe != null ? targetLobe.name : "Unknown";
//			latex.append(String.format(
//				"%s[%d] & %s[%d] & %d & D%d \\\\ \\hline\n",
//				sourceName, dendrite.sourceCell, targetName, dendrite.targetCell,
//				dendrite.strength, dendrite.type
//			));
//		}
//		latex.append("\\end{longtable}\n\n");

		// Section : Légende des Instincts
		latex.append("\\section*{Instincts}\n\n");
		latex.append("Number :").append(instincts.size()).append("\n\n");
		latex.append("\\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{8cm}|}\n");
		latex.append("\\hline\n");
		latex.append("{\\bf Action} & {\\bf Reinforcement Drive} & {\\bf Reinforcement Level} & {\\bf Conditions} \\\\ \\hline\n");
		for (InstinctGene instinct : instincts) {
			StringBuilder conditions = new StringBuilder();
			for (PairSimple<UnsignedByte, UnsignedByte> condition : instinct.getConditions()) {
				String lobename = CreaturesEnums.getLobesNames().get(condition.first.getValue());
				PairSimple<String, String> lobeANDCell = BrainLobeGene.getLobeAndInputCell(condition.first.getValue(), condition.second.getValue());
				if (lobeANDCell != null) {
					String cellSTR = lobeANDCell.second;
					int cellNUM = condition.second.getValue();
					conditions.append( String.format("%s[%d/%s]~\\newline ", lobename + " (" + condition.first.getValue() + ")", cellNUM, cellSTR) );
				}
			}
			latex.append(String.format(
				"%s & %s & %d & %s \\\\ \\hline\n",
				BrainLobeGene.C1_ACTIONS[instinct.getAction()], 
				// instinct.getReinforcementDrive(),
				instinct.getReinforcementDrive() == 49 ? "Reward" : "Punish", 
				instinct.getReinforcementLevel(), 
				conditions.toString()
			));
		}
		latex.append("\\end{longtable}\n\n");
		
		latex.append("\\newpage\n\n");

		// Section : Récepteurs
		latex.append("\\section*{Receptors}\n\n");
		latex.append("Number :").append(receptors.size()).append("\n\n");
		latex.append("\\begin{longtable}{|p{7cm}|p{3cm}|p{2cm}|p{2cm}|p{2cm}|}\n");
		latex.append("\\hline\n");
		latex.append("{\\bf Locus} & {\\bf Chemical} & {\\bf Threshold} & {\\bf Nominal} & {\\bf Gain} \\\\ \\hline\n");
		for (BioChemicalReceptorGene receptor : receptors) {
			List<String> localData = BioChemicalReceptorGene.getReceptorOrgan(receptor.getOrgan(), receptor.getTissue(), receptor.getLocus());
			latex.append(String.format(
				"(%s, %s, %s) & %s & %d & %d & %d \\\\ \\hline\n", localData.get(0), localData.get(1), localData.get(2), 
				// "(%d, %d, %d) & %s & %d & %d & %d \\\\ \\hline\n", receptor.getOrgan(), receptor.getTissue(), receptor.getLocus(),
				C1ChemicalsHelper.getInstance().getChemicalNameBy(receptor.getChemical()), // receptor.getChemical(), 
				receptor.getTheshold(), receptor.getNominal(), receptor.getGain()
			));
		}
		latex.append("\\end{longtable}\n\n");
		
		latex.append("\\newpage\n\n");

		// Section : Émetteurs
		latex.append("\\section*{Emitters}\n\n");
		latex.append("Number :").append(emitters.size()).append("\n\n");
		latex.append("\\begin{longtable}{|p{7cm}|p{3cm}|p{2cm}|p{2cm}|p{2cm}|}\n");
		latex.append("\\hline\n");
		latex.append("{\\bf Locus} & {\\bf Chemical} & {\\bf Threshold} & {\\bf Sample Rate} & {\\bf Gain} \\\\ \\hline\n");
		for (BioChemicalEmitterGene emitter : emitters) {
			List<String> localData = BioChemicalEmitterGene.getEmitterOrgan(emitter.getOrgan(), emitter.getTissue(), emitter.getLocus());
			latex.append(String.format(
				"(%s, %s, %s) & %s & %d & %d & %d \\\\ \\hline\n", localData.get(0), localData.get(1), localData.get(2), 
				// "(%d, %d, %d) & %s & %d & %d & %d \\\\ \\hline\n", emitter.getOrgan(), emitter.getTissue(), emitter.getLocus(),
				C1ChemicalsHelper.getInstance().getChemicalNameBy(emitter.getChemical()), // emitter.getChemical(), 
				emitter.getTheshold(), emitter.getRate(), emitter.getGain()
			));
		}
		latex.append("\\end{longtable}\n\n");
		
		latex.append("\\newpage\n\n");

		// Section : Stimulus
		latex.append("\\section*{Stimuli}\n\n");
		latex.append("Number :").append(stimuli.size()).append("\n\n");
		latex.append("\\begin{longtable}{|p{2cm}|p{2cm}|p{2cm}|p{2cm}|p{1.50cm}|p{1.50cm}|p{1.50cm}|p{1.50cm}|}\n");
		latex.append("\\hline\n");
		latex.append("{\\bf SeqNum} & {\\bf Significance} & {\\bf SensoryNeu} & {\\bf Intensity} & {\\bf Locus 1} & {\\bf Locus 2} & {\\bf Locus 3} & {\\bf Locus 4} \\\\ \\hline\n");
		for (StimulusGene stimulus : stimuli) {
			List<PairSimple<UnsignedByte, UnsignedByte>> das = stimulus.getDrivesAmounts();
			latex.append(String.format(
				"%d & %d & %d & %d & (%d,%d) & (%d,%d) & (%d,%d) & (%d,%d) \\\\ \\hline\n",
				stimulus.getSequenceNumber().getValue(), stimulus.getSignificance(), stimulus.getSensoryNeurone(), stimulus.getIntensity(),
				das.get(0).first.getValue(), das.get(0).second.getValue(), 
				das.get(1).first.getValue(), das.get(1).second.getValue(), 
				das.get(2).first.getValue(), das.get(2).second.getValue(), 
				das.get(3).first.getValue(), das.get(3).second.getValue()
			));
		}
		latex.append("\\end{longtable}\n\n");
		
		latex.append("\\newpage\n\n");

		// Section : Réactions Chimiques
		latex.append("\\section*{Chemical Reactions}\n\n");
		latex.append("Number :").append(reactions.size()).append("\n\n");
		latex.append("\\begin{longtable}{|p{7cm}|p{7cm}|p{2cm}|}\n");
		latex.append("\\hline\n");
		latex.append("{\\bf Reactants} & {\\bf Products} & {\\bf Rate} \\\\ \\hline\n");
		// C1ChemicalsHelper.getInstance().getChemicalNameBy(0)
		for (BioChemicalReactionGene reaction : reactions) {
			StringBuilder reactants = new StringBuilder();
			reactants.append(reaction.getQuantity1())
					 .append("*")
					 .append(C1ChemicalsHelper.getInstance().getChemicalNameBy(reaction.getReactant1()));
			reactants.append(" + ")
					 .append(reaction.getQuantity2())
					 .append("*")
					 .append(C1ChemicalsHelper.getInstance().getChemicalNameBy(reaction.getReactant2()));
			StringBuilder products = new StringBuilder();
			products .append(reaction.getQuantity3())
					 .append("*")
					 .append(C1ChemicalsHelper.getInstance().getChemicalNameBy(reaction.getReactant3()))
					 .append(" + ")
					 .append(reaction.getQuantity4())
					 .append("*")
					 .append(C1ChemicalsHelper.getInstance().getChemicalNameBy(reaction.getReactant4()));
			latex.append(String.format(
				"%s & %s & %d \\\\ \\hline\n",
				reactants.toString(), products.toString(), reaction.getRateOfReaction()
			));
		}
		latex.append("\\end{longtable}\n\n");
		
		latex.append("\\newpage\n\n");

		// Section : Demi-Vies
		latex.append("\\section*{Chemical Half-Lives}\n\n");
		latex.append("Number :").append(halfLives.size()).append("\n\n");
		latex.append("\\begin{longtable}{|p{4cm}|p{4cm}|}\n");
		latex.append("\\hline\n");
		latex.append("{\\bf Chemical} & {\\bf Half-Life} \\\\ \\hline\n");
		for (HalfLivesGene halfLife : halfLives) {
			UnsignedByte key = null;
			UnsignedByte val = null;
			for (UnsignedByte keyvalue: halfLife.getDatas()) {
				if (key == null) { key = keyvalue; }
				else {
					val = keyvalue;
					latex.append(String.format( "%s & %d \\\\ \\hline\n", key.getValue(), val.getValue() ));
					key = null;val = null;
				}
			}
		}
		latex.append("\\end{longtable}\n\n");

		// Pied de page
		latex.append("\\end{document}\n");

		// Sauvegarder le fichier LaTeX
		try {
			Files.write(Paths.get(filename), latex.toString().getBytes());
		} catch (IOException e) {
			// e.printStackTrace();
			Logger.printlnLog(LoggerLevel.LL_ERROR, "Cannot write {" + filename + "}");
		}
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
