package gabywald.biosilico.model.enums;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public enum DirectionWorld implements IChemicalsType {
	CURRENT		(800, "LLL", "Local / Current Position"), 
	NorthWest	(801, "LNW", "Local North-West"), 
	North		(802, "LNN", "Local North"), 
	NorthEast	(803, "LNE", "Local North-East"), 
	East		(804, "LEE", "Local East"), 
	SouthEast	(805, "LSE", "Local South-East"), 
	South		(806, "LSS", "Local South"), 
	SouthWest	(807, "LSW", "Local South-West"), 
	West		(808, "LWW", "Local West"), 
	UP				(810, "UUU", "UP Position"), 
	UpNorthWest		(811, "UNW", "UP North-West"), 
	UPNorth			(812, "UNN", "UP North"), 
	UPNorthEast		(813, "UNE", "UP North-East"), 
	UPEast			(814, "UEE", "UP East"), 
	UPSouthEast		(815, "USE", "UP South-East"), 
	UPSouth			(816, "USS", "UP South"), 
	UPSouthWest		(817, "USW", "UP South-West"), 
	UPWest			(818, "UWW", "UP West"), 
	DOWN				(820, "DUU", "Down Position"), 
	DOWNNorthWest		(821, "DNW", "Down North-West"), 
	DOWNNorth			(822, "DNN", "Down North"), 
	DOWNNorthEast		(823, "DNE", "Down North-East"), 
	DOWNEast			(824, "DEE", "Down East"), 
	DOWNSouthEast		(825, "DSE", "Down South-East"), 
	DOWNSouth			(826, "DSS", "Down South"), 
	DOWNSouthWest		(827, "DSW", "Down South-West"), 
	DOWNWest			(828, "DWW", "Down West"), ;
	
	private int index;
	private String name, definition;

	private DirectionWorld(int index, String name, String definition) {
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
}
