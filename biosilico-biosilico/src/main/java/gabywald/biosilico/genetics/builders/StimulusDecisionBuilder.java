package gabywald.biosilico.genetics.builders;

import gabywald.biosilico.genetics.StimulusDecision;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class StimulusDecisionBuilder extends GeneBuilder<StimulusDecision> {
	
	public StimulusDecisionBuilder() {
		super();
		this.map.put(GeneBuilderEnum.PERCEPTION, 	Pair.of(GeneAttemptedType.BOOLEAN,  "false"));
		this.map.put(GeneBuilderEnum.OBJECT, 		Pair.of(GeneAttemptedType.BOOLEAN,  "false"));
		this.map.put(GeneBuilderEnum.INDICATOR, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.THRESHOLD, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.ATTRIBUTE, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.VARIABLE, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.VALUE, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.SCRIPT, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
	}
	
	
	public boolean setPerception(boolean perception) {
		return this.setBoolean(GeneBuilderEnum.PERCEPTION, perception);
	}
	
	public boolean setObject(boolean object) {
		return this.setBoolean(GeneBuilderEnum.OBJECT, object);
	}
	
	public int setIndicator(int indicator) {
		return this.setInteger(GeneBuilderEnum.INDICATOR, indicator);
	}

	public int setThreshold(int threshold) {
		return this.setInteger(GeneBuilderEnum.THRESHOLD, threshold);
	}
	
	public int setAttribute(int attribute) {
		return this.setInteger(GeneBuilderEnum.ATTRIBUTE, attribute);
	}
	
	public int setVariable(int varia) {
		return this.setInteger(GeneBuilderEnum.VARIABLE, varia);
	}

	public int setValue(int value) {
		return this.setInteger(GeneBuilderEnum.VALUE, value);
	}
	
	public int setScript(int script) {
		return this.setInteger(GeneBuilderEnum.SCRIPT, script);
	}
	
	public StimulusDecisionBuilder perception(boolean perception) {
		this.setPerception(perception);
		return this;
	}
	
	public StimulusDecisionBuilder object(boolean object) {
		this.setObject(object);
		return this;
	}
	
	public StimulusDecisionBuilder indicator(int indicator) {
		this.setIndicator(indicator);
		return this;
	}

	public StimulusDecisionBuilder threshold(int threshold) {
		this.setThreshold(threshold);
		return this;
	}
	
	public StimulusDecisionBuilder attribute(int attribute) {
		this.setAttribute(attribute);
		return this;
	}
	
	public StimulusDecisionBuilder varia(int varia) {
		this.setVariable(varia);
		return this;
	}

	public StimulusDecisionBuilder value(int value) {
		this.setValue(value);
		return this;
	}
	
	public StimulusDecisionBuilder script(int script) {
		this.setScript(script);
		return this;
	}
	
	@Override
	public StimulusDecision build() {
		StimulusDecision toReturn = new StimulusDecision(	
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.MUTATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DUPLICATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DELETE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.ACTIV).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MIN).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MAX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.SEX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.MUTATION_RATE).second), 
				
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.PERCEPTION).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.OBJECT).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.INDICATOR).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.THRESHOLD).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.ATTRIBUTE).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.VARIABLE).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.VALUE).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.SCRIPT).second));
		if ( ! this.map.get(GeneBuilderEnum.NAME).second.equals(""))
			{ toReturn.setName( this.map.get(GeneBuilderEnum.NAME).second ); }
		return toReturn;
	}

}
