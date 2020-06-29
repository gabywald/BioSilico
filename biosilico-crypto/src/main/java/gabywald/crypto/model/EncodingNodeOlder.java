package gabywald.crypto.model;

/**
 * 
 * @author Gabriel Chandesris (2011)
 * @deprecated Use {@link EncodingNode} instead !!
 */
public class EncodingNodeOlder {
	/** Level of the node into the tree. */
	private int level;
	/** Number of the node at this level. */
	private int numbe;
	/** Father of current node in the tree. */
	private EncodingNodeOlder father;
	/** Childs of current node in the tree. */
	private EncodingNodeOlder[] childs;
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
	public EncodingNodeOlder(int maxLvls, char c[], String val[])  {
		this.init(0, 0, null, '\u0000', val);
		for (int i = 0 ; i < c.length ; i++) {
			EncodingNodeOlder child = new EncodingNodeOlder(maxLvls, c, val, i, this.level+1, i, this);
			this.addChild(child);
		}
	}
	
	/**
	 * Constructor to build sub-nodes of the tree
	 * @param maxLvls (int) number max of levels (start from 1). 
	 * @param c (char[]) characters of base. 
	 * @param val (String[]) table of values. 
	 * @param pos (int) position at this level, according to ancestors. 
	 * @param lvl (int) level of the node into the tree. 
	 * @param num (int) number of the node. 
	 * @param fat (EncodingNodeOld) father node. 
	 * @see EncodingNodeOlder#EncodingNodeOld(char, String[], int, int, EncodingNodeOld)
	 */
	private EncodingNodeOlder(int maxLvls, char c[], String val[], 
						 int pos, int lvl, int num, 
						 EncodingNodeOlder fat)  {
		this.init(lvl, num, fat, c[pos], val);
		
		for (int i = 0 ; i < c.length ; i++) {
			if (this.level < maxLvls-1) { /** Because refer to 0. */
				EncodingNodeOlder child = new EncodingNodeOlder(maxLvls, c, val, i, this.level+1, i, this);
				this.addChild(child);
			} else {
				EncodingNodeOlder child = new EncodingNodeOlder(c[i], val, this.level+1, i, this);
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
	 * @param fat (EncodingNodeOld) father node. 
	 */
	private EncodingNodeOlder(char c, String val[], int lvl, int num, EncodingNodeOlder fat) 
		{ this.init(lvl, num, fat, c, val); }
	
	/**
	 * Constructors Helper. 
	 * @param lvl (int) level of the node into the tree. 
	 * @param num (int) number of the node. 
	 * @param fat (EncodingNodeOld) father node. 
	 * @param c (char)current character for this node. 
	 * @param val (String[])  table of values. 
	 */
	private void init(int lvl, int num, 
					  EncodingNodeOlder fat, 
					  char c, String val[]) {
		this.level		= lvl;
		this.numbe		= num;
		this.father		= fat;
		this.character	= c;
		this.value		= val[this.getNumber()];
		this.childs		= new EncodingNodeOlder[0];
		this.start		= false;
		this.stop		= false;
	}
	
	private void addChild(EncodingNodeOlder child) {
		EncodingNodeOlder[] nextChilds = new EncodingNodeOlder[this.childs.length+1];
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
	public EncodingNodeOlder[] getChilds()	{ return this.childs; }
	
	public String[] getValuesAA()			{
		EncodingNodeOlder[] leaves	= this.getLeaves();
		String[] currentVales	= new String[leaves.length];
		for (int  i = 0 ; i < leaves.length ; i++) 
			{ currentVales[i] = leaves[i].getCurrentValue(); }
		return currentVales;
	}
	
	public String getConcatenedAA() {
		EncodingNodeOlder[] leaves	= this.getLeaves();
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
		EncodingNodeOlder[] leaves	= this.getLeaves();
		String[] currentCodes	= new String[leaves.length];
		for (int  i = 0 ; i < leaves.length ; i++) 
			{ currentCodes[i] = leaves[i].getCurrentCode(); }
		return currentCodes;
	}
	
	public EncodingNodeOlder getChildWith(char c) {
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
	
	public EncodingNodeOlder[] getLeaves() {
		EncodingNodeOlder[] result = new EncodingNodeOlder[0];
		for (int i = 0 ; i < this.childs.length ; i++) {
			if (this.childs[i].getChilds().length > 0) {
				/** Not a Leave, so get iteratively. */
				EncodingNodeOlder[] moreResults = this.childs[i].getLeaves();
				for (int j = 0 ; j < moreResults.length ; j++) 
					{ result = EncodingNodeOlder.addNodeInTable(result, moreResults[j]); }
			} else { result = EncodingNodeOlder.addNodeInTable(result, this.childs[i]); }
		}
		return result;
	}

	public String getCode(String value) {
		EncodingNodeOlder[] leaves = this.getLeaves();
		for (int i = 0 ; i < leaves.length ; i++) {
			if (leaves[i].value.equals(value))
				{ return leaves[i].getCurrentCode(); }
		}
		return null;
	}
	
	public String getValue(String code) {
		EncodingNodeOlder last = this.getRoot();
		int charIndex = 0;
		while ( (charIndex < code.length()) 
					&& ( last.getChildWith(code.charAt(charIndex)) != null ) ) {
			last = last.getChildWith(code.charAt(charIndex));
			charIndex++;
		}
		return last.value;
	}
	
	public EncodingNodeOlder getNode(String code) {
		EncodingNodeOlder last = this.getRoot();
		int charIndex = 0;
		while ( (charIndex < code.length()) 
					&& ( last.getChildWith(code.charAt(charIndex)) != null ) ) {
			last = last.getChildWith(code.charAt(charIndex));
			charIndex++;
		}
		return last;
	}
	
	public EncodingNodeOlder getRoot() {
		EncodingNodeOlder root = this;
		while ( (root.father != null) || (root.character != '\u0000') )
			{ root = root.father; }
		return root;
	}
	
	public int getNumber() {
		int number			= this.numbe;
		EncodingNodeOlder tmp	= this.father;
		while (tmp != null) {
			number	+= tmp.numbe*((int)Math.pow(2, (this.level-tmp.level)*2));
			tmp		= tmp.father;
		}
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
	
	public boolean equals(EncodingNodeOlder toCompare) {
		if (this.level != toCompare.level)					{ return false; }
		if (this.numbe != toCompare.numbe)					{ return false; }
		if (this.childs.length != toCompare.childs.length)	{ return false; }
		if (this.character != toCompare.character)			{ return false; }
		if (!this.value.equals(toCompare.value))			{ return false; }
		return true;
	}
	
	public static String treeView(EncodingNodeOlder root) {
		String result = EncodingNodeOlder.multiple("\t", root.getLevel()) 
							+ root.toString()+"\n";
		EncodingNodeOlder[] rootChilds = root.getChilds();
		for (int i = 0 ; i < rootChilds.length ; i++) 
			{ result += EncodingNodeOlder.treeView(rootChilds[i]); }
		return result;
	}
	
	/**
	 * Make an append of txt 'multi' times. 
	 * @param txt (String)
	 * @param multi (String)
	 * @return (String) 'txt' appended (multi) times. 
	 */
	public static String multiple(String txt, int multi) {
		String result = "";
		for (int i = 0 ; i < multi ; i++) { result += txt; }
		return result;
	}
	
	private static EncodingNodeOlder[] addNodeInTable(EncodingNodeOlder[] table, EncodingNodeOlder toAdd) {
		if (toAdd == null) { return table; }
		EncodingNodeOlder[] nextTable = new EncodingNodeOlder[table.length+1];
		for (int i = 0 ; i < table.length ; i++) 
			{ nextTable[i] = table[i]; }
		nextTable[table.length] = toAdd;
		return nextTable;
	}
}
