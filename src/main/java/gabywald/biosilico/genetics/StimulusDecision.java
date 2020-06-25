package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of Gene provoque some inputs or available behaviour of an organism. 
 * <br>This can although be used to import / export directly chemicals. 
 * <br><i>Creatures inspired. </i>
 * @author Gabriel Chandesris (2009)
 */
public class StimulusDecision extends GeneGattaca {
	/** Indicates if gene is Stimulus or Decision. */
	private boolean perception;
	/** Indicates if dectect a variable or an object. */
	private boolean object;
	/** Which variable (or object) treated like a receptor. */
	private int indicator;
	/** Level of detection. */
	private int threshold;
	/** A specific attribute of variable or object. */
	private int attribute;
	/** Variable to change like an emitter. */
	private int varia;
	/** Value to be set (emitter-like). */
	private int value;
	/** What action to execute (if decision). */
	private int scrip;
	
	/**
	 * Main Constructor for this kind of Gattaca Gene. <br>
	 * Aim of this type of Gene is to transmit information between brain and chemicals. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param age_min (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param age_max (int) Maximal age of activation. <b>In general is 999. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Rate of mutation of this Gene. 
	 * @param perc (boolean) Perception or decision flag. 
	 * @param obje (boolean) Object or variable flag.
	 * @param indi (int) Indicator flag (to object type or variable) ; [000-999].
	 * @param thre (int) Threshold, minimal level of variable ; [000-999].
	 * @param attr (int) Attribute for object ; [000-999].
	 * @param vari (int) Variable to change ; [000-999].
	 * @param valu (int) Value to add / remove / set ; [000-999].
	 * @param scri (int) Action to do ; [000-999].
	 */
	public StimulusDecision(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int age_min, int age_max, int sex, int mut_rate,
			boolean perc,boolean obje,int indi,int thre,int attr,
			int vari,int valu,int scri) {
		super(mutate, duplicate, delete, activ, age_min, age_max, sex, mut_rate);
		this.perception = perc;this.object = obje;
		this.indicator	= Gene.obtainValue(0, 999, indi);
		this.threshold	= Gene.obtainValue(0, 999, thre);
		this.attribute	= Gene.obtainValue(0, 999, attr);
		this.varia		= Gene.obtainValue(0, 999, vari);
		this.value		= Gene.obtainValue(0, 999, valu);
		this.scrip		= Gene.obtainValue(0, 999, scri);
	}
	
	public String reverseTranslation(boolean end) {
		String result = super.reverseTranslation(false);
		String tmp = "";
		tmp += (this.perception)?"0":"1";
		tmp += (this.object)?"2":"3";
		tmp += ((this.indicator < 100)?"0"+((this.indicator < 10)?"0":""):"")+this.indicator;
		tmp += ((this.threshold < 100)?"0"+((this.threshold < 10)?"0":""):"")+this.threshold;
		tmp += ((this.attribute < 100)?"0"+((this.attribute < 10)?"0":""):"")+this.attribute;
		tmp += ((this.varia < 100)?"0"+((this.varia < 10)?"0":""):"")+this.varia;
		tmp += ((this.value < 100)?"0"+((this.value < 10)?"0":""):"")+this.value;
		tmp += ((this.scrip < 100)?"0"+((this.scrip < 10)?"0":""):"")+this.scrip;
		
		for (int i = 0 ; i < tmp.length() ; i++) 
			{ result += GeneticTranslator.reverseGattaca(tmp.charAt(i)+""); }
		 /** end is given here "GGT" */
		return result+GeneticTranslator.reverseGattaca("*");
	}

	protected void exec(Organism orga) throws GeneException {
		if (this.perception) { /** This is an input / stimulus */
			if (this.object) { /** Acts from an object */
				/** Works for object AND one attribute. */
				if (orga.getLocation().hasAgentType(this.indicator)
						>= this.threshold) 
					{ orga.getChemicals()
						.setVariable(this.varia, this.value); }
				if (orga.getLocation().hasAgentType(this.attribute)
						>= this.threshold) { orga.getChemicals()
							.setVariable(this.varia, this.value); }
			} else { /** Acts from a variable located with attribute. */
				if (orga.getLocation().getDirection(this.attribute)
						.getVariables().getVariable(this.indicator) 
						>= this.threshold) { orga.getChemicals()
							.setVariable(this.varia, this.value); }
			}
		} else { /** this is an output / decision */
			/** Activity on variable or objet make sense in action. */
			if (this.object) {
				/** Acts to an object */
				if (orga.getChemicals() /** Action has to be done. */
						.getVariable(this.indicator) > this.threshold) {
					orga.activity(this.scrip,  this.indicator, this.threshold, 
								 this.attribute, this.varia, this.value);
					/** Variable change here is :  
					 * 		(standard) indicator set / tend to 0. 
					 * => here (--) because can be increased by action. */
					orga.getChemicals().setVarLessLess(this.indicator);
				}
			} else { 
				/** Acts to a variable : emit, receive... */
				if (orga.getChemicals() /** Action has to be done. */
						.getVariable(this.varia) > this.threshold) {
					orga.activity(this.scrip,  this.indicator, this.threshold, 
								 this.attribute, this.varia, this.value);
				}
			}
		}
	}
	
	public boolean getPerception()	{ return this.perception; }
	public boolean getObject()		{ return this.object; }
	
	public int getIndicator()	{ return this.indicator; }
	public int getThreshold()	{ return this.threshold; }
	public int getAttribute()	{ return this.attribute; }
	public int getVariable()	{ return this.varia; }
	public int getValue()		{ return this.value; }
	public int getScript()		{ return this.scrip; }

	
	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+this.perception+"\t"+
							this.object+"\t"+this.indicator+"\t"+
							this.threshold+"\t"+this.attribute+"\t"+
							this.varia+"\t"+this.value+"\t"+this.scrip+"\t";
		return stringenize;
	}
}
