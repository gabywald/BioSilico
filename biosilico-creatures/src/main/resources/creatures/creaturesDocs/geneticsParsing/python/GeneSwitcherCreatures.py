#!/usr/bin/python3
# -*- coding: utf-8 -*-

import struct

class GeneSwitcherCreatures1(object):
  
  def define4creatures1(self, arg):
    """Dispatch method"""
    method_name = 'geneC1_' + str(len(arg))
    # Get the method from 'self'. Default to a lambda. 
    method = getattr(self, method_name, lambda arg: "Invalid ! (%d) {%s}" %(len(arg), arg) )
    # Call the method as we return it
    return method( arg )
 
  def geneC1_0(self, arg):
    return "No Gene"
  
  def geneC1_15(self, arg) : 
    print( struct.unpack('7c 4s 4s', arg) )
    return "Gene of length 15 {%s}" %(arg)
  
  def geneC1_22(self, arg) : 
    return "Gene of length 22 {%s}" %(arg)
  
  def geneC1_8(self, arg) : 
    return "Gene of length 8 {%s}" %(arg)
