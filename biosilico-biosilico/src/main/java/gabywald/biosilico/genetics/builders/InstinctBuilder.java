package gabywald.biosilico.genetics.builders;

import gabywald.biosilico.genetics.Instinct;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class InstinctBuilder extends GeneBuilder<Instinct> {

	public InstinctBuilder() {
		super();
		this.map.put(GeneBuilderEnum.INPUTPOSX, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.INPUTPOSY, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.OUTPUTPOSX, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.OUTPUTPOSY, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.WEIGHT, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.VARIABLE, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.THRESHOLD, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.CHECK, 		Pair.of(GeneAttemptedType.BOOLEAN,  "false"));
		this.map.put(GeneBuilderEnum.POSITIVE, 		Pair.of(GeneAttemptedType.BOOLEAN,  "true"));
	}
	
	public int setInputPosX(int inputPosX) {
		return this.setInteger(GeneBuilderEnum.INPUTPOSX, inputPosX);
	}
	
	public int setInputPosY(int inputPosY) {
		return this.setInteger(GeneBuilderEnum.INPUTPOSY, inputPosY);
	}
	
	public int setOutputPosX(int outputPosX) {
		return this.setInteger(GeneBuilderEnum.OUTPUTPOSX, outputPosX);
	}
	
	public int setOutputPosY(int outputPosY) {
		return this.setInteger(GeneBuilderEnum.OUTPUTPOSY, outputPosY);
	}
	
	public int setWeight(int weight) {
		return this.setInteger(GeneBuilderEnum.WEIGHT, weight);
	}
	
	public int setVariable(int variable) {
		return this.setInteger(GeneBuilderEnum.VARIABLE, variable);
	}
	
	public int setThreshold(int threshold) {
		return this.setInteger(GeneBuilderEnum.THRESHOLD, threshold);
	}
	
	public boolean setCheck(boolean check) {
		return this.setBoolean(GeneBuilderEnum.CHECK, check);
	}
	
	public boolean setPositive(boolean posi) {
		return this.setBoolean(GeneBuilderEnum.POSITIVE, posi);
	}
	
	public InstinctBuilder inputPosX(int inputPosX) {
		this.setInputPosX(inputPosX);
		return this;
	}
	
	public InstinctBuilder inputPosY(int inputPosY) {
		this.setInputPosY(inputPosY);
		return this;
	}
	
	public InstinctBuilder outputPosX(int outputPosX) {
		this.setOutputPosX(outputPosX);
		return this;
	}
	
	public InstinctBuilder outputPosY(int outputPosY) {
		this.setOutputPosY(outputPosY);
		return this;
	}
	
	public InstinctBuilder weight(int weight) {
		this.setWeight(weight);
		return this;
	}
	
	public InstinctBuilder variable(int variable) {
		this.setVariable(variable);
		return this;
	}
	
	public InstinctBuilder threshold(int threshold) {
		this.setThreshold(threshold);
		return this;
	}
	
	public InstinctBuilder check(boolean check) {
		this.setCheck(check);
		return this;
	}
	
	public InstinctBuilder positiv(boolean posi) {
		this.setPositive(posi);
		return this;
	}
	
	@Override
	public Instinct build() {
		Instinct toReturn = new Instinct(	
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.MUTATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DUPLICATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DELETE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.ACTIV).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MIN).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MAX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.SEX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.MUTATION_RATE).second), 
				
				Integer.parseInt(this.map.get(GeneBuilderEnum.INPUTPOSX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.INPUTPOSY).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.OUTPUTPOSX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.OUTPUTPOSY).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.WEIGHT).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.VARIABLE).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.THRESHOLD).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.CHECK).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.POSITIVE).second));
		if ( ! this.map.get(GeneBuilderEnum.NAME).second.equals(""))
			{ toReturn.setName( this.map.get(GeneBuilderEnum.NAME).second ); }
		return toReturn;
	}

}
