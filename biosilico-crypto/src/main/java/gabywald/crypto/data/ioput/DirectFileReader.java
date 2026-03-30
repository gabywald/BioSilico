package gabywald.crypto.data.ioput;

import java.util.List;

import gabywald.crypto.data.DirectFormat;
import gabywald.crypto.model.ITranslator;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public class DirectFileReader implements IFileCryptoReader {
	
	private List<DirectFormat> bankOfData;
	private StringBuilder sbDecodedPath;
	private StringBuilder sbDecodedContent;
	
	private DirectFileCreator companion = null;
	
	public DirectFileReader() { this(""); }
	
	public DirectFileReader(String fileContent) { 
		this.companion = new DirectFileCreator(0, 1, ITranslator.TranslatorEnum.simple);
		this.setFileContent(fileContent);
	}

	@Override
	public void setFileContent(String fileContent) {
		this.sbDecodedPath		= new StringBuilder();
		this.sbDecodedContent	= new StringBuilder();
		if ( ! fileContent.equals("")) { 
			this.bankOfData = DirectFormat.fromString(fileContent);
			for (int i = 0 ; i < this.bankOfData.size() ; i++) {
				DirectFormat current	= this.bankOfData.get(i);
				this.sbDecodedPath.append(this.companion.getForPathDirName().decode(current.getComment(), 0, 0));
				this.sbDecodedContent.append(this.companion.getForFileContent().decode(current.getOrigin().getContent(), 0, 0));
			}
		}
	}

	@Override
	public String getPath()		{ return this.sbDecodedPath.toString(); }
	@Override
	public String getContent()	{ return this.sbDecodedContent.toString(); }
	@Override
	public IFileCryptoCreator getCompanion() { return this.companion; }

}
