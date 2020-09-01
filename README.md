# BioSilico

Some ideas about Artificial Life, Creatures (game series) and Neural Networks. Rare review to make this working with Maven / Java 8 / in a GitHub Repository ! (i.e. some file reading in relative path). 

Probably out-of-date for some reasons (but many ideas inside), codes from 2008-2012 (and before then) ; ideas I didn't continue for times spent reasons and I wish to continue (year 2020). 

Tests and learning on coding / programming (Java, Perl, I did some HyperTalk / HyperCard before that long ago) ; some studies on biochemistry and video games (Myst, Marathon, and the first Creature game) then bioinformatics. Find some old code here, and reviewed so far. 

Some parts where done in Java 1.4 (before the Generics of Java 1.5) ! Some reviews for generics and some other for stream (Java 8 !). Adding some unit tests and more review ! (these were done in June and July 2020). 

More review were also done to get an "AntHill Example" (Ants and Plant in a small environment) in July to September 2020. [note at September 1st, 2020]

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
* ... 
* ... 
* ... 

## Maven modules

### 'biosilico-anthill' : Ant Hill Example

First Aim was to do / retake an example with Ants, and some plants and fruits. Not totally from scratch / from nothing : it gives some ideas about how the engine could works. 

Based on 'biosilico-biosilico' module !

### 'biosilico-biojava' : 

Aim of these Part is to put some example of BioJava FrameWork use !

### 'biosilico-biosilico' : 

A complete (?) dev framework to build a computer modelization / simulation for 'biological' agents. 

Mostly inspired from game Creatures and publications around it. 

Implemented as a discrete "World" composed of WorldCase, which can contains Agents. Some Agents are Organisms, which genomes are haploïd (reflexion about diploïd / polyploïd ... ?!). 

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
