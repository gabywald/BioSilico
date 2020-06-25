package gabywald.biosilico.structures;

import gabywald.biosilico.model.Neuron;
import gabywald.global.structures.ObjectListe;

public class NeuronListe extends ObjectListe {
	/**
	 * To get the length of the current list of Object's
	 * @return (int)
	 * @see ObjectListe#length()
	 * @see ObjectListe#liste
	 */
	public int length() { return this.liste.length; }
	
	/** 
	 * Default constructor with a list of 0 elements.
	 * @see ObjectListe#ObjectListe() */
	public NeuronListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of Neuron's. 
	 * @param liste (Neuron[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public NeuronListe(Neuron[] liste) { super(liste); }
	
	/**
	 * To get the current list of Neuron's as a table. 
	 * @return (Neuron[]) A table of Neuron's.
	 * @see ObjectListe#getListeObjects()
	 */
	public Neuron[] getListeNeurons() {
		Neuron[] tabReturn = new Neuron[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getNeuron(i); }
		return tabReturn;
		// return (Neuron[])super.getListeObjects(); 
	} 
	
	/**
	 * To get a specific Neuron of the list. 
	 * @param i (int) Position of the Neuron in the list. 
	 * @return (Neuron) Neuron at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public Neuron getNeuron(int i) 
		{ return (Neuron)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of NeuronListe. 
	 * @param liste (Neuron[]) A table of Neuron's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(Neuron[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an Neuron at a specific place in the list, replace the old one. 
	 * @param elt (Neuron) Neuron to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setNeuron(Neuron elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an Neuron to the end of the list. 
	 * @param elt (Neuron) Neuron to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addNeuron(Neuron elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an Neuron at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Neuron)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addNeuron(Neuron elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an Neuron is present is the list. 
	 * @param elt (Neuron)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(Neuron elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific Neuron (nothing append if not present). 
	 * @param elt (Neuron) Neuron to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeNeuron(Neuron elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an Neuron at a specific place in the liste.
	 * @param nbElt (int) Position of the Neuron
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeNeuron(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your Neuron and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
	
	/**
	 * Get the highest activity in a set og Neuron's. 
	 * @return (int)
	 * @see gabywald.biosilico.model.Neuron#ckActivated()
	 */
	public int getHighestActivity() {
		int max = 0;
		for (int i = 0 ; i < this.liste.length ; i++) {
			Neuron tmp = this.getNeuron(i);
			if ( (tmp.ckActivated()) && (tmp.getActivity() > max) )
				{ max = tmp.getActivity(); }
		}
		return max;
	}
	
	/**
	 * Get the Neuron with the highest activity in a set. 
	 * @return (Neuron)
	 * @see gabywald.biosilico.structures.NeuronListe#getHighestActivity()
	 * @see gabywald.biosilico.model.Neuron#ckActivated()
	 */
	public Neuron getFirestNeuron() {
		int max = this.getHighestActivity();
		if (max == 0) { return null; }
		for (int i = 0 ; i < this.liste.length ; i++) {
			Neuron tmp = this.getNeuron(i);
			if ( (tmp.ckActivated()) && (tmp.getActivity() == max) )
				{ return tmp; }
		}
		return null;
	}

}
