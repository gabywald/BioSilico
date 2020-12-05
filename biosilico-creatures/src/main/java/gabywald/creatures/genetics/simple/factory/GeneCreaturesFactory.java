package gabywald.creatures.genetics.simple.factory;

import java.util.HashMap;
import java.util.Map;

import gabywald.creatures.genetics.simple.GeneTypeSubType;
import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesFactory {
	
	private static GeneCreaturesFactory instance = null;
	
	private Map<String, IGeneCreaturesStrategy> map2cmd = null;
	
	public GeneCreaturesFactory() {
		this.map2cmd = new HashMap<String, IGeneCreaturesStrategy>();
/*
     0     0        Brain lobe
     0     1        Brain organ (C2)
     1     0        Receptor
     1     1        Emitter
     1     2        Chemical reaction
     1     3        Half lives
     1     4        Initial concentration
     2     0        Stimulus
     2     1        Genus
     2     2        Appearance
     2     3        Pose
     2     4        Gait
     2     5        Instinct
     2     6        Pigment
     2     7        Pigment bleed (C2)
     3     0        Organ (C2)
 */
		Map<String, GeneTypeSubType> mapGTST = GeneTypeSubType.getGeneTypesSubTypes();
		for (String key : mapGTST.keySet()) 
			{ this.map2cmd.put(key, new GeneCreaturesStrategyGeneric( key )); }
		// this.map2cmd.put("2-1", new GeneCreaturesStrategyGenusC1(  ));
	}
	
	private static GeneCreaturesFactory getInstance() {
		if (GeneCreaturesFactory.instance == null) 
			{ GeneCreaturesFactory.instance = new GeneCreaturesFactory(); }
		return GeneCreaturesFactory.instance;
	}
	
	public static ICreaturesGene generateFrom(String key, String input) {
		return GeneCreaturesFactory.getInstance().instanceGenerateFrom(key, input);
	}
	
	private ICreaturesGene instanceGenerateFrom(String key, String input) {
		if (this.map2cmd.containsKey(key)) {
			return this.map2cmd.get(key).generateFrom(input);
		} else {
			Logger.printlnLog(LoggerLevel.LL_WARNING, "key: {" + key + "} not found !");
		}
		return null;
	}
	
}
