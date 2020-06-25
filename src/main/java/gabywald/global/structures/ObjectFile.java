package gabywald.global.structures;

/**
 * Generic  &lt;Object&gt;File (FIFO set) made for example. 
 * @author Gabriel Chandesris (2010)
 * @deprecated Make your own inheritant class !!
 */
public class ObjectFile {
	/** The list of Object's. */
	private Object[] file;
	
	/** Default constructor with a list of 0 element. */
	public ObjectFile() { this.file = new Object[0]; }
	
	/**
	 * To get the length of the current list of Object's
	 * @return (int)
	 */
	public int length(){ return this.file.length; }
	
	/**
	 * To insert an Object in the list. 
	 * @param elt (Object)
	 */
	public void push(Object elt) {
		Object[] nextTMP = new Object[this.file.length+1];
		for (int i = 0 ; i < this.file.length ; i++) 
			{ nextTMP[i] = this.file[i]; }
		nextTMP[this.file.length] = elt;
		this.file = nextTMP;
	}
	
	/**
	 * To get the first Object in the list and remove it from file. 
	 * @return (Object)
	 */
	public synchronized Object pop() {
		Object tmp = null;
		if (!this.isFileEmpty()) {
			tmp = this.file[0];
			Object[] nextTMP = new Object[this.file.length-1];
			for (int i = 1 ; i < this.file.length ; i++) 
				{ nextTMP[i-1] = this.file[i]; }
			this.file = nextTMP;
		}
		return tmp;
	}
	
	/**
	 * To know if file is empty. 
	 * @return (boolean)
	 */
	public boolean isFileEmpty() {
		if (this.file.length > 0) { return false; }
		else { return true; }
	}
	
}
