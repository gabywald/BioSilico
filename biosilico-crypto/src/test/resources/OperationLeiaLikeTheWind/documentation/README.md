# Operation Like The Wind

## Idée de base

Chiffrement d'un discours de Leia au Sénat Impérial avec paramètres de bases de 'biosilico-crypto' (fichier 0 de chiffrement 'nucléique', et fichier par défaut chiffrement 'protéique'). Quatre (4) sorties (formats possibles) : direct, fasta, embl, genbank. 

Publication sur réseaux sociaux (LinkedIn, Twitter, Mastodon/Framapiaf, BlueSky, Minds) et "laisser-faire". 

Fichiers PDF générés avec ImageMagick depuis fichiers d'origines en TXT. 

### Discours et traitement

- DiscoursAuSenatLeiaOrgana.txt
- DiscoursAuSenatLeiaOrgana-original.txt

Différence entre les deux : traitement des caractères façon "LaTeX" (car chiffrement par défaut ne contient pas accents, diacritiques et autres caractères spéciaux pour le moment). 

### Génération des fichiers chiffrés

```
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -f -s --DATA=biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt > biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.direct
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -f -s --DATA=biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt --fasta > biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.fasta
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -f -s --DATA=biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt --embl > biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.embl
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -e -f -s --DATA=biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt --genbank > biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.genbank
```

### Tests de déchiffrement

```
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -x -f -s --DATA=biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.direct 
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -x -f -s --DATA=biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.fasta --fasta
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -x -f -s --DATA=biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.genbank --genbank
java -jar biosilico-crypto/target/biosilico-crypto-0.4.0-SNAPSHOT-jar-with-dependencies.jar -x -f -s --DATA=biosilico-crypto/src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.embl --embl
```

### Debug et corrections de code

Basé sur code fait vers 2010-2011.Fait première quinzaine avril 2026 (pour partie déchiffrement), et mars 2026 (revue partie chiffrement).  

## Méthode "rétro" en utilisant un chat IA

### premières notes

Sur Mistral : 
	Lit les PDF, réussit (?) lecture COT
	Peut halluciner assez vite 
		- entre reconnaissance séquences nucléiques identiques, séquences protéiques identiques
		- analyse totalement éloignée de la réalité !

YP483494215 (Haliotis kamtschatkana)
Haliotis kamtschatkana (ID TWRXF899556669)


DPCSCTCYCSDHCSDMCTDRDMCNCQCVCPCTCHCYCNDMCHCPCMCYCPCHCNCMCYCTCRC
DPCSCTCYCSDHCSDMCTDRDMCNCQCVCPCTCHCYCNDMCHCPCMCYCPCHCNCMCYCTCRC
	Score : 100% identité

