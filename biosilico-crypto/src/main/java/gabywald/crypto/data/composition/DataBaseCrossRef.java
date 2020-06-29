package gabywald.crypto.data.composition;

public class DataBaseCrossRef {
	/** Some data about Cross-reference ; [ database name, first ID, second ID ]. */
	private String[] someDatas;
	
	public DataBaseCrossRef(String database, 
							String iden) 
		{ this.init(database, iden, null); }
	
	public DataBaseCrossRef(String database, 
							String primary, 
							String secondary) 
		{ this.init(database, primary, secondary); }
	
	private void init(String database, 
					  String primary, 
					  String secondary) {
		this.someDatas = new String[3];
		this.someDatas[0] = database;
		this.someDatas[1] = primary;
		this.someDatas[2] = secondary;
	}
	
	public String toString() { return this.toStringEMBL(); }
	
	public String toStringEMBL() {
		String toReturn = new String("");
		toReturn += "DR   "+this.someDatas[0]+"; "+this.someDatas[1];
		toReturn += ((this.someDatas[2] != null)?"; "+this.someDatas[2]:"")+".";
		return toReturn;
	}
	
}
