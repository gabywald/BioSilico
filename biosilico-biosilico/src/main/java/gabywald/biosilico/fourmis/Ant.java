package gabywald.biosilico.fourmis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Ant extends Agent implements AgentContent {
	/** What the Ant has in its bag... */
	private List<Agent> liste;
	/**
	 * Status of ant. 
	 * <p></p>
	 * <ul>
	 * <li><b>0-1</b> : Egg / Larva</li>
	 * <li><b>2-7</b> : Worker</li>
	 * <li><b>8</b> : Soldier</li>
	 * <li><b>9</b> : Queen</li>
	 * </ul>
	 */
	private int status;
	/** 
	 * In which direction Ant will move (if move). 
	 * <p></p>
	 * <ul>
	 * <li><b>0</b> : stay in place</li>
	 * <li><b>1</b> : North-West</li>
	 * <li><b>2</b> : North</li>
	 * <li><b>3</b> : North-East</li>
	 * <li><b>4</b> : East</li>
	 * <li><b>5</b> : South-East</li>
	 * <li><b>6</b> : South</li>
	 * <li><b>7</b> : South-West</li>
	 * <li><b>8</b> : West</li>
	 * </ul> 
	 */
	private int direction;
	/**
	 * Ant detects pheromones. 
	 */
	private int[] detection;

	/** Default constructor of an Ant agent (alive). */
	public Ant() { 
		super(true,false,false);
		/** TODO genetic initial concentration. */
		for (int i = 8 ; i < 11 ; i++) 
			{ this.variables.setVariable(i, 999); }
		this.liste = new ArrayList<Agent>();
		this.status = 0; /** start with an egg. */
		this.direction = 0;
	}
	
	public void execution(WorldCase local) {
		this.biochemistery();
		this.emitterReceptor(local);
		this.stimulus(local);
		if ( (this.status > 1) && (this.status < 8) ) 
			{ this.move(local); } /** move following pheromone's */
		if (this.status == 8) { this.move(local); } /** "conquest" */
		if (this.status == 9) { ; } /** "reproduction" */
		
	}
	
	public void deplace(WorldCase local) {
		switch(this.direction) {
		case(1):local.getNW().addAgent(this);break;
		case(2):local.getNN().addAgent(this);break;
		case(3):local.getNE().addAgent(this);break;
		case(4):local.getEE().addAgent(this);break;
		case(5):local.getSE().addAgent(this);break;
		case(6):local.getSS().addAgent(this);break;
		case(7):local.getSW().addAgent(this);break;
		case(8):local.getWW().addAgent(this);break;
		}
		local.getAgentListe().remove(this);
	}
	
	protected void biochemistery() {
		this.BioCHGene( 20, 1, 21, 1, 22, 1, 23, 1, 5);
		this.BioCHGene( 22, 1, 23, 1, 24, 1, 25, 1, 5);
		this.BioCHGene( 24, 1, 25, 1, 26, 1, 27, 1, 5);
		this.BioCHGene( 26, 1, 27, 1, 28, 1, 29, 1, 5);
		this.BioCHGene( 28, 1, 29, 1, 30, 1, 31, 1, 5);
		this.BioCHGene( 30, 1, 31, 1, 32, 1, 33, 1, 5);
		this.BioCHGene( 32, 1, 33, 1, 34, 1, 35, 1, 5);
		this.BioCHGene( 34, 1, 35, 1, 36, 1, 37, 1, 5);
		
		this.BioCHGene( 36, 1, 37, 1,  0, 1,  1, 1, 5);
		this.BioCHGene(  0, 1,  1, 1,  2, 1,  3, 1, 5);
		this.BioCHGene(  2, 1,  3, 1,  4, 1,  5, 1, 5);
		this.BioCHGene(  4, 1,  5, 1,  6, 1,  7, 1, 5);
		this.BioCHGene(  6, 1,  7, 1,  8, 1,  9, 1, 5);
		this.BioCHGene(  8, 1,  9, 1, 10, 1, 11, 1, 5);
	}

	protected void stimulus(WorldCase local) {
		this.getFruit(local);
		this.eatFruit();
		this.dropFruits(local);
	}
	
	protected void emitterReceptor(WorldCase local) {
		/** internal receptor's ?? */
		if (this.isHungry()) 
			{ this.variables.setVarPlus(100, 10); } 
		else { this.variables.setVarLess(100, 10); }
		/** external receptor's */
		this.importUse(local);
		/** external emitter's */
		this.exportUse(local);
	}
	
	/**
	 * A stimulus / decision action to get some movable and eatable Agent (Fruit).
	 * @param local (WorldCase)
	 * TODO genetic decision (get fruit). 
	 */
	private void getFruit(WorldCase local) {
		if ( (local.hasFruit()) && (this.detection[0] == 0) ) {
			int i = 0;
			while (i < local.getAgentListLength()) {
				Agent fruit = local.getAgent(i);
				if ( (fruit.isMovable()) && (fruit.isEatable()) ) { 
					this.addAgent(fruit);
					local.remAgent(i);
				} else { i++; }
			}
		}
	}
	
	/** 
	 * A stimulus / decision action to drop all movable Agents. 
	 * @param local (WorldCase)
	 * TODO genetic stimulus (drop fruit).
	 */
	private void dropFruits(WorldCase local) {
		if ( (this.detection[0] > 0) || (this.status == 8) ) {
			int i = 0;
			while (i < this.getAgentListLength()) {
				Agent fruit = this.getAgent(i);
				if (fruit.isMovable()) { 
					local.addAgent(fruit);
					this.remAgent(i);
				} else { i++; }
			}
		}
	}
	
	/** 
	 * A stimulus / decision action to eat a fruit (carrying). 
	 * TODO genetic stimulus (use / eat fruit)
	 */
	private void eatFruit() {
		if (this.hasFruit() && this.isHungry()) {
			int i = 0;
			Agent fruit = this.getAgent(i);
			i++;
			while ( (i < this.getAgentListLength())
					&& (!fruit.isEatable()) ){
				fruit = this.getAgent(i);i++;
			}
			this.remAgent(i-1); /** remove fruit */
			/** Integrating fruits variables in Fourmi variables */
			this.variables.incorporate(fruit.getVariables());
			/** deleting fruit here */
		}
	}
	
	/**
	 * Detection o about carrying some eatable Agent. 
	 * @return (boolean)
	 */
	private boolean hasFruit() {
		for (int i = 0 ; i < this.liste.size() ; i++) {
			if (this.isAgentEatable(i))
				{ return true; }
		}
		return false;
	}
	
	/**
	 * Hunger detection : emitter's / receptor's ?
	 * @return (boolean)
	 */
	private boolean isHungry() {
		if ( (this.variables.getVariable(20) < 10) 
				&& (this.variables.getVariable(21) < 10) ) {
			if ( (this.variables.getVariable(22) < 10) 
					&& (this.variables.getVariable(23) < 10) ) 
				{ return true; }
		}
		return false;
	}
	


	/**
	 * External emitter.  
	 * @param local
	 * TODO genetic emitter / receptor upon age ?? see bioCH "magic" and aging...
	 */
	private void exportUse(WorldCase local) {
		/** pheromone deposit */ 
		switch(this.status) {
		case(2): /** when worker : trace going and back */ 
		case(3):
		case(4):
		case(5):
		case(6):
		case(7):
			if (this.hasFruit()) { local.addToVariable(50, 10); }
			else { local.addToVariable(55, 10); }
		break;
		case(0): /** egg, larva and queen : nest */
		case(1):
		case(9):
			local.addToVariable(60, 10);
		break;
		}
	}
	
	/**
	 * External receptor : detection of pheromones (3: 50, 55, 60). 
	 * @param local (WorldCase)
	 * TODO connection to Brain instead of detection ?
	 */
	private void importUse(WorldCase local) {
		this.detection = new int[9];
		int pheroToGet = 50;
		int pheroToBac = 55;
		int pheroColon = 60;
		int pheroToDetect = (this.hasFruit())?pheroToBac:pheroToGet;
		this.detection[0] = local.getVariable(pheroColon);
		this.detection[1] = local.getNW().getVariable(pheroToDetect);
		this.detection[2] = local.getNN().getVariable(pheroToDetect);
		this.detection[3] = local.getNE().getVariable(pheroToDetect);
		this.detection[4] = local.getEE().getVariable(pheroToDetect);
		this.detection[5] = local.getSE().getVariable(pheroToDetect);
		this.detection[6] = local.getSS().getVariable(pheroToDetect);
		this.detection[7] = local.getSW().getVariable(pheroToDetect);
		this.detection[8] = local.getWW().getVariable(pheroToDetect);
	}
	
	/** 
	 * The specific way to move for ants : randomly or pheromone detection. 
	 * @param local (WorldCase)
	 */
	private void move(WorldCase local) {
		switch(this.status) {
		case(0):
		case(1):this.direction = 0;break;
		case(2):
		case(3):
		case(4):
		case(5):
		case(6):
		case(7):this.chooseDirectionPheromone(local);break;
		case(8):this.chooseDirectionRandom();break;
		case(9):this.direction = 0;break;
		default:this.direction = 0;
		}
	}
	
	private void chooseDirectionRandom() {
		Random rand = new Random();
		if (this.direction == 0) 
			{ this.direction = rand.nextInt(8)+1; }
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
			case(-2):this.direction = 6;break;
			case(-1):this.direction = 7;break;
			case(0):this.direction = 8;break;
			case(9):this.direction = 1;break;
			case(10):this.direction = 2;break;
			case(11):this.direction = 3;break;
			default:this.direction = 
				( (this.direction < 1) || (this.direction > 8) )
					?0:this.direction;
			}
		}
	}
	
	private void chooseDirectionPheromone(WorldCase local) {
		/** Take first higher direction. */
		int max = 0; 
		for (int i = 0 ; i < this.detection.length ; i++) 
			{ if (this.detection[i] > this.detection[max]) { max = i; } }
		this.direction = max;
		/** In case of no decision. */ 
		if (this.direction == 0) { this.chooseDirectionRandom(); }
	}
	
	public int getDirection() { return this.direction; }
	
	public int getAgentListLength()			{ return this.liste.size(); }
	public List<Agent> getAgentListe()		{ return this.liste; }
	public void addAgent(Agent elt)			{ this.liste.add(elt); }
	public void remAgent(int i)				{ this.liste.remove(i); }
	public Agent getAgent(int i)			{ return this.liste.get(i); }
	
	public boolean isAgentAlive(int i) 		{ return this.liste.get(i).isAlive(); }
	public boolean isAgentMovable(int i) 	{ return this.liste.get(i).isMovable(); }
	public boolean isAgentEatable(int i)	{ return this.liste.get(i).isEatable(); }
	
}
