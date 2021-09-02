#!/usr/bin/python3
# -*- coding: utf-8 -*-

## 20210830+ GCH to test some features in Biopython

from Bio import SeqIO

## from Bio.Alphabet import generic_dna

## read and print FASTA data
fasta_records = []
for seq_record in SeqIO.parse("../resources/ls_orchid.fasta", "fasta"):
  print(seq_record.id)
  print(repr(seq_record.name))
  print(repr(seq_record.description))
  print(repr(seq_record.seq))
  print(len(seq_record))
  print(len(seq_record.features))
  fasta_records.append( seq_record )


## read and print GENBANK data
genbank_records = []
for seq_record in SeqIO.parse("../resources/ls_orchid.gbk", "genbank"):
  print(seq_record.id)
  print(repr(seq_record.name))
  print(repr(seq_record.description))
  print(repr(seq_record.seq))
  print(len(seq_record))
  print(len(seq_record.features))
  for feat in seq_record.features:
    print( "\t feature: %s" % ( feat ) )
  genbank_records.append( seq_record )
  print( "--- " )

print( "*****END*****" )

## some treatments
fasta_seq_toTest = fasta_records[1]
genba_seq_toTest = genbank_records[1]
print( "" )
print( type(fasta_seq_toTest) )
print( "" )
print( fasta_seq_toTest )
print( "" )
print( "Some basic manipulations" )
print( len(fasta_seq_toTest) )
print( fasta_seq_toTest[2:5] )
print( fasta_seq_toTest[:5] )
print( fasta_seq_toTest[2:] )

## ORF / Cadres de lecture
print( "" )
print( "Other manipulations" )
print( fasta_seq_toTest[0::3] )
print( fasta_seq_toTest[1::3] )
print( fasta_seq_toTest[2::3] )
print( fasta_seq_toTest[::-1] )

## Autres manipulations
from Bio.Seq import Seq
brin1 = Seq('CATGTCATAA')
print( "" )
print( "Other (2)" )
print( brin1 )
print( brin1.complement() )
print( brin1.reverse_complement() )

gene = Seq('ATGTCATAA')
print( gene.translate() )

# print( brin1[1:] )
# print( brin1[1:].translate() )
# print( brin1[2:] )
# print( brin1[2:].translate() )

## known bug for six_frame_translations !!
from Bio.SeqUtils import six_frame_translations
print( "" )
# print(six_frame_translations( fasta_seq_toTest ) )
# print(six_frame_translations( genba_seq_toTest ) )

## does not work above for GC()
from Bio.SeqUtils import GC, GC123
print( "" )
# print(GC( fasta_seq_toTest ) )
print(GC123( fasta_seq_toTest ) )
# print(GC( genba_seq_toTest ) )
print(GC123( genba_seq_toTest ) )


## NCBI connection
def FetchSeq(mydb, myrettype, myretmode, myid):
  from Bio import Entrez
  from Bio import SeqIO
  Entrez.email = "gabywald@laposte.net"
  with Entrez.efetch(db=mydb, rettype=myrettype, retmode=myretmode, id=myid) as handle:
    seq_record = SeqIO.read(handle, "fasta")
  print("%s with %i features" % (seq_record.id, len(seq_record.features)))

print( "***** FetchSeq nucleotide fasta text 6273291" )
FetchSeq("nucleotide", "fasta", "text", "6273291")
print( "" )

def FetchSeqMulti(mydb, myrettype, myretmode, myid):
  from Bio import Entrez
  from Bio import SeqIO
  Entrez.email = "gabywald@laposte.net"
  with Entrez.efetch(db=mydb, rettype=myrettype,\
                       retmode=myretmode, id=myid) as handle:
    for seq_record in SeqIO.parse(handle, "gb"):
      print("%s %s..." % (seq_record.id, seq_record.description[:50]))
      print("Sequence length %i, %i features, from: %s" 
        % (len(seq_record), len(seq_record.features), seq_record.annotations["source"]))

print( "***** FetchSeqMulti nucleotide fasta text 6273291,6273290,6273289" )
FetchSeqMulti("nucleotide", "gb", "text", "6273291,6273290,6273289")
print( "" )


