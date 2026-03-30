package gabywald.crypto.data.ioput;

/**
 * 
 */
public interface IFileCryptoReader {

	public void setFileContent(String fileContent);
	
	public String getPath();
	public String getContent();

	public IFileCryptoCreator getCompanion();
	
}
