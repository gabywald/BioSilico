package gabywald.biosilico.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import gabywald.biosilico.exceptions.GeneticTreeNodeException;

/**
 * This class to compute and generate a tree for encoding / decoding with triplet 
 * or quadruplets String constitued with a given 4-char-String alphabet 
 * (64 elements for triplets or 256 elements for quadruplets).<br>
 * Useful for computing genetic code and coding / decoding genetic sequences. <br>
 * Examples (see also getting pre-defined tree) : 
 * <ul>
 * <li>String itemsStandardGeneticCode = "KKNNRRSSIMIITTTTEEDDGGGGVVVVAAAA**YY*WCCLLFFSSSSQQHHRRRRLLLLPPPP";
 * GeneticTreeNode standardGeneticCode = new GeneticTreeNode("aguc",itemsStandardGeneticCode,true);</li>
 * <li>String itemsGattaca = new String("abcdefghijklmnopqrstuvwxyz000111222333444555666777888999::''MM**");<br>
 * GeneticTreeNode testRootGattaca = new GeneticTreeNode("ACTG",itemsGattaca,true);</li>
 * </ul>
 * @author Gabriel Chandesris (2009-2010, 2020)
 * @see GeneticTreeNode#getGeneticCodeStandard()
 * @see GeneticTreeNode#getGeneticCodeGattaca()
 * @see GeneticTreeNode#getGeneticCodePhaseTwo()
 */
public class GeneticTreeNode {
	/** Level of current node in the tree. */
	private int level;
	/** Laterality of node at current level.  */
	private int lateral;
	/** ... */
	private int output;
	/** If tree if made for triplets or quadruplets */
	private boolean triplet;
	/** List of chars for encoding.  */
	private String codeValues;
	/** List of potential results. */
	private String[] endValues;
	/** Son nodes of current node. */
	private List<GeneticTreeNode> nodes;
	/** Father of current node. */
	private GeneticTreeNode father;

	/**
	 * Main constructor build the root of the tree and nodes under it to leaves. 
	 * @param codeValues (String) Set of characters to build the code. 
	 * @param endValues (String) The characters needed to get using the code. 
	 * @param triplet (boolean) Indicate if triplet of quadruplet. 
	 * @throws GeneticTreeNodeException If length of <i>endValues</i> is not equal to (length of codeValues)^[3|4]. 
	 */
	public GeneticTreeNode(String codeValues, String endValues, boolean triplet) 
			throws GeneticTreeNodeException 
		{ this.init(codeValues, GeneticTreeNode.toTable(endValues), triplet); }

	/**
	 * Main constructor build the root of the tree and nodes under it to leaves. 
	 * @param codeValues (String) Set of characters to build the code. 
	 * @param endValues (String[]) The String's to get using the code.  
	 * @param triplet (boolean) Indicate if triplet of quadruplet. 
	 * @throws GeneticTreeNodeException If length of <i>endValues</i> is not equal to (length of codeValues)^[3|4]. 
	 */
	public GeneticTreeNode(String codeValues, String[] endValues, boolean triplet) 
			throws GeneticTreeNodeException 
		{ this.init(codeValues, endValues, triplet); }

	/**
	 * Build nodes in the tree and leaves (not main root). 
	 * @param codeValues (String) Set of characters to build the code. 
	 * @param endValues (String[]) The String's to get using the code. 
	 * @param triplet (boolean) Indicate if triplet of quadruplet. 
	 * @param father (GeneticTreeNode) Father of current node. 
	 * @param lateral (int) Place of node at its level under its father node. 
	 */
	private GeneticTreeNode(String codeValues, String[] endValues, boolean triplet,
							GeneticTreeNode father, int lateral) {
		this.father		= father;
		this.level		= this.father.getLevel()+1;
		this.lateral	= lateral;
		this.output		= this.lateral;
		this.codeValues	= codeValues;
		this.endValues	= endValues;
		this.triplet	= triplet;
		this.nodes		= new ArrayList<GeneticTreeNode>();
		// ***** limit is 3 for triplets and 4 for quadruplets
		int limit = (this.triplet)?3:4;
		if (this.level < limit ) {
			for (int i = 0 ; i < this.codeValues.length() ; i++ ) {
				this.nodes.add(new GeneticTreeNode(	this.codeValues, this.endValues, 
													triplet, this, i));
			}
		} else { this.output = this.cumulateLateral(); }
	}

