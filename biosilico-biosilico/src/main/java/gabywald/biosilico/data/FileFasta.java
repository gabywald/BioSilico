package gabywald.biosilico.data;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.utils.Base;
import gabywald.biosilico.utils.Sequence;
import gabywald.global.data.File;

/**
 * This class for FASTA sequences or ClustalW alignment in FASTA Format. 
 * @author St&eacute;fan Engelen (2006)
 * @author Gabriel Chandesris (2008-2010)
 */
@SuppressWarnings("serial")
public class FileFasta extends FileBiological {
	/** List of sequences contained in the fasta file. */
	private List<Sequence> liste;
	
	/**
	 * Constructor with a given String of alignment. 
	 * @param alignement String
	 * @see File#File(String, String)
	 */
	public FileFasta(String alignement) {
		super("fasta","alignement");
		if (FileFasta.isFastaAlignment(alignement)) {
			this.setValid(true);
			String[] tabAlign = alignement.split("\n");
			alignement = "";
			/** sequences to uppercase */
			for (int i = 0 ; i < tabAlign.length ; i++) {
				if (tabAlign[i].charAt(0) != '>') 
					{ alignement += tabAlign[i].toUpperCase(); } 
				else { 
					if (i > 0) { alignement += "\n"; }
					alignement += tabAlign[i]+"\n";
				}
			}
		} else { this.setValid(false); }
		/** tested as valid : adding in liste and champs... */
		if (this.isValid()) {
			/** queuing the Sequence's */
			this.liste = new ArrayList<Sequence>();
			String[] tempo = alignement.split("\n");
			String nom = "", sequence = "";
			for (int i = 0 ; i < tempo.length ; i++) {
				super.addToChamps(tempo[i]);
				if (i%2 == 0) { nom = tempo[i]; }
				else { 							
					sequence = tempo[i]; 
					this.liste.add(new Sequence(nom, sequence));
					nom			= "";
					sequence	= "";
				}
			}
		}
	}
	
	/**
	 * Constructor with a given name and a String of alignment. 
	 * <br>Useful constructor for recording FASTA file. 
	 * @param alignement String
	 * @see File#File(String, String)
	 * @see File#printFile()
	 */
	public FileFasta(String nom,String alignement) {
		super("fasta",nom);
		if (FileFasta.isFastaAlignment(alignement)) {
			this.setValid(true);
			boolean test = true;
			if (alignement.equals("")) { test = false; }
			String[] tabAlign = alignement.split("\n");
			alignement = "";
			for (int i = 0 ; (i < tabAlign.length) && test ; i++) {
				if (tabAlign[i].charAt(0) != '>') 
					{ alignement += tabAlign[i].toUpperCase(); } 
				else { 
					if (i > 0) { alignement += "\n"; }
					alignement += tabAlign[i]+"\n";
				}
			}
		} else { this.setValid(false); }
		if (this.isValid()) {
			/** queuing the Sequence's */
			this.liste = new ArrayList<Sequence>();
			String[] tempo = alignement.split("\n");
			String name = "",sequense = "";
			for (int i = 0 ; i < tempo.length ; i++) {
				if (i%2 == 0) { name = tempo[i]; } 
				else { 							
					sequense = tempo[i]; 
					this.liste.add(new Sequence(name,sequense));
					name		= "";
					sequense	= "";
				}
				super.addToChamps(tempo[i]);
			}
		}
	}
	
	/**
	 * Constructor with a given SequenceListe. 
	 * @param liste (Sequence List)
	 */
	public FileFasta(List<Sequence> liste) {
		super("fasta","alignement");
		this.liste = liste;
		this.liste.stream().forEach( seq -> {
			super.addToChamps(seq.getNom());
			super.addToChamps(seq.getSequence());
		});
		this.setValid(true);
	}
	
	public List<Sequence> getListe()	{ return this.liste; }
	public int lengthListe()			{ return this.liste.size(); }
	
