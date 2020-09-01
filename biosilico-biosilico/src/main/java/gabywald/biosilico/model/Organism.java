package gabywald.biosilico.model;

import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.interfaces.IAgentContent;
import gabywald.biosilico.model.decisions.DecisionBuilder;
import gabywald.biosilico.model.decisions.IDecision;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.biosilico.structures.ExtendedLineageItem;
import gabywald.global.structures.StringCouple;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class intends to have generic organisms and test genes implementations. 
 * Organism could contain other Agent's. 
 * @author Gabriel Chandesris (2009-2010, 2020)
 */
public class Organism extends Agent implements IAgentContent {
	/** Brain of current Organism (could be null). */
	private Brain currentBrain;
	/** An organism can contain several Agent's. */
	private List<Agent> liste;
	/** Genome of the Organism ; haploïd. */
	private List<Chromosome> genome;
	/** Extended lineage of the Organism. */
	private List<ExtendedLineageItem> extendedlineage;
	/** Set of Genetic code use. */
	private List<StringCouple> geneticCodes;

	/** Default Constructor with empty Genome. */
	public Organism() {
		super(true, true, true);
		this.genome				= new ArrayList<Chromosome>();
		// ***** XXX default organism's are Daemons (ch942 set to 935)
		this.variables.setVariable(StateType.AGENT_TYPE.getIndex(), AgentType.BIOSILICO_DAEMON.getIndex());
		this.geneticCodes		= new ArrayList<StringCouple>();
		this.geneticCodes.add(new StringCouple("0000000010", "Gattaca01"));
		this.currentBrain		= null;
		this.liste				= new ArrayList<Agent>();
		this.extendedlineage	= new ArrayList<ExtendedLineageItem>();
	}

	/**
	 * Constructor with several chromosomes (sex is 0). 
	 * @param genome (Chromosome[]) pre-build genome. 
	 */
	public Organism(Chromosome genome[]) {
		this();
		this.genome.addAll(Arrays.asList(genome));
	}

	/**
	 * Constructor with several chromosomes (sex is 0). 
	 * @param genome (ChromosomeListe) pre-build genome. 
	 */
	public Organism(List<Chromosome> genome) {
		this();
		this.genome	= genome;
	}

	/**
	 * Constructor with only one chromosome (sex is 0). 
	 * @param genome (Chromosome) pre-build genome. 
	 */
	public Organism(Chromosome genome) {
		this();
		this.genome.add(genome);
	}

	public Brain getBrain() 				{ return this.currentBrain; }
	public List<Chromosome> getGenome() 	{ return this.genome; }

	public void setGenome(List<Chromosome> genome) 
		{ this.genome = genome; }

	public int lineageSize()
		{ return this.extendedlineage.size(); }

	public void setExtendedLineage(List<ExtendedLineageItem> lineage) 
		{ this.extendedlineage = lineage; }
	
	public List<ExtendedLineageItem> getExtendedLineage() 
		{ return this.extendedlineage; }

	/**
	 * Create an instance of ExtendedLineageItem and add it to the end of the list. 
	 * @param uniqueID (String)
	 * @param scientificName (String)
	 * @param rank (String)
	 */
	public void addExtendedLineageItem(	String uniqueID,
			String scientificName,
			String rank) 
		{ this.extendedlineage.add(new ExtendedLineageItem(uniqueID, scientificName, rank)); }
	
	public void addExtendedLineageItem(	ExtendedLineageItem eli ) 
		{ this.extendedlineage.add( eli ); }

	public List<String> getSimpleLinage() {
		return this.extendedlineage.stream().map(el -> el.getScientificName()).collect(Collectors.toList());
	}

	public String getSimpleLineage(int i) 
		{ return this.extendedlineage.get(i).getScientificName(); }

	public List<StringCouple> getGeneticCodes()
		{ return this.geneticCodes; }

	/**
	 * Set a Brain for Organism (specific gene). instanciated by a specific Gene
	 * @param cb (Brain)
	 * @see BrainGene#BrainGene(boolean, boolean, boolean, boolean, int, int, int, int, int, int, int, int)
	 * @see BrainGene#exec(Organism)
	 */
	public void setBrain(Brain cb) { 
		this.currentBrain = cb; 
	}
	
	public void setOrganismStatus(StatusType type) {
		this.variables.setVariable(StateType.STATUS.getIndex(), type.getIndex());
	}
	
	public StatusType getOrganismStatus() {
		int type = this.variables.getVariable(StateType.STATUS.getIndex());
		return StatusType.getFrom( type );
	}

	public void execution(WorldCase local) {
		this.current = local;
		
		this.setState( "" );
		
		// ***** Genome is "executed". 
		// NOTE : here mainly works for haploïd genomes !! 
		this.genome.stream().forEach( c -> c.execution(this) );
		// TODO genome length to 0 : death ?! 
		// ***** Running the brain (if not null). 
		if (this.currentBrain != null) 
			{ this.currentBrain.networking(); }
	}

