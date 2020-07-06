package gabywald.creatures.model;

/**
 * 
 * @author Gabriel Chandesris (2013)
 */
public class Creature extends GenomicAgent {
	/**
	 * 
	 * @author Gabriel Chandesris (2013)
	 */
	public enum KindOfCreature {
		NORN(0, 1000, 300, 10), 
		GRENDEL(1, 1000, 300, 10), 
		ETTING(2, 1000, 300, 10), 
		SHEE(3, 1000, 300, 10), 
		PLANT(10, 1000, 100, 2), 
		INERT(20, 1000, 0, 0);
		/** Kind of. */
		private final int type;
		/** Number (default) of Variables. */
		private final int vars;
		/** Number (default) of Genes. */
		private final int gene;
		/** Number (default) of Organs. */
		private final int orgs;
		KindOfCreature(int type, int vars, int gene, int orgs) { 
			this.type = type;this.vars = vars;
			this.gene = gene;this.orgs = orgs;
		}
		
		public int getType() { return this.type; }
		public int getVars() { return this.vars; }
		public int getGene() { return this.gene; }
		public int getOrgs() { return this.orgs; }
	}
	
	private KindOfCreature typeOf;
		
	public Creature(KindOfCreature kindOf) 
		{ super(kindOf.vars, kindOf.gene, kindOf.orgs);this.typeOf = kindOf; }
	
	public KindOfCreature getKind() 
		{ return this.typeOf; }
}
