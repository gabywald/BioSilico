package gabywald.crypto.data.ioput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.composition.Author;
import gabywald.crypto.data.composition.Organism;
import gabywald.crypto.data.composition.Reference;
import gabywald.crypto.model.GeneticTranslator;
import gabywald.global.data.StringUtils;
import gabywald.global.data.samples.BioDataFile;
import gabywald.global.data.samples.UplinkDataFile;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public abstract class BiologicalFileCreatorHelper {
	
	public static final GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
	public static final GeneticTranslator forPathDirName = BiologicalUtils.getGenericCrypto(1);
	
	/** Primary Types of sequences [DNA|mRNA|genomicDNA|precursorRNA|cDNA[rRNA|tRNA|snRNA|snRNA]... */
	public static final String[] PRIMARY_TYPE = { 
		"DNA" , "mRNA" , "DNA" , "mRNA" , "DNA" , "mRNA"
			, "DNA" , "mRNA" , "DNA" , "mRNA" , "DNA" , "mRNA"
			, "DNA" , "mRNA" , "DNA" , "mRNA" , "DNA" , "mRNA"
			, "DNA" , "mRNA" , "genomic DNA" , "precursor RNA"
			, "mRNA (cDNA)" , "ribosomal RNA" , "transfer RNA"
			, "small nuclear RNA" , "small cytoplasmic RNA" 
	};
	
	/** Secondary Types of sequences [circular|linear|]... */
	public static final String[] SECONDARY_TYPE = 
		{ "" , "circular" , "linear" , "linear" , "linear" , "linear" };
	
	/** Type of publication. */
	public static final String[] PUBLICATION_TYPE = { 
		"Publications", 	"Publications",	"Publications", "Publications", "Publications", 
		"Journal",			"Magazine", 	"Gazette", 		"Fanzine", 		"HackZine"
	};
	
	/** To avoid instanciation. */
	private BiologicalFileCreatorHelper()	{ ; }

	public static Organism createOrganism() {
		BioDataFile bdfOrganisms	= BioDataFile.getTableOfOrganism();
		BioDataFile bdfTaxonomy		= BioDataFile.getTableOfTaxonomy();
		try {
			bdfOrganisms.load();
			bdfTaxonomy.load();
		} catch (IOException e) {
			// e.printStackTrace();
			Logger.printlnLog(LoggerLevel.LL_ERROR, "BiologicalFileCreatorHelper createOrganism FAILED (load associated files) ! " + e.getMessage());
			return null;
		}
		
		String[] orgaNames	= bdfOrganisms.getChampsAsTable();
		int whichOrga		= StringUtils.randomValue(orgaNames.length);
		String[] organism	= orgaNames[whichOrga].split("\t");
		
		String organismName = organism[0];
		int directTaxonNum	= Integer.parseInt(organism[3].split("==")[0]);
		
		String[] taxxaNames	= bdfTaxonomy.getChampsAsTable();
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
	
	public static Reference createReference(int n, int year, int start, int stopp) {
		Reference tmpRef = new Reference(n+1, start + 1, start + stopp);
		tmpRef.setTitle(BiologicalFileCreatorHelper.createTitle() + " " + (n+1) ); /** XXX !! */
		
		UplinkDataFile udfCompanyA = UplinkDataFile.getCompanyAMore();
		UplinkDataFile udfCompanyB = UplinkDataFile.getCompanyBMore();
		UplinkDataFile udfSurNames = UplinkDataFile.getSurNames();
		UplinkDataFile udfForNamesMore = UplinkDataFile.getForNamesMore();
		
		try {
			udfCompanyA.load();
			udfCompanyB.load();
			udfSurNames.load();
			udfForNamesMore.load();
		} catch (IOException e) {
			// e.printStackTrace();
			Logger.printlnLog(LoggerLevel.LL_ERROR, "BiologicalFileCreatorHelper createReference FAILED (load associated files) ! " + e.getMessage());
			return null;
		}
		
		String[] companya 	= udfCompanyA.getChampsAsTable();
		String[] companyb 	= udfCompanyB.getChampsAsTable();
		
		String journal		= companya[StringUtils.randomValue(companya.length)]+" "+companyb[StringUtils.randomValue(companyb.length)];
		String journalType	= BiologicalFileCreatorHelper.PUBLICATION_TYPE[StringUtils.randomValue(BiologicalFileCreatorHelper.PUBLICATION_TYPE.length)];
		journal				+= (journalType.length() > 0)?" "+journalType:"";
		journal				+= ". "+StringUtils.randomValue(StringUtils.randomValue(10000));
		if (StringUtils.randomValue(10000)%2 == 0) 
			{ journal		+= " ("+StringUtils.randomValue(StringUtils.randomValue(100))+")"; }
		journal				+= ", ";
		int startPage		= StringUtils.randomValue(10000);
		int stoppPage		= startPage+StringUtils.randomValue(StringUtils.randomValue(30));
		journal				+= startPage+"-"+stoppPage+" ("+(year-StringUtils.randomValue(StringUtils.randomValue(150)))+")";
		
		tmpRef.setJournal(journal);
		String[] surnames = udfSurNames.getChampsAsTable();
		String[] fornames = udfForNamesMore.getChampsAsTable();
		int numberOfAuthors = StringUtils.randomValue(10)+StringUtils.randomValue(10)+1;
		for (int j = 0 ; j < numberOfAuthors ; j++) {
			String name = surnames[StringUtils.randomValue(surnames.length)];
			String firstnames = fornames[StringUtils.randomValue(fornames.length)];
			Author tmpAut = new Author(name, firstnames);
			tmpRef.addAuthor(tmpAut);
		}
		
		if (StringUtils.randomValue(10000)%5 == 0) 
			{ tmpRef.setRemark("REVIEW"); }
		
		return tmpRef;
	}
	
	public static String createTitle() {
		String toReturn = new String("");
		
		/** TODO ... */
		toReturn = "title of the reference";
		
		return toReturn;
	}
	
}
