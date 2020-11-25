#!/usr/bin/python3
# -*- coding: utf-8 -*-

class GeneCreatures1(object):
  """ Gene Creatures 1 """
  def __init__(self, type = None, header = [], attemptedlength = None ):
    """GeneCreatures1 Constructor. """
    self.type = type
    self.header = header
    self.attempted = attemptedlength
    self.contents = []
    self.haserror = None
  
  def __str__(self) : 
    """GeneCreatures1 to str. """
    str = "GeneCreatures1 ( % s , % s ) \n"  % (self.type, self.header)
    str += "\t contents: %s \n" % (self.contents)
    if (self.haserror != None) : 
      str += "\thas (%s) errors \n" % (self.haserror)
    return str
  
  def appendContent( self, content) : 
    self.contents.append( content )
  
  def printInLine( self ) : 
    str = "%s : %s => %s" %(self.type, self.header, self.contents)
    if (self.haserror != None) : 
      str += "\thas errors (%s)" %(self.haserror)
    print( str )


class GeneTypeSubtype(object) : 
  """ Gene TypeSubtype Creatures 1 """
  def __init__(self, type = 0, subtype = 0, attemptedlength = None, name = "" ):
    """GeneTypeSubtype Constructor. """
    self.type = type
    self.subtype = subtype
    self.attempted = attemptedlength
    self.name = name
  
  def __str__(self) : 
    """GeneTypeSubtype to str. """
    return "GeneTypeSubtype ( % s , % s, [%s], '% s' ) "  \
                    % (self.type, self.subtype, self.attempted, self.name)


