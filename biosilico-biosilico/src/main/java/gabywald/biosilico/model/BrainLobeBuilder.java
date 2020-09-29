package gabywald.biosilico.model;

import java.util.HashMap;
import java.util.Map;

import gabywald.biosilico.genetics.builders.Pair;
import gabywald.biosilico.model.environment.PositionBuilder;

/**
 * Builder for BrainLobe Model class. 
 * @author Gabriel Chandesris (2020)
 * @Deprecated Do not use. 
 */
public class BrainLobeBuilder {
	
	protected Map<BrainLobeBuilderEnum, Pair<BrainLobeAttemptedType, String> > map = new HashMap<BrainLobeBuilderEnum, Pair<BrainLobeAttemptedType, String> >();

	enum BrainLobeAttemptedType {
		BOOLEAN, INTEGER, String;
	}
	
	public enum BrainLobeBuilderEnum {
		REST, THRESHOLD, DESC, ACTIVATION, DENDRITICMIN, DENDRITICMAX, 
		INDEX, PROX, REPR, REPY, WTA, POSX, POSY;
	}
	
	public BrainLobeBuilder() {
		this.init();
	}
	
	public void init() { 
		this.map.put(BrainLobeBuilderEnum.REST, 			Pair.of(BrainLobeAttemptedType.INTEGER, "0"));
		this.map.put(BrainLobeBuilderEnum.THRESHOLD, 		Pair.of(BrainLobeAttemptedType.INTEGER, "0"));
		this.map.put(BrainLobeBuilderEnum.DESC, 			Pair.of(BrainLobeAttemptedType.INTEGER, "0"));
		this.map.put(BrainLobeBuilderEnum.DENDRITICMIN, 	Pair.of(BrainLobeAttemptedType.INTEGER, "0"));
		this.map.put(BrainLobeBuilderEnum.DENDRITICMAX, 	Pair.of(BrainLobeAttemptedType.INTEGER, "0"));
		this.map.put(BrainLobeBuilderEnum.PROX, 			Pair.of(BrainLobeAttemptedType.INTEGER, "0"));
		this.map.put(BrainLobeBuilderEnum.REPR, 			Pair.of(BrainLobeAttemptedType.BOOLEAN, "false"));
		this.map.put(BrainLobeBuilderEnum.REPY, 			Pair.of(BrainLobeAttemptedType.INTEGER, "0"));
		this.map.put(BrainLobeBuilderEnum.WTA, 				Pair.of(BrainLobeAttemptedType.BOOLEAN, "false"));
		this.map.put(BrainLobeBuilderEnum.POSX, 			Pair.of(BrainLobeAttemptedType.INTEGER, "0"));
		this.map.put(BrainLobeBuilderEnum.POSY, 			Pair.of(BrainLobeAttemptedType.INTEGER, "0"));

	}
	
	// ***** 
	
	protected String setString(BrainLobeBuilderEnum nbe, String val) {
		return this.map.put(nbe, Pair.of(BrainLobeAttemptedType.String, val)).second.toString();
	}
	
	protected boolean setBoolean(BrainLobeBuilderEnum nbe, boolean val) {
		return this.map.put(nbe, Pair.of(BrainLobeAttemptedType.BOOLEAN, val?"true":"false")).second.equals(Boolean.TRUE.toString());
	}
	
	protected int setInteger(BrainLobeBuilderEnum nbe, int val) {
		return Integer.parseInt(this.map.put(nbe, Pair.of(BrainLobeAttemptedType.INTEGER, val + "")).second.toString());
	}
	
	// ***** 
	
	public int setRest(int rest) {
		return this.setInteger(BrainLobeBuilderEnum.REST, rest);
	}
	
	public int setThreshold(int threshold) {
		return this.setInteger(BrainLobeBuilderEnum.THRESHOLD, threshold);
	}
	
	public int setDescent(int desc) {
		return this.setInteger(BrainLobeBuilderEnum.DESC, desc);
	}
	
