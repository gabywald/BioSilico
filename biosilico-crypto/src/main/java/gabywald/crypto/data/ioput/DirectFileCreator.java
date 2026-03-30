package gabywald.crypto.data.ioput;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.DirectFormat;
import gabywald.crypto.data.composition.Sequence;
import gabywald.crypto.model.ITranslator.TranslatorEnum;

public class DirectFileCreator extends BiologicalFileCreator {

	public DirectFileCreator() 
		{ this("", ""); }
	
	/**
	 * Constructor with given path and content. 
	 * @param path Path to a file. 
	 * @param content Content of a file. 
	 */
	public DirectFileCreator(String path, String content) {
		super();
		this.addPathAndContent(path, content);
		this.bioFormat		= new DirectFormat();
	}
	
	/**
	 * 
	 * @param bioencoder4file
	 * @param bioencoder4path
	 * @param which
	 */
	public DirectFileCreator(int bioencoder4file, int bioencoder4path, TranslatorEnum which) {
		super(bioencoder4file, bioencoder4path, which);
		this.bioFormat = new DirectFormat();
	}

	@Override
	protected void initialize() {
		this.bioFormat.setAccession(BiologicalUtils.generateIdentifier());
		
		StringBuilder sbPathes = new StringBuilder();
		this.getEncodedPath().stream().forEach( seq -> sbPathes.append(seq + DirectFormat.MINOR_CUTTER) );
		this.bioFormat.setComment(sbPathes.toString());
		
		StringBuilder sbSequenceToRecord = new StringBuilder();
		this.getEncodedCont().stream().forEach( seq -> sbSequenceToRecord.append(seq + DirectFormat.MINOR_CUTTER) );
		this.bioFormat.setSequence(new Sequence("", sbSequenceToRecord.toString()));
	}

}
