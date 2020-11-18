#!/usr/bin/python3
# -*- coding: utf-8 -*-

import struct

class GeneSwitcherCreatures1(object):
  
  def define4creatures1(self, arg):
    """Dispatch method"""
    if ( len(arg) < 7 ) : 
      return "STARTING" ## "Invalid / NO GENE (%s) " %(arg)
    valuesGeneHeader = struct.unpack('7c', arg[:7])
    obValuesGH = self.printGeneHeader( valuesGeneHeader )
    ## print( obValuesGH )
    print( "\t---%s, %s---" %(obValuesGH[0], obValuesGH[1]) )
    method_name = "geneC1_type%d_subt%d" %(obValuesGH[0], obValuesGH[1])
    # Get the method from 'self'. Default to a lambda. 
    method = getattr(self, method_name, lambda arg: "Invalid ! {%s}" %( obValuesGH ) )
    # Call the method as we return it
    return method( arg )
  
  def printGeneHeader(self, bValues) : 
    ## gt st sn dn ss fl mc
    ## genetype subtype sequencenumber duplicatenumber switchstage flags mutationschances
    # print( "\t---type: %d" %ord( bValues[0] ) )
    # print( "\tsubtype: %d" %ord( bValues[1] ) )
    # print( "\tseqnumb: %d" %ord( bValues[2] ) )
    # print( "\tdupnumb: %d" %ord( bValues[3] ) )
    # print( "\tswitch-: %d" %ord( bValues[4] ) )
    # print( "\tflags--: %d" %ord( bValues[5] ) )
    # print( "\tmutate-: %d" %ord( bValues[6] ) ) ## only from C2+ ?!
    return [ ord(value) for value in bValues[0:6] ]
 
  def geneC1_0(self, arg):
    return "No Gene"
    
  def geneC1_type0_subt0(self, arg) :
    ## Brain lobe
    pass

  def geneC1_type0_subt1(self, arg) :
    ## Brain organ
    pass
  
  def geneC1_type1_subt0(self, arg) :
    ## Receptor
    print( "Re (%d) {%s}" %(len(arg), arg) )
    pass
  
  def geneC1_type1_subt1(self, arg) :
    ## Emitter
    print( "Em (%d) {%s}" %(len(arg), arg) )
    pass
  
  def geneC1_type1_subt2(self, arg) :
    ## Chemical Reaction
    print( "CR (%d) {%s}" %(len(arg), arg) )
    pass
  
  def geneC1_type1_subt3(self, arg) :
    ## Half Lives
    print( "HL (%d) {%s}" %(len(arg), arg) )
    values = struct.unpack('6c 255c', arg[:261] )
    toreturn = GeneCreatures1("Half Lives", self.printGeneHeader(values))
    for i in range(6, 255+6) : 
      toreturn.appendContent( ord(values[ i ]) )
    return toreturn
  
  def geneC1_type1_subt4(self, arg) :
    ## Initial Concentration
    print( "IC (%d) {%s}" %(len(arg), arg) )
    try:
      values = struct.unpack('6c 1c 1c', arg)
      toreturn = GeneCreatures1("Initial Concentration", self.printGeneHeader(values))
      toreturn.appendContent( ord(values[6]) )
      toreturn.appendContent( ord(values[7]) )
      return toreturn
    except struct.error : 
      return "\t*****Not Well Structured ! IC (%d) {%s}*****" %(len(arg), arg)
  
  def geneC1_type2_subt1(self, arg) : 
    ## Genus
    values = struct.unpack('7c 4s 4s', arg)
    toreturn = GeneCreatures1("Genus", self.printGeneHeader(values))
    toreturn.appendContent( values[7].decode('ascii') )
    toreturn.appendContent( values[8].decode('ascii') )
    return toreturn

##      2     0        Stimulus
##      2     1        Genus
##      2     2        Appearance
##      2     3        Pose
##      2     4        Gait
##      2     5        Instinct
##      2     6        Pigment
##      2     7        Pigment bleed
##      3     0        Organ

class GeneCreatures1(object):
  """ Gene Creatures 1 """
  def __init__(self, type = None, header = [] ):
    """GeneCreatures1 Constructor. """
    self.type = type
    self.header = header
    self.contents = []
  
  def __str__(self) : 
    """GeneCreatures1 to str. """
    str = "GeneCreatures1 ( % s , % s ) \n"  % (self.type, self.header)
    str += "\t contents: %s \n" % (self.contents)
    return str
  
  def appendContent( self, content) : 
    self.contents.append( content )

