package gabywald.biosilico.genetics;

import java.util.Random;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.interfaces.IChemicals;
import gabywald.biosilico.interfaces.IGeneMutation;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of Gene is to permit chemicals reactions of these sort, in mass action chemical reaction <b>aA + bB -> cC + dD</b> : 
 * <p></p>
 * <ul>
 * <li>A + B -> C + D : standard chemical reaction</li>
 * <li>A + B -> C : fusion</li>
 * <li>A -> null : breakdown</li>
 * <li>A + B -> A + C : catalytic reaction (A is unchanged)</li>
 * <li>A + B -> A : catalytic breakdown of B </li>
 * </ul>
 * The reaction 'null to something' is avoided.
 * <br><i>Creatures inspired. </i>
 * @author Gabriel Chandesris (2009, 2020)
 */
public class BiochemicalReaction extends GeneGattaca {
	
	public static final int NEUTRAL_INDEX = SomeChemicals.NEUTRAL.getIndex();
	
	/** Chemical A (variables indice). */
	private int Achem;
	/** Coefficient applied to A. */
	private int Acoef;
	/** Chemical B (variables indice). */
	private int Bchem;
	/** Coefficient applied to B. */
	private int Bcoef;
	/** Chemical C (variables indice). */
	private int Cchem;
	/** Coefficient applied to C. */
	private int Ccoef;
	/** Chemical D (variables indice). */
	private int Dchem;
	/** Coefficient applied to D. */
	private int Dcoef;
	/** How quickly reaction is made. */
	private int KminVmax;

