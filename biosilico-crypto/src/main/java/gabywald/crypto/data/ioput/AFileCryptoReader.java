package gabywald.crypto.data.ioput;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public abstract class AFileCryptoReader implements IFileCryptoReader {

	protected StringBuilder sbDecodedPath;
	protected StringBuilder sbDecodedContent;
	
	private IFileCryptoCreator companion = null;
	
	/**
	 * 
	 * @param forFiles
	 * @param forPathes
	 * @param which
	 */
	protected AFileCryptoReader(IFileCryptoCreator ifcc) { this.companion = ifcc; }
	
	@Override
	public IFileCryptoCreator getCompanion() { return this.companion; }
	
	@Override
	public String getDecodedPath()		{ return (this.sbDecodedPath == null) ? null : this.sbDecodedPath.toString(); }
	@Override
	public String getDecodedContent()	{ return (this.sbDecodedContent == null) ? null : this.sbDecodedContent.toString(); }
	
}
