package gabywald.global.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class to determine file extension. 
 * @author Gabriel Chandesris (2008-2010, 2020)
 */
public class FilterUtils extends Filter {
	/** Default Description. */
	private static final String DEFAULT_DESCRIPTION = "Default Filter Utils";
	/** Description of the current instance. */
	private String description;
	/** List of extension in current Filter. */
	private List<String> liste;
	
	/** Default Constructor (empty set).  */
	public FilterUtils () 
		{ this.init(FilterGroupType.NONE, FilterUtils.DEFAULT_DESCRIPTION); }
	
	/**
	 * A constructor with a switching type. 
	 * @param type (int)
	 * @see FilterUtils#init(int, String)
	 */
	public FilterUtils (FilterGroupType type) 
		{ this.init(type, FilterUtils.DEFAULT_DESCRIPTION); }
	
	/**
	 * Constructor with description (empty set). 
	 * @param description (String)
	 */
	public FilterUtils (String description) 
		{ this.init(FilterGroupType.NONE, description); }
	
	/**
	 * Constructor with type and description. 
	 * @param type (int)
	 * @param description (String)
	 */
	public FilterUtils (FilterGroupType type, String description) 
		{ this.init(type, description); }
	
	/**
	 * 
	 *@author Gabriel Chandesris (2020)
	 */
	public enum FilterType {
		/** clustalW alignment */
		aln("aln", 0),
		/** fasta sequence / alignment */
		fasta("fasta", 1), 
		/** fastq sequence / alignment */
		fastq("fastq", 1), 
		/** fasta sequence / alignment : nucleic acid */
		fna("fna", 1), 
		/** fasta sequence / alignment : nucleotide coding region */
		ffn("ffn", 1), 
		/** fasta sequence / alignment : amino acid*/
		faa("faa", 1), 
		/** fasta sequence / alignment : non coding rna */
		frn("frn", 1), 
		/** sequence */
		seq("seq", 1), 
		/** alignment */
		ali("ali", 0), 
		/** result */
		res("res", -1), 
		/** connect file format, see bracket too */
		ct("ct", -1), 
		/** bracket sequence+palindrome(s) */
		b("b", -1), 
		/** tiff image */
		tiff("tiff", 2), 
		/** jpg image */
		jpg("jpg", 2), 
		/** jpeg image */
		jpeg("jpeg", 2), 
		/** gif image */
		gif("gif", 2), 
		/** png image */
		png("png", 2), 
		/** XML files. */
		xml("xml", 3),
		/** TXT files. */
		txt("txt", 3);
		
		private int grp;
		
		private String ext;

		/**
		 * 
		 * @param ext Extension. 
		 * @param grp Group (0 : alignment ; 1 : sequence ; 2 : image ; 3 : txt, xml and similar ; -1 : others)
		 */
		FilterType(String ext, int grp) {
			this.ext = ext;
			this.grp = grp;
		}
		
		public String getExtension() {
			return this.ext;
		}
		
		public int getGroup() { 
			return this.grp;
		}
		
		public static String[] getAll() {
			return Arrays.asList(FilterType.values()).stream()
					.map( type -> type.getExtension())
					.collect(Collectors.toList()).toArray(new String[0]);
		}
		
		public static String[] getFilterTypeOfGroup(int grp) {
			return Arrays.asList(FilterType.values()).stream()
							.filter( type -> (type.grp == grp) ).map( type -> type.getExtension())
							.collect(Collectors.toList()).toArray(new String[0]);
		}

	}
	
	/**
	 * 
	 * @author Gabriel Chandesris (2020)
	 */
	public enum FilterGroupType {
		/** Images (gif, jpg, png, tiff). */
		IMAGES(FilterType.getFilterTypeOfGroup(2)), 
		/** Biological sequences (fasta, seq).  */
		SEQUENCES(FilterType.getFilterTypeOfGroup(1)), 
		/** Alignments (ali, aln).*/
		ALIGNMENTS(FilterType.getFilterTypeOfGroup(0)),
		/** Specific texts formats (TXT, XML) */
		TEXT(FilterType.getFilterTypeOfGroup(3)), 
		/** Some others (b, ct, res)... */
		OTHER(FilterType.getFilterTypeOfGroup(-1)), 
		/** All types described above... */
		ALL(FilterType.getAll()), 
		/** No type described above. */
		NONE;
		
		private List<String> exts = new ArrayList<String>();
		
		/** Empty constructor (NONE). */
		private FilterGroupType() { ; }
		
		private FilterGroupType(String... extensions) {
			this.exts.addAll(Arrays.asList(extensions));
		}
		
		public List<String> extensions() { 
			return this.exts;
		}
	}
	
	/**
	 * Initialization for constructors. 
	 * @param type (int)
	 * @param description (String)
	 */
	private void init(FilterGroupType type, String description) {
		this.description	= description;
		this.liste			= new ArrayList<String>();
		this.liste.addAll(type.extensions());
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
		{ return this.liste.contains(extension); }
	
	protected void addExtension(String ext) 
		{ this.liste.add(ext); }
	
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
		{ return new FilterUtils(FilterGroupType.IMAGES); }
	
	/**
	 * To get a FilterUtils for alignments. 
	 * @return (FilterUtils)
	 */
	public static FilterUtils getAlignmentsFilter()
		{ return new FilterUtils(FilterGroupType.ALIGNMENTS); }
	
	/**
	 * To get a FilterUtils for sequences. 
	 * @return (Filterutils)
	 */
	public static FilterUtils getSequencesFilter()
		{ return new FilterUtils(FilterGroupType.SEQUENCES); }
	
}
