package gabywald.biosilico.structures;

/**
 * This class defines properties of extended lineage for an organism / taxon. 
 * @author Gabriel Chandesris (2010)
 */
public class ExtendedLineageItem {
	/** Unique ID of the taxon. */
	private String uniqueID;
	/** Scientific name of the taxon. */
	private String scientificName;
	/** rank of the taxon. */
	private String rank;
	
	/**
	 * Constructor with Scientific Name (ID id 'unknown' and rank is 'norank'). 
	 * @param scientificName (String)
	 */
	public ExtendedLineageItem(String scientificName)
		{ this.init("unknown", scientificName, "norank"); }
	
	/**
	 * Constructor with Scientific Name and Rank (ID id 'unknown'). 
	 * @param scientificName (String)
	 * @param rank (String)
	 */
	public ExtendedLineageItem(String scientificName,String rank) 
		{ this.init("unknown", scientificName, rank); }
	/**
	 * Constructor with Scientific Name, Rank and ID. 
	 * @param uniqueID (String)
	 * @param scientificName (String)
	 * @param rank (String)
	 */
	public ExtendedLineageItem(String uniqueID,String scientificName,String rank)
		{ this.init(uniqueID, scientificName, rank); }
	
	/**
	 * helper for constructors. 
	 * @param uniqueID (String)
	 * @param scientificName (String)
	 * @param rank (String)
	 */
	private void init(String uniqueID,String scientificName,String rank) {
		this.uniqueID = uniqueID;
		this.scientificName = scientificName;
		this.rank = rank;
	}
	
	public String getScientificName()	{ return this.scientificName; }
	public String getRank()				{ return this.rank; }
	public String getUniqueID()			{ return this.uniqueID; }
	
	public String toString() {
		return "\tTAXON\n\t\tID\t"+this.uniqueID+"\n\t\t"+
		"SCIENTIFIC NAME\t"+this.scientificName+"\n\t\t"+
		"RANK\t"+this.rank;
	}
}
