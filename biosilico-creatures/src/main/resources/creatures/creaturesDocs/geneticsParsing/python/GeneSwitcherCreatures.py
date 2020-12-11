#!/usr/bin/python3
# -*- coding: utf-8 -*-

import struct
import re

import ModuleHelper

from GenesCreatures import GeneCreatures1

class GeneSwitcherCreatures1(object):
  """GeneSwitcherCreatures1 permit to switch between good methods to call for Gene Treatment"""
  def define4creatures1(self, arg):
    """Dispatch method"""
    if ( len(arg) < 7 ) : 
      return "STARTING" ## "Invalid / NO GENE (%s) " %(arg)
    valuesGeneHeader = struct.unpack('<7c', arg[:7])
    obValuesGH = self.printGeneHeader( valuesGeneHeader )
    ## print( obValuesGH )
    ## print( "\t---%s, %s---" %(obValuesGH[0], obValuesGH[1]) )
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
  
  def parseDataAndCreateObjectC1(self, arg, name, nbelts) : 
    baselen = 6 + nbelts
    haserror = None
    if (len(arg) < baselen) : 
      haserror = 0
      xs = bytearray( arg )
      while (len(arg) < baselen) : 
        ## arg.append( b'\x00' )
        arg = arg + bytes(chr(0), 'ascii')
        haserror += 1
      ## print( "INCOMPLETE DATA !" )
    ## print ( "*****%s*****" %(str(arg)) )
    values = struct.unpack('<6c %dc' %(nbelts), arg[:baselen] )
    toreturn = GeneCreatures1(name, self.printGeneHeader(values))
    for i in range(6, baselen) : 
      toreturn.appendContent( ord(values[ i ] ) )
    toreturn.haserror = haserror
    return toreturn
  
  def geneC1_type0_subt0(self, arg) :
    ## Brain lobe
    ## print( "Bl (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Brain lobe", 112)

  def geneC1_type0_subt1(self, arg) :
    ## Brain organ
    print( "Bo (%d) {%s}" %(len(arg), arg) )
    pass
  
  def geneC1_type1_subt0(self, arg) :
    ## Receptor
    ## print( "Re (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Receptor", 8)
  
  def geneC1_type1_subt1(self, arg) :
    ## Emitter
    ## print( "Em (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Emitter", 8)
  
  def geneC1_type1_subt2(self, arg) :
    ## Chemical Reaction
    ## print( "CR (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Chemical Reaction", 9)
  
  def geneC1_type1_subt3(self, arg) :
    ## Half Lives
    ## print( "HL (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Half Lives", 255)
  
  def geneC1_type1_subt4(self, arg) :
    ## Initial Concentration
    ## print( "IC (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Initial Concentration", 2)
    
  def geneC1_type2_subt1(self, arg) : 
    ## Genus : 2 strings
    values = struct.unpack('<7c 4s 4s', arg)
    toreturn = GeneCreatures1("Genus", self.printGeneHeader(values))
    toreturn.appendContent( values[7].decode('ascii') )
    toreturn.appendContent( values[8].decode('ascii') )
    return toreturn
  
  def geneC1_type2_subt0(self, arg) :
    ## Stimulus
    ## print( "ST (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Stimulus", 13)
  
  def geneC1_type2_subt2(self, arg) :
    ## Appearance
    ## print( "AP (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Appearance", 2)
  
  def geneC1_type2_subt3(self, arg) :
    ## Pose
    ## print( "PO (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Pose", 16)
  
  def geneC1_type2_subt4(self, arg) :
    ## Gait
    ## print( "GA (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Gait", 9)
  
  def geneC1_type2_subt5(self, arg) :
    ## Instinct
    ## print( "IN (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Instinct", 9)
  
  def geneC1_type2_subt6(self, arg) :
    ## Pigment
    ## print( "Pi (%d) {%s}" %(len(arg), arg) )
    return self.parseDataAndCreateObjectC1(arg, "Pigment", 2)
  
  def geneC1_type2_subt7(self, arg) :
    ## Pigment bleed
    print( "PB (%d) {%s}" %(len(arg), arg) )
    pass
  
  def geneC1_type3_subt0(self, arg) :
    ## Organ
    print( "OR (%d) {%s}" %(len(arg), arg) )
    pass
    

