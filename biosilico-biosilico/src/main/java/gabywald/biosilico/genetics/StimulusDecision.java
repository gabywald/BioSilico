package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of Gene provoque some inputs or available behaviour of an organism. 
 * <br>This can although be used to import / export directly chemicals. 
 * <br><i>Creatures inspired. </i>
 * @author Gabriel Chandesris (2009, 2020)
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
	 * @param ageMin (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param ageMax (int) Maximal age of activation. <b>In general is 999. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mutRate (int) Rate of mutation of this Gene. 
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
			int ageMin, int ageMax, int sex, int mutRate,
			boolean perc, boolean obje, 
			int indi, int thre, int attr, int vari, int valu, int scri) {
		super(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutRate);
		this.perception = perc;this.object = obje;
		this.indicator	= Gene.obtainValue(0, 999, indi);
		this.threshold	= Gene.obtainValue(0, 999, thre);
		this.attribute	= Gene.obtainValue(0, 999, attr);
		this.varia		= Gene.obtainValue(0, 999, vari);
		this.value		= Gene.obtainValue(0, 999, valu);
		this.scrip		= Gene.obtainValue(0, 999, scri);
	}
	
	public String reverseTranslation(boolean end) {
		String result		= super.reverseTranslation(false);
		StringBuilder tmp	= new StringBuilder();
		tmp.append((this.perception)?"0":"1");
		tmp.append((this.object)?"2":"3");
		tmp.append(Gene.convert0to999(this.indicator));
		tmp.append(Gene.convert0to999(this.threshold));
		tmp.append(Gene.convert0to999(this.attribute));
		tmp.append(Gene.convert0to999(this.varia));
		tmp.append(Gene.convert0to999(this.value));
		tmp.append(Gene.convert0to999(this.scrip));
		
		result += GeneticTranslator.reverseSequenceGattaca( tmp.toString() );
		
		// end is given here "GGT" 
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
