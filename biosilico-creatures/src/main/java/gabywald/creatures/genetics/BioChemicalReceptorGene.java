package gabywald.creatures.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gabywald.creatures.model.UnsignedByte;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * The biochemical receptors latch onto a locus within the brain or the current organ, and provide feedback to the brain about chemicals within the body.
 * <br>{ Organ ; Tissue ; Locus ; Chemical ; Threshold ; Nominal ; Gain ; Flags}
 * <br>The flags indicate if the receptor output is inverted (1) or if it is digital (2).
 * <br>A digital receptor has its state calculated as: <i>Output = Nominal &plusmn; Gain If Signal &gt; Threshold</i>
 * <br>
 * @author Gabriel Chandesris (2013, 2026)
 */
public class BioChemicalReceptorGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * <br>Organ, tissue, locus (first chemicals), Chemical, Threshold, Nominal, Gain, Flags. 
	 * <br>The flags indicate if the receptor output is inverted (1) or if it is digital (2). 
	 * @param orga (UnsignedByte)
	 * @param tissue (UnsignedByte)
	 * @param locus (UnsignedByte)
	 * @param chemical (UnsignedByte)
	 * @param threshold (UnsignedByte)
	 * @param nominal (UnsignedByte)
	 * @param gain (UnsignedByte)
	 * @param flags (UnsignedByte)
	 */
	public BioChemicalReceptorGene(UnsignedByte orga, UnsignedByte tissue, UnsignedByte locus, UnsignedByte chemical, 
									UnsignedByte threshold, UnsignedByte nominal, UnsignedByte gain, UnsignedByte flags) {
		super(1, 0);
		this.data = new ArrayList<UnsignedByte>(8);
		this.data.add(orga);
		this.data.add(tissue);
		this.data.add(locus);
		this.data.add(chemical);
		this.data.add(threshold);
		this.data.add(nominal);
		this.data.add(gain);
		this.data.add(flags);
	}
	
	public int getOrgan()		{ return this.data.get(0).getValue(); }
	public int getTissue()		{ return this.data.get(1).getValue(); }
	public int getLocus()		{ return this.data.get(2).getValue(); }
	public int getChemical()	{ return this.data.get(3).getValue(); }
	public int getTheshold()	{ return this.data.get(4).getValue(); }
	public int getNominal()		{ return this.data.get(5).getValue(); }
	public int getGain()		{ return this.data.get(6).getValue(); }
	public int getFlags()		{ return this.data.get(7).getValue(); }
	
	// DONE see in [http://meliweb.net/creatures/receptor.htm] / creatures1LocusOfAttachmentReceptor.txt
	public static String[][] RECEPTOR_C1_ORGANS_TISSUES_SITES	= {
	// NB	Organ	NB	Tissue	NB	Site	
		{ "0", "Brain",  "0", "Perceptible i/ps", 		"0", "Threshold" }, 
		{ "0", "Brain",  "1", "Drive i/ps", 			"1", "Leakage" }, 
		{ "0", "Brain",  "2", "Stim Source i/ps", 		"2", "Gain" }, 
		{ "0", "Brain",  "3", "Verb i/ps", 				"3", "Den(0) relax susceptibility" }, 
		{ "0", "Brain",  "4", "Noun i/ps", 				"4", "Den(0) relax STW" }, 
		{ "0", "Brain",  "5", "General Sensory i/ps", 	"5", "Den(0) relax LTW" }, 
		{ "0", "Brain",  "6", "Decision o/ps", 			"6", "Den(0) Strength gain rate" }, 
		{ "0", "Brain",  "7", "Attention/Seek o/ps", 	"7", "Den(0) Strength loss rate" }, 
		{ "0", "Brain",  "8", "Lobe 8", 				"8", "Den(1) relax susceptibility" }, 
		{ "0", "Brain",  "9", "Lobe 9", 				"9", "Den(1) relax STW" }, 
		{ "0", "Brain",  "0A", "Lobe 10", 				"0A", "Den(1) relax LTW" },
		{ "0", "Brain",  "0B", "Lobe 11", 				"0B", "Den(1) Strength gain rate" },
		{ "0", "Brain",  "0C", "Lobe 12", 				"0C", "Den(1) Strength loss rate" },
		{ "0", "Brain",  "0D", "Lobe 13", 				"0D", "Chemical 0" },
		{ "0", "Brain",  "0E", "Lobe 14", 				"0E", "Chemical 1" },
		{ "0", "Brain",  "0F", "Lobe 15", 				"0F", "Chemical 2" },
		{ "0", "Brain",  "10", "Lobe 16", "10", "Chemical 3" },
		{ "0", "Brain",  "11", "Lobe 17", "11", "Cell(0) State" },
		{ "0", "Brain",  "12", "Lobe 18", "12", "Cell(1) State" },
		{ "0", "Brain",  "13", "Lobe 19", "13", "Cell(2) State" },
		{ "0", "Brain",  "14", "Lobe 20", "14", "Cell(3) State" }, 
		{ "0", "Brain",  "15", "Lobe 21", "15", "Cell(4) State" }, 
		{ "0", "Brain",  "16", "Lobe 22", "16", "Cell(5) State" }, 
		{ "0", "Brain",  "17", "Lobe 23", "17", "Cell(6) State" }, 
		{ "0", "Brain",  "18", "Lobe 24", "18", "Cell(7) State" }, 
		{ "0", "Brain",  "19", "Lobe 25", "19", "Cell(8) State" }, 
		{ "0", "Brain",  "1A", "Lobe 26", "1A", "Cell(9) State" }, 
		{ "0", "Brain",  "1B", "Lobe 27", "1B", "Cell(10) State" }, 
		{ "0", "Brain",  "1C", "Lobe 28", "1C", "Cell(11) State" }, 
		{ "0", "Brain",  "1D", "Lobe 29", "1D", "Cell(12) State" }, 
		{ "0", "Brain",  "1E", "Lobe 30", "1E", "Cell(13) State" }, 
		{ "0", "Brain",  "1F", "Lobe 31", "1F", "Cell(14) State" }, 
		{ "0", "Brain",  "", "", "20", "Cell(15) State" }, 
		{ "1", "Creature",  "0", "Somatic", "0", "Become a child" }, 
		{ "1", "Creature",  "0", "Somatic", "1", "Become youth" }, 
		{ "1", "Creature",  "0", "Somatic", "2", "Become adolescent" }, 
		{ "1", "Creature",  "0", "Somatic", "3", "Become adult" }, 
		{ "1", "Creature",  "0", "Somatic", "4", "Become old" }, 
		{ "1", "Creature",  "0", "Somatic", "5", "Become senile" }, 
		{ "1", "Creature",  "0", "Somatic", "6", "Die of old age" }, 
		{ "1", "Creature",  "1", "Circulatory", "0", "Floating recep", "emit 0" }, 
		{ "1", "Creature",  "1", "Circulatory", "1", "Floating recep", "emit 1" }, 
		{ "1", "Creature",  "1", "Circulatory", "2", "Floating recep", "emit 2" }, 
		{ "1", "Creature",  "1", "Circulatory", "3", "Floating recep", "emit 3" }, 
		{ "1", "Creature",  "1", "Circulatory", "4", "Floating recep", "emit 4" }, 
		{ "1", "Creature",  "1", "Circulatory", "5", "Floating recep", "emit 5" }, 
		{ "1", "Creature",  "1", "Circulatory", "6", "Floating recep", "emit 6" }, 
		{ "1", "Creature",  "1", "Circulatory", "7", "Floating recep", "emit 7" }, 
		{ "1", "Creature",  "2", "Reproductive ", "0", "Become fertile if high" }, 
		{ "1", "Creature",  "2", "Reproductive ", "1", "Receptive to sperm if > 0" }, 
		{ "1", "Creature",  "3", "Immune", "0", "Die if non", "zero!" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "0", "Involuntary action 0" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "1", "Involuntary action 1" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "2", "Involuntary action 2" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "3", "Involuntary action 3" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "4", "Involuntary action 4" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "5", "Involuntary action 5" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "6", "Involuntary action 6" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "7", "Involuntary action 7" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "8", "Normal walk gait (DO NOT USE)" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "9", "Special gait 1" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "0A", "Special gait 2" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "0B", "Special gait 3" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "0C", "Special gait 4" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "0D", "Special gait 5" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "0E", "Special gait 6" }, 
		{ "1", "Creature",  "4", "Sensorimotor", "0F", "Special gait 7" }, 
		{ "1", "Creature",  "5", "Drive", "0", "Pain" }, 
		{ "1", "Creature",  "5", "Drive", "1", "Need for pleasure" }, 
		{ "1", "Creature",  "5", "Drive", "2", "Hunger" }, 
		{ "1", "Creature",  "5", "Drive", "3", "Coldness" }, 
		{ "1", "Creature",  "5", "Drive", "4", "Hotness" }, 
		{ "1", "Creature",  "5", "Drive", "5", "Tiredness" }, 
		{ "1", "Creature",  "5", "Drive", "6", "Sleepiness" }, 
		{ "1", "Creature",  "5", "Drive", "7", "Loneliness" }, 
		{ "1", "Creature",  "5", "Drive", "8", "Crowded" }, 
		{ "1", "Creature",  "5", "Drive", "9", "Fear" }, 
		{ "1", "Creature",  "5", "Drive", "0A", "Boredom" }, 
		{ "1", "Creature",  "5", "Drive", "0B", "Anger" }, 
		{ "1", "Creature",  "5", "Drive", "0C", "Sex drive" }, 
		{ "1", "Creature",  "5", "Drive", "0D", "Not allocated 2" }, 
		{ "1", "Creature",  "5", "Drive", "0E", "Not allocated 3" }, 
		{ "1", "Creature",  "5", "Drive", "0F", "Not allocated 4" }, 
	};
	
	/**
	 * TODO more optimisation here (cand checking / tests)
	 * @param organNB
	 * @param tissueNB
	 * @param locusSite
	 * @return
	 */
	public static List<String> getReceptorOrgan(int organNB, int tissueNB, int locusSite) {
		int iStarter = 0;
		switch(organNB) { // ** Some Optimisation ** 
		case 0:iStarter =   0;break;
		case 1:iStarter =  30;break;
		default:
			Logger.printlnLog(LoggerLevel.LL_WARNING, "1 BioChemicalReceptorGene.getReceptorOrgan(" + organNB + ", " + tissueNB + ", " + locusSite  + ") return NULL !");
			return null;
		}
		if (organNB == 0) { // Brain
			String organ = "Brain";
			String tissu = null;
			String locus = null;
			for (int i = iStarter ; i < BioChemicalReceptorGene.RECEPTOR_C1_ORGANS_TISSUES_SITES.length 
					&& (tissu == null || locus == null) ; i++) {
				String[] currentSelection = BioChemicalReceptorGene.RECEPTOR_C1_ORGANS_TISSUES_SITES[i];
				if (Integer.decode("0x" + currentSelection[2]) == tissueNB)		{ tissu = currentSelection[3]; }
				if (Integer.decode("0x" + currentSelection[4]) == locusSite)	{ locus = currentSelection[5]; }
			}
			return Arrays.asList(organ, tissu, locus);
		} else { // Creature
			for (int i = iStarter ; i < BioChemicalReceptorGene.RECEPTOR_C1_ORGANS_TISSUES_SITES.length ; i++) {
				String[] currentSelection = BioChemicalReceptorGene.RECEPTOR_C1_ORGANS_TISSUES_SITES[i];
				if ( (Integer.decode("0x" + currentSelection[0]) == organNB) 
						&& (Integer.decode("0x" + currentSelection[2]) == tissueNB)
						&& (Integer.decode("0x" + currentSelection[4]) == locusSite)
						) 
					{ return Arrays.asList(currentSelection[1], currentSelection[3], currentSelection[5]); }
			}
		}
		Logger.printlnLog(LoggerLevel.LL_WARNING, "2 BioChemicalReceptorGene.getReceptorOrgan(" + organNB + ", " + tissueNB + ", " + locusSite  + ") return NULL !");
		return null; // Pair.of(lobeNB+"", cellNB+"");
	}

	// TODO C2/C3 organs and tissues....
}
