# BioSilico

Some ideas about Artificial Life, Creatures (game series) and Neural Networks. Rare review to make this working with Maven / Java 8 / in a GitHub Repository ! (i.e. some file reading in relative path). 

Probably out-of-date for some reasons (but many ideas inside), codes from 2008-2012 (and before then) ; ideas I didn't continue for times spent reasons and I wish to continue (year 2020). 

Tests and learning on coding / programming (Java, Perl, I did some HyperTalk / HyperCard before that long ago) ; some studies on biochemistry and video games (Myst, Marathon, and the first Creature game) then bioinformatics. Find some old code here, and reviewed so far. 

Some parts where done in Java 1.4 (before the Generics of Java 1.5) ! Some reviews for generics and some other for stream (Java 8 !). Adding some unit tests and more review ! (these were done in June and July 2020). 

More review were also done to get an "AntHill Example" (Ants and Plant in a small environment) in July to September 2020. [note at September 1st, 2020]

## NOTE For Dev Configuration
  - Eclipse 2020
  - Java 8
  - Maven 3
 
OS used : Ubuntu 20.04
For elements in python, version used is 3.8
For elements in Perl, version used is 5.30.0 

## Things to Explore

For this Project or others : 
* Python language and usual librairies for data analysis (Pandas, NumPy, Scikit Learn...)
* BioJava, BioPerl, BioPython... (BioC++ ?) ; EMBOSS ?
* Some Inspirationnal Video Games and associated topics : 
	
	- Creatures Game Serie
	- Memetic AI (use in "NeverWinter" Video Game) : https://sourceforge.net/projects/memeticai/ ; https://github.com/squattingmonk/memeticai ; ... 
	- World Of WarCraft "Corrupted Blood Incident" : https://en.wikipedia.org/wiki/Corrupted_Blood_incident
	- Procedural Generation : https://en.wikipedia.org/wiki/Procedural_generation ; https://linuxfr.org/tags/gamedev/public ; https://linuxfr.org/news/je-cree-mon-jeu-video-e10-generation-procedurale-de-carte-partie-1 ; https://linuxfr.org/news/je-cree-mon-jeu-video-e11-generation-procedurale-de-carte-partie-2 ; ...
	- BioInspiration https://en.wikipedia.org/wiki/Bioinspiration
	- Deliverable D2.2.3 - Overview on Bio-inspired operation strategies : https://irriis.org/index6bf2.html?lang=en&nav=344 ; https://irriis.org/File6836.pdf?lang=2&oiid=9139&pid=572 ; ...
 
## Indication for internal readings 

See document present in "/biosilico-biosilico/src/main/resources/biosilico/docs/redaction/", there are some notes (mostly in french, some documentation about Creatures in english). 

## Licence : Libre

GNU General Public Licence V3 (GPL3)

## Adding and notes about evolutions

Units Tests ; review ; ...

For dev on BioSilico : 

* genetic execution / genes use (ageMin and ageMax) and cycle (to avoid multiple instantiation of Brain / BrainLobe ++ execution of InitialConcentration Gene) : make good usage of ageMin and ageMax (0, 0) for initiation at start / 'birth'. 
* Design Patterns 'Builder' to make better implements ; IChemicals permits different implementations ; Chemicals Half-lives apply separatly (optional) ; ... 
* Some ideas "on the way to be done" (20200828+) : 
    * AntHill / Ant example (some genetics and pathway to be precised)
    * Kind of reproduction according to Agent / Organism Types (DONE, to previse for virions...)
    * Abstraction for Chemicals / IChemicals
    * Decisions and Instincts to be precisely tested (with some examples)
    * Ideas about "Automatic Pathway construction" (GUI / Human Interface, Graphical or not)
* Review for some genetics model / Type of genes : 
    * BiochemicalReaction
    * Instinct
    * EmitterReceptor
    * StimulusDecision
    * ... 
* ... 
* ... 

## Maven modules

### 'biosilico-anthill' : Ant Hill Example

First Aim was to do / retake an example with Ants, and some plants and fruits. Not totally from scratch / from nothing : it gives some ideas about how the engine could works. 

Based on 'biosilico-biosilico' module !

Work on July, August and September 2020 to get an example of Ant working basically (nervous system / Brain / Brain Lobes, Instincts, EmitterReceptor, StimulusDecisions to GET/DROP FOOD, MOVE_AWAY, MOVE... Seems to be a good example to build a genome and tools to build genomes. 

Idea of a Tool to build "meta-genes" / Pathways : 
* StimulusDecision in Input => EmitterReceptor (receptor) => InputNeuron => Instinct's [=> Concept Neuron's => Instincts =>]* => OutPutNeuron => EmitterReceptor ( ! receptor) => StimulusDecision (decision)
* At Least 6 Genes !

Some biochemistry to get deeper on this Ant ! 

=> Exploration with Plant (without any Brain / BrainLobe / Instinct / EmitterReceptor genes) !

And a Bacteria too ?!

TODO better testing "for real"

Notes 20210707
  * Generate full family (bacta, viridita, anima) with builders with genetic algorithm to select correct and valid genomes ; 
  * Make Builder(s) of pathways (as indicated above ! => selection of genes to make a specific behavior, not only metabolic) : to permit to tests them exhaustively ; 
  * Graphical view of evolution of populations, elements and chemicals... With charts and plots ! See JFreeChart  (?!) : 
    * https://zetcode.com/java/jfreechart/
    * https://caveofprogramming.com/java/charts-in-java-swing-with-jfreechart.html 
    * https://stackoverflow.com/questions/29708147/custom-graph-java-swing

