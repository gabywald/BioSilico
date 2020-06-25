package gabywald.javabio.data.composition;

import java.util.HashMap;

/**
 * 
 * <br><i>Multiton ("multiple singleton")</i>
 * @author Gabriel Chandesris (2011)
 */
public class FeatureDefinition {
	/** Data Tag 0. */
	private static final String FEATUREDEFINITION_TAG_FEATUREKEY	= 
		"Feature Key           ";
	/** Data Tag 1. */
	private static final String FEATUREDEFINITION_TAG_PARENTKEY		= 
		"Parent Key            ";
	/** Data Tag 2. */
	private static final String FEATUREDEFINITION_TAG_DEFINITION	= 
		"Definition            ";
	/** Data Tag 3 (one line). */
	private static final String FEATUREDEFINITION_TAG_ORGANISMSCOPE	= 
		"Organism scope        ";
	/** Data Tag 4 (one line). */
	private static final String FEATUREDEFINITION_TAG_MOLECULESCOPE	= 
		"Molecule scope        ";
	/** Data Tag 5. */
	private static final String FEATUREDEFINITION_TAG_REFERENCES	= 
		"References            ";
	/** Data Tag 6. */
	private static final String FEATUREDEFINITION_TAG_COMMENT		= 
		"Comment               ";
	/** Mandatory qualifiers tag. */
	private static final String FEATUREDEFINITION_TAG_MANDATORY		= 
		"Mandatory qualifiers  ";
	/** Optional qualifiers tag. */
	private static final String FEATUREDEFINITION_TAG_OPTIONNAL		= 
		"Optional qualifiers   ";
	/** Blank line (reference). */
	public static final String FEATUREDEFINITION_BLANKLINE			= 
		"                      ";
	/** Tags about data (7). */
	private static final String[] FEATUREDEFINITION_TAG_TABLE		= {
		FeatureDefinition.FEATUREDEFINITION_TAG_FEATUREKEY, 
		FeatureDefinition.FEATUREDEFINITION_TAG_PARENTKEY, 
		FeatureDefinition.FEATUREDEFINITION_TAG_DEFINITION, 
		FeatureDefinition.FEATUREDEFINITION_TAG_ORGANISMSCOPE, 
		FeatureDefinition.FEATUREDEFINITION_TAG_MOLECULESCOPE, 
		FeatureDefinition.FEATUREDEFINITION_TAG_REFERENCES, 
		FeatureDefinition.FEATUREDEFINITION_TAG_COMMENT
	};
	/**
	 * This table defines some data about a Feature Definition. 
	 * <ol type=1>
	 * 		<li>Feature Key<li>
	 * 		<li>Parent Key<li>
	 * 		<li>Definition</li>
	 *  	<li>Organism scope</li>
	 *  	<li>Molecule scope</li>
	 *  	<li>References</li>
	 *  	<li>Comment</li>
	 * </ol>
	 */
	private String[] properties;
	/** Name and type of mandatory qualifiers. */
	private HashMap<String, String> mandatory;
	/** Name and type of optional qualifiers. */
	private HashMap<String, String> optionnal;
	
	/**
	 * Main constructor with feature code. 
	 * @param featureKey (String)
	 * @see FeatureDefinition#getFromFactory(String)
	 */
	private FeatureDefinition(String featureKey) 
		{ this.init();this.properties[0] = featureKey; }
	
	/** Constructors' helper (intialization). */
	private void init() {
		this.properties		= new String[7];
		for (int i = 0 ; i < this.properties.length ; i++) 
			{ this.properties[i] = null; }
		if (this.mandatory == null) 
			{ this.mandatory = new HashMap<String, String>(0); }
		if (this.optionnal == null) 
			{ this.optionnal = new HashMap<String, String>(0); }
	}
	
	public String getFeatureKey()		{ return this.properties[0]; }
	public String getDefinition()		{ return this.properties[1]; }
	public String getOrganismScope()	{ return this.properties[2]; }
	public String getMoleculeScope()	{ return this.properties[3]; }
	public String getReferences()		{ return this.properties[4]; }
	public String getComment()			{ return this.properties[5]; }
	
//	public void setFeatureKey(String data)		{ this.properties[0] = data; }
//	public void setDefinition(String data)		{ this.properties[1] = data; }
//	public void setOrganismScope(String data)	{ this.properties[2] = data; }
//	public void setMoleculeScope(String data)	{ this.properties[3] = data; }
//	public void setReferences(String data)		{ this.properties[4] = data; }
//	public void setComment(String data)			{ this.properties[5] = data; }
	
	public int getMandatorySize()	{ return this.mandatory.size(); }
	public int getOptionnalSize()	{ return this.optionnal.size(); }
	
	public boolean check(Feature fe) {
		/** TODO checking qualifiers with type's */
		return true;
	}
	
