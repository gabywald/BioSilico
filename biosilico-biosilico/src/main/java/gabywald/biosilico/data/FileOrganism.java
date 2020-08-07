package gabywald.biosilico.data;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.genetics.GeneGattaca;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.BrainBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.Chemicals;
import gabywald.biosilico.structures.ExtendedLineageItem;
import gabywald.biosilico.utils.Sequence;
import gabywald.biosilico.view.GeneJPanel;
import gabywald.global.data.StringUtils;
import gabywald.global.exceptions.DataException;
import gabywald.global.structures.StringCouple;

/**
 * 
 *  @author Gabriel Chandesris (2010, 2020)
 */
@SuppressWarnings("serial")
public class FileOrganism extends FileBiological {
	/** Date Formatter (ddmmyyyy hh:mm:ss). */
	private static final SimpleDateFormat GENERIC_FORMATER = new SimpleDateFormat("dd-MM-yyyy    HH:mm:ss");
	/** Current instance of Organism */
	private Organism orga;
	/** Container of genome. */
	private List<Sequence> geneListe;
	/** CREATED DATE ; UPDATED DATE ; PUBLICATION DATE. */
	private List<StringCouple> dates;
	//	/** Chemical variables as pair (index, values). */
	//	private StringCoupleListe chemicalVariables;
	private int brainHeight, brainWidth, brainDepth;

