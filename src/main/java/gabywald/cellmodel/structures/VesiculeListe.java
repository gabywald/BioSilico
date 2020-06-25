package gabywald.cellmodel.structures;

import gabywald.cellmodel.model.Vesicule;

public class VesiculeListe {
	private Vesicule[] liste;

	public VesiculeListe() { this.liste = new Vesicule[0]; }

	public VesiculeListe(Vesicule[] liste) { this.liste = liste; }

	public int length() { return this.liste.length; }

	public Vesicule[] getListeVesicule() { return this.liste; }

	public Vesicule getVesicule(int i) { 
		if (i >= this.liste.length) { return null; }
		if (i < 0) { return null; }
		return this.liste[i]; 
	}

	public void setListe(Vesicule[] liste) { this.liste = liste; }

	public void setVesicule(Vesicule elt,int i) {
		if ( (i < this.liste.length) && (i >= 0) ) 
		{ this.liste[i] = elt; }
	}

	public void addVesicule(Vesicule elt) {
		Vesicule[] nouvelleListe = new Vesicule[this.liste.length+1];
		for (int i = 0 ; i < this.liste.length ; i++) 
		{ nouvelleListe[i] = this.liste[i]; }
		nouvelleListe[this.liste.length] = elt;
		this.liste = nouvelleListe;;
	}

	public boolean has(Vesicule elt) {
		for (int i = 0 ; i < this.liste.length ; i++) {
			if (this.liste[i].equals(elt)) { return true; }
		}
		return false;
	}

	public void removeVesicule(Vesicule elt) {
		if (this.has(elt)) {
			Vesicule[] nouvelleListe = new Vesicule[this.liste.length-1];
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

	public void removeVesicule(int nbElt) {;
	if ( (nbElt >= 0) && (nbElt < this.liste.length) ) {
		Vesicule[] nouvelleListe = new Vesicule[this.liste.length-1];
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

