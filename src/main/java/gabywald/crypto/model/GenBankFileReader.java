package gabywald.crypto.model;

import java.util.List;

import gabywald.global.data.Utils;
import gabywald.javabio.data.BiologicalUtils;
import gabywald.javabio.data.GenBank;
import gabywald.javabio.data.composition.FeaturesListe;

public class GenBankFileReader {
	private static final GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
	private static final GeneticTranslator forPathDirName = BiologicalUtils.getGenericCrypto(1);
	
	private List<GenBank> genBank;
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
			String separator = "\n"+Utils.repeat("=", 80)+"\n";
			this.genBank = GenBank.fromString(content);
			for (int i = 0 ; i < this.genBank.size() ; i++) {
				GenBank currentGB	= this.genBank.get(i);
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
