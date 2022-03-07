package gabywald.crypto.data.ioput;

import java.util.ArrayList;
import java.util.List;

import gabywald.crypto.data.BiologicalFormat;

/**
 * 
 * @author Gabriel Chandesris (2020, 2022)
 */
public abstract class BiologicalFileCreator {
	
	protected BiologicalFormat bioFormat;
	
	protected List<String> encodedPath		= new ArrayList<String>();
	protected List<String> encodedContent	= new ArrayList<String>();
	
	/**
	 * Constructor with given path and content. 
	 * @param path
	 * @param content
	 */
	protected BiologicalFileCreator(String path, String content) {
		if ( (path != null) && (! path.equals("")) ) 
			{ this.setPath(path); }
		if ( (content != null) && (! content.equals("")) ) 
			{ this.setContent(content); }
	}
	
	/** Encryption takes place here ! */
	protected abstract void initialize();
	
	public String getFullEncryption() {
		this.initialize();
		return this.bioFormat.toString();
	}
	
	public List<String> getEncodedPath() { return this.encodedPath; }
	public List<String> getEncodedCont() { return this.encodedContent; }
	
	public void setPathAndContent(String path, String content) {
		this.setPath(path);
		this.setContent(content);
	}
	
	protected String setPath(String path) {
		this.encodedPath	= new ArrayList<String>();
		return this.addPath(path);
	}
	
	protected String setContent(String content) {
		this.encodedContent	= new ArrayList<String>();
		return this.addContent(content);
	}
	
	public void addPathAndContent(String path, String content) { 
		this.addPath(path);
		this.addContent(content);
	}
	
	private String addPath(String path) {
		this.encodedPath.add(path.equals("") ? "" : BiologicalFileCreatorHelper.forPathDirName.encode(path, 1) );
		return this.encodedPath.get(this.encodedPath.size() - 1);
	}
	
	private String addContent(String content) {
		this.encodedContent.add(content.equals("") ? "" : BiologicalFileCreatorHelper.forFileContent.encode(content, 1) );
		return this.encodedContent.get(this.encodedContent.size() - 1);
	}

}
