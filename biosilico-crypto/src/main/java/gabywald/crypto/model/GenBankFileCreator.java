package gabywald.crypto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.GenBankFormat;
import gabywald.crypto.data.composition.Feature;
import gabywald.crypto.data.composition.FeatureDefinition;
import gabywald.crypto.data.composition.Sequence;
import gabywald.crypto.data.ioput.AFileCryptoCreator;
import gabywald.crypto.data.ioput.BiologicalFileCreatorHelper;
import gabywald.global.data.StringUtils;

/**
 * Aim of this class is to generate a GenBank file with encrypted data. 
 * <br>Data is encrypted when included (content and path of file, respectively as proteomic and nucleotidic data). 
 * <br>Encryption according to current "genetic encryption". 
 * @author Gabriel Chandesris (2011, 2020, 2022, 2026)
 */
public class GenBankFileCreator extends AFileCryptoCreator {
	
	private GenBankFormat genBank;
	
	public GenBankFileCreator() { super("", ""); }

	public GenBankFileCreator(String path, String content) { super(path, content); }
	
	private void initialize() {
		this.genBank			= new GenBankFormat();
		String identification	= BiologicalUtils.generateIdentifier();
		
		/** LOCUS PART. */
		this.genBank.setIdentification(identification);
		
		int basePairNumber = 0;
		for (int i = 0 ; i < this.getEncodedCont().size() ; i++) 
			{ basePairNumber += this.getEncodedCont().get(i).length(); }
		this.genBank.setBasePairNumber(""+basePairNumber);
		
		String primaryType = BiologicalFileCreatorHelper.PRIMARY_TYPE
			[BiologicalUtils.randomValue(BiologicalFileCreatorHelper.PRIMARY_TYPE.length)];
		this.genBank.setPrimaryType(primaryType);
		this.genBank.setSecondaryType(BiologicalFileCreatorHelper.SECONDARY_TYPE
				[BiologicalUtils.randomValue(BiologicalFileCreatorHelper.SECONDARY_TYPE.length)]);
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
		this.genBank.setOrganism(BiologicalFileCreatorHelper.createOrganism());
		
		/** Some datas... */
		String location = BiologicalUtils.generateLocationOfSequence();
		this.genBank.setKeywords("");
		this.genBank.setDefinition(this.genBank.getOrganism().getSourceName() 
									+ " (" + location + "), " + primaryType + ".");
		this.genBank.setAccession(identification);
		this.genBank.setVersion(identification+"."+StringUtils.randomValue(5));
		
		/** References PART. */
		int numberOfRefs = StringUtils.randomValue(10)+1;
		for (int i = 0 ; (i < numberOfRefs) && (this.getEncodedCont().size() > 0) ; i++) {
			int selectCont	= StringUtils.randomValue(this.getEncodedCont().size());
			int start		= 0;
			int stopp		= this.getEncodedCont().get(selectCont).length();
			for (int j = 0 ; j < selectCont ; j++) 
				{ start += this.getEncodedCont().get(j).length(); }
			
			this.genBank.addReference(BiologicalFileCreatorHelper.createReference(i, year, start, stopp));
		}
		
		/** Sequence and Features PART. */
		String sequenceToRecord	= new String("");
		int start				= 0;
		for (int i = 0 ; i < this.getEncodedCont().size() ; i++) { 
			/** Append... */
			sequenceToRecord += this.getEncodedCont().get(i);
		
			int length	= this.getEncodedCont().get(i).length();
			String pos	= (start + 1)+".."+( start + 1 + length );
			if (this.getEncodedPath().size() > 0) {
				FeatureDefinition cds	= FeatureDefinition.getFromFactory("CDS");
				Feature featToAdd		= new Feature(cds, pos);
				featToAdd.addQualifier("codon_start", (start + 1)+"");
				featToAdd.addQualifier("gene", location);
				featToAdd.addQualifier("product", "*****"); /** XXX !! */
				if (this.getEncodedPath().get(i).length() != 0)
					{ featToAdd.addQualifier("translation", this.getEncodedPath().get(i)); }
				this.genBank.addFeature(featToAdd);
			} // END "if (this.getEncodedPath().size() > 0)"
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
	
	public String getFullEncryption() {
		this.initialize();
		// this.genBank.setSequence(new Sequence("", this.encodedContent));
		return this.genBank.toString();
	}
	
}
