package gabywald.biosilico.model;

import gabywald.biosilico.interfaces.VariableContent;
import gabywald.global.structures.ObservableObject;
import gabywald.global.structures.StringCouple;
import gabywald.global.structures.StringListe;

/**
 * This class define an abstract Object for simulation which derived all objects in World. 
 * An Agent has chamicals Variables.  
 * @author Gabriel Chandesris (2009)
 */
public abstract class Agent extends ObservableObject 
							implements VariableContent {
	/** Chemical concentrations. */
	protected Chemicals variables;
	/** Flag to know if it is alive or not. */
	protected boolean alive;
	/** Direction where Agent is going (if move). */
	protected int direction;
	/** Current location of the agent. */
	protected WorldCase current;
	/** Next location of the agent (if move). */
	protected WorldCase nextStep;
	/** TODO calcul taxon ID */
	private Integer taxonID;
	/** 
	 * Names of the agent / organism (reserved spaces, each could be empty). 
	 * <br>First is Scientific name. 
	 * <br>Second is BioSilico name. 
	 * <br>Third is Common name. 
	 * <br>Fourth is Included name. 
	 * <br>Followings are Others names. 
	 */
	private StringListe allOtherNames;
	/** Rank and Division of the Agent / Organism. */
	private StringCouple rankDivision;
	
	/** Default Constructor (not eatable, but movable).  */
	public Agent() {
		this.init();
		this.alive		= false;
		this.variables.setVariable(931, 0); /** Not eatable */
		this.variables.setVariable(930, 100); /** Movable */
	}
	
	/**
	 * Construcotr with some parameters. 
	 * @param alive (boolean)
	 * @param movable (boolean)
	 * @param eatable (boolean)
	 */
	public Agent(boolean alive,boolean movable,boolean eatable) {
		this.init();
		this.alive		= alive;
		this.variables.setVariable(931, (eatable)?100:0); /** Eatable */
		this.variables.setVariable(930, (movable)?100:0); /** Movable */
	}
	
	/** Initialization helper for constructors. */
	private void init() {
		this.variables	= new Chemicals();
		this.taxonID	= null;
		this.current	= null;
		this.nextStep	= null;
		this.variables.setVariable(942, 901); /** Default agent type.  */
		this.allOtherNames	= new StringListe();
		this.allOtherNames.addString(""); /** Scientific name */
		this.allOtherNames.addString(""); /** BioSilico name */
		this.allOtherNames.addString(""); /** Common name */
		this.allOtherNames.addString(""); /** Included name */
		this.rankDivision	= new StringCouple();
		
	}
	
	public Chemicals getChemicals()	{ return this.variables; }
	public Chemicals getVariables()	{ return this.variables; }
	public boolean isAlive()		{ return this.alive; }
	public boolean isMovable() 
		{ return (this.variables.getVariable(943) > 0); }
	public boolean isEatable() 
		{ return (this.variables.getVariable(944) > 0); }
	public boolean isFertile() 
		{ return (this.variables.getVariable(945) > 0); }
	public boolean isPregnant() 
		{ return (this.variables.getVariable(946) > 0); }

	
	public int getCycle()			{ return this.variables.getVariable(941); }
	public void cyclePlusPlus()		{ this.variables.setVarPlusPlus(941); }
	
	public int getSex()				{ return this.variables.getVariable(940); }
	public void setSex(int tosex)	{ this.variables.setVariable(940, tosex); }
	
	public WorldCase getLocation()				{ return this.current; }
	public void setLocation(WorldCase current)	{ this.current = current; }
	
	/** 
	 * Define what Agent does (if it does something). 
	 * @param local (WorldCase) Where Agent is located. 
	 */
	public abstract void execution(WorldCase local);
	
	/** Define the movement of the Agent (if it is moving). */
	public abstract void deplace();
	
	/** Agent is pushed. */
	public abstract void push();
	/** Agent is pulled. */
	public abstract void pull();
	/** Agent is stopped. */
	public abstract void stop();
	/** Agent is slapped. */
	public abstract void slap();
	
	
	public void run() {
		while(this.alive) {
			this.setState("cycle "+this.getCycle()+" = = = = = \n");
			this.execution(this.current);
			this.deplace();
			/** XXX to make organism death */
			if (this.getCycle() >= 999) { this.alive = false; }
			/** some messages */
			for (int i = 0 ; i < 40 ; i++) 
				{ this.addState(this.variables.getVariable(i)+":"); }
			/** next cycle */
			/** XXX if cycle not managed biochemicaly. (aging++) */
			this.cyclePlusPlus();
			this.change();
		}
	}
	
	
	public void setRank(String rank) 
		{ this.rankDivision.setValueA(rank); }
	public void setDivision(String division) 
		{ this.rankDivision.setValueB(division); }
	
	public void setNameScientific(String scientificName) 
		{ this.allOtherNames.setString(scientificName, 0); }
	public void setNameBiosilico(String biosilicoName) 
		{ this.allOtherNames.setString(biosilicoName, 1); }
	public void setNameCommon(String commonName) 
		{ this.allOtherNames.setString(commonName, 2); }
	public void setNameIncluded(String includedName) 
		{ this.allOtherNames.setString(includedName, 3); }
	public void addOtherName(String otherName)
		{ this.allOtherNames.addString(otherName); }
	
	public StringListe getAllNames()
		{ return this.allOtherNames; }
	
	public String getBioSilicoName() 
		{ return this.allOtherNames.getString(1); }
	public String getScientificName()	
		{ return this.allOtherNames.getString(0); }
	
	public String getRank()				
		{ return this.rankDivision.getValueA(); }
	public String getDivision()				
		{ return this.rankDivision.getValueB(); }
	public String getUniqueID()			
		{ return ((this.taxonID == null)?"unknown":this.taxonID.toString()); }
	
	public String toString() {
		String result = new String();
		
		result += "TAXON ID\t"+
					((this.taxonID == null)?"unknown":this.taxonID.toString())
					+"\n";
		/** Names export. */
		result += "SCIENTIFIC NAME\t"+this.allOtherNames.getString(0)+"\n";
		result += "OTHER NAMES\n\tBIOSILICO COMMON NAME\t"+
				this.allOtherNames.getString(1)+"\n";
		result += "\tCOMMON NAME\t\t"+this.allOtherNames.getString(2)+"\n";
		result += "\tINCLUDES\t\t"+this.allOtherNames.getString(3)+"\n";
		if (this.allOtherNames.length() > 4) {
			result += "\tNAMES\n";
			for (int i = 4 ; i < this.allOtherNames.length() ; i++)
				{ result += "\t\t"+this.allOtherNames.getString(i)+"\n"; }
		}
		result += "RANK\t"+this.rankDivision.getValueA()+"\n";
		result += "DIVISION\t"+this.rankDivision.getValueB()+"\n";
		result += "CHEMICAL VARIABLES\n";
		result += this.variables.toString();
//		for (int i = 0 ; i < this.variables.length() ; i++) { 
//			String chemIndex = this.chemicalVariables
//										.getStringCouple(i).getValueA();
//			String chemValue = this.chemicalVariables
//										.getStringCouple(i).getValueB();
//			result += "\t"+chemIndex+"\t"+chemValue+"\n";
//		}
		return result;
	}
}