	/** TODO tests.... */
	public String toString()	{
		String toReturn = new String("");
		
		for (int i = 0 ; i < this.properties.length ; i++) {
			if (this.properties[i] != null) {
				String tagTitle = /** FeatureDefinition.FEATUREDEFINITION_TAG_BLANKLINE; */
					FeatureDefinition.FEATUREDEFINITION_TAG_TABLE[i];
				toReturn += tagTitle;
				if ( (i == 2) || ( (i == 5) || (i == 6) ) ) {
					String[] contentTable = this.properties[i].split("\n");
					for (int j = 0 ; j < contentTable.length ; j++) {
						toReturn += ((j != 0)?FeatureDefinition.FEATUREDEFINITION_BLANKLINE:"")
										+contentTable[i]+"\n";
					}
				} else { toReturn += this.properties[i]+"\n\n"; }
			}
		}
		
		if (this.mandatory.size() > 0) { 
			toReturn += FeatureDefinition.FEATUREDEFINITION_TAG_MANDATORY;
			String[] keys = this.mandatory.keySet().toArray(new String [0]);
			for (int i = 0 ; i < keys.length ; i++) {
				toReturn += ((i != 0)?FeatureDefinition.FEATUREDEFINITION_BLANKLINE:"")
							+"\\"+keys[i]+"="+this.mandatory.get(keys[i])+"\n";
			}
			toReturn += "\n";
		}
		
		if (this.optionnal.size() > 0) { 
			toReturn += FeatureDefinition.FEATUREDEFINITION_TAG_OPTIONNAL;
			String[] keys = this.optionnal.keySet().toArray(new String [0]);
			for (int i = 0 ; i < keys.length ; i++) {
				toReturn += ((i != 0)?FeatureDefinition.FEATUREDEFINITION_BLANKLINE:"")
							+"\\"+keys[i]+"="+this.optionnal.get(keys[i])+"\n";
			}
			toReturn += "\n";
		}
		
		return toReturn;
	}
	
	public static FeatureDefinition getFromFactory(String nameOfFD) {
		if (nameOfFD.equals("attenuator"))
			{ return FeatureDefinition.getAttenuatorFD(); }
		if (nameOfFD.equals("C_region"))
			{ return FeatureDefinition.getCregionFD(); }
		if (nameOfFD.equals("CAAT_signal"))
			{ return FeatureDefinition.getCAATsignalFD(); }
		if (nameOfFD.equals("CDS"))
			{ return FeatureDefinition.getCDSFD(); }
		if (nameOfFD.equals("D-loop"))
			{ return FeatureDefinition.getDloopFD(); }
		if (nameOfFD.equals("D_segment"))
			{ return FeatureDefinition.getDsegmentFD(); }
		if (nameOfFD.equals("enhancer"))
			{ return FeatureDefinition.getEnhancerFD(); }
		if (nameOfFD.equals("exon"))
			{ return FeatureDefinition.getExonFD(); }
		if (nameOfFD.equals("gap"))
			{ return FeatureDefinition.getGapFD(); }
		if (nameOfFD.equals("GC_signal"))
			{ return FeatureDefinition.getGCsignalFD(); }
		if (nameOfFD.equals("gene"))
			{ return FeatureDefinition.getGeneFD(); }
		if (nameOfFD.equals("iDNA"))
			{ return FeatureDefinition.getIDNAFD(); }
		if (nameOfFD.equals("intron"))
			{ return FeatureDefinition.getIntronFD(); }
		if (nameOfFD.equals("J_segment"))
			{ return FeatureDefinition.getJsegmentFD(); }
		if (nameOfFD.equals("LTR"))
			{ return FeatureDefinition.getLTRFD(); }
		if (nameOfFD.equals("mat_peptide"))
			{ return FeatureDefinition.getMatpeptideFD(); }
		if (nameOfFD.equals("misc_binding"))
			{ return FeatureDefinition.getMiscbindingFD(); }
		if (nameOfFD.equals("misc_difference"))
			{ return FeatureDefinition.getMiscdifferenceFD(); }
		if (nameOfFD.equals("misc_feature"))
			{ return FeatureDefinition.getMiscfeatureFD(); }
		if (nameOfFD.equals("misc_recomb"))
			{ return FeatureDefinition.getMiscrecombFD(); }
		if (nameOfFD.equals("misc_RNA"))
			{ return FeatureDefinition.getMiscRNAFD(); }
		if (nameOfFD.equals("misc_signal"))
			{ return FeatureDefinition.getMiscsignalFD(); }
		if (nameOfFD.equals("misc_structure"))
			{ return FeatureDefinition.getMiscstructureFD(); }
		if (nameOfFD.equals("mobile_element"))
			{ return FeatureDefinition.getMobileelementFD(); }
		if (nameOfFD.equals("modified_base"))
			{ return FeatureDefinition.getModifiedbaseFD(); }
		if (nameOfFD.equals("mRNA"))
			{ return FeatureDefinition.getMRNAFD(); }
		if (nameOfFD.equals("ncRNA"))
			{ return FeatureDefinition.getNcRNAFD(); }
		if (nameOfFD.equals("N_region"))
			{ return FeatureDefinition.getNregionFD(); }
		if (nameOfFD.equals("old_sequence"))
			{ return FeatureDefinition.getOldsequenceFD(); }
		if (nameOfFD.equals("operon"))
			{ return FeatureDefinition.getOperonFD(); }
		if (nameOfFD.equals("oriT"))
			{ return FeatureDefinition.getOriTFD(); }
		if (nameOfFD.equals("polyA_signal"))
			{ return FeatureDefinition.getPolyAsignalFD(); }
		if (nameOfFD.equals("polyA_site"))
			{ return FeatureDefinition.getPolyAsiteFD(); }
		if (nameOfFD.equals("precursor_RNA"))
			{ return FeatureDefinition.getPrecursorRNAFD(); }
		if (nameOfFD.equals("prim_transcript"))
			{ return FeatureDefinition.getPrimtranscriptFD(); }
		if (nameOfFD.equals("primer_bind"))
			{ return FeatureDefinition.getPrimerbindFD(); }
		if (nameOfFD.equals("promoter"))
			{ return FeatureDefinition.getPromoterFD(); }
		if (nameOfFD.equals("protein_bind"))
			{ return FeatureDefinition.getProteinbindFD(); }
		if (nameOfFD.equals("RBS"))
			{ return FeatureDefinition.getRBSFD(); }
		if (nameOfFD.equals("repeat_region"))
			{ return FeatureDefinition.getRepeatregionFD(); }
		if (nameOfFD.equals("rep_origin"))
			{ return FeatureDefinition.getReporiginFD(); }
		if (nameOfFD.equals("rRNA"))
			{ return FeatureDefinition.getRRNAFD(); }
		if (nameOfFD.equals("S_region"))
			{ return FeatureDefinition.getSregionFD(); }
		if (nameOfFD.equals("sig_peptide"))
			{ return FeatureDefinition.getSigpeptideFD(); }
		if (nameOfFD.equals("source"))
			{ return FeatureDefinition.getSourceFD(); }
		if (nameOfFD.equals("stem_loop"))
			{ return FeatureDefinition.getStemloopFD(); }
		if (nameOfFD.equals("STS"))
			{ return FeatureDefinition.getSTSFD(); }
		if (nameOfFD.equals("TATA_signal"))
			{ return FeatureDefinition.getTATAsignalFD(); }
		if (nameOfFD.equals("terminator"))
			{ return FeatureDefinition.getTerminatorFD(); }
		if (nameOfFD.equals("tmRNA"))
			{ return FeatureDefinition.getTmRNAFD(); }
		if (nameOfFD.equals("transit_peptide"))
			{ return FeatureDefinition.getTransitpeptideFD(); }
		if (nameOfFD.equals("tRNA"))
			{ return FeatureDefinition.getTRNAFD(); }
		if (nameOfFD.equals("unsure"))
			{ return FeatureDefinition.getUnsureFD(); }
		if (nameOfFD.equals("V_region"))
			{ return FeatureDefinition.getVregionFD(); }
		if (nameOfFD.equals("V_segment"))
			{ return FeatureDefinition.getVsegmentFD(); }
		if (nameOfFD.equals("variation"))
			{ return FeatureDefinition.getVariationFD(); }
		if (nameOfFD.equals("3'UTR"))
			{ return FeatureDefinition.get3UTRFD(); }
		if (nameOfFD.equals("5'UTR"))
			{ return FeatureDefinition.get5UTRFD(); }
		if (nameOfFD.equals("-10_signal"))
			{ return FeatureDefinition.get10signalFD(); }
		return null;
	}
	
