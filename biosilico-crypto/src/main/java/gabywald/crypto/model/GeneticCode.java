package gabywald.crypto.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import gabywald.global.data.Fichier;
import gabywald.global.data.samples.BioDataFile;

public class GeneticCode {
	/** private static final String DIRECTORY_DATAS = 
		"resources/conf/dataBioBio/"; // "resources/documentation/cryptoDevNotes/datas/"; */
	public static final String SEPARATOR = ";;;;;";
	/** Set of String's : name ; abbreviation, ncbiAA, startM, base[1-3]. */
	private String[] elements;
	/** ID of the Genetic Code. */
	private int id;
	
	/** Default constructor with 3 levels of 'encoding'. */
	public GeneticCode() 
		{ this.init(0, new String[7+0]); }
	
	/** 
	 * Constructor with more than 3 levels of 'encoding'. 
	 * @param more (int) From 3 (more = 0) to up.
	 */
	public GeneticCode(int more) 
		{ this.init(0, new String[7+more]); }
	
	/**
	 * Constructor with defined ident end elts. 
	 * @param ident (int)
	 * @param elts (Strings)
	 */
	/** public GeneticCode(int ident, String[] elts) 
		{ this.init(ident, elts); } */
	
	public int getCodonLength()	{ return this.elements.length-4; }
	
	public int getID()			{ return this.id; }
	
	/**
	 * Constructors helper. 
	 * @param id (int)
	 * @param elts (String[])
	 */
	private void init(int id, String[] elts) {
		this.id			= id;
		this.elements	= elts;
	}
	
	public EncodingNode makeAsEncodingNode() {
		int lvls		= (7-this.elements.length)+3;
		/** alphabet => chars */
		char[] basesArray	= this.elements[4].toCharArray();
		char[] chars		= GeneticCode.getAlphabetFromGeneticCode(basesArray);
		
		/** Getting values... */
		String[] values = null;
		if (this.elements[2].toCharArray().length == basesArray.length) {
			/** One-to-One _character_ relation between n-uplet and its result ('AminoAcid'). */
			char[] tmps	= this.elements[2].toCharArray();
			values		= new String[tmps.length];
			for (int  i = 0 ; i < tmps.length ; i++) 
				{ values[i] = tmps[i]+""; }
		} else { values = this.elements[2].split(GeneticCode.SEPARATOR); }
		/** XXX "the best separator ever" in case of complex result. */
		
		/** Creating EncodingNode ! */
		EncodingNode tree		= new EncodingNode(lvls, chars, values);
		
		/** Setting start / stop values. */ 
		EncodingNode leaves[]	= tree.getLeaves();
		char[] starts = this.elements[3].toCharArray();
		for (int i = 0 ; i < starts.length ; i++) 
			{ if (starts[i] == 'M') { leaves[i].setStart(true); } }
		if (this.elements[2].toCharArray().length == basesArray.length) {
			/** One-to-One _character_ relation between n-uplet and its result ('AminoAcid'). */
			char[] stops = this.elements[2].toCharArray();
			for (int  i = 0 ; i < stops.length ; i++) 
				{ if (stops[i] == '*') { leaves[i].setStop(true); } }
		}
		
		return tree;
	}
	
