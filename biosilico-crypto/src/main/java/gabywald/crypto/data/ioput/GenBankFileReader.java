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
public class GenBankFileReader {
	private List<GenBankFormat> genBank;
	private StringBuilder sbDecodedPath;
	private StringBuilder sbDecodedContent;
	
	private GenBankFileCreator companion = null;
	
	public GenBankFileReader() { this(""); }
	
	public GenBankFileReader(String fileContent) { 
		this.companion = new GenBankFileCreator(0, 1, ITranslator.TranslatorEnum.simple);
		this.setFileContent(fileContent);
	}
	
	/**
	 * 
	 * @param bioencoder4file
	 * @param bioencoder4path
	 * @param which
	 * @param content
	 */
	public GenBankFileReader(int bioencoder4file, int bioencoder4path, ITranslator.TranslatorEnum which, String fileContent) { 
		this.companion = new GenBankFileCreator(0, 1, ITranslator.TranslatorEnum.simple);
		this.setFileContent(fileContent);
	}
	
	public void setFileContent(String fileContent) {
		this.sbDecodedPath		= new StringBuilder();
		this.sbDecodedContent	= new StringBuilder();
		if ( ! fileContent.equals("")) { 
			this.genBank = GenBankFormat.fromString(fileContent);
			for (int i = 0 ; i < this.genBank.size() ; i++) {
				GenBankFormat currentGB	= this.genBank.get(i);
				FeaturesListe fl 	= currentGB.getFeatures().getFeaturesWith("CDS");
				for (int j = 0 ; j < fl.size() ; j++) {
					this.sbDecodedPath.append(this.companion.getForPathDirName() // <= PATH !!
							.decode(fl.get(i).get("translation"), 0, 0));
				}
				this.sbDecodedContent.append(this.companion.getForFileContent() // <= CONTENT !!
						.decode(currentGB.getOrigin().getContent(), 0, 0));
			}
		}
	}
	
	// String separator = "\n"+StringUtils.repeat("=", 80)+"\n";
	public String getPath()		{ return this.sbDecodedPath.toString(); }
	public String getContent()	{ return this.sbDecodedContent.toString(); }

	public GenBankFileCreator getCompanion() 
		{ return this.companion; }
	
	

}
