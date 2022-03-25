package gabywald.global.data.samples;

import gabywald.global.data.DataFile;

/**
 * 
 * @author Gabriel Chandesris (2011, 2022)
 */
@SuppressWarnings("serial")
public class UplinkDataFile extends DataFile {
	private static final String DEFAULT_DIRECTORY	= "dataUplink/";
	private static final String[] FILENAMES_SET		= {
		"agentaliases.txt",		"agentaliasesMore.txt", 
		"companya.txt",			"companyaMore.txt",		
		"companyb.txt", 		"companybMore.txt", 
		"fornames.txt",			"fornamesMore.txt", 
		"surnames.txt",			"wordlist.txt", 
	};

	private UplinkDataFile(String name) 
		{ super(UplinkDataFile.DEFAULT_DIRECTORY+name); }
	
	@Override
	public String getDefaultDirectory() 
		{ return UplinkDataFile.DEFAULT_DIRECTORY; }
	@Override
	public String[] getFileNamesSet() 
		{ return UplinkDataFile.FILENAMES_SET; }

	public static UplinkDataFile getAgentAliases() 
		{ return new UplinkDataFile(UplinkDataFile.FILENAMES_SET[0]); }
	
	public static UplinkDataFile getAgentAliasesMore() 
		{ return new UplinkDataFile(UplinkDataFile.FILENAMES_SET[1]); }
	
	public static UplinkDataFile getCompanyA() 
		{ return new UplinkDataFile(UplinkDataFile.FILENAMES_SET[2]); }
	
	public static UplinkDataFile getCompanyAMore() 
		{ return new UplinkDataFile(UplinkDataFile.FILENAMES_SET[3]); }
	
	public static UplinkDataFile getCompanyB() 
		{ return new UplinkDataFile(UplinkDataFile.FILENAMES_SET[4]); }
	
	public static UplinkDataFile getCompanyBMore() 
		{ return new UplinkDataFile(UplinkDataFile.FILENAMES_SET[5]); }
	
	public static UplinkDataFile getForNames() 
		{ return new UplinkDataFile(UplinkDataFile.FILENAMES_SET[6]); }
	
	public static UplinkDataFile getForNamesMore() 
		{ return new UplinkDataFile(UplinkDataFile.FILENAMES_SET[7]); }
	
	public static UplinkDataFile getSurNames() 
		{ return new UplinkDataFile(UplinkDataFile.FILENAMES_SET[8]); }
	
	public static UplinkDataFile getWordList() 
		{ return new UplinkDataFile(UplinkDataFile.FILENAMES_SET[9]); }
	
}
