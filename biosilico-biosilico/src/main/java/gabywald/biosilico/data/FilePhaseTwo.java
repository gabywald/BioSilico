package gabywald.biosilico.data;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.utils.BasePhaseTwo;
import gabywald.biosilico.utils.Sequence;
import gabywald.global.data.File;

/**
 * This class for Genomes in Phase II format (genes line by line written with U,B,V,P). 
 * @author Gabriel Chandesris (2009, 2020)
 */
@SuppressWarnings("serial")
public class FilePhaseTwo extends FileBiological {
	/** To avoid Warning. */
	// private static final long serialVersionUID = 525L;
	
	private List<Sequence> liste;

	/**
	 * Constructor with given content. 
	 * @param content (String)
	 * @see Fichier#Fichier(String, String)
	 */
/**	public FichierPhaseTwo(String content) {
		super("phase2","no_name");
		if (FichierPhaseTwo.isPhaseTwo(content)) {
			this.setValid(true);
			String tabContent[] = content.split("\n");
			content = "";
			for (int i = 0 ; i < tabContent.length ; i++) 
				{ content += tabContent[i].toUpperCase()+"\n"; } 
		} else { this.setValid(false); }
		
		if (this.isValid()) {
			this.liste = new SequenceListe();
			String[] tempo = content.split("\n");
			String nom = "",sequence = "";
			for (int i = 0 ; i < tempo.length ; i++) {
				super.addToChamps(tempo[i]);	
				nom = "Gene "+i;
				sequence = tempo[i]; 
				this.liste.addSequence(new Sequence(nom,sequence));
			}
		}
	}*/
	
	/**
	 * Constructor with given name and content. 
	 * @param name (String)
	 * @param content (String)
	 * @see File#File(String, String)
	 */
	public FilePhaseTwo(String name,String content) {
		super("phase2",name+".ph2");
		if (FilePhaseTwo.isPhaseTwo(content)) {
			this.setValid(true);
			String tabContent[] = content.split("\n");
			content = "";
			for (int i = 0 ; i < tabContent.length ; i++) 
				{ content += tabContent[i].toUpperCase()+"\n"; } 
		} else { this.setValid(false); }
		
		if (this.isValid()) {
			this.liste = new ArrayList<Sequence>();
			String[] tempo = content.split("\n");
			String nom = "",sequence = "";
			for (int i = 0 ; i < tempo.length ; i++) {
				super.addToChamps(tempo[i]);	
				nom = "Gene "+i;
				sequence = tempo[i]; 
				this.liste.add(new Sequence(nom,sequence));
			}
		}
	}

	
	public static boolean isPhaseTwo(String contenu) {
		if (contenu.equals("")) { return false; }
		String tabContent[] = contenu.split("\n");
		for (int i = 0 ; i < tabContent.length ; i++) {			
			if (!tabContent[i].equals("")) {
				tabContent[i] = tabContent[i].toUpperCase();
				if (!FilePhaseTwo.isPhaseTwoContent(tabContent[i])) 
					 { return false; }
			}
		}
		return true;
	}
	
	private static boolean isPhaseTwoContent(String sequence) {
		int length = 0;
		/** maybe \n or \r\n or \r at end : ignore last char if not good */
		/** 
		String avoidcharsa = "\n|\r|\r\n|\n\r";
		String avoidcharsb = "[\n\r\r\n\n\r]$";
		if (sequence.matches(avoidcharsa)) 
			{ Terminal.ecrireStringln("****************"); }
		if (sequence.matches(avoidcharsb)) 
			{ Terminal.ecrireStringln("----------------"); }
		*/
		if (BasePhaseTwo.isBaseOrGap(sequence.charAt(sequence.length()-1))) 
			{ length = sequence.length()-1; } /** from direct sequence */
		else { length = sequence.length()-2; } /** from a split table */
		for (int i = 0 ; i < length ; i++) {
			if (!BasePhaseTwo.isBaseOrGap(sequence.charAt(i)) )
				{ return false; }
		}
		return true;
	}
	
	public List<Sequence> getListe()	{ return this.liste; }
	
	public int lengthListe()			{ return this.liste.size(); }
}
