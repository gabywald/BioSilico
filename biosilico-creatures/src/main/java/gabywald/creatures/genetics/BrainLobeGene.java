package gabywald.creatures.genetics;

import gabywald.global.structures.PairSimple;
import gabywald.creatures.model.UnsignedByte;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.util.List;

/**
 * Brain lobe genes perform all their calculations by means of SV rules.

 * A : Lobe position / size -- 5
 * 		xx ; yy ; ww ; hh ; pl (perception lobe link : no / yes / mutually exclusive) ; ...
 * B : Cell Body -- 13
 * ... ; nt - nominal threshold ; lk - leakage rate ; rs - rest state ; ig - input gain lo-hi ; sr{8} - state rule ; wt - winner takes all (WTA)  ; ...
 * C : D0 Growth -- 10
 * ... ; sl - source lobe ; ld - min # dendrites ; ud - max # dendrites ; sp - spread ; fo - fanout ; ll - min LTW ; ul - max LTW ; ls - min Strength ; us - max Strength ; mr - migration rule  ; ...
 * D : D0 Dynamics -- 37
 * ... ;  ; ...
 * E : D0 Growth -- 10
 * ... ;  ; ...
 * F : D0 Dynamics -- 37
 * ... ;  ; ...
 * 
 * NOTE -- about Brain Lobe Gene flags : 
 * No sex dependence. 
 * Lobes 1-4, 8, 9 - no mutability. 
 * Lobe 7 - mutable. 
 * Lobes 5 and 6 - mutable, duplicable. 
 * @author Gabriel Chandesris (2013, 2026)
 */
public class BrainLobeGene extends CreatureGene {

	/**
", "* 
", "* @param datas
", "* @throws CreatureGeneException
", "*/
	public BrainLobeGene(List<UnsignedByte> datas) 
		{ super(0, 0);this.data.addAll(datas); }
	
	public int getXXstartPosition()		{ return this.data.get( 0).getValue(); }
	public int getYYstartPosition()		{ return this.data.get( 1).getValue(); }
	public int getWidth()				{ return this.data.get( 2).getValue(); }
	public int getHeight()				{ return this.data.get( 3).getValue(); }
	public int getPerceptionLobeLink()	{ return this.data.get( 4).getValue(); }
	
	public int getNominalThreshold()	{ return this.data.get( 5).getValue(); }
	public int getLeakageRate()			{ return this.data.get( 6).getValue(); }
	public int getRestRate()			{ return this.data.get( 7).getValue(); }
	public int getInputGain()			{ return this.data.get( 8).getValue(); }
	public int getSVRule1()				{ return this.data.get( 9).getValue(); }
	public int getSVRule2()				{ return this.data.get(10).getValue(); }
	public int getSVRule3()				{ return this.data.get(11).getValue(); }
	public int getSVRule4()				{ return this.data.get(12).getValue(); }
	public int getSVRule5()				{ return this.data.get(13).getValue(); }
	public int getSVRule6()				{ return this.data.get(14).getValue(); }
	public int getSVRule7()				{ return this.data.get(15).getValue(); }
	public int getSVRule8()				{ return this.data.get(16).getValue(); }
	public boolean isWinnerTakeAll()	{ return (this.data.get(17).getValue() != 0)/** or '== 1' */; }
	
