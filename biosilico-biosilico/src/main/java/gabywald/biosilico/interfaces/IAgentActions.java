package gabywald.biosilico.interfaces;

import gabywald.biosilico.interfaces.functionnals.IAgentDeplace;
import gabywald.biosilico.interfaces.functionnals.IAgentPull;
import gabywald.biosilico.interfaces.functionnals.IAgentPush;
import gabywald.biosilico.interfaces.functionnals.IAgentRest;
import gabywald.biosilico.interfaces.functionnals.IAgentSlap;
import gabywald.biosilico.interfaces.functionnals.IAgentSleep;
import gabywald.biosilico.interfaces.functionnals.IAgentStop;

/**
 * To centralized the FuncrionalInterfaces on Agent. 
 * @author Gabriel Chandesris (2020)
 */
public interface IAgentActions 
	extends IAgentDeplace, IAgentPull, IAgentPush, IAgentSlap, IAgentStop, IAgentRest, IAgentSleep {
	// Use of methods / functions has to be defined !
	// And record in current chemical of Agent : Agent "implements IChemicalsContent, IAgentActions" !
}