	/**
	 * Initialization which centralize some elements of root constructors. 
	 * @param codeValues (String) Set of characters to build the code. 
	 * @param endValues (String[]) The String's to get using the code. 
	 * @param triplet (boolean) Indicate if triplet of quadruplet. 
	 * @throws GeneticTreeNodeException
	 * @see GeneticTreeNode#GeneticTreeNode(String, String, boolean)
	 * @see GeneticTreeNode#GeneticTreeNode(String, String[], boolean)
	 */
	private void init(String codeValues, String[] endValues, boolean triplet) 
			throws GeneticTreeNodeException {
		int cube = codeValues.length()*codeValues.length()*codeValues.length();
		int quad = cube*codeValues.length();
		if ( (triplet) && (endValues.length != cube ) )
			{ throw new GeneticTreeNodeException("Length of end values not according to triplet. "); }
		if ( (!triplet) && (endValues.length != quad ) )
			{ throw new GeneticTreeNodeException("Length of end values not according to quadruplet. "); }
		
		this.father		= null;
		this.level		= 0;
		this.lateral	= 0;
		this.output		= this.lateral;
		this.codeValues	= codeValues;
		this.endValues	= endValues;
		this.triplet	= triplet;
		this.nodes		= new ArrayList<GeneticTreeNode>();
		
		// ***** Building the whole tree. 
		IntStream.range(0, this.codeValues.length()).forEach( i -> {
			this.nodes.add(new GeneticTreeNode(	this.codeValues, this.endValues, this.triplet, this, i));
		});
	}

	public int getLevel()					{ return this.level; }
	public int getLateral()					{ return this.lateral; }
	public int getOutput()					{ return this.output; }
	public boolean getTriplet()				{ return this.triplet; }
	public String getCodeValues()			{ return this.codeValues; }
	public String[] getEndValues()			{ return this.endValues; }
	public GeneticTreeNode getFather()		{ return this.father; }
	public List<GeneticTreeNode> getNodes()	{ return this.nodes; }

	/**
	 * This method to get all leaves of the whole current tree. 
	 * <i>Get back to the root of the tree. </i>
	 * @return List of GeneticTreeNode. 
	 */
	public List<GeneticTreeNode> getLeaves() {
		// ***** Getting the root node of the tree. 
		GeneticTreeNode root = this;
		while (!root.isRoot()) { root = root.getFather(); }
		List<GeneticTreeNode> toReturn = new ArrayList<GeneticTreeNode>();
		for (int i = 0 ; i < root.getNodes().size() ; i++) {
			GeneticTreeNode level1 = root.getNodes().get(i);
			for (int j = 0 ; j < level1.getNodes().size() ; j++) {
				GeneticTreeNode level2 = level1.getNodes().get(j);
				for (int k = 0 ; k < level2.getNodes().size() ; k++) {
					GeneticTreeNode level3 = level2.getNodes().get(k);
					if ( (this.triplet) && (level3.isLeave()) )
						{ toReturn.add(level3); }
					else { toReturn.addAll(level3.getNodes()); }
				}
			}
		}
		return toReturn;
	}

	/**
	 * To get the whole code given by current node (empty string for root, then cumulative). 
	 * @return (String) Empty String ; one-char-String to [3|4]-char-String. 
	 * @see GeneticTreeNode#getLastCode()
	 */
	public String getCode() {
		return (this.father == null) ? "" : this.father.getCode()+""+this.getLastCode();
	}

