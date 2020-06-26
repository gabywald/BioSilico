package gabywald.global.structures;

/**
 * Generic  &lt;Object&gt;Pile (FILO set) made for example. 
 * @author Gabriel Chandesris (2010)
 * @deprecated Make your own inheritant class !!
 */
public class ObjectPile {
	/** The list of Object's. */
	private Object[] pile;
	
	/** Default constructor with a list of 0 element. */
	public ObjectPile() { this.pile = new Object[0]; }
	
	/**
	 * To get the length of the current list of Object's
	 * @return (int)
	 */
	public int length(){ return this.pile.length; }
	
	/**
	 * To insert an Object in the list. 
	 * @param elt (Object)
	 */
	public void push(Object elt) {
		Object[] nextTMP = new Object[this.pile.length+1];
		for (int i = 0 ; i < this.pile.length ; i++) 
			{ nextTMP[i] = this.pile[i]; }
		nextTMP[this.pile.length] = elt;
		this.pile = nextTMP;
	}
	
	/**
	 * To get the last Object in the list and remove it from pile. 
	 * @return (Object)
	 */
	public synchronized Object pop() {
		Object tmp = null;
		if (!this.isPileEmpty()) {
			tmp = this.pile[this.pile.length-1];
			Object[] nextTMP = new Object[this.pile.length-1];
			for (int i = 0 ; i < this.pile.length-1 ; i++) 
				{ nextTMP[i-1] = this.pile[i]; }
			this.pile = nextTMP;
		}
		return tmp;
	}
	
	/**
	 * To know if pile is empty. 
	 * @return (boolean)
	 */
	public boolean isPileEmpty() {
		if (this.pile.length > 0) { return false; }
		else { return true; }
	}
	
}
