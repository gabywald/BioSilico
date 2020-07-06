package gabywald.global.data.samples;

public class GeneratorDataFile extends DataFile {
	private static final String DEFAULT_DIRECTORY	= "dataSciGen/";
	private static final String[] FILENAMES_SET		= {
		"forLaTeXrules.txt",			"forLaTeXSlidesRules.txt",	
		"functions.txt",				"systemName.txt", 	
		"forLaTeXScientificRules.txt", 	"notFoundKeys.txt", 
	};
	
	public GeneratorDataFile(String name) 
		{ super(GeneratorDataFile.DEFAULT_DIRECTORY+name); }

	@Override
	public String getDefaultDirectory() 
		{ return GeneratorDataFile.DEFAULT_DIRECTORY; }
	@Override
	public String[] getFileNamesSet() 
		{ return GeneratorDataFile.FILENAMES_SET; }
	
	public static GeneratorDataFile getForLaTeX() 
		{ return new GeneratorDataFile(GeneratorDataFile.FILENAMES_SET[0]); }
	
	public static GeneratorDataFile getForLaTeXSlides() 
		{ return new GeneratorDataFile(GeneratorDataFile.FILENAMES_SET[1]); }
	
	public static GeneratorDataFile getFunctions() 
		{ return new GeneratorDataFile(GeneratorDataFile.FILENAMES_SET[2]); }
	
	public static GeneratorDataFile getSystemName() 
		{ return new GeneratorDataFile(GeneratorDataFile.FILENAMES_SET[3]); }
	
	public static GeneratorDataFile getForLaTeXScientific() 
		{ return new GeneratorDataFile(GeneratorDataFile.FILENAMES_SET[4]); }
	
	public static GeneratorDataFile getNotFoundKeys() 
		{ return new GeneratorDataFile(GeneratorDataFile.FILENAMES_SET[5]); }
}
