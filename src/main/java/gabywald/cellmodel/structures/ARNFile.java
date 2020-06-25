package gabywald.cellmodel.structures;

import gabywald.cellmodel.model.ARN;

public class ARNFile {
	private ARN[] file;
	
	public ARNFile() { this.file = new ARN[0]; }
	
	public int length() { return this.file.length; }
	
	public void push(ARN elt) {
		ARN[] nextTMP = new ARN[this.file.length+1];
		for (int i = 0 ; i < this.file.length ; i++) 
			{ nextTMP[i] = this.file[i]; }
		nextTMP[this.file.length] = elt;
		this.file = nextTMP;
	}
	
	public synchronized ARN pop() {
		// System.out.println("\t\tARN.pop() "+this.length());
		ARN tmp = null;
		if (!this.isFileEmpty()) {
			tmp = this.file[0];
			ARN[] nextTMP = new ARN[this.file.length-1];
			for (int i = 1 ; i < this.file.length ; i++) 
				{ nextTMP[i-1] = this.file[i]; }
			this.file = nextTMP;
		}
		return tmp;
	}
	
	public boolean isFileEmpty() {
		if (this.file.length > 0) { return false; }
		else { return true; }
	}
	
	public boolean remove(ARN elt) {
		ARNFile nextTMP = new ARNFile();
		ARN tmp = this.pop();
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
