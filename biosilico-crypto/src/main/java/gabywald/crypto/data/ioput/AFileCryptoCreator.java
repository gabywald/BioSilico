package gabywald.crypto.data.ioput;

import java.util.ArrayList;
import java.util.List;

import gabywald.crypto.model.ITranslator;

/**
 * Main Implementation for {@link IFileCryptoCreator}, some specific elements have to be implemented in inheritent classes !
 * @author Gabriel Chandesris (2026)
 */
public abstract class AFileCryptoCreator implements IFileCryptoCreator {
	
	private List<String> encodedPath;
	private List<String> encodedContent;
	
	private ITranslator forFileContent = null;
	private ITranslator forPathDirName = null;
	private ITranslator.TranslatorEnum whichTR = null;
	
	/**
	 * 
	 * @param forFiles
	 * @param forPathes
	 * @param which
	 */
	protected AFileCryptoCreator(ITranslator forFiles, ITranslator forPathes, ITranslator.TranslatorEnum which) {
		this.encodedPath	= new ArrayList<String>();
		this.encodedContent	= new ArrayList<String>();
		this.forFileContent = forFiles;
		this.forPathDirName = forPathes;
		this.whichTR		= which;
	}
	
	@Override
	public void addPathAndContent(String path, String content) {
		// Do nothing if both {path,content} are empty. 
		if ( (path == null) && (content == null)) { return; }
		if ( (path.length() == 0) && (content.length() == 0)) { return; }
		// all other cases. 
		this.encodedPath.add( (path == null) || (path.equals("")) ? "" : this.forPathDirName.encode(path, this.whichTR) );
		this.encodedContent.add( (content == null) || (content.equals("")) ? "" : this.forFileContent.encode(content, this.whichTR));
	}

	@Override
	public void setPathAndContent(String path, String content) {
		// all other cases. 
		this.emptyPathAndContent();
		this.addPathAndContent(path, content);
	}
	
	@Override
	public void emptyPathAndContent() {
		this.encodedPath	= new ArrayList<String>();
		this.encodedContent	= new ArrayList<String>();
	}
	
	@Override
	public List<String> getEncodedPath() { return this.encodedPath; }
	
	@Override
	public List<String> getEncodedCont() { return this.encodedContent; }

	@Override
	public ITranslator getForFileContent() { return this.forFileContent; }

	@Override
	public ITranslator getForPathDirName() { return this.forPathDirName; }

	@Override
	public ITranslator.TranslatorEnum getWhichTR() { return this.whichTR; }
	
}
