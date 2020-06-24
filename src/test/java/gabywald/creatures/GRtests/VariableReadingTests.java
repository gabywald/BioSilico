package gabywald.creatures.GRtests;

import gabywald.creatures.model.VariableDefinition;
import gabywald.global.exceptions.MessageException;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class VariableReadingTests extends TestCase {
	
	public void testGTC001() {
		String variableFile = "resources/creatures/creaturesArchivesResources/geNorNics/creatures1chemicals.txt";
		
		List<VariableDefinition> variableDefinitions = new ArrayList<VariableDefinition>();
		
		try {
			InputStream ips 		= new FileInputStream( variableFile );
			InputStreamReader ipsr	= new InputStreamReader(ips);
			BufferedReader br		= new BufferedReader(ipsr);
			String line;
			while ( (line = br.readLine()) != null) {
				List<VariableDefinition> tmpVarDefs = new ArrayList<VariableDefinition>();
				try { tmpVarDefs = VariableDefinition.loadVariableDefinitions(line); }
				catch(MessageException e) 
					{ Logger.printlnLog(LoggerLevel.LL_WARNING, "line not read -- {" + line + "}"); }
				if (tmpVarDefs.size() != 0) { 
					for (int i = 0 ; i < tmpVarDefs.size(); i++) 
						{ variableDefinitions.add(tmpVarDefs.get(i)); }
				} /** END "if (tmpVarDefs.size() != 0)" */
			} /** END "while ( (line = br.readLine()) != null)" */
			br.close();
		} 
		catch (FileNotFoundException e)	{ Logger.printlnLog(LoggerLevel.LL_ERROR, "File Not Found !"); }
		catch (IOException e)			{ Logger.printlnLog(LoggerLevel.LL_ERROR, "IOException !"); }
		
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + variableFile + "] -- {" + variableDefinitions.size() + " variables}");
		
		TestCase.assertEquals(true, (variableDefinitions.size() > 0) );
	}
	
	public void testGTC002() {
		String variableFile = "resources/creatures/creaturesArchivesResources/geNorNics/creatures2chemicals.txt";
		
		List<VariableDefinition> variableDefinitions = new ArrayList<VariableDefinition>();
		
		try {
			InputStream ips 		= new FileInputStream( variableFile );
			InputStreamReader ipsr	= new InputStreamReader(ips);
			BufferedReader br		= new BufferedReader(ipsr);
			String line;
			while ( (line = br.readLine()) != null) {
				List<VariableDefinition> tmpVarDefs = new ArrayList<VariableDefinition>();
				try { tmpVarDefs = VariableDefinition.loadVariableDefinitions(line); }
				catch(MessageException e) 
					{ Logger.printlnLog(LoggerLevel.LL_WARNING, "line not read -- {" + line + "}"); }
				if (tmpVarDefs.size() != 0) { 
					for (int i = 0 ; i < tmpVarDefs.size(); i++) 
						{ variableDefinitions.add(tmpVarDefs.get(i)); }
				} /** END "if (tmpVarDefs.size() != 0)" */
			} /** END "while ( (line = br.readLine()) != null)" */
			br.close();
		} 
		catch (FileNotFoundException e)	{ Logger.printlnLog(LoggerLevel.LL_ERROR, "File Not Found !"); }
		catch (IOException e)			{ Logger.printlnLog(LoggerLevel.LL_ERROR, "IOException !"); }
		
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + variableFile + "] -- {" + variableDefinitions.size() + " variables}");
		
		TestCase.assertEquals(true, (variableDefinitions.size() > 0) );
	}
}
