package gabywald.creatures.genetics;

import gabywald.global.exceptions.MessageException;

/**
 * 
 * @author Gabriel Chandesris (2013)
 */
public class CreatureGeneException extends MessageException {
	public static String[][] CORRESPONDING_NAMES = {
		{ "Brain Lobe" }, 
		{ "BioCH Receptor", "BioCH Emitter", "BioCH Reaction", "BioCH Half_Lives", "BioCH Initial" }, 
		{ "Creature Stimulus", "Creature Genus", "Creature Appearance", "Creature Pose", 
			"Creature Gait", "Creature Instinct", "Creature Pigment", "Creature Pigment Bleed" }
	};
	
	public CreatureGeneException(int type, int subt) 
		{ super("Creature Gene Exception (" + type + ", " + subt + ") [" + 
					CreatureGeneException.CORRESPONDING_NAMES[type][subt] + "]"); }
	
	public CreatureGeneException(int type, int subt, String msg) 
		{ super("Creature Gene Exception (" + type + ", " + subt + ") [" + 
					CreatureGeneException.CORRESPONDING_NAMES[type][subt] + "] + {" + msg + "}"); }
}
