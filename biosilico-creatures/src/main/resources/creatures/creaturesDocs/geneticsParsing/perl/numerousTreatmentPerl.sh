#!/bin/bash

exportdirectory="exported/";

if [ -d $exportdirectory ]; then
	rm -rfv $exportdirectory
fi

mkdir -v $exportdirectory

for file in ../../genetics/*.gen
do
	## name = $(sed 's/[(\.\.\/)|genetics/]//g' <<< "../../genetics/7RMN.gen")
	## see also : https://tldp.org/LDP/abs/html/parameter-substitution.html
	nextfile=${file%.gen}.export
	nextfile=exported/${nextfile#../../genetics/}
	echo "file = $file TO nextfile = $nextfile"
	./creatures1GenomeParsing.pl $file > $nextfile
done

echo "*****"
grep "Number of genes (" exported/*.export
echo "*****"
grep "Number of genes (320)" exported/*.export
echo "*****"
grep "Number of genes (258)" exported/*.export
grep "Number of genes (259)" exported/*.export
echo "*****"
grep "Number of genes (321)" exported/*.export
echo "*****"
grep "Number of genes (319)" exported/*.export