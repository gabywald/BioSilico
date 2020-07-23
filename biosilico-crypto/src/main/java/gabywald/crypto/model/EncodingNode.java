package gabywald.crypto.model;

import gabywald.global.data.Utils;

/**
 * 
 * @author Gabriel Chandesris (2012, 2020)
 */
public class EncodingNode {
	/** Level of the node into the tree. */
	private int level;
	/** Number of the node at this level. */
	private int numbe;
	/** Number of bases used for this tree. */
	private int bases;
	/** Father of current node in the tree. */
	private EncodingNode father;
	/** Childs of current node in the tree. */
	private EncodingNode[] childs;
	/** Character of the current node. */
	private char character;
	/** Value of the current node. */
	private String value;
	/** Indicate if special codon / n-uplet. */
	private boolean start, stop;
	
	/**
	 * Main constructor of the Node (root tree). 
	 * @param maxLvls (int) number max of levels (start from 1). 
	 * @param c (char[]) characters of base. 
	 * @param val (String[]) Table of values. 
	 */
	public EncodingNode(int maxLvls, char c[], String val[])  {
		this.init(0, 0, null, c, val);
		this.character	= '\u0000';
		for (int i = 0 ; i < c.length ; i++) {
			EncodingNode child = new EncodingNode(maxLvls, c, val, this.level+1, i, this);
			this.addChild(child);
		}
	}
	
	/**
	 * Constructor to build sub-nodes of the tree
	 * @param maxLvls (int) number max of levels (start from 1). 
	 * @param c (char[]) characters of base. 
	 * @param val (String[]) table of values. 
	 * @param lvl (int) level of the node into the tree. 
	 * @param num (int) number of the node. 
	 * @param father (EncodingNodeNewer) father node. 
	 * @see EncodingNode#EncodingNodeNewer(char, String[], int, int, EncodingNode)
	 */
	private EncodingNode(int maxLvls, char c[], String val[], 
						 int lvl, int num, 
						 EncodingNode father)  {
		this.init(lvl, num, father, c, val);
		
		for (int i = 0 ; i < c.length ; i++) {
			if (this.level < maxLvls-1) { /** Because refer to 0. */
				EncodingNode child = new EncodingNode(maxLvls, c, val, this.level+1, i, this);
				this.addChild(child);
			} else {
				EncodingNode child = new EncodingNode(c, val, this.level+1, i, this);
				this.addChild(child);
			}
		}
	}
	
	/**
	 * Constructor for leaves of the tree. 
	 * @param c (char[]) characters of base. 
	 * @param val (String[]) table of values. 
	 * @param lvl (int) level of the node into the tree. 
	 * @param num (int) number of the node. 
	 * @param fat (EncodingNodeNewer) father node. 
	 */
	private EncodingNode(char c[], String val[], int lvl, int num, EncodingNode fat) 
		{ this.init(lvl, num, fat, c, val); }
	
	/**
	 * Constructors Helper. 
	 * @param lvl (int) level of the node into the tree. 
	 * @param num (int) number of the node. 
	 * @param father (EncodingNodeNewer) father node. 
	 * @param c (char)current character for this node. 
	 * @param val (String[])  table of values. 
	 */
	private void init(int lvl, int num, 
					  EncodingNode father, 
					  char c[], String val[]) {
		this.level		= lvl;
		this.numbe		= num;
		this.bases		= c.length;
		this.father		= father;
		this.character	= c[this.numbe];
		/** System.out.println(Utils.multiple("\t", this.level)
				+this.level+":"+this.bases+":"+this.numbe+" -> "+this.getNumber()
				+((this.level < 3)?" ("+this.getNumber()*this.bases+")":"") ); */
		this.value		= val[this.getNumber()];
		this.childs		= new EncodingNode[0];
		this.start		= false;
		this.stop		= false;
	}
	
	private void addChild(EncodingNode child) {
		EncodingNode[] nextChilds = new EncodingNode[this.childs.length+1];
		for (int i = 0 ; i < this.childs.length ; i++) 
			{ nextChilds[i] = this.childs[i]; }
		nextChilds[this.childs.length] = child;
		this.childs = nextChilds;
	}

	public boolean isStart()			{ return this.start; }
	public boolean isStop()				{ return this.stop; }
	
	public void setStart(boolean b)		{ 
		this.start = b;
		if (b) { this.setStop(!b); }
	}
	public void setStop(boolean b)		{ 
		this.stop = b;
		if (b) { this.setStart(!b); }
	}
	
	public int getLevel()				{ return this.level; }
	public EncodingNode[] getChilds()	{ return this.childs; }
	
	public String[] getValuesAA()			{
		EncodingNode[] leaves	= this.getLeaves();
		String[] currentVales	= new String[leaves.length];
		for (int  i = 0 ; i < leaves.length ; i++) 
			{ currentVales[i] = leaves[i].getCurrentValue(); }
		return currentVales;
	}
	
