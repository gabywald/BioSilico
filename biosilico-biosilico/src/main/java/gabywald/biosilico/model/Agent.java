package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gabywald.biosilico.interfaces.IAgentActions;
import gabywald.biosilico.interfaces.VariableContent;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.global.structures.ObservableObject;
import gabywald.global.structures.StringCouple;

/**
 * This class define an abstract Object for simulation which derived all objects in World. 
 * An Agent has chamicals Variables.  
 * @author Gabriel Chandesris (2009, 2020)
 */
public abstract class Agent extends ObservableObject 
							implements VariableContent, IAgentActions {
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
	/** TODO compute taxon ID */
	private UUID taxonID;
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
		this.variables.setVariable(StateType.EATABLE.getIndex(), (eatable) ? 100 : 0); /** Eatable */
		this.variables.setVariable(StateType.MOVABLE.getIndex(), (movable) ? 100 : 0); /** Movable */
	}
	
	/** Initialization helper for constructors. */
	private void init() {
		this.variables	= new Chemicals();
		this.taxonID	= UUID.randomUUID();
		this.current	= null;
		this.nextStep	= null;
		this.variables.setVariable(StateType.TYPEOF.getIndex(), AgentType.BIOSILICO_DAEMON.getIndex()); /** Default agent type.  */
		this.allOtherNames	= new ArrayList<String>();
		this.allOtherNames.add(""); /** Scientific name */
		this.allOtherNames.add(""); /** BioSilico name */
		this.allOtherNames.add(""); /** Common name */
		this.allOtherNames.add(""); /** Included name */
		this.rankDivision	= new StringCouple();
	}
	
	public Chemicals getChemicals()	{ return this.variables; }
	@Override
	public Chemicals getVariables()	{ return this.variables; }
	
	public boolean isAlive()		{ return this.alive; }
	public boolean isMovable() 
		{ return (this.variables.getVariable(StateType.MOVABLE.getIndex()) > 0); }
	public boolean isEatable() 
		{ return (this.variables.getVariable(StateType.EATABLE.getIndex()) > 0); }
	public boolean isFertile() 
		{ return (this.variables.getVariable(StateType.FERTILE.getIndex()) > 0); }
	public boolean isPregnant() 
		{ return (this.variables.getVariable(StateType.PREGNANT.getIndex()) > 0); }
	
	public void setAlive(boolean isAlive)		
		{ this.alive = isAlive; }
	
	protected void setMovable(boolean isMovable) 
		{ this.variables.setVariable(StateType.MOVABLE.getIndex(), isMovable ? 100 : 0); }
	protected void setEatable(boolean isEatable) 
		{ this.variables.setVariable(StateType.EATABLE.getIndex(), isEatable ? 100 : 0); }

	public int getCycle()			{ return this.variables.getVariable(StateType.AGING.getIndex()); }
	public void cyclePlusPlus()		{ this.variables.setVarPlusPlus(StateType.AGING.getIndex()); }
	
	public int getSex()				{ return this.variables.getVariable(StateType.GENDER.getIndex()); }
	public void setSex(int tosex)	{ this.variables.setVariable(StateType.GENDER.getIndex(), tosex); }
	
	public WorldCase getCurrentWorldCase()				{ return this.current; }
	public void setCurrentWorldCase(WorldCase current)	{ 
		this.current	= current;
		if (this.current != null) { this.current.addAgent( this ); }
	}
	
	public void setNextWorldCase(WorldCase nextWC)		{ this.nextStep = nextWC; }
	
	public int getDirection()							{ return this.direction; }
	public void setDirection(int direction)				{ this.direction = direction; }
	
	/** 
	 * Define what Agent does (if it does something). 
	 * @param local (WorldCase) Where Agent is located. 
	 */
	public abstract void execution(WorldCase local);
	
	@Override
	public boolean deplace() {
		if (this.nextStep != null) { this.current = this.nextStep; }
		this.nextStep = null;
		return true;
	}
	
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