Notes 20210903
  * What make an organism 'valid' / alive exactly ? And what change to 'death' (programmatically) ?
    * Some specific  active metabolic pathways ? TODO precise analysis here about dynamic of pathways
    * Death genes
    * ...
  * "Genetic Algorithm" to generate Ant valid examples of genomes (optimisation) ?!
    * test around "optimal genome" from "hand-made genome"
    * automatisation of tests
    * ... 
  * ... 

... 

### 'biosilico-bioframeworks' : 

Aim of these Part is to put some example of BioFrameWork use !
  * BioJava
  * Biopython
  * BioPerl
  * BioC++
  * EMBOSS
  * ... 

And some other examples and ideas ?!

Some notes and tutorials : 
  * Biopython
    * https://biopython.org/DIST/docs/tutorial/Tutorial.pdf
    * https://biopython.org/DIST/docs/tutorial/Tutorial.html
    * https://biopython.org/docs/1.75/api/Bio.SeqUtils.html
    * http://helios.mi.parisdescartes.fr/~lomn/Cours/BI/
    * https://www.epi.asso.fr/revue/articles/a1912b.htm / https://www.laurentbloch.net/MySpip3/Scheme-ou-Python Scheme ou Python ? Pour des biologistes... (Laurent Bloch)
    * https://www.laurentbloch.net/MySpip3/Python-et-Biopython Python et Biopython
    * => (article gratuit) " GNU/Linux Magazine Hors-série N° Numéro 73 Python : niveau avancé à expert " (Juillet / Août 2014) https://connect.ed-diamond.com/GNU-Linux-Magazine/glmfhs-073/la-bioinformatique-avec-biopython
    * https://www.tutorialspoint.com/biopython/biopython_sequence_alignments.htm
    * https://www.hebergementwebs.com/tutoriel-biopython/biopython-alignements-de-sequences
    * http://emboss.sourceforge.net/docs/emboss_tutorial/emboss_tutorial.html
    * 
    * ... 
  * BioJava
    * https://github.com/biojava/biojava-tutorial
    * https://biojava.org/docs/api5.3.0/index.html
    * ... 
  * BioPerl
    * https://etutorials.org/Programming/perl+bioinformatics/Part+II+Perl+and+Bioinformatics/Chapter+9.+Introduction+to+Bioperl/
    * https://www.math.utah.edu/~palais/pcr/Ian/Perl/BioPerl%20Docs/BioPerlTutorial%20-%20a%20tutorial%20for%20bioperl.htm
    * ... 
  * BioC++
    * http://biocpp.sourceforge.net/
    * https://www.denbi.de/training/810-bioc-solving-daily-bioinformatic-tasks-with-c-efficiently
    * https://github.com/seqan
    * ...
    * ...
  * EMBOSS (tools suite). 
    * "Voyage initiatique vers la bio-informatique : les premiers pas" (article payant) https://connect.ed-diamond.com/gnu-linux-magazine/glmf-251/voyage-initiatique-vers-la-bio-informatique-les-premiers-pas
    * ... 
  * Some More Courses / Training / Tutorials : 
    * ... 

### 'biosilico-biosilico' : 

A complete (?) dev framework to build a computer modelization / simulation for 'biological' agents. 

Mostly inspired from game Creatures and publications around it. 

Implemented as a discrete "World" composed of WorldCase, which can contains Agents. Some Agents are Organisms, which genomes are haploïd (reflexion about diploïd / polyploïd ... ?!). 

Some generalisation about Chemicals (IChemicals) and Environment (IEnvironment, IEnvironmentItem, IPosition...) to permit other implementations and different global uses !

### 'biosilico-cellmodel' : 

A project made in 2009, in the same idea than the rest : modelisation of a cell in Java. Aim is to visualize tranfert of some elements between part of the cell (RNA, Protein...) and could be used as a basic to teach the CDB (Central Dogma of Biology). Transcription, traduction, treatment, transportation excretion of proteins / RNA / DNA...

### 'biosilico-creatures' : Creatures (Game Series) ressources

Some (original) ideas comes from here, some articles, documentations, ressources...

Some exploration of Creatures Game series

* documentations, articles, pictures...
* some reverse engineering to 'decypher' their "digital DNA" and reconstitue some internal concepts
* some notes... 

...

### 'biosilico-commons' : Common resources

Some utils. Data (File, Directory, Filter), Structures, View... 

### 'biosilico-crypto' : 

Some cryptographic ideas, based on biological translation and use of a 'genetic code' to translate a nucleic sequence (DNA / RNA) to protein sequence. Main idea is to translate / reverse-translate 'computer source code' to 'biological sequences' and provides a basis to compare them with bioinformatic tools (phylogeny for example). 

### 'biosilico-neuralnetworks' : 

Some tests on neural networks. 

### 'biosilico-tests' : 

... 

## Ideas, notes, evolutions 

### What if if want to report something ?

Bug, error, idea, evolution, ...

See just below and write to the author ! Thanks !

### Where to begin to participate ?

If you want to participate : first step warn the author (search on the InterWeb, "Gabriel Chandesris" or "Gaby Wald"). Then you will probably be added on project. 

Then copy / clone the repository and source code, study some and indicate on what you want to work (if no further indications). Just work with usual habits (i.e. on GitHub, Use Git uses : create a branch from master, then when you finished (units tests included !), make a pull request to merge !

Have Fun !

### Ideas incoming for some devs and / or documentations

* biosilico-biosilico : gabywald.biosilico.model.reproduction <= to be reviewed !
* biosilico-biosilico : gabywald.biosilico.model.decisions <= some review would be great too !
