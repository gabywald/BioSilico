package gabywald.crypto.data.ioput;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.DirectFormat;
import gabywald.crypto.data.FastaFormat;
import gabywald.crypto.data.composition.Sequence;
import gabywald.crypto.model.ITranslator.TranslatorEnum;

/**
 * Aim of this class is to generate a Fasta file with encrypted data. 
 * <br>Data is encrypted when included (content and path of file, respectively as nucleotidic and proteomic data). 
 * <br>Encryption according to current "genetic encryption". 
 * @author Gabriel Chandesris (2020, 2026)
 */
public class FastaFileCreator extends BiologicalFileCreator {
	
	public FastaFileCreator() 
		{ this("", ""); }

	/**
	 * Constructor with given path and content. 
	 * @param path Path to a file. 
	 * @param content Content of a file. 
	 */
	public FastaFileCreator(String path, String content) {
		super();
		this.addPathAndContent(path, content);
		this.bioFormat		= new FastaFormat();
	}
	
	/**
	 * 
	 * @param bioencoder4file
	 * @param bioencoder4path
	 * @param which
	 */
	public FastaFileCreator(int bioencoder4file, int bioencoder4path, TranslatorEnum which) {
		super(bioencoder4file, bioencoder4path, which);
		this.bioFormat = new DirectFormat();
	}

	@Override
	protected void initialize() {
		
		String identification = BiologicalUtils.generateIdentifier();
		this.bioFormat.setIdentification(identification);
		this.bioFormat.setAccession(identification);
		
		int basePairNumber = 0;
		for (int i = 0 ; i < this.getEncodedCont().size() ; i++) 
			{ basePairNumber += this.getEncodedCont().get(i).length(); }
		this.bioFormat.setBasePairNumber(""+basePairNumber);
		
		// TODO path ?
		StringBuilder sbCommentToRecord	= new StringBuilder();
		this.getEncodedPath().stream().forEach( str -> sbCommentToRecord.append(str));
		this.bioFormat.setComment(sbCommentToRecord.toString());
		
		StringBuilder sbSequenceToRecord	= new StringBuilder();
		this.getEncodedCont().stream().forEach( str -> sbSequenceToRecord.append(str));
		this.bioFormat.setSequence(new Sequence("", sbSequenceToRecord.toString()));

	}

}
