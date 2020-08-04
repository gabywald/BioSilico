package gabywald.biosilico.model.decisions;

import java.util.Random;

import gabywald.biosilico.model.Organism;

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
		this.getOrga().setDirection( this.getVariable(0) );
		this.getOrga().setNextWorldCase(this.getOrga().getCurrentWorldCase().getDirection( this.getVariable(0) )); 
	}
	
	public static int getRandomDirection(int initValue) {
		Random rand		= new Random();
		int direction	= initValue;
		if (direction == 800) 
			{ direction = rand.nextInt(8)+800; }
		else {
			int test = rand.nextInt(34)+1;
			switch(test) {
			case(1):direction -= 3;break;
			case(2):
			case(3):direction -= 2;break;
			case(4):
			case(5):
			case(6):
			case(7):
			case(8):
			case(9):direction--;break;
			case(26):
			case(27):
			case(28):
			case(29):
			case(30):
			case(31):direction++;break;
			case(32):
			case(33):direction += 2;break;
			case(34):direction += 3;break;
			default:direction += 0; /** same direction */
			}
			switch(direction) {
			case(798):direction = 806;break;
			case(799):direction = 807;break;
			case(800):direction = 808;break;
			case(809):direction = 801;break;
			case(810):direction = 802;break;
			case(811):direction = 803;break;
			default:direction = 
					( (direction < 800) || (direction > 808) ) ? 800 : direction;
			}
		}
		return direction;
	}

}
