package gabywald.biosilico.model;

import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.interfaces.AgentContent;
import gabywald.biosilico.model.decisions.DecisionBuilder;
import gabywald.biosilico.model.decisions.IDecision;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.biosilico.structures.ExtendedLineageItem;
import gabywald.biosilico.view.GeneJPanel;
import gabywald.global.data.StringUtils;
import gabywald.global.structures.StringCouple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class intends to have generic organisms and test genes implementations. 
 * Organism could contain other Agent's. 
 * @author Gabriel Chandesris (2009-2010, 2020)
 */
public class Organism extends Agent implements AgentContent {
	/** Brain of current Organism (could be null). */
	private Brain currentBrain;
	/** An organism can contain several Agent's. */
	private List<Agent> liste;
	/** Genome of the Organism. */
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
		this.variables.setVariable(StateType.TYPEOF.getIndex(), AgentType.BIOSILICO_DAEMON.getIndex());
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

	public int lengthLineage()
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
	
	public void setOrganismType(AgentType type) {
		this.variables.setVariable(StateType.TYPEOF.getIndex(), type.getIndex());
	}
	
	public int getOrganismType() {
		return this.variables.getVariable(StateType.TYPEOF.getIndex());
	}
	
	public AgentType getOrganismTypeAsType() {
		int type = this.variables.getVariable(StateType.TYPEOF.getIndex());
		return AgentType.getFrom( type );
	}
	
	public void setOrganismStatus(StatusType type) {
		this.variables.setVariable(StateType.STATUS.getIndex(), type.getIndex());
	}
	
	public int getOrganismStatus() {
		return this.variables.getVariable(StateType.STATUS.getIndex());
	}
	
	public StatusType getOrganismStatusAsType() {
		int type = this.variables.getVariable(StateType.STATUS.getIndex());
		return StatusType.getFrom( type );
	}

	public void execution(WorldCase local) {
		this.current = local;
		// ***** Genome is "executed". 
		this.genome.stream().forEach( c -> c.execution(this) );
		// TODO genome length to 0 : death ?! 
		// ***** Running the brain (if not null). 
		if (this.currentBrain != null) 
			{ this.currentBrain.networking(); }
	}

	/**
	 * Activity of organism (Decision)
	 * @param which (int) code of script / action. 
	 * @param object (int) code object. 
	 * @param threshold (int)
	 * @param attribute (int)
	 * @param variable (int)
	 * @param value (int)
	 */
	public void activity(	int which, int object, int threshold, 
							int attribute, int variable, int value) {
		
		DecisionType dType = DecisionType.getValue( which );
		if (dType == null) { return; }
		
		DecisionBuilder db	= new DecisionBuilder();
		IDecision decision	= db.type(dType).organism(this).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		if (decision != null) { decision.action(); }
		
	}
	
	/**
	 * Add a state as agent. 
	 * @param txt
	 */
	public void think(String txt) {
		this.addState(txt);
	}

	@Override
	public boolean push()		{ return true; }
	@Override
	public boolean pull()		{ return true; }
	@Override
	public boolean stop()		{ return true; }
	@Override
	public boolean slap()		{ return true; }
	
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
	public int hasAgentType(AgentType type) {
		return (int)this.liste.stream().filter( a -> (a.getChemicals().getVariable(StateType.TYPEOF.getIndex()) == type.getIndex()) ).count();
	}
	
	@Override
	public Agent getAgentType(AgentType type) {
		if (this.liste.stream().anyMatch( p -> p.getChemicals().getVariable(StateType.TYPEOF.getIndex()) == type.getIndex() )) {
			for (int i = 0 ; i < this.liste.size() ; i++) {
				if (this.liste.get(i).getChemicals().getVariable(StateType.TYPEOF.getIndex()) == type.getIndex()) { 
					return this.liste.get(i);
				}
			}
		}
		return null;
	}
	
	@Override
	public int hasAgentStatus(StatusType type) {
		return (int)this.liste.stream().filter( a -> (a.getChemicals().getVariable(StateType.STATUS.getIndex()) == type.getIndex()) ).count();
	}
	
	@Override
	public Agent getAgentStatus(StatusType type) {
		if (this.liste.stream().anyMatch( p -> p.getChemicals().getVariable(StateType.STATUS.getIndex()) == type.getIndex() )) {
			for (int i = 0 ; i < this.liste.size() ; i++) {
				if (this.liste.get(i).getChemicals().getVariable(StateType.STATUS.getIndex()) == type.getIndex()) { 
					return this.liste.get(i);
				}
			}
		}
		return null;
	}
	
	@Override
	public void addAgent(Agent object) { 
		if (object != null) { this.liste.add(object); } 
	}

	public String toString() {
		// TODO use of Java 8 streams
		StringBuilder result = new StringBuilder();
		// ***** Get String definition of SuperClass. 
		result.append(super.toString());
		// ***** Current instance. 
		result.append("GENETIC CODE\n\tID\t").append(this.geneticCodes.get(0).getValueA()).append( "\n" );
		result.append("\tNAME\t").append(this.geneticCodes.get(0).getValueB()).append( "\n" );
		for (int i = 1 ; i < this.geneticCodes.size() ; i++) {
			result.append("SUB GENETIC CODE\n\tID\t").append(this.geneticCodes.get(i).getValueA()).append( "\n" );
			result.append("\tNAME\t").append(this.geneticCodes.get(i).getValueB()).append( "\n" );
		}
		result.append("LINEAGE\n");
		if (this.extendedlineage.size() == 0)
			{ result.append("\tNO DATA\n"); }
		else {
			this.extendedlineage.stream().forEach( el -> result.append( "\t" ).append( el.getScientificName() ).append(" (").append( el.getUniqueID() ).append(")\n") );
		}
		
		result.append("GENOME\n");
		for (int i = 0 ; i < this.genome.size() ; i++) {
			Chromosome chr = this.genome.get(i);
			for (int j = 0 ; j < chr.length() ; j++) { 
				result.append( "\t" ).append(chr.getGene(j).getName()).append( "\t" ).append(chr.getGene(j).reverseTranslation(true)).append( "\n" );
			}
			if (i < this.genome.size()-1) { 
				String delim = StringUtils.repeat("-", 50);
				result.append( "\t" ).append(delim).append( "\t" ).append(delim).append( "\n" );
			}
		} // END "for (int i = 0 ; i < this.genome.size() ; i++)"

		if (this.currentBrain != null) { 
			result.append("BRAIN HEIGHT\t")	.append(GeneJPanel.convertTwoChars(this.currentBrain.getHeight())).append( "\n" );
			result.append("BRAIN WIDTH\t")	.append(GeneJPanel.convertTwoChars(this.currentBrain.getWidth() )).append( "\n" );
			result.append("BRAIN DEPTH\t")	.append(GeneJPanel.convertTwoChars(this.currentBrain.getDepth() )).append( "\n" );
			result.append("NEURONS LIST DESCRIPTION\n\tNO DATA\n");
			// ***** TODO record neural networks / lobes ! 
		} else { result.append("NO DATA ABOUT BRAIN, LOBES AND NEURONS\n"); }

		return result.toString();
	}

}
