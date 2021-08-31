#!/usr/bin/python3
# -*- coding: utf-8 -*-

## 20210830+ GCH to test some features in Biopython

from Bio import SeqIO

## from Bio.Alphabet import generic_dna

## read and print FASTA data
fasta_records = []
for seq_record in SeqIO.parse("ls_orchid.fasta", "fasta"):
  print(seq_record.id)
  print(repr(seq_record.name))
  print(repr(seq_record.description))
  print(repr(seq_record.seq))
  print(len(seq_record))
  fasta_records.append( seq_record )


## read and print GENBANK data
genbank_records = []
for seq_record in SeqIO.parse("ls_orchid.gbk", "genbank"):
  print(seq_record.id)
  print(repr(seq_record.name))
  print(repr(seq_record.description))
  print(repr(seq_record.seq))
  print(len(seq_record))
  genbank_records.append( seq_record )

print( "*****END*****" )
print( "" )

## some treatments
fasta_seq_toTest = fasta_records[1]
print( type(fasta_seq_toTest) )
print( "" )
print( fasta_seq_toTest )
print( "" )
print( len(fasta_seq_toTest) )
print( fasta_seq_toTest[2:5] )
print( fasta_seq_toTest[0::3] )

