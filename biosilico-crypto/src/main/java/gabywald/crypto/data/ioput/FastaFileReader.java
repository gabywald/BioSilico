package gabywald.crypto.data.ioput;

import java.util.List;

import gabywald.crypto.data.FastaFormat;
import gabywald.crypto.model.ITranslator;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public class FastaFileReader extends BiologicalFileReader {

	private List<FastaFormat> bankOfData;
	
	public FastaFileReader(ITranslator forFiles, ITranslator forPathes, ITranslator.TranslatorEnum which) { 
		super( new FastaFileCreator(forFiles, forPathes, which) );
	}

	@Override
	public void setFileContent(String fileContent) {
		// TODO Auto-generated method stub

	}

}
