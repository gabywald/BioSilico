package gabywald.biosilico.anthill;

import gabywald.biosilico.interfaces.IChemicalsType;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Ant extends Organism {
	/** Index in Chemicals which indicates status of Ant. */
	public static final int ANT_STATUS_INDEX = 930;
	
	/**
	 * Status of ant. 
	 * <ul>
	 * <li><b>0-1</b> : Egg / Larva</li>
	 * <li><b>2-6</b> : Worker</li>
	 * <li><b>7</b> : Soldier</li>
	 * <li><b>8</b> : Reproductor</li>
	 * <li><b>9</b> : Queen</li>
	 * </ul>
	 */
	public enum AntStatus implements IChemicalsType {
		EGG_LARVA	("Egg Or Larva", "egg or larva", 	0, 1), 
		WORKER		("Worker", "worker", 				2, 3, 4, 5, 6), 
		SOLDIER		("Soldier", "soldier", 				7), 
		REPRODUCTOR	("Reproductor", "reproductor", 		8), 
		QUEEN		("Queen", "queen", 					9);
		
		private int[] indexes;
		private String name, definition;
		
		private AntStatus(String name, String definition, int... values) {
			this.indexes	= values;
			this.name		= name;
			this.definition	= definition;
		}

		/**
		 * Computation to "translate" from Agent's status. 
		 * @param status
		 * @param states {gender, aging, fertile, pregnant}
		 * @return
		 */
		public static AntStatus compute(StatusType status, int[] states) {
			// TODO computation to "translate" from Agent's status
			return null;
		}

		@Override
		public int getIndex()			{ return this.indexes[0]; }
		@Override
		public String getName()			{ return this.name; }
		@Override
		public String getDefinition()	{ return this.definition; }
		
		public static AntStatus getFrom(int index) {
			for (AntStatus at : AntStatus.values()) {
				for (int atindex : at.indexes) {
					if (index == atindex) 
						{ return at; }
				}
			}
			return null;
		}
	}

	/** Default constructor of an Ant agent (alive). */
	public Ant() { 
		super();
		this.setAlive( true );
		this.setEatable( false );
		this.setMovable( false );
		
		this.setNameCommon("Ant");
		this.setObjectType(ObjectType.AGENT);
		this.setAgentType(AgentType.BIOSILICO_ANIMA);
		this.setOrganismStatus(StatusType.EGG);
		
	}
	
	public void setAntStatus(AntStatus type) {
		this.variables.setVariable(Ant.ANT_STATUS_INDEX, type.getIndex());
	}
	
	public AntStatus getAntStatus() {
		int type = this.variables.getVariable(Ant.ANT_STATUS_INDEX);
		return AntStatus.getFrom( type );
	}
	
}
