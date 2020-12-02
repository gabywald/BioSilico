package gabywald.creatures.genetics.simple.factory;

import java.util.HashMap;
import java.util.Map;

import gabywald.creatures.genetics.simple.Creatures1Gene;
import gabywald.creatures.genetics.simple.GeneTypeSubType;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreatures1Factory {
	
	private static GeneCreatures1Factory instance = null;
	
	private Map<String, GeneCreatures1Command> map2cmd = null;
	
	public GeneCreatures1Factory() {
		this.map2cmd = new HashMap<String, GeneCreatures1Command>();
/*
     0     0        Brain lobe
     0     1        Brain organ
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
     2     7        Pigment bleed
     3     0        Organ
 */
		// this.map2cmd.put("0-0", new GeneCreatures1CommandGeneric( "0-0" ));
		Map<String, GeneTypeSubType> mapGTST = GeneTypeSubType.getGeneTypesSubTypes();
		for (String key : mapGTST.keySet()) {
			this.map2cmd.put(key, new GeneCreatures1CommandGeneric( key ));
			// TODO precise some specific commands
		}
		this.map2cmd.put("2-1", new GeneCreatures1CommandGenus(  ));
	}
	
	private static GeneCreatures1Factory getInstance() {
		if (GeneCreatures1Factory.instance == null) 
			{ GeneCreatures1Factory.instance = new GeneCreatures1Factory(); }
		return GeneCreatures1Factory.instance;
	}
	
	public static Creatures1Gene generateFrom(String key, String input) {
		return GeneCreatures1Factory.getInstance().instanceGenerateFrom(key, input);
	}
	
	private Creatures1Gene instanceGenerateFrom(String key, String input) {
		if (this.map2cmd.containsKey(key)) {
			return this.map2cmd.get(key).generateFrom(input);
		} else {
			Logger.printlnLog(LoggerLevel.LL_WARNING, "key: {" + key + "} not found !");
		}
		return null;
	}
	
}
