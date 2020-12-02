package gabywald.creatures.genetics.simple;

import gabywald.global.exceptions.MessageException;

/**
 * 
 * @author Gabriel Chandesris (2013, 2020)
 */
@SuppressWarnings("serial")
public class CreatureGeneException extends MessageException {
	public CreatureGeneException(int type, int subt) 
		{ super("Creature Gene Exception (" + type + ", " + subt + ") [" + 
				GeneTypeSubType.getGeneTypeSubType( type, subt ) + "]"); }
	
	public CreatureGeneException(int type, int subt, String msg) 
		{ super("Creature Gene Exception (" + type + ", " + subt + ") [" + 
				GeneTypeSubType.getGeneTypeSubType( type, subt ) + "] + {" + msg + "}"); }
}
