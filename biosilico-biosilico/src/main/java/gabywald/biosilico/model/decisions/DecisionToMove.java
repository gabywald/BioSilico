package gabywald.biosilico.model.decisions;

import java.util.Random;

import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * Indicate a location where to go. 
 * @author Gabriel Chandesris (2020)
 */
public class DecisionToMove extends BaseDecisionOnlyOneAttribute {

	DecisionToMove(Organism orga, int locationDestination) {
		super(orga, locationDestination);
	}

	@Override
	public void action() {
		this.getOrga().setDirection( DirectionWorld.get2DFrom( this.getVariable(0) ) );
		this.getOrga().setNextWorldCase(this.getOrga().getCurrentWorldCase().getDirection( this.getVariable(0) ));
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "{" + this.getOrga().getUniqueID() + "} MOVE TO {" + DirectionWorld.get2DFrom( this.getVariable(0) ) + "}");
	}
	
	public static DirectionWorld getRandomDirection2D(DirectionWorld initValue) {
		return DecisionToMove.getRandomDirection(initValue, true);
	}
	
	public static DirectionWorld getRandomDirection3D(DirectionWorld initValue) {
		return DecisionToMove.getRandomDirection(initValue, false);
	}
	
	/**
	 * Choose randomly a direction
	 * @param initValue Starting direction value. 
	 * @param not3D If 2D or 3D. 
	 * @return A direction. 
	 */
	public static DirectionWorld getRandomDirection(DirectionWorld initValue, boolean not3D) {
		Random rand		= new Random();
		int value		= ( rand.nextInt( not3D ? 9 : 27) );
		
		// TODO DW base indexLessRemove
		if ( not3D ) {
			return DirectionWorld.get2DFrom(800 + value);
		} else {
			return DirectionWorld.get3DFrom(800 + value);
		}
	}

}
