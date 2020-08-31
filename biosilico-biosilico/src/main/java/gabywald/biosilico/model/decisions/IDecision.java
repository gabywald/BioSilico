package gabywald.biosilico.model.decisions;

import gabywald.biosilico.interfaces.IAgentActions;
import gabywald.biosilico.interfaces.IChemicalsContent;
import gabywald.biosilico.model.enums.DecisionType;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
@FunctionalInterface
public interface IDecision {

	/**
	 * Defines the action to make. 
	 */
	public void action();
	
	/**
	 * To record action in chemical just when done in agent's instance. . 
	 * @param agent Agent's instance. 
	 * @param dType1 Typically DecisionType.RECORDSTATE 
	 * @param dType2 Other element from DecisionType enum. 
	 * @see {@link IAgentActions}
	 * @see {@link IChemicalsContent}
	 */
	public static void recordInChemical(IChemicalsContent agent, DecisionType dType1, DecisionType dType2) {
		agent.getChemicals().setVariable(dType1.getIndex(), dType2.getIndex());
	}
	
}
