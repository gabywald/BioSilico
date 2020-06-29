package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

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
 * @author Gabriel Chandesris (2013)
 */
public class BrainLobeGene extends CreatureGene {
	/**

	 */
	
	public BrainLobeGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(0, 0, datas, 5 + 13 + 10 + 37 + 10 + 37); /* size expected is 5 + 13 + 10 + 37 + 10 + 37 ! */ }
	
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
}
