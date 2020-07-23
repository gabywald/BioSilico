package gabywald.biosilico.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabywald.biosilico.genetics.GeneAnnotation;
import gabywald.biosilico.utils.Sequence;

/**
 * Specific file format for annotated Gene's (GeneAnnotation).
 * <br>File Format similar to GenBank (and similar) format, 
 * see documentation and <a href="http://www.ebi.ac.uk/2can/tutorials/formats.html">http://www.ebi.ac.uk/2can/tutorials/formats.html</a>. 
 * @author Gabriel Chandesris (2010)
 */
@SuppressWarnings("serial")
public class FileGene extends FileBiological {
	private static final Pattern LOCUS = // Pattern.compile("^LOCUS\t(.*)$");
			Pattern.compile("^LOCUS\t([A-Z]{5})\t([0-9]*) bp\t(.*)\t(.*)\t([0-9]{2}.[a-zA-Z]{3}.[0-9]{4})$");
	private static final Pattern DEFINITION	= Pattern.compile("^DEFINITION\t(.*)$");
	private static final Pattern ACCESSION	= Pattern.compile("^ACCESSION\t([A-Z][0-9]{5})$");
	private static final Pattern VERSION	= Pattern.compile("^VERSION\t([A-Z][0-9]{5}.[0-9])\t");
	private static final Pattern KEYWORDS	= Pattern.compile("^KEYWORDS\t(([a-z]+; )*[a-z]+.)$");
	private static final Pattern SOURCE		= Pattern.compile("^SOURCE\t(.*)$");
	private static final Pattern ORGANISM	= Pattern.compile("^ORGANISM\t(.*)$");
	private static final Pattern REFERENCE	= Pattern.compile("^REFERENCE\t(.*?)\tbase ([0-9]*) to ([0-9]*)$");
	private static final Pattern COMMENT	= Pattern.compile("^COMMENT\t(.*)$");
	private static final Pattern FEATURES	= Pattern.compile("^FEATURES\t(.*)$");
	private static final Pattern ORIGIN		= Pattern.compile("^ORIGIN\t(.*)$");
	private static final Pattern BASECOUNT	= Pattern.compile("^BASE COUNT\t([0-9]+) a\t([0-9]+) c\t([0-9]+) g\t([0-9]+) t\t(([0-9]+) others)?$");
	private static final Pattern ENDGENE	= Pattern.compile("^//$");

	private static final Pattern featureDescriptor	= Pattern.compile("^\t\t\t(.*?)=\"?(.*?)\"? ?");
	private static final Pattern origineSequence	= Pattern.compile("^\t *[0-9]* (.*)$");

	/** Container of annotated Gene's. */
	private List<GeneAnnotation> genesAnnotated;

