package gabywald.creatures.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gabywald.creatures.model.UnsignedByte;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * Emitters tie to loci as do receptors. They read the value of the locus, and emit chemicals based on their input states.
 * <br>{ Organ ; Tissue ; Locus ; Chemical ; Threshold ; Rate ; Gain ; Flags } 
 * <br>The Emitter flags can be: 1: Clear input signal after reading or 2: Invert Input Signal.
 * @author Gabriel Chandesris (2013, 2026)
 */
public class BioChemicalEmitterGene extends CreatureGene {
	/**
", "* Old variant of the constructor. 
", "* @param orga (UnsignedByte)
", "* @param tissue (UnsignedByte)
", "* @param locus (UnsignedByte)
", "* @param chemical (UnsignedByte)
", "* @param threshold (UnsignedByte)
", "* @param nominal (UnsignedByte)
", "* @param gain (UnsignedByte)
", "* @param flags (UnsignedByte)
", "*/
	public BioChemicalEmitterGene(	UnsignedByte orga, UnsignedByte tissue, UnsignedByte locus, UnsignedByte chemical, 
									UnsignedByte threshold, UnsignedByte nominal, UnsignedByte gain, UnsignedByte flags) {
		super(1, 1);
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
	public int getRate()		{ return this.data.get(5).getValue(); }
	public int getGain()		{ return this.data.get(6).getValue(); }
	public int getFlags()		{ return this.data.get(7).getValue(); }
	
	// DONE see in [http://meliweb.net/creatures/emitter.htm] / creatures1LocusOfAttachmentEmitter
	public static String[][] EMITTER_C1_ORGANS_TISSUES_SITES	= {
		// NB	Organ	NB	Tissue	NB	Site	
		{ "0", "Brain",  "0", "Perceptible i/ps", 		"0", "Lobe activity" }, 
		{ "0", "Brain",  "1", "Drive i/ps", 			"1", "\\# Loose dens/cells type 0" }, 
		{ "0", "Brain",  "2", "Stim Source i/ps", 		"2", "\\# Loose dens/cells type 1" }, 
		{ "0", "Brain",  "3", "Verb i/ps", 				"3", "Cell(0) Output" }, 
		{ "0", "Brain",  "4", "Noun i/ps", 				"4", "Cell(1) Output" }, 
		{ "0", "Brain",  "5", "General Sensory i/ps", 	"5", "Cell(2) Output" }, 
		{ "0", "Brain",  "6", "Decision o/ps", 			"6", "Cell(3) Output" }, 
		{ "0", "Brain",  "7", "Attention/Seek o/ps", 	"7", "Cell(4) Output" }, 
		{ "0", "Brain",  "8", "Lobe 8", 				"8", "Cell(5) Output" }, 
		{ "0", "Brain",  "9", "Lobe 9", 				"9", "Cell(6) Output" }, 
		{ "0", "Brain", "0A", "Lobe 10", 				"0A", "Cell(7) Output" }, 
		{ "0", "Brain", "0B", "Lobe 11", 				"0B", "Cell(8) Output" }, 
		{ "0", "Brain", "0C", "Lobe 12", 				"0C", "Cell(9) Output" }, 
		{ "0", "Brain", "0D", "Lobe 13", 				"0D", "Cell(10) Output" }, 
		{ "0", "Brain", "0E", "Lobe 14", 				"0E", "Cell(11) Output" }, 
		{ "0", "Brain", "0F", "Lobe 15", 				"0F", "Cell(12) Output" }, 
		{ "0", "Brain", "10", "Lobe 16",				"10", "Cell(13) Output" }, 
		{ "0", "Brain", "11", "Lobe 17",				"11", "Cell(14) Output" }, 
		{ "0", "Brain", "12", "Lobe 18", 				"12", "Cell(15) Output" }, 
		{ "0", "Brain", "13", "Lobe 19", "", "" }, 
		{ "0", "Brain", "14", "Lobe 20", "", "" }, 
		{ "0", "Brain", "15", "Lobe 21", "", "" }, 
		{ "0", "Brain", "16", "Lobe 22", "", "" }, 
		{ "0", "Brain", "17", "Lobe 23", "", "" }, 
		{ "0", "Brain", "18", "Lobe 24", "", "" }, 
		{ "0", "Brain", "19", "Lobe 25", "", "" }, 
		{ "0", "Brain", "1A", "Lobe 26", "", "" }, 
		{ "0", "Brain", "1B", "Lobe 27", "", "" }, 
		{ "0", "Brain", "1C", "Lobe 28", "", "" }, 
		{ "0", "Brain", "1D", "Lobe 29", "", "" }, 
		{ "0", "Brain", "1E", "Lobe 30", "", "" }, 
		{ "0", "Brain", "1F", "Lobe 31", "", "" }, 
		{ "1", "Creature", "0", "Somatic", 		"0", "Muscle energy used" }, 
		{ "1", "Creature", "1", "Circulatory",  "0", "Floating recep	emit 0" }, 
		{ "1", "Creature", "1", "Circulatory",  "1", "Floating recep	emit 1" }, 
		{ "1", "Creature", "1", "Circulatory",  "2", "Floating recep	emit 2" }, 
		{ "1", "Creature", "1", "Circulatory",  "3", "Floating recep	emit 3" }, 
		{ "1", "Creature", "1", "Circulatory",  "4", "Floating recep	emit 4" }, 
		{ "1", "Creature", "1", "Circulatory",  "5", "Floating recep	emit 5" }, 
		{ "1", "Creature", "1", "Circulatory",  "6", "Floating recep	emit 6" }, 
		{ "1", "Creature", "1", "Circulatory",  "7", "Floating recep	emit 7" }, 
		{ "1", "Creature", "2", "Reproductive", "0", "I am fertile (egg/sperm ready)" }, 
		{ "1", "Creature", "2", "Reproductive", "1", "I am pregnant (egg\\&sperm ready)" }, 
		{ "1", "Creature", "3", "Immune", 		"0", "I'm dead (post	mortem chemistry)" }, 
		{ "1", "Creature", "4", "Sensorimotor", "0", "Permanently active (255)" }, 
		{ "1", "Creature", "4", "Sensorimotor", "1", "I'm asleep (255)" }, 
		{ "1", "Creature", "4", "Sensorimotor", "2", "Air is this cold" }, 
		{ "1", "Creature", "4", "Sensorimotor", "3", "Air is this hot" }, 
		{ "1", "Creature", "4", "Sensorimotor", "4", "Light level" }, 
		{ "1", "Creature", "4", "Sensorimotor", "5", "Crowdedness" }, 
		{ "1", "Creature", "5", "Drive", "0", "Pain" }, 
		{ "1", "Creature", "5", "Drive", "1", "Need for pleasure" }, 	
		{ "1", "Creature", "5", "Drive", "2", "Hunger" }, 
		{ "1", "Creature", "5", "Drive", "3", "Coldness" }, 
		{ "1", "Creature", "5", "Drive", "4", "Hotness" }, 
		{ "1", "Creature", "5", "Drive", "5", "Tiredness" }, 
		{ "1", "Creature", "5", "Drive", "6", "Sleepiness" }, 
		{ "1", "Creature", "5", "Drive", "7", "Loneliness" }, 
		{ "1", "Creature", "5", "Drive", "8", "Crowded" }, 
		{ "1", "Creature", "5", "Drive", "9", "Fear" }, 
		{ "1", "Creature", "5", "Drive", "0A", "Boredom" }, 
		{ "1", "Creature", "5", "Drive", "0B", "Anger" }, 
		{ "1", "Creature", "5", "Drive", "0C", "Sex Drive" }, 
		{ "1", "Creature", "5", "Drive", "0D", "Not allocated 2" }, 
		{ "1", "Creature", "5", "Drive", "0E", "Not allocated 3" }, 
		{ "1", "Creature", "5", "Drive", "0F", "Not allocated 4" }
	};
	
	/**
	 * TODO more optimisation here (cand checking / tests)
	 * @param organNB
	 * @param tissueNB
	 * @param locusSite
	 * @return
	 */
	public static List<String> getEmitterOrgan(int organNB, int tissueNB, int locusSite) {
		int iStarter = 0;
		switch(organNB) { // ** Some Optimisation ** 
		case 0:iStarter =   0;break;
		case 1:iStarter =  30;break;
		default:
			Logger.printlnLog(LoggerLevel.LL_WARNING, "BioChemicalEmitterGene.getEmitterOrgan(" + organNB + ", " + tissueNB + ", " + locusSite  + ") return NULL !");
			return null;
		}
		if (organNB == 0) { // Brain
			String organ = "Brain";
			String tissu = null;
			String locus = null;
			for (int i = iStarter ; i < BioChemicalEmitterGene.EMITTER_C1_ORGANS_TISSUES_SITES.length 
					&& (tissu == null || locus == null) ; i++) {
				String[] currentSelection = BioChemicalEmitterGene.EMITTER_C1_ORGANS_TISSUES_SITES[i];
				if (Integer.decode("0x" + currentSelection[2]) == tissueNB)		{ tissu = currentSelection[3]; }
				if (Integer.decode("0x" + currentSelection[4]) == locusSite)	{ locus = currentSelection[5]; }
			}
			return Arrays.asList(organ, tissu, locus);
		} else { // Creature
			for (int i = iStarter ; i < BioChemicalEmitterGene.EMITTER_C1_ORGANS_TISSUES_SITES.length ; i++) {
				String[] currentSelection = BioChemicalEmitterGene.EMITTER_C1_ORGANS_TISSUES_SITES[i];
				if ( (Integer.decode("0x" + currentSelection[0]) == organNB) 
						&& (Integer.decode("0x" + currentSelection[2]) == tissueNB)
						&& (Integer.decode("0x" + currentSelection[4]) == locusSite)
						) 
					{ return Arrays.asList(currentSelection[1], currentSelection[3], currentSelection[5]); }
			}
		}
		Logger.printlnLog(LoggerLevel.LL_WARNING, "BioChemicalEmitterGene.getEmitterOrgan(" + organNB + ", " + tissueNB + ") return NULL !");
		return null; // Pair.of(lobeNB+"", cellNB+"");
	}
	
	// TODO C2/C3 organs and tissues....
}
