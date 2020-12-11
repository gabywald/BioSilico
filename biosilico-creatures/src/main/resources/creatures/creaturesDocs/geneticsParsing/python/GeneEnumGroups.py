#!/usr/bin/python3
# -*- coding: utf-8 -*-

class GeneEnumGroups(object) : 
  """ GeneEnumGroups : Multi-Singleton for some enumerations of data (fixed sets) """
  _containerTSG = None
  _containerSVR = None
  _containerGBF = None
  _containerBoP = None
  _containerSOS = None
  _containerSpe = None
  _containerPiC = None
  
  @classmethod
  def getEnumsTSG(self) : 
    """ Type and Subtype Gene definitions """
    if (self._containerTSG != None) : 
      return self._containerTSG
    data = ModuleHelper.loadFileConfig( "geneC1C2definitions" )
    self._containerTSG = []
    for line in data : 
      lineDetection = re.match( "^(.*?)\t(.*?)\t(.*?)?$", line)
      if (lineDetection != None) : 
        self._containerTSG.append( GeneTypeSubtype( lineDetection.groups()[0], \
                                                    lineDetection.groups()[1], \
                                                    lineDetection.groups()[2], \
                                                    lineDetection.groups()[3] ) )
    return self._containerTSG
  
  @classmethod
  def getEnumsDatas(self, name) : 
    return ModuleHelper.loadDataConfig( name )
  
  @classmethod
  def getEnumsSVRules(self) : 
    """ SVRules """
    if (self._containerSVR != None) : 
      return self._containerSVR
    self._containerSVR = GeneEnumGroups.getEnumsDatas( "svrules" )
    return self._containerSVR
  
  @classmethod
  def getEnumsGeneBitFlags(self) : 
    """ Gene Bit Flags """
    if (self._containerGBF != None) : 
      return self._containerGBF
    self._containerGBF = GeneEnumGroups.getEnumsDatas( "genebitflags" )
    return self._containerGBF
  
  @classmethod
  def getEnumsBodyParts(self) : 
    """ Body Parts """
    if (self._containerBoP != None) : 
      return self._containerBoP
    self._containerBoP = GeneEnumGroups.getEnumsDatas( "bodyparts" )
    return self._containerBoP
  
  @classmethod
  def getEnumsSwitchOnStage(self) : 
    """ Switch On Stage """
    if (self._containerSOS != None) : 
      return self._containerSOS
    self._containerSOS = GeneEnumGroups.getEnumsDatas( "switchonstage" )
    return self._containerSOS
  
  @classmethod
  def getEnumsSpecies(self) : 
    """ Species """
    if (self._containerSpe != None) : 
      return self._containerSpe
    self._containerSpe = GeneEnumGroups.getEnumsDatas( "species" )
    return self._containerSpe
  
  @classmethod
  def getEnumsPigmentColor(self) : 
    """ Pigment Color """
    if (self._containerPiC != None) : 
      return self._containerPiC
    self._containerPiC = GeneEnumGroups.getEnumsDatas( "pigmentcolor" )
    return self._containerPiC