	/**
	 * Transform and EncodingNode to a GeneticCode. 
	 * @param tree (EncodingNode) root of the tree. 
	 * @return (GeneticCode)
	 */
	public static GeneticCode fromEncodingNode(EncodingNode tree) {
		EncodingNode root		= tree.getRoot();
		char[] basics			= GeneticCode.getAlphabetFromEncodingNode(root);

		/** Getting leaves and end-values. 
		 * Here are get in an optimized way 
		 * (one loop instead of one for each of set of Codons, Values)... */
		EncodingNode[] leaves	= root.getLeaves();
		String[] currentCodes	= new String[leaves.length];
		String[] currentVales	= new String[leaves.length];
		boolean hasAlwaysOne	= true;
		int maxLvls				= 1; /** WARN beware this (maxLvls should smaller : '1') !! */
		for (int  i = 0 ; i < leaves.length ; i++) {
			currentCodes[i] = leaves[i].getCurrentCode();
			currentVales[i] = leaves[i].getCurrentValue();
			if (currentVales[i].length() > 1) { hasAlwaysOne = false; }
			maxLvls = Math.max(maxLvls, leaves[i].getLevel());
		}
		
		String aminoAcids	= new String("");
		String areStarters	= new String("");
		for (int i = 0 ; i < leaves.length ; i++) { 
			aminoAcids += currentVales[i]
					+((!hasAlwaysOne)?GeneticCode.SEPARATOR:"");
			
			areStarters += ((leaves[i].isStart())?"M": 
							/** indication of stop should not be indicated. */
							((leaves[i].isStop())?"*":"-") )
					+((!hasAlwaysOne)?GeneticCode.SEPARATOR:"");
		}
		/** To avoid a useless separator at end, if it is present. */
		if (!hasAlwaysOne) {
			aminoAcids	= aminoAcids.substring(0, aminoAcids.length()-GeneticCode.SEPARATOR.length());
			areStarters	= areStarters.substring(0, areStarters.length()-GeneticCode.SEPARATOR.length());
		}
		
		String[] bases = new String[currentCodes[0].length()];
		for (int i = 0 ; i < bases.length ; i++) 
			{ bases[i] = new String(""); }
		for (int i = 0 ; i < leaves.length ; i++) {
			String current = currentCodes[i];
			for (int j = 0 ; j < current.length() ; j++) 
				{ bases[j] += current.charAt(j); }
		}
		
		GeneticCode result	= new GeneticCode(maxLvls-3);
		String basicsStr	= new String("");
		for (int i = 0 ; i < basics.length ; i++) { basicsStr += basics[i]; }
		result.elements[0]	= new String("Genetic Code '"+basicsStr+"' with "+maxLvls+" levels. ");
		
		result.elements[2]	= aminoAcids;
		result.elements[3]	= areStarters;
		
		for (int i = 0 ; i < currentCodes[0].length() ; i++) 
			{ result.elements[4+i] = bases[i]; }
		return result;
	}
	
	private String getElement(int i) {
		if ( (i >= 0) && (i < this.elements.length) ) 
			{ return ((this.elements[i] != null)?this.elements[i]:""); }
		return null;
	}
	
	public String toStringNCBI01() {
		String result = new String();
		result += "  name \""+this.getElement(0)+"\" ,\n";
		if (this.elements[1] != null)
			{ result += "  name \""+this.getElement(1)+"\" ,\n"; }
		result += "  id \""+this.id+"\" ,\n";
		result += "  ncbieaa  \""+this.getElement(2)+"\",\n";
		result += "  sncbieaa \""+this.getElement(3)+"\"\n";
		for (int i = 4 ; i < this.elements.length ; i++) 
			{ result += "  -- Base"+(i-3)+"  "+this.elements[i]+"\n"; }
		return result;
	}
	
	public String toStringNCBI02() {
		String result = new String();
		result += "  Genetic Code ["+this.id+"]\n";
		result += "  "+this.getElement(0)+"\n\n";
		result += "    AAs  = "+this.getElement(2)+"\n";
		result += "  Starts = "+this.getElement(3)+"\n";
		for (int i = 4 ; i < this.elements.length ; i++) 
			{ result += "  Base"+(i-3)+"  = "+this.elements[i]+"\n"; }
		return result;
	}
	
