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

## To review for BioInformatics 

### See also 'biosilico-bioframeworks'

Specific maven module for some example and frameworks and tools used in BioInformartics

### FAIR Principles "Findable, Accessible, Interoperable, Reusable"

This part in French, corresponding to [Les principes FAIR : Findable, Accessible, Interoperable, Reusable](https://openscience.pasteur.fr/2020/10/05/les-principes-fair-findable-accessible-interoperable-reusable/), linked to ["The FAIR Guiding Principles for scientific data management and stewardship"](https://www.nature.com/articles/sdata201618). 

On vous explique
Les principes FAIR : Findable, Accessible, Interoperable, Reusable
5 octobre 2020 CeRIS - Institut Pasteur	

Les principes FAIR (Findable, Accessible, Interoperable, Reusable) correspondent à des lignes directrices dont l’objectif premier est d’améliorer la réutilisation des données de la recherche. Ils ont été publiés en 2016 dans l’article The FAIR Guiding Principles for scientific data management and stewardship.

À bien garder en tête : des données peuvent être « FAIR » sans être librement accessibles. Suivre les principes FAIR permet de s’assurer que ses données sont réutilisables, qu’elles soient partagées ou non.

À chaque lettre du mot FAIR sont liées des bonnes pratiques de gestion des données :

Findable / Facile à trouver :
  * Déposer les données dans un entrepôt
  * Attribuer un identifiant unique et pérenne aux données
  * Décrire les données par des métadonnées riches

Accessible :
  * Définir les conditions d’accès aux données
  * Si possible, rendre les données accessibles librement
  * Si les données doivent rester en accès restreint, rendre accessibles les métadonnées pour signaler l’existence des données

Interopérable :
  * Privilégier des formats ouverts ou largement utilisés
  * Mettre à disposition le code source du logiciel nécessaire pour lire, traiter, analyser les données s’il a été développé en interne
  * Privilégier les standards de métadonnées et les vocabulaires standards
  * Si possible, indiquer des liens vers d’autres ressources (autres données, publication…)

Reusable / Réutilisable :
  * Associer une licence de diffusion aux jeux de données
  * Associer de la documentation pour décrire les données de façon détaillée, les contextualiser, les rendre compréhensibles…

Les principes FAIR peuvent également s’appliquer aux logiciels de recherche, en les reformulant et en les adaptant. C’est ce que proposent les auteurs de l’article Towards FAIR principles for research software.

## Adding and notes about evolutions of this project : 

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
    * ...
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

### 'biosilico-bioframeworks' : BioInformatics frameworks and similar tools

Aim of these Part is to put some example of BioFrameWork use and other tools !
  * BioJava / Biopython / BioPerl / BioC++
  * EMBOSS (tool suite)
  * Pipelining / Pipeline tools : SnakeMake, ...
  * FAIR principles in bioinformatics (reproductibility, open data, ...) 
  * ... 

And some other examples and ideas ?!

Some notes and tutorials : 
  * Biopython
    * [Tutorial Biopython in PDF](https://biopython.org/DIST/docs/tutorial/Tutorial.pdf)
    * [Tutorial Biopython in HTML](https://biopython.org/DIST/docs/tutorial/Tutorial.html)
    * [Biopython SeqUtils](https://biopython.org/docs/1.75/api/Bio.SeqUtils.html)
    * [Cours BioInfo Université Paris Descartes](http://helios.mi.parisdescartes.fr/~lomn/Cours/BI/)
    * [Scheme ou Python ? Pour des biologistes...](https://www.epi.asso.fr/revue/articles/a1912b.htm) / [Python Scheme ou Python ? Pour des biologistes...](https://www.laurentbloch.net/MySpip3/Scheme-ou-) (Laurent Bloch)
    * [Python et Biopython](https://www.laurentbloch.net/MySpip3/Python-et-Biopython)
    * ["GNU/Linux Magazine Hors-série N° Numéro 73 Python : niveau avancé à expert " (Juillet / Août 2014)](https://connect.ed-diamond.com/GNU-Linux-Magazine/glmfhs-073/la-bioinformatique-avec-biopython)
    * [Biopython - Sequence Alignments](https://www.tutorialspoint.com/biopython/biopython_sequence_alignments.htm)
    * [Biopython - Alignements de séquence ](https://www.hebergementwebs.com/tutoriel-biopython/biopython-alignements-de-sequences)
    * ... 
  * BioJava
    * [BioJava tutorial](https://github.com/biojava/biojava-tutorial)
    * [BioJava API 5.3.0](https://biojava.org/docs/api5.3.0/index.html)
    * ... 
  * BioPerl
    * [Introduction to BioPerl](https://etutorials.org/Programming/perl+bioinformatics/Part+II+Perl+and+Bioinformatics/Chapter+9.+Introduction+to+Bioperl/)
    * [Tutorial for BioPerl](https://www.math.utah.edu/~palais/pcr/Ian/Perl/BioPerl%20Docs/BioPerlTutorial%20-%20a%20tutorial%20for%20bioperl.htm)
    * ... 
  * BioC++
    * [BioC++ Offical HomePage](http://biocpp.sourceforge.net/)
    * [BioC++ - solving daily bioinformatic tasks with C++ efficiently - BIOSTEC 2020](https://www.denbi.de/training/810-bioc-solving-daily-bioinformatic-tasks-with-c-efficiently)
    * [SeqAn](https://github.com/seqan)
    * ...
    * ...
  * EMBOSS (tools suite). 
    * [Voyage initiatique vers la bio-informatique : les premiers pas" (article payant)](https://connect.ed-diamond.com/gnu-linux-magazine/glmf-251/voyage-initiatique-vers-la-bio-informatique-les-premiers-pas)
    * [Introduction to Sequence Analysis using EMBOSS](http://emboss.sourceforge.net/docs/emboss_tutorial/emboss_tutorial.html)
    * ... 
  * Pipelining / Pipelines : 
    * [Introduction aux pipelines](https://bioinfo-fr.net/introduction-aux-pipelines)
    * [SnakeMake on Bioinfo-fr.net](https://bioinfo-fr.net/mot-clef/snakemake)
    * [GitHub - snakemake / snakemake](https://github.com/snakemake/snakemake)
    * [snakemake : a framework for reproductible data analysis](https://snakemake.github.io/)
    * [snakemake stable documentation](https://snakemake.readthedocs.io/en/stable/)
    * ... 
    * [Nextflow, pour votre prochain pipeline ?](https://bioinfo-fr.net/nextflow-pour-votre-prochain-pipeline)
    * [Galaxy: Bien plus qu'un gestionnaire de workflows](https://bioinfo-fr.net/galaxy-bien-plus-quun-gestionnaire-de-workflows)
    * [Bioinformatics and Next Generation Sequencing Extensions](https://www.knime.com/bioinformatics-and-next-generation-sequencing-extensions)
    * ... 
  * FAIR : 
    * ["FAIR" à l'UFB / Institut Français de BioInformatique](https://www.france-bioinformatique.fr/?s=FAIR)
    * [Présentation "Bonnes pratiques en bioinformatique : (essayer) d’aller vers plus de reproductibilité"](https://du-bii.github.io/module-5-Methodes-Outils/seance3_goodpractices/slides.html#1)
    * [Formation IFB science ouverte & PGD -- Comment gérer des jeux de données haut-débit en sciences de la vie et de la santé](https://ifb-elixirfr.github.io/IFB-FAIR-data-training/)
    * [Les principes FAIR : Findable, Accessible, Interoperable, Reusable](https://openscience.pasteur.fr/2020/10/05/les-principes-fair-findable-accessible-interoperable-reusable/)
    * ...
  * Some More Courses / Training / Tutorials : 
    * ... 
    
TODO SnakeMake example(s)

TODO other pipeline tools example(s)

TODO BioC++ example(s)

### 'biosilico-biosilico' : Core represnetation for "In Silico" modelisation

A complete (?) dev framework to build a computer modelization / simulation for 'biological' agents. 

Mostly inspired from game Creatures and publications around it. 

Implemented as a discrete "World" composed of WorldCase, which can contains Agents. Some Agents are Organisms, which genomes are haploïd (reflexion about diploïd / polyploïd ... ?!). 

Some generalisation about Chemicals (IChemicals) and Environment (IEnvironment, IEnvironmentItem, IPosition...) to permit other implementations and different global uses !

### 'biosilico-cellmodel' : a previous sample Cell Model

A project made in 2009, in the same idea than the rest : modelisation of a cell in Java. Aim is to visualize tranfert of some elements between part of the cell (RNA, Protein...) and could be used as a basic to teach the CDB (Central Dogma of Biology). Transcription, traduction, treatment, transportation excretion of proteins / RNA / DNA...

### 'biosilico-creatures' : Creatures (Game Series) ressources

Some (original) ideas comes from here, some articles, documentations, ressources...

Some exploration of Creatures Game series

* documentations, articles, pictures...
* some reverse engineering to 'decypher' their "digital DNA" and reconstitue some internal concepts
* some notes... 

...

### 'biosilico-commons' : Common resources. 

Some utils. Data (File, Directory, Filter), Structures, View... 

### 'biosilico-crypto' : Ciphering ideas and some ressources. 

Some cryptographic ideas, based on biological translation and use of a 'genetic code' to translate a nucleic sequence (DNA / RNA) to protein sequence. Main idea is to translate / reverse-translate 'computer source code' to 'biological sequences' and provides a basis to compare them with bioinformatic tools (phylogeny for example). 

### 'biosilico-neuralnetworks' : Some tests on neural networks. 

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
