package gabywald.crypto.data.ioput;

import java.util.List;

import gabywald.crypto.data.FastaFormat;
import gabywald.crypto.model.ITranslator;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public class FastaFileReader implements IFileCryptoReader {

	private List<FastaFormat> bankOfData;
	private StringBuilder sbDecodedPath;
	private StringBuilder sbDecodedContent;
	
	private FastaFileCreator companion = null;
	
	public FastaFileReader() { this(""); }
	
	public FastaFileReader(String fileContent) { 
		this.companion = new FastaFileCreator(0, 1, ITranslator.TranslatorEnum.simple);
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
