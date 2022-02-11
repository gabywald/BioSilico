package gabywald.biosilico.data;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.utils.BaseGattaca;
import gabywald.biosilico.utils.Sequence;
import gabywald.global.data.File;

/**
 * This class for Genomes in gattaca format (genes line by line written with A,C,G,T). 
 * @author Gabriel Chandesris (2009-2010, 2020, 2022)
 */
@SuppressWarnings("serial")
public class FileGattaca extends FileBiological {
	
	private List<Sequence> liste;

	/**
	 * Constructor with given name and content. 
	 * @param name (String)
	 * @param content (String)
	 * @see File#File(String, String)
	 */
	public FileGattaca(String name, String content) {
		super("gattaca", name + ".gat");
		if (FileGattaca.isGattaca(content)) {
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

	public static boolean isGattaca(String contenu) {
		if (contenu.equals("")) { return false; }
		String tabContent[] = contenu.split("\n");
		for (int i = 0 ; i < tabContent.length ; i++) {			
			if (!tabContent[i].equals("")) {
				tabContent[i] = tabContent[i].toUpperCase();
				if (!FileGattaca.isGattacaGene(tabContent[i])) 
					 { return false; }
			}
		}
		return true;
	}
	
	private static boolean isGattacaGene(String gene) {
		int length = 0;
		/** maybe \n or \r\n or \r at end : ignore last char if not good */
		/** 
		String avoidcharsa = "\n|\r|\r\n|\n\r";
		String avoidcharsb = "[\n\r\r\n\n\r]$";
		if (gene.matches(avoidcharsa)) 
			{ Terminal.ecrireStringln("****************"); }
		if (gene.matches(avoidcharsb)) 
			{ Terminal.ecrireStringln("----------------"); }
		*/
		if (BaseGattaca.isBaseOrGap(gene.charAt(gene.length()-1))) 
			{ length = gene.length()-1; }	/** from direct sequence */
		else { length = gene.length()-2; }	/** from a split table */
		for (int i = 0 ; i < length ; i++) {
			if (!BaseGattaca.isBaseOrGap(gene.charAt(i)) )
				{ return false; }
		}
		return true;
	}
	
	public List<Sequence> getListe() { return this.liste; }
	
	public int lengthListe() { return this.liste.size(); }
}