	/**
	 * To get the current code char for current node (last char of code). 
	 * @return (char) One char of code values. 
	 */
	public char getLastCode() { return this.codeValues.charAt(this.lateral); }

	/**
	 * To get the end value for a current node (mainly leaves). 
	 * If current node is not a leave, return the laterality in code values. 
	 * @return (String) End or code value for current node. 
	 */
	public String getValue() {
		return (this.isLeave()) ? this.endValues[this.output]
		                        : this.codeValues.charAt(this.lateral)+"";
	}

	/**
	 * To get the value for a given triplet or quadruplet. 
	 * <i>Get back to the root of the tree. </i>
	 * @param code (String) Triplet or quadruplet. 
	 * @return (String) Corresponding value in end values. 
	 * @throws GeneticTreeNodeException If code has unapropriate length ; if code not using code values ; if code not valid. 
	 * @see GeneticTreeNode#getValue()
	 * @see GeneticTreeNode#getLastCode()
	 * @see GeneticTreeNode#getCode()
	 */
	public String getValue(String code) 
			throws GeneticTreeNodeException {
		/** This method must start from the root of the tree. */
		GeneticTreeNode root = this;
		while (!root.isRoot()) { root = root.getFather(); }
		/** Length is not good. */
		if ( (this.triplet) && (code.length() != 3) )
			{ throw new GeneticTreeNodeException("For triplet, code must be of length 3. "); }
		if ( (!this.triplet) && (code.length() != 4) )
			{ throw new GeneticTreeNodeException("For quadruplet, code must be of length 4. "); }
		/** 'code' will not be recognized at all. */
		/** if (!code.matches("["+this.code_values+"]")) */
		if (!GeneticTreeNode.codeRecognition(code, this.codeValues))
			{ throw new GeneticTreeNodeException("Code is not recognized with according values. "); }
		/** Get through the tree (3 levels if this.triplet) */
		GeneticTreeNode lvl1 = null;
		GeneticTreeNode lvl2 = null;
		GeneticTreeNode lvl3 = null;
		int i = 0;
		// ***** Checking first char (0) at first level, node selection. 
		while ( (i < root.getNodes().size()) && (lvl1 == null) ) {
			GeneticTreeNode tmp = root.getNodes().get(i);
			if (code.charAt(0) == tmp.getLastCode()) { lvl1 = tmp;i = -1; }
			i++;
		}
		while ( (i < lvl1.getNodes().size()) && (lvl2 == null) ) {
			GeneticTreeNode tmp = lvl1.getNodes().get(i);
			if (code.charAt(1) == tmp.getLastCode()) { lvl2 = tmp;i = -1; }
			i++;
		}
		while ( (i < lvl2.getNodes().size()) && (lvl3 == null) ) {
			GeneticTreeNode tmp = lvl2.getNodes().get(i);
			if (code.charAt(2) == tmp.getLastCode()) { lvl3 = tmp;i = -1; }
			i++;
		}
		if (this.triplet) {
			if (code.equals(lvl3.getCode())) { return lvl3.getValue(); }
			else { throw new GeneticTreeNodeException("Not a good value according to triplet lvl 3. "); }
		} else {
			// ***** The fourth level when ( ! this.triplet) ; selecting fourth level node. 
			GeneticTreeNode lvl4 = null;
			while ( (i < lvl3.getNodes().size()) && (lvl4 == null) ) {
				GeneticTreeNode tmp = lvl3.getNodes().get(i);
				if (code.charAt(3) == tmp.getLastCode()) { lvl4 = tmp;i = -1; }
				i++;
			}
			if (code.equals(lvl4.getCode())) { return lvl4.getValue(); }
			else { throw new GeneticTreeNodeException("Not a good value according to quadruplet lvl 4. "); }
		}
	}
	
