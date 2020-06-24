package gabywald.javabio.data.composition;

/**
 * 
 * @author Gabriel Chandesris (2011)
 * @see gabywald.javabio.data.GenBank
 * @see gabywald.javabio.data.Embl
 */
public class Sequence {
	/** 
	 * see 'http://www.ncbi.nlm.nih.gov/Sequin/sequin.hlp.html#SpecifyMolecule'
	 - Genomic DNA: Sequence derived directly from the DNA of an organism. Note: The DNA sequence of an rRNA gene has this molecule type, as does that from a naturally-occurring plasmid.
     - Genomic RNA: Sequence derived directly from the genomic RNA of certain organisms, such as viruses.
     - Precursor RNA: An RNA transcript before it is processed into mRNA, rRNA, tRNA, or other cellular RNA species.
     - mRNA[cDNA]: A cDNA sequence derived from mRNA.
     - Ribosomal RNA: A sequence derived from the RNA in ribosomes. This should only be selected if the RNA itself was isolated and sequenced. If the gene for the ribosomal RNA was sequence, select Genomic DNA.
     - Transfer RNA: A sequence derived from the RNA in a transfer RNA, for example, the sequence of a cDNA derived from tRNA.
     - Other-Genetic: A synthetically derived sequence including cloning vectors and tagged fusion constructs.
     - cRNA: A sequence derived from complementary RNA transcribed from DNA, mainly used for viral submissions.
     - Transcribed RNA: A sequence derived from any transcribed RNA not listed above.
     - Tranfer-messenger RNA: A sequence derived from transfer-messenger RNA, which acts as a tRNA first and then an mRNA that encodes a peptide tag. If the gene for the tmRNA was sequenced, use genomic DNA.
     - ncRNA: A sequence derived from other non-coding RNA not specified above. If the gene for the ncRNA was sequenced, select Genomic DNA. 
	 */
	
	/** Indicates if DNA, RNA, mRNA, aligned... */
	/** private String type; /** private int type; */
	/** The sequence contain itself. */
	private String content;
	// TODO Sequence Class
	
	public Sequence() 
		{ this.content = new String(""); }
	
	public Sequence(String type, String sequence) {
		/** this.type		= type; */
		this.content	= sequence;
	}
	
	public void addInSequence(String part) 
		{ this.content += part; }
	
	public String getContent()		{ return this.content; }
	public String toString()		{ return this.content; }
	
	/**
	 * Format specific output for FASTA Format. 
	 * @return (String)
	 */
	public String toStringFasta()	{ 
		int lengthToTake = 60; /** Standard line length for Fasta. */
		return Sequence.sequenceFormatting(this.content, lengthToTake);
	}
	
	public String toStringEMBL() {
		String toReturn = new String("");
		
		int division		= 10;	/** Ten (10) chars by segment. */
		int segmentsGroup	= 6;	/** Six (6) segments by lines. */
		int segmentsNumber	= (this.content.length() / division) + 1;
		String[] segmentation = new String[segmentsNumber];
		for (int i = 0 ; i < this.content.length() ; i += division) {
			int maxToGet				= Math.min(i+division, this.content.length());
			String segment				= this.content.substring(i, maxToGet);
			segmentation[i/division]	= segment;
		}
		
		String tempSeq = new String("");
		for (int i = 0 ; i < segmentsNumber ; i++) {
			if ( (i != 0) && (i%segmentsGroup == 0) ) {
				int lineIndex	= (i * division );
				String index	= Sequence.completeDataWith(lineIndex, ' ', true, 10); 
				String newLine	= "     "+tempSeq.substring(0, tempSeq.length()-1)+index;
				toReturn		+= newLine+"\n";
				/** System.out.println("\t'"+i+"'\t'"+index+"'\t'"+tempSeq+"'"); */
				/** System.out.println("\t\t'"+newLine+"'"); */
				tempSeq = new String("");
			}
			
			tempSeq += segmentation[i]+" ";
			
			/** For the last line -- if incomplete : most cases !! */
			if (i == (segmentsNumber-1) ) {
				// while (i%segmentsGroup != 0) { i++; }
				int lineIndex	= (i * division);
				int more		= tempSeq.replace(" ", "").length()%10;
				lineIndex 		+= more;
				int toComplete	= 10 + (56 - (tempSeq.length()%10) );
				String index	= Sequence.completeDataWith(lineIndex, ' ', true, toComplete); 
				String newLine	= "     "+tempSeq.substring(0, tempSeq.length()-1)+index;
				toReturn		+= newLine+"\n";
			}
		}
		
		return toReturn;
	}
	
