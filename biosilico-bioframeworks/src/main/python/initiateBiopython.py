#!/usr/bin/python3
# -*- coding: utf-8 -*-

## 20210830+ GCH to test some features in Biopython

from Bio import SeqIO
for seq_record in SeqIO.parse("ls_orchid.fasta", "fasta"):
  print(seq_record.id)
  print(repr(seq_record.seq))
  print(len(seq_record))

print( "*****END*****" )