	/**
	 * Activity of organism (IDecision), launch action on IDecision (if apply), then return it. 
	 * @param which (int) code of script / action. 
	 * @param object (int) code object. 
	 * @param threshold (int)
	 * @param attribute (int) 
	 * @param variable (int) index of chemical. 
	 * @param value (int) value of chemical. 
	 * @return (IDecision) could be ull
	 */
	public IDecision activity(	int which, int object, int threshold, 
								int attribute, int variable, int value) {
		
		DecisionType dType = DecisionType.getFrom( which );
		if (dType == null) { return null; }
		
		DecisionBuilder db	= new DecisionBuilder();
		IDecision decision	= db.type(dType).organism(this).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		if (decision != null) { decision.action(); }
		
		return decision;
	}
	
	/**
	 * Add a state as agent. 
	 * @param txt
	 */
	public void think(String txt) {
		String stateMSG = this.getUniqueID() + "::" + txt;
		this.addState( stateMSG );
		Logger.printlnLog(LoggerLevel.LL_INFO, stateMSG);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.THINK);
	}

	@Override
	public boolean push()		{ 
		String msg = "Organism {" + this.getUniqueID() + "} PUSHed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.PUSH);
		return true; 
	}
	@Override
	public boolean pull()		{ 
		String msg = "Organism {" + this.getUniqueID() + "} PULLed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.PULL);
		return true; 
	}
	@Override
	public boolean stop()		{ 
		String msg = "Organism {" + this.getUniqueID() + "} STOPed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.STOP);
		return true; 
	}
	@Override
	public boolean slap()		{ 
		String msg = "Organism {" + this.getUniqueID() + "} SLAPed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.SLAP);
		return true; 
	}
	@Override
	public boolean rest()		{ 
		String msg = "Organism {" + this.getUniqueID() + "} RESTed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.REST);
		return true; 
	}
	@Override
	public boolean sleep()		{ 
		String msg = "Organism {" + this.getUniqueID() + "} SLEEPed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.SLEEP);
		return true; 
	}
	
	@Override
	public int getAgentListLength()		{ return this.liste.size(); }
	@Override
	public List<Agent> getAgentListe()	{ return this.liste; }
	@Override
	public Agent remAgent(int i)		{ return this.liste.remove(i); }
	@Override
	public boolean remAgent(Agent o)	{ return this.liste.remove(o); }
	@Override
	public Agent getAgent(int i)		{ return this.liste.get(i); }	
	
	@Override
	public int hasObjectType(ObjectType type) {
		return IAgentContent.hasType(type, StateType.TYPEOF.getIndex(), this.liste);
	}
	
	@Override
	public Agent getObjectType(ObjectType type) {
		return IAgentContent.getType(type, StateType.TYPEOF.getIndex(), this.liste);
	}
	
	@Override
	public int hasAgentType(AgentType type) {
		return IAgentContent.hasType(type, StateType.AGENT_TYPE.getIndex(), this.liste);
	}
	
	@Override
	public Agent getAgentType(AgentType type) {
		return IAgentContent.getType(type, StateType.AGENT_TYPE.getIndex(), this.liste);
	}
	
	@Override
	public int hasAgentStatus(StatusType status) {
		return IAgentContent.hasType(status, StateType.STATUS.getIndex(), this.liste);
	}
	
	@Override
	public Agent getAgentStatus(StatusType status) {
		return IAgentContent.getType(status, StateType.STATUS.getIndex(), this.liste);
	}
	
	@Override
	public void addAgent(Agent object) { 
		if (object != null) { this.liste.add(object); } 
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		// ***** Get String definition of SuperClass. 
		result.append(super.toString());
		// ***** Current instance. 
		result.append("GENETIC CODE\n\tID\t").append(this.geneticCodes.get(0).getValueA()).append( "\n" );
		result.append("\tNAME\t").append(this.geneticCodes.get(0).getValueB()).append( "\n" );
		this.geneticCodes.stream().forEach( gc -> {
			result.append("SUB GENETIC CODE\n\tID\t").append(gc.getValueA()).append( "\n" );
			result.append("\tNAME\t").append(gc.getValueB()).append( "\n" );
		});
		result.append("LINEAGE\n");
		if (this.extendedlineage.size() == 0)
			{ result.append("\tNO DATA\n"); }
		else {
			this.extendedlineage.stream().forEach( el -> result.append( "\t" ).append( el.getScientificName() ).append(" (").append( el.getUniqueID() ).append(")\n") );
		}
		
		result.append("GENOME\n");
		for (int i = 0 ; i < this.genome.size() ; i++) {
			Chromosome chr = this.genome.get(i);
			result.append("\tchr").append( i ).append("\t").append(chr.getName()).append("\n");
			chr.streamGene().forEach( g -> {
				result.append( "\t\t" ).append(g.getName()).append( "\t" ).append(g.reverseTranslation(true)).append( "\n" );
			});
		} // END "for (int i = 0 ; i < this.genome.size() ; i++)"

		result.append("BRAIN\n");
		if (this.currentBrain != null) 
			{ result.append( this.currentBrain.toString() ); }
		else 
			{ result.append("\tNO DATA ABOUT BRAIN, LOBES AND NEURONS\n"); }

		return result.toString();
	}

}
