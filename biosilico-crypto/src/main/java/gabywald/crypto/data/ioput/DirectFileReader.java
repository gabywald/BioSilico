package gabywald.crypto.data.ioput;

import java.util.List;

import gabywald.crypto.data.DirectFormat;
import gabywald.crypto.model.ITranslator;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public class DirectFileReader extends BiologicalFileReader {
	
	private List<DirectFormat> bankOfData;
	private StringBuilder sbDecodedPath;
	private StringBuilder sbDecodedContent;
	
	/**
	 * 
	 * @param forFiles
	 * @param forPathes
	 * @param which
	 */
	public DirectFileReader(ITranslator forFiles, ITranslator forPathes, ITranslator.TranslatorEnum which) {
		super( new DirectFileCreator(forFiles, forPathes, which) );
	}

	@Override
	public void setFileContent(String fileContent) {
		this.sbDecodedPath		= new StringBuilder();
		this.sbDecodedContent	= new StringBuilder();
		if ( ! fileContent.equals("")) { 
			this.bankOfData = DirectFormat.fromString(fileContent);
			for (int i = 0 ; i < this.bankOfData.size() ; i++) {
				DirectFormat current	= this.bankOfData.get(i);
				this.sbDecodedPath.append(this.getCompanion().getForPathDirName().decode(current.getComment(), 0, 0));
				this.sbDecodedContent.append(this.getCompanion().getForFileContent().decode(current.getOrigin().getContent(), 0, 0));
			}
		}
	}

}
