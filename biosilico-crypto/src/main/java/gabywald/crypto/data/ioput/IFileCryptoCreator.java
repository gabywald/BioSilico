package gabywald.crypto.data.ioput;

import java.util.List;

import gabywald.crypto.model.ITranslator;

/**
 * Interface for encryption of files (path and content). 
 * @author Gabriel Chandesris (2026)
 */
public interface IFileCryptoCreator {
	
	/**
	 * To add a path and a content. 
	 * <br/>If both path and contant are empty, they have to be ignored. 
	 * @param path
	 * @param content
	 */
	public void addPathAndContent(String path, String content);
	
	/**
	 * To set a path and a content (list are emptied before adding). . 
	 * <br/>If both path and contant are empty, they have to be ignored. 
	 * @param path
	 * @param content
	 */
	public void setPathAndContent(String path, String content);
	
	public void emptyPathAndContent();
	
	public List<String> getEncodedPath();
	
	public List<String> getEncodedCont();
	
	public ITranslator getForFileContent();

	public ITranslator getForPathDirName();

	public ITranslator.TranslatorEnum getWhichTR();
}
