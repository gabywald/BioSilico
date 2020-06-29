package gabywald.cellmodel.structures;

import gabywald.cellmodel.model.Protein;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public class ProteinFile {
	private Protein[] file;
	
	public ProteinFile() { this.file = new Protein[0]; }
	
	public int length() { return this.file.length; }
	
	public void push(Protein elt) {
		Protein[] nextTMP = new Protein[this.file.length+1];
		for (int i = 0 ; i < this.file.length ; i++) 
			{ nextTMP[i] = this.file[i]; }
		nextTMP[this.file.length] = elt;
		this.file = nextTMP;
	}
	
	public synchronized Protein pop() {
		// System.out.println("\t\tProtein.pop() "+this.length());
		Protein tmp = null;
		if (!this.isFileEmpty()) {
			tmp = this.file[0];
			Protein[] nextTMP = new Protein[this.file.length-1];
			for (int i = 1 ; i < nextTMP.length ; i++) 
				{ nextTMP[i-1] = this.file[i]; }
			this.file = nextTMP;
		}
		return tmp;
	}
	
	public boolean isFileEmpty() {
		if (this.file.length > 0) { return false; }
		else { return true; }
	}
	
	public boolean remove(Protein elt) {
		ProteinFile nextTMP = new ProteinFile();
		Protein tmp = this.pop();
		while ( (!this.isFileEmpty()) && (tmp.equals(elt)) ) {
			nextTMP.push(tmp);
			tmp = this.pop();
		}
		if (tmp.equals(elt)) { return true; }
		else {
			while (!this.isFileEmpty()) {
				nextTMP.push(tmp);
				tmp = this.pop();
			}
			return false;
		}
	}
}
