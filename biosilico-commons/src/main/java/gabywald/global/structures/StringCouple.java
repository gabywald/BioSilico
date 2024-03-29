package gabywald.global.structures;

/**
 * This class describe a couple of String's in same object. 
 * @author Gabriel Chandesris (2010)
 */
public class StringCouple {
	private String valueA;
	private String valueB;

	public StringCouple() {
		this.valueA = new String();
		this.valueB = new String();
	}
	
	public StringCouple(String a,String b) 
		{ this.valueA = a;this.valueB = b; }
	
	public String getValueA() { return this.valueA; }
	public String getValueB() { return this.valueB; }
	
	public void setValueA(String valueA) { this.valueA = valueA; }
	public void setValueB(String valueB) { this.valueB = valueB; }
	
	public String toStringComma() { return "("+this.valueA+","+this.valueB+")"; }
	
	public String toString() { return this.valueA+"          "+this.valueB; }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			{ return true; }

		if ( (obj == null) || (this.getClass() != obj.getClass()) )
			{ return false; }
		
		StringCouple sc = (StringCouple) obj;
		
		// Call equals() method of the underlying objects
		if ( ! this.valueA.equals(sc.valueA))
			{ return false; }
		return this.valueB.equals(sc.valueB);
	}
}
