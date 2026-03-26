package gabywald.crypto.data.composition;

import java.util.List;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.GenBankFormat;
import gabywald.crypto.data.composition.FeaturesListe;
import gabywald.crypto.model.GeneticTranslator;
import gabywald.global.data.StringUtils;

/**
 * Aim of this class is to generate a GenBank file with encrypted data, from a file to read. 
 * <br>Data is encrypted when included (content and path of file, respectively as proteomic and nucleotidic data). 
 * <br>Encryption according to current "genetic encryption". 
 * @author Gabriel Chandesris (2011, 2020, 2022, 2026)
 */
public class GenBankFileReader {
	private static final GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
	private static final GeneticTranslator forPathDirName = BiologicalUtils.getGenericCrypto(1);
	
	private List<GenBankFormat> genBank;
	private String decodedPath;
	private String decodedContent;
	
	public GenBankFileReader() 
		{ this.setContent(""); }
	
	public GenBankFileReader(String content) 
		{ this.setContent(content); }
	
	public void setContent(String content) {
		this.decodedPath	= new String("");
		this.decodedContent	= new String("");
		if (!content.equals("")) { 
			String separator = "\n"+StringUtils.repeat("=", 80)+"\n";
			this.genBank = GenBankFormat.fromString(content);
			for (int i = 0 ; i < this.genBank.size() ; i++) {
				GenBankFormat currentGB	= this.genBank.get(i);
				FeaturesListe fl 	= currentGB.getFeatures().getFeaturesWith("CDS");
				for (int j = 0 ; j < fl.size() ; j++) {
					String encodedPath	= fl.get(i).get("translation");
					this.decodedPath	+= GenBankFileReader.forPathDirName
											.decodeWithStartStopCodons(encodedPath, 0, 0)+separator;
				}
				
				String encodedContent	= currentGB.getOrigin().getContent();
				
				this.decodedContent		+= GenBankFileReader.forFileContent
											.decodeWithStartStopCodons(encodedContent, 0, 0)+separator;
			}
		}
	}
	
	public String getPath()		{ return this.decodedPath; }
	public String getContent()	{ return this.decodedContent; }


}
