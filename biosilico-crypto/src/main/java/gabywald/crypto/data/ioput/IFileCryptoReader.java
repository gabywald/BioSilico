package gabywald.crypto.data.ioput;

/**
 * 
 */
public interface IFileCryptoReader {

	public void setFileContent(String fileContent);
	
	public String getDecodedPath();
	public String getDecodedContent();

	public IFileCryptoCreator getCompanion();
	
//	public ITranslator getForFileContent();
//
//	public ITranslator getForPathDirName();
//
//	public ITranslator.TranslatorEnum getWhichTR();
	
}