	/**
	 * Format specific output for GeneBank Format. 
	 * @return (String)
	 */
	public String toStringGenBank() {
		String toReturn		= new String("");
		
		int division		= 10;	/** Ten (10) chars by segment. */
		int segmentsGroup	= 6;	/** Six (6) segments by lines. */
		int globalByLine	= (segmentsGroup * division);
		int segmentsNumber	= (this.content.length() / division) + 1;
		String[] segmentation = new String[segmentsNumber];
		for (int i = 0 ; i < this.content.length() ; i += division) {
			int maxToGet				= Math.min(i+division, this.content.length());
			String segment				= this.content.substring(i, maxToGet);
			segmentation[i/division]	= segment;
		}
		
		String tempSeq = new String("");
		for (int i = 0 ; i < segmentsNumber ; i++) {
			if ( (i != 0) && (i%segmentsGroup == 0) ) {
				int lineIndex	= (i * division) - (globalByLine - 1);
				String index	= Sequence.completeDataWith(lineIndex+" ", ' ', true, 10); 
				String newLine	= index + tempSeq.substring(0, tempSeq.length()-1);
				toReturn		+= newLine + "\n";
				/** System.out.println("\t'"+i+"'\t'"+index+"'\t'"+tempSeq+"'"); */
				/** System.out.println("\t\t'"+newLine+"'"); */
				tempSeq = new String("");
			}
			
			/** WARN : Avoiding a bug if encoded sequence is 60-characters long... */
			if (segmentation[i] != null) { tempSeq += segmentation[i]+" "; }
			
			/** For the last line -- if incomplete : most cases !! */
			if ( ( (i != 0) && (segmentation[i] != null) )
					&& (i == (segmentsNumber - 1) ) ) {
				
				/** In order to avoid bugs at beginning of line about base count... */
				int before = i; 
				while (i%segmentsGroup != 0) { i++; }
				if (i == before) { i++; }
				while (i%segmentsGroup != 0) { i++; }
				
				int lineIndex	= (i * division) - (globalByLine - 1);
				String index	= Sequence.completeDataWith(lineIndex+" ", ' ', true, 10); 
				String newLine	= index + tempSeq.substring(0, 
						(tempSeq.length()>0)?tempSeq.length()-1:0);
				toReturn		+= newLine+"\n";
			}
		}
		
		return toReturn;
	}
	
	/**
	 * To cut a sequence along a certain length before passing next line. 
	 * @param whole (String) Complete sequence. 
	 * @param lengthToTake (int) Number of characters from the sequence for each line. 
	 * @return (String)
	 * @see Sequence#sequenceFormatting(String, int, String, int)
	 */
	public static String sequenceFormatting(String whole, int lengthToTake) 
		{ return Sequence.sequenceFormatting(whole, lengthToTake, "", 0); }
	
	/**
	 * A specific formatting which allows a beginning of each line in output and starting after first character. 
	 * @param whole (String) Complete sequence. 
	 * @param lengthToTake (int) Number of characters from the sequence for each line. 
	 * @param starter (String) Element to put at beginning of each line. 
	 * @param startPoint (int) Where to start from whole sequence. 
	 * @return (String)
	 * @see Sequence#sequenceFormatting(String, int)
	 */
	public static String sequenceFormatting(String whole, int lengthToTake, 
											String starter, int startPoint) {
		String toReturn = new String("");
		for (int j = startPoint ; j < whole.length() ; j += lengthToTake) {
			int maxToGet				= Math.min(j+lengthToTake, whole.length());
			String segment				= whole.substring(j, maxToGet);
			toReturn					+= starter+segment
									+((maxToGet < whole.length()-1)?"\n":"")/** +"\n" */;
		}
		return toReturn;
	}
	
	public static String completeDataWith(String data, 
										  char elt, 
										  boolean before, 
										  int maxsTimes) {
		String toReturn = data;
		for (int i = data.length() ; i < maxsTimes  ; i++) {
			if (before)	{ toReturn = elt + toReturn; }
			else		{ toReturn += elt; }
		}
		return toReturn;
	}
	
	public static String completeDataWith(int data, 
										  char elt, 
										  boolean before, 
										  int maxsTimes) 
		{ return Sequence.completeDataWith(data+"", elt, before, maxsTimes); }
}
