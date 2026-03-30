package gabywald.crypto.data.ioput;

import java.util.List;

import gabywald.crypto.data.EmblFormat;
import gabywald.crypto.model.ITranslator;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public class EmblFileReader implements IFileCryptoReader {

	private List<EmblFormat> bankOfData;
	private StringBuilder sbDecodedPath;
	private StringBuilder sbDecodedContent;
	
	private EmblFileCreator companion = null;
	
	public EmblFileReader() { this(""); }
	
	public EmblFileReader(String fileContent) { 
		this.companion = new EmblFileCreator(0, 1, ITranslator.TranslatorEnum.simple);
		this.setFileContent(fileContent);
	}

	@Override
	public void setFileContent(String fileContent) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPath()		{ return this.sbDecodedPath.toString(); }
	@Override
	public String getContent()	{ return this.sbDecodedContent.toString(); }
	@Override
	public IFileCryptoCreator getCompanion() { return this.companion; }

}
