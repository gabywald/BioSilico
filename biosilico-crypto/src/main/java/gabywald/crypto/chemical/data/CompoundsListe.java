package gabywald.crypto.chemical.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a set of Molecules'. 
 * @author Gabriel Chandesris (2011)
 * @deprecated use List !!
 */
public class CompoundsListe extends ArrayList<Molecule> {
	
	public String toString() {
		String result = new String("");
		for (int i = 0 ; i < this.size() ; i++) 
			{ result += this.get(i).toString(); }
		return result;
	}
	
	public boolean equals(CompoundsListe toCompare) {
		if (this.size() != toCompare.size()) { return false; }
		/** System.out.println("Same size of compounds '"+this.size()+"' !!"); */
		boolean test = true;
		for (int i = 0 ; (i < this.size()) && (test) ; i++) 
			{ test = this.get(i).equals(toCompare.get(i)); }
		if (!test) { return false; }
		return true;
	}
	
	public void add(CompoundsListe molecules) {
		for (int  i = 0 ; i < molecules.size() ; i++) 
			{ this.add(molecules.get(i)); }
	}
	
	public boolean hasError() {
		if (this.size() == 0) {
			System.out.println("Compounds list empty ??");
			return true;
		}
		
		for (int i = 0 ; i < this.size() ; i++) {
			if (this.get(i).hasError()) { return true; }
		}
		return false;
	}
	
	public Molecule getCompoundsWith(String name) {
		/** System.out.println("Getting Configuration with name '"+name+"'"); */
		for (int i = 0 ; i < this.size() ; i++) {
			if (this.get(i).getName().equals(name))
				{ return this.get(i); }
		}
		return null;
	}
	
	public String[] getStringSet() {
		String[] result = new String[this.size()];
		for (int i = 0 ; i < this.size() ; i++) 
			{ result[i] = this.get(i).getName(); }
		return result;
	}
	
	public String[] getDataKeys() {
		List<String> toReturn = new ArrayList<String>();
		for (int i = 0 ; i < this.size() ; i++) {
			String[] tmp = this.get(i).getDataKeys();
			/** After getting list of keys / annotations, 
			 * adding without double's. */
			for (int j = 0 ; j < tmp.length ; j++) 
				{ if (!toReturn.contains(tmp[j])) 
					{ toReturn.add(tmp[j]); } }
		}
		return toReturn.toArray(new String[toReturn.size()]);
	}
}
