#!/bin/bash

for file in ../../genetics/*.gen
do
	echo "file = $file TO nextfile = $nextfile"
	## name = $(sed 's/[(\.\.\/)|genetics/]//g' <<< "../../genetics/7RMN.gen")
	## see also : https://tldp.org/LDP/abs/html/parameter-substitution.html
	nextfile=${file%.gen}.export
	nextfile=exported/${nextfile#../../genetics/}
	./creatures1GenomeParsing.py $file > $nextfile
done

echo "*****"
grep "Number of genes (" exported/*.export
echo "*****"
grep "Number of genes (320)" exported/*.export
echo "*****"
grep "Number of genes (259)" exported/*.export
echo "*****"
grep "Number of genes (321)" exported/*.export
echo "*****"
grep "Number of genes (319)" exported/*.export