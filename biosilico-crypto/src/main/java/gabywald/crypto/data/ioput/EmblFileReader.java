package gabywald.crypto.data.ioput;

import java.util.List;

import gabywald.crypto.data.EmblFormat;
import gabywald.crypto.data.composition.FeaturesListe;
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
		this.sbDecodedPath		= new StringBuilder();
		this.sbDecodedContent	= new StringBuilder();
		if ( ! fileContent.equals("")) { 
			this.bankOfData = EmblFormat.fromString(fileContent);
			for (int i = 0 ; i < this.bankOfData.size() ; i++) {
				EmblFormat current	= this.bankOfData.get(i);
				FeaturesListe fl 	= current.getFeatures().getFeaturesWith("CDS");
				for (int j = 0 ; j < fl.size() ; j++) {
					this.sbDecodedPath.append(this.getCompanion().getForPathDirName() // <= PATH !!
							.decode(fl.get(i).get("translation"), 0, 0));
				}
				this.sbDecodedContent.append(this.getCompanion().getForFileContent() // <= CONTENT !!
						.decode(current.getOrigin().getContent(), 0, 0));
			}
		}
	}

}