	/**
	 * To get the triplet or quadruplet for a given value. 
	 * @param value (String) An end-value. 
	 * @return (String) The code for that value. 
	 * @throws GeneticTreeNodeException If value is not in possible end values. 
	 * @see GeneticTreeNode#getCode()
	 * @see GeneticTreeNode#getValue()
	 * @see GeneticTreeNode#getCode(String, boolean)
	 * @see GeneticTreeNode#valueRecognition(String, String[])
	 */
	public String getCode(String value) 
			throws GeneticTreeNodeException 
		{ return this.getCode(value, true); }
	
	/**
	 * To get the triplet or quadruplet for a given value. 
	 * @param value (String) An end-value. 
	 * @return (String) The code for that value. 
	 * @throws GeneticTreeNodeException If value is not in possible end values. 
	 * @see GeneticTreeNode#getCode()
	 * @see GeneticTreeNode#getValue()
	 * @see GeneticTreeNode#getCode(String)
	 * @see GeneticTreeNode#valueRecognition(String, String[])
	 */
	public String getCode(String value,boolean simple) 
			throws GeneticTreeNodeException {
		/** Checking that value should exists in tree. */
		if (!GeneticTreeNode.valueRecognition(value, this.endValues))
			{ throw new GeneticTreeNodeException("Value is not recognized with according end values. "); }
		// ***** This method must start from the root of the tree. 
		GeneticTreeNode root = this;
		while (!root.isRoot()) { root = root.getFather(); }
		// ***** Encoding : Getting leaves. 
		List<GeneticTreeNode> leaves = root.getLeaves();
		// ***** Encoding : Getting list of possibles codes. 
		List<String> codes = new ArrayList<String>();
		
		for (int i = 0 ; i < leaves.size() ; i++) {
			GeneticTreeNode tmp = leaves.get(i);
			if (simple) {
				// ***** Simple coding : first occurrence of value. 
				if (tmp.getValue().equals(value)) { return tmp.getCode(); } 
			} else {
				// ***** Complex coding : get all possibles codes for values. 
				if (tmp.getValue().equals(value)) 
					{ codes.add(tmp.getCode()); }
			}
		}
		// ***** Choice for complex coding. 
		if ( (!simple) && (codes.size() > 0) ) {
			Random rand	= new Random();
			int choice	= rand.nextInt(codes.size());
			return codes.get(choice);
		}
		
		// ***** Empty String output. 
		return root.getCode(); 
	}

	/**
	 * To know if node is root of the determining tree. 
	 * @return (boolean) True if level is 0.
	 * @see GeneticTreeNode#level
	 */
	public boolean isRoot() 
		{ return ( (this.level == 0) && (this.father == null) )?true:false; }

	/**
	 * To know if current node is a terminal node (a leave).
	 * @return (boolean) True if triplet and level 3 of quadruplet and level 4. 
	 * @see GeneticTreeNode#level
	 * @see GeneticTreeNode#triplet
	 */
	public boolean isLeave() 
		{ return (this.triplet)?(this.level == 3):(this.level == 4); }

	/**
	 * To know if current node is not specific (not root, neither leaf)
	 * @return (boolean) True if not root neither leave. 
	 * @see GeneticTreeNode#isRoot()
	 * @see GeneticTreeNode#isLeave()
	 */
	public boolean isNode() 
		{ return ( (!this.isRoot()) && (!this.isLeave()) )?true:false; }

	/**
	 * In order to compute the output order for leaves (last nodes in tree). 
	 * @return (int) Computed value
	 * @see GeneticTreeNode#lateral
	 */
	private int cumulateLateral() {
		if (!this.isLeave()) { return this.lateral; }
		GeneticTreeNode tmp = this.father;
		int sumLaterals = this.lateral;
		int a = 16, b = 4, c = 1;
		// ***** For quadruplets : coefficients change !! 
		if (!this.triplet) { a = 64;b = 16;c = 4; }
		while(tmp != null) {
			switch(tmp.getLevel()) {
			case(0):sumLaterals += 0;break;
			case(1):sumLaterals += tmp.getLateral()*a;break;
			case(2):sumLaterals += tmp.getLateral()*b;break;
			case(3):sumLaterals += tmp.getLateral()*c;break;
			}
			tmp = tmp.getFather();
		}
		return sumLaterals;
	}
	
