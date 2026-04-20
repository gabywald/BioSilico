package gabywald.global.data.samples;

import gabywald.global.data.DataFile;

/**
 * 
 * @author Gabriel Chandesris (2011, 2022, 2026)
 */
@SuppressWarnings("serial")
public class GenericDataFile extends DataFile {
	
	private static final String DEFAULT_DIRECTORY	= "dataGeneric/";
	
	private static final String[] FILENAMES_SET		= {
		"genericCryptoAlphanumeric.txt",	"genericCryptoASCIIJavaCPlus.txt",	
		"genericCryptoASCIIJavaCPlus2.txt",	"genericCryptoASCIIJavaCPlus3.txt",	
	};

	private GenericDataFile(String name) 
		{ super("GENERIC_DATA_TYPE", GenericDataFile.DEFAULT_DIRECTORY + name); }
	
	
	@Override
	public String getDefaultDirectory() 
		{ return GenericDataFile.DEFAULT_DIRECTORY; }
	
	@Override
	public String[] getFileNamesSet() 
		{ return GenericDataFile.FILENAMES_SET; }
	
	public static GenericDataFile getCryptoAlphaNumeric() 
		{ return new GenericDataFile(GenericDataFile.FILENAMES_SET[0]); }
	
	public static GenericDataFile getCryptoASCIIJavaCPlus() 
		{ return new GenericDataFile(GenericDataFile.FILENAMES_SET[1]); }
	
	public static GenericDataFile getCryptoFileDefinition(int i) 
		{ return new GenericDataFile(GenericDataFile.FILENAMES_SET[i]); }
}
