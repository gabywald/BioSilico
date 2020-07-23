package gabywald.cellmodel.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Vesicule implements ContentProtein {
	private Queue<Protein> proteins;
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
		{ this(new LinkedList<Protein>(), 0); }
	
	public Vesicule(int type) 
		{ this(new LinkedList<Protein>(), type);  } 
	
	public Vesicule(LinkedList<Protein> tmp) 
		{ this(tmp, 0); }
	
	public Vesicule(LinkedList<Protein> tmp, int type) {
		this.proteins	= tmp;
		this.type		= type;
	}

	public boolean addProtein(Protein elt) {
		this.proteins.add(elt);
		return true;
	}

	public boolean remProtein(Protein elt) 
		{ return this.proteins.remove(elt); }
	
	public boolean isEmpty()	{ return (this.proteins.size() == 0); }
	public int length()			{ return this.proteins.size(); }
	public int getType()		{ return this.type; }
	public Protein popProtein()	{ return this.proteins.poll(); }
	
	public List<Vesicule> transport() 
		{ return new ArrayList<Vesicule>(); }
	
	public void fusion(Cytoplasme cytoplasme, AppareilDeGolgi golgi, Exocytose exterieur, Membrane membrane) {
		switch(this.type) {
		case(Vesicule.TYPE_GOLGI):this.targetTo(golgi);break;
		case(Vesicule.TYPE_CYTOPLASME):this.targetTo(cytoplasme);break;
		case(Vesicule.TYPE_MEMBRANE):this.targetTo(membrane);break;
		default:this.targetTo(exterieur);
		}
	}
	
	private void targetTo(AppareilDeGolgi golgi) {
		Protein tmp = this.proteins.poll();
		while (tmp != null) {
			golgi.addProtein(tmp);
			tmp = this.proteins.poll();
		}
	}
	
	private void targetTo(Cytoplasme cytoplasme) {
		Protein tmp = this.proteins.poll();
		while (tmp != null) {
			cytoplasme.addProtein(tmp);
			tmp = this.proteins.poll();
		}
	}
	
	private void targetTo(Exocytose exterieur) {
		Protein tmp = this.proteins.poll();
		while (tmp != null) {
			exterieur.addProtein(tmp);
			tmp = this.proteins.poll();
		}
	}
	
	private void targetTo(Membrane membrane) {
		Protein tmp = this.proteins.poll();
		while (tmp != null) {
			membrane.addProtein(tmp);
			tmp = this.proteins.poll();
		}
	}
	
}