	/**
	 * Main Constructor for this kind of Gattaca Gene. <br>
	 * Aim of this type of Gene is to set a certain reaction between chemicals.<br>
	 * Reaction is of type <i>aA + bB => cC + dD</i> . 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param ageMax (int) Maximal age of activation. <b>In general is 999. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mutRate (int) Rate of mutation of this Gene. 
	 * @param Achem (int) Chemical A number.
	 * @param Acoef (int) How many of chemical A is used in reaction. 
	 * @param Bchem (int) Chemical B number.
	 * @param Bcoef (int) How many of chemical B is used in reaction. 
	 * @param Cchem (int) Chemical C number.
	 * @param Ccoef (int) How many of chemical C is used in reaction. 
	 * @param Dchem (int) Chemical D number.
	 * @param Dcoef (int) How many of chemical D is used in reaction. 
	 * @param KminVmax (int) Reaction speed. 
	 * @see GeneGattaca#GeneGattaca(boolean, boolean, boolean, boolean, int, int, int, int)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	public BiochemicalReaction(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int ageMin, int ageMax, int sex, int mutRate,
			int Achem, int Acoef,
			int Bchem, int Bcoef,
			int Cchem, int Ccoef,
			int Dchem, int Dcoef,
			int KminVmax) {
		super(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutRate);
		this.Achem = Gene.obtainValue(0, 999, Achem);
		this.Bchem = Gene.obtainValue(0, 999, Bchem);
		this.Cchem = Gene.obtainValue(0, 999, Cchem);
		this.Dchem = Gene.obtainValue(0, 999, Dchem);
		this.Acoef = Gene.obtainValue(0, 999, Acoef);
		this.Bcoef = Gene.obtainValue(0, 999, Bcoef);
		this.Ccoef = Gene.obtainValue(0, 999, Ccoef);
		this.Dcoef = Gene.obtainValue(0, 999, Dcoef);
		this.KminVmax = Gene.obtainValue(0, 999, KminVmax);
	}
	
	@Override
	public String reverseTranslation(boolean end) {
		String result		= super.reverseTranslation(false);
		StringBuilder tmp	= new StringBuilder();
		tmp.append(Gene.convert0to999(this.Achem));
		tmp.append(Gene.convert0to999(this.Acoef));
		tmp.append(Gene.convert0to999(this.Bchem));
		tmp.append(Gene.convert0to999(this.Bcoef));
		tmp.append(Gene.convert0to999(this.Cchem));
		tmp.append(Gene.convert0to999(this.Ccoef));
		tmp.append(Gene.convert0to999(this.Dchem));
		tmp.append(Gene.convert0to999(this.Dcoef));
		tmp.append(Gene.convert0to999(this.KminVmax));
		
		result += GeneticTranslator.reverseSequenceGattaca( tmp.toString() );
		
		// end is given here "GGT" 
		return result+GeneticTranslator.reverseGattaca("*");
	}
	
	@Override
	protected void exec(Organism orga) throws GeneException { 
		IChemicals vars = orga.getChemicals();
		// ***** Avoiding the reaction from nothing. 
		if ( (this.Achem == 0) && (this.Bchem == 0) )
			{ throw new GeneException("Chemical A and B are 0. "); }
		// ***** Need to get enough A and B. One can be 'default var'. 
		boolean reaction = this.testABchemicals(vars);
		int local_cycle = 0;
		while (reaction && (this.KminVmax > local_cycle) ) {
			if (this.Achem != NEUTRAL_INDEX) 
				{ vars.setVarLess(this.Achem, this.Acoef); }
			if (this.Bchem != NEUTRAL_INDEX) 
				{ vars.setVarLess(this.Bchem, this.Bcoef); }
			if (this.Cchem != NEUTRAL_INDEX) 
				{ vars.setVarPlus(this.Cchem, this.Ccoef); }
			if (this.Dchem != NEUTRAL_INDEX) 
				{ vars.setVarPlus(this.Dchem, this.Dcoef);}
			local_cycle++;
			reaction = this.testABchemicals(vars);
		} // end "while (reaction && (this.KminVmax > local_cycle) )"
		
	}
	
	private boolean testABchemicals(IChemicals vars) {
		return ( ( (this.Achem == NEUTRAL_INDEX) ||
						 (vars.getVariable(this.Achem) >= this.Acoef) )
				 && ( (this.Bchem == NEUTRAL_INDEX) ||
						 (vars.getVariable(this.Bchem) >= this.Bcoef) ) );
	}

	public int getAchem() { return this.Achem; }
	public int getAcoef() { return this.Acoef; }
	public int getBchem() { return this.Bchem; }
	public int getBcoef() { return this.Bcoef; }
	public int getCchem() { return this.Cchem; }
	public int getCcoef() { return this.Ccoef; }
	public int getDchem() { return this.Dchem; }
	public int getDcoef() { return this.Dcoef; }
	public int getKMVMs() { return this.KminVmax; }

	@Override
	public String toString() {
		String stringenize = this.reverseTranslation(true) + "\t" + super.toString() + 
							this.Acoef + "\t" + this.Achem + "\t" +
							this.Bcoef + "\t" + this.Bchem + "\t" +
							this.Ccoef + "\t" + this.Cchem + "\t" +
							this.Dcoef + "\t" + this.Dchem + "\t" + this.KminVmax+"\t";
		return stringenize;
	}

	@Override
	public Gene clone() {
		Gene toReturn = new BiochemicalReaction(	this.canMutate(), this.canDuplicate(), this.canDelete(), this.isActiv(), 
													this.getAgeMin(), this.getAgeMax(), this.getSexAct(), this.getMutationRate(), 
													this.Achem, this.Acoef, this.Bchem, this.Bcoef, 
													this.Cchem, this.Ccoef, this.Dchem, this.Dcoef, this.KminVmax);
		toReturn.setName( this.getName() );
		return toReturn;
	}
	
	@Override
	public void mutationChanges() {
		Random rand				= new Random();
		int selectedAttribute	= rand.nextInt(9);
		boolean moreOrLess		= rand.nextBoolean();
		switch( selectedAttribute ) {
		case(0):	this.Achem = IGeneMutation.mutate(this.Achem, moreOrLess, 999); break;
		case(1):	this.Acoef = IGeneMutation.mutate(this.Achem, moreOrLess, 999); break;
		case(2):	this.Bchem = IGeneMutation.mutate(this.Bchem, moreOrLess, 999); break;
		case(3):	this.Bcoef = IGeneMutation.mutate(this.Bcoef, moreOrLess, 999); break;
		case(4):	this.Cchem = IGeneMutation.mutate(this.Cchem, moreOrLess, 999); break;
		case(5):	this.Ccoef = IGeneMutation.mutate(this.Ccoef, moreOrLess, 999); break;
		case(6):	this.Dchem = IGeneMutation.mutate(this.Dchem, moreOrLess, 999); break;
		case(7):	this.Dcoef = IGeneMutation.mutate(this.Dcoef, moreOrLess, 999); break;
		case(8):	this.KminVmax = IGeneMutation.mutate(this.KminVmax, moreOrLess, 999); break;
		}
	}
}