	public int setActivation(int activation) {
		return this.setInteger(BrainLobeBuilderEnum.ACTIVATION, activation);
	}
	
	public int setDmin(int dmin) {
		return this.setInteger(BrainLobeBuilderEnum.DENDRITICMIN, dmin);
	}
	
	public int setDmax(int dmax) {
		return this.setInteger(BrainLobeBuilderEnum.DENDRITICMAX, dmax);
	}
	
	public int setIndex(int index) {
		return this.setInteger(BrainLobeBuilderEnum.INDEX, index);
	}
	
	public int setProximity(int prox) {
		return this.setInteger(BrainLobeBuilderEnum.PROX, prox);
	}
	
	public boolean setReproduction(boolean isRepr) {
		return this.setBoolean(BrainLobeBuilderEnum.REPR, isRepr);
	}
	
	public int setReproductibility(int repy) {
		return this.setInteger(BrainLobeBuilderEnum.REPY, repy);
	}
	
	public boolean setWTA(boolean isWTA) {
		return this.setBoolean(BrainLobeBuilderEnum.WTA, isWTA);
	}
	
	public int setPosX(int posx) {
		return this.setInteger(BrainLobeBuilderEnum.POSX, posx);
	}
	
	public int setPosY(int posy) {
		return this.setInteger(BrainLobeBuilderEnum.POSY, posy);
	}
	
	public BrainLobeBuilder rest(int rest) {
		this.setRest(rest);
		return this;
	}
	
	public BrainLobeBuilder threshold(int threshold) {
		this.setThreshold(threshold);
		return this;
	}
	
	public BrainLobeBuilder desc(int desc) {
		this.setDescent(desc);
		return this;
	}
	
	public BrainLobeBuilder activation(int activation) {
		this.setActivation(activation);
		return this;
	}
	
	public BrainLobeBuilder dmin(int dmin) {
		this.setDmin(dmin);
		return this;
	}
	
	public BrainLobeBuilder dmax(int dmax) {
		this.setDmax(dmax);
		return this;
	}
	
	public BrainLobeBuilder index(int index) {
		this.setIndex(index);
		return this;
	}
	
	public BrainLobeBuilder prox(int prox) {
		this.setProximity(prox);
		return this;
	}
	
	public BrainLobeBuilder repr(boolean repr) {
		this.setReproduction(repr);
		return this;
	}
	
	public BrainLobeBuilder repy(int repy) {
		this.setReproductibility(repy);
		return this;
	}
	
	public BrainLobeBuilder wta(boolean wta) {
		this.setWTA(wta);
		return this;
	}
	
	public BrainLobeBuilder posx(int posx) {
		this.setPosX(posx);
		return this;
	}
	
	public BrainLobeBuilder posY(int posY) {
		this.setPosY(posY);
		return this;
	}
	
	public BrainLobe build() {
		return new BrainLobe(	
				Integer.parseInt(this.map.get(BrainLobeBuilderEnum.REST).second), 
				Integer.parseInt(this.map.get(BrainLobeBuilderEnum.THRESHOLD).second), 
				Integer.parseInt(this.map.get(BrainLobeBuilderEnum.DESC).second), 
				Integer.parseInt(this.map.get(BrainLobeBuilderEnum.DENDRITICMIN).second), 
				Integer.parseInt(this.map.get(BrainLobeBuilderEnum.DENDRITICMAX).second), 
				Integer.parseInt(this.map.get(BrainLobeBuilderEnum.PROX).second), 
				Boolean.parseBoolean(this.map.get(BrainLobeBuilderEnum.REPR).second), 
				Integer.parseInt(this.map.get(BrainLobeBuilderEnum.REPY).second), 
				Boolean.parseBoolean(this.map.get(BrainLobeBuilderEnum.WTA).second), 
				PositionBuilder.buildPosition(
						Integer.parseInt(this.map.get(BrainLobeBuilderEnum.POSX).second), 
						Integer.parseInt(this.map.get(BrainLobeBuilderEnum.POSY).second) ) );
	}
	
}
