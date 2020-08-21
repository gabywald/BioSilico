package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gabywald.biosilico.genetics.builders.Pair;

/**
 * Builder for Neuron Model class. 
 * @author Gabriel Chandesris (2020)
 */
public class NeuronBuilder {
	
	protected Map<NeuronBuilderEnum, Pair<NeuronAttemptedType, String> > map = new HashMap<NeuronBuilderEnum, Pair<NeuronAttemptedType, String> >();

	enum NeuronAttemptedType {
		BOOLEAN, INTEGER, String;
	}
	
	public enum NeuronBuilderEnum {
		REST, THRESHOLD, DESC, ACTIVITY, DENDRITICMIN, DENDRITICMAX, 
		INDEX, PROX, REPR, REPY, WTA, POSX, POSY;
	}
	
	public NeuronBuilder() {
		this.init();
	}
	
	public void init() { 
		this.map.put(NeuronBuilderEnum.REST, 			Pair.of(NeuronAttemptedType.INTEGER, "0"));
		this.map.put(NeuronBuilderEnum.THRESHOLD, 		Pair.of(NeuronAttemptedType.INTEGER, "0"));
		this.map.put(NeuronBuilderEnum.DESC, 			Pair.of(NeuronAttemptedType.INTEGER, "0"));
		this.map.put(NeuronBuilderEnum.ACTIVITY, 		Pair.of(NeuronAttemptedType.INTEGER, "0"));
		this.map.put(NeuronBuilderEnum.DENDRITICMIN, 	Pair.of(NeuronAttemptedType.INTEGER, "0"));
		
		this.map.put(NeuronBuilderEnum.DENDRITICMAX, 	Pair.of(NeuronAttemptedType.INTEGER, "0"));
		this.map.put(NeuronBuilderEnum.INDEX, 			Pair.of(NeuronAttemptedType.INTEGER, "0"));
		this.map.put(NeuronBuilderEnum.PROX, 			Pair.of(NeuronAttemptedType.INTEGER, "0"));
		this.map.put(NeuronBuilderEnum.REPR, 			Pair.of(NeuronAttemptedType.BOOLEAN, "false"));
		this.map.put(NeuronBuilderEnum.REPY, 			Pair.of(NeuronAttemptedType.INTEGER, "0"));
		
		this.map.put(NeuronBuilderEnum.WTA, 			Pair.of(NeuronAttemptedType.BOOLEAN, "false"));
		this.map.put(NeuronBuilderEnum.POSX, 			Pair.of(NeuronAttemptedType.INTEGER, "0"));
		this.map.put(NeuronBuilderEnum.POSY, 			Pair.of(NeuronAttemptedType.INTEGER, "0"));

	}
	
	// ***** 
	
	protected String setString(NeuronBuilderEnum nbe, String val) {
		return this.map.put(nbe, Pair.of(NeuronAttemptedType.String, val)).second.toString();
	}
	
	protected boolean setBoolean(NeuronBuilderEnum nbe, boolean val) {
		return this.map.put(nbe, Pair.of(NeuronAttemptedType.BOOLEAN, val?"true":"false")).second.equals(Boolean.TRUE.toString());
	}
	
	protected int setInteger(NeuronBuilderEnum nbe, int val) {
		return Integer.parseInt(this.map.put(nbe, Pair.of(NeuronAttemptedType.INTEGER, val + "")).second.toString());
	}
	
	// ***** 
	
	public int setRest(int rest) {
		return this.setInteger(NeuronBuilderEnum.REST, rest);
	}
	
	public int setThreshold(int threshold) {
		return this.setInteger(NeuronBuilderEnum.THRESHOLD, threshold);
	}
	
	public int setDescent(int desc) {
		return this.setInteger(NeuronBuilderEnum.DESC, desc);
	}
	
	public int setActivity(int activity) {
		return this.setInteger(NeuronBuilderEnum.ACTIVITY, activity);
	}
	
