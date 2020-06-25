package gabywald.biosilico.fourmis;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public abstract class Agent implements VariableContent {
	protected Variables variables;
	private boolean alive;
	private boolean movable;
	private boolean eatable;
	
	public Agent() {
		this.variables = new Variables();
		this.alive = false;
		this.movable = true;
		this.eatable = false;
	}
	
	public Agent(boolean alive,boolean movable,boolean eatable) {
		this.variables = new Variables();
		this.alive = alive;
		this.movable = movable;
		this.eatable = eatable;
	}
	
	public Variables getVariables() { return this.variables; }
	public boolean isAlive() { return this.alive; }
	public boolean isMovable() { return this.movable; }
	public boolean isEatable() { return this.eatable; }
	
	/** 
	 * Define what Agent does (if it does something). 
	 * @param local (WorldCase) Where Agent is located. 
	 */
	public abstract void execution(WorldCase local);
	
	/**
	 * Define the movement of the Agent (if it is moving). 
	 * @param local (WorldCase) Where Agent is located. 
	 */
	public abstract void deplace(WorldCase local);
	
	protected void BioCHGene(int Achem,int Acoef,int Bchem,int Bcoef,
							 int Cchem,int Ccoef,int Dchem,int Dcoef,
							 int KM) {
		/** Avoiding the reaction from nothing. */
		if ( (Achem != 0) && (Bchem != 0) ) {
			/** Need to get enough A and B. One can be 'default var'.  */
			boolean reaction = ( 	
					( (Achem == 0) ||
							(this.variables.getVariable(Achem) > Acoef) )
						&& ( (Bchem == 0) ||
							(this.variables.getVariable(Bchem) > Bcoef) )
			);
			int local_cycle = 0;
			while (reaction && (KM > local_cycle) ) {
				this.variables.setVarLess(Achem, Acoef);
				this.variables.setVarLess(Bchem, Bcoef);
				this.variables.setVarLess(Cchem, Ccoef);
				this.variables.setVarLess(Dchem, Dcoef);
				local_cycle++;
				reaction = 
					( (this.variables.getVariable(Achem) > Acoef)
							&& (this.variables.getVariable(Bchem) > Bcoef) );
			}
		}
	}
	
	/**
	 * TODO genetic biochemistry's. 
	 */
	protected abstract void biochemistery();

	/**
	 * TODO genetic stimulus' (decision's action)
	 * @param local
	 */
	protected abstract void stimulus(WorldCase local);
	
	/**
	 * TODO genetic emitterReceptor's
	 * @param local
	 */
	protected abstract void emitterReceptor(WorldCase local);
}
