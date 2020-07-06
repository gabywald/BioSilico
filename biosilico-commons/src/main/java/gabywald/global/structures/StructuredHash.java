package gabywald.global.structures;

/**
 * @author Gabriel Chandesris (2010)
 * @deprecated ... (2020)
 */
public class StructuredHash {
	/** Indicates if it is a String (true) or Short (false) set. */
	private boolean type;
	/** String set (if type is true). */
	private StringListe idents;
	/** Short set (if type is false). */
	private ShortListe numbers;
	/**  */
	private Object items[];
	
	/**
	 * Default constructor. 
	 * @param b (boolean) true for String, false for Short
	 */
	public StructuredHash(boolean b) {
		this.items	= new Object[0];
		this.type	= b;
		if (this.type) { this.idents = new StringListe(); }
		else { this.numbers = new ShortListe(); }
	}
	
	/**
	 * Length of the hash. 
	 * @return (int)
	 */
	public int length() { return this.items.length; }
	
	/**
	 * To add an Object instance in the hash and return it 
	 * (if key already exists, return previous Object instance). 
	 * <br>If type is false : return null. 
	 * @param key (String)
	 * @param elt (Object)
	 * @return (Object)
	 */
	public Object put(String key,Object elt) {
		if (!this.type) { return null; }
		if (this.idents.has(key)) {
			int detect = this.detectKey(key);
			Object tmp = this.items[detect];
			this.idents.setString(key, detect);
			this.items[detect] = elt;
			return tmp;
		} else {
			this.idents.addString(key);
			this.addObject(elt);
			return elt;
		}
	}
	
	/**
	 * To add an Object instance in the hash and return it 
	 * (if key already exists, return previous Object instance). 
	 * <br>If type is true : return null. 
	 * @param key (short)
	 * @param elt (Object)
	 * @return (Object)
	 */
	public Object put(short key,Object elt) {
		if (this.type) { return null; }
		Short keybis = new Short(key);
		if (this.numbers.has(keybis)) {
			int detect = this.detectKey(keybis);
			Object tmp = this.items[detect];
			this.numbers.setShort(keybis, detect);
			this.items[detect] = elt;
			return tmp;
		} else {
			this.numbers.addShort(keybis);
			this.addObject(elt);
			return elt;
		}
	}
	
	/**
	 * To get an Object instance in the hash. 
	 * <br>if type is false : return null. 
	 * @param key (String)
	 * @return (Object)
	 */
	public Object get(String key) {
		if (!this.type) { return null; }
		for (int i = 0 ; i < this.idents.length() ; i++) { 
			if (this.idents.getString(i).equals(key))
				{ return this.items[i]; }
		}
		return null;
	}
	
	/**
	 * To get an Object instance in the hash. 
	 * <br>if type is true : return null. 
	 * @param key (short)
	 * @return (Object)
	 */
	public Object get(short key) {
		if (this.type) { return null; }
		Short keybis = new Short(key);
		for (int i = 0 ; i < this.numbers.length() ; i++) { 
			if (this.numbers.getShort(i).equals(keybis))
				{ return this.items[i]; }
		}
		return null;
	}
	
	/**
	 * To add an Object to the end of the list. 
	 * @param elt (Object) Object to add. 
	 */
	private void addObject(Object elt) {
		Object[] nextItems = new Object[this.items.length+1];
		for (int i = 0 ; i < this.items.length ; i++) 
			{ nextItems[i] = this.items[i]; }
		nextItems[this.items.length] = elt;
		this.items = nextItems;
	}
	
	/**
	 * To know if a key is present, index if present or '-1'. 
	 * @param key (String)
	 * @return (int)
	 */
	private int detectKey(String key) {
		for (int i = 0 ; i < this.idents.length() ; i++) {
			if (this.idents.getString(i).equals(key))
				{ return i; }
		}
		return -1;
	}
	
	/**
	 * To know if a key is present, index if present or '-1'. 
	 * @param key (Short)
	 * @return (int)
	 */
	private int detectKey(Short key) {
		for (int i = 0 ; i < this.numbers.length() ; i++) {
			if (this.numbers.getShort(i).equals(key))
				{ return i; }
		}
		return -1;
	}
}
