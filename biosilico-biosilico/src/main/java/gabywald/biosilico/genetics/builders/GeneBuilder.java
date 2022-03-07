package gabywald.biosilico.genetics.builders;

import java.util.HashMap;
import java.util.Map;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.interfaces.IBuilder;

/**
 * 
 * @author Gabriel Chandesris (2020)
 * @param <T>
 */
public abstract class GeneBuilder<T extends Gene> implements IBuilder<T> {
	
	protected Map<GeneBuilderEnum, Pair<GeneAttemptedType, String> > map = new HashMap<GeneBuilderEnum, Pair<GeneAttemptedType, String> >();
	
	enum GeneAttemptedType {
		BOOLEAN, INTEGER, String;
	}
	
	public enum GeneBuilderEnum {
		NAME, // String
		MUTATE, DUPLICATE, DELETE, ACTIV, // Boolean
		AGE_MIN, AGE_MAX, SEX, MUTATION_RATE, // Integer
		// ***** BiochemicalReaction
		ACHEM, ACOEF, 
		BCHEM, BCOEF, 
		CCHEM, CCOEF, 
		DCHEM, DCOEF, 
		KMVM, 
		// ***** InitialConcentration
		VARIABLE, VALUE, 
		// ***** EmitterReaction
		THRESHOLD, IOPUT, // VARIABLE
		POSX, POSY, 
		RECEPTOR, INTERNAL, 
		// ***** Instinct
		INPUTPOSX, INPUTPOSY, OUTPUTPOSX, OUTPUTPOSY, 
		WEIGHT, CHECK, POSITIVE, // VARIABLE, THRESHOLD, 
		// ***** StimulusDecision
		PERCEPTION, OBJECT, INDICATOR, // THRESHOLD, 
		ATTRIBUTE, // VARIABLE, VALUE, 
		SCRIPT, 
		// ***** BrainGene
		HEIGHT, WIDTH, DEPTH, MORE, 
		// ***** BrainLobeGene
		REST, THRE, DESC, DENDRITICMIN, DENDRITICMAX, 
		PROX, REPR, REPY, WTA, // HEIGHT, WIDTH, POSX, POSY, 
		REPLACE
		;
	}
	
	protected GeneBuilder() {
		this.map.put(GeneBuilderEnum.NAME, 			Pair.of(GeneAttemptedType.String,  ""));
		this.map.put(GeneBuilderEnum.MUTATE, 		Pair.of(GeneAttemptedType.BOOLEAN, "false"));
		this.map.put(GeneBuilderEnum.DUPLICATE, 	Pair.of(GeneAttemptedType.BOOLEAN, "false"));
		this.map.put(GeneBuilderEnum.DELETE, 		Pair.of(GeneAttemptedType.BOOLEAN, "false"));
		this.map.put(GeneBuilderEnum.ACTIV, 		Pair.of(GeneAttemptedType.BOOLEAN, "false"));
		this.map.put(GeneBuilderEnum.AGE_MIN, 		Pair.of(GeneAttemptedType.INTEGER, "0"));
		this.map.put(GeneBuilderEnum.AGE_MAX, 		Pair.of(GeneAttemptedType.INTEGER, "0"));
		this.map.put(GeneBuilderEnum.SEX, 			Pair.of(GeneAttemptedType.INTEGER, "0"));
		this.map.put(GeneBuilderEnum.MUTATION_RATE, Pair.of(GeneAttemptedType.INTEGER, "0"));
	}
	
	// ***** 
	
	protected String setString(GeneBuilderEnum gbe, String val) {
		return this.map.put(gbe, Pair.of(GeneAttemptedType.String, val)).second.toString();
	}
	
	protected boolean setBoolean(GeneBuilderEnum gbe, boolean val) {
		return this.map.put(gbe, Pair.of(GeneAttemptedType.BOOLEAN, val?"true":"false")).second.equals(Boolean.TRUE.toString());
	}
	
	protected int setInteger(GeneBuilderEnum gbe, int val) {
		return Integer.parseInt(this.map.put(gbe, Pair.of(GeneAttemptedType.INTEGER, val + "")).second.toString());
	}
	
	// ***** 
	
	public String setName(String name) {
		return this.setString(GeneBuilderEnum.NAME, name);
	}
	
	public boolean setMutate(boolean isMutable) {
		return this.setBoolean(GeneBuilderEnum.MUTATE, isMutable);
	}
	
	public boolean setDuplicate(boolean isDuplicate) {
		return this.setBoolean(GeneBuilderEnum.DUPLICATE, isDuplicate);
	}
	
	public boolean setDelete(boolean isDelete) {
		return this.setBoolean(GeneBuilderEnum.DELETE, isDelete);
	}
	
	public boolean setActiv(boolean isActiv) {
		return this.setBoolean(GeneBuilderEnum.ACTIV, isActiv);
	}
	
	public int setAgeMin(int ageMin) {
		return this.setInteger(GeneBuilderEnum.AGE_MIN, ageMin);
	}
	
	public int setAgeMax(int ageMax) {
		return this.setInteger(GeneBuilderEnum.AGE_MAX, ageMax);
	}
	
	public int setSex(int sex) {
		return this.setInteger(GeneBuilderEnum.SEX, sex);
	}
	
	public int setMutation(int mutation) {
		return this.setInteger(GeneBuilderEnum.MUTATION_RATE, mutation);
	}
	
	public GeneBuilder<T> name(String name) {
		this.setName(name);
		return this;
	}
	
	public GeneBuilder<T> mutate(boolean mutable) {
		this.setMutate(mutable);
		return this;
	}
	
	public GeneBuilder<T> duplicate(boolean duplicate) {
		this.setDuplicate(duplicate);
		return this;
	}
	
	public GeneBuilder<T> delete(boolean delete) {
		this.setDelete(delete);
		return this;
	}
	
	public GeneBuilder<T> activ(boolean activ) {
		this.setActiv(activ);
		return this;
	}
	
	public GeneBuilder<T> agemin(int agemin) {
		this.setAgeMin(agemin);
		return this;
	}
	
	public GeneBuilder<T> agemax(int agemax) {
		this.setAgeMax(agemax);
		return this;
	}
	
	public GeneBuilder<T> sex(int sex) {
		this.setSex(sex);
		return this;
	}
	
	public GeneBuilder<T> mutation(int mutation) {
		this.setMutation(mutation);
		return this;
	}
	
	@Override
	public abstract T build();
	
}
