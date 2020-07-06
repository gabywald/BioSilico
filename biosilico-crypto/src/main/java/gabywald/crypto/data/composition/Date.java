package gabywald.crypto.data.composition;

public class Date {
	/** Indicates if created (true) or Last Version (false). */
	private boolean created;
	/** Someb usefule data ; { day, month, year, release, version }. */
	private int[] someDatas;
	
	/**
	 * Generic Constructor. 
	 * @param day (int)
	 * @param month (String) '[A-Z]{3}'
	 * @param year (int)
	 * @param release (int)
	 * @param version (int)
	 * @param creation (boolean)
	 */
	public Date(int day, String month, int year, 
				int release, int version, 
				boolean creation) 
		{ this.init(creation, new int[] 
		                     { day, Date.monthDetection(month), year, 
								release, version} ); }
	
	/**
	 * Constructor for 'Created' Date (created is true). 
	 * @param day (int)
	 * @param month (String) '[A-Z]{3}'
	 * @param year (int)
	 * @param release (int)
	 */
	public Date(int day, String month, int year, 
				int release) 
		{ this.init(true, new int[] 
 		                     { day, Date.monthDetection(month), year, 
 								release, 0} ); }
	
	/**
	 * Constructor for 'Last Release' Date (created is false). 
	 * @param day (int)
	 * @param month (String) '[A-Z]{3}'
	 * @param year (int)
	 * @param release (int)
	 * @param version (int)
	 */
	public Date(int day, String month, int year, 
				int release, int version) 
		{ this.init(false, new int[] 
 		                     { day, Date.monthDetection(month), year, 
 								release, version} ); }
	
	private void init(boolean creation, int[] datas) {
		this.created	= creation;
		this.someDatas	= new int[5];
		for (int i = 0 ; i < this.someDatas.length ; i++) 
			{ this.someDatas[i] = 0; }
		for (int i = 0 ; (i < this.someDatas.length) 
							&& (i < datas.length) ; i++) 
			{ this.someDatas[i] = datas[i]; }
	}
	
	private static int monthDetection(String month) {
		if (month.equals("JAN")) { return  1; }
		if (month.equals("FEB")) { return  2; }
		if (month.equals("MAR")) { return  3; }
		if (month.equals("APR")) { return  4; }
		if (month.equals("MAY")) { return  5; }
		if (month.equals("JUN")) { return  6; }
		if (month.equals("JUL")) { return  7; }
		if (month.equals("AUG")) { return  8; }
		if (month.equals("SEP")) { return  9; }
		if (month.equals("OCT")) { return 10; }
		if (month.equals("NOV")) { return 11; }
		if (month.equals("DEC")) { return 12; }
		return 0;
	}
	
	private static String month(int mon) {
		switch(mon) {
		case( 1):return "JAN";
		case( 2):return "FEB";
		case( 3):return "MAR";
		case( 4):return "APR";
		case( 5):return "MAY";
		case( 6):return "JUN";
		case( 7):return "JUL";
		case( 8):return "AUG";
		case( 9):return "SEP";
		case(10):return "OCT";
		case(11):return "NOV";
		case(12):return "DEC";
		}
		return "XXX";
	}
	
	/** @deprecated Use another toString*() !! */
	public String toString() { return this.toStringEmbl(); }
	
	public String toStringEmbl() {
		String toReturn = new String("");
		
		if (this.created) {
			/** '12-SEP-1991 (Rel. 29, Created)' */
			String month = Date.month(this.someDatas[1]);
			toReturn += this.someDatas[0]+"-"+month+"-"+this.someDatas[2]
			            +" (Rel. "+this.someDatas[3]+", Created)";
		} else {
			/** '25-NOV-2005 (Rel. 85, Last updated, Version 11)' */
			String month = Date.month(this.someDatas[1]);
			toReturn += this.someDatas[0]+"-"+month+"-"+this.someDatas[2]
			            +" (Rel. "+this.someDatas[3]+", Last updated, Version "
			            +this.someDatas[4]+")";
		}
		
		return toReturn;
	}
	
}
