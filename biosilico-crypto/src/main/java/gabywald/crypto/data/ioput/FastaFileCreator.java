package gabywald.crypto.data.ioput;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.FastaFormat;
import gabywald.crypto.data.composition.Sequence;

/**
 * Aim of this class is to generate a Fasta file with encrypted data. 
 * <br>Data is encrypted when included (content and path of file, respectively as nucleotidic and proteomic data). 
 * <br>Encryption according to current "genetic encryption". 
 * @author Gabriel Chandesris (2020)
 */
public class FastaFileCreator extends BiologicalFileCreator {

	/**
	 * Constructor with given path and content. 
	 * @param path Path to a file. 
	 * @param content Content of a file. 
	 */
	public FastaFileCreator(String path, String content) {
		super(path, content);
		this.bioFormat		= new FastaFormat();
	}

	@Override
	protected void initialize() {
		
		String identification = BiologicalUtils.generateIdentifier();
		this.bioFormat.setIdentification(identification);
		this.bioFormat.setAccession(identification);
		
		int basePairNumber = 0;
		for (int i = 0 ; i < this.encodedContent.size() ; i++) 
			{ basePairNumber += this.encodedContent.get(i).length(); }
		this.bioFormat.setBasePairNumber(""+basePairNumber);
		
		String sequenceToRecord	= new String("");
		for (int i = 0 ; i < this.encodedContent.size() ; i++) { 
			/** Append... */
			sequenceToRecord += this.encodedContent.get(i);
		}
		this.bioFormat.setSequence(new Sequence("", sequenceToRecord));

	}

}
