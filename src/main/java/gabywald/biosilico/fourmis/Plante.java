package gabywald.biosilico.fourmis;

/**
 * This class describes plants which produce fruits. 
 * @author Gabriel Chandesris (2009)
 * @see Fruit
 */
public class Plante extends Agent {

	/** Default Constructor. */
	public Plante() { 
		super(true,false,false);
		for (int i = 0 ; i < 10 ; i++) 
			{ this.variables.setVariable(i, 100); }
	}
	
	public void execution(WorldCase local) {
		this.biochemistery();
		this.emitterReceptor(local);
		this.stimulus(local);
	}
	
	public void deplace(WorldCase local) 
		{ ; } /** Plants do not move */
	
	protected void biochemistery() {
		this.BioCHGene( 0, 1, 1, 1, 2, 1, 3, 1, 5);
		this.BioCHGene( 2, 1, 3, 1, 4, 1, 5, 1, 5);
		this.BioCHGene( 4, 1, 5, 1, 6, 1, 7, 1, 5);
		this.BioCHGene( 6, 1, 7, 1, 8, 1, 9, 1, 5);
		this.BioCHGene( 8, 1, 9, 1,10, 1,11, 1, 5);
		this.BioCHGene(10, 1,11, 1,12, 1,13, 1, 5);
		this.BioCHGene(12, 1,13, 1,14, 1,15, 1, 5);
		this.BioCHGene(14, 1,15, 1,16, 1,17, 1, 5);
		this.BioCHGene(16, 1,17, 1,18, 1,19, 1, 5);
		this.BioCHGene(18, 1,19, 1,20, 1,21, 1, 5);
		this.BioCHGene(20, 1,21, 1,22, 1,23, 1, 5);
		this.BioCHGene(22, 1,23, 1,24, 1,25, 1, 5);
		this.BioCHGene(24, 1,25, 1,26, 1,27, 1, 5);
		this.BioCHGene(26, 1,27, 1,28, 1,29, 1, 5);
		this.BioCHGene(28, 1,29, 1,30, 1,31, 1, 5);
		this.BioCHGene(30, 1,31, 1,32, 1,33, 1, 5);
		this.BioCHGene(32, 1,33, 1,34, 1,35, 1, 5);
		this.BioCHGene(34, 1,35, 1,36, 1,37, 1, 5);
	}
	
	protected void stimulus(WorldCase local) 
		{ this.exportFruits(local); }
	
	protected void emitterReceptor(WorldCase local) {
		/** external receptor's */
		this.importUse(local);
		/** external emitter's */
		this.exportUse(local);
	}
	
	/**
	 * A receptor from WorldCase for chemicals 0 to 6, step of 5. 
	 * @param local (WorldCase)
	 */
	private void importUse(WorldCase local) {
		int more = 5;
		for (int i = 0 ; i < 6 ; i++) { 
			if (local.getVariable(i) > more) {
				this.variables.setVarPlus(i, more);
				local.addToVariable(i, (-1)*more);
			}
		}
	}
	
	/**
	 * An emitter to WorldCase for chemicals 30 to 36, step of 5. 
	 * @param local (WorldCase)
	 */
	private void exportUse(WorldCase local) {
		int less = 5;
		for (int i = 30 ; i < 36 ; i++) { 
			if (this.variables.getVariable(i) > less) {
				this.variables.setVarPlus(i, (-1)*less);
				local.addToVariable(i, less);
			}
		}
	}
	
	/**
	 * A stimulus which aims production of fruits. 
	 * @param local (WorldCase)
	 */
	private void exportFruits(WorldCase local) {
		for (int i = 20 ; i < 31 ; i++) {
			Variables forProduct = new Variables();
			if (this.variables.getVariable(i) > 5) {
				forProduct.setVariable(i, this.variables.getVariable(i));
				this.variables.setVariable(i, 0);
			}
			Fruit product = new Fruit(forProduct);
			local.addAgent(product);
		}
	}
}
