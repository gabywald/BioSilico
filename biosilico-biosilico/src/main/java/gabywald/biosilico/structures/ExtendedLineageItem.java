package gabywald.biosilico.structures;

/**
 * This class defines properties of extended lineage for an organism / taxon. 
 * @author Gabriel Chandesris (2010, 2020)
 */
public class ExtendedLineageItem {
	/** Unique ID of the taxon. */
	private String uniqueID;
	/** Scientific name of the taxon. */
	private String scientificName;
	/** rank of the taxon. */
	private String rank;
	
	/** Default Rank. 'norank' */
	public static final String DEFAULT_RANK	= "norank";
	/**
	 * Default Unique ID. 'unknown'
	 * NOTE XXX 'UUID.randomUUID();' ? // 10 numbers => [0-9]{10}
	 */
	public static final String DEFAULT_UID	= "unknown";
	
	/**
	 * Constructor with Scientific Name (ID id 'unknown' and rank is 'norank'). 
	 * @param scientificName (String)
	 */
	public ExtendedLineageItem(String scientificName)
		{ this("unknown", scientificName, "norank"); }
	
	/**
	 * Constructor with Scientific Name and Rank (ID id 'unknown'). 
	 * @param scientificName (String)
	 * @param rank (String)
	 */
	public ExtendedLineageItem(String scientificName, String rank) 
		{ this("unknown", scientificName, rank); }
	/**
	 * Constructor with Scientific Name, Rank and ID. 
	 * @param uniqueID (String)
	 * @param scientificName (String)
	 * @param rank (String)
	 */
	public ExtendedLineageItem(String uniqueID, String scientificName, String rank) {
		this.uniqueID		= uniqueID;
		this.scientificName	= scientificName;
		this.rank			= rank;
	}
	
	public String getScientificName()	{ return this.scientificName; }
	public String getRank()				{ return this.rank; }
	public String getUniqueID()			{ return this.uniqueID; }
	
	public String toString() {
		StringBuilder sbToReturn = new StringBuilder();
		sbToReturn.append( "\tTAXON\n\t\tID\t" )	.append( this.uniqueID ).append( "\n" );
		sbToReturn.append( "\t\tSCIENTIFIC NAME\t" ).append( this.scientificName ).append( "\n" );
		sbToReturn.append( "\t\tRANK\t" )			.append( this.rank );
		
		return sbToReturn.toString();
	}
}
