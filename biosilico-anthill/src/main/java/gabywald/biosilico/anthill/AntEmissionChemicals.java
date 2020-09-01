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
 * @deprecated Use DirectionWorld and other similar enums for "internal chemicals transmission of data". 
 */
public enum AntEmissionChemicals implements IChemicalsType {
	PHEROMONE_00_CURRENT	(600, "00atLLL", "Pheromone 00 at Current"), 
	PHEROMONE_00_NorthWest	(601, "00atLNW", "Pheromone 00 at Local North-West"), 
	PHEROMONE_00_North		(602, "00atLNN", "Pheromone 00 at Local North"), 
	PHEROMONE_00_NorthEast	(603, "00atLNE", "Pheromone 00 at Local North-East"), 
	PHEROMONE_00_East		(604, "00atLEE", "Pheromone 00 at Local East"), 
	PHEROMONE_00_SouthEast	(605, "00atLSE", "Pheromone 00 at Local South-East"), 
	PHEROMONE_00_South		(606, "00atLSS", "Pheromone 00 at Local South"), 
	PHEROMONE_00_SouthWest	(607, "00atLSW", "Pheromone 00 at Local South-West"), 
	PHEROMONE_00_West		(608, "00atLWW", "Pheromone 00 at Local West"), 
	
	PHEROMONE_01_CURRENT	(630, "01atLLL", "Pheromone 01 at Current"), 
	PHEROMONE_01_NorthWest	(631, "01atLNW", "Pheromone 01 at Local North-West"), 
	PHEROMONE_01_North		(632, "01atLNN", "Pheromone 01 at Local North"), 
	PHEROMONE_01_NorthEast	(633, "01atLNE", "Pheromone 01 at Local North-East"), 
	PHEROMONE_01_East		(634, "01atLEE", "Pheromone 01 at Local East"), 
	PHEROMONE_01_SouthEast	(635, "01atLSE", "Pheromone 01 at Local South-East"), 
	PHEROMONE_01_South		(636, "01atLSS", "Pheromone 01 at Local South"), 
	PHEROMONE_01_SouthWest	(637, "01atLSW", "Pheromone 01 at Local South-West"), 
	PHEROMONE_01_West		(638, "01atLWW", "Pheromone 01 at Local West"), 

	FOOD_CURRENT	(660, "FOODatLLL", "FOOD at Current"), 
	FOOD_NorthWest	(661, "FOODatLNW", "FOOD at Local North-West"), 
	FOOD_North		(662, "FOODatLNN", "FOOD at Local North"), 
	FOOD_NorthEast	(663, "FOODatLNE", "FOOD at Local North-East"), 
	FOOD_East		(664, "FOODatLEE", "FOOD at Local East"), 
	FOOD_SouthEast	(665, "FOODatLSE", "FOOD at Local South-East"), 
	FOOD_South		(666, "FOODatLSS", "FOOD at Local South"), 
	FOOD_SouthWest	(667, "FOODatLSW", "FOOD at Local South-West"), 
	FOOD_West		(668, "FOODatLWW", "FOOD at Local West"), 
	;
	
	private int index;
	private String name, definition;

	AntEmissionChemicals(int index, String name, String definition) {
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
	
	public static AntEmissionChemicals getFrom(DirectionWorld dw, SomeChemicals sc) {
		AntEmissionChemicals aecToReturn = null;
		switch( sc ) {
		case PHEROMONE_00: 
			switch( dw ) {
			case CURRENT : 	aecToReturn = AntEmissionChemicals.PHEROMONE_00_CURRENT;break;
			case East : 	aecToReturn = AntEmissionChemicals.PHEROMONE_00_East;break;
			case North : 	aecToReturn = AntEmissionChemicals.PHEROMONE_00_North;break;
			case NorthEast :aecToReturn = AntEmissionChemicals.PHEROMONE_00_NorthEast;break;
			case NorthWest :aecToReturn = AntEmissionChemicals.PHEROMONE_00_NorthWest;break;
			case South : 	aecToReturn = AntEmissionChemicals.PHEROMONE_00_South;break;
			case SouthEast :aecToReturn = AntEmissionChemicals.PHEROMONE_00_SouthEast;break;
			case SouthWest :aecToReturn = AntEmissionChemicals.PHEROMONE_00_SouthWest;break;
			case West : 	aecToReturn = AntEmissionChemicals.PHEROMONE_00_West;break;
			default:
				Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown DW: {" + dw.toString() + "}");
			}
			break;
		case PHEROMONE_01: 
			switch( dw ) {
			case CURRENT : 	aecToReturn = AntEmissionChemicals.PHEROMONE_01_CURRENT;break;
			case East : 	aecToReturn = AntEmissionChemicals.PHEROMONE_01_East;break;
			case North : 	aecToReturn = AntEmissionChemicals.PHEROMONE_01_North;break;
			case NorthEast :aecToReturn = AntEmissionChemicals.PHEROMONE_01_NorthEast;break;
			case NorthWest :aecToReturn = AntEmissionChemicals.PHEROMONE_01_NorthWest;break;
			case South : 	aecToReturn = AntEmissionChemicals.PHEROMONE_01_South;break;
			case SouthEast :aecToReturn = AntEmissionChemicals.PHEROMONE_01_SouthEast;break;
			case SouthWest :aecToReturn = AntEmissionChemicals.PHEROMONE_01_SouthWest;break;
			case West : 	aecToReturn = AntEmissionChemicals.PHEROMONE_01_West;break;
			default:
				Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown DW: {" + dw.toString() + "}");
			}
			break;
		default: 
			Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown SC: {" + sc.toString() + "}");
		}
		return aecToReturn;
	}
	
	public static AntEmissionChemicals getFrom(DirectionWorld dw, ObjectType ot) {
		AntEmissionChemicals aecToReturn = null;
		switch( ot ) {
		case FOOD: 
			switch( dw ) {
			case CURRENT : 	aecToReturn = AntEmissionChemicals.FOOD_CURRENT;break;
			case East : 	aecToReturn = AntEmissionChemicals.FOOD_East;break;
			case North : 	aecToReturn = AntEmissionChemicals.FOOD_North;break;
			case NorthEast :aecToReturn = AntEmissionChemicals.FOOD_NorthEast;break;
			case NorthWest :aecToReturn = AntEmissionChemicals.FOOD_NorthWest;break;
			case South : 	aecToReturn = AntEmissionChemicals.FOOD_South;break;
			case SouthEast :aecToReturn = AntEmissionChemicals.FOOD_SouthEast;break;
			case SouthWest :aecToReturn = AntEmissionChemicals.FOOD_SouthWest;break;
			case West : 	aecToReturn = AntEmissionChemicals.FOOD_West;break;
			default:
				Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown DW: {" + ot.toString() + "}");
			}
			break;
		default: 
			Logger.printlnLog(LoggerLevel.LL_WARNING, "Unknown OT: {" + ot.toString() + "}");
		}
		return aecToReturn;
	}
	
}