	// TODO ... see [ http://meliweb.net/creatures/lobes.htm ]
	// SEE defintions in creatures1BrainMapCells_GenesHeader.txt
	public static String[] C1_ACTIONS = { // Ordered !
			"Move", "Activate 1 (push) it", "Activate 2 (pull) it", "Deactivate it", "Approach it", 
			"Retreat frm it", "Get it", "Drop all", "Say what you need (how?)", "Rest", 
			"Travel west", "Travel east", "Eat it", "Hit it", "<unallocated 3>", "<unallocated 4>" 
	};
	public static String[][] C1_LOBE_T0_TRANSLATION = {
		{ "1", "Drive", "0", "Pain" }, 
		{ "1", "Drive", "1", "Need for pleasure" }, 
		{ "1", "Drive", "2", "Hunger" }, 
		{ "1", "Drive", "3", "Coldness" }, 
		{ "1", "Drive", "4", "Hotness" }, 
		{ "1", "Drive", "5", "Tiredness" }, 
		{ "1", "Drive", "6", "Sleepiness" }, 
		{ "1", "Drive", "7", "Loneliness" }, 
		{ "1", "Drive", "8", "Crowded" }, 
		{ "1", "Drive", "9", "Fear" }, 
		{ "1", "Drive", "10", "Boredom" }, 
		{ "1", "Drive", "11", "Anger" }, 
		{ "1", "Drive", "12", "Sex drive" }, 
		{ "1", "Drive", "13", "Not allocated 2" }, 
		{ "1", "Drive", "14", "Not allocated 3" }, 
		{ "1", "Drive", "15", "Not allocated 4" }, 
		{ "2", "Stimulus source", "0", "<ID 0>" }, 
		{ "2", "Stimulus source", "1", "<ID 1> (Creature's name)" }, 
		{ "2", "Stimulus source", "2", "<ID 2> (hand name)" }, 
		{ "2", "Stimulus source", "3", "<ID 3> (red lift call button)" }, 
		{ "2", "Stimulus source", "4", "<ID 4> (water)" }, 
		{ "2", "Stimulus source", "5", "<ID 5> (herb/plant)" }, 
		{ "2", "Stimulus source", "6", "<ID 6> (egg)" }, 
		{ "2", "Stimulus source", "7", "<ID 7> (food)" }, 
		{ "2", "Stimulus source", "8", "<ID 8> (drink)" }, 
		{ "2", "Stimulus source", "9", "<ID 9> (vendor)" }, 
		{ "2", "Stimulus source", "10", "<ID 10> (music)" }, 
		{ "2", "Stimulus source", "11", "<ID 11> (animal)" }, 
		{ "2", "Stimulus source", "12", "<ID 12> (fire)" }, 
		{ "2", "Stimulus source", "13", "<ID 13> (shower/clock)" }, 
		{ "2", "Stimulus source", "14", "<ID 14> (toy)" }, 
		{ "2", "Stimulus source", "15", "<ID 15> (bigtoy)" }, 
		{ "2", "Stimulus source", "16", "<ID 16> (weed)" }, 
		{ "2", "Stimulus source", "17", "<ID 17>" }, 
		{ "2", "Stimulus source", "18", "<ID 18>" }, 
		{ "2", "Stimulus source", "19", "<ID 19>" }, 
		{ "2", "Stimulus source", "20", "<ID 20>" }, 
		{ "2", "Stimulus source", "21", "<ID 21>" }, 
		{ "2", "Stimulus source", "22", "<ID 22>" }, 
		{ "2", "Stimulus source", "23", "<ID 23>" }, 
		{ "2", "Stimulus source", "24", "<ID 24>" }, 
		{ "2", "Stimulus source", "25", "<ID 25>" }, 
		{ "2", "Stimulus source", "26", "<ID 26> (mover)" }, 
		{ "2", "Stimulus source", "27", "<ID 27> (lift)" }, 
		{ "2", "Stimulus source", "28", "<ID 28> (computer)" }, 
		{ "2", "Stimulus source", "29", "<ID 29> (fun/projector)" }, 
		{ "2", "Stimulus source", "30", "<ID 30> (bang/cannon)" }, 
		{ "2", "Stimulus source", "31", "<ID 31>" }, 
		{ "2", "Stimulus source", "32", "<ID 32>" }, 
		{ "2", "Stimulus source", "33", "<ID 33>" }, 
		{ "2", "Stimulus source", "34", "<ID 34>" }, 
		{ "2", "Stimulus source", "35", "<ID 35>" }, 
		{ "2", "Stimulus source", "36", "<ID 36> (norn)" }, 
		{ "2", "Stimulus source", "37", "<ID 37> (grendel)" }, 
		{ "2", "Stimulus source", "38", "<ID 38>" }, 
		{ "2", "Stimulus source", "39", "<ID 39>" }, 
		{ "3", "Verb", "0", "Default (quiescent/stay)" }, 
		{ "3", "Verb", "1", "Activate 1 it" }, 
		{ "3", "Verb", "2", "Activate 2 it" }, 
		{ "3", "Verb", "3", "Deactivate it" }, 
		{ "3", "Verb", "4", "Approach it" }, 
		{ "3", "Verb", "5", "Retreat frm it" }, 
		{ "3", "Verb", "6", "Get it" }, 
		{ "3", "Verb", "7", "Drop all" }, 
		{ "3", "Verb", "8", "Say what you need" }, 
		{ "3", "Verb", "9", "Rest" }, 
		{ "3", "Verb", "10", "Travel west" }, 
		{ "3", "Verb", "11", "Travel east" }, 
		{ "3", "Verb", "12", "<unallocated 1>" }, 
		{ "3", "Verb", "13", "<unallocated 2>" }, 
		{ "3", "Verb", "14", "<unallocated 3>" }, 
		{ "3", "Verb", "15", "<unallocated 4>" }, 
		{ "4", "Noun", "0", " <ID 0>" }, 
		{ "4", "Noun", "1", "<ID 1> (Creature's name)" }, 
		{ "4", "Noun", "2", "<ID 2> (hand name)" }, 
		{ "4", "Noun", "3", "<ID 3> (red lift call button)" }, 
		{ "4", "Noun", "4", "<ID 4> (water)" }, 
		{ "4", "Noun", "5", "<ID 5> (herb/plant)" }, 
		{ "4", "Noun", "6", "<ID 6> (egg)" }, 
		{ "4", "Noun", "7", "<ID 7> (food)" }, 
		{ "4", "Noun", "8", "<ID 8> (drink)" }, 
		{ "4", "Noun", "9", "<ID 9> (vendor)" }, 
		{ "4", "Noun", "10", "<ID 10> (music)" }, 
		{ "4", "Noun", "11", "<ID 11> (animal)" }, 
		{ "4", "Noun", "12", "<ID 12> (fire)" }, 
		{ "4", "Noun", "13", "<ID 13> (shower/clock)" }, 
		{ "4", "Noun", "14", "<ID 14> (toy)" }, 
		{ "4", "Noun", "15", "<ID 15> (bigtoy)" }, 
		{ "4", "Noun", "16", "<ID 16> (weed)" }, 
		{ "4", "Noun", "17", "<ID 17>" }, 
		{ "4", "Noun", "18", "<ID 18>" }, 
		{ "4", "Noun", "19", "<ID 19>" }, 
		{ "4", "Noun", "20", "<ID 20>" }, 
		{ "4", "Noun", "21", "<ID 21>" }, 
		{ "4", "Noun", "22", "<ID 22>" }, 
		{ "4", "Noun", "23", "<ID 23>" }, 
		{ "4", "Noun", "24", "<ID 24>" }, 
		{ "4", "Noun", "25", "<ID 25>" }, 
		{ "4", "Noun", "26", "<ID 26> (mover)" }, 
		{ "4", "Noun", "27", "<ID 27> (lift)" }, 
		{ "4", "Noun", "28", "<ID 28> (computer)" }, 
		{ "4", "Noun", "29", "<ID 29> (fun/projector)" }, 
		{ "4", "Noun", "30", "<ID 30> (bang/cannon)" }, 
		{ "4", "Noun", "31", "<ID 31>" }, 
		{ "4", "Noun", "32", "<ID 32>" }, 
		{ "4", "Noun", "33", "<ID 33>" }, 
		{ "4", "Noun", "34", "<ID 34>" }, 
		{ "4", "Noun", "35", "<ID 35>" }, 
		{ "4", "Noun", "36", "<ID 36> (norn)" }, 
		{ "4", "Noun", "37", "<ID 37> (grendel)" }, 
		{ "4", "Noun", "38", "<ID 38>" }, 
		{ "4", "Noun", "39", "<ID 39>" }, 
		{ "5", "General sensory", "0", "I've been patted" }, 
		{ "5", "General sensory", "1", "I've been slapped" }, 
		{ "5", "General sensory", "2", "I've bumped a wall" }, 
		{ "5", "General sensory", "3", "I am near a wall" }, 
		{ "5", "General sensory", "4", "I am in a vehicle" }, 
		{ "5", "General sensory", "5", "User has spoken" }, 
		{ "5", "General sensory", "6", "Creature has spoken" }, 
		{ "5", "General sensory", "7", "Own kind has spoken" }, 
		{ "5", "General sensory", "8", "Audible event" }, 
		{ "5", "General sensory", "9", "Visible event" }, 
		{ "5", "General sensory", "10", "IT is approaching" }, 
		{ "5", "General sensory", "11", "IT is retreating" }, 
		{ "5", "General sensory", "12", "IT is near to me" }, 
		{ "5", "General sensory", "13", "IT is Active" }, 
		{ "5", "General sensory", "14", "IT is an object" }, 
		{ "5", "General sensory", "15", "IT is a creature" }, 
		{ "5", "General sensory", "16", "IT is my sibling" }, 
		{ "5", "General sensory", "17", "IT is my parent" }, 
		{ "5", "General sensory", "18", "IT is my child" }, 
		{ "5", "General sensory", "19", "IT is opposite sex" }, 
		{ "5", "General sensory", "20", "<spare2>" }, 
		{ "5", "General sensory", "21", "<spare3>" }, 
		{ "5", "General sensory", "22", "<spare4>" }, 
		{ "5", "General sensory", "23", "<spare5>" }, 
		{ "5", "General sensory", "24", "<spare6>" }, 
		{ "5", "General sensory", "25", "<spare7>" }, 
		{ "5", "General sensory", "26", "<spare8>" }, 
		{ "5", "General sensory", "27", "<spare9>" }, 
		{ "5", "General sensory", "28", "<spare10>" }, 
		{ "5", "General sensory", "29", "<spare11>" }, 
		{ "5", "General sensory", "30", "<spare12>" }, 
		{ "5", "General sensory", "31", "<spare13>" }, 
		{ "6", "Decision", "0", "Default (quiescent/stay)" }, 
		{ "6", "Decision", "1", "Activate 1 it" }, 
		{ "6", "Decision", "2", "Activate 2 it" }, 
		{ "6", "Decision", "3", "Deactivate it" }, 
		{ "6", "Decision", "4", "Approach it" }, 
		{ "6", "Decision", "5", "Retreat frm it" }, 
		{ "6", "Decision", "6", "Get it" }, 
		{ "6", "Decision", "7", "Drop all" }, 
		{ "6", "Decision", "8", "Say what you need" }, 
		{ "6", "Decision", "9", "Rest" }, 
		{ "6", "Decision", "10", "Travel west" }, 
		{ "6", "Decision", "11", "Travel east" }, 
		{ "6", "Decision", "12", "<unallocated 1>" }, 
		{ "6", "Decision", "13", "<unallocated 1>" }, 
		{ "6", "Decision", "14", "<unallocated 1>" }, 
		{ "6", "Decision", "15", "<unallocated 1>" }, 
		{ "7", "Attention/seek", "0", "<ID 0>" }, 
		{ "7", "Attention/seek", "1", "<ID 1> (Creature's name)" }, 
		{ "7", "Attention/seek", "2", "<ID 2> (hand name)" }, 
		{ "7", "Attention/seek", "3", "<ID 3> (red lift call button)" }, 
		{ "7", "Attention/seek", "4", "<ID 4> (water)" }, 
		{ "7", "Attention/seek", "5", "<ID 5> (herb/plant)" }, 
		{ "7", "Attention/seek", "6", "<ID 6> (egg)" }, 
		{ "7", "Attention/seek", "7", "<ID 7> (food)" }, 
		{ "7", "Attention/seek", "8", "<ID 8> (drink)" }, 
		{ "7", "Attention/seek", "9", "<ID 9> (vendor)" }, 
		{ "7", "Attention/seek", "10", "<ID 10> (music)" }, 
		{ "7", "Attention/seek", "11", "<ID 11> (animal)" }, 
		{ "7", "Attention/seek", "12", "<ID 12> (fire)" }, 
		{ "7", "Attention/seek", "13", "<ID 13> (shower/clock)" }, 
		{ "7", "Attention/seek", "14", "<ID 14> (toy)" }, 
		{ "7", "Attention/seek", "15", "<ID 15> (bigtoy)" }, 
		{ "7", "Attention/seek", "16", "<ID 16> (weed)" }, 
		{ "7", "Attention/seek", "17", "<ID 17>" }, 
		{ "7", "Attention/seek", "18", "<ID 18>" }, 
		{ "7", "Attention/seek", "19", "<ID 19>" }, 
		{ "7", "Attention/seek", "20", "<ID 20>" }, 
		{ "7", "Attention/seek", "21", "<ID 21>" }, 
		{ "7", "Attention/seek", "22", "<ID 22>" }, 
		{ "7", "Attention/seek", "23", "<ID 23>" }, 
		{ "7", "Attention/seek", "24", "<ID 24>" }, 
		{ "7", "Attention/seek", "25", "<ID 25>" }, 
		{ "7", "Attention/seek", "26", "<ID 26> (mover)" }, 
		{ "7", "Attention/seek", "27", "<ID 27> (lift)" }, 
		{ "7", "Attention/seek", "28", "<ID 28> (computer)" }, 
		{ "7", "Attention/seek", "29", "<ID 29> (fun/projector)" }, 
		{ "7", "Attention/seek", "30", "<ID 30> (bang/cannon)" }, 
		{ "7", "Attention/seek", "31", "<ID 31>" }, 
		{ "7", "Attention/seek", "32", "<ID 32>" }, 
		{ "7", "Attention/seek", "33", "<ID 33>" }, 
		{ "7", "Attention/seek", "34", "<ID 34>" }, 
		{ "7", "Attention/seek", "35", "<ID 35>" }, 
		{ "7", "Attention/seek", "36", "<ID 36> (norn)" }, 
		{ "7", "Attention/seek", "37", "<ID 37> (grendel)" }, 
		{ "7", "Attention/seek", "38", "<ID 38>" }, 
		{ "7", "Attention/seek", "39", "<ID 39> " }
	};
	