	/**
	 * Take NCBI codes from the 'NCBI genetic code table' reference. 
	 * @return (GeneticCode[]) set of the possible Genetic Codes. 
	 */
	public static GeneticCode[] readFromNCBIG01() {
		GeneticCode[] gcTable	= new GeneticCode[0];
		/** String pathToFile		= GeneticCode.DIRECTORY_DATAS+"gc.prt"; */
		/** FichierV0001 gcFile		= new FichierV0001(pathToFile); */
		Fichier gcFile			= BioDataFile.getGeneticCodesPRT();
		
		Pattern name = Pattern.compile("  name \"(.*)\" ,");
		Pattern iden = Pattern.compile("  id ([0-9]+) ,");
		Pattern ncbi = Pattern.compile("  ncbieaa  \"(.*)\",");
		Pattern star = Pattern.compile("  sncbieaa \"(.*)\"");
		Pattern base = Pattern.compile("  -- Base([0-9])  (.*)");
		
		boolean startedDefinitions	= false;
		GeneticCode code			= null;

		for (int i = 0 ; i < gcFile.getNbLines() ; i++) {
			String line = gcFile.getLine(i);
			if (!startedDefinitions) {
				if (line.matches("(.*)Genetic-code-table(.*)")) 
					{ startedDefinitions = true; }
			} else {
				Matcher mName = name.matcher(line);
				Matcher mIden = iden.matcher(line);
				Matcher mNcbi = ncbi.matcher(line);
				Matcher mStar = star.matcher(line);
				Matcher mBase = base.matcher(line);
				if (line.matches("(.*)\\{(.*)")) {
					code = new GeneticCode();
				} else if ( (code != null) && (line.matches("(.*)\\}(.*)")) ) {
					/** if (code != null) { ; } */
					gcTable = GeneticCode.addCodeInTable(gcTable, code);
					// System.out.println("\t"+gcTable.length+" : '"+code.id+"'");
					code = null;
				} else if (mName.matches()) {
					if (code.elements[0] == null) 
						{ code.elements[0] = mName.group(1); }
					else { code.elements[1] = mName.group(1); }
				} else if (mIden.matches()) 
					{ code.id = Integer.parseInt(mIden.group(1)); }
				else if (mNcbi.matches()) 
					{ code.elements[2] = mNcbi.group(1); }
				else if (mStar.matches()) 
					{ code.elements[3] = mStar.group(1); }
				else if (mBase.matches()) {
					for (int j = 4 ; j <= 6 ; j++) {
						if (code.elements[j] == null) 
							{ code.elements[j] = mBase.group(2);j = 7; }
					}
				}
			}
		}
		return gcTable;
	}
	
	/**
	 * Take NCBI codes from the 'Features table definition' (extract). 
	 * @return (GeneticCode[]) set of the possible Genetic Codes. 
	 */
	public static GeneticCode[] readFromNCBIG02() {
		GeneticCode[] gcTable	= new GeneticCode[0];
		/** String pathToFile		= GeneticCode.DIRECTORY_DATAS+"featuresExtract.txt"; */
		/** FichierV0001 gcFile		= new FichierV0001(pathToFile); */
		Fichier gcFile			= BioDataFile.getFeatureExtracts();
		
		Pattern name = Pattern.compile("  (.*( \\(transl_table=[0-9]+\\))?)\\s*");
		Pattern iden = Pattern.compile("  Genetic Code \\[([0-9]+)\\]\\s*");
		Pattern ncbi = Pattern.compile("    AAs  = (.*)");
		Pattern star = Pattern.compile("  Starts = (.*)");
		Pattern base = Pattern.compile("  Base([0-9])  = (.*)");
		
		GeneticCode code			= null;

		for (int i = 0 ; i < gcFile.getNbLines() ; i++) {
			String line = gcFile.getLine(i);
			Matcher mName = name.matcher(line);
			Matcher mIden = iden.matcher(line);
			Matcher mNcbi = ncbi.matcher(line);
			Matcher mStar = star.matcher(line);
			Matcher mBase = base.matcher(line);
			if (mIden.matches()) { 
				if (code != null) { 
					gcTable = GeneticCode.addCodeInTable(gcTable, code);
					// System.out.println("\t"+gcTable.length+" : '"+code.id+"'");
					code = null;
				}
				code = new GeneticCode();
				code.id = Integer.parseInt(mIden.group(1)); 
			} else if ( (code.elements[0] == null) && (mName.matches()) )  {
				if (code.elements[0] == null) 
					{ code.elements[0] = mName.group(1); }
				else { code.elements[1] = mName.group(1); }
			} else if (mNcbi.matches()) 
				{ code.elements[2] = mNcbi.group(1); }
			else if (mStar.matches()) 
				{ code.elements[3] = mStar.group(1); }
			else if (mBase.matches()) {
				for (int j = 4 ; j <= 6 ; j++) {
					if (code.elements[j] == null) 
						{ code.elements[j] = mBase.group(2);j = 7; }
				}
			}
			// else { System.out.println("\'"+line+"\'"); }
		}
		/** For the last code. */
		gcTable = GeneticCode.addCodeInTable(gcTable, code);
		// System.out.println("\t"+gcTable.length+" : '"+code.id+"'");
		return gcTable;
	}
	
