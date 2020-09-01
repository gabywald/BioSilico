package gabywald.biosilico.model.enums;

import java.util.Arrays;
import java.util.List;

import gabywald.biosilico.interfaces.IChemicalsType;

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
	UP				(809, "UUU", "UP Position"), 
	UpNorthWest		(810, "UNW", "UP North-West"), 
	UPNorth			(811, "UNN", "UP North"), 
	UPNorthEast		(812, "UNE", "UP North-East"), 
	UPEast			(813, "UEE", "UP East"), 
	UPSouthEast		(814, "USE", "UP South-East"), 
	UPSouth			(815, "USS", "UP South"), 
	UPSouthWest		(816, "USW", "UP South-West"), 
	UPWest			(817, "UWW", "UP West"), 
	DOWN				(818, "DUU", "Down Position"), 
	DOWNNorthWest		(819, "DNW", "Down North-West"), 
	DOWNNorth			(820, "DNN", "Down North"), 
	DOWNNorthEast		(821, "DNE", "Down North-East"), 
	DOWNEast			(822, "DEE", "Down East"), 
	DOWNSouthEast		(823, "DSE", "Down South-East"), 
	DOWNSouth			(824, "DSS", "Down South"), 
	DOWNSouthWest		(825, "DSW", "Down South-West"), 
	DOWNWest			(826, "DWW", "Down West"), ;
	
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
	
	public static DirectionWorld[] values2D()	{
		return (DirectionWorld[]) DirectionWorld.values2DasList().toArray();
	}
	
	public static List<DirectionWorld> values2DasList() {
		return Arrays.asList(	DirectionWorld.CURRENT	, DirectionWorld.West	, DirectionWorld.East		, 
								DirectionWorld.NorthWest, DirectionWorld.North	, DirectionWorld.NorthEast	,  
								DirectionWorld.SouthEast, DirectionWorld.South	, DirectionWorld.SouthWest	);
	}
	
	public static List<DirectionWorld> valuesAsList() { 
		return Arrays.asList( DirectionWorld.values() );
	}
	
	public static DirectionWorld get2DFrom(int index) {
		return DirectionWorld.getFrom(index, true);
	}
	
	public static DirectionWorld get3DFrom(int index) {
		return DirectionWorld.getFrom(index, false);
	}
	
	public static DirectionWorld getFrom(int index, boolean not3D) {
		if ( (index < 800) || (index > ( (not3D) ? 808 : 826 )) ) { return null; }
		for (DirectionWorld dw : Arrays.asList( DirectionWorld.values() ) ) {
			if (index == dw.getIndex()) { return dw; }
		}
		return null;
	}
}
