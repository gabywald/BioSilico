package gabywald.creatures.genetics.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gabywald.creatures.model.UnsignedByte;
import gabywald.global.data.File;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class CreaturesChemical {
	private UnsignedByte number;
	private String group;
	private String name;
	private UnsignedByte halfLive;
	
	CreaturesChemical(UnsignedByte number, String group, String name, UnsignedByte halfLive) {
		this.number = number;
		this.group = group;
		this.name = name;
		this.halfLive = halfLive;
	}

	public UnsignedByte getNumber() 
		{ return this.number; }

	public String getGroup() 
		{ return this.group; }

	public String getName() 
		{ return this.name; }

	public UnsignedByte getHalfLive() 
		{ return this.halfLive; }
	
	public String toString() {
		StringBuilder sbToReturn = new StringBuilder();
		
		sbToReturn	.append( Integer.toHexString( this.number.getValue() ) ).append("\t")
					.append( this.number.getValue() ).append("\t")
					.append( this.group ).append("\t")
					.append( this.name ).append("\t")
					.append( Integer.toHexString( this.halfLive.getValue() ) ).append("\t")
					.append( this.halfLive.getValue() ); 
		
		return sbToReturn.toString();
	}
	
	public static List<CreaturesChemical> getCreaturesChemicals(CreaturesVersion v) {
		List<CreaturesChemical> toReturn = new ArrayList<CreaturesChemical>();
		
		// NOTE : property to be "resources.creatures.archives.genornics.c1.chemicals.csv" ??
		String propertyV1	= "data.creatures1.chemicals.csv";
		String path2file	= null;
		switch(v) {
		case CREATURES1:
			path2file = Creatures1GenomeParser.PROPERTIES.getProperty( propertyV1 );
			break;
		case CREATURES2:
			path2file = Creatures1GenomeParser.PROPERTIES.getProperty( propertyV1.replace("1", "2") );
			break;
		case CREATURES3:
			// XXX NOTE no C3 chemicals !
			return toReturn; // break;
		}
		
		try {
			// NOTE : here file in base of ressources !
			File chemicalDefinitions = File.loadFile( path2file );
			String group = null;
			for (int i = 0 ; (i < chemicalDefinitions.lengthFile()) ; i++) {
				String line			= chemicalDefinitions.getChamp(i);
				if (line.startsWith( "## " )) { continue; }
				String[] splitter	= line.split(";");
				String number	= splitter[2];
				group			= ( ! splitter[3].equals("") ) ? splitter[3] : group;
				String name		= splitter[4];
				String halfLive	= splitter[6];
				if (number.matches("^\"[0-9]+-[0-9]+\"$")) {
					String[] endpoints = number.substring(1, number.length() - 1).split( "-" );
					int start = Integer.parseInt( endpoints[0] );
					int stopp = Integer.parseInt( endpoints[1] );
					for (int j = start ; j <= stopp ; j++) {
						toReturn.add(new CreaturesChemical(	new UnsignedByte( j ), 
															group, name, 
															new UnsignedByte( Integer.parseInt( halfLive ) )));
					}
				} else {
					toReturn.add(new CreaturesChemical(	new UnsignedByte( Integer.parseInt( number ) ), 
														group, name, 
														new UnsignedByte( Integer.parseInt( halfLive ) )));
				}
				
			}
		}		
		catch (IOException e) { e.printStackTrace(); }
		
		return toReturn;
	}
	
}
