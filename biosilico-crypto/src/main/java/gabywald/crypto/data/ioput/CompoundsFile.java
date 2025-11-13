package gabywald.crypto.data.ioput;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabywald.crypto.data.chemical.Atom;
import gabywald.crypto.data.chemical.Bond;
import gabywald.crypto.data.chemical.Molecule;
import gabywald.global.data.File;
import gabywald.global.exceptions.DataException;

/**
 * This class defines a File and loading for Compounds. 
 * @author Gabriel Chandesris (2011, 2025)
 */
@SuppressWarnings("serial")
public class CompoundsFile extends File {
	/** '  6  6  0  0  0  0  0  0  0  0  1 V2000' */
	/** ' 12 11  0     1  0  0  0  0  0999 V2000' */
	private static final Pattern PATTERN_GENERAL_INFO = 
		Pattern.compile("^([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})( V2000)$");
	/** '    1.9050   -0.7932    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0' */
	private static final Pattern PATTERN_ATOM_INFO = 
			/** ^([ \\.0-9]{10})([ \\.0-9]{10})([ \\.0-9]{10}) ([ a-zA-Z]{2})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})$ */
			Pattern.compile("^([ \\.\\-0-9]{10})([ \\.\\-0-9]{10})([ \\.\\-0-9]{10}) ([ a-zA-Z]{2})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})$(.*?)$");
	/** '  2  1  1  0  0  0  0' */
	private static final Pattern PATTERN_BOND_INFO = 
		Pattern.compile("^([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})([ 0-9]{3})$");
	
	private static final Pattern PATTERN_MORE_INFO = Pattern.compile("^>\\s+<(.*?)>$");
	/** Set of compounds loaded. */
	private List<Molecule> compounds;
	
	private CompoundsFile() {
		super("");
		this.compounds = new ArrayList<Molecule>();
	}
	
