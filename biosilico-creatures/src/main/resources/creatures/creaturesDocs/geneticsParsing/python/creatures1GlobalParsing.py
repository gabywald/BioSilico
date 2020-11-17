#!/usr/bin/python3
# -*- coding: utf-8 -*-

import sys
import struct

from GeneSwitcherCreatures import GeneSwitcherCreatures1

file2parse = None
if (len(sys.argv) < 2) : 
  print( "Need file to parse in argument !" )
  exit( 1 )
else : 
  file2parse = sys.argv[1]

## use struct package : https://docs.python.org/3/library/struct.html
## see also : https://www.tutorialspoint.com/struct-module-in-python

def each_chunk(stream, separator, CHUNK_SIZE = 4096):
  """Read in file part by part"""
  buffer = ''
  while True : # until EOF
    chunk = stream.read(CHUNK_SIZE)
    if not chunk : # EOF?
      yield buffer
      break
    buffer += chunk
    while True : # until no separator is found
      try:
        part, buffer = buffer.split(separator, 1)
      except ValueError:
        break
      else:
        yield part

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
with open(file2parse, 'rb') as bfile :
  while True : 
    data = readBinaryGenes( bfile )
    ## print( data )
    print( switcher.define4creatures1( data[:-4] ) )
    ## print( struct.unpack('7c 4s 4s', data) )
    if (data.endswith( b'gend') ) : 
      print( "END OF GENOME" )
      break





