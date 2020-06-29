package gabywald.crypto.data.composition;

import java.util.HashMap;

/**
 * 
 * @author Gabriel Chandesris (2011)
 * @see gabywald.crypto.data.GenBank
 * @see gabywald.crypto.data.Embl
 * @see FeatureDefinition
 */
public class Feature {
	/** In order to permit parsing of positionning... */
	public static final String[][] GENBANK_FEATURES_POSITION = {
		{ "^([0-9]+)$",						"Single base" }, 
		{ "^([0-9]+)\\.\\.([0-9]+)$",		"Range including start and end. " }, 
		{ "^<([0-9]+)\\.\\.([0-9]+)$",		"Range with a 'before start' indication" }, 
		{ "^([0-9]+)\\.\\.>([0-9]+)$",		"Range with a 'after end' indication" }, 
		{ "^([0-9]+)\\.([0-9]+)$",			"Exact location unknow but between" }, 
		{ "^([0-9]+)\\^([0-9]+)$",			"Exact location between two bases" }, 
		{ "^join\\((.*?)(,(.*?))+\\)$",		"To make a contiguous (generic)"  },
		{ "^complement\\((.*?)\\)$",		"Make complement (generic)"  },
		{ "^order\\((.*?)(,(.*?))+\\)$",	"Found in this order but not joined (generic)"  },
		{ "^(.*?):(.*?)$",					"Pointer to a part of another entry"  }, 
		/** { "^join\\(([0-9]+)\\.\\.([0-9]+),([0-9]+)\\.\\.([0-9]+)\\)$",		"To make a contig." }, */
		/** { "^complement\\(([0-9]+)\\.\\.([0-9]+)\\)$",						"Make complement"  }, */
	};
	/** Global indication of position. */ 
	private String positionning;
	/** Feature Definition associated */
	private FeatureDefinition definition;
	/** All qualifiers / data for this Feature instance. */
	private HashMap<String, String> qualifiers;
	
	/**
	 * Main Constructor. 
	 * @param fd (FeatureDefinition) NOT 'null'
	 * @param pos (String)
	 */
	public Feature(FeatureDefinition fd, String pos) { 
		this.positionning	= pos;
		this.definition		= fd;
		this.qualifiers		= new HashMap<String, String>();
	}
	
	public boolean hasDefinition(FeatureDefinition fd) 
		{ return this.definition.equals(fd); }
	
	public void addQualifier(String qualifier, String value) {
		if (!this.qualifiers.containsKey(qualifier)) 
			{ this.qualifiers.put(qualifier, value); }
		else {
			int i = 0;
			this.qualifiers.put(qualifier+""+i, this.qualifiers.get(qualifier));
			while (this.qualifiers.containsKey(qualifier+""+i))
				{ i++; }
			this.qualifiers.put(qualifier+""+i, value);
		}
	}

	public String get(String qualifier) 
		{ return ((this.qualifiers.get(qualifier) != null)
				?this.qualifiers.get(qualifier):""); }

	public boolean isCorrect()
		{ return this.definition.check(this); }
	
	
	/** @deprecated Use another toString*() !! */
	public String toString() { return this.toStringGeneBank(); }
	
	public String toStringEMBL() {
		String toReturn = new String("");
		
		String featureKey	= Sequence.completeDataWith
			(this.definition.getFeatureKey(), ' ', false, 16);
		String startLine	= "FT   "+Sequence.completeDataWith("", ' ', false, 16);
		toReturn += "FT   "+featureKey+this.positionning+"\n";
		/** toReturn += "FT   "+whiteLine; */
		String[] keySet = this.qualifiers.keySet().toArray(new String[0]);
		for (int i = 0 ; i < keySet.length ; i++) {
			if (keySet[i].equals("translation")) {
				int firstCut		= 44; /** Those two (2) to make 80-chars line. */
				int lengthToTake	= 80 - startLine.length() - 1;
				String translationContent = this.qualifiers.get(keySet[i]);
				toReturn += startLine+"/"+keySet[i]
				        		+"=\""+translationContent.substring(0, firstCut)+"\n";
				
				toReturn += Sequence.sequenceFormatting(translationContent, lengthToTake, 
														startLine, firstCut)+"\"\n";
			} else 
				{ toReturn += startLine+"/"+keySet[i]
				            +"=\""+this.qualifiers.get(keySet[i])+"\"\n"; }
		}
		return toReturn;
	}
	
	public String toStringGeneBank() {
		String toReturn = new String("");
		
		/** First line : name / key of feature and positionning. */
		toReturn += Feature.completeFeatureKey(this.definition.getFeatureKey());
		toReturn += this.positionning+"\n";
		
		/** Other lines : qualifiers. */
		String whiteStarter = Feature.completeFeatureKey("");
		String[] keySet = this.qualifiers.keySet().toArray(new String[0]);
		for (int i = 0 ; i < keySet.length ; i++) {
			if (keySet[i].equals("translation")) {
				int firstCut		= 44; /** Those two (2) to make 80-chars line. */
				int lengthToTake	= 80 - whiteStarter.length() - 1;
				String translationContent = this.qualifiers.get(keySet[i]);
				firstCut = (translationContent.length() < firstCut)
							?translationContent.length():firstCut;
				toReturn += whiteStarter+"/"+keySet[i]
				        		+"=\""+translationContent.substring(0, firstCut);
				if (translationContent.length() == firstCut) 
					{ toReturn += "\"\n"; } /** special case : add directly the double quote. */
				else { toReturn += "\n"+Sequence.sequenceFormatting(translationContent, 
											lengthToTake, whiteStarter, firstCut)+"\"\n"; }
			} else 
				{ toReturn += whiteStarter+"/"+keySet[i]
				            +"=\""+this.qualifiers.get(keySet[i])+"\"\n"; }
		}
		
		return toReturn;
	}
	
	/** 
	 * From a Feature Key (in FeatureDefinition instance), 
	 * <br>builds a 21-chars String, 
	 * <br>starting with five (5) spaces and completed with spaces. 
	 * @param featureKey (String)
	 * @return (String)
	 * @see FeatureDefinition#getFeatureKey()
	 */
	private static String completeFeatureKey(String featureKey) {
		String toReturn = "     "+featureKey;
		toReturn = Sequence.completeDataWith(toReturn, ' ', false, 21);
		return toReturn;
	}
	
}
