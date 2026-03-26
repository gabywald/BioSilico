package gabywald.crypto.data.ioput;

import gabywald.crypto.data.BiologicalFormat;

/**
 * 
 * @author Gabriel Chandesris (2020, 2022, 2026)
 */
public abstract class BiologicalFileCreator extends AFileCryptoCreator {
	
	protected BiologicalFormat bioFormat;
	
	/**
	 * Constructor with given path and content. 
	 * @param path
	 * @param content
	 */
	protected BiologicalFileCreator(String path, String content) 
		{ super(path, content); }
	
	/** Encryption takes place here ! */
	protected abstract void initialize();
	
	public String getFullEncryption() {
		this.initialize();
		return this.bioFormat.toString();
	}
	
}
