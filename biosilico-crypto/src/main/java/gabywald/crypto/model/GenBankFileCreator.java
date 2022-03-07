package gabywald.crypto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.GenBankFormat;
import gabywald.crypto.data.composition.Author;
import gabywald.crypto.data.composition.Feature;
import gabywald.crypto.data.composition.FeatureDefinition;
import gabywald.crypto.data.composition.Organism;
import gabywald.crypto.data.composition.Reference;
import gabywald.crypto.data.composition.Sequence;
import gabywald.global.data.Utils;
import gabywald.global.data.samples.BioDataFile;
import gabywald.global.data.samples.UplinkDataFile;

/**
 * Aim of this class is to generate a GenBank file with encrypted data. 
 * <br>Data is encrypted when included (content and path of file, respectively as proteomic and nucleotidic data). 
 * <br>Encryption according to current "genetic encryption". 
 * @author Gabriel Chandesris (2011, 2020, 2022)
 */
public class GenBankFileCreator {
	private static final GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
	private static final GeneticTranslator forPathDirName = BiologicalUtils.getGenericCrypto(1);
	/** Primary Types of sequences [DNA|mRNA|genomicDNA|precursorRNA|cDNA[rRNA|tRNA|snRNA|snRNA]... */
	private static final String[] PRIMARY_TYPE = 
		{ "DNA" , "mRNA" , "DNA" , "mRNA" , "DNA" , "mRNA"
			, "DNA" , "mRNA" , "DNA" , "mRNA" , "DNA" , "mRNA"
			, "DNA" , "mRNA" , "DNA" , "mRNA" , "DNA" , "mRNA"
			, "DNA" , "mRNA" , "genomic DNA" , "precursor RNA"
			, "mRNA (cDNA)" , "ribosomal RNA" , "transfer RNA"
			, "small nuclear RNA" , "small cytoplasmic RNA" };
	
	/** Secondary Types of sequences [circular|linear|]... */
	private static final String[] SECONDARY_TYPE = 
		{ "" , "circular" , "linear" , "linear" , "linear" , "linear" };
	
	/** Type of publication. */
	private static final String[] PUBLICATION_TYPE = 
		{ /** "", 				"",				"", 			"", 			"", */
		  "Publications", 	"Publications",	"Publications", "Publications", "Publications", 
		  "Journal",		"Magazine", 	"Gazette", 		"Fanzine", 		"HackZine"};
	
	private GenBankFormat genBank;
	private List<String> encodedPath;
	private List<String> encodedContent;
	
	public GenBankFileCreator() 
		{ this("", ""); }

	public GenBankFileCreator(String content) 
		{ this("", content); }
	
	public GenBankFileCreator(String path, String content) {
		this.encodedPath	= new ArrayList<String>();
		this.encodedContent	= new ArrayList<String>();
		if ( (path != null) && (! path.equals("")) ) 
			{ this.setPath(path); }
		if ( (content != null) && (! content.equals("")) ) 
			{ this.setContent(content); }
	}
	
