package gabywald.biosilico.genetics;

import java.util.Random;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.interfaces.IEnvironmentItem;
import gabywald.biosilico.interfaces.IGeneMutation;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of Gene make some inputs or available behavior of an organism. 
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
		this.perception	= perc;
		this.object		= obje;
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

	/**
	 * StimulusDecision, Some explanations :
	 * ***** perception : apply detection on WorldCase and chemicals of current organism
	 * (perception) && (object) => if (indicator||attribute >= threshold) => set value at variable (else 0)
	 * (perception) && ( ! object) => if (indicator at direction attribute >= threshold) => set value at variable (else 0)
	 * ***** not perception : chemicals of current organism then (if apply) decision 
	 * ( ! perception) && (object) => if (indicator > threshold) => Decision !
	 * ( ! perception) && ( ! object) => if (variable > threshold) => Decision !
	 */
	@Override
	protected void exec(Organism orga) throws GeneException {
		if (this.perception) { 
			// ***** This is an input / stimulus
			DirectionWorld direction	= DirectionWorld.get2DFrom( this.attribute );
			if (direction == null)		{ return; }
			IEnvironmentItem detectWC		= orga.getCurrentEnvironmentItem().getDirection( direction );
			
			if (this.object) { 
				// ***** Acts from an object 
				/** Works for object AND one attribute. */
				if ( (detectWC != null) && (detectWC.hasObjectType( ObjectType.getFrom(this.indicator) ) > this.threshold ) ) 
					{  orga.getChemicals().setVariable(this.varia, this.value); } 
				else { orga.getChemicals().setVariable(this.varia, 0); }

				// Logger.printlnLog(LoggerLevel.LL_DEBUG, this.attribute + "::" + ((detectWC != null)?detectWC.getPos().toString() + detectWC.hasObjectType( ObjectType.getFrom(this.indicator) ):"null") + " (" + orga.getCurrentWorldCase().hasObjectType( ObjectType.getFrom(this.indicator) ) + ")" +  "::" + this.varia + "::" + orga.getChemicals().getVariable(this.varia) );

			} else { 
				// ***** Acts from a variable located with attribute. 
				if ( (detectWC != null) && (detectWC.getChemicals().getVariable(this.indicator) > this.threshold) ) 
					{ orga.getChemicals().setVariable(this.varia, this.value); }
				else { orga.getChemicals().setVariable(this.varia, 0); }
			}
		} else { 
			// ***** this is an output / decision 
			/** Activity on variable or object make sense in action. */
			if (this.object) {
				// ***** Acts to an object 
				if (orga.getChemicals().getVariable(this.scrip) > this.threshold) {
					// ***** Action has to be done. 
					orga.activity(this.scrip,  this.indicator, this.threshold, 
								  this.attribute, this.varia, this.value);
					/** Variable change here is :  (standard) indicator set / tend to 0. 
					 * => here (--) because can be increased by action. */
					orga.getChemicals().setVarLessLess(this.scrip);
				} // END "if (orga.getChemicals().getVariable(this.indicator) > this.threshold)"
			} else { 
				// ***** Acts to a variable : emit, receive... death...
				// if (orga.getChemicals().getVariable(this.value) > this.threshold) {
					// ***** Action has to be done. 
					orga.activity(this.scrip,  this.indicator, this.threshold, 
								  this.attribute, this.varia, this.value);
				// } // END "if (orga.getChemicals().getVariable(this.varia) > this.threshold)"
			}
		}
	}
	
	public boolean getPerception()	{ return this.perception; }
	public boolean getObject()		{ return this.object; }
	
	public int getIndicator()		{ return this.indicator; }
	public int getThreshold()		{ return this.threshold; }
	public int getAttribute()		{ return this.attribute; }
	public int getVariable()		{ return this.varia; }
	public int getValue()			{ return this.value; }
	public int getScript()			{ return this.scrip; }

	@Override
	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+this.perception+"\t"+
							this.object+"\t"+this.indicator+"\t"+
							this.threshold+"\t"+this.attribute+"\t"+
							this.varia+"\t"+this.value+"\t"+this.scrip+"\t";
		return stringenize;
	}
	
	@Override
	public Gene clone() {
		Gene toReturn = new StimulusDecision(	this.canMutate(), this.canDuplicate(), this.canDelete(), this.isActiv(), 
												this.getAgeMin(), this.getAgeMax(), this.getSexAct(), this.getMutationRate(), 
												this.perception, this.object, this.indicator, this.threshold, 
												this.attribute, this.varia, this.value, this.scrip);
		toReturn.setName( this.getName() );
		return toReturn;
	}

	@Override
	public void mutationChanges() {
		Random rand				= new Random();
		int selectedAttribute	= rand.nextInt(8);
		boolean moreOrLess		= rand.nextBoolean();
		switch( selectedAttribute ) {
		case(0):	this.perception	= moreOrLess; break;
		case(1):	this.object		= moreOrLess; break;
		case(2):	this.indicator	= IGeneMutation.mutate(this.indicator, moreOrLess, 999); break;
		case(3):	this.threshold	= IGeneMutation.mutate(this.threshold, moreOrLess, 999); break;
		case(4):	this.attribute	= IGeneMutation.mutate(this.attribute, moreOrLess, 999); break;
		case(5):	this.varia		= IGeneMutation.mutate(this.varia, moreOrLess, 999); break;
		case(6):	this.value		= IGeneMutation.mutate(this.value, moreOrLess, 999); break;
		case(7):	this.scrip		= IGeneMutation.mutate(this.scrip, moreOrLess, 999); break;
		}
	}

}