	/**
	 * To add a GeneticCode in a set of geneticCode (set as a table / array). 
	 * @param table (GeneticCode[])
	 * @param toAdd (GeneticCode)
	 * @return (GeneticCode[])
	 * @see GeneticCode#readFromNCBIG01()
	 * @see GeneticCode#readFromNCBIG02()
	 */
	private static GeneticCode[] addCodeInTable(GeneticCode[] table, GeneticCode toAdd) {
		if (toAdd == null) { return table; }
		GeneticCode[] nextTable = new GeneticCode[table.length+1];
		for (int i = 0 ; i < table.length ; i++) 
			{ nextTable[i] = table[i]; }
		nextTable[table.length] = toAdd;
		return nextTable;
	}
	
	public char[] getAlphabet() 
		{ return GeneticCode.getAlphabetFromGeneticCode(this.elements[4].toCharArray()); }
	
	private static char[] getAlphabetFromGeneticCode(char[] sampleOfChars) {
//		/** Getting the alphabet. */
//		char[] basics			= new char[0];
//		for (int i = 0 ; i < root.getChilds().length ; i++) {
//			char base = root.getChilds()[i].getCurrentCode().charAt(0);
//			if (!GeneticCode.isPresent(basics, base))
//				{ basics = GeneticCode.addBaseInBasics(basics, base); }
//		}
//		/** *********************************************** */
//		/** Getting chars... */
//		char chars[]	= new char[0];
//		char[] basesArray = this.elements[4].toCharArray();
//		for (int i = 0 ; i < basesArray.length ; i++) {
//			/** Test if NOT present. */
//			boolean isPresent = true;
//			int index = 0;
//			while ( (index < basesArray.length) && (isPresent)) {
//				isPresent = GeneticCode.isPresent(chars, basesArray[index]);
//				index++;
//			}
//			/** Add if NOT. */
//			if (!isPresent) 
//				{ chars = GeneticCode.addBaseInBasics(chars, basesArray[index]); }
//		}
		
		char[] alphabet			= new char[0];
		for (int i = 0 ; i < sampleOfChars.length ; i++) {
			/** Test if NOT present. */
			boolean isPresent = true;
			int index = 0;
			while ( (index < sampleOfChars.length) && (isPresent)) {
				isPresent = GeneticCode.isPresent(alphabet, sampleOfChars[index]);
				index++;
			}
			/** Add if NOT. */
			if ( (!isPresent) && (index < sampleOfChars.length) )
				{ alphabet = GeneticCode.addBaseInBasics(alphabet, sampleOfChars[index]); }
		}
		
		return alphabet;
	}
	
	private static char[] getAlphabetFromEncodingNode(EncodingNode root) {
		char[] basics			= new char[0];
		for (int i = 0 ; i < root.getChilds().length ; i++) {
			char[] more = GeneticCode.getAlphabetFromGeneticCode
							(root.getChilds()[i].getCurrentCode().toCharArray());
			for (int j = 0 ; j < more.length ; j++) {
				if (!GeneticCode.isPresent(basics, more[i]))
					{ basics = GeneticCode.addBaseInBasics(basics, more[i]); }
			}
		}
		return basics;
	}

	private static boolean isPresent(char[] tab, char c) {
		for (int i = 0 ; i < tab.length ; i++) 
			{ if (tab[i] == c) { return true; } }
		return false;
	}
	
	private static char[] addBaseInBasics(char[] table, char toAdd) {
		char[] nextTable = new char[table.length+1];
		for (int i = 0 ; i < table.length ; i++) 
			{ nextTable[i] = table[i]; }
		nextTable[table.length] = toAdd;
		return nextTable;
	}
	
	
	

}
