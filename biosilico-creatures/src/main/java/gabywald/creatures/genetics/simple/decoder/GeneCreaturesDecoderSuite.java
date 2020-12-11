package gabywald.creatures.genetics.simple.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gabywald.creatures.genetics.simple.Creatures1GenomeParser;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

public class GeneCreaturesDecoderSuite {

	public static List<IGeneCreaturesDecoder> getCreatures1Suite() {
		return GeneCreaturesDecoderSuite.getSuite( "configuration.GeneCreaturesDecoderSuite.classes.creatures1" );
	}
	
	private static List<IGeneCreaturesDecoder> getSuite(String classesProperty) {
		List<IGeneCreaturesDecoder> toReturn = new ArrayList<IGeneCreaturesDecoder>();
		String packagePath		= Creatures1GenomeParser.PROPERTIES.getProperty( "configuration.GeneCreaturesDecoderSuite.package" );
		List<String> classes	= Arrays.asList( Creatures1GenomeParser.PROPERTIES.getProperty( classesProperty ).split( ";" ) );
		
		for (String classe : classes) {
			String completeClass = packagePath + "." + classe;
			try {
				Class<IGeneCreaturesDecoder> loadClass = GeneCreaturesDecoderSuite.extraction(completeClass);
				Logger.printlnLog(LoggerLevel.LL_INFO, loadClass.toString());
				toReturn.add( loadClass.newInstance() );
			} 
			catch (ClassNotFoundException e) { e.printStackTrace(); } 
			catch (InstantiationException e) { e.printStackTrace(); } 
			catch (IllegalAccessException e) { e.printStackTrace(); } 
		}
		
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	private static Class<IGeneCreaturesDecoder> extraction(String completeClass) throws ClassNotFoundException {
		return (Class<IGeneCreaturesDecoder>) GeneCreaturesDecoderSuite.class.getClassLoader().loadClass( completeClass );
	}
}