	/**
	 * Constructor with complete path to File. 
	 * @param completePath (String)
	 */
	public FileOrganism(String completePath) {
		super(completePath);
		this.init();
		this.setType("Organism");
		this.orga	= new Organism();
		List<Chromosome> currentGenome	= new ArrayList<Chromosome>();
		Chromosome currentChromosome	= new Chromosome();

		try { this.load(); } 
		catch (IOException e) { e.printStackTrace(); }

		for (int i = 0 ; i < this.lengthFile() ; i++) {
			String currentLine = this.getChamp(i);
			if (currentLine.matches("^SCIENTIFIC NAME\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
				{ this.orga.setNameScientific(currentLine.substring(pos+1)); }
				// System.out.println(currentLine.substring(pos+1));
			} else if (currentLine.matches("^\tBIOSILICO COMMON NAME\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
				{ this.orga.setNameBiosilico(currentLine.substring(pos+1)); }
			} else if (currentLine.matches("^\tCOMMON NAME\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
				{ this.setNameCommon(currentLine.substring(pos+1)); }
			} else if (currentLine.matches("^\tINCLUDES\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
				{ this.setNameIncluded(currentLine.substring(pos+1)); }
			} else if (currentLine.matches("^\tNAMES$")) {
				currentLine = this.getChamp(++i);
				while ( (i < this.lengthFile()) 
						&& (currentLine.matches("^\t\t(.*)$")) ) {
					this.orga.addOtherName(currentLine.substring(2));
					currentLine = this.getChamp(++i);
				}
				i--; /** rollback out of while **/
			} else if (currentLine.matches("^RANK\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
				{ this.orga.setRank(currentLine.substring(pos+1)); }
			} else if (currentLine.matches("^DIVISION\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
				{ this.orga.setDivision(currentLine.substring(pos+1)); }
			} else if (currentLine.matches("^(SUB )?GENETIC CODE$")) {
				boolean isSub = (currentLine.matches("^SUB GENETIC CODE$"));
				StringCouple geneticCode = new StringCouple(
						this.getChamp(++i).substring(4),
						this.getChamp(++i).substring(6));
				if (isSub)	{ this.orga.getGeneticCodes().add(geneticCode); }
				else		{ this.orga.getGeneticCodes().set(0, geneticCode); }
			} 
			//			else if (currentLine.matches("^LINEAGE$")) {
			//				currentLine = this.getChamp(++i);
			//				while ( (i < this.lengthFile()) 
			//						&& (currentLine.matches("^\t[a-zA-Z0-9 ]*$")) ) {
			//					this.lineage.addString(currentLine.substring(1));
			//					currentLine = this.getChamp(++i);
			//				}
			//				i--; /** rollback out of while **/
			//			} 
			else if (currentLine.matches("^EXTENDED LINEAGE$")) {
				currentLine = this.getChamp(++i);
				while ( (i < this.lengthFile()) 
						&& (currentLine.matches("^\tTAXON$")) ) {
					String iden = this.getChamp(++i).substring(5);
					String name = this.getChamp(++i).substring(18);
					String rank = this.getChamp(++i).substring(7);
					this.orga.addExtendedLineageItem(iden,name,rank);
					currentLine = this.getChamp(++i);
				}
				i--; /** rollback out of while **/
			} else if (currentLine.matches("^CREATED DATE\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
				{ this.dates.add(new StringCouple(
						"CREATED DATE",
						currentLine.substring(pos+1))); }
			} else if (currentLine.matches("^UPDATED DATE\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
				{ this.dates.add(new StringCouple(
						"UPDATED DATE",
						currentLine.substring(pos+1))); }
			} else if (currentLine.matches("^PUBLICATION DATE\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
				{ this.dates.add(new StringCouple(
						"PUBLICATION DATE",
						currentLine.substring(pos+1))); }
			} else if (currentLine.matches("^GENOME$")) {
				currentLine = this.getChamp(++i);
				while ( (i < this.lengthFile()) 
						&& (currentLine.matches("^\t(.*)\t[ACGT-]*$")) ) {
					String tab[] = currentLine.substring(1).split("\t");
					this.geneListe.add(new Sequence(tab[0],tab[1]));

					if (tab[1].matches("^-*$")) {
						currentGenome.add(currentChromosome);
						currentChromosome = new Chromosome();
					} else 
					{ currentChromosome.addGene(GeneGattaca.getInstance(tab[1])); }

					currentLine = this.getChamp(++i);
					// System.out.println(this.getChamp(i));
				}
				i--; /** rollback out of while **/
			} else if (currentLine.matches("^CHEMICAL VARIABLES$")) {
				currentLine = this.getChamp(++i);
				while ( (i < this.lengthFile()) 
						&& (currentLine.matches("^\t[0-9]{3}\t[0-9]{3}$")) ) {
					String chemIndex = currentLine.substring(1, 4);
					String chemValue = currentLine.substring(5, 8);
					// System.out.println("\t"+chemIndex+"\t"+chemValue);
					this.orga.getChemicals().setVariable(	Integer.parseInt( chemIndex ), 
															Integer.parseInt( chemValue ));
					currentLine = this.getChamp(++i);
				}
				i--; /** rollback out of while **/
			} else if (currentLine.matches("^BRAIN HEIGHT\t(.*)$")) 
				{ this.brainHeight = Integer.parseInt(currentLine.substring(13)); } 
			else if (currentLine.matches("^BRAIN WIDTH\t(.*)$")) 
				{ this.brainWidth = Integer.parseInt(currentLine.substring(12)); } 
			else if (currentLine.matches("^BRAIN DEPTH\t(.*)$")) 
				{ this.brainDepth = Integer.parseInt(currentLine.substring(12)); }
		}
		// System.out.println("######");
		if ( ( (this.brainHeight != 0) && (this.brainWidth != 0) ) 
				&& (this.brainDepth != 0) ) {
			// ***** TODO lecture réseau neuronal 
			Brain cc = null;
			try { cc = BrainBuilder.brainBuilder(this.brainWidth, this.brainHeight, this.brainDepth); } 
			catch (BrainLengthException e) { cc = null; }
			this.orga.setBrain(cc);
		}
		if (currentChromosome.length() > 0) 
		{ currentGenome.add(currentChromosome); }
		this.orga.setGenome(currentGenome);
	}

	/**
	 * Constructor with a given Name and Organism. 
	 * <br>Aim here is to save organism to file. 
	 * @param fileName (String)
	 * @param orga (Organism)
	 */
	public FileOrganism(String fileName,Organism orga) { 
		super("Organism "+fileName,fileName+".gatorg");
		this.init();this.loadFrom(orga);
	}

	public List<Sequence> getListe()	{ return this.geneListe; }
	public int lengthListe()			{ return this.geneListe.size(); }

	public static String getCurrentDate() 
	{ return FileOrganism.GENERIC_FORMATER.format(new Date()); }

	/** Initialization helper for constructors. */
	private void init() {
		this.geneListe			= new ArrayList<Sequence>();
		this.dates				= new ArrayList<StringCouple>();
		// this.orga				= new Organism();
		// this.chemicalVariables	= new StringCoupleListe();
		this.brainHeight		= 0;
		this.brainWidth			= 0;
		this.brainDepth			= 0;
	}

	/**
	 * Loading from a given Organism and generate file content. 
	 * @param orgaExt (Organism)
	 */
	private void loadFrom(Organism orgaExt) {
		this.dates.add(	new StringCouple("CREATED DATE",
				FileOrganism.getCurrentDate()));
		Brain cc = orgaExt.getBrain();
		if (cc != null) {
			this.brainHeight	= cc.getHeight();
			this.brainWidth		= cc.getWidth();
			this.brainDepth		= cc.getDepth();
		}

		List<Chromosome> genome = orgaExt.getGenome();
		for (int i = 0 ; i < genome.size() ; i++) {
			Chromosome chromosome = genome.get(i);
			for (int j = 0 ; j < chromosome.length() ; j++) {
				this.geneListe.add(	new Sequence(chromosome.getGene(j).getName(),
									chromosome.getGene(j).reverseTranslation(true)));
			}
			this.geneListe.add(	new Sequence("chromosome "+i+"separator",
								StringUtils.repeat("-", 50)));
		}

		// ***** Instanciate local organism. 
		this.orga = new Organism(genome);

		Chemicals chemicals = orgaExt.getChemicals();
		for (int i = 0 ; i < chemicals.length() ; i++) {
			if (chemicals.getVariable(i) > 0) {
				String chemIndex = GeneJPanel.convertThreeChars(i);
				String chemValue = GeneJPanel.convertThreeChars(chemicals.getVariable(i));
				this.orga.getChemicals().setVariable(	Integer.parseInt( chemIndex ), 
						Integer.parseInt( chemValue ));
			}
		}

		this.orga.setBrain(cc);
	}

	public Organism getOrganism()		{ return this.orga; }

	public int lengthLineage()			
		{ return this.orga.lengthLineage(); }
	public String getSimpleLinage(int i) 
		{ return this.orga.getSimpleLineage(i); }

	public void setGenome(List<Chromosome> genome) 
	{ this.orga.setGenome(genome); }

	public List<String> getAllNames()	{ return this.orga.getAllNames(); }
	public void setNameScientific(String scientificName) 
		{ this.orga.setNameScientific(scientificName); }
	public void setNameBiosilico(String biosilicoName) 
		{ this.orga.setNameBiosilico(biosilicoName); }
	public void setNameCommon(String commonName) 
		{ this.orga.setNameCommon(commonName); }
	public void setNameIncluded(String includedName) 
		{ this.orga.setNameIncluded(includedName); }
	public void addOtherName(String otherName)
		{ this.orga.addOtherName(otherName); }

	public String getBioSilicoName() 
		{ return this.orga.getBioSilicoName(); }
	public String getScientificName()	
		{ return this.orga.getScientificName(); }

	public String getRank()				
		{ return this.orga.getRank(); }
	public String getUniqueID()			
		{ return this.orga.getUniqueID(); }

	public void setExtendedLineage(List<ExtendedLineageItem> lineage)
		{ this.orga.setExtendedLineage(lineage); }

	public String printFile() throws DataException {
		this.setChamps(this.toString().split("\n"));
		return super.printFile();
	}

	/**
	TAXON ID	[0-9]{10}
	SCIENTIFIC NAME	Silico[Daemon|Bacta|Viridita|Anima|Viria] [A-Z][a-z]*
	OTHER NAMES
		BIOSILICO COMMON NAME	[A-Z][a-z]*
		COMMON NAME	[A-Z][a-z]*
		INCLUDES	[A-Z][a-z]*
		NAMES
			[CLASS+\t+DISPLAY]
			## CLASS	[A-Z][a-z]*
			## DISPLAY	[A-Z][a-z]*
	## [...]* (for other names)
	RANK	[A-Z][a-z]*
	DIVISION	[A-Z][a-z]*
	GENETIC CODE
		ID	0000000010
		NAME	Gattaca01
	SUB GENETIC CODE
		ID	[0-9]{10}
		NAME	[A-Z][a-z]*
	## [...]* (for sub genetic code)
	LINEAGE
		[A-Z][a-z]*
		[A-Z][a-z]*
		## [...]+ (for taxon in lineage)
	EXTENDED LINEAGE
		TAXON
			ID [0-9]{10}
			SCIENTIFIC NAME [A-Z][a-z]*
			RANK	[norank|.*]
		## [...]+ (for taxon in extended lineage)
	CREATED DATE	YYYY-MM-DD HH:mm:SS
		## with Y/M/D/H/m/S [0-9]
	UPDATED DATE	YYYY-MM-DD HH:mm:SS
		## with Y/M/D/H/m/S [0-9]
	PUBLICATION DATE	YYYY-MM-DD HH:mm:SS
		## with Y/M/D/H/m/S [0-9]
	GENOME -- LIST of GENES
		## list of encoded genes (Gattaca encoded)
	CHEMICAL VARIABLES
		## [0-9]{3}::[0-9]{3} ## values > 0
	BRAIN HEIGHT	[0-9]{2}
	BRAIN WIDTH	[0-9]{2}
	BRAIN DEPTH	[0-9]{2}
	NEURONS LIST DESCRIPTION
		[TODO]
	 */

	public String toString() {
		String result = new String();

		result += this.orga.toString();

		for (int i = 0 ; i < this.dates.size() ; i++) {
			result += this.dates.get(i).getValueA()+
					"\t"+this.dates.get(i).getValueB()+"\n";
		}
		return result;
	}
}