	private void initialize() {
		this.genBank			= new GenBankFormat();
		String identification	= BiologicalUtils.generateIdentifier();
		
		/** LOCUS PART. */
		this.genBank.setIdentification(identification);
		
		int basePairNumber = 0;
		for (int i = 0 ; i < this.encodedContent.size() ; i++) 
			{ basePairNumber += this.encodedContent.get(i).length(); }
		this.genBank.setBasePairNumber(""+basePairNumber);
		
		String primaryType = GenBankFileCreator.PRIMARY_TYPE
			[BiologicalUtils.randomValue(GenBankFileCreator.PRIMARY_TYPE.length)];
		this.genBank.setPrimaryType(primaryType);
		this.genBank.setSecondaryType(GenBankFileCreator.SECONDARY_TYPE
				[BiologicalUtils.randomValue(GenBankFileCreator.SECONDARY_TYPE.length)]);
		String[][] divisions = BiologicalUtils.GENEBANK_DIVISIONS; 
			// BioDataFile.getDivisionClass().getTable().split("\\s+|\\s+")[1];
		this.genBank.setDivision(divisions[BiologicalUtils.randomValue(divisions.length)][0]);
		String randomDate	= BiologicalUtils.getRandomDate();
		this.genBank.setDate(randomDate);
		
		Pattern gettingYear = Pattern.compile("([0-9])+\\-([A-Z]{3})\\-([0-9]{4})");
		Matcher matcherYear = gettingYear.matcher(randomDate);
		String yearToReUse	= (matcherYear.matches())?matcherYear.group(3):"2000";
		int year			= Integer.parseInt(yearToReUse);
		
		/** Taxonomy and Organism PART. */
		this.genBank.setOrganism(GenBankFileCreator.createOrganism());
		
		/** Some datas... */
		String location = BiologicalUtils.generateLocationOfSequence();
		this.genBank.setKeywords("");
		this.genBank.setDefinition(this.genBank.getOrganism().getSourceName() 
									+ " (" + location + "), " + primaryType + ".");
		this.genBank.setAccession(identification);
		this.genBank.setVersion(identification+"."+Utils.randomValue(5));
		
		/** References PART. */
		int numberOfRefs = Utils.randomValue(10)+1;
		for (int i = 0 ; (i < numberOfRefs) && (this.encodedContent.size() > 0) ; i++) {
			int selectCont	= Utils.randomValue(this.encodedContent.size());
			int start		= 0;
			int stopp		= this.encodedContent.get(selectCont).length();
			for (int j = 0 ; j < selectCont ; j++) 
				{ start += this.encodedContent.get(j).length(); }
			
			this.genBank.addReference(GenBankFileCreator.createReference(i, year, start, stopp));
		}
		
		/** Sequence and Features PART. */
		String sequenceToRecord	= new String("");
		int start				= 0;
		for (int i = 0 ; i < this.encodedContent.size() ; i++) { 
			/** Append... */
			sequenceToRecord += this.encodedContent.get(i);
		
			int length	= this.encodedContent.get(i).length();
			String pos	= (start + 1)+".."+( start + 1 + length );
			if (this.encodedPath.size() > 0) {
				FeatureDefinition cds	= FeatureDefinition.getFromFactory("CDS");
				Feature featToAdd		= new Feature(cds, pos);
				featToAdd.addQualifier("codon_start", (start + 1)+"");
				featToAdd.addQualifier("gene", location);
				featToAdd.addQualifier("product", "*****"); /** XXX !! */
				if (this.encodedPath.get(i).length() != 0)
					{ featToAdd.addQualifier("translation", this.encodedPath.get(i)); }
				this.genBank.addFeature(featToAdd);
			} // END "if (this.encodedPath.size() > 0)"
			start += length;
			FeatureDefinition src	= FeatureDefinition.getFromFactory("source");
			Feature srcToAdd		= new Feature(src, pos);
			srcToAdd.addQualifier("organism", this.genBank.getOrganism().getSourceName());
			srcToAdd.addQualifier("mol_type", primaryType); 
			this.genBank.addFeature(srcToAdd);
			FeatureDefinition gene	= FeatureDefinition.getFromFactory("gene");
			Feature geneToAdd		= new Feature(gene, pos);
			geneToAdd.addQualifier("gene", location);
			geneToAdd.addQualifier("note", "***** part [" + (i+1) + "] *****"); /** XXX !! */
			this.genBank.addFeature(geneToAdd);
		}
		this.genBank.setSequence(new Sequence("", sequenceToRecord));
		
		/** Base Counting Part !! */
		List<String> bases = new ArrayList<String>();
		for (int i = 0 ; i < sequenceToRecord.length() ; i++) {
			String element = sequenceToRecord.charAt(i) + "";
			if (!bases.contains(element) )	{ bases.add(element); }
		}
		bases.add("other");
		int[] basesCounts	= new int[bases.size()];
		String[] basesNames	= bases.toArray(new String[0]);
		for (int i = 0 ; i < sequenceToRecord.length() ; i++) {
			boolean counted	= false;/** 'other' counted separately */
			char toTest		= sequenceToRecord.charAt(i);
			for (int j = 0 ; (j < basesCounts.length - 1) 
					&& (!counted) ; j++) 
				{ if (toTest == basesNames[j].charAt(0)) 
					{ basesCounts[j]++;counted = true; } }
			if (!counted) { basesCounts[basesCounts.length-1]++; }
		}
		this.genBank.setBasesCountsAndNames(basesCounts, basesNames);
	}
	