>Sequence_Nucleique_1_Haliotis_kamtschatkana
agggagggcacacggcctatcgatcgttctccctagctatagaacgcacgccagaaccatcgttcgtgagaacaaccgtactcacgccctatctatcgccagaacgtacgacagaaccaactagcggccgtgcgatcgccctatctatcgccagaacatacgcccggccgacagaacattctagcgctcgaccgtgcgacagaacgcaagctcaaccgtacgcacgccctagcgaccgaccgtgagggagggaaggagggcaaccgtacgtacgttcgatctccctcacggccgttcgtgagaacgcacgccctcgcgaccgtgctcaagaacgtacgccagaaccatctgtcctaagctcgcccttccgtgcgacctcaagaacagccgtcctaactgtcctaagctcgcccttcctagcggccgaccgtaagaaagtcagtcagaaatacatgactgtcctacgaacgcccttccgtccgccagaacgaccgtgcgtgctgtcctaagctcgcccttccgccagaacgcactccagaactagctgtcctacgaacgcccttccgctcgtgcgccagaacgcacgccagaacgtaagctcacccgtcctaacgccctagcgccctccctagagaaccaacgaccgtactaacgacctcacggccgtgcgccagggaaggaaggagtcagtcagtcaaggaaggagggagggctgtcctacgaacaaccttcagaacgtaagctcaacctatctatcgcccgtccgagcgtactgtcctaagctcgcccttccgccagaacgcactccagaaccatctgtcctaagctcgcccttccgtgcgacctcaagaacagccgtcctaactgtcctaagctcgcccttcctagcggccgaccgtaagtaagggagggaaggagggagggctgtcctacgaacaaccttcagaacgtccgccctatagaactaacgaccggcctagctatagtaagaaccatctgtcctaagctcgcccttccgtgcgacctcacgccctccctagctatagaacgccctcaagaaccatctgtcctaagctcgcccttccgtgcgacctcactagcggccgatcgccctatagaacgcacgccctatagaacgtccgttcgtgcgcacgccctatagaactcccgtgcggcctatagaactatcgttctccctatagaacgtacgacagaacgagcgaccgtgcgtgcggcctgtcctacgaacgcccttcctagcgccagaacggccgtcctaactgtcctaagctcgcccttcctagcggccgaccgtacgccagtaagggagggaaggagggagggcaccctcaagaactgtcctacgaacgaccttcagaactcacgttctccctatagaacgatcgccctccctgaagaactacctcccggcagtaagaacgtccgaccgtacgctctagctgtcctaagctcgcccttcagaacgtacgccctatagaacgttcgtccgagctagcgccctatagtaagaacgatctagcgttcggccgcccgtgctcaagaacgcccgtgcgatcgttctagcgccagaacgcccgtgagaacgtacgacagaacgtactcccgtccggcctgtcctacgaacgcccttcctagcgccagtaagggagggaaggaaggcaggcgccagaacgtccgccagaactcacggccgcccgtgctatagaacgacctcccgggcgttctccctagcgcaagctcggactcccggcagaacgcacgccctcgcgaccgtgctcaagaactcgcgttctccctatagaacgtgcgttcgtgagaacgcccgtgagaactcacgaccgtgctcaagaactacctcccgccagaactaactagcggccgtgcgatcgccctatctatcgccagaacatacgcccggccgacagaacattctagcgctcgaccgtgcgacagaacgcaagctcaaccgtacgcacgccctagcgaccgaccgtgagggaaggagggccaacgttctccctagagaacgtaagctcggacgttcgtgcgtgcgccctccctagagaacgcactccagaaccatctgtcctaagctcgcccttccgtgcgacctcaagtaagaactaacgttctccctagagaacgtaagctcgacctcgcgcccgtgcggcctagagaacgcacgccagaacgtacgacagaacgctcgaccgtacgacctgacggccgccagtgagggaaggaaggagtcagtcagtcaagg

