package gabywald.crypto.data.composition;

/**
 * This abstract class is a repository to generate some content. 
 * @author Gabriel Chandesris (2011)
 */
public abstract class ComposUtils {
	/** Max length of a 'line' (68), excluding 'title' (12 chars out of 80). */
	public static final int GENBANK_CUTTER = 80-12;
	/** Max length of a 'line' (75), excluding 'title' (5 chars out of 80). */
	public static final int EMBL_CUTTER = 80-5;
	
	
	public static final String GENBANK_PRIMARY_LINE		= 
		"PRIMARY     REFSEQ_SPAN         PRIMARY_IDENTIFIER PRIMARY_SPAN        COMP";
	public static final String GENBANK_FEATURES_LINE	= 
		"FEATURES             Location/Qualifiers";
	public static final String GENBANK_ORIGIN_LINE		= 
		"ORIGIN      ";
	
	public static final String AUTHOR_SEPARATOR_COMMA	= ", ";
	public static final String AUTHOR_SEPARATOR_AND		= " and ";
	public static final String TAXONOMY_SEPARATOR		= "; ";
	public static final String BEGIN_EMPTY_LINE			= "            ";
	
	public static final String GENBANK_TAG_LOCUS		= "LOCUS       ";
	public static final String GENBANK_TAG_DEFINITION	= "DEFINITION  ";
	public static final String GENBANK_TAG_ACCESSION	= "ACCESSION   ";
	public static final String GENBANK_TAG_VERSION		= "VERSION     ";
	public static final String GENBANK_TAG_KEYWORDS		= "KEYWORDS    ";
	public static final String GENBANK_TAG_SOURCE		= "SOURCE      ";
	public static final String GENBANK_TAG_ORGANISM		= "  ORGANISM  ";
	public static final String GENBANK_TAG_REFERENCE	= "REFERENCE   ";
	public static final String GENBANK_TAG_AUTHORS		= "  AUTHORS   ";
	public static final String GENBANK_TAG_TITLE		= "  TITLE     ";
	public static final String GENBANK_TAG_JOURNAL		= "  JOURNAL   ";
	public static final String GENBANK_TAG_MEDLINE		= "  MEDLINE   ";
	public static final String GENBANK_TAG_PUBMED		= "   PUBMED   ";
	public static final String GENBANK_TAG_REMARK		= "  REMARK    ";
	public static final String GENBANK_TAG_COMMENT		= "COMMENT     ";
	public static final String GENBANK_TAG_PRIMARY		= "PRIMARY     ";
	public static final String GENBANK_TAG_FEATURES		= "FEATURES             ";
	public static final String GENBANK_TAG_BASECOUNT	= "BASE COUNT  ";
	public static final String GENBANK_TAG_ORIGIN		= "ORIGIN      ";
	public static final String GENBANK_TAG_ENDOF		= "//";
}
