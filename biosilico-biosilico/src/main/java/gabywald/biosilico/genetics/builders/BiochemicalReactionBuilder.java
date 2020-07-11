package gabywald.biosilico.genetics.builders;

import gabywald.biosilico.genetics.BiochemicalReaction;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class BiochemicalReactionBuilder extends GeneBuilder<BiochemicalReaction> {

	public BiochemicalReactionBuilder() {
		super();
		this.map.put(GeneBuilderEnum.ACHEM, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.ACOEF, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.BCHEM, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.BCOEF, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.CCHEM, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.CCOEF, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.DCHEM, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.DCOEF, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.KMVM, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
	}

	public int setAchem(int achem) {
		return this.setInteger(GeneBuilderEnum.ACHEM, achem);
	}

	public int setAcoef(int acoef) {
		return this.setInteger(GeneBuilderEnum.ACOEF, acoef);
	}

	public int setBchem(int achem) {
		return this.setInteger(GeneBuilderEnum.BCHEM, achem);
	}

	public int setBcoef(int acoef) {
		return this.setInteger(GeneBuilderEnum.BCOEF, acoef);
	}

	public int setCchem(int achem) {
		return this.setInteger(GeneBuilderEnum.CCHEM, achem);
	}

	public int setCcoef(int acoef) {
		return this.setInteger(GeneBuilderEnum.CCOEF, acoef);
	}

	public int setDchem(int achem) {
		return this.setInteger(GeneBuilderEnum.DCHEM, achem);
	}

	public int setDcoef(int acoef) {
		return this.setInteger(GeneBuilderEnum.DCOEF, acoef);
	}

	public int setKMVM(int kmvm) {
		return this.setInteger(GeneBuilderEnum.KMVM, kmvm);
	}

	public BiochemicalReactionBuilder achem(int achem) {
		this.setAchem(achem);
		return this;
	}

	public BiochemicalReactionBuilder acoef(int acoef) {
		this.setAcoef(acoef);
		return this;
	}

	public BiochemicalReactionBuilder bchem(int achem) {
		this.setBchem(achem);
		return this;
	}

	public BiochemicalReactionBuilder bcoef(int acoef) {
		this.setBcoef(acoef);
		return this;
	}

	public BiochemicalReactionBuilder cchem(int achem) {
		this.setCchem(achem);
		return this;
	}

	public BiochemicalReactionBuilder ccoef(int acoef) {
		this.setCcoef(acoef);
		return this;
	}

	public BiochemicalReactionBuilder dchem(int achem) {
		this.setDchem(achem);
		return this;
	}

	public BiochemicalReactionBuilder dcoef(int acoef) {
		this.setDcoef(acoef);
		return this;
	}

	public BiochemicalReactionBuilder kmvm(int kmvm) {
		this.setKMVM(kmvm);
		return this;
	}

	@Override
	public BiochemicalReaction build() {
		return new BiochemicalReaction(	
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.MUTATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DUPLICATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DELETE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.ACTIV).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MIN).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MAX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.SEX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.MUTATION_RATE).second), 
				
				Integer.parseInt(this.map.get(GeneBuilderEnum.ACHEM).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.ACOEF).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.BCHEM).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.BCOEF).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.CCHEM).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.CCOEF).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.DCHEM).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.DCOEF).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.KMVM).second));
	}

}
