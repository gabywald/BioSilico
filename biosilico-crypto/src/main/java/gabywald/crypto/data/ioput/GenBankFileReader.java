package gabywald.crypto.data.ioput;

import java.util.List;

import gabywald.crypto.data.GenBankFormat;
import gabywald.crypto.data.composition.FeaturesListe;
import gabywald.crypto.model.ITranslator;

/**
 * Aim of this class is to generate a GenBank file with encrypted data, from a file to read. 
 * <br>Data is encrypted when included (content and path of file, respectively as proteomic and nucleotidic data). 
 * <br>Encryption according to current "genetic encryption". 
 * @author Gabriel Chandesris (2011, 2020, 2022, 2026)
 */
public class GenBankFileReader extends BiologicalFileReader {
	
	private List<GenBankFormat>  bankOfData;
	
	public GenBankFileReader(ITranslator forFiles, ITranslator forPathes, ITranslator.TranslatorEnum which) { 
		super( new GenBankFileCreator(forFiles, forPathes, which) );
	}
	
	public void setFileContent(String fileContent) {
		this.sbDecodedPath		= new StringBuilder();
		this.sbDecodedContent	= new StringBuilder();
		if ( ! fileContent.equals("")) { 
			this.bankOfData = GenBankFormat.fromString(fileContent);
			for (int i = 0 ; i < this.bankOfData.size() ; i++) {
				GenBankFormat current	= this.bankOfData.get(i);
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
