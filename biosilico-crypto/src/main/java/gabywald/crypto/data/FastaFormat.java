package gabywald.crypto.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <br>See <a href="http://en.wikipedia.org/wiki/FASTA_format">FASTA FOrmat (Wikipedia EN)</a>. 
 * <br>See also <a href="http://www.ebi.ac.uk/2can/tutorials/formats.html#fasta">Fasta Format (EBI)</a>
 * @author Gabriel Chandesris (2011)
 */
public class FastaFormat extends BiologicalFormat {
	// TODO Fasta : precise content on first line at '>'
	/** Possibles forms of header
 GenBank                           gi|gi-number|gb|accession|locus
 EMBL Data Library                 gi|gi-number|emb|accession|locus
 DDBJ, DNA Database of Japan       gi|gi-number|dbj|accession|locus
 NBRF PIR                          pir||entry
 Protein Research Foundation       prf||name
 SWISS-PROT                        sp|accession|name
 Brookhaven Protein Data Bank (1)  pdb|entry|chain
 Brookhaven Protein Data Bank (2)  entry:chain|PDBID|CHAIN|SEQUENCE
 Patents                           pat|country|number
 GenInfo Backbone Id               bbs|number
 General database identifier       gnl|database|identifier
 NCBI Reference Sequence           ref|accession|locus
 Local Sequence identifier         lcl|identifier
	 */
	
	/** Files Extensions
fasta 	generic fasta 	Any generic fasta file. Other extensions can be fa, seq, fsa
fna 	fasta nucleic acid 	For coding regions of a specific genome, use ffn, but otherwise fna is useful for generically specifying nucleic acids.
ffn 	FASTA nucleotide coding regions 	Contains coding regions for a genome.
faa 	fasta amino acid 	Contains amino acids. A multiple protein fasta file can have the more specific extension mpfa.
frn 	FASTA non-coding RNA 	Contains non-coding RNA regions for a genome, in DNA alphabet e.g. tRNA, rRNA
	 */
	
	/** Explicit call to "default" super-constructor. */
	public FastaFormat() { super(); }
	
	public String toString() {
		String toReturn = new String("");
		/**
		Term 	Entry Name 	Molecule Type 	Gene Name 	Sequence Length
		e.g. 	FOSB_MOUSE 	Protein 		fosB 		338 bp
		 */
		toReturn += ">"+this.someDatas[1]+"|"+this.someDatas[0]
		           +"|"+this.locusData[0]+"|"+
		           ((this.locusData[1] != null)?this.locusData[1]+" bp":"");
		toReturn += this.origin.toStringFasta();
		return toReturn;
	}
	
	public static List<FastaFormat> fromString(String content) {
		String[] cont			= content.split("\n");
		List<FastaFormat> toReturn	= new ArrayList<FastaFormat>();
		
		FastaFormat data				= new FastaFormat();
		
		for (int i = 0 ; i < cont.length ; i++) {
			if (cont[i].startsWith(">")) {
				if (i != 0)	{ toReturn.add(data); }
				data = new FastaFormat();
				String[] contItable = cont[i].split("[ |]");
				data.someDatas[1]	= contItable[0];
				data.someDatas[0]	= contItable[1];
				data.locusData[0]	= contItable[2];
				if (contItable.length > 3) {
					data.locusData[1]	= (contItable[3].endsWith("bp")?
							contItable[3].split(" ")[0]:contItable[3]);
				}
			} else 
				{ data.origin.addInSequence(cont[i].replace("\n", "")); }
		}
	
		return toReturn;
	}
}