	public static PairSimple<String, String> getLobeAndInputCell(int lobeNB, int cellNB) {
		int iStarter = 0;
		switch(lobeNB) { // ** Some Optimisation ** 
		case 1:iStarter =  00;break;
		case 2:iStarter =  16;break;
		case 3:iStarter =  56;break;
		case 4:iStarter =  72;break;
		case 5:iStarter = 112;break;
		case 6:iStarter = 144;break;
		case 7:iStarter = 160;break;
		default:
			Logger.printlnLog(LoggerLevel.LL_WARNING, "BrainLobeGene.getLobeAndInputCell(" + lobeNB + ", " + cellNB + ") return NULL !");
			return null; // Pair.of(lobeNB+"", cellNB+""); // throw new IllegalArgumentException("Unexpected value: " + lobeNB);
		}
		for (int i = iStarter ; i < BrainLobeGene.C1_LOBE_T0_TRANSLATION.length ; i++) {
			String[] currentSelection = BrainLobeGene.C1_LOBE_T0_TRANSLATION[i];
			if ( (currentSelection[0].equals(lobeNB + "")) && (currentSelection[2].equals(cellNB + "")) ) {
				return PairSimple.of(currentSelection[1], currentSelection[3]);
			}
		}
		Logger.printlnLog(LoggerLevel.LL_WARNING, "BrainLobeGene.getLobeAndInputCell(" + lobeNB + ", " + cellNB + ") return NULL !");
		return null; // Pair.of(lobeNB+"", cellNB+"");
	}

//		private int lobeNB, cellNB;
//		private String lobeName, cellName;
//		C1_BRAINS_CELLS(String lobenb, String lobeName, String cellnb, String cellName) {
//			this.lobeNB = Integer.parseInt(lobenb);
//			this.cellNB = Integer.parseInt(cellnb);
//			this.lobeName = lobeName;
//			this.cellName = cellName;
//		}
	
}
