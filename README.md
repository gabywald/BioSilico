# BioSilico

Some ideas about Artificial Life, Creatures (game series) and Neural Networks. Rare review to make this working with Maven / Java 8 / in a GitHub Repository ! (i.e. some file reading in relative path). 

Probably out-of-date for some reasons (but many ideas inside), codes from 2008-2012 (and before then) ; ideas I didn't continue for times spent reasons and I wish to continue (year 2020). 

Tests and learning on coding / programming (Java, Perl, I did some HyperTalk / HyperCard before that long ago) ; some studies on biochemistry and video games (Myst, Marathon, and the first Creature game) then bioinformatics. Find some old code here, and reviewed so far. 

Some parts where done in Java 1.4 (before the Generics of Java 1.5) ! Some reviews for generics and some other for stream (Java 8 !). Adding some unit tests and more review !

## Adding and notes

Units Tests ; review ; ...

For dev on BioSilico : 

* genetic execution / genes use (ageMin and ageMax) and cycle (to avoid multiple instantiation of Brain / BrainLobe ++ execution of InitialConcentration Gene) : make good usage of ageMin and ageMax (0, 0) for initiation at start / 'birth'. 
* Design Patterns 'Builder' to make better implements ; IChemicals permits different implementations ; Chemicals Half-lives apply separatly (optional) ; ... 
* ... 

## Maven modules

### 'biosilico-anthill' : Ant Hill Example

First Aim was to do / retake an example with Ants, and some plants and fruits. Not totally from scratch / from nothing : it gives some ideas about how the engine could works. 

Based on 'biosilico-biosilico' module !

### 'biosilico-biojava' : 

... 

### 'biosilico-biosilico' : 

A complete (?) dev framework to build a computer modelization / simulation for 'biological' agents. 

Mostly inspired from game Creatures and publications around it. 

### 'biosilico-cellmodel' : 

... 

### 'biosilico-creatures' : Creatures (Game Series) ressources

Some (original) ideas comes from here, some articles, documentations, ressources...

Some exploration of Creatures Game series

* documentations, articles...
* 

### 'biosilico-commons' : Common resources

Some utils. Data (File, Directory, Filter), Structures, View... 

### 'biosilico-crypto' : 

Some cryptographic ideas, based on biological translation and use of a 'genetic code' to translate a nucleic sequence (DNA / RNA) to protein sequence. Main idea is to translate / reverse-translate 'computer source code' to 'biological sequences' and provides a basis to compare them with bioinformatic tools (phylogeny for example). 

### 'biosilico-neuralnetworks' : 

Some tests on neural networks. 

### 'biosilico-tests' : 

... 
