#!/usr/bin/python3
# -*- coding: utf-8 -*-

import sys
import struct

import GeneSwitcherCreatures
from GeneSwitcherCreatures import GeneSwitcherCreatures1
from GeneSwitcherCreatures import GeneEnumGroups

file2parse = None
if (len(sys.argv) < 2) : 
  print( "Need file to parse in argument !" )
  exit( 1 )
else : 
  file2parse = sys.argv[1]

## use struct package : https://docs.python.org/3/library/struct.html
## see also : https://www.tutorialspoint.com/struct-module-in-python

## NOTEs of DEV of this script : 
## ## ## '.gen' files appears somehow corrupted and script here (with module) take account of these ; 
## ## ## 1/ gene longer than expected : just cut off !
## ## ## 2/ gene shorter than expected : completion ?!

def readBinaryGenes(stream) : 
  data = b''
  while True:
    bytes = stream.read(1)
    if bytes == "":
      break;
    data += bytes
    ## NOTE : optimize this below !!
    if (data.endswith( b'gene' )) or (data.endswith( b'gend' )):
      break;
  return data
    
switcher = GeneSwitcherCreatures1()
listOfGenes = []
with open(file2parse, 'rb') as bfile :
  while True : 
    data = readBinaryGenes( bfile )
    ## print( data )
    nextgene = switcher.define4creatures1( data[:-4] )
    ## print( nextgene )
    ## print( type( nextgene ) )
    if ( type(nextgene) is GeneSwitcherCreatures.GeneCreatures1) : 
      listOfGenes.append( nextgene )
    if (data.endswith( b'gend') ) : 
      print( "END OF GENOME" )
      break

for gene in listOfGenes : 
  gene.printInLine()

for tsg in GeneEnumGroups.getEnumsTSG() : 
  print( tsg )