	/**
	 * Constructor with complete path to File. 
	 * @param completePath (String)
	 */
	public FileGene(String completePath) {
		super(completePath);
		this.setType("Gene");

		try { this.load(); } 
		catch (IOException e) { e.printStackTrace(); }

		this.genesAnnotated	= new ArrayList<GeneAnnotation>();
		/** Current and first instance to be "generated". */
		GeneAnnotation lastGA = new GeneAnnotation();
		this.genesAnnotated.add( lastGA );
		
		for (int i = 0 ; i < this.lengthFile() ; i++) {
			String currentLine = this.getChamp(i);
			// TODO see 'GattacaGeneExample.txt'
			Matcher locusMatch = FileGene.LOCUS.matcher(currentLine);
			Matcher definMatch = FileGene.DEFINITION.matcher(currentLine);
			Matcher accesMatch = FileGene.ACCESSION.matcher(currentLine);
			Matcher versiMatch = FileGene.VERSION.matcher(currentLine);
			Matcher keywoMatch = FileGene.KEYWORDS.matcher(currentLine);
			Matcher sourcMatch = FileGene.SOURCE.matcher(currentLine);
			Matcher organMatch = FileGene.ORGANISM.matcher(currentLine);
			Matcher commeMatch = FileGene.COMMENT.matcher(currentLine);
			Matcher referMatch = FileGene.REFERENCE.matcher(currentLine);
			Matcher basecMatch = FileGene.BASECOUNT.matcher(currentLine);
			/** 
			Matcher authoMatch = FileGene.AUTHORS.matcher(currentLine);
			Matcher titleMatch = FileGene.TITLE.matcher(currentLine);
			Matcher journMatch = FileGene.JOURNAL.matcher(currentLine);
			Matcher medliMatch = FileGene.MEDLINE.matcher(currentLine);
			Matcher pubmeMatch = FileGene.PUBMED.matcher(currentLine);
			 **/ 
			Matcher featuMatch = FileGene.FEATURES.matcher(currentLine);
			Matcher origiMatch = FileGene.ORIGIN.matcher(currentLine);
			Matcher endgeMatch = FileGene.ENDGENE.matcher(currentLine);
			if (locusMatch.matches()) {
				String locus = locusMatch.group(1);
				String numPB = locusMatch.group(2);
				String type	 = locusMatch.group(3);
				String bolo	 = locusMatch.group(4);
				/** group 4 useless for now ?? */
				String date	 = locusMatch.group(5);
				String outp = "\t"+locus+"\t"+numPB+"\t"+type+"\t"+bolo+"\t"+date;
				lastGA.addProperty(0, outp);
			} else if (definMatch.matches()) {
				String defi = definMatch.group(1);
				lastGA.addProperty(1, defi);
			} else if (accesMatch.matches()) {
				String acce = accesMatch.group(1);
				lastGA.addProperty(2, acce);
			} else if (versiMatch.matches()) {
				String vers = versiMatch.group(1);
				lastGA.addProperty(3,vers);
			} else if (keywoMatch.matches()) {
				String keys = keywoMatch.group(1);
				lastGA.addProperty(4, keys);
			} else if (sourcMatch.matches()) {
				String sour = sourcMatch.group(1);
				lastGA.addProperty(5, sour);
			} else if (organMatch.matches()) {
				String orga = organMatch.group(1);
				lastGA.addProperty(6, orga);
				i++;currentLine = this.getChamp(i);
				String lineage = currentLine;
				lastGA.addProperty(7, lineage);
			} else if (commeMatch.matches()) {
				String comm = commeMatch.group(1);
				lastGA.addProperty(8, comm);
			} else if(basecMatch.matches()) {
				String base = basecMatch.group(1)+"\t"
						+basecMatch.group(2)+"\t"
						+basecMatch.group(3)+"\t"
						+basecMatch.group(4)+"\t"
						+((basecMatch.group(6) != null)?basecMatch.group(6)+"\t":"");
				lastGA.addProperty(9, base);
			} else if (referMatch.matches()) {
				String refun = referMatch.group(1);
				String start = referMatch.group(2);
				String stops = referMatch.group(3);
				String reference = refun+"\t"+start+"\t"+stops;
				/** TODO references detection and adding... */
				i++;currentLine = this.getChamp(i);
				String authors = currentLine.split("\t")[1];
				i++;currentLine = this.getChamp(i);
				String title = currentLine.split("\t")[1];
				i++;currentLine = this.getChamp(i);
				String journal = currentLine.split("\t")[1];
				i++;currentLine = this.getChamp(i);
				String medline = currentLine.split("\t")[1];
				i++;currentLine = this.getChamp(i);
				String pubmed = currentLine.split("\t")[1];
				lastGA
				.addReference(reference, authors, title, 
						journal, medline, pubmed);
			} else if (featuMatch.matches()) {
				i++;currentLine = this.getChamp(i);
				String mainFeature = "^\t[a-zA-Z_]*\t[0-9]+..[0-9]+$";
				if (currentLine.matches(mainFeature)) {
					do {
						String[] splot = currentLine.split("\t");
						String name = splot[1];
						String part = splot[2];
						lastGA
						.addFeature(name, part);
						/** Items in feature. */
						i++;currentLine = this.getChamp(i);
						String subbFeature = "^\t\t\t(.*)";
						if (currentLine.matches(subbFeature)) {
							do {
								Matcher matcher = FileGene.featureDescriptor.matcher(currentLine);
								if (matcher.matches()) {
									String desc = matcher.group(1);
									String cont = matcher.group(2);
									lastGA
									.addFeatureData(desc, cont);
								}
								/** Next item in current feature. */
								i++;currentLine = this.getChamp(i);
							} while (currentLine.matches(subbFeature));
							i--;
						} else { i--; }
						/** Next Feature ?! */
						i++;currentLine = this.getChamp(i);
					} while (currentLine.matches(mainFeature));
					i--; /** Exiting loop of features' treatment. */
				} else { i--; } 
			} else if (origiMatch.matches()) {
				i++;currentLine = this.getChamp(i);
				String mainSequence = "^\t *[0-9]+ (.*)";
				if (currentLine.matches(mainSequence)) {
					String sequence = "";
					do {
						Matcher matcher = FileGene.origineSequence.matcher(currentLine);
						if (matcher.matches()) 
						{ sequence = sequence.concat(matcher.group(1)); }
						i++;currentLine = this.getChamp(i);
					} while (currentLine.matches(mainSequence));
					i--;
					String name = lastGA
							.getVersion();
					String[] tabSequence = sequence.split(" ");
					sequence = "";
					for (int j = 0 ; j < tabSequence.length ; j++) 
					{ sequence += tabSequence[j]; }
					lastGA
					.setSequence(name, sequence);
				} else { i--; }
			} else if (endgeMatch.matches()) {
				lastGA = new GeneAnnotation();
				this.genesAnnotated.add( new GeneAnnotation() ); 
			}
			else { System.out.println(currentLine); }
		}
		/** Removing Last GeneAnnotation instance (not valid / empty). */
		this.genesAnnotated.remove(this.genesAnnotated.size()-1);
	}

	public List<String> getContentSemiColon(String content) {
		String tmp = content;
		List<String> result = new ArrayList<String>();
		Pattern semiColon = Pattern.compile("^((.*?); )(.*).$");
		Matcher splitter = semiColon.matcher(tmp);
		while(splitter.matches()) {
			result.add(splitter.group(2));
			tmp = splitter.group(3);
		};
		result.add(tmp);
		return result;
	}

	public List<GeneAnnotation> getGenesAnnotated()	{ return this.genesAnnotated; }

	@Override
	public List<Sequence> getListe()	{
		final List<Sequence> geneList = new ArrayList<Sequence>();
		this.genesAnnotated.stream().forEach( ga -> geneList.add( ga.getSequence() ));
		return geneList; 
	}

	@Override
	public int lengthListe() {
		return this.genesAnnotated.size();
	}

}
