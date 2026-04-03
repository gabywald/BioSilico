package gabywald.crypto.data.ioput;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.DirectFormat;
import gabywald.crypto.data.composition.Sequence;
import gabywald.crypto.model.ITranslator;

public class DirectFileCreator extends BiologicalFileCreator {

	/**
	 * 
	 * @param forFiles
	 * @param forPathes
	 * @param which
	 */
	public DirectFileCreator(ITranslator forFiles, ITranslator forPathes, ITranslator.TranslatorEnum which) {
		super(forFiles, forPathes, which);
		this.bioFormat = new DirectFormat();
	}
	
	/**
	 * 
	 * @param bioencoder4file
	 * @param bioencoder4path
	 * @param which
	 */
	public DirectFileCreator(int bioencoder4file, int bioencoder4path, ITranslator.TranslatorEnum which) {
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
