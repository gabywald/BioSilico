package gabywald.global.structures;

/**
 * Generic &lt;Object&gt;Liste made for example, liability and make this programm
 * readable (and modeling). <u><b>Please do NOT use it ! (except for inheritance)</b></u>, to discourage
 * anyone to use it, this is an abstract class and deprecated functions. 
 * <br>Useness of this class is to transmit a model like Vector but althought 
 * more specific : this permit to avoid cast, if you do not like operations
 * like the following one (because this is not very read-friendly) : <i>
 * &lt;ObjectType&gt; something = <u>(&lt;ObjectType&gt;)</u>vector.getObjetAt(42)
 * </i><br>[...]
 * <br>Methods here are for examples of what you can find in descendants. 
 * Copy, Paste and change it to new specific Liste's. <u>Think you can add 
 * (and you already have to do so) sorting procedure, 
 * average calculator methods...</u>
 * <br>Of course since Java 1.5, you can do Vector&lt;ObjectType&gt;... 
 * Not so easy to use this Vector&lt;ObjectType&gt; !
 * <br><b><u>Use case of this class (and inheritant classes : </u></b>
 * <ul type="circle">
 * 		<li>ObjectListe current = new ObjectListe() 
 * 				<i>To get an empty liste...</i></li>
 * 		<li>ObjectListe current = new ObjectListe(tab) 
 * 				<i>To get a liste with pre-made table of Object's</i></li>
 * 		<li>current.addObject((Object)obj)
 * 				<i>To add an object</i></li>
 * 		<li>current.removeObject([(Object)obj|(int)i])
 * 				<i>To remove an Object (if present)</i></li>
 * 		<li>[...]</li>
 * </ul>
 * @author Gabriel Chandesris (2008-2010)
 * @since 2008
 * @see ObjectExampleListe
 */
public abstract class ObjectListe {
	/** The list of Object's. */
	protected Object[] liste;
	
	/**
	 * To get the length of the current list of Object's
	 * @return (int)
	 */
	public abstract int length();
	
	/** Default constructor with a list of 0 elements.  */
	protected ObjectListe() { this.liste = new Object[0]; }
	
	/**
	 * Constructor with a pre-made table of Object's. 
	 * @param liste (Object[])
	 */
	protected ObjectListe(Object[] liste) { this.liste = liste; }
	
	/**
	 * To get the current list of Object's as a table. 
	 * @return (Object[]) A table of Object's.
	 */
	protected Object[] getListeObjects() { return this.liste; } 
	
	/**
	 * To get a specific Object of the list. 
	 * @param i (int) Position of the Object in the list. 
	 * @return (Object) Object at position i. 
	 */
	protected Object getObject(int i) { 
		/** These controls with if to be sure... */ 
		if (i >= this.liste.length) { return null; }
		if (i < 0) { return null; }
		return this.liste[i]; 
	}
	
	/**
	 * To set a new list in the instance of ObjectListe. 
	 * @param liste (Object[]) A table of Object's. 
	 */
	protected void setListe (Object[] liste) { this.liste = liste; }
	
	/**
	 * To set an Object at a specific place in the list, replace the old one. 
	 * @param elt (Object) Object to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 */
	protected void setObject(Object elt,int i) {
		if ( (i < this.liste.length) && (i >= 0) ) 
			{ this.liste[i] = elt; }
	}
	
	/**
	 * To add an Object to the end of the list. 
	 * @param elt (Object) Object to add. 
	 */
	protected void addObject(Object elt) {
		Object[] nextListe = new Object[this.liste.length+1];
		for (int i = 0 ; i < this.liste.length ; i++) 
			{ nextListe[i] = this.liste[i]; }
		nextListe[this.liste.length] = elt;
		this.liste = nextListe;
	}
	
	/**
	 * To add an Object at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Object)
	 * @param pos (int)
	 * @see ObjectListe#addObject(Object)
	 */
	protected void addObject(Object elt,int pos) {
		if (pos >= this.liste.length) { this.addObject(elt); }
		Object[] nextListe = new Object[this.liste.length+1];
		for (int i = 0 ; i < pos ; i++) 
			{ nextListe[i] = this.liste[i]; }
		nextListe[pos] = elt;
		for (int i = pos ; i < this.liste.length ; i++) 
			{ nextListe[i+1] = this.liste[i]; }
		this.liste = nextListe;
	}
	
	/**
	 * To know if an Object is present is the list. 
	 * @param elt (Object)
	 * @return (boolean)
	 */
	protected boolean has(Object elt) {
		for (int i = 0 ; i < this.liste.length ; i++) {
			if (this.liste[i].equals(elt)) { return true; }
		}
		return false;
	}
	
	/**
	 * To remove a specific Object (nothing append if not present). 
	 * @param elt (Object) Object to remove. 
	 */
	protected void removeObject(Object elt) {
		if (this.has(elt)) {
			Object[] nextListe = new Object[this.liste.length-1];
			int i = 0;
			if (nextListe.length > 0) {
				while ( (i < this.liste.length) && (!this.liste[i].equals(elt)) ) 
					{ nextListe[i] = this.liste[i];i++; }
				if (this.liste[i].equals(elt)) {
					i++;
					while (i < this.liste.length)
						{ nextListe[i-1] = this.liste[i];i++; }
				}
			}
			this.liste = nextListe;
		}
	}
	
	/**
	 * To remove an Object at a specific place in the liste.
	 * @param nbElt (int) Position of the Object
	 */
	protected void removeObject(int nbElt) {
		if ( (nbElt >= 0) && (nbElt < this.liste.length) ) {
			Object[] nextListe = new Object[this.liste.length-1];
			int i = 0;
			while ( (i < this.liste.length) && (i != nbElt) ) 
				{ nextListe[i] = this.liste[i];i++; }
			if (i == nbElt) {
				i++;
				while (i < this.liste.length)
					{ nextListe[i-1] = this.liste[i];i++; }
			}
			this.liste = nextListe;
		}
	}
	
	/**
	 * To exchange two Object's within the list et their positions. 
	 * @param a (int)
	 * @param b (int)
	 */
	public void exchange(int a,int b) {
		if ( ( (a >= 0) && (a < this.liste.length) )
				&& ( (b >= 0) && (b < this.liste.length) ) ){
			Object tmp = this.liste[a];
			this.liste[a] = this.liste[b];
			this.liste[b] = tmp;
		}
	}
	
	/**
	 * To know if two ObjectListe are equals (local instance and an other). 
	 * @param toCompare (ObjectListe) Other ObjectListe. 
	 * @return (boolean)
	 */
	public boolean equals(ObjectListe toCompare) {
		if (this.liste.length != toCompare.length()) { return false; }
		/** Other criteria could be used as above, like average... */
		for (int i = 0 ; i < this.liste.length ; i++) {
			if (!this.liste[i].equals(toCompare.getObject(i)))
				{ return false; }
		}
		return true;
	}
	
	/** This method has to be copied if changes needed into inheritant classes. */
	public String toString() {
		String result = new String();
		for (int i = 0 ; i < this.liste.length ; i++) 
			{ result += this.liste[i].toString()+"\n"; }
		return result;
	}
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your Object and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
}
