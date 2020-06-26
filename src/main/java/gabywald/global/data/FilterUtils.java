package gabywald.global.data;

import gabywald.global.structures.StringListe;

import java.io.File;

/**
 * This class to determine file extension. 
 * @author St&eacute;fan Engelen (2006)
 * @author Gabriel Chandesris (2008-2010)
 */
public class FilterUtils extends Filter {
	/** Default Description. */
	private static final String DEFAULT_DESCRIPTION = "Default Filter Utils";
	/** clustalW alignment */
	public final static String aln = "aln";
	/** fasta sequence / alignment */
	public final static String fasta = "fasta";
	/** sequence */
	public final static String seq = "seq";
	/** alignment */
	public final static String ali = "ali";
	/** result */
	public final static String res = "res";
	/** connect file format, see bracket too */
	public final static String ct = "ct";
	/** bracket sequence+palindrome(s) */
	public final static String b = "b";
	/** tiff image */
	public final static String tiff = "tiff";
	/** jpeg image */
	public final static String jpg = "jpg";
	/** gif image */
	public final static String gif = "gif";
	/** png image */
	public final static String png = "png";
	/** XML files. */
	public final static String xml = "xml";
	/** TXT files. */
	public final static String txt = "txt";
	
	/** Description of the current instance. */
	private String description;
	/** List of extension in current Filter. */
	private StringListe liste;
	
	/** Default Constructor (empty set).  */
	public FilterUtils () 
		{ this.init(-1, FilterUtils.DEFAULT_DESCRIPTION); }
	
	/**
	 * A constructor with a switching type. 
	 * @param type (int)
	 * @see FilterUtils#init(int, String)
	 */
	public FilterUtils (int type) 
		{ this.init(type, FilterUtils.DEFAULT_DESCRIPTION); }
	
	/**
	 * Constructor with description (empty set). 
	 * @param description (String)
	 */
	public FilterUtils (String description) 
		{ this.init(-1, description); }
	
	/**
	 * Constructor with type and description. 
	 * @param type (int)
	 * @param description (String)
	 */
	public FilterUtils (int type,String description) 
		{ this.init(type, description); }
	
	/**
	 * Initialization for constructors. 
	 * <p></p>
	 * <ul>
	 * 		<li><b>0 : </b>Images (gif, jpg, png, tiff). </li>
	 * 		<li><b>1 : </b>Alignments (ali, aln). </li>
	 * 		<li><b>2 : </b>Biological sequences (fasta, seq). </li>
	 * 		<li><b>3 : </b>Specific texts formats (TXT, XML)</li>
	 * 		<li><b>4 : </b>Some others (b, ct, res)...</li>
	 * 		<li><b>9 : </b>All types described above...</li>
	 *  	<li><b>(default) : </b>Empty set.</li>
	 * </ul>
	 * @param type (int)
	 * @param description (String)
	 */
	private void init(int type, String description) {
		this.description = description;
		this.liste = new StringListe();
		switch (type) {
		case(0): /** images */
			this.liste.addString(FilterUtils.gif);
			this.liste.addString(FilterUtils.jpg);
			this.liste.addString(FilterUtils.png);
			this.liste.addString(FilterUtils.tiff);
			break;
		case(1):	/** alignments */
			this.liste.addString(FilterUtils.ali);
			this.liste.addString(FilterUtils.aln);
			break;
		case(2):	/** sequences formats */
			this.liste.addString(FilterUtils.fasta);
			this.liste.addString(FilterUtils.seq);
			break;
		case(3):	/** files formats */
			this.liste.addString(FilterUtils.xml);
			this.liste.addString(FilterUtils.txt);
			break;
		case(4):	/** others formats */
			this.liste.addString(FilterUtils.b);
			this.liste.addString(FilterUtils.ct);
			this.liste.addString(FilterUtils.res);
			break;
		case(9):
			this.liste.addString(FilterUtils.ali);
			this.liste.addString(FilterUtils.aln);
			this.liste.addString(FilterUtils.b);
			this.liste.addString(FilterUtils.ct);
			this.liste.addString(FilterUtils.fasta);
			this.liste.addString(FilterUtils.gif);
			this.liste.addString(FilterUtils.jpg);
			this.liste.addString(FilterUtils.png);
			this.liste.addString(FilterUtils.res);
			this.liste.addString(FilterUtils.seq);
			this.liste.addString(FilterUtils.tiff);
			this.liste.addString(FilterUtils.xml);
			this.liste.addString(FilterUtils.txt);
			break;
		default:break;
		}
	}
	
	public boolean accept(File fich) {
		if (fich.isDirectory()) { return true; }
		String extension = FilterUtils.getExtension(fich);
		if (extension != null) 
			{ return this.isValid(extension); }
		return false;
	}

	public String getDescription() { return this.description; }
	
	/**
	 * To know if a specific extension is recognized by current FilterUtils. 
	 * @param extension (String)
	 * @return (boolean)
	 */
	public boolean isValid(String extension) 
		{ return this.liste.has(extension); }
	
	protected void addExtension(String ext) 
		{ this.liste.addString(ext); }
	
	/** 
	 * Get the extension of a file.
	 * @param f (File)
	 * @return (String)
	 */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if ( (i > 0) && (i < s.length() - 1) ) 
				{ ext = s.substring(i+1).toLowerCase(); }
		return ext;
	}
	
	/** 
	 * Get the extension of a file.
	 * @param completePath (String)
	 * @return (String)
	 */
	public static String getExtension(String completePath) {
		String ext = null;
		String s = completePath;
		int i = s.lastIndexOf('.');
		if ( (i > 0) && (i < s.length() - 1) ) 
				{ ext = s.substring(i+1).toLowerCase(); }
		return ext;
	}
	
	/**
	 * To get a FilterUtils for images. 
	 * @return (FilterUtils)
	 */
	public static FilterUtils getImagesFilter() 
		{ return new FilterUtils(0); }
	
	/**
	 * To get a FilterUtils for alignments. 
	 * @return (FilterUtils)
	 */
	public static FilterUtils getAlignmentsFilter()
		{ return new FilterUtils(1); }
	
	/**
	 * To get a FilterUtils for sequences. 
	 * @return (Filterutils)
	 */
	public static FilterUtils getSequencesFilter()
		{ return new FilterUtils(2); }
	
	/**
	 * To get a FilterUtils for BioSilico Files. 
	 * @return (Filterutils)
	 */
	public static FilterUtils getBioSilicoFilter()
		{ return new FilterUtils(4); }

}
