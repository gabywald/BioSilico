package gabywald.cellmodel.model;

import gabywald.cellmodel.structures.ProteinFile;
import gabywald.cellmodel.structures.VesiculeListe;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public class Vesicule implements ContentProtein {
	private ProteinFile tempa;
	private int type;
	
	/**
	 * Level treshold of vesicules building. 
	 * @see Cytoplasme#transport()
	 * @see AppareilDeGolgi#transport()
	 * @see Vesicule#TYPE_CYTOPLASME
	 * @see Vesicule#TYPE_EXOCYTOSE
	 * @see Vesicule#TYPE_GOLGI
	 * @see Vesicule#TYPE_MEMBRANE
	 * @see Vesicule#HIGHER_TYPE
	 */
	public static final int VESICULE_LEVEL	= 20;
	public static final int TYPE_EXOCYTOSE	= 0;
	public static final int TYPE_GOLGI		= 1;
	public static final int TYPE_CYTOPLASME	= 2;
	public static final int TYPE_MEMBRANE	= 3;
	public static final int HIGHER_TYPE		= (Vesicule.TYPE_MEMBRANE+1);
	
	public Vesicule() 
		{ this.tempa = new ProteinFile();this.type = 0; }
	
	public Vesicule(int type) 
		{ this.tempa = new ProteinFile();this.type = type; } 
	
	public Vesicule(ProteinFile tmp) 
		{ this.tempa = tmp;this.type = 0; }
	
	public Vesicule(ProteinFile tmp,int type) 
		{ this.tempa = tmp;this.type = type; }

	public boolean addProtein(Protein elt) {
		this.tempa.push(elt);
		return true;
	}

	public boolean remProtein(Protein elt) 
		{ return this.tempa.remove(elt); }
	
	public boolean isEmpty() { return (this.tempa.length() == 0); }
	public int length() { return this.tempa.length(); }
	public int getType() { return this.type; }
	public Protein popProtein() { return this.tempa.pop(); }
	
	public VesiculeListe transport() 
		{ return new VesiculeListe(); }
	
	public void fusion  (Cytoplasme cytoplasme
						,AppareilDeGolgi golgi
						,Exocytose exterieur
						,Membrane membrane) {
		switch(this.type) {
		case(Vesicule.TYPE_GOLGI):this.targetTo(golgi);break;
		case(Vesicule.TYPE_CYTOPLASME):this.targetTo(cytoplasme);break;
		case(Vesicule.TYPE_MEMBRANE):this.targetTo(membrane);break;
		default:this.targetTo(exterieur);
		}
	}
	
	private void targetTo(AppareilDeGolgi golgi) {
		Protein tmp = this.tempa.pop();
		while(tmp != null) {
			golgi.addProtein(tmp);
			tmp = this.tempa.pop();
		}
	}
	
	private void targetTo(Cytoplasme cytoplasme) {
		Protein tmp = this.tempa.pop();
		while(tmp != null) {
			cytoplasme.addProtein(tmp);
			tmp = this.tempa.pop();
		}
	}
	
	private void targetTo(Exocytose exterieur) {
		Protein tmp = this.tempa.pop();
		while(tmp != null) {
			exterieur.addProtein(tmp);
			tmp = this.tempa.pop();
		}
	}
	
	private void targetTo(Membrane membrane) {
		Protein tmp = this.tempa.pop();
		while(tmp != null) {
			membrane.addProtein(tmp);
			tmp = this.tempa.pop();
		}
	}
	
}
