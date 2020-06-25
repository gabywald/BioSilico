package gabywald.biosilico.data;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.genetics.GeneGattaca;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.Chemicals;
import gabywald.biosilico.structures.ChromosomeListe;
import gabywald.biosilico.structures.ExtendedLineage;
import gabywald.biosilico.structures.SequenceListe;
import gabywald.biosilico.utils.Sequence;
import gabywald.biosilico.view.GeneJPanel;
import gabywald.global.exceptions.DataException;
import gabywald.global.structures.StringCouple;
import gabywald.global.structures.StringCoupleListe;
import gabywald.global.structures.StringListe;

public class FileOrganism extends FileBiological {
	/** To avoid Warning. */
	// private static final long serialVersionUID = 526L;
	/** Date Formatter (ddmmyyyy hh:mm:ss). */
	private static final SimpleDateFormat GENERIC_FORMATER = 
		new SimpleDateFormat("dd-MM-yyyy    HH:mm:ss");
	/** Current instance of Organism */
	private Organism orga;
	/** Container of genome. */
	private SequenceListe geneListe;
	/** CREATED DATE ; UPDATED DATE ; PUBLICATION DATE. */
	private StringCoupleListe dates;
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
		ChromosomeListe currentGenome = new ChromosomeListe();
		Chromosome currentChromosome = new Chromosome();
		
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
				if (isSub)	{ this.orga.getGeneticCodes()
											.addStringCouple(geneticCode); }
				else		{ this.orga.getGeneticCodes()
											.setStringCouple(geneticCode, 0); }
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
					{ this.dates.addStringCouple(new StringCouple(
							"CREATED DATE",
							currentLine.substring(pos+1))); }
			} else if (currentLine.matches("^UPDATED DATE\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
					{ this.dates.addStringCouple(new StringCouple(
							"UPDATED DATE",
							currentLine.substring(pos+1))); }
			} else if (currentLine.matches("^PUBLICATION DATE\t(.*)$")) {
				int pos = currentLine.lastIndexOf("	");
				if ( (pos > 0) && (pos < currentLine.length() - 1) )
					{ this.dates.addStringCouple(new StringCouple(
							"PUBLICATION DATE",
							currentLine.substring(pos+1))); }
			} else if (currentLine.matches("^GENOME$")) {
				currentLine = this.getChamp(++i);
				while ( (i < this.lengthFile()) 
						&& (currentLine.matches("^\t(.*)\t[ACGT-]*$")) ) {
					String tab[] = currentLine.substring(1).split("\t");
					this.geneListe.addSequence(new Sequence(tab[0],tab[1]));
					
					if (tab[1].matches("^-*$")) {
						currentGenome.addChromosome(currentChromosome);
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
					String chemIndex = currentLine.substring(1,4);
					String chemValue = currentLine.substring(5,8);
					// System.out.println("\t"+chemIndex+"\t"+chemValue);
					this.orga.getChemicals().setVariable(chemIndex, chemValue);
//					this.chemicalVariables.addStringCouple(new StringCouple(chemIndex,chemValue));
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
			/** TODO lecture réseau neuronal */
			Brain cc = null;
			try { cc = new Brain(this.brainWidth,
								 this.brainHeight,
								 this.brainDepth); } 
			catch (BrainLengthException e) { ; }
			this.orga.setBrain(cc);
		}
		if (currentChromosome.length() > 0) 
			{ currentGenome.addChromosome(currentChromosome); }
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

	public SequenceListe getListe()	{ return this.geneListe; }
	public int lengthListe()		{ return this.geneListe.length(); }
	
	public static String getCurrentDate() 
		{ return FileOrganism.GENERIC_FORMATER.format(new Date()); }

	/** Initialization helper for constructors. */
	private void init() {
		this.geneListe			= new SequenceListe();
		this.dates				= new StringCoupleListe();
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
		this.dates.addStringCouple(new StringCouple("CREATED DATE",
									FileOrganism.getCurrentDate()));
		Brain cc = orgaExt.getBrain();
		if (cc != null) {
			this.brainHeight	= cc.getHeight();
			this.brainWidth		= cc.getWidth();
			this.brainDepth		= cc.getDepth();
		}

		ChromosomeListe genome = orgaExt.getGenome();
		for (int i = 0 ; i < genome.length() ; i++) {
			Chromosome chromosome = genome.getChromosome(i);
			for (int j = 0 ; j < chromosome.length() ; j++) {
				this.geneListe.addSequence(
									new Sequence(chromosome.getGene(j).getName(),
									chromosome.getGene(j).reverseTranslation(true)));
			}
			this.geneListe.addSequence(
									new Sequence("chromosome "+i+"separator",
									StringListe.repeat("-", 50)));
		}
		
		/** Instanciate local organism. */
		this.orga = new Organism(genome);
		
		Chemicals chemicals = orgaExt.getChemicals();
		for (int i = 0 ; i < chemicals.length() ; i++) {
			if (chemicals.getVariable(i) > 0) {
				String chemIndex = GeneJPanel.convertThreeChars(i);
				String chemValue = 
					GeneJPanel.convertThreeChars(chemicals.getVariable(i));
				this.orga.getChemicals().setVariable(chemIndex, chemValue);
//				this.chemicalVariables.addStringCouple(new StringCouple(chemIndex,chemValue));
			}
		}
		
		this.orga.setBrain(cc);
	}
	
	public Organism getOrganism()		{ return this.orga; }
	
	public int lengthLineage()			{ return this.orga.lengthLineage(); }
	public String getSimpleLinage(int i) 
		{ return this.orga.getSimpleLineage(i); }
	
	public void setGenome(ChromosomeListe genome) 
		{ this.orga.setGenome(genome); }
	
	public StringListe getAllNames()	{ return this.orga.getAllNames(); }
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
	
	public void setExtendedLineage(ExtendedLineage lineage)
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
		
		for (int i = 0 ; i < this.dates.length() ; i++) {
			result += this.dates.getStringCouple(i).getValueA()+
						"\t"+this.dates.getStringCouple(i).getValueB()+"\n";
		}
		return result;
	}
}
