package gabywald.crypto.data.ioput;

import java.util.ArrayList;
import java.util.List;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.model.GeneticTranslator;

public abstract class AFileCryptoCreator implements IFileCryptoCreator {
	
	protected GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
	protected GeneticTranslator forPathDirName = BiologicalUtils.getGenericCrypto(1);
	
	private List<String> encodedPath;
	private List<String> encodedContent;
	
	protected AFileCryptoCreator() {
		this.encodedPath	= new ArrayList<String>();
		this.encodedContent	= new ArrayList<String>();
	}
	
	protected AFileCryptoCreator(String path, String content) {
		this();
		this.addPathAndContent(path, content);
	}

	@Override
	public void addPathAndContent(String path, String content) {
		// this.encodedPath.add(path);
		// this.encodedContent.add(content);
		this.encodedPath.add( path.equals("") ? "" : this.forPathDirName.encode(path, 1) );
		this.encodedContent.add( content.equals("") ? "" : this.forFileContent.encode(content, 1));
	}

	@Override
	public void setPathAndContent(String path, String content) {
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
	
}
