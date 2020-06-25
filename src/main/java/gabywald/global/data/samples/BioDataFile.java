package gabywald.global.data.samples;

/**
 * 
 * @author Gabriel Chandesris (2011)
 */
public class BioDataFile extends DataFile {
	private static final String DEFAULT_DIRECTORY	= "dataBioBio/";
	private static final String[] FILENAMES_SET		= {
		"divisionClassification.txt",	"featuresExtract.txt",	"gc.prt",	
		"gencode.txt",					"table_organism.txt", 	"table_taxonoms.txt",	
		"IUPACcodes.txt",	
	};

	private BioDataFile(String name) 
		{ super(BioDataFile.DEFAULT_DIRECTORY+name); }
	
	@Override
	public String getDefaultDirectory() 
		{ return BioDataFile.DEFAULT_DIRECTORY; }
	@Override
	public String[] getFileNamesSet() 
		{ return BioDataFile.FILENAMES_SET; }
	
	public static BioDataFile getDivisionClass() 
		{ return new BioDataFile(BioDataFile.FILENAMES_SET[0]); }
	
	public static BioDataFile getFeatureExtracts() 
		{ return new BioDataFile(BioDataFile.FILENAMES_SET[1]); }
	
	public static BioDataFile getGeneticCodesPRT() 
		{ return new BioDataFile(BioDataFile.FILENAMES_SET[2]); }
	
	public static BioDataFile getGeneticCodesTXT() 
		{ return new BioDataFile(BioDataFile.FILENAMES_SET[3]); }
	
	public static BioDataFile getTableOfOrganism() 
		{ return new BioDataFile(BioDataFile.FILENAMES_SET[4]); }
	
	public static BioDataFile getTableOfTaxonomy() 
		{ return new BioDataFile(BioDataFile.FILENAMES_SET[5]); }
	
	public static BioDataFile getIUPACTable() 
		{ return new BioDataFile(BioDataFile.FILENAMES_SET[5]); }

}
