package gabywald.crypto.data.ioput;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.EmblFormat;
import gabywald.crypto.data.composition.Feature;
import gabywald.crypto.data.composition.FeatureDefinition;
import gabywald.crypto.data.composition.Sequence;
import gabywald.crypto.model.ITranslator;
import gabywald.crypto.model.ITranslator.TranslatorEnum;
import gabywald.global.data.StringUtils;

/**
 * Aim of this class is to generate a EMBL file with encrypted data. 
 * <br>Data is encrypted when included (content and path of file, respectively as nucleotidic and proteomic data). 
 * <br>Encryption according to current "genetic encryption". 
 * @author Gabriel Chandesris (2020, 2026)
 */
public class EmblFileCreator extends BiologicalFileCreator {
	
	/**
	 * 
	 * @param forFiles
	 * @param forPathes
	 * @param which
	 */
	public EmblFileCreator(ITranslator forFiles, ITranslator forPathes, ITranslator.TranslatorEnum which) {
		super(forFiles, forPathes, which);
		this.bioFormat = new EmblFormat();
	}

	/**
	 * 
	 * @param bioencoder4file
	 * @param bioencoder4path
	 * @param which
	 */
	public EmblFileCreator(int bioencoder4file, int bioencoder4path, TranslatorEnum which) {
		super(bioencoder4file, bioencoder4path, which);
		this.bioFormat = new EmblFormat();
	}

	@Override
	protected void initialize() {
		String identification	= BiologicalUtils.generateIdentifier();
		
		/** LOCUS PART. */
		this.bioFormat.setIdentification(identification);
		this.bioFormat.setAccession(identification);
		
		int basePairNumber = 0;
		for (int i = 0 ; i < this.getEncodedCont().size() ; i++) 
			{ basePairNumber += this.getEncodedCont().get(i).length(); }
		this.bioFormat.setBasePairNumber(""+basePairNumber);
		
		String primaryType = BiologicalFileCreatorHelper.PRIMARY_TYPE
			[BiologicalUtils.randomValue(BiologicalFileCreatorHelper.PRIMARY_TYPE.length)];
		this.bioFormat.setPrimaryType(primaryType);
		this.bioFormat.setSecondaryType(BiologicalFileCreatorHelper.SECONDARY_TYPE
				[BiologicalUtils.randomValue(BiologicalFileCreatorHelper.SECONDARY_TYPE.length)]);
		String[][] divisions = BiologicalUtils.EMBL_DIVISIONS; 
			// BioDataFile.getDivisionClass().getTable().split("\\s+|\\s+")[1];
		this.bioFormat.setDivision(divisions[BiologicalUtils.randomValue(divisions.length)][0]);
		String randomDate	= BiologicalUtils.getRandomDate();
		this.bioFormat.setDate(randomDate);
		
		Pattern gettingYear = Pattern.compile("([0-9])+\\-([A-Z]{3})\\-([0-9]{4})");
		Matcher matcherYear = gettingYear.matcher(randomDate);
		String yearToReUse	= (matcherYear.matches())?matcherYear.group(3):"2000";
		int year			= Integer.parseInt(yearToReUse);
		
		/** Taxonomy and Organism PART. */
		this.bioFormat.setOrganism(BiologicalFileCreatorHelper.createOrganism());
		
		/** Some datas... */
		String location = BiologicalUtils.generateLocationOfSequence();
		this.bioFormat.setKeywords("");
		this.bioFormat.setDefinition(this.bioFormat.getOrganism().getSourceName() 
									+ " (" + location + "), " + primaryType + ".");
		this.bioFormat.setAccession(identification);
		this.bioFormat.setVersion(identification+"."+StringUtils.randomValue(5));
		
		/** References PART. */
		int numberOfRefs = StringUtils.randomValue(10)+1;
		for (int i = 0 ; (i < numberOfRefs) && (this.getEncodedCont().size() > 0) ; i++) {
			int selectCont	= StringUtils.randomValue(this.getEncodedCont().size());
			int start		= 0;
			int stopp		= this.getEncodedCont().get(selectCont).length();
			for (int j = 0 ; j < selectCont ; j++) 
				{ start += this.getEncodedCont().get(j).length(); }
			
			this.bioFormat.addReference(BiologicalFileCreatorHelper.createReference(i, year, start, stopp));
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
				this.bioFormat.addFeature(featToAdd);
			} // END "if (this.getEncodedPath().size() > 0)"
			start += length;
			FeatureDefinition src	= FeatureDefinition.getFromFactory("source");
			Feature srcToAdd		= new Feature(src, pos);
			srcToAdd.addQualifier("organism", this.bioFormat.getOrganism().getSourceName());
			srcToAdd.addQualifier("mol_type", primaryType); 
			this.bioFormat.addFeature(srcToAdd);
			FeatureDefinition gene	= FeatureDefinition.getFromFactory("gene");
			Feature geneToAdd		= new Feature(gene, pos);
			geneToAdd.addQualifier("gene", location);
			geneToAdd.addQualifier("note", "***** part [" + (i+1) + "] *****"); /** XXX !! */
			this.bioFormat.addFeature(geneToAdd);
		}
		this.bioFormat.setSequence(new Sequence("", sequenceToRecord));
		
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
			boolean counted	= false; /** 'other' counted separately */
			char toTest		= sequenceToRecord.charAt(i);
			for (int j = 0 ; (j < basesCounts.length - 1) 
					&& (!counted) ; j++) 
				{ if (toTest == basesNames[j].charAt(0)) 
					{ basesCounts[j]++;counted = true; } }
			if (!counted) { basesCounts[basesCounts.length-1]++; }
		}
		this.bioFormat.setBasesCountsAndNames(basesCounts, basesNames);
	}

}
