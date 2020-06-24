package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the genetically specified result of a specific stimulus being applied to the creature. For example, releasing pain into the drive 1 when hit.
	{ StimulusNumber ; Significance ; Sensory-Neurone ; Intensity ; Features ; ...
	... ; Drive0 ; Amount0 ; Drive1 ; Amount1 ; Drive2 ; Amount2 ; Drive3 ; Amount3 }
	Concerning features : 
	All the observed values can be explained with the last three bits of the byte:
	
	    Bit # 7 6 5 4 3 2 1 0
	      00 - 0 0 0 0 0 0 0 0
	      01 - 0 0 0 0 0 0 0 1
	      02 - 0 0 0 0 0 0 1 0
	      04 - 0 0 0 0 0 1 0 0 	
	
	    Bit 0 - Modulate using sensory signal
	    Bit 1 - Add offset to neu
	    Bit 2 - Detected even if asleep
	
	In summary:
	
	    00 - 
	    01 - Modulate
	    02 -           Add offset (unused)
	    04 -                       Detected
 
 * @author Gabriel Chandesris (2013)
 */
public class StimulusGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param stiNum (int)
	 * @param signi (int)
	 * @param neuSen (int)
	 * @param intensity (int)
	 * @param feats (int)
	 * @param dri1 (int)
	 * @param qnt1 (int)
	 * @param dri2 (int)
	 * @param qnt2 (int)
	 * @param dri3 (int)
	 * @param qnt3 (int)
	 * @param dri4 (int)
	 * @param qnt4 (int)
	 * @deprecated Use {@link StimulusGene#StimulusGene(List)} instead !
	 */
	private StimulusGene(	int stiNum, int signi, int neuSen, int intensity, 
							int feats, 
							int dri1, int qnt1, 
							int dri2, int qnt2, 
							int dri3, int qnt3, 
							int dri4, int qnt4) {
		super(2, 0);
		this.data = new ArrayList<UnsignedByte>(13);
		this.data.add(new UnsignedByte(stiNum));
		this.data.add(new UnsignedByte(signi));
		this.data.add(new UnsignedByte(neuSen));
		this.data.add(new UnsignedByte(intensity));
		this.data.add(new UnsignedByte(feats));
		this.data.add(new UnsignedByte(dri1));
		this.data.add(new UnsignedByte(qnt1));
		this.data.add(new UnsignedByte(dri2));
		this.data.add(new UnsignedByte(qnt2));
		this.data.add(new UnsignedByte(dri3));
		this.data.add(new UnsignedByte(qnt3));
		this.data.add(new UnsignedByte(dri4));
		this.data.add(new UnsignedByte(qnt4));
	}
	
	public StimulusGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(2, 0, datas, 13); /* size expected is 13 ! */ }
	
	public int getStimulusNumber()	{ return this.data.get(0).getValue(); }
	public int getSignificance()	{ return this.data.get(1).getValue(); }
	public int getSensoryNeurone()	{ return this.data.get(2).getValue(); }
	public int getIntensity()		{ return this.data.get(3).getValue(); }
	
	public int getDrive1()	{ return this.data.get(5).getValue(); }
	public int getAmount1()	{ return this.data.get(6).getValue(); }
	public int getDrive2()	{ return this.data.get(7).getValue(); }
	public int getAmount2()	{ return this.data.get(8).getValue(); }
	public int getDrive3()	{ return this.data.get(9).getValue(); }
	public int getAmount3()	{ return this.data.get(10).getValue(); }
	public int getDrive4()	{ return this.data.get(11).getValue(); }
	public int getAmount4()	{ return this.data.get(12).getValue(); }
	
	public boolean isModulateUsingSensorySignal()		
		{ return CreatureGene.binaryCompleted(this.data.get(4).getValue(), 8, '0').charAt(8) == '1'; }
	public boolean isAddOffsetToNeu()	
		{ return CreatureGene.binaryCompleted(this.data.get(4).getValue(), 8, '0').charAt(7) == '1'; }
	public boolean isDeletable()	
		{ return CreatureGene.binaryCompleted(this.data.get(4).getValue(), 8, '0').charAt(6) == '1'; }
	
	public static String[] AVAILABLE_STIMULI = {
		"Disappointment", 
		"Pointer pats me", 
		"Creature pats me", 
		"Pointer slaps me", 
		"Creature slaps me", 
		"It is approaching", 
		"It is retreating", 
		"I bump into wall", 
		"Object comes into view", 
		"Unrecognized word", 
		"Heard user speak", 
		"Heard creature speak", 
		"I am quiescent (periodic)", 
		"I've activated1 (push)", 
		"I've activated2 (pull)", 
		"I've deactivated", 
		"I am approaching (periodic)", 
		"I have retreated", 
		"I have got", 
		"I have dropped", 
		"I've stated need", 
		"I am resting (periodic)", 
		"I am sleeping (periodic)", 
		"I am travelling (periodic)", 
		"I've been pushed", 
		"I've been hit", 
		"I've eaten something", 
		"<spare action>", 
		"Involuntary action 0 (flinch)", 
		"Involuntary action 1 (lay egg)", 
		"Involuntary action 2 (sneeze)", 
		"Involuntary action 3 (cough)", 
		"Involuntary action 4 (shiver)", 
		"Involuntary action 5 (sleep)", 
		"Involuntary action 6 (languish)", 
		"Involuntary action 7", 
		"Approaching edge", 
		"Retreating from edge", 
		"Falling through air", 
		"Impact post-fall", 
		"<spare action>", "<spare action>", "<spare action>", "<spare action>", 
		"I've hit something", 
		"I've mated"
	};
	
	public static String[] SENSORY_NEURON = {
		"I've been patted", 
		"I've been slapped", 
		"I've bumped a wall", 
		"I am near a wall", 
		"I am in a vehicle", 
		"User has spoken", 
		"Creature has spoken", 
		"Own kind has spoken", 
		"Audible event", 
		"Visible event", 
		"It is approaching", 
		"It is retreating", 
		"It is near to me", 
		"It is active", 
		"It is an object", 
		"It is a creature", 
		"It is my sibling", 
		"It is my parent", 
		"It is my child", 
		"It is opposite sex", 
		"It has pushed me", 
		"It has hit me", 
		"<spare>", "<spare>", "<spare>", "<spare>", "<spare>", "<spare>", 
		"Approaching edge", 
		"Retreating from edge", 
		"Falling through air", 
		"Impact post-fall", 
		"<none>"				// XXX NOTE [20130828] is the FF / 255 / Last !!
	};
}