>Sequence_Nucleique_2_Daubentonia_madagascariensis
agggagggcacacggcctatcgatcgttctccctagctatagaacgcacgccagaaccatcgttcgtgagaacaaccgtactcacgccctatctatcgccagaacgtacgacagaaccaactagcggccgtgcgatcgccctatctatcgccagaacatacgcccggccgacagaacattctagcgctcgaccgtgcgacagaacgcaagctcaaccgtacgcacgccctagcgaccgaccgtgagggagggaaggagggcaaccgtacgtacgttcgatctccctcacggccgttcgtgagaacgcacgccctcgcgaccgtgctcaagaacgtacgccagaaccatctgtcctaagctcgcccttccgtgcgacctcaagaacagccgtcctaactgtcctaagctcgcccttcctagcggccgaccgtaagaaagtcagtcagaaatacatgactgtcctacgaacgcccttccgtccgccagaacgaccgtgcgtgctgtcctaagctcgcccttccgccagaacgcactccagaactagctgtcctacgaacgcccttccgctcgtgcgccagaacgcacgccagaacgtaagctcacccgtcctaacgccctagcgccctccctagagaaccaacgaccgtactaacgacctcacggccgtgcgccagggaaggaaggagtcagtcagtcaaggaaggagggagggctgtcctacgaacaaccttcagaacgtaagctcaacctatctatcgcccgtccgagcgtactgtcctaagctcgcccttccgccagaacgcactccagaaccatctgtcctaagctcgcccttccgtgcgacctcaagaacagccgtcctaactgtcctaagctcgcccttcctagcggccgaccgtaagtaagggagggaaggagggagggctgtcctacgaacaaccttcagaacgtccgccctatagaactaacgaccggcctagctatagtaagaaccatctgtcctaagctcgcccttccgtgcgacctcacgccctccctagctatagaacgccctcaagaaccatctgtcctaagctcgcccttccgtgcgacctcactagcggccgatcgccctatagaacgcacgccctatagaacgtccgttcgtgcgcacgccctatagaactcccgtgcggcctatagaactatcgttctccctatagaacgtacgacagaacgagcgaccgtgcgtgcggcctgtcctacgaacgcccttcctagcgccagaacggccgtcctaactgtcctaagctcgcccttcctagcggccgaccgtacgccagtaagggagggaaggagggagggcaccctcaagaactgtcctacgaacgaccttcagaactcacgttctccctatagaacgatcgccctccctgaagaactacctcccggcagtaagaacgtccgaccgtacgctctagctgtcctaagctcgcccttcagaacgtacgccctatagaacgttcgtccgagctagcgccctatagtaagaacgatctagcgttcggccgcccgtgctcaagaacgcccgtgcgatcgttctagcgccagaacgcccgtgagaacgtacgacagaacgtactcccgtccggcctgtcctacgaacgcccttcctagcgccagtaagggagggaaggaaggcaggcgccagaacgtccgccagaactcacggccgcccgtgctatagaacgacctcccgggcgttctccctagcgcaagctcggactcccggcagaacgcacgccctcgcgaccgtgctcaagaactcgcgttctccctatagaacgtgcgttcgtgagaacgcccgtgagaactcacgaccgtgctcaagaactacctcccgccagaactaactagcggccgtgcgatcgccctatctatcgccagaacatacgcccggccgacagaacattctagcgctcgaccgtgcgacagaacgcaagctcaaccgtacgcacgccctagcgaccgaccgtgagggaaggagggccaacgttctccctagagaacgtaagctcggacgttcgtgcgtgcgccctccctagagaacgcactccagaaccatctgtcctaagctcgcccttccgtgcgacctcaagtaagaactaacgttctccctagagaacgtaagctcgacctcgcgcccgtgcggcctagagaacgcacgccagaacgtacgacagaacgctcgaccgtacgacctgacggccgccagtgagggaaggaaggagtcagtcagtcaagg

>Haliotis_kamtschatkana_full
agggagggcacacggcctatcgatcgttctccctagctatagaacgcacgccagaaccatcgttcgtgagaacaaccgtactcacgccctatctatcgccagaacgtacgacagaaccaactagcggccgtgcgatcgccctatctatcgccagaacatacgcccggccgacagaacattctagcgctcgaccgtgcgacagaacgcaagctcaaccgtacgcacgccctagcgaccgaccgtgagggagggaaggagggcaaccgtacgtacgttcgatctccctcacggccgttcgtgagaacgcacgccctcgcgaccgtgctcaagaacgtacgccagaaccatctgtcctaagctcgcccttccgtgcgacctcaagaacagccgtcctaactgtcctaagctcgcccttcctagcggccgaccgtaagaaagtcagtcagaaatacatgactgtcctacgaacgcccttccgtccgccagaacgaccgtgcgtgctgtcctaagctcgcccttccgccagaacgcactccagaactagctgtcctacgaacgcccttccgctcgtgcgccagaacgcacgccagaacgtaagctcacccgtcctaacgcc

