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
		this.sbDecodedPath		= new StringBuilder();
		this.sbDecodedContent	= new StringBuilder();
		if ( ! fileContent.equals("")) { 
			this.bankOfData = FastaFormat.fromString(fileContent);
			for (int i = 0 ; i < this.bankOfData.size() ; i++) {
				FastaFormat current	= this.bankOfData.get(i);
				this.sbDecodedPath.append(this.getCompanion().getForPathDirName().decode(current.getComment(), 0, 0));
				this.sbDecodedContent.append(this.getCompanion().getForFileContent().decode(current.getOrigin().getContent(), 0, 0));
			}
		}
	}

}
