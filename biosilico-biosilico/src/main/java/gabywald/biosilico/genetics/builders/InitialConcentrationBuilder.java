package gabywald.biosilico.genetics.builders;

import gabywald.biosilico.genetics.InitialConcentration;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class InitialConcentrationBuilder extends GeneBuilder<InitialConcentration> {

	public InitialConcentrationBuilder() {
		super();
		this.map.put(GeneBuilderEnum.VARIABLE, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.VALUE, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
	}
	
	public int setVariable(int varia) {
		return this.setInteger(GeneBuilderEnum.VARIABLE, varia);
	}

	public int setValue(int value) {
		return this.setInteger(GeneBuilderEnum.VALUE, value);
	}
	
	public InitialConcentrationBuilder varia(int varia) {
		this.setVariable(varia);
		return this;
	}

	public InitialConcentrationBuilder value(int value) {
		this.setValue(value);
		return this;
	}
	
	@Override
	public InitialConcentration build() {
		return new InitialConcentration(	
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.MUTATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DUPLICATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DELETE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.ACTIV).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MIN).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MAX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.SEX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.MUTATION_RATE).second), 
				
				Integer.parseInt(this.map.get(GeneBuilderEnum.VARIABLE).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.VALUE).second));
	}

}
