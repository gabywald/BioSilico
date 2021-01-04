package gabywald.biosilico.model.decisions;

import java.util.function.Function;

import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.ObjectType;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class BaseDecisionSimpleAction<T extends Agent> extends BaseDecisionOnlyOneAttribute {

	private Function<Agent, Boolean> toApply;
	
	BaseDecisionSimpleAction(Organism orga, int oneAttribute, Function<Agent, Boolean> function) {
		super(orga, oneAttribute);
		this.toApply = function;
	}

	@Override
	public void action() {
		ObjectType oType = ObjectType.getFrom(this.getVariable(0));
		Agent tmpAgent = (oType == ObjectType.CURRENT)? this.getOrga() : this.getOrga().getCurrentEnvironmentItem()
												.getObjectType( ObjectType.getFrom(this.getVariable(0)) );
		if (tmpAgent != null) { toApply.apply( tmpAgent ); }
	}

}