	/**
	 * Transform a String input into a table of String (with a character in each element). 
	 * @param input (String)
	 * @return (String[])
	 * @see GeneticTreeNode#GeneticTreeNode(String, String, boolean)
	 * @see GeneticTreeNode#endValues
	 * @see GeneticTreeNode#getEndValues()
	 */
	private static String[] toTable(String input)  {
		String table[] = new String[input.length()];
		// ** input.toCharArray() but as String[]
		for (int i = 0 ; i < input.length() ; i++) 
			{ table[i] = input.charAt(i)+""; }
		return table;
	}
	
	/**
	 * Recognize if a given char is in a string. 
	 * @param a (char) A letter from a triplet or quadruplet code. 
	 * @param s (String) A String where to search (this.code_values). 
	 * @return (boolean) true if in ; false if not.
	 * @see GeneticTreeNode#codeRecognition(String, String)
	 */
	private static boolean charIsInString(char a,String s) {
		for (int i = 0 ; i < s.length() ; i++) 
			{ if (s.charAt(i) == a) { return true; } }
		return false;
	}
	
	/**
	 * Recognize if a given String use letters of an other string. 
	 * @param code (String) Triplet or quadruplet. 
	 * @param s (String)  A String where to search (this.code_values). 
	 * @return (boolean) true if in ; false if not.
	 * @see GeneticTreeNode#charIsInString(char, String)
	 * @see GeneticTreeNode#getValue(String)
	 */
	private static boolean codeRecognition(String code,String s) {
		for (int i = 0 ; i < code.length() ; i++) {
			if (!GeneticTreeNode.charIsInString(code.charAt(i), s))
				{ return false; }
		}
		return true;
	}
	
	/**
	 * Recognize if a given String is an item of a String table. 
	 * @param value (String) A valuable String. 
	 * @param s (String[]) A table of String where to search (this.end_values). 
	 * @return (boolean) true if in ; false if not.
	 * @see GeneticTreeNode#toTable(String)
	 * @see GeneticTreeNode#getCode(String)
	 */
	private static boolean valueRecognition(String value,String[] s) {
		for (int i = 0 ; i < s.length ; i++) 
			{ if (value.equals(s[i])) { return true; } }
		return false;
	}

	/**
	 * To get a pre-defined GeneticTreeNode for the standard genetic code. 
	 * @return (GeneticTreeNode) Pre-build Tree of standard genetic code. 
	 */
	public static GeneticTreeNode getGeneticCodeStandard() {
		String itemsStandardGeneticCode = "KKNNRRSSIMIITTTTEEDDGGGGVVVVAAAA**YY*WCCLLFFSSSSQQHHRRRRLLLLPPPP";
		try { 
			return new GeneticTreeNode("aguc", itemsStandardGeneticCode, true);
		} catch (GeneticTreeNodeException e) { return null; }
	}
	
	/**
	 * To get a pre-defined GeneticTreeNode for the 'Gattaca' genetic code. 
	 * @return (GeneticTreeNode) Pre-build Tree of 'Gattaca' genetic code. 
	 */
	public static GeneticTreeNode getGeneticCodeGattaca() {
		String itemsGattaca = "abcdefghijklmnopqrstuvwxyz000111222333444555666777888999::''MM**";
		try { 
			return new GeneticTreeNode("ACTG", itemsGattaca, true);
		} catch (GeneticTreeNodeException e) { return null; }
	}
	