Haliotis_kamtschatkana_nucleique.txt
AGGGAGGGCACACGGCCTATCGATCGTTCTCCCTAGCTATAGAACGCACGCCAGAACCATCGTTCGTGAGAACAACCGTACTCACGCCCTATCTATCGCCAGAACGTACGACAGAACCAACTAGCGGCCGTCGATCGCCCTATCTATCGCCAGAACATACGCCCGGCCGACAGAACATTCTAGCGCTCGACCGTGCGACAGAACGCAAGCTCAACCGTAACGCACGCCCTAGCGACCGACGGTGAGGGAGGGAAGGAGGGCAACCGTAAGTACGTTTGATCTCCCTCACGGCCGTTCGTGAGAACGCACGCCCTCGCGACCGTGCTCAAGAACGTACGCCAGAACCATCTGTCTCAAGCTCGCCCTTCCGTCGACCTCAAGAACAGCCGTCCTAACTGTCCTAAGCTCGCCCTTCCTAGCGGCCGACCGTAGGAAAGTCAGTCAGAATACATGACTGTCCTACGAACGCCCTTCCGTCGCCAGAACGACCGTGCGTGCTGTCCTAAGCTCGCCCTTCCGCCAGAACGCACGCCAGAACGTAAGCTCACCCGTCCTAACGCCCTAGCGCCCTCCCTAGAGAACCAACGACCGTACGTAACGACCTCACGGCCGTGCGCCAGGGAAGGAAGGAGTCAGTCAGTCAAGG

Daubentonia_madagascariensis_nucleique.txt
AGGGAGGGCACACGGCCTATCGAACGTTCTCCCTAGCTATAGAACGCACGCCAGAACGCCCTATCTCAAGAACGTCGTTCTCCCTATAGAACGTCCTCCCTCGCCTATAGTAAGAACGCCCTCAAGAACGTCCTCCCTAGCGTCCCTCCCTAGCTATAGTAAGAACGCCCTCAAGAACGTCCTCCCTAGCGTCCCTCCCTAGCTATAGTAAGAACGCCCTCAAGAACGCCCTATCTCAAGAACGTCGTTCTCCCTATAGAACGTCCTCCCTAGCGTCCCTCCCTAGCTATAGTAAGAACGCCCTCAAGAACGCCCTATCTCAAGAACGTCGTTCTCCCTATAGAACGTCCTCCCTAGCGTCCCTCCCTAGCTATAGTAAGAACGCCCTCAAGAACGCCCTATCTCAAGAACGTCGTTCTCCCTATAGAACGTCCTCCCTAGCGTCCCTCCCTAGCTATAGTAAGAACGCCCTCAAGAACGCCCTATCTCAAGAACGTCGTTCTCCCTATAGAACGTCCTCCCTAGCGTCCCTCCCTAGCTATAGTAAGAACGCCCTCAAGAACGCCCTATCTCAAGAACGTCGTTCTCCCTATAGAAGGAAGGAGTCAGTCAGTCAAGG

Sequences_proteiques.txt
DPCSCTCYCSDHCSDMCTDRDMCNCQCVCPCTCHCYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP

>Sequence_Protéique (traduction ?)
DPCSCTCYCSDHCSDMCTDRDMCNCQCVCPCTCHCYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP

MRRQQRRFYRSSPLLRRHRQNHPFRQQQRTQGRRMILYLPQTYDRMGLRPEEWK

DPCSCTCYCSDHCSDMCTDRDMCNCQCVCPCTCHCYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP

