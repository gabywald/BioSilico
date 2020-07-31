package gabywald.biosilico.model;

import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.interfaces.AgentContent;
import gabywald.biosilico.model.decisions.IDecision;
import gabywald.biosilico.model.reproduction.ReproductionAnima;
import gabywald.biosilico.model.reproduction.ReproductionBacta;
import gabywald.biosilico.model.reproduction.ReproductionDaemon;
import gabywald.biosilico.model.reproduction.ReproductionViridita;
import gabywald.biosilico.structures.ExtendedLineageItem;
import gabywald.biosilico.view.GeneJPanel;
import gabywald.global.data.StringUtils;
import gabywald.global.structures.StringCouple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
		this.variables.setVariable(942, 935);
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
	
	public enum OrganismType {
		BIOSILICO_DAEMON(935), 
		BIOSILICO_BACTER(936), 
		BIOSILICO_VIRIDITA(937), 
		BIOSILICO_ANIMA(935), 
		BIOSILICO_VIRIA(935);
		
		private int type;
		
		private OrganismType(int type) {
			this.type = type;
		}
		
		public int getType() { return this.type; }
	}

	public void setOrganismType(OrganismType type) {
		this.variables.setVariable(942, type.getType());
	}
	
	public int getOrganismType() {
		return this.variables.getVariable(942);
	}
	
	public OrganismType getOrganismTypeAsType() {
		int type = this.variables.getVariable(942);
		for (OrganismType otype : OrganismType.values()) {
			if (type == otype.getType()) { return otype; }
		}
		return null;
	}

	public void deplace() {
		if (this.nextStep != null) { this.current = this.nextStep; }
		this.nextStep = null;
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
		IDecision what2do = null;
		switch(which) {
		case(851): what2do = new IDecision() {
			@Override
			public void action(Organism orga, int... variables) {
				orga.nextStep = orga.current;
			}
		};
		what2do.action(this);break;
		case(852): this.decisionToPush(object);break;
		case(853): this.decisionToPull(object);break;
		case(854): this.decisionToStop(object);break;
		case(855): this.decisionToMove(attribute);break;
		case(856): this.decisionToMoveAway();break;
		case(857): this.decisionToGet(object);break;
		case(858): this.decisionToDrop(object);break;
		case(859): this.decisionToThink(object);break;
		case(860): this.decisionToSlap(object);break;
		case(861): this.decisionToRest();break;
		case(862): this.decisionToSleep();break;
		case(863): this.decisionToEat(object, threshold);break;
		case(864): this.decisionToDeath(variable, value);break;
		case(865): this.decisionEmit(object, variable, value);break;
		case(866): this.decisionReceive(object, threshold, variable, value);break;
		case(867): this.decisionHas(object, threshold, attribute, variable, value);break;
		case(868): this.decisionIs(variable, value);break;
		case(869): this.decisionToMakeGamet();break;
		case(870): this.decisionToLayEgg();break;
		case(871): this.decisionToMate();break;
		case(872): 
			// ***** to create eggs, virions, fruits... then have to been "laid" !!
			// this.decisiontoCreateEgg();
		break; 
		case(873): break; /** 873 to 880 are free. */
		case(874): break;
		case(875): break;
		case(876): break;
		case(877): break;
		case(878): break;
		case(879): break;
		case(880): break;
		}
	}

	/**
	 * Decision to push (act1) an agent. 
	 * @param object (int) type of agent
	 */
	private void decisionToPush(int object) {
		Agent tmp = this.current.getAgentType(object);
		tmp.push();this.current.addAgent(tmp);
	}

	/**
	 * Decision to pull (act2) an agent. 
	 * @param object (int) type of agent
	 */
	private void decisionToPull(int object) { 
		Agent tmp = this.current.getAgentType(object);
		tmp.pull();this.current.addAgent(tmp);
	}

	/**
	 * Decision to stop an agent. 
	 * @param object (int) type of agent
	 */
	private void decisionToStop(int object) { 
		Agent tmp = this.current.getAgentType(object);
		tmp.stop();this.current.addAgent(tmp);
	}

	/**
	 * Decision to slap an agent. 
	 * @param object (int) type of agent
	 */
	private void decisionToSlap(int object) { 
		Agent tmp = this.current.getAgentType(object);
		tmp.slap();this.current.addAgent(tmp);
	}

	/**
	 * Indicate a location where to go. 
	 * @param location (int) 800 to 808 (2D). 
	 */
	private void decisionToMove(int location) { 
		this.direction	= location;
		this.nextStep	= this.current.getDirection(location); 
	}

	/** 
	 * Choose randomly a location to go. 
	 * @see Organism#decisionToMove(int)
	 */
	private void decisionToMoveAway() {
		Random rand = new Random();
		if (this.direction == 800) 
			{ this.direction = rand.nextInt(8)+800; }
		else {
			int test = rand.nextInt(34)+1;
			switch(test) {
			case(1):this.direction -= 3;break;
			case(2):
			case(3):this.direction -= 2;break;
			case(4):
			case(5):
			case(6):
			case(7):
			case(8):
			case(9):this.direction--;break;
			case(26):
			case(27):
			case(28):
			case(29):
			case(30):
			case(31):this.direction++;break;
			case(32):
			case(33):this.direction += 2;break;
			case(34):this.direction += 3;break;
			default:this.direction += 0; /** same direction */
			}
			switch(this.direction) {
			case(798):this.direction = 6;break;
			case(799):this.direction = 7;break;
			case(800):this.direction = 8;break;
			case(809):this.direction = 1;break;
			case(810):this.direction = 2;break;
			case(811):this.direction = 3;break;
			default:this.direction = 
					( (this.direction < 800) || (this.direction > 808) )
					?800:this.direction;
			}
		}
		this.decisionToMove(this.direction);
	}

	/**
	 * Getting an object / agent. 
	 * @param object (int) type of agent. 
	 */
	private void decisionToGet(int objectType) { 
		Agent o	= this.current.getAgentType(objectType);
		this.current.remAgent( o );
		this.liste.add( o );
	}

	/**
	 * Getting an object / agent. 
	 * @param object (int) type of agent. 
	 */
	private void decisionToDrop(int object) {
		this.current.addAgent(this.getAgentType(object));
	}

	/**
	 * Change state.
	 * @param object (int) type of agent. 
	 */
	private void decisionToThink(int object) 
		{ this.addState("think about [" + object + "]\t"); }

	private void decisionToRest()	{ ; }
	
	/** XXX which variable for sleeping ? */
	private void decisionToSleep()	{ ; }

	/**
	 * Eating decision (on eatable object: 904). 
	 * @param object (int) default should be 904 (ch Food). 
	 * @param threshold (int) default should be 0.
	 */
	private void decisionToEat(int object, int threshold) { 
		if (this.hasAgentType(object) > threshold) {
			Agent fruit = this.getAgentType(object);
			this.variables.incorporate(fruit.getVariables());
		}
	}

	/**
	 * Death append. (ch941 set to 999, alife to false).
	 * @param variable (int) default should be 941 (ch Aging). 
	 * @param value (int) default should be 999.
	 */
	private void decisionToDeath(int variable, int value) { 
		this.alive = false;
		this.variables.setVariable(variable, value);
	}

	/**
	 * Emission action (pheromone). 
	 * @param object (int) location for emission : ch800 to ch829. 
	 * @param variable (int) One chemical to change in environment. 
	 * @param value (int) New value for chemical. 
	 */
	private void decisionEmit(int object, int variable, int value) { 
		WorldCase tmp = this.current.getDirection(object);
		if (tmp != null) { 
			tmp.getVariables().setVarPlus(variable, value);
			this.getVariables().setVarLess(variable, value);
		}
	}

	/**
	 * Reception action (pheromone). 
	 * @param object (int) location of reception : ch800 to ch829
	 * @param threshold (int) level of reaction. 
	 * @param variable (int) internal chemical to change. 
	 * @param value (int) new value. 
	 */
	private void decisionReceive(int object, int threshold, int variable, int value) {
		WorldCase tmp = this.current.getDirection(object);
		if ( (tmp != null) && (tmp.getVariables().getVariable(variable) > threshold) ) { 
			this.variables.setVarPlus(variable, value);
			tmp.getVariables().setVarLess(variable, value);
		}
	}

	/**
	 * Internal detection of an object and an attribute type. 
	 * @param object (int) type of object. 
	 * @param threshold (int) level of detection. 
	 * @param attribute (int) state of object type. 
	 * @param variable (int) internal chemical to change. 
	 * @param value (int) new value. 
	 */
	private void decisionHas(int object, int threshold, int attribute,
							 int variable, int value) {
		if ( (this.hasAgentType(object) >= threshold)
				&& (this.hasAgentType(attribute) >= threshold) )
			{ this.variables.setVarPlus(variable, value); }
	}

	/**
	 * Action to set new type or state of current agent. 
	 * @param variable (int) internal chemical to change. 
	 * @param value (int) new value.
	 */
	private void decisionIs(int variable, int value) 
		{ this.variables.setVariable(variable, value); }

	private void decisionToMakeGamet() { /** XXX */
		if (this.isFertile()) {
			 // ***** Increase Gamet TODO to change it / values !!
			this.variables.setVarPlus(920, 50);
		}
	}

	private void decisionToLayEgg() { /** XXX */
		// ***** Change status about egg contenant (pregnancy). 
		this.variables.setVariable(933, this.hasAgentType(921));

		if ( (this.isFertile()) && (!this.isPregnant()) ) { ; }

		if (this.isPregnant()) {
			Agent egg = this.getAgentType(921);
			this.current.addAgent(egg);
			this.remAgent(egg);
			// ***** Basic pregnancy signal : number of eggs. 
			this.variables.setVariable(933, this.hasAgentType(921));
		}
	}

	private void decisionToMate() { /** XXX */
		// ***** Gamets presence increases fertility signal. 
		this.variables.setVarPlus(932, this.hasAgentType(920));
		// ***** Eggs presence decreases fertility signal. 
		this.variables.setVarLess(932, this.hasAgentType(921));
		// ***** Basic pregnancy signal : number of eggs. 
		this.variables.setVariable(933, this.hasAgentType(921));
		if (this.isFertile()) {
			
			OrganismType otype = this.getOrganismTypeAsType();
			if (otype == null) { return; }
			switch(otype) {
			case BIOSILICO_DAEMON:
				// ***** nothing definately defined yet (cloning ?!)
				ReproductionDaemon.getInstance().action(this);
				break;
			case BIOSILICO_BACTER:
				// ***** create a duplicate ! (modulo duplication, deletion, mutations of genes... )
				ReproductionBacta.getInstance().action(this);
				break;
			case BIOSILICO_VIRIDITA:
				// ***** find mate (could be itself ? depending of sex ?)
				// ***** find another agent to mate ! (could be itself ?)
			case BIOSILICO_ANIMA:
				// ***** find mate (could be itself ? depending of sex ?)
				// ***** find another agent to mate ! (could be itself ?)
				List<Organism> maters = new ArrayList<Organism>();
				maters.add( this );
				// IntStream.range(0, this.variables.getVariable( 932 ) / 2)
				Agent futuremate = this.current.getAgentType( this.getOrganismType() );
				if (futuremate instanceof Organism) {
					maters.add( (Organism) futuremate );
				}
				if (otype.equals(OrganismType.BIOSILICO_VIRIDITA)) {
					ReproductionViridita.getInstance().action( maters.toArray(new Organism[0]) );
				} else { // OrganismType.BIOSILICO_ANIMA
					ReproductionAnima.getInstance().action( maters.toArray(new Organism[0]) );
				}
				break;
			case BIOSILICO_VIRIA:
				// ***** no reproduction, only inside organism and create virions ?!
				break;
			}

		}
	}

	public void pull() { ; }
	public void push() { ; }
	public void slap() { ; }
	public void stop() { ; }

	public int getAgentListLength()		{ return this.liste.size(); }
	public List<Agent> getAgentListe()	{ return this.liste; }
	public Agent remAgent(int i)		{ return this.liste.remove(i); }
	public boolean remAgent(Agent o)	{ return this.liste.remove(o); }
	public Agent getAgent(int i)		{ return this.liste.get(i); }	

	public int hasAgentType(int type) {
		return (int) this.liste.stream().filter( a -> (a.getChemicals().getVariable(942) == type) ).count();
//		int count  = 0;
//		for (int i = 0 ; i < this.liste.size() ; i++) {
//			if (this.liste.get(i).getChemicals()
//					.getVariable(942) == type) { count++; }
//		}
//		return count;
	}

	public Agent getAgentType(int type) {
		// TODO optimize with Java 8 stream ?!
		if (this.liste.stream().anyMatch( p -> p.getChemicals().getVariable(942) == type )) {
			for (int i = 0 ; i < this.liste.size() ; i++) {
				if (this.liste.get(i).getChemicals().getVariable(942) == type) { 
					return this.liste.get(i);
				}
			}
		}
		return null;
	} 

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
		
		result.append(this.extendedlineage.toString());

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