	/** 
	 * Constructor with given name. 
	 * @param name (String)
	 */
	public CompoundsFile(String name) {
		super(name);
		this.compounds = new ArrayList<Molecule>();
		// Molecule currentMole			= null;
		int[] generalInformation		= new int[11];
		Atom[] atoms					= null;
		int atomIndex = 0;
		Bond[] bonds					= null;
		int bondIndex = 0;
		Hashtable<String,String> sdf	= new Hashtable<String,String>();
		String compoundName				= null;
		String compoundComm				= null;
		
		if ( (this.nbLines() == 0) 
				|| (this.line(0).matches("ERROR(.*)")) ) { 
			/** System.out.println(this.getLine(0)); */
			/** System.out.println("Not available !!!"); */
		} else {
			for (int i = 0 ; i < this.lengthFile() ; i++) {
				String line = this.line(i);
				if (compoundName == null)		{ compoundName = line; }
				else if (compoundComm == null)	{ compoundComm = line; }
				else {
					Matcher generalInfoMatch	= CompoundsFile.PATTERN_GENERAL_INFO.matcher(line);
					Matcher atomInfoMatch		= CompoundsFile.PATTERN_ATOM_INFO.matcher(line);
					Matcher bondInfoMatch		= CompoundsFile.PATTERN_BOND_INFO.matcher(line);
					Matcher moreInfoMatch		= CompoundsFile.PATTERN_MORE_INFO.matcher(line);
					if (generalInfoMatch.matches()) {
						for (int j = 1 ; j < generalInfoMatch.groupCount() ; j++) {
							if (generalInfoMatch.group(j).trim().equals("")) 
								{ generalInformation[j-1] = 0; } /** !! */
							else { generalInformation[j-1] = 
									Integer.parseInt(generalInfoMatch.group(j).trim()); }
						}
						atoms = new Atom[generalInformation[0]];
						bonds = new Bond[generalInformation[1]];
					} else if ( (atomInfoMatch.matches()) && (atoms[atoms.length-1] == null) ) {
						double posX = Double.parseDouble(atomInfoMatch.group(1).trim());
						double posY = Double.parseDouble(atomInfoMatch.group(2).trim());
						double posZ = Double.parseDouble(atomInfoMatch.group(3).trim());
						Atom tmp = new Atom(atomInfoMatch.group(4).trim().toCharArray(), 
												posX, posY, posZ);
						int[] extra = new int[12];
						for (int j = 5 ; j < atomInfoMatch.groupCount() ; j++) 
							{ extra[j-5] = Integer.parseInt(atomInfoMatch.group(j).trim()); }
						tmp.setExtraInfo(extra);
						atoms[atomIndex++] = tmp;
						// System.out.println(tmp.toString());
					}  else if ( (bondInfoMatch.matches()) && (bonds[bonds.length-1] == null) ) {
						int first	= Integer.parseInt(bondInfoMatch.group(1).trim());
						int second	= Integer.parseInt(bondInfoMatch.group(2).trim());
						int type	= Integer.parseInt(bondInfoMatch.group(3).trim());
						int[] extra = new int[4];
						for (int j = 4 ; j < bondInfoMatch.groupCount() ; j++) 
							{ extra[j-4] = Integer.parseInt(bondInfoMatch.group(j).trim()); }
						Bond tmp = new Bond(first, second, type);
						tmp.setExtraInfo(extra);
						bonds[bondIndex++] = tmp;
						// System.out.println(tmp.toString());
					} else if (moreInfoMatch.matches()) { 
						String infoName = moreInfoMatch.group(1);
						String infoMore	= new String("");
						String infoCont = this.line(++i);
						do { /** !! If annotation counts more than one line !! */
							infoMore += infoCont+"\n";
							infoCont = this.line(++i);
							// System.out.println(infoCont);
						} while (!infoCont.equals(""));
						if (infoMore.charAt(infoMore.length()-1) == '\n') 
							{ infoMore = infoMore.substring(0, infoMore.length()-1); }
						/** Adding annotation... */
						sdf.put(infoName, infoMore);
						// System.out.println("\t'"+infoName+"'\t'"+infoMore+"'");
					} else if (line.endsWith("$$$$")) {
						Molecule currentMole = new Molecule(compoundName, compoundComm, generalInformation);
						currentMole.setAtoms(atoms);
						currentMole.setBonds(bonds);
						currentMole.setDataValues(sdf);
						// System.out.println(currentMole.toString());
						// System.out.println(currentMole.toString()+" line "+i+" !!!!!"
						// 					+"\t'"+atoms.length+"'\t'"+bonds.length+"'");
						
						/** String[] keys = currentMole.getDataKeys();
						for (int par = 0 ; par < keys.length ; par++) 
							{ currentMole.getDataValue(keys[par]); } */
						
						this.compounds.add(currentMole);
						/** Reset values... */
						compoundName		= null;
						compoundComm		= null;
						atoms				= null;
						bonds				= null;
						atomIndex			= 0;
						bondIndex			= 0;
						sdf					= new Hashtable<String,String>();
						generalInformation	= new int[11];
						
						currentMole		= null;
						
					} else { ; }
				}
			}
		}
	}
	
	public boolean hasError() {
		if (this.hasError())									{ return true; }
		if (CompoundsFile.hasErrorCompounds(this.compounds))	{ return true; }
		return false;
	}
	
	public static boolean hasErrorCompounds(List<Molecule> compounds) {
		if (compounds.size() == 0) {
			System.out.println("Compounds list empty ??");
			return true;
		}
		
		return compounds.stream().anyMatch( m -> m.hasError() );
	}
	
	public List<Molecule> getCompounds() 
		{ return this.compounds; }
	
	public void setCompounds(List<Molecule> comps)
		{ this.compounds = comps; }
	
	public void write() throws DataException {
		this.empty();
		this.addToChamps(this.compounds.toString());
		this.printFile();
	}
	
	public CompoundsFile clone() {
		CompoundsFile result = new CompoundsFile();
		result.compounds = this.getCompounds();
		return result;
	}
	
	public boolean equals(CompoundsFile toCompare) {
		if (!super.equals(toCompare)) { return false; }
		/** System.out.println("Same basic file !!"); */
		if (!this.compounds.equals(toCompare.getCompounds())) 
			{ return false; }
		return true;
	}

}
