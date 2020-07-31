package gabywald.crypto.data.ioput;

import java.util.List;

import gabywald.crypto.data.GenBankFormat;
import gabywald.crypto.data.composition.FeaturesListe;
import gabywald.global.data.Utils;

/**
 * 
 * @author Gabriel Chandesris (2011, 2020)
 */
public class GenBankFileReader {
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
			String separator = "\n"+Utils.repeat("=", 80)+"\n";
			this.genBank = GenBankFormat.fromString(content);
			for (int i = 0 ; i < this.genBank.size() ; i++) {
				GenBankFormat currentGB	= this.genBank.get(i);
				FeaturesListe fl 	= currentGB.getFeatures().getFeaturesWith("CDS");
				for (int j = 0 ; j < fl.size() ; j++) {
					String encodedPath	= fl.get(i).get("translation");
					this.decodedPath	+= BiologicalFileCreatorHelper.forPathDirName
											.decodeWithStartStopCodons(encodedPath, 0, 0)+separator;
				}
				
				String encodedContent	= currentGB.getOrigin().getContent();
				
				this.decodedContent		+= BiologicalFileCreatorHelper.forFileContent
											.decodeWithStartStopCodons(encodedContent, 0, 0)+separator;
			}
		}
	}
	
	public String getPath()		{ return this.decodedPath; }
	public String getContent()	{ return this.decodedContent; }


}
