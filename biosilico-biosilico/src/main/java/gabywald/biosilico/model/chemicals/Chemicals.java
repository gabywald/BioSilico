package gabywald.biosilico.model.chemicals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import gabywald.biosilico.interfaces.IChemicals;

/**
 * To give a specific table of variables and tools to use it. 
 * @author Gabriel Chandesris (2009-2010, 2020)
 */
public class Chemicals implements IChemicals {
	
	/** The table of variables. */
	private List<Integer> vars;
	
	/** Default constructor (length of 1000, initiated to 0 for each value). */
	Chemicals() { 
		this.vars = IntStream.iterate(0, i -> i++).limit( ChemicalsHelper.CHEMICAL_LENGTH )
						.map( i -> 0 ).boxed().collect(Collectors.toList());
	}
	
	public int length()				{ return this.vars.size(); }
	
	public int getVariable(int i)	{ return this.vars.get(i).intValue(); }
	
	public void setVariable(int i, int value) {
		if ( (i >= 0) && (i < this.vars.size()) )
			{ this.vars.set(i, value); }
		this.regulate(i);
	}
	
	@Override
	public void setVarPlus(int i, int val) {
		if ( (i >= 0) && (i < this.vars.size()) )
			{ this.vars.set(i, this.vars.get(i) + val); }
		this.regulate(i);
	}
	
	@Override
	public void setVarPlusPlus(int i) {
		this.setVarPlus(i, 1);
	}
	
	@Override
	public void setVarLess(int i, int val) {
		this.setVarPlus(i, -val);
	}
	
	@Override
	public void setVarLessLess(int i) {
		this.setVarLess(i, 1);
	}
	
	@Override
	public void incorporate(IChemicals toSum) {
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH )
			.forEach( i -> {
				this.setVarPlus(i, toSum.getVariable(i));
				this.regulate(i);
			});
	}
	
	/**
	 * To "regulate" variables between 0 and 999. 
	 * @param i (int) variable to regulate. 
	 */
	private void regulate(int i) {
		if (this.vars.get(i).intValue() < 0)	{ this.vars.set(i, 0); }
		if (this.vars.get(i).intValue() > 999)	{ this.vars.set(i, 999); }
	}
	
	public String toString() {
		String result = new String();
		for (int i = 0 ; i < this.vars.size() ; i++) {
			if (this.vars.get(i).intValue() != 0) 
				{ result += "\t"+i+"\t"+this.vars.get(i).intValue()+"\n"; }
		}
		return result;
	}
	

	
}
