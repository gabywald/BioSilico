package gabywald.biosilico.genetics.builders;

import gabywald.biosilico.genetics.BrainGene;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class BrainGeneBuilder extends GeneBuilder<BrainGene> {
	
	public BrainGeneBuilder() {
		super();
		this.map.put(GeneBuilderEnum.HEIGHT, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.WIDTH, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.DEPTH, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.MORE, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
	}
	
	public int setHeight(int heigth) {
		return this.setInteger(GeneBuilderEnum.HEIGHT, heigth);
	}

	public int setWidth(int width) {
		return this.setInteger(GeneBuilderEnum.WIDTH, width);
	}
	
	public int setDepth(int depth) {
		return this.setInteger(GeneBuilderEnum.DEPTH, depth);
	}
	
	public int setMore(int more) {
		return this.setInteger(GeneBuilderEnum.MORE, more);
	}
	
	public BrainGeneBuilder heigth(int heigth) {
		this.setHeight(heigth);
		return this;
	}
	
	public BrainGeneBuilder width(int width) {
		this.setWidth(width);
		return this;
	}
	
	public BrainGeneBuilder depth(int depth) {
		this.setDepth(depth);
		return this;
	}
	
	public BrainGeneBuilder more(int more) {
		this.setMore(more);
		return this;
	}

	@Override
	public BrainGene build() {
		BrainGene toReturn = new BrainGene(	
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.MUTATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DUPLICATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DELETE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.ACTIV).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MIN).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MAX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.SEX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.MUTATION_RATE).second), 
				
				Integer.parseInt(this.map.get(GeneBuilderEnum.HEIGHT).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.WIDTH).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.DEPTH).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.MORE).second));
		if ( ! this.map.get(GeneBuilderEnum.NAME).second.equals(""))
			{ toReturn.setName( this.map.get(GeneBuilderEnum.NAME).second ); }
		return toReturn;
	}

}