	public int setDmin(int dmin) {
		return this.setInteger(NeuronBuilderEnum.DENDRITICMIN, dmin);
	}
	
	public int setDmax(int dmax) {
		return this.setInteger(NeuronBuilderEnum.DENDRITICMAX, dmax);
	}
	
	public int setIndex(int index) {
		return this.setInteger(NeuronBuilderEnum.INDEX, index);
	}
	
	public int setProximity(int prox) {
		return this.setInteger(NeuronBuilderEnum.PROX, prox);
	}
	
	public boolean setReproduction(boolean isRepr) {
		return this.setBoolean(NeuronBuilderEnum.REPR, isRepr);
	}
	
	public int setReproductibility(int repy) {
		return this.setInteger(NeuronBuilderEnum.REPY, repy);
	}
	
	public boolean setWTA(boolean isWTA) {
		return this.setBoolean(NeuronBuilderEnum.WTA, isWTA);
	}
	
	public int setPosX(int posx) {
		return this.setInteger(NeuronBuilderEnum.POSX, posx);
	}
	
	public int setPosY(int posy) {
		return this.setInteger(NeuronBuilderEnum.POSY, posy);
	}
	
	public NeuronBuilder rest(int rest) {
		this.setRest(rest);
		return this;
	}
	
	public NeuronBuilder threshold(int threshold) {
		this.setThreshold(threshold);
		return this;
	}
	
	public NeuronBuilder desc(int desc) {
		this.setDescent(desc);
		return this;
	}
	
	public NeuronBuilder activation(int activation) {
		this.setActivity(activation);
		return this;
	}
	
	public NeuronBuilder dmin(int dmin) {
		this.setDmin(dmin);
		return this;
	}
	
	public NeuronBuilder dmax(int dmax) {
		this.setDmax(dmax);
		return this;
	}
	
	public NeuronBuilder index(int index) {
		this.setIndex(index);
		return this;
	}
	
	public NeuronBuilder prox(int prox) {
		this.setProximity(prox);
		return this;
	}
	
	public NeuronBuilder repr(boolean repr) {
		this.setReproduction(repr);
		return this;
	}
	
	public NeuronBuilder repy(int repy) {
		this.setReproductibility(repy);
		return this;
	}
	
	public NeuronBuilder wta(boolean wta) {
		this.setWTA(wta);
		return this;
	}
	
	public NeuronBuilder posx(int posx) {
		this.setPosX(posx);
		return this;
	}
	
	public NeuronBuilder posY(int posY) {
		this.setPosY(posY);
		return this;
	}
	
	public Neuron build() {
		return new Neuron(	
				Integer.parseInt(this.map.get(NeuronBuilderEnum.REST).second), 
				Integer.parseInt(this.map.get(NeuronBuilderEnum.THRESHOLD).second), 
				Integer.parseInt(this.map.get(NeuronBuilderEnum.ACTIVITY).second), 
				Integer.parseInt(this.map.get(NeuronBuilderEnum.DESC).second), 
				Integer.parseInt(this.map.get(NeuronBuilderEnum.DENDRITICMIN).second), 
				Integer.parseInt(this.map.get(NeuronBuilderEnum.DENDRITICMAX).second), 
				Integer.parseInt(this.map.get(NeuronBuilderEnum.PROX).second), 
				Boolean.parseBoolean(this.map.get(NeuronBuilderEnum.REPR).second), 
				Integer.parseInt(this.map.get(NeuronBuilderEnum.REPY).second), 
				Boolean.parseBoolean(this.map.get(NeuronBuilderEnum.WTA).second), 
				new Position(Integer.parseInt(this.map.get(NeuronBuilderEnum.POSX).second), Integer.parseInt(this.map.get(NeuronBuilderEnum.POSY).second) ), 
				new ArrayList<Neuron>(), new ArrayList<Integer>() );
	}
	
}