	private static FeatureDefinition getAttenuatorFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "attenuator");
		toReturn.properties[2] = "1) region of DNA at which regulation of termination of"+
			"transcription occurs, which controls the expression"+
			"of some bacterial operons;"+
			"2) sequence segment located between the promoter and the"+
			"first structural gene that causes partial termination"+
			"of transcription"; /** Definition */
		toReturn.properties[3] = "prokaryotes"; /** Organism scope */
		toReturn.properties[4] = "DNA"; /** Molecule scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("phenotype", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getCregionFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "C_region");
		toReturn.properties[1] = "CDS"; /** Parent Key */
		toReturn.properties[2] = "constant region of immunoglobulin light and heavy "+
			"chains, and T-cell receptor alpha, beta, and gamma "+
			"chains; includes one or more exons depending on the "+
			"particular chain"; /** Definition */
		toReturn.properties[3] = "eukaryotes"; /** Organism scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getCAATsignalFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "CAAT_signal");
		toReturn.properties[2] = "CAAT box; part of a conserved sequence located about 75"+
			"bp up-stream of the start point of eukaryotic"+
			"transcription units which may be involved in RNA"+
			"polymerase binding; consensus=GG(C or T)CAATCT [1,2]."; /** Definition */
		toReturn.properties[3] = "eukaryotes and eukaryotic viruses"; /** Organism scope */
		toReturn.properties[4] = "DNA"; /** Molecule scope */
		toReturn.properties[5] = "[1]  Efstratiadis, A.  et al.  Cell 21, 653-668 (1980)"+
			"[2]  Nevins, J.R.  \"The pathway of eukaryotic mRNA formation\"  "+
			"Ann Rev Biochem 52, 441-466 (1983)"; /** References */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		return toReturn;
	}



	private static FeatureDefinition getCDSFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "CDS");
		toReturn.properties[2] = "coding sequence; sequence of nucleotides that"+
			"corresponds with the sequence of amino acids in a"+
			"protein (location includes stop codon); "+
			"feature includes amino acid conceptual translation."; /** Definition */
		toReturn.properties[6] = "/codon_start has valid value of 1 or 2 or 3, indicating"+
			"the offset at which the first complete codon of a coding"+
			"feature can be found, relative to the first base of"+
			"that feature;"+
			"/transl_table defines the genetic code table used if"+
			"other than the universal genetic code table;"+
			"genetic code exceptions outside the range of the specified"+
			"tables are reported in /codon or /transl_except qualifiers"+
			"/protein_id consists of a stable ID portion (3+5 format"+
			"with 3 position letters and 5 numbers) plus a version "+
			"number after the decimal point; when the protein "+
			"sequence encoded by the CDS changes, only the version "+
			"number of the /protein_id value is incremented; the"+
			"stable part of the /protein_id remains unchanged and as "+
			"a result will permanently be associated with a given "+
			"protein; "; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("artificial_location", "\"[artificial_location_value]\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("codon_start", "<1 or 2 or 3>");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("EC_number", "\"text\"");
		toReturn.optionnal.put("exception", "\"[exception_value]\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("number", "unquoted text (single token)");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("protein_id", "\"<identifier>\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		toReturn.optionnal.put("translation", "\"text\"");
		toReturn.optionnal.put("transl_except", "(pos:<base_range>,aa:<amino_acid>)");
		toReturn.optionnal.put("transl_table ", "<integer>");
		return toReturn;
	}



	private static FeatureDefinition getDloopFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "D-loop");
		toReturn.properties[2] = "displacement loop; a region within mitochondrial DNA in"+
			"which a short stretch of RNA is paired with one strand"+
			"of DNA, displacing the original partner DNA strand in"+
			"this region; also used to describe the displacement of a"+
			"region of one strand of duplex DNA by a single stranded"+
			"invader in the reaction catalyzed by RecA protein"; /** Definition */
		toReturn.properties[4] = "DNA"; /** Molecule scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		return toReturn;
	}



	private static FeatureDefinition getDsegmentFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "D_segment");
		toReturn.properties[2] = "Diversity segment of immunoglobulin heavy chain, and "+
			"T-cell receptor beta chain;  "; /** Definition */
		toReturn.properties[3] = "eukaryotes"; /** Organism scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getEnhancerFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "enhancer");
		toReturn.properties[2] = "a cis-acting sequence that increases the utilization of"+
			"(some)  eukaryotic promoters, and can function in either"+
			"orientation and in any location (upstream or downstream)"+
			"relative to the promoter;"; /** Definition */
		toReturn.properties[3] = "eukaryotes and eukaryotic viruses"; /** Organism scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("bound_moiety", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"  ");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getExonFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "exon");
		toReturn.properties[2] = "region of genome that codes for portion of spliced mRNA, "+
			"rRNA and tRNA; may contain 5'UTR, all CDSs and 3' UTR; "; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("EC_number", "\"text\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("number", "unquoted text (single token)");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getGapFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "gap");
		toReturn.properties[2] = "gap in the sequence"; /** Definition */
		toReturn.properties[6] = "the location span of the gap feature for an unknown "+
			"gap is 100 bp, with the 100 bp indicated as 100 \"n\"'s in "+
			"the sequence.  Where estimated length is indicated by "+
			"an integer, this is indicated by the same number of "+
			"\"n\"'s in the sequence. "+
			"No upper or lower limit is set on the size of the gap."; /** Comment */
		toReturn.mandatory.put("estimated_length", "unknown or <integer>");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getGCsignalFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "GC_signal");
		toReturn.properties[2] = "GC box; a conserved GC-rich region located upstream of"+
			"the start point of eukaryotic transcription units which"+
			"may occur in multiple copies or in either orientation;"+
			"consensus=GGGCGG;"; /** Definition */
		toReturn.properties[3] = "eukaryotes and eukaryotic viruses"; /** Organism scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		return toReturn;
	}



	private static FeatureDefinition getGeneFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "gene");
		toReturn.properties[2] = "region of biological interest identified as a gene "+
			"and for which a name has been assigned;"; /** Definition */
		toReturn.properties[6] = "the gene feature describes the interval of DNA that "+
			"corresponds to a genetic trait or phenotype; the feature is,"+
			"by definition, not strictly bound to it's positions at the "+
			"ends;  it is meant to represent a region where the gene is "+
			"located."+
			""; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("phenotype", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getIDNAFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "iDNA");
		toReturn.properties[2] = "intervening DNA; DNA which is eliminated through any of"+
			"several kinds of recombination;"; /** Definition */
		toReturn.properties[4] = "DNA"; /** Molecule scope */
		toReturn.properties[6] = "e.g., in the somatic processing of immunoglobulin genes."; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("number", "unquoted text (single token)");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getIntronFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "intron");
		toReturn.properties[2] = "a segment of DNA that is transcribed, but removed from"+
			"within the transcript by splicing together the sequences"+
			"(exons) on either side of it;"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("number", "unquoted text (single token)");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getJsegmentFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "J_segment");
		toReturn.properties[1] = "CDS"; /** Parent Key */
		toReturn.properties[2] = "joining segment of immunoglobulin light and heavy "+
			"chains, and T-cell receptor alpha, beta, and gamma "+
			"chains;  "; /** Definition */
		toReturn.properties[3] = "eukaryotes"; /** Organism scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getLTRFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "LTR");
		toReturn.properties[2] = "long terminal repeat, a sequence directly repeated at"+
			"both ends of a defined sequence, of the sort typically"+
			"found in retroviruses;"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getMatpeptideFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "mat_peptide");
		toReturn.properties[2] = "mature peptide or protein coding sequence; coding"+
			"sequence for the mature or final peptide or protein"+
			"product following post-translational modification; the"+
			"location does not include the stop codon (unlike the"+
			"corresponding CDS);"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("EC_number", "\"text\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getMiscbindingFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "misc_binding");
		toReturn.properties[2] = "site in nucleic acid which covalently or non-covalently"+
			"binds another moiety that cannot be described by any"+
			"other binding key (primer_bind or protein_bind);"; /** Definition */
		toReturn.properties[6] = "note that the key RBS is used for ribosome binding sites"; /** Comment */
		toReturn.mandatory.put("bound_moiety", "\"text\"");
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		return toReturn;
	}



	private static FeatureDefinition getMiscdifferenceFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "misc_difference");
		toReturn.properties[2] = "feature sequence is different from that presented "+
			"in the entry and cannot be described by any other "+
			"Difference key (unsure, old_sequence, "+
			"variation, or modified_base);"; /** Definition */
		toReturn.properties[6] = "the misc_difference feature key should be used to "+
			"describe variability that arises as a result of "+
			"genetic manipulation (e.g. site directed mutagenesis);"+
			"use /replace=\"\" to annotate deletion, e.g. "+
			"misc_difference 412..433"+
			"/replace=\"\"  "; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("clone", "\"text\"");
		toReturn.optionnal.put("compare", "[accession-number.sequence-version]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\" ");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("phenotype", "\"text\"");
		toReturn.optionnal.put("replace", "\"text\" ");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getMiscfeatureFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "misc_feature");
		toReturn.properties[2] = "region of biological interest which cannot be described"+
			"by any other feature key; a new or rare feature;"; /** Definition */
		toReturn.properties[6] = "this key should not be used when the need is merely to "+
			"mark a region in order to comment on it or to use it in "+
			"another feature's location"; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("number", "unquoted text (single token)");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("phenotype", "\"text\"");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getMiscrecombFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "misc_recomb");
		toReturn.properties[2] = "site of any generalized, site-specific or replicative"+
			"recombination event where there is a breakage and"+
			"reunion of duplex DNA that cannot be described by other"+
			"recombination keys or qualifiers of source key "+
			"(/proviral);"; /** Definition */
		toReturn.properties[4] = "DNA"; /** Molecule scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getMiscRNAFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "misc_RNA");
		toReturn.properties[2] = "any transcript or RNA product that cannot be defined by"+
			"other RNA keys (prim_transcript, precursor_RNA, mRNA,"+
			"5'UTR, 3'UTR, exon, CDS, sig_peptide, transit_peptide,"+
			"mat_peptide, intron, polyA_site, ncRNA, rRNA and tRNA);"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getMiscsignalFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "misc_signal");
		toReturn.properties[2] = "any region containing a signal controlling or altering"+
			"gene function or expression that cannot be described by"+
			"other signal keys (promoter, CAAT_signal, TATA_signal,"+
			"-35_signal, -10_signal, GC_signal, RBS, polyA_signal,"+
			"enhancer, attenuator, terminator, and rep_origin)."; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("phenotype", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getMiscstructureFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "misc_structure");
		toReturn.properties[2] = "any secondary or tertiary nucleotide structure or "+
			"conformation that cannot be described by other Structure"+
			"keys (stem_loop and D-loop);"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getMobileelementFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "mobile_element");
		toReturn.properties[2] = "region of genome containing mobile elements;"; /** Definition */
		toReturn.mandatory.put("mobile_element_type", ":\"<mobile_element_type>");
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\" ");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("rpt_family", "\"text\"");
		toReturn.optionnal.put("rpt_type", "<repeat_type>");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getModifiedbaseFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "modified_base");
		toReturn.properties[2] = "the indicated nucleotide is a modified nucleotide and"+
			"should be substituted for by the indicated molecule"+
			"(given in the mod_base qualifier value)"+
			""; /** Definition */
		toReturn.properties[6] = "value is limited to the restricted vocabulary for "+
			"modified base abbreviations;"; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("frequency", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		return toReturn;
	}



	private static FeatureDefinition getMRNAFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "mRNA");
		toReturn.properties[2] = "messenger RNA; includes 5'untranslated region (5'UTR),"+
			"coding sequences (CDS, exon) and 3'untranslated region"+
			"(3'UTR);"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("artificial_location", "\"[artificial_location_value]\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getNcRNAFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "ncRNA");
		toReturn.properties[2] = "a non-protein-coding gene, other than ribosomal RNA and"+
			"transfer RNA, the functional molecule of which is the RNA"+
			"transcript;"; /** Definition */
		toReturn.properties[6] = "the ncRNA feature is not used for ribosomal and transfer"+
			"RNA annotation, for which the rRNA and tRNA feature keys"+
			"should be used, respectively;"; /** Comment */
		toReturn.mandatory.put("ncRNA_class", "\"TYPE\"");
		toReturn.mandatory.put("citation", "[number]");
		toReturn.mandatory.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.mandatory.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.mandatory.put("function", "\"text\"");
		toReturn.mandatory.put("gene", "\"text\"");
		toReturn.mandatory.put("gene_synonym", "\"text\"");
		toReturn.mandatory.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.mandatory.put("locus_tag", "\"text\" (single token)");
		toReturn.mandatory.put("map", "\"text\"");
		toReturn.mandatory.put("note", "\"text\"");
		toReturn.mandatory.put("old_locus_tag", "\"text\" (single token)");
		toReturn.mandatory.put("operon", "\"text\"");
		toReturn.mandatory.put("product", "\"text\"");
		toReturn.mandatory.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getNregionFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "N_region");
		toReturn.properties[1] = "CDS"; /** Parent Key */
		toReturn.properties[2] = "extra nucleotides inserted between rearranged "+
			"immunoglobulin segments."; /** Definition */
		toReturn.properties[3] = "eukaryotes"; /** Organism scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getOldsequenceFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "old_sequence");
		toReturn.properties[2] = "the presented sequence revises a previous version of the"+
			"sequence at this location;"; /** Definition */
		toReturn.properties[6] = "/replace=\"\" is used to annotate deletion, e.g. "+
			"old_sequence 12..15"+
			"/replace=\"\" "+
			"NOTE: This feature key is not valid in entries/records"+
			"created from 15-Oct-2007."; /** Comment */
		toReturn.mandatory.put("citation", "[number]");
		toReturn.mandatory.put("compare", "[accession-number.sequence-version]");
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("replace", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getOperonFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "operon");
		toReturn.properties[2] = "region containing polycistronic transcript                     "+
			"containing genes that encode enzymes that are "+
			"in the same metabolic pathway and regulatory sequences "; /** Definition */
		toReturn.mandatory.put("operon", "\"text\"");
		toReturn.mandatory.put("citation", "[number]");
		toReturn.mandatory.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.mandatory.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.mandatory.put("function", "\"text\"");
		toReturn.mandatory.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.mandatory.put("map", "\"text\"");
		toReturn.mandatory.put("note", "\"text\"");
		toReturn.mandatory.put("phenotype", "\"text\"");
		toReturn.mandatory.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getOriTFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "oriT");
		toReturn.properties[2] = "origin of transfer; region of a DNA molecule where transfer is"+
			"initiated during the process of conjugation or mobilization"; /** Definition */
		toReturn.properties[6] = "rep_origin should be used for origins of replication; "+
			"/direction has legal values RIGHT, LEFT and BOTH, however only                "+
			"RIGHT and LEFT are valid when used in conjunction with the oriT  "+
			"feature;"+
			"origins of transfer can be present in the chromosome; "+
			"plasmids can contain multiple origins of transfer"; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("bound_moiety", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\" ");
		toReturn.optionnal.put("direction", "value");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("rpt_family", "\"text\"");
		toReturn.optionnal.put("rpt_type", "<repeat_type>");
		toReturn.optionnal.put("rpt_unit_range", "<base_range>");
		toReturn.optionnal.put("rpt_unit_seq", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getPolyAsignalFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "polyA_signal");
		toReturn.properties[2] = "recognition region necessary for endonuclease cleavage"+
			"of an RNA transcript that is followed by polyadenylation;"+
			"consensus=AATAAA [1];"; /** Definition */
		toReturn.properties[3] = "eukaryotes and eukaryotic viruses"; /** Organism scope */
		toReturn.properties[5] = "[1] Proudfoot, N. and Brownlee, G.G. Nature 263, 211-214"+
			"(1976)"; /** References */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		return toReturn;
	}



	private static FeatureDefinition getPolyAsiteFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "polyA_site");
		toReturn.properties[2] = "site on an RNA transcript to which will be added adenine"+
			"residues by post-transcriptional polyadenylation;"; /** Definition */
		toReturn.properties[3] = "eukaryotes and eukaryotic viruses"; /** Organism scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\" ");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		return toReturn;
	}



	private static FeatureDefinition getPrecursorRNAFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "precursor_RNA");
		toReturn.properties[2] = "any RNA species that is not yet the mature RNA product;"+
			"may include 5' untranslated region (5'UTR), coding"+
			"sequences (CDS, exon), intervening sequences (intron)"+
			"and 3' untranslated region (3'UTR);"; /** Definition */
		toReturn.properties[6] = "used for RNA which may be the result of "+
			"post-transcriptional processing;  if the RNA in question "+
			"is known not to have been processed, use the "+
			"prim_transcript key."; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"  ");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getPrimtranscriptFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "prim_transcript");
		toReturn.properties[2] = "primary (initial, unprocessed) transcript;  includes 5'"+
			"untranslated region (5'UTR), coding sequences"+
			"(CDS, exon), intervening sequences (intron) and 3'"+
			"untranslated region (3'UTR);"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getPrimerbindFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "primer_bind");
		toReturn.properties[2] = "non-covalent primer binding site for initiation of"+
			"replication, transcription, or reverse transcription;"+
			"includes site(s) for synthetic e.g., PCR primer elements;"; /** Definition */
		toReturn.properties[6] = "used to annotate the site on a given sequence to which a primer "+
			"molecule binds - not intended to represent the sequence of the"+
			"primer molecule itself; PCR components and reaction times may "+
			"be stored under the \"/PCR_conditions\" qualifier; "+
			"since PCR reactions most often involve pairs of primers,"+
			"a single primer_bind key may use the order() operator"+
			"with two locations, or a pair of primer_bind keys may be"+
			"used."; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		toReturn.optionnal.put("PCR_conditions", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getPromoterFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "promoter");
		toReturn.properties[2] = "region on a DNA molecule involved in RNA polymerase"+
			"binding to initiate transcription;"; /** Definition */
		toReturn.properties[4] = "DNA"; /** Molecule scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("bound_moiety", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("phenotype", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getProteinbindFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "protein_bind");
		toReturn.properties[2] = "non-covalent protein binding site on nucleic acid;"; /** Definition */
		toReturn.properties[6] = "note that RBS is used for ribosome binding sites."; /** Comment */
		toReturn.mandatory.put("bound_moiety", "\"text\"");
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getRBSFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "RBS");
		toReturn.properties[2] = "ribosome binding site;"; /** Definition */
		toReturn.properties[5] = "[1] Shine, J. and Dalgarno, L.  Proc Natl Acad Sci USA"+
			"71, 1342-1346 (1974)"+
			"[2] Gold, L. et al.  Ann Rev Microb 35, 365-403 (1981)"; /** References */
		toReturn.properties[6] = "in prokaryotes, known as the Shine-Dalgarno sequence: is"+
			"located 5 to 9 bases upstream of the initiation codon;"+
			"consensus GGAGGT [1,2]."; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getRepeatregionFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "repeat_region");
		toReturn.properties[2] = "region of genome containing repeating units;"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\" ");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("rpt_family", "\"text\"");
		toReturn.optionnal.put("rpt_type", "<repeat_type>");
		toReturn.optionnal.put("rpt_unit_range", "<base_range>");
		toReturn.optionnal.put("rpt_unit_seq", "\"text\"");
		toReturn.optionnal.put("satellite", "\"<satellite_type>[:<class>][ <identifier>]\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getReporiginFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "rep_origin");
		toReturn.properties[2] = "origin of replication; starting site for duplication of"+
			"nucleic acid to give two identical copies; "; /** Definition */
		toReturn.properties[6] = "/direction has valid values: RIGHT, LEFT, or BOTH."; /** Comment */
		return toReturn;
	}



	private static FeatureDefinition getRRNAFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "rRNA");
		toReturn.properties[2] = "mature ribosomal RNA; RNA component of the"+
			"ribonucleoprotein particle (ribosome) which assembles"+
			"amino acids into proteins."; /** Definition */
		toReturn.properties[6] = "rRNA sizes should be annotated with the /product"+
			"Qualifier.   "; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getSregionFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "S_region");
		toReturn.properties[1] = "misc_signal"; /** Parent Key */
		toReturn.properties[2] = "switch region of immunoglobulin heavy chains;  "+
			"involved in the rearrangement of heavy chain DNA leading "+
			"to the expression of a different immunoglobulin class "+
			"from the same B-cell;"; /** Definition */
		toReturn.properties[3] = "eukaryotes"; /** Organism scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getSigpeptideFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "sig_peptide");
		toReturn.properties[2] = "signal peptide coding sequence; coding sequence for an"+
			"N-terminal domain of a secreted protein; this domain is"+
			"involved in attaching nascent polypeptide to the"+
			"membrane leader sequence;"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getSourceFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "source");
		toReturn.properties[2] = "identifies the biological source of the specified span of"+
			"the sequence; this key is mandatory; more than one source"+
			"key per sequence is allowed; every entry/record will have, as a"+
			"minimum, either a single source key spanning the entire"+
			"sequence or multiple source keys, which together, span the"+
			"entire sequence."; /** Definition */
		toReturn.properties[4] = "any"; /** Molecule scope */
		toReturn.properties[6] = "transgenic sequences must have at least two source feature"+
			"keys; in a transgenic sequence the source feature key"+
			"describing the organism that is the recipient of the DNA"+
			"must span the entire sequence;"+
			"see Appendix IV /organelle for a list of <organelle_value>"; /** Comment */
		toReturn.mandatory.put("organism", "\"text\"");
		toReturn.mandatory.put("mol_type", "\"genomic DNA\", \"genomic RNA\", \"mRNA\", \"tRNA\",");
		toReturn.optionnal.put("bio_material", "\"[<institution-code>:[<collection-code>:]]<material_id>\"");
		toReturn.optionnal.put("cell_line", "\"text\"");
		toReturn.optionnal.put("cell_type", "\"text\"");
		toReturn.optionnal.put("chromosome", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("clone", "\"text\"");
		toReturn.optionnal.put("clone_lib", "\"text\"");
		toReturn.optionnal.put("collected_by", "\"text\" ");
		toReturn.optionnal.put("collection_date", "\"text\"");
		toReturn.optionnal.put("country", "\"<country_value>[:<region>][, <locality>]\"");
		toReturn.optionnal.put("cultivar", "\"text\"");
		toReturn.optionnal.put("culture_collection", "\"<institution-code>:[<collection-code>:]<culture_id>\"");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("dev_stage", "\"text\"");
		toReturn.optionnal.put("ecotype", "\"text\"");
		toReturn.optionnal.put("frequency", "\"text\"");
		toReturn.optionnal.put("haplogroup", "\"text\"");
		toReturn.optionnal.put("haplotype", "\"text\"");
		toReturn.optionnal.put("host", "\"text\"");
		toReturn.optionnal.put("identified_by", "\"text\"");
		toReturn.optionnal.put("isolate", "\"text\"");
		toReturn.optionnal.put("isolation_source", "\"text\"");
		toReturn.optionnal.put("lab_host", "\"text\"");
		toReturn.optionnal.put("lat_lon", "\"text\"");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("mating_type", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("organelle", "<organelle_value>");
		toReturn.optionnal.put("PCR_primers", "\"[fwd_name: XXX, ]fwd_seq: xxxxx, ");
		toReturn.optionnal.put("plasmid", "\"text\"");
		toReturn.optionnal.put("pop_variant", "\"text\"");
		toReturn.optionnal.put("segment", "\"text\"");
		toReturn.optionnal.put("serotype", "\"text\"");
		toReturn.optionnal.put("serovar", "\"text\"");
		toReturn.optionnal.put("sex", "\"text\"");
		toReturn.optionnal.put("specimen_voucher", "\"[<institution-code>:[<collection-code>:]]<specimen_id>\"");
		toReturn.optionnal.put("strain", "\"text\"");
		toReturn.optionnal.put("sub_clone", "\"text\"");
		toReturn.optionnal.put("sub_species", "\"text\"");
		toReturn.optionnal.put("sub_strain", "\"text\"");
		toReturn.optionnal.put("tissue_lib", "\"text\"");
		toReturn.optionnal.put("tissue_type", "\"text\"");
		toReturn.optionnal.put("variety", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getStemloopFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "stem_loop");
		toReturn.properties[2] = "hairpin; a double-helical region formed by base-pairing"+
			"between adjacent (inverted) complementary sequences in a"+
			"single strand of RNA or DNA. "; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getSTSFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "STS");
		toReturn.properties[2] = "sequence tagged site; short, single-copy DNA sequence"+
			"that characterizes a mapping landmark on the genome and"+
			"can be detected by PCR; a region of the genome can be"+
			"mapped by determining the order of a series of STSs;"; /** Definition */
		toReturn.properties[4] = "DNA"; /** Molecule scope */
		toReturn.properties[6] = "STS location to include primer(s) in primer_bind key or"+
			"primers."; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getTATAsignalFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "TATA_signal");
		toReturn.properties[2] = "TATA box; Goldberg-Hogness box; a conserved AT-rich"+
			"septamer found about 25 bp before the start point of"+
			"each eukaryotic RNA polymerase II transcript unit which"+
			"may be involved in positioning the enzyme  for correct "+
			"initiation; consensus=TATA(A or T)A(A or T) [1,2];"; /** Definition */
		toReturn.properties[3] = "eukaryotes and eukaryotic viruses"; /** Organism scope */
		toReturn.properties[4] = "DNA"; /** Molecule scope */
		toReturn.properties[5] = "[1] Efstratiadis, A.  et al.  Cell 21, 653-668 (1980)"+
			"[2] Corden, J., et al.  \"Promoter sequences of"+
			"eukaryotic protein-encoding genes\"  Science 209,"+
			"1406-1414 (1980)"; /** References */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token) ");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		return toReturn;
	}



	private static FeatureDefinition getTerminatorFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "terminator");
		toReturn.properties[2] = "sequence of DNA located either at the end of the"+
			"transcript that causes RNA polymerase to terminate "+
			"transcription;"; /** Definition */
		toReturn.properties[4] = "DNA"; /** Molecule scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getTmRNAFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "tmRNA");
		toReturn.properties[2] = "transfer messenger RNA; tmRNA acts as a tRNA first,"+
			"and then as an mRNA that encodes a peptide tag; the"+
			"ribosome translates this mRNA region of tmRNA and attaches"+
			"the encoded peptide tag to the C-terminus of the"+
			"unfinished protein; this attached tag targets the protein for"+
			"destruction or proteolysis;"; /** Definition */
		toReturn.properties[6] = "the tmRNA feature key will become valid on 15-Dec-2007"; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		toReturn.optionnal.put("tag_peptide", "<base_range>");
		return toReturn;
	}



	private static FeatureDefinition getTransitpeptideFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "transit_peptide");
		toReturn.properties[2] = "transit peptide coding sequence; coding sequence for an"+
			"N-terminal domain of a nuclear-encoded organellar"+
			"protein; this domain is involved in post-translational"+
			"import of the protein into the organelle;"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getTRNAFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "tRNA");
		toReturn.properties[2] = "mature transfer RNA, a small RNA molecule (75-85 bases"+
			"long) that mediates the translation of a nucleic acid"+
			"sequence into an amino acid sequence;"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("anticodon", "(pos:<base_range>,aa:<amino_acid>)");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getUnsureFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "unsure");
		toReturn.properties[2] = "author is unsure of exact sequence in this region;"; /** Definition */
		toReturn.properties[6] = "use /replace=\"\" to annotate deletion, e.g. "+
			"Unsure      11..15"+
			"/replace=\"\"  "; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("compare", "[accession-number.sequence-version]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("replace", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getVregionFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "V_region");
		toReturn.properties[1] = "CDS"; /** Parent Key */
		toReturn.properties[2] = "variable region of immunoglobulin light and heavy"+
			"chains, and T-cell receptor alpha, beta, and gamma"+
			"chains;  codes for the variable amino terminal portion;"+
			"can be composed of V_segments, D_segments, N_regions,"+
			"and J_segments;"; /** Definition */
		toReturn.properties[3] = "eukaryotes"; /** Organism scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getVsegmentFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "V_segment");
		toReturn.properties[1] = "CDS"; /** Parent Key */
		toReturn.properties[2] = "variable segment of immunoglobulin light and heavy"+
			"chains, and T-cell receptor alpha, beta, and gamma"+
			"chains; codes for most of the variable region (V_region)"+
			"and the last few amino acids of the leader peptide;"; /** Definition */
		toReturn.properties[3] = "eukaryotes"; /** Organism scope */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition getVariationFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "variation");
		toReturn.properties[2] = "a related strain contains stable mutations from the same"+
			"gene (e.g., RFLPs, polymorphisms, etc.) which differ"+
			"from the presented sequence at this location (and"+
			"possibly others);"; /** Definition */
		toReturn.properties[6] = "used to describe alleles, RFLP's,and other naturally occurring "+
			"mutations and  polymorphisms; variability arising as a result "+
			"of genetic manipulation (e.g. site directed mutagenesis) should "+
			"be described with the misc_difference feature;"+
			"use /replace=\"\" to annotate deletion, e.g. "+
			"variation   4..5"+
			"/replace=\"\"  "; /** Comment */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("compare", "[accession-number.sequence-version]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("frequency", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("phenotype", "\"text\"");
		toReturn.optionnal.put("product", "\"text\"");
		toReturn.optionnal.put("replace", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition get3UTRFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "3'UTR");
		toReturn.properties[2] = "region at the 3' end of a mature transcript (following "+
			"the stop codon) that is not translated into a protein;"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}



	private static FeatureDefinition get5UTRFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "5'UTR");
		toReturn.properties[2] = "region at the 5' end of a mature transcript (preceding "+
			"the initiation codon) that is not translated into a "+
			"protein;"; /** Definition */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("function", "\"text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}


	private static FeatureDefinition get10signalFD() {
		FeatureDefinition toReturn = new FeatureDefinition( "-10_signal");
		toReturn.properties[2] = "Pribnow box; a conserved region about 10 bp upstream of"+
			"the start point of bacterial transcription units which"+
			"may be involved in binding RNA polymerase;"+
			"consensus=TAtAaT [1,2,3,4];"; /** Definition */
		toReturn.properties[3] = "prokaryotes"; /** Organism scope */
		toReturn.properties[4] = "DNA"; /** Molecule scope */
		toReturn.properties[5] = "[1] Schaller, H., Gray, C., and Hermann, K.  Proc Natl"+
			"Acad Sci USA 72, 737-741 (1974)"+
			"[2] Pribnow, D.  Proc Natl Acad Sci USA 72, 784-788 (1974)"+
			"[3] Hawley, D.K. and McClure, W.R.  \"Compilation and"+
			"analysis of Escherichia coli promoter DNA sequences\" "+
			"Nucl Acid Res 11, 2237-2255 (1983)"+
			"[4] Rosenberg, M. and Court, D.  \"Regulatory sequences"+
			"involved in the promotion and termination of RNA"+
			"transcription\"  Ann Rev Genet 13, 319-353 (1979)"; /** References */
		toReturn.optionnal.put("allele", "\"text\"");
		toReturn.optionnal.put("citation", "[number]");
		toReturn.optionnal.put("db_xref", "\"<database>:<identifier>\"");
		toReturn.optionnal.put("experiment", "\"[CATEGORY:]text\"");
		toReturn.optionnal.put("gene", "\"text\"");
		toReturn.optionnal.put("gene_synonym", "\"text\"");
		toReturn.optionnal.put("inference", "\"[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]\"");
		toReturn.optionnal.put("locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("map", "\"text\"");
		toReturn.optionnal.put("note", "\"text\"");
		toReturn.optionnal.put("old_locus_tag", "\"text\" (single token)");
		toReturn.optionnal.put("operon", "\"text\"");
		toReturn.optionnal.put("standard_name", "\"text\"");
		return toReturn;
	}




	
}