	/**
	 * To get a pre-defined GeneticTreeNode for the 'Phase II' genetic code. 
	 * @return (GeneticTreeNode) Pre-build Tree of 'Phase II' genetic code. 
	 */
	public static GeneticTreeNode getGeneticCodePhaseTwo() {
		String itemsPhaseTwo[] = new String[256];
		itemsPhaseTwo[0] = "a";itemsPhaseTwo[1] = "a";
		itemsPhaseTwo[2] = "b";itemsPhaseTwo[3] = "b";
		itemsPhaseTwo[4] = "c";itemsPhaseTwo[5] = "c";
		itemsPhaseTwo[6] = "d";itemsPhaseTwo[7] = "d";
		itemsPhaseTwo[8] = "e";itemsPhaseTwo[9] = "e";
		itemsPhaseTwo[10] = "f";itemsPhaseTwo[11] = "f";
		itemsPhaseTwo[12] = "g";itemsPhaseTwo[13] = "g";
		itemsPhaseTwo[14] = "h";itemsPhaseTwo[15] = "h";
		itemsPhaseTwo[16] = "i";itemsPhaseTwo[17] = "i";
		itemsPhaseTwo[18] = "j";itemsPhaseTwo[19] = "j";
		itemsPhaseTwo[20] = "k";itemsPhaseTwo[21] = "k";
		itemsPhaseTwo[22] = "l";itemsPhaseTwo[23] = "l";
		itemsPhaseTwo[24] = "m";itemsPhaseTwo[25] = "m";
		itemsPhaseTwo[26] = "n";itemsPhaseTwo[27] = "n";
		itemsPhaseTwo[28] = "o";itemsPhaseTwo[29] = "o";
		itemsPhaseTwo[30] = "p";itemsPhaseTwo[31] = "p";
		itemsPhaseTwo[32] = "q";itemsPhaseTwo[33] = "q";
		itemsPhaseTwo[34] = "r";itemsPhaseTwo[35] = "r";
		itemsPhaseTwo[36] = "s";itemsPhaseTwo[37] = "s";
		itemsPhaseTwo[38] = "t";itemsPhaseTwo[39] = "t";
		itemsPhaseTwo[40] = "u";itemsPhaseTwo[41] = "u";
		itemsPhaseTwo[42] = "v";itemsPhaseTwo[43] = "v";
		itemsPhaseTwo[44] = "w";itemsPhaseTwo[45] = "w";
		itemsPhaseTwo[46] = "x";itemsPhaseTwo[47] = "x";
		itemsPhaseTwo[48] = "y";itemsPhaseTwo[49] = "y";
		itemsPhaseTwo[50] = "z";itemsPhaseTwo[51] = "z";
		itemsPhaseTwo[52] = "A";itemsPhaseTwo[53] = "A";
		itemsPhaseTwo[54] = "B";itemsPhaseTwo[55] = "B";
		itemsPhaseTwo[56] = "C";itemsPhaseTwo[57] = "C";
		itemsPhaseTwo[58] = "D";itemsPhaseTwo[59] = "D";
		itemsPhaseTwo[60] = "E";itemsPhaseTwo[61] = "E";
		itemsPhaseTwo[62] = "F";itemsPhaseTwo[63] = "F";
		itemsPhaseTwo[64] = "G";itemsPhaseTwo[65] = "G";
		itemsPhaseTwo[66] = "H";itemsPhaseTwo[67] = "H";
		itemsPhaseTwo[68] = "I";itemsPhaseTwo[69] = "I";
		itemsPhaseTwo[70] = "J";itemsPhaseTwo[71] = "J";
		itemsPhaseTwo[72] = "K";itemsPhaseTwo[73] = "K";
		itemsPhaseTwo[74] = "L";itemsPhaseTwo[75] = "L";
		itemsPhaseTwo[76] = "M";itemsPhaseTwo[77] = "M";
		itemsPhaseTwo[78] = "N";itemsPhaseTwo[79] = "N";
		itemsPhaseTwo[80] = "O";itemsPhaseTwo[81] = "O";
		itemsPhaseTwo[82] = "P";itemsPhaseTwo[83] = "P";
		itemsPhaseTwo[84] = "Q";itemsPhaseTwo[85] = "Q";
		itemsPhaseTwo[86] = "R";itemsPhaseTwo[87] = "R";
		itemsPhaseTwo[88] = "S";itemsPhaseTwo[89] = "S";
		itemsPhaseTwo[90] = "T";itemsPhaseTwo[91] = "T";
		itemsPhaseTwo[92] = "U";itemsPhaseTwo[93] = "U";
		itemsPhaseTwo[94] = "V";itemsPhaseTwo[95] = "V";
		itemsPhaseTwo[96] = "W";itemsPhaseTwo[97] = "W";
		itemsPhaseTwo[98] = "X";itemsPhaseTwo[99] = "X";
		itemsPhaseTwo[100] = "Y";itemsPhaseTwo[101] = "Y";
		itemsPhaseTwo[102] = "Z";itemsPhaseTwo[103] = "Z";
		itemsPhaseTwo[104] = "0";itemsPhaseTwo[105] = "0";
		itemsPhaseTwo[106] = "0";itemsPhaseTwo[107] = "0";
		itemsPhaseTwo[108] = "1";itemsPhaseTwo[109] = "1";
		itemsPhaseTwo[110] = "1";itemsPhaseTwo[111] = "1";
		itemsPhaseTwo[112] = "2";itemsPhaseTwo[113] = "2";
		itemsPhaseTwo[114] = "2";itemsPhaseTwo[115] = "2";
		itemsPhaseTwo[116] = "3";itemsPhaseTwo[117] = "3";
		itemsPhaseTwo[118] = "3";itemsPhaseTwo[119] = "3";
		itemsPhaseTwo[120] = "4";itemsPhaseTwo[121] = "4";
		itemsPhaseTwo[122] = "4";itemsPhaseTwo[123] = "4";
		itemsPhaseTwo[124] = "5";itemsPhaseTwo[125] = "5";
		itemsPhaseTwo[126] = "5";itemsPhaseTwo[127] = "5";
		itemsPhaseTwo[128] = "6";itemsPhaseTwo[129] = "6";
		itemsPhaseTwo[130] = "6";itemsPhaseTwo[131] = "6";
		itemsPhaseTwo[132] = "7";itemsPhaseTwo[133] = "7";
		itemsPhaseTwo[134] = "7";itemsPhaseTwo[135] = "7";
		itemsPhaseTwo[136] = "8";itemsPhaseTwo[137] = "8";
		itemsPhaseTwo[138] = "8";itemsPhaseTwo[139] = "8";
		itemsPhaseTwo[140] = "9";itemsPhaseTwo[141] = "9";
		itemsPhaseTwo[142] = "9";itemsPhaseTwo[143] = "9";
		itemsPhaseTwo[144] = "=";itemsPhaseTwo[145] = "=";
		itemsPhaseTwo[146] = "!";itemsPhaseTwo[147] = "!";
		itemsPhaseTwo[148] = ".";itemsPhaseTwo[149] = ".";
		itemsPhaseTwo[150] = "-";itemsPhaseTwo[151] = "-";
		itemsPhaseTwo[152] = "+";itemsPhaseTwo[153] = "+";
		itemsPhaseTwo[154] = "/";itemsPhaseTwo[155] = "/";
		itemsPhaseTwo[156] = "*";itemsPhaseTwo[157] = "*";
		itemsPhaseTwo[158] = "\"";itemsPhaseTwo[159] = "\"";
		itemsPhaseTwo[160] = "?";itemsPhaseTwo[161] = "?";
		itemsPhaseTwo[162] = ":";itemsPhaseTwo[163] = ":";
		itemsPhaseTwo[164] = "++";itemsPhaseTwo[165] = "++";
		itemsPhaseTwo[166] = "--";itemsPhaseTwo[167] = "--";
		itemsPhaseTwo[168] = ";";itemsPhaseTwo[169] = ";";
		itemsPhaseTwo[170] = "\n";itemsPhaseTwo[171] = "\n";
		itemsPhaseTwo[172] = "\t";itemsPhaseTwo[173] = "\t";
		itemsPhaseTwo[174] = " ";itemsPhaseTwo[175] = " ";
		itemsPhaseTwo[176] = "(";itemsPhaseTwo[177] = "(";
		itemsPhaseTwo[178] = ")";itemsPhaseTwo[179] = ")";
		itemsPhaseTwo[180] = "[";itemsPhaseTwo[181] = "[";
		itemsPhaseTwo[182] = "]";itemsPhaseTwo[183] = "]";
		itemsPhaseTwo[184] = "{";itemsPhaseTwo[185] = "{";
		itemsPhaseTwo[186] = "}";itemsPhaseTwo[187] = "}";
		itemsPhaseTwo[188] = "<";itemsPhaseTwo[189] = "<";
		itemsPhaseTwo[190] = ">";itemsPhaseTwo[191] = ">";
		itemsPhaseTwo[192] = "class";itemsPhaseTwo[193] = "class";
		itemsPhaseTwo[194] = "class";itemsPhaseTwo[195] = "class";
		itemsPhaseTwo[196] = "interface";itemsPhaseTwo[197] = "interface";
		itemsPhaseTwo[198] = "interface";itemsPhaseTwo[199] = "interface";
		itemsPhaseTwo[200] = "static";itemsPhaseTwo[201] = "static";
		itemsPhaseTwo[202] = "static";itemsPhaseTwo[203] = "static";
		itemsPhaseTwo[204] = "final";itemsPhaseTwo[205] = "final";
		itemsPhaseTwo[206] = "final";itemsPhaseTwo[207] = "final";
		itemsPhaseTwo[208] = "private";itemsPhaseTwo[209] = "private";
		itemsPhaseTwo[210] = "private";itemsPhaseTwo[211] = "private";
		itemsPhaseTwo[212] = "public";itemsPhaseTwo[213] = "public";
		itemsPhaseTwo[214] = "public";itemsPhaseTwo[215] = "public";
		itemsPhaseTwo[216] = "void";itemsPhaseTwo[217] = "void";
		itemsPhaseTwo[218] = "void";itemsPhaseTwo[219] = "void";
		itemsPhaseTwo[220] = "int";itemsPhaseTwo[221] = "int";
		itemsPhaseTwo[222] = "int";itemsPhaseTwo[223] = "int";
		itemsPhaseTwo[224] = "double";itemsPhaseTwo[225] = "double";
		itemsPhaseTwo[226] = "double";itemsPhaseTwo[227] = "double";
		itemsPhaseTwo[228] = "true";itemsPhaseTwo[229] = "true";
		itemsPhaseTwo[230] = "true";itemsPhaseTwo[231] = "true";
		itemsPhaseTwo[232] = "false";itemsPhaseTwo[233] = "false";
		itemsPhaseTwo[234] = "false";itemsPhaseTwo[235] = "false";
		itemsPhaseTwo[236] = "if";itemsPhaseTwo[237] = "if";
		itemsPhaseTwo[238] = "else";itemsPhaseTwo[239] = "else";
		itemsPhaseTwo[240] = "while";itemsPhaseTwo[241] = "while";
		itemsPhaseTwo[242] = "while";itemsPhaseTwo[243] = "while";
		itemsPhaseTwo[244] = "&&";itemsPhaseTwo[245] = "&&";
		itemsPhaseTwo[246] = "||";itemsPhaseTwo[247] = "||";
		itemsPhaseTwo[248] = "return";itemsPhaseTwo[249] = "return";
		itemsPhaseTwo[250] = "return";itemsPhaseTwo[251] = "return";
		itemsPhaseTwo[252] = "[STOP]";itemsPhaseTwo[253] = "[STOP]";
		itemsPhaseTwo[254] = "[STOP]";itemsPhaseTwo[255] = "[STOP]";
		try {
			return new GeneticTreeNode("UBVP", itemsPhaseTwo, false);
		} catch (GeneticTreeNodeException e) { return null; }
	}
	

}
