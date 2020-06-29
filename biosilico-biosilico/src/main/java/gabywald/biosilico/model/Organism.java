package gabywald.biosilico.model;

import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.interfaces.AgentContent;
import gabywald.biosilico.structures.ExtendedLineage;
import gabywald.biosilico.view.GeneJPanel;
import gabywald.global.data.StringUtils;
import gabywald.global.structures.StringCouple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
	private ExtendedLineage extendedlineage;
	/** Set of Genetic code use. */
	private List<StringCouple> geneticCodes;

	/** Default Constructor with empty Genome. */
	public Organism() {
		super(true, true, true);
		this.genome	= new ArrayList<Chromosome>();
		this.init();
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

	/**
	 * Constructors' helper (initialisation). 
	 * XXX default organism's are Daemons (ch942 set to 935)
	 */
	private void init() {
		this.variables.setVariable(942, 935);
		this.geneticCodes	= new ArrayList<StringCouple>();
		this.currentBrain	= null;
		this.liste			= new ArrayList<Agent>();
		this.geneticCodes.add(new StringCouple("0000000010", "Gattaca01"));
		this.extendedlineage = new ExtendedLineage();
	}

	public Brain getBrain() 				{ return this.currentBrain; }
	public List<Chromosome> getGenome() 	{ return this.genome; }

	public void setGenome(List<Chromosome> genome) 
	{ this.genome = genome; }

	public int lengthLineage()		{ return this.extendedlineage.length(); }

	public void setExtendedLineage(ExtendedLineage lineage) 
	{ this.extendedlineage = lineage; }

	/**
	 * Create an instance of ExtendedLineageItem and add it to the end of the list. 
	 * @param uniqueID (String)
	 * @param scientificName (String)
	 * @param rank (String)
	 */
	public void addExtendedLineageItem(String uniqueID,
			String scientificName,
			String rank) 
	{ this.extendedlineage.addExtendedLineageItem(uniqueID, scientificName, rank); }

	public List<String> getSimpleLinage() 
	{ return this.extendedlineage.getSimpleLineage(); }

	public String getSimpleLineage(int i) 
	{ return this.extendedlineage.getSimpleLineage(i); }

	public List<StringCouple> getGeneticCodes()
	{ return this.geneticCodes; }

	/**
	 * Set a Brain for Organism (specific gene). instanciated by a specific Gene
	 * @param cc (Brain)
	 * @see BrainGene#BrainGene(boolean, boolean, boolean, boolean, int, int, int, int, int, int, int, int)
	 * @see BrainGene#exec(Organism)
	 */
	public void setBrain(Brain cc) { this.currentBrain = cc; }

	public void setOrganismType(int type) {
		switch(type) {
		case(1):this.variables.setVariable(942, 935);break; /** SilicoDaemon */
		case(2):this.variables.setVariable(942, 936);break; /** SilicoBacter */
		case(3):this.variables.setVariable(942, 937);break; /** SilicoViridita */
		case(4):this.variables.setVariable(942, 938);break; /** SilicoAnima */
		case(5):this.variables.setVariable(942, 939);break; /** SilicoViria */
		}
	}

	public void deplace() {
		if (this.nextStep != null) { this.current = this.nextStep; }
		this.nextStep = null;
	}

	public void execution(WorldCase local) {
		this.current = local;
		/** Genome is "executed". */
		this.genome.stream().forEach( c -> c.execution(this) );
		/** TODO genome length to 0 : death ?! */
		/** Running the brain (if not null). */
		if (this.currentBrain != null) 
		{ this.currentBrain.networking(); }
	}

	/**
	 * Ativity of organism (Decision)
	 * @param which (int) code of script / action. 
	 * @param object (int) code object. 
	 * @param threshold (int)
	 * @param attribute (int)
	 * @param variable (int)
	 * @param value (int)
	 */
	public void activity(int which,int object, int threshold, 
			int attribute, int variable, int value) {
		switch(which) {
		case(851): this.decisionToStay();break;
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
		case(863): this.decisionToEat(object,threshold);break;
		case(864): this.decisionToDeath(variable,value);break;
		case(865): this.decisionEmit(object,variable,value);break;
		case(866): this.decisionReceive(object,threshold,variable,value);break;
		case(867): this.decisionHas(object,threshold,attribute,variable,value);break;
		case(868): this.decisionIs(variable,value);break;
		case(869): this.decisionToMakeGamet();break;
		case(870): this.decisionToLayEgg();break;
		case(871): this.decisionToMate();break;
		case(872): break; /** 871 to 880 are free. */
		case(873): break;
		case(874): break;
		case(875): break;
		case(876): break;
		case(877): break;
		case(878): break;
		case(879): break;
		case(880): break;
		}
	}

	private void decisionToStay() { this.nextStep = this.current; }

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
		this.direction = location;
		this.nextStep = this.current.getDirection(location); 
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
	private void decisionToGet(int object) 
		{ this.liste.add(this.current.getAgentType(object)); }

	/**
	 * Getting an object / agent. 
	 * @param object (int) type of agent. 
	 */
	private void decisionToDrop(int object) 
		{ this.current.addAgent(this.getAgentType(object)); }

	/**
	 * Change state.
	 * @param object (int) type of agent. 
	 */
	private void decisionToThink(int object) 
		{ this.addState("think about " + object + "\t"); }

	private void decisionToRest() { ; }
	
	/** XXX which variable for sleeping ? */
	private void decisionToSleep() { ; }

	/**
	 * Eating decision (on eatable object: 904). 
	 * @param object (int) default should be 904 (ch Food). 
	 * @param threshold (int) default should be 0.
	 */
	private void decisionToEat(int object,int threshold) { 
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
	private void decisionToDeath(int variable,int value) { 
		this.alive = false;
		this.variables.setVariable(variable, value);
	}

	/**
	 * Emission action (pheromone). 
	 * @param object (int) location for emission : 800 to 829. 
	 * @param variable (int) One chemical to change in environment. 
	 * @param value (int) New value for chemical. 
	 */
	private void decisionEmit(int object,int variable,int value) { 
		WorldCase tmp = this.current.getDirection(object);
		if (tmp != null) { 
			tmp.getVariables().setVarPlus(variable, value);
			this.getVariables().setVarLess(variable, value);
		}
	}

	/**
	 * Reception action (pheromone). 
	 * @param object (int) location of reception : 800 to 829
	 * @param threshold (int) level of reaction. 
	 * @param variable (int) internal chemical to change. 
	 * @param value (int) new value. 
	 */
	private void decisionReceive(int object,int threshold,
			int variable,int value) {
		WorldCase tmp = this.current.getDirection(object);
		if ( (tmp != null) 
				&& (tmp.getVariables().getVariable(variable) > threshold) ) { 
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
	private void decisionHas(int object,int threshold,int attribute,
			int variable,int value) {
		if ( (this.hasAgentType(object) >= threshold)
				&& (this.hasAgentType(attribute) >= threshold) )
		{ this.variables.setVarPlus(variable, value); }
	}

	/**
	 * Action to set new type or state of current agent. 
	 * @param variable (int) internal chemical to change. 
	 * @param value (int) new value.
	 */
	private void decisionIs(int variable,int value) 
	{ this.variables.setVariable(variable, value); }

	private void decisionToMakeGamet() { /** XXX */
		if (this.isFertile()) {

		}
	}

	private void decisionToLayEgg() { /** XXX */
		/** Change status about egg contenant (pregnancy). */
		this.variables.setVariable(946, this.hasAgentType(921));

		if ( (this.isFertile()) && (!this.isPregnant()) ) { ; }

		if (this.isPregnant()) {
			Agent egg = this.getAgentType(921);
			this.current.addAgent(egg);
			/** Basic pregnancy signal : number of eggs. */
			this.variables.setVariable(946, this.hasAgentType(921));
		}
	}

	private void decisionToMate() { /** XXX */
		/** Gamets presence increases fertility signal. */
		this.variables.setVarPlus(945, this.hasAgentType(920));
		/** Eggs presence decreases fertility signal. */
		this.variables.setVarLess(945, this.hasAgentType(921));
		/** Basic pregnancy signal : number of eggs. */
		this.variables.setVariable(946, this.hasAgentType(921));
		if (this.isFertile()) {

		}
	}

	public void pull() { ; }
	public void push() { ; }
	public void slap() { ; }
	public void stop() { ; }

	public int getAgentListLength()		{ return this.liste.size(); }
	public List<Agent> getAgentListe()	{ return this.liste; }
	public Agent remAgent(int i)			{ return this.liste.remove(i); }
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
		for (int i = 0 ; i < this.liste.size() ; i++) {
			if (this.liste.get(i).getChemicals().getVariable(942) == type) { 
				return this.liste.remove(i);
			}
		}
		return null;
	} 

	public void addAgent(Agent object) { 
		if (object != null) { this.liste.add(object); } 
	}

	public String toString() {
		// TODO Strong to StringBuilder
		// TODO use of Java 8 streams
		String result = new String();
		/** Get String definition of SuperClass. */
		result += super.toString();
		/** Current instance. */
		result += "GENETIC CODE\n\tID\t"
				+this.geneticCodes.get(0).getValueA()+"\n";
		result += "\tNAME\t"
				+this.geneticCodes.get(0).getValueB()+"\n";
		for (int i = 1 ; i < this.geneticCodes.size() ; i++) {
			result += "SUB GENETIC CODE\n\tID\t"
					+this.geneticCodes.get(i).getValueA()+"\n";
			result += "\tNAME\t"
					+this.geneticCodes.get(i).getValueB()+"\n";
		}
		result += "LINEAGE\n";
		if (this.extendedlineage.length() == 0) { result += "\tNO DATA\n"; }
		for (int i = 0 ; i < this.extendedlineage.length() ; i++) { 
			result += "\t"+this.extendedlineage.getSimpleLineage(i)
			+" ("+this.extendedlineage.getUniqueID(i)+")\n";
		}
		result += this.extendedlineage.toString();

		result += "GENOME\n";
		for (int i = 0 ; i < this.genome.size() ; i++) {
			Chromosome chr = this.genome.get(i);
			for (int j = 0 ; j < chr.length() ; j++) { 
				result += "\t"+chr.getGene(j).getName()
						+"\t"+chr.getGene(j).reverseTranslation(true)+"\n";
			}
			if (i < this.genome.size()-1) { 
				String delim = StringUtils.repeat("-", 50);
				result += "\t"+delim+"\t"+delim+"\n"; 
			}
		}

		if (this.currentBrain != null) { 
			result += "BRAIN HEIGHT\t"+GeneJPanel.convertTwoChars
					(this.currentBrain.getHeight())+"\n";
			result += "BRAIN WIDTH\t"+GeneJPanel.convertTwoChars
					(this.currentBrain.getWidth())+"\n";
			result += "BRAIN DEPTH\t"+GeneJPanel.convertTwoChars
					(this.currentBrain.getDepth())+"\n";
			result += "NEURONS LIST DESCRIPTION\n\tNO DATA\n";
			/** TODO enregistrer réseaux de neurones / lobes... */
		} else { result += "NO DATA ABOUT BRAIN, LOBES AND NEURONS\n"; }

		return result;
	}

}
