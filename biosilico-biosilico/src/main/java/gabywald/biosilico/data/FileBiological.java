package gabywald.biosilico.data;

import java.util.List;

import gabywald.biosilico.utils.Sequence;
import gabywald.global.data.File;

/**
 * Generic biological file format (with sequence list to be defined in inherited classes). 
 * @author Gabriel Chandesris (2010, 2020)
 */
@SuppressWarnings("serial")
public abstract class FileBiological extends File {

	/** Default path to record files for BioSilico. */
	public static final String DEFAULT_PATH_NAME = "biosilico/data/";
	
	/**
	 * Constructor with complete path to File. 
	 * @param completePath (String)
	 */
	public FileBiological(String completePath) 
		{ super("Biological File", completePath); }
	
	/** 
	 * Main constructor with type and simple name of the File. 
	 * @param type (String)
	 * @param fileName (String) name with extension and without path. 
	 */
	public FileBiological(String type,String fileName) 
		{ super(type,fileName); }
	
	/**
	 * To get the liste of current instance of FichierBiological's sub-class. 
	 * @return (SequenceListe)
	 */
	public abstract List<Sequence> getListe();
	
	/**
	 * Length of the current liste of instance of SequenceListe's.
	 * @return (int)
	 */
	public abstract int lengthListe();
	
}