	/**
	 * To get the whole alignment of Sequence's.  
	 * @return String (as original file). 
	 */
	public String getSequencesToString() {
		StringBuilder sequences = new StringBuilder();
		this.liste.stream().forEach( seq -> {
			sequences.append(seq.getNom()).append("\n");
			sequences.append(seq.getSequence()).append("\n");
		});
		return sequences.toString();
	}


	
    /**
     * To get a sequence with given name. <br/>Tab result contain cible 
     * Sequence at [0] and other Sequence's in [1].  
     * @param nom (String)
     * @return (SequenceListe)
     */
	public List<Sequence> getCible(String nom) {
		List<Sequence> cible = new ArrayList<Sequence>();
		List<Sequence> other = new ArrayList<Sequence>();
		
		this.liste.stream().forEach(seq -> {
			if (nom.equals(seq.getNom())) 
				{ cible.add( seq ); }
			else 
				{ other.add( seq ); }
		});
		
		return cible;
	}
	
	
	/**
	 * To know if the alignment is a FASTA alignment. <br>
	 * @param alignement (String)
	 * @return (boolean)
	 */
	public static boolean isFastaAlignment(String alignement) {
		if (alignement.equals("")) { return false; }
		String[] tabAlign = alignement.split("\n");
		/** for checking that we have couple (name, sequence) */
		boolean hasname = false;
		boolean hasseq = false;
		for (int i = 0 ; i < tabAlign.length ; i++) {
			if (!tabAlign[i].equals("")) {
				if (tabAlign[i].charAt(0) != '>') {
					tabAlign[i] = tabAlign[i].toUpperCase();
					if (!FileFasta.isNucleicSequence(tabAlign[i])) 
						 { return false; }
					else { hasseq = true; }
					/** something but no name... */
					if (!hasname) { return false; }
				} else {
					if ( (i > 0) && ( (hasname) && (!hasseq) ) ) 
						{ return false; }
					hasname = true; /** at least one name */
					hasseq = false;
				}
			} else { 
				/** if name of sequence without sequence */
				if ( (hasname) && (!hasseq) ) { return false; }
			}
		}
		/** if name of sequence without sequence */
		if ( (hasname) && (!hasseq) ) { return false; }
		return true;
	}
	
	/**
	 * <i><b>Take care for treatment</b> : aligned sequences are putted on the same line
	 * in the way to ensure format is [names on odd lines and alignes sequences in 
	 * one line (even lines)]
	 * <br>Check if it is a fasta alignment before treatment.  
	 * @param alignement (String)
	 * @return (String)
	 * @see FileFasta#isFastaAlignment(String)
	 */
	public static String ensureFastaAlignment(String alignement) {
		if (!FileFasta.isFastaAlignment(alignement)) { return alignement; }
		else  {
			String[] tabAlign = alignement.split("\n");
			/** This String to ensure that aligned sequences are on 
			 * the same line after return boolean */
			String savealignement = "";
			String tmpsequence = "";
			boolean isFirst = true;
			for (int i = 0 ; i < tabAlign.length ; i++) {
				if (!tabAlign[i].equals("")) {
					if (tabAlign[i].charAt(0) != '>') {
						tmpsequence += tabAlign[i];
					} else {
						/** name of sequence */
						if (isFirst)  { savealignement = tabAlign[i]+"\n";isFirst = false; }
						else { savealignement += tmpsequence+"\n"+tabAlign[i]+"\n"; }
						tmpsequence = "";
					}
				}
			}
			savealignement += tmpsequence;
			/** changing the inputted argument */
			return savealignement;
		}
	}
	
	/**
	 * To know if a sequence is a nucleic sequence. 
	 * @param sequence String
	 * @return boolean
	 */
	private static boolean isNucleicSequence(String sequence) {
		int length = 0;
		/** maybe \n or \r\n or \r at end : ignore last char if not good */
		// String avoidcharsa = "\n|\r|\r\n|\n\r";
		// String avoidcharsb = "[\n\r\r\n\n\r]$";
		// if (sequence.matches(avoidcharsa)) { Terminal.ecrireStringln("****************"); }
		// if (sequence.matches(avoidcharsb)) { Terminal.ecrireStringln("----------------"); }
		if (Base.isBaseOrGap(sequence.charAt(sequence.length()-1))) 
			{ length = sequence.length()-1; } /** from direct sequence */
		else { length = sequence.length()-1; } /** from a split table */
		for (int i = 0 ; i < length ; i++) {
			if (!Base.isBaseOrGap(sequence.charAt(i)) )
				{ return false; }
		}
		return true;
	}
	
}