https://www.didac-tic.fr/seq/
https://planet-vie.ens.fr/thematiques/cellules-et-molecules/physiologie-cellulaire/la-traduction-de-l-arn-messager-a-la
https://www.didac-tic.fr/seq/dna2pro.php
Nombre de codons : 761
REGTRPIDRSP.L.NARQNH
RS.EQPYSRPIYRQNVRQNQ
LAAVRSPYLSPEHTPGRQNI
LALDRATERKLNRTHALATD
REGGKEGNRTYVRSPSRPFV
RTHALATVLKNVRQNHLS.A
RPSVRPQEQPS.LS.ARPS.
RPTVRKSVRNT.LSYERPSV
RQNDRACCPKLALPPERTPE
LAVLRTPFRSCARTHART.A
HPS.RPSALPREPTTVLTTS
RPCAREGRSQSVKEGGRAVL
RTTFRT.AQPIYRPSERTVL
SSPFRQNALQNHLS.ARPSV
RPQEQPS.LS.ARPS.RPTV
SKGGKEGGLSYEQPSERPPY
RTNDRPSYSKNHLS.ARPSV
RPHALPSYRTPSRTICPKLA
LPCDLTSGRSPYRTHAL.NV
RSCARPIELPCGL.NYRSPY
RTYDRTSDRACGLSYERPS.
RQNGRPNCPKLALPSGRPYA
SKGGKEGGHPQELSYERPSE
LTFSL.NDRPP.RTTSRQ.E
RPTVRSSCPKLALQNVRPIE
RSSELAPYSKNDLAFGRPCS
RTPVRSF.RQNARENVRQNV
LPSGLSYERPS.RQ.GREGR
QAPERPPELTAARAIERPPG
RSP.RKLGLPAERTPSRPCS
RTRVLPIERAFVRTPVRTHD
RAQELPPARTN.RPCDRPIY
RQNIRPADRTF.RSTVRQNA
SSTVRTP.RPTVREGGPTFS
LENVSSDVRACALPRERTPE
PSVLSSPFRATSSKN.RSP.
RT.ARPRARAA.RTHARTYD
RTLDRTT.RPPVREGRSQSV
K

=> protéine fournie ne correcpond pas à traduction séquence nucléique fournie !

Expasy confirme
	- séquence proteique pas issue séquence nucléique (testé sur 100 premiers bp)
	
https://scientist-publications.webnode.fr/news/convertisseurs-de-formats-de-sequences/

### Autres notes et Rapports générés

Avec Mistral, en soumettant fichiers PDF (analyse reconnaissance de caractères) et TXT, sans lui donner indications autres que fichiers initiaux et en orientant sur ses propres hypothèses jusqu'à hallkucinations patentes en bioinformatique ; indications erreurs et demande autres hypothèses possibles. 

Voir comment Système IA générative prospecte, recherche, propose comme analyses et démarches en partant des fichiers initiaux chiffrés, que ce soient fichiers PDF ou TXT. 
 - démarche bioinformatique pure puis blocage / hallucinations / erreurs d'interprétation ; 
 - propose d'autres choses suite à incohérences (chiffrement, réalisation artistique) ; 
 - sort hyôthèses de cryptographies (DNA-crypt, recherche selon hypothèses de chiffrement, démarches analyses de fréquences de k-mer...) ; 
 
 cf. fichiers "Procotole*.md"

## Extraits "manuels" (rawedData)

### Obtention fichiers "bruts" et comparaisons

```
grep "translation" -A OperationLikeTheWind.embl > rawedDatas/emblProteicSequence.txt
grep "translation" -A 2 OperationLikeTheWind.embl > rawedDatas/emblProteicSequence.txt
## ...
for f in nucleicSequence* ; do echo $f; cat $f | wc ; done;
nucleicSequenceDirect.txt
      1       1   18984
nucleicSequenceEMBL.txt
      1       1   18985
nucleicSequenceFASTA.txt
      1       1   18985
nucleicSequenceGenBank.txt
      1       1   18985
## ...
for f in proteicSequence* ; do echo $f; cat $f | wc ; done;
proteicSequenceDirect.txt
      1       1     147
proteicSequenceEMBL.txt
      1       1     147
proteicSequenceFASTA.txt
      1       1     147
proteicSequenceGenBank.txt
      1       1     147
## ... 
diff proteicSequenceDirect.txt proteicSequenceFASTA.txt 
diff proteicSequenceDirect.txt proteicSequenceEMBL.txt 
diff proteicSequenceDirect.txt proteicSequenceGenBank.txt 
## ... 
diff nucleicSequenceDirect.txt nucleicSequenceFASTA.txt
diff nucleicSequenceDirect.txt nucleicSequenceEMBL.txt
diff nucleicSequenceDirect.txt nucleicSequenceGenBank.txt
```

