package gabywald.crypto.model;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class EncodingNodeBuilder {
	
	private int maxLvls		= 3;
	private char[] bases 	= "acgt".toCharArray();
	private String[] values	= "A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z;A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z;1;2;3;4;5;6;7;8;9;0;1;2".split(";");
	
	public EncodingNodeBuilder() {
		;
	}
	
	public EncodingNodeBuilder maxLvls(int maxLvls) {
		this.maxLvls = maxLvls;
		return this;
	}
	
	public EncodingNodeBuilder bases(char[] bases) {
		this.bases = bases;
		return this;
	}
	
	public EncodingNodeBuilder values(String[] values) {
		this.values = values;
		return this;
	}
	
	public EncodingNode build() throws EncodingNodeException {
		
		int nbBases = this.bases.length;
		
		int expElts = (int) Math.pow(nbBases, this.maxLvls);
		
		if (expElts == this.values.length) {
			return new EncodingNode(this.maxLvls, this.bases, this.values);
		} else {
			throw new EncodingNodeException("maxLvls (" + maxLvls + ") nbBases (" + nbBases +") expElts (" + expElts + ") real [" + this.values.length + "]");
		}
	}
}
