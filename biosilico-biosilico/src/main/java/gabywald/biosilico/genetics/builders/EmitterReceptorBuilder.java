package gabywald.biosilico.genetics.builders;

import gabywald.biosilico.genetics.EmitterReceptor;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class EmitterReceptorBuilder extends GeneBuilder<EmitterReceptor> {
	
	public EmitterReceptorBuilder() {
		super();
		this.map.put(GeneBuilderEnum.VARIABLE, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.THRESHOLD, Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.IOPUT, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.POSX, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.POSY, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.RECEPTOR, 	Pair.of(GeneAttemptedType.BOOLEAN,  "false"));
		this.map.put(GeneBuilderEnum.INTERNAL, 	Pair.of(GeneAttemptedType.BOOLEAN,  "false"));
	}
	
	public int setVariable(int variable) {
		return this.setInteger(GeneBuilderEnum.VARIABLE, variable);
	}
	
	public int setThreshold(int threshold) {
		return this.setInteger(GeneBuilderEnum.THRESHOLD, threshold);
	}
	
	public int setIOPut(int ioput) {
		return this.setInteger(GeneBuilderEnum.IOPUT, ioput);
	}
	
	public int setPosX(int posx) {
		return this.setInteger(GeneBuilderEnum.POSX, posx);
	}
	
	public int setPosY(int posy) {
		return this.setInteger(GeneBuilderEnum.POSY, posy);
	}
	
	public boolean setReceptor(boolean receptor) {
		return this.setBoolean(GeneBuilderEnum.RECEPTOR, receptor);
	}
	
	public boolean setInternal(boolean internal) {
		return this.setBoolean(GeneBuilderEnum.INTERNAL, internal);
	}
	
	public EmitterReceptorBuilder variable(int variable) {
		this.setVariable(variable);
		return this;
	}
	
	public EmitterReceptorBuilder threshold(int threshold) {
		this.setThreshold(threshold);
		return this;
	}
	
	public EmitterReceptorBuilder ioput(int ioput) {
		this.setIOPut(ioput);
		return this;
	}
	
	public EmitterReceptorBuilder posx(int posx) {
		this.setPosX(posx);
		return this;
	}
	
	public EmitterReceptorBuilder posy(int posy) {
		this.setPosY(posy);
		return this;
	}
	
	public EmitterReceptorBuilder receptor(boolean receptor) {
		this.setReceptor(receptor);
		return this;
	}
	
	public EmitterReceptorBuilder internal(boolean internal) {
		this.setInternal(internal);
		return this;
	}

	@Override
	public EmitterReceptor build() {
		return new EmitterReceptor(	
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.MUTATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DUPLICATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DELETE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.ACTIV).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MIN).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MAX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.SEX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.MUTATION_RATE).second), 
				
				Integer.parseInt(this.map.get(GeneBuilderEnum.VARIABLE).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.THRESHOLD).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.IOPUT).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.POSX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.POSY).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.RECEPTOR).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.INTERNAL).second));
	}

}
