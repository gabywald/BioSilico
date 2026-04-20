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
	
	protected BiologicalFileCreator(int bioencoder4file, int bioencoder4path, ITranslator.TranslatorEnum which) { 
		super(	BiologicalUtils.getGenericCrypto( bioencoder4file ), 
				BiologicalUtils.getGenericCrypto( bioencoder4path ), 
				which );
	}
	
	protected BiologicalFileCreator(ITranslator forFiles, ITranslator forPathes, ITranslator.TranslatorEnum which) 
		{ super( forFiles, forPathes, which ); }
	
	/** Encryption takes place here ! */
	protected abstract void initialize();
	
	/**
	 * 
	 * @return (String) Full encryptued and obsfuscated sequence(s). 
	 * @see {@link BiologicalFileCreator#initialize()}
	 * @see {@link BiologicalFormat#toString()}
	 */
	public String getFullEncryption() {
		this.initialize();
		return this.bioFormat.toString();
	}

}
