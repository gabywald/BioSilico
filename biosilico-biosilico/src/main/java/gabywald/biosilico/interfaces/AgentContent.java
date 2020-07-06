package gabywald.biosilico.interfaces;

import java.util.List;

import gabywald.biosilico.model.Agent;

/**
 * This interface defines use of a list of Agent's in one instance of Agent. 
 * @author Gabriel Chandesris (2009-2010)
 */
public interface AgentContent {
	/** Length of the list. */
	public int getAgentListLength();
	/** To get the list of Agent's. */
	public List<Agent> getAgentListe();
	/** To remove an Agent in current instance of. 
	 * @return */
	public Agent remAgent(int i);
	/** To get an Agent in current instance of. */
	public Agent getAgent(int i);
	
	/**
	 * Determine if current WorldCase owns a certain type of object. 
	 * @param type (int) 901 to 939
	 * @return (int) Number of object's of that type. 
	 */
	public int hasAgentType(int type);
	
	/**
	 * In order to get an agent of a certain type. 
	 * @param type (int) 
	 * @return (Agent) Can be null.
	 * @see AgentContent#hasAgentType(int)
	 */
	public Agent getAgentType(int type);
	
	/**
	 * In order to put an object in (nothing if object is null).  
	 * @param object (Agent)
	 */
	public void addAgent(Agent object);
	
	/**
	 * Determine if current WorldCase owns a certain type of object. 
	 * @param type (int) 901 to 939
	 * @return (int) Number of object's of that type.
	 * @see AgentContent#hasAgentType(int) 
	 * @deprecated Replaced by better function. 
	 */
	/*public int hasAgentOfType(int type) {
		String searchedType = "Agent";
		switch(type) {
		case(901):searchedType = "SmallElt";break;
		case(902):searchedType = "MiddElt";break;
		case(903):searchedType = "BigElt";break;
		case(904):searchedType = "Food";break;
		case(905):searchedType = "Drink";break;
		case(906):searchedType = "Vehicle";break;
		case(907):searchedType = "Automaton";break;
		case(908):searchedType = "Computer";break;
		case(909):searchedType = "Laptop";break;
		case(910):searchedType = "Phone";break;
		case(911):searchedType = "CellPhone";break;
		case(912):searchedType = "Ant";break;
		case(913): *//** these are free and return 0. *//*
		case(914):
		case(915):
		case(916):
		case(917):
		case(918):
		case(919):return 0;
		case(920):searchedType = "Gamet";break;
		case(921):searchedType = "Egg";break;
		case(922):searchedType = "Embryo";break;
		case(923):searchedType = "Larva";break;
		case(924):searchedType = "Child";break;
		case(925):searchedType = "Teen";break;
		case(926):searchedType = "Adult";break;
		case(927):searchedType = "Senior";break;
		case(928):searchedType = "Dead";break;
		case(929):return 0;
		case(930):searchedType = "Movable";break;
		case(931):searchedType = "Eatable";break;
		case(932):searchedType = "Fertile";break;
		case(933):searchedType = "Pregnant";break;
		case(934):return 0;
		case(935):searchedType = "Daemon";break;
		case(936):searchedType = "Bacta";break;
		case(937):searchedType = "Plant";break;
		case(938):searchedType = "Virus";break;
		case(939):searchedType = "Anima";break;
		default:return 0;
		}
		int count = 0;
		if ( (type >= 930) || (type <= 933) ) {
			*//** if eatable or movable agents or fertile or pregnant *//*
			for (int i = 0 ; i < this.liste.length() ; i++) {
				if (this.liste.getElement(i)
						.getChemicals().getVariable(type) > 0) 
					{ count ++; }
				*//** 
				if ( (type == 930) 
						&& (this.liste.getElement(i).isEatable()) )
					{ count++; }
				if ( (type == 931) 
						&& (this.liste.getElement(i).isMovable()) )
					{ count++; }
				if ( (type == 932) 
						&& (this.liste.getElement(i).isPregnant()) )
					{ count++; }
				if ( (type == 933) 
						&& (this.liste.getElement(i).isFertile()) )
					{ count++; } *//*
			}
			return count;
		} else {
			if ( (type >= 920) && (type <= 928) ) {
				*//** aging detection, "default values" *//*
				for (int i = 0 ; i < this.liste.length() ; i++) {
					int aging_i = this.liste.getElement(i).getCycle();
					if ( (type == 920) && (aging_i == 0) )*//** Gamet *//*
						{ count++; } 
					if ( (type == 921) *//** Egg *//*
							&& ( (aging_i >= 0) && (aging_i <= 100) ))
						{ count++; } 
					if ( (type == 922) *//** Embryo *//*
							&& ( (aging_i >= 100) && (aging_i <= 200) ))
						{ count++; } 
					if ( (type == 923) *//** Larva *//*
							&& ( (aging_i >= 200) && (aging_i <= 300) ))
						{ count++; } 
					if ( (type == 924) *//** Child *//*
							&& ( (aging_i >= 300) && (aging_i <= 400) ))
						{ count++; } 
					if ( (type == 925) *//** Teen *//*
							&& ( (aging_i >= 400) && (aging_i <= 500) ))
						{ count++; } 
					if ( (type == 926) *//** Adult *//*
							&& ( (aging_i >= 500) && (aging_i <= 800) ))
						{ count++; } 
					if ( (type == 927) *//** Senior *//*
							&& ( (aging_i >= 800) && (aging_i <= 998) ))
						{ count++; } 
					if ( (type == 928) *//** Dead *//*
							&& (aging_i == 999) )
						{ count++; } 
				}
			} else {
				*//** default standard detection *//*
				for (int i = 0 ; i < this.liste.length() ; i++) {
					if ( this.liste.getElement(i).getType()
							.equals(searchedType) )
						{ count++; }
				}
			}
			return count;
		}
	}*/
}
