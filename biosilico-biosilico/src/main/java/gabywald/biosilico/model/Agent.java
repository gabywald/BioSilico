package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.interfaces.VariableContent;
import gabywald.global.structures.ObservableObject;
import gabywald.global.structures.StringCouple;

/**
 * This class define an abstract Object for simulation which derived all objects in World. 
 * An Agent has chamicals Variables.  
 * @author Gabriel Chandesris (2009, 2020)
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
	private List<String> allOtherNames;
	/** Rank and Division of the Agent / Organism. */
	private StringCouple rankDivision;
	
	/** Default Constructor (not alive, not eatable, but movable).  */
	public Agent() {
		this(false, false, true);
	}
	
	/**
	 * Constructor with some parameters. 
	 * @param alive (boolean)
	 * @param movable (boolean)
	 * @param eatable (boolean)
	 */
	public Agent(boolean alive, boolean movable, boolean eatable) {
		this.init();
		this.alive		= alive;
		this.variables.setVariable(944, (eatable)?100:0); /** Eatable */
		this.variables.setVariable(943, (movable)?100:0); /** Movable */
	}
	
	/** Initialization helper for constructors. */
	private void init() {
		this.variables	= new Chemicals();
		this.taxonID	= null;
		this.current	= null;
		this.nextStep	= null;
		this.variables.setVariable(942, 901); /** Default agent type.  */
		this.allOtherNames	= new ArrayList<String>();
		this.allOtherNames.add(""); /** Scientific name */
		this.allOtherNames.add(""); /** BioSilico name */
		this.allOtherNames.add(""); /** Common name */
		this.allOtherNames.add(""); /** Included name */
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
	
	protected void setAlive(boolean isAlive)		
		{ this.alive = isAlive; }
	protected void setMovable(boolean isMovable) 
		{ this.variables.setVariable(943, isMovable ? 100 : 0); }
	protected void setEatable(boolean isEatable) 
		{ this.variables.setVariable(944, isEatable ? 100 : 0); }

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
	
	@Override
	public void run() {
		while(this.alive) {
			this.setState("cycle "+this.getCycle()+" = = = = = \n");
			this.execution(this.current);
			this.deplace();
			// ***** XXX to make organism death
			if (this.getCycle() >= 999) { this.alive = false; }
			// ***** some messages
			for (int i = 0 ; i < 40 ; i++) 
				{ this.addState(this.variables.getVariable(i)+":"); }
			// ***** next cycle
			// ***** XXX if cycle not managed biochemicaly. (aging++)
			this.cyclePlusPlus();
			this.change();
		}
	}
	
	
	public void setRank(String rank) 
		{ this.rankDivision.setValueA(rank); }
	public void setDivision(String division) 
		{ this.rankDivision.setValueB(division); }
	
	public void setNameScientific(String scientificName) 
		{ this.allOtherNames.set(0, scientificName); }
	public void setNameBiosilico(String biosilicoName) 
		{ this.allOtherNames.set(1, biosilicoName); }
	public void setNameCommon(String commonName) 
		{ this.allOtherNames.set(2, commonName); }
	public void setNameIncluded(String includedName) 
		{ this.allOtherNames.set(3, includedName); }
	public void addOtherName(String otherName)
		{ this.allOtherNames.add(otherName); }
	
	public List<String> getAllNames()
		{ return this.allOtherNames; }
	
	public String getBioSilicoName() 
		{ return this.allOtherNames.get(1); }
	public String getScientificName()	
		{ return this.allOtherNames.get(0); }
	
	public String getRank()				
		{ return this.rankDivision.getValueA(); }
	public String getDivision()				
		{ return this.rankDivision.getValueB(); }
	public String getUniqueID()			
		{ return ((this.taxonID == null)?"unknown":this.taxonID.toString()); }
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result	.append("TAXON ID\t")
				.append( ((this.taxonID == null)?"unknown":this.taxonID.toString()) )
				.append( "\n" );
		// ***** Names export. 
		result	.append("SCIENTIFIC NAME\t").append(this.allOtherNames.get(0)).append( "\n" );
		result	.append("OTHER NAMES\n\tBIOSILICO COMMON NAME\t").append(this.allOtherNames.get(1)).append( "\n" );
		result	.append("\tCOMMON NAME\t\t").append(this.allOtherNames.get(2)).append( "\n" );
		result	.append("\tINCLUDES\t\t").append(this.allOtherNames.get(3)).append( "\n" );
		if (this.allOtherNames.size() > 4) {
			result.append("\tNAMES\n");
			for (int i = 4 ; i < this.allOtherNames.size() ; i++)
				{ result.append("\t\t").append(this.allOtherNames.get(i)).append( "\n" ); }
		}
		result	.append("RANK\t").append(this.rankDivision.getValueA()).append( "\n" );
		result	.append("DIVISION\t").append(this.rankDivision.getValueB()).append( "\n" );
		result	.append("CHEMICAL VARIABLES\n");
		result	.append(this.variables.toString()).append( "\n" );
		return result.toString();
	}
}
