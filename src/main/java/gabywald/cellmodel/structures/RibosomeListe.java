package gabywald.cellmodel.structures;

import gabywald.cellmodel.model.Ribosome;

public class RibosomeListe {
	private Ribosome[] liste;

	public RibosomeListe() { this.liste = new Ribosome[0]; }

	public RibosomeListe(Ribosome[] liste) { this.liste = liste; }

	public int length() { return this.liste.length; }

	public Ribosome[] getListeRibosome() { return this.liste; }

	public Ribosome getRibosome(int i) { 
		if (i >= this.liste.length) { return null; }
		if (i < 0) { return null; }
		return this.liste[i]; 
	}

	public void setListe(Ribosome[] liste) { this.liste = liste; }

	public void setRibosome(Ribosome elt,int i) {
		if ( (i < this.liste.length) && (i >= 0) ) 
		{ this.liste[i] = elt; }
	}

	public void addRibosome(Ribosome elt) {
		Ribosome[] nouvelleListe = new Ribosome[this.liste.length+1];
		for (int i = 0 ; i < this.liste.length ; i++) 
			{ nouvelleListe[i] = this.liste[i]; }
		nouvelleListe[this.liste.length] = elt;
		this.liste = nouvelleListe;;
	}

	public boolean has(Ribosome elt) {
		for (int i = 0 ; i < this.liste.length ; i++) {
			if (this.liste[i].equals(elt)) { return true; }
		}
		return false;
	}

	public void removeRibosome(Ribosome elt) {
		if (this.has(elt)) {
			Ribosome[] nouvelleListe = new Ribosome[this.liste.length-1];
			int i = 0;
			if (nouvelleListe.length > 0) {
				while ( (i < this.liste.length) && (!this.liste[i].equals(elt)) ) 
				{ nouvelleListe[i] = this.liste[i];i++; }
				if (this.liste[i].equals(elt)) {
					i++;
					while (i < this.liste.length)
					{ nouvelleListe[i-1] = this.liste[i];i++; }
				}
			}
			this.liste = nouvelleListe;
		}
	}

	public void removeRibosome(int nbElt) {;
	if ( (nbElt >= 0) && (nbElt < this.liste.length) ) {
		Ribosome[] nouvelleListe = new Ribosome[this.liste.length-1];
		int i = 0;
		while ( (i < this.liste.length) && (i != nbElt) ) 
		{ nouvelleListe[i] = this.liste[i];i++; }
		if (i == nbElt) {
			i++;
			while (i < this.liste.length)
			{ nouvelleListe[i-1] = this.liste[i];i++; }
		}
		this.liste = nouvelleListe;;
	}
	}
}