Comparaisons "rapides" indiquent pas de différences : pas besoin autres comparaisons !
	NOTE : sinon cela aurait été sympathique d'utiliser / faire les scripts de comparaison SmiuthWaterman et NeedlemanWunsch (ou trouver implémzeentations existantes, par exemple en python). 
	

Voici tout de même ce que cela donne avec SW sur séquences protéiques : 

```
python3 ../utilitiesScripts/swAlign.py proteicSequenceDirect.txt proteicSequenceFASTA.txt 
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
Alignment score: 146
python3 ../utilitiesScripts/swAlign.py proteicSequenceDirect.txt proteicSequenceEMBL.txt 
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
Alignment score: 146
python3 ../utilitiesScripts/swAlign.py proteicSequenceDirect.txt proteicSequenceGenBank.txt 
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
Alignment score: 146
```

Voici tout de même ce que cela donne avec NW sur séquences protéiques :

```
python3 ../utilitiesScripts/nwAlign.py proteicSequenceDirect.txt proteicSequenceFASTA.txt 
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
Alignment score: 146
python3 ../utilitiesScripts/nwAlign.py proteicSequenceDirect.txt proteicSequenceEMBL.txt 
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
Alignment score: 146
python3 ../utilitiesScripts/nwAlign.py proteicSequenceDirect.txt proteicSequenceGenBank.txt 
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP
Alignment score: 146
```

En bref : aucun intérêt de comparer plus que de base ! (à part intérêt pour méthodologies / algorithmes) !

### Traduction en proteine, quelque soit frame de lecture (?)

... 

Traduction complète (deux sens, trois cadres de lecture). 

NOTE : on prend code traduction par défaut ! (il y en a	 plusieurs, pour rappel !)
	https://www.ncbi.nlm.nih.gov/Taxonomy/taxonomyhome.html/index.cgi?chapter=tgencodes
	https://www.ncbi.nlm.nih.gov/Taxonomy/taxonomyhome.html/index.cgi?chapter=cgencodes
	
Traduction avec https://www.bioinformatics.org/sms2/translate.html !

Séquence proteique retrouvée ? (fort peu probable !)

```
grep "CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP" frame1translation.txt 
grep "CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP" frame2translation.txt 
grep "CYCNDMCHCPCMCYCPCHCNCMCYCTCRCNDMCMCYCHAKCVCMCNCKCPCSCTDQAWCMCSCKAWCSDGCMAFDECMCACSDQDACHAPCSCYDMCTCRCNCYAACRANCMDQCKCPAWCMCSCKAKCNDDCKDQCKEACPDLCP" frame3translation.txt 
## Comparer avec SW alignment
python3 ../utilitiesScripts/swAlign.py proteicSequenceDirect.txt frame1translation.txt 
HCP
|||
HCP
Alignment score: 3
python3 ../utilitiesScripts/swAlign.py proteicSequenceDirect.txt frame2translation.txt 
PCSCTDQA
|||*| ||
PCSRT-QA
Alignment score: 4
python3 ../utilitiesScripts/swAlign.py proteicSequenceDirect.txt frame3translation.txt 
HCP
|||
HCP
Alignment score: 3
```

### Autre hypothèses : chiffrement (?)

Analyse par k-mers / k-uplets : Analyses de fréquences ? Valeur de k ? Reconstitution d'alphabet ?

Deux séquences à analyser ?

Autres informations cachées ?



