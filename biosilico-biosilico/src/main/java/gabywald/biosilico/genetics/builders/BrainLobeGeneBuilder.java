package gabywald.biosilico.genetics.builders;

import gabywald.biosilico.genetics.BrainLobeGene;
import gabywald.biosilico.model.Brain;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class BrainLobeGeneBuilder extends GeneBuilder<BrainLobeGene> {

	public BrainLobeGeneBuilder() {
		super();
		this.map.put(GeneBuilderEnum.REST, 			Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.THRESHOLD, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.DESC, 			Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.DENDRITICMIN, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.DENDRITICMAX, 	Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.PROX, 			Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.REPR, 			Pair.of(GeneAttemptedType.BOOLEAN,  "false"));
		this.map.put(GeneBuilderEnum.REPY, 			Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.WTA, 			Pair.of(GeneAttemptedType.BOOLEAN,  "false"));
		this.map.put(GeneBuilderEnum.HEIGHT, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.WIDTH, 		Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.POSX, 			Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.POSY, 			Pair.of(GeneAttemptedType.INTEGER,  "0"));
		this.map.put(GeneBuilderEnum.REPLACE, 		Pair.of(GeneAttemptedType.BOOLEAN,  "false"));
	}
	
	public int setRest(int rest) {
		return this.setInteger(GeneBuilderEnum.REST, rest);
	}
	
	public int setThreshold(int threshold) {
		return this.setInteger(GeneBuilderEnum.THRESHOLD, threshold);
	}
	
	public int setDesc(int desc) {
		return this.setInteger(GeneBuilderEnum.DESC, desc);
	}
	
	public int setDendriticMin(int dmin) {
		return this.setInteger(GeneBuilderEnum.DENDRITICMIN, dmin);
	}
	
	public int setDendriticMax(int dmax) {
		return this.setInteger(GeneBuilderEnum.DENDRITICMAX, dmax);
	}
	
	public int setProx(int prox) {
		return this.setInteger(GeneBuilderEnum.PROX, prox);
	}
	
	public int setRepy(int repy) {
		return this.setInteger(GeneBuilderEnum.REPY, repy);
	}
	
	public int setHeigth(int heigth) {
		return this.setInteger(GeneBuilderEnum.HEIGHT, heigth);
	}
	
	public int setWidth(int width) {
		return this.setInteger(GeneBuilderEnum.WIDTH, width);
	}
	
	public int setPosX(int posx) {
		return this.setInteger(GeneBuilderEnum.POSX, posx);
	}
	
	public int setPosY(int posy) {
		return this.setInteger(GeneBuilderEnum.POSY, posy);
	}
	
	public boolean setRepr(boolean repr) {
		return this.setBoolean(GeneBuilderEnum.REPR, repr);
	}
	
	public boolean setWTA(boolean wta) {
		return this.setBoolean(GeneBuilderEnum.WTA, wta);
	}
	
	public boolean setReplace(boolean replace) {
		return this.setBoolean(GeneBuilderEnum.REPLACE, replace);
	}
	
	public BrainLobeGeneBuilder rest(int rest) {
		this.setRest(rest);
		return this;
	}
	
	public BrainLobeGeneBuilder threshold(int threshold) {
		this.setThreshold(threshold);
		return this;
	}
	
	public BrainLobeGeneBuilder desc(int desc) {
		this.setDesc(desc);
		return this;
	}
	
	public BrainLobeGeneBuilder dmin(int dmin) {
		this.setDendriticMin(dmin);
		return this;
	}
	
	public BrainLobeGeneBuilder dmax(int dmax) {
		this.setDendriticMax(dmax);
		return this;
	}
	
	public BrainLobeGeneBuilder prox(int prox) {
		this.setProx(prox);
		return this;
	}
	
	public BrainLobeGeneBuilder repy(int repy) {
		this.setRepy(repy);
		return this;
	}
	
	public BrainLobeGeneBuilder heigth(int heigth) {
		this.setHeigth(heigth);
		return this;
	}
	
	public BrainLobeGeneBuilder width(int width) {
		this.setWidth(width);
		return this;
	}
	
	public BrainLobeGeneBuilder posx(int posx) {
		this.setPosX(posx);
		return this;
	}
	
	public BrainLobeGeneBuilder posy(int posy) {
		this.setPosY(posy);
		return this;
	}
	
	public BrainLobeGeneBuilder repr(boolean repr) {
		this.setRepr(repr);
		return this;
	}
	
	public BrainLobeGeneBuilder wta(boolean wta) {
		this.setWTA(wta);
		return this;
	}
	
	public BrainLobeGeneBuilder replace(boolean replace) {
		this.setReplace(replace);
		return this;
	}
	
	@Override
	public BrainLobeGene build() {
		return new BrainLobeGene(	
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.MUTATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DUPLICATE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.DELETE).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.ACTIV).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MIN).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.AGE_MAX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.SEX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.MUTATION_RATE).second), 
				
				Integer.parseInt(this.map.get(GeneBuilderEnum.REST).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.THRESHOLD).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.DESC).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.DENDRITICMIN).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.DENDRITICMAX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.PROX).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.REPR).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.REPY).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.WTA).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.HEIGHT).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.WIDTH).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.POSX).second), 
				Integer.parseInt(this.map.get(GeneBuilderEnum.POSY).second), 
				Boolean.parseBoolean(this.map.get(GeneBuilderEnum.REPLACE).second));
	}
	
	// ***** some pre-build example BrainLobeGene !
	
	// Neuron(	int rest, int thre, int desc, 
	// 			int dendriticmin, int dendriticmax,
	// 			int prox, boolean repr, int repy, 
	//			boolean wta)
	// NOTE Receptor Neuron : 0, 100, 10, 0, 0, 0, false, 0, false
	// NOTE Emitter- Neuron : 0, 100, 10, 8, 16, 4, false, 0, false
	// NOTE Decision Neuron : 0, 100, 10, 8, 16, 4, false, 0, true
	// NOTE Conception Neuron : 0, 10, 1, 3, 8, 2, true, 2
	
	private static BrainLobeGeneBuilder instance = null;
	
	public static BrainLobeGeneBuilder getBuilderExampleInstance() {
		if (BrainLobeGeneBuilder.instance == null) {
			BrainLobeGeneBuilder.instance = new BrainLobeGeneBuilder();
		}
		return BrainLobeGeneBuilder.instance;
	}
	
	public static BrainLobeGene generateReceptorLobeGene() {
		return BrainLobeGeneBuilder.getBuilderExampleInstance()
					.heigth(1).width(Brain.MAX_WIDTH / 2)
					.rest(0).threshold(100).desc(10).dmin(0).dmax(0).prox(0).repr(false).repy(0).wta(false)
					.posx(0).posy(0).replace(false)
					.agemin(0).agemax(0).mutation(50).build();
	}
	
	public static BrainLobeGene generateEmitterLobeGene() {
		return BrainLobeGeneBuilder.getBuilderExampleInstance()
					.heigth(1).width(Brain.MAX_WIDTH)
					.rest(0).threshold(100).desc(10).dmin(8).dmax(16).prox(4).repr(false).repy(0).wta(false)
					.posx(Brain.MAX_HEIGHT - 1).posy(0).replace(false)
					.agemin(0).agemax(0).mutation(50).build();
	}
	
	public static BrainLobeGene generateDecisionLobeGene() {
		return BrainLobeGeneBuilder.getBuilderExampleInstance()
					.heigth(1).width(Brain.MAX_WIDTH)
					.rest(0).threshold(100).desc(10).dmin(8).dmax(16).prox(4).repr(false).repy(0).wta(true)
					.posx(Brain.MAX_HEIGHT - 1).posy(0).replace(false)
					.agemin(0).agemax(0).mutation(50).build();
	}
	
	public static BrainLobeGene generateConceptionLobeGene() {
		return BrainLobeGeneBuilder.getBuilderExampleInstance()
					.heigth(Brain.MAX_HEIGHT - 2).width(Brain.MAX_WIDTH - 7)
					.rest(0).threshold(10).desc(1).dmin(3).dmax(8).prox(2).repr(true).repy(2).wta(false)
					.posx(1).posy(3).replace(true)
					.agemin(0).agemax(0).mutation(50).build();
	}
	
	

}
