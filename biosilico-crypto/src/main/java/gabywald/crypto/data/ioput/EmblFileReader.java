package gabywald.crypto.data.ioput;

import java.util.List;

import gabywald.crypto.data.EmblFormat;
import gabywald.crypto.model.ITranslator;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public class EmblFileReader extends BiologicalFileReader {

	private List<EmblFormat> bankOfData;
	
	/**
	 * 
	 * @param forFiles
	 * @param forPathes
	 * @param which
	 */
	public EmblFileReader(ITranslator forFiles, ITranslator forPathes, ITranslator.TranslatorEnum which) {
		super( new EmblFileCreator(forFiles, forPathes, which) );
	}

	@Override
	public void setFileContent(String fileContent) {
		// TODO Auto-generated method stub

	}

}
