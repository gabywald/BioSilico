#!/usr/bin/python3
# -*- coding: utf-8 -*-

## Notes : https://docs.python.org/3/library/configparser.html
import configparser

def readFileToList( filePath ) : 
  """Read file from path indicated in parameter and return it as a list of lines. """
  listToReturn = []
  with open(filePath, 'r') as file : 
    data     = file.read()
    listToReturn = data.split( "\n" )
  return listToReturn

def readConfigFile( config = "../data/sources.ini" ) : 
  """ To read config file ! """
  ## Use a configuration file ! '../data/sources.ini' !
  parser = configparser.ConfigParser()
  parser.read( config )
  return parser

def loadFileConfig( nameOfRSC ) : 
  """ To read file resources ! """
  parser = readConfigFile()
  if parser.has_option('paths', nameOfRSC):
    return readFileToList( parser[ "paths" ].get( nameOfRSC ) )
  else:
    return []
    
def loadDataConfig( nameOfRSC ) : 
  """ To read data resources ! """
  parser = readConfigFile()
  if parser.has_option('data', nameOfRSC):
    return parser[ "data" ].get( nameOfRSC )[2:-2].split( ", " )
  else:
    return []
