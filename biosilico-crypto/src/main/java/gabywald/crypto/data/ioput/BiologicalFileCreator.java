package gabywald.crypto.data.ioput;

import gabywald.crypto.data.BiologicalFormat;
import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.model.ITranslator;

/**
 * Specific subclass of {@link AFileCryptoCreator}, to make similar obsfuscation as Biological Data Files. 
 * <br/>Intermediate (abstract class) due to different formats. 
 * @author Gabriel Chandesris (2020, 2022, 2026)
 * @see {@link FastaFileCreator}
 * @see {@link EmblFileCreator}
 * @see {@link GenBankFileCreator}
 */
public abstract class BiologicalFileCreator extends AFileCryptoCreator {
	
	public enum BiologicalFileTypes { fasta, embl, genbank }
	
	protected BiologicalFormat bioFormat;
	
	protected BiologicalFileCreator() 
		{ this( 0 , 1, ITranslator.TranslatorEnum.simple ); }
	
	protected BiologicalFileCreator(ITranslator.TranslatorEnum which) 
		{ this( 0 , 1, which ); }
	
	protected BiologicalFileCreator(int bioencoder4file, int bioencoder4path, ITranslator.TranslatorEnum which) { 
		super(	BiologicalUtils.getGenericCrypto( bioencoder4file ), 
				BiologicalUtils.getGenericCrypto( bioencoder4path ), 
				which);
	}
	
	/** Encryption takes place here ! */
	protected abstract void initialize();
	
	/**
	 * 
	 * @return (String) Full encryptued and obsfuscated sequence(s). 
	 * @see {@link BiologicalFileCreator#initialize()}
	 * @see {@link BiologicalFileCreator#bioencoder_forFileContent}
	 * @see {@link BiologicalFileCreator#bioencoder_forPathDirName}
	 */
	public String getFullEncryption() {
		this.initialize();
		return this.bioFormat.toString();
	}

//	public GeneticTranslator getBioencoderForFileContent() 
//		{ return (this.getBioencoderForFileContent() instanceof GeneticTranslator) ? 
//				(GeneticTranslator)this.getBioencoderForFileContent() : null; }
//
//	public GeneticTranslator getBioencoderForPathDirName() 
//		{ return (this.getBioencoderForPathDirName() instanceof GeneticTranslator) ? 
//				(GeneticTranslator)this.getBioencoderForPathDirName() : null; }
	
}
