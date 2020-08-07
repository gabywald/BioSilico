package gabywald.biosilico.interfaces;

import gabywald.biosilico.interfaces.functionnals.AgentDeplace;
import gabywald.biosilico.interfaces.functionnals.AgentPull;
import gabywald.biosilico.interfaces.functionnals.AgentPush;
import gabywald.biosilico.interfaces.functionnals.AgentSlap;
import gabywald.biosilico.interfaces.functionnals.AgentStop;

/**
 * To centralized the FuncrionalInterfaces on Agent. 
 * @author Gabriel Chandesris (2020)
 */
public interface IAgentActions 
	extends AgentDeplace, AgentPull, AgentPush, AgentSlap, AgentStop {
	// Use of methods / functions has to be defined !
}