	public String getConcatenedAA() {
		EncodingNode[] leaves	= this.getLeaves();
		String[] currentVales	= new String[leaves.length];
		boolean hasAlwaysOne	= true;
		for (int  i = 0 ; i < leaves.length ; i++) {
			currentVales[i] = leaves[i].getCurrentValue();
			if (currentVales[i].length() > 1) 
				{ hasAlwaysOne = false; }
		}
		
		String aminoAcids	= new String("");
		for (int i = 0 ; i < leaves.length ; i++) 
			{ aminoAcids += currentVales[i]
					+((!hasAlwaysOne)?GeneticCode.SEPARATOR:""); }
		/** To avoid a useless separator at end. */
		aminoAcids = aminoAcids.substring(0, aminoAcids.length()-GeneticCode.SEPARATOR.length());
		
		return aminoAcids;
	}
	
	public String[] getCodons() {
		EncodingNode[] leaves	= this.getLeaves();
		String[] currentCodes	= new String[leaves.length];
		for (int  i = 0 ; i < leaves.length ; i++) 
			{ currentCodes[i] = leaves[i].getCurrentCode(); }
		return currentCodes;
	}
	
	public EncodingNode getChildWith(char c) {
		for (int i = 0 ; i < this.childs.length ; i++) 
			{ if (this.childs[i].character == c) 
				{ return this.childs[i]; } }
		return null;
	}
	
	public String getCurrentCode() {
		if ( (this.father == null) || (this.character == '\u0000') ) 
			{ return ""; }
		else { return this.father.getCurrentCode()+this.getCharacter(); }
	}
	
	public String getCurrentValue() {
		if (this.value == null) { return ""; }
		else { return this.value; }
	}
	
	public String getCharacter() {
		if ( (this.father == null) || (this.character == '\u0000') )
			{ return ""; } else { return this.character+""; }
	}
	
	public EncodingNode[] getLeaves() {
		EncodingNode[] result = new EncodingNode[0];
		for (int i = 0 ; i < this.childs.length ; i++) {
			if (this.childs[i].getChilds().length > 0) {
				/** Not a Leave, so get iteratively. */
				EncodingNode[] moreResults = this.childs[i].getLeaves();
				for (int j = 0 ; j < moreResults.length ; j++) 
					{ result = EncodingNode.addNodeInTable(result, moreResults[j]); }
			} else { result = EncodingNode.addNodeInTable(result, this.childs[i]); }
		}
		return result;
	}

	public String getCode(String value) {
		EncodingNode[] leaves = this.getLeaves();
		for (int i = 0 ; i < leaves.length ; i++) {
			if (leaves[i].value.equals(value))
				{ return leaves[i].getCurrentCode(); }
		}
		return null;
	}
	
	public String getValue(String code) {
		EncodingNode last = this.getRoot();
		int charIndex = 0;
		while ( (charIndex < code.length()) 
					&& ( last.getChildWith(code.charAt(charIndex)) != null ) ) {
			last = last.getChildWith(code.charAt(charIndex));
			charIndex++;
		}
		return last.value;
	}
	
	public EncodingNode getNode(String code) {
		EncodingNode last = this.getRoot();
		int charIndex = 0;
		while ( (charIndex < code.length()) 
					&& ( last.getChildWith(code.charAt(charIndex)) != null ) ) {
			last = last.getChildWith(code.charAt(charIndex));
			charIndex++;
		}
		return last;
	}
	
	public EncodingNode getRoot() {
		EncodingNode root = this;
		while ( (root.father != null) 
				|| (root.character != '\u0000') )
			{ root = root.father; }
		return root;
	}
	
	public int getNumber() {
		int number			= this.numbe;
		EncodingNode tmp	= this.father;
		while ( (tmp != null) && ( (this.level - tmp.level) < 2) ) {
			number	+= tmp.getNumber() * tmp.bases;
			tmp		= tmp.father;
		}
		// System.out.println(Utils.multiple("\t", this.level)+" => "+number);
		return number;
	}
	
	public String toString() {
		String result = "'"+this.level+"'\t'"+this.numbe+"'\t'"
					+((this.character == '\u0000')?"null":this.character)
					+"'\t'"+this.getNumber()+"' ("
					+this.getCurrentCode()+")"+" => '"
					+((this.value == null)?"":this.value)+"'"
					/** XXX start / stop codon... */
					+((this.start)?"\t'START'":"")+((this.stop)?"\t'STOP'":"");
		return result;
	}
	
	public boolean equals(EncodingNode toCompare) {
		if (this.level != toCompare.level)					{ return false; }
		if (this.numbe != toCompare.numbe)					{ return false; }
		if (this.childs.length != toCompare.childs.length)	{ return false; }
		if (this.character != toCompare.character)			{ return false; }
		if (!this.value.equals(toCompare.value))			{ return false; }
		return true;
	}
	
	public static String treeView(EncodingNode root) {
		StringBuilder toResult = new StringBuilder();
		toResult.append( Utils.repeat("\t", root.getLevel()))
				.append(root.toString()).append("\n" );
		EncodingNode[] rootChilds = root.getChilds();
		for (int i = 0 ; i < rootChilds.length ; i++) 
			{ toResult.append(EncodingNode.treeView(rootChilds[i])); }
		return toResult.toString();
	}
	
	private static EncodingNode[] addNodeInTable(EncodingNode[] table, EncodingNode toAdd) {
		if (toAdd == null) { return table; }
		EncodingNode[] nextTable = new EncodingNode[table.length+1];
		for (int i = 0 ; i < table.length ; i++) 
			{ nextTable[i] = table[i]; }
		nextTable[table.length] = toAdd;
		return nextTable;
	}
}
