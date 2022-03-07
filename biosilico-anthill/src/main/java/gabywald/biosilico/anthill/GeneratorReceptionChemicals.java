package gabywald.biosilico.anthill;

import gabywald.biosilico.interfaces.IChemicalsType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public enum GeneratorReceptionChemicals implements IChemicalsType {
	PHEROMONE_00_CURRENT	(700, "00atLLL", "Pheromone 00 at Current"), 
	PHEROMONE_00_NorthWest	(701, "00atLNW", "Pheromone 00 at Local North-West"), 
	PHEROMONE_00_North		(702, "00atLNN", "Pheromone 00 at Local North"), 
	PHEROMONE_00_NorthEast	(703, "00atLNE", "Pheromone 00 at Local North-East"), 
	PHEROMONE_00_East		(704, "00atLEE", "Pheromone 00 at Local East"), 
	PHEROMONE_00_SouthEast	(705, "00atLSE", "Pheromone 00 at Local South-East"), 
	PHEROMONE_00_South		(706, "00atLSS", "Pheromone 00 at Local South"), 
	PHEROMONE_00_SouthWest	(707, "00atLSW", "Pheromone 00 at Local South-West"), 
	PHEROMONE_00_West		(708, "00atLWW", "Pheromone 00 at Local West"), 
	
	PHEROMONE_01_CURRENT	(730, "01atLLL", "Pheromone 01 at Current"), 
	PHEROMONE_01_NorthWest	(731, "01atLNW", "Pheromone 01 at Local North-West"), 
	PHEROMONE_01_North		(732, "01atLNN", "Pheromone 01 at Local North"), 
	PHEROMONE_01_NorthEast	(733, "01atLNE", "Pheromone 01 at Local North-East"), 
	PHEROMONE_01_East		(734, "01atLEE", "Pheromone 01 at Local East"), 
	PHEROMONE_01_SouthEast	(735, "01atLSE", "Pheromone 01 at Local South-East"), 
	PHEROMONE_01_South		(736, "01atLSS", "Pheromone 01 at Local South"), 
	PHEROMONE_01_SouthWest	(737, "01atLSW", "Pheromone 01 at Local South-West"), 
	PHEROMONE_01_West		(738, "01atLWW", "Pheromone 01 at Local West"), 
	
	FOOD_CURRENT	(760, "FOODatLLL", "FOOD at Current"), 
	FOOD_NorthWest	(761, "FOODatLNW", "FOOD at Local North-West"), 
	FOOD_North		(762, "FOODatLNN", "FOOD at Local North"), 
	FOOD_NorthEast	(763, "FOODatLNE", "FOOD at Local North-East"), 
	FOOD_East		(764, "FOODatLEE", "FOOD at Local East"), 
	FOOD_SouthEast	(765, "FOODatLSE", "FOOD at Local South-East"), 
	FOOD_South		(766, "FOODatLSS", "FOOD at Local South"), 
	FOOD_SouthWest	(767, "FOODatLSW", "FOOD at Local South-West"), 
	FOOD_West		(768, "FOODatLWW", "FOOD at Local West"), 
	;
	
	private int index;
	private String name, definition;

	GeneratorReceptionChemicals(int index, String name, String definition) {
		this.index		= index;
		this.name		= name;
		this.definition	= definition;
	}

	@Override
	public int getIndex()			{ return index; }
	@Override
	public String getName()			{ return name; }
	@Override
	public String getDefinition()	{ return definition; }
	
	public static GeneratorReceptionChemicals getFrom(DirectionWorld dw, SomeChemicals sc) {
		GeneratorReceptionChemicals arcToReturn = null;
		switch( sc ) {
		case PHEROMONE_00: 
			switch( dw ) {
			case CURRENT : 	arcToReturn = GeneratorReceptionChemicals.PHEROMONE_00_CURRENT;break;
			case East : 	arcToReturn = GeneratorReceptionChemicals.PHEROMONE_00_East;break;
			case North : 	arcToReturn = GeneratorReceptionChemicals.PHEROMONE_00_North;break;
			case NorthEast :arcToReturn = GeneratorReceptionChemicals.PHEROMONE_00_NorthEast;break;
			case NorthWest :arcToReturn = GeneratorReceptionChemicals.PHEROMONE_00_NorthWest;break;
			case South : 	arcToReturn = GeneratorReceptionChemicals.PHEROMONE_00_South;break;
			case SouthEast :arcToReturn = GeneratorReceptionChemicals.PHEROMONE_00_SouthEast;break;
			case SouthWest :arcToReturn = GeneratorReceptionChemicals.PHEROMONE_00_SouthWest;break;
			case West : 	arcToReturn = GeneratorReceptionChemicals.PHEROMONE_00_West;break;
			default:
				Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown DW: {" + dw.toString() + "}");
			}
			break;
		case PHEROMONE_01: 
			switch( dw ) {
			case CURRENT : 	arcToReturn = GeneratorReceptionChemicals.PHEROMONE_01_CURRENT;break;
			case East : 	arcToReturn = GeneratorReceptionChemicals.PHEROMONE_01_East;break;
			case North : 	arcToReturn = GeneratorReceptionChemicals.PHEROMONE_01_North;break;
			case NorthEast :arcToReturn = GeneratorReceptionChemicals.PHEROMONE_01_NorthEast;break;
			case NorthWest :arcToReturn = GeneratorReceptionChemicals.PHEROMONE_01_NorthWest;break;
			case South : 	arcToReturn = GeneratorReceptionChemicals.PHEROMONE_01_South;break;
			case SouthEast :arcToReturn = GeneratorReceptionChemicals.PHEROMONE_01_SouthEast;break;
			case SouthWest :arcToReturn = GeneratorReceptionChemicals.PHEROMONE_01_SouthWest;break;
			case West : 	arcToReturn = GeneratorReceptionChemicals.PHEROMONE_01_West;break;
			default:
				Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown DW: {" + dw.toString() + "}");
			}
			break;
		default: 
			Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown SC: {" + sc.toString() + "}");
		}
		return arcToReturn;
	}

public static GeneratorReceptionChemicals getFrom(DirectionWorld dw, ObjectType ot) {
	GeneratorReceptionChemicals arcToReturn = null;
	switch( ot ) {
	case FOOD: 
		switch( dw ) {
		case CURRENT : 	arcToReturn = GeneratorReceptionChemicals.FOOD_CURRENT;break;
		case East : 	arcToReturn = GeneratorReceptionChemicals.FOOD_East;break;
		case North : 	arcToReturn = GeneratorReceptionChemicals.FOOD_North;break;
		case NorthEast :arcToReturn = GeneratorReceptionChemicals.FOOD_NorthEast;break;
		case NorthWest :arcToReturn = GeneratorReceptionChemicals.FOOD_NorthWest;break;
		case South : 	arcToReturn = GeneratorReceptionChemicals.FOOD_South;break;
		case SouthEast :arcToReturn = GeneratorReceptionChemicals.FOOD_SouthEast;break;
		case SouthWest :arcToReturn = GeneratorReceptionChemicals.FOOD_SouthWest;break;
		case West : 	arcToReturn = GeneratorReceptionChemicals.FOOD_West;break;
		default:
			Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown DW: {" + ot.toString() + "}");
		}
		break;
	default: 
		Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown OT: {" + ot.toString() + "}");
	}
	return arcToReturn;
}
	
}
