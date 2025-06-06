package gabywald.global.data;

/**
 * 
 * @author Gabriel Chandesris (2012)
 */
@SuppressWarnings("serial")
public class FrameworkDataFile extends DataFile {
	private static final String DEFAULT_DIRECTORY	= "dataFrameWork/";
	private static final String[] FILENAMES_SET		= {
		"peerConnections.txt", 	
	};

	public FrameworkDataFile(String name) 
		{ super(FrameworkDataFile.DEFAULT_DIRECTORY+name); }
	
	@Override
	public String getDefaultDirectory() 
		{ return FrameworkDataFile.DEFAULT_DIRECTORY; }
	
	@Override
	public String[] getFileNamesSet() 
		{ return FrameworkDataFile.FILENAMES_SET; }
	
	public static FrameworkDataFile getPeerConnections() 
		{ return new FrameworkDataFile(FrameworkDataFile.FILENAMES_SET[0]); }
	
	
}