	public static Organism createOrganism() {
		String[] orgaNames	= BioDataFile.getTableOfOrganism().getTable();
		int whichOrga		= Utils.randomValue(orgaNames.length);
		String[] organism	= orgaNames[whichOrga].split("\t");
		
		String organismName = organism[0];
		int directTaxonNum	= Integer.parseInt(organism[3].split("==")[0]);
		
		String[] taxxaNames	= BioDataFile.getTableOfTaxonomy().getTable();
		String[][] taxaTabl	= new String[taxxaNames.length][3];
		for (int i = 0 ; i < taxaTabl.length ; i++) 
			{ taxaTabl[i] = taxxaNames[i].split("\t"); }
		
		List<Integer> taxa	= new ArrayList<Integer>();
		taxa.add(new Integer(directTaxonNum));
		int taxaFatherID = directTaxonNum;
		while (taxaFatherID != -1) {
			boolean found = false;
			for (int i = 0 ; (i < taxaTabl.length) && (!found) ; i++) {
				/** Searching current ID... */
				int currentID = Integer.parseInt(taxaTabl[i][1]);
				if (currentID == taxaFatherID) {
					taxa.add(new Integer(currentID));
					/** ... to gain father's ID. */
					taxaFatherID = Integer.parseInt(taxaTabl[i][2]);
				}
			}
		}
		
		Organism orga = new Organism(organismName);
		orga.setOrganism(organismName);
		for (int i = taxa.size()-1 ; i >= 0 ; i--) {
			int currentID = taxa.get(i).intValue();
			if (currentID != -1) 
				{ orga.addLineage(taxaTabl[currentID][0]); }
		}
		
		return orga;
	}
	
	/**
	 * 
	 * @param n (int) Number of the reference. 
	 * @param year
	 * @param start
	 * @param stopp
	 * @return (Reference)
	 */
	public static Reference createReference(int n, int year, int start, int stopp) {
		Reference tmpRef = new Reference(n+1, start + 1, start + 1 + stopp);
		tmpRef.setTitle(GenBankFileCreator.createTitle() + " " + (n+1) ); /** XXX !! */
		
		String[] companya 	= UplinkDataFile.getCompanyAMore().getTable();
		String[] companyb 	= UplinkDataFile.getCompanyBMore().getTable();
		
		String journal		= companya[Utils.randomValue(companya.length)]+" "+companyb[Utils.randomValue(companyb.length)];
		String journalType	= GenBankFileCreator.PUBLICATION_TYPE[Utils.randomValue(GenBankFileCreator.PUBLICATION_TYPE.length)];
		journal				+= (journalType.length() > 0)?" "+journalType:"";
		journal				+= ". "+Utils.randomValue(Utils.randomValue(10000));
		if (Utils.randomValue(10000)%2 == 0) 
			{ journal		+= " ("+Utils.randomValue(Utils.randomValue(100))+")"; }
		journal				+= ", ";
		int startPage		= Utils.randomValue(10000);
		int stoppPage		= startPage+Utils.randomValue(Utils.randomValue(30));
		journal				+= startPage+"-"+stoppPage+" ("+(year-Utils.randomValue(Utils.randomValue(150)))+")";
		
		tmpRef.setJournal(journal);
		String[] surnames = UplinkDataFile.getSurNames().getTable();
		String[] fornames = UplinkDataFile.getForNamesMore().getTable();
		int numberOfAuthors = Utils.randomValue(10)+Utils.randomValue(10)+1;
		for (int j = 0 ; j < numberOfAuthors ; j++) {
			String name = surnames[Utils.randomValue(surnames.length)];
			String firstnames = fornames[Utils.randomValue(fornames.length)];
			Author tmpAut = new Author(name, firstnames);
			tmpRef.addAuthor(tmpAut);
		}
		
		if (Utils.randomValue(10000)%5 == 0) 
			{ tmpRef.setRemark("REVIEW"); }
		
		return tmpRef;
	}
	
	public static String createTitle() {
		String toReturn = new String("");
		
		/** TODO ... */
		toReturn = "title of the reference";
		
		return toReturn;
	}
	
	public String getFullEncryption() {
		this.initialize();
		// this.genBank.setSequence(new Sequence("", this.encodedContent));
		return this.genBank.toString();
	}
	
	public List<String> getEncodedPath() { return this.encodedPath; }
	public List<String> getEncodedCont() { return this.encodedContent; }
	
	public void setPathAndContent(String path, String content) {
		this.setPath(path);
		this.setContent(content);
	}
	
	private String setPath(String path) {
		this.encodedPath	= new ArrayList<String>();
		return this.addPath(path);
	}
	
	private String setContent(String content) {
		this.encodedContent	= new ArrayList<String>();
		return this.addContent(content);
	}
	
	public void addPathAndContent(String path, String content) { 
		this.addPath(path);
		this.addContent(content);
	}
	
	private String addPath(String path) {
		this.encodedPath.add(path.equals("") ? "" : GenBankFileCreator.forPathDirName.encode(path, 1) );
		return this.encodedPath.get(this.encodedPath.size() - 1);
	}
	
	private String addContent(String content) {
		this.encodedContent.add(content.equals("") ? "" : GenBankFileCreator.forFileContent.encode(content, 1) );
		return this.encodedContent.get(this.encodedContent.size() - 1);
	}
	
}
