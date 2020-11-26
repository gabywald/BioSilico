#!/bin/bash

exportdirectory="exportcomparison/";

if [ -d $exportdirectory ]; then
	rm -rfv $exportdirectory
fi

mkdir -v $exportdirectory

for perlfile in perl/exported/*.export
do
	## name = $(sed 's/[(\.\.\/)|genetics/]//g' <<< "../../genetics/7RMN.gen")
	## see also : https://tldp.org/LDP/abs/html/parameter-substitution.html
	pythonfile=python/${perlfile#perl/}
	ecfile=exportcomparison/${perlfile#perl/exported/}.diff
	echo "diff $perlfile $pythonfile TO $ecfile"
	diff $perlfile $pythonfile > $ecfile
done