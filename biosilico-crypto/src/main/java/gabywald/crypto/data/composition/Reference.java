package gabywald.crypto.data.composition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2011)
 * @see gabywald.crypto.data.GenBank
 * @see gabywald.crypto.data.Embl
 * @see Author
 */
public class Reference {
	private static final String PUBMED_HASH_KEY = "PUBMED", 
								MEDLIN_HASH_KEY = "MEDLINE";
	/** Some useful indication attribute [number, startBase, endBase]. */
	private int[] numbers;
	/** Some other useful indication attribute [title, journal, remark]. */
	private String[] someDatas;
	/** Cross-References (id's of databases like PudMed, MedLine, DOI, AGRICOLA... */
	private HashMap<String, String> crossRefs;
	/** Set of authors. */
	private List<Author> authors;
	/** Set of authors. */
	private List<String> groups;
	/** Set of location data. */
	private List<String> locations;
	
	/**
	 * Main Constructor. 
	 * @param num (int) Number of the reference into the set. 
	 * @param start (int) Start point concerned in the sequence described. 
	 * @param end (int) End point concerned in the sequence described. 
	 */
	public Reference(int num, int start, int end) {
		this.numbers	= new int[3];
		this.numbers[0]	= num;
		this.numbers[1]	= start;
		this.numbers[2]	= end;
		this.someDatas	= new String[3];
		for (int i = 0 ; i < this.someDatas.length ; i++) 
			{ this.someDatas[i] = null; }
		this.crossRefs	= new HashMap<String, String>();
		this.authors	= new ArrayList<Author>();
		this.groups		= new ArrayList<String>();
		this.locations	= new ArrayList<String>();
	}
	
	public void addToTitle(String more) {
		if (this.someDatas[0] != null) 
			{ this.someDatas[0] += more+"\n"; }
		else { this.someDatas[0] = more; }
	}
	
	public void setStartStop(int start, int stop)
		{ this.numbers[1] = start;this.numbers[2] = stop; }
	
	public void setTitle(String title) 
		{ this.someDatas[0] = title+"\n"; }
	
	public void setJournal(String journal) 
		{ this.someDatas[1] = journal; }
	

	
	public void setRemark(String remark) 
		{ this.someDatas[2] = remark; }
	
	public void setPubmedID(String pubmed) 
		{ this.addCrossReference(Reference.PUBMED_HASH_KEY, pubmed); }
	
	public void setMedline(String medline) 
		{ this.addCrossReference(Reference.MEDLIN_HASH_KEY, medline); }
	
	public void addCrossReference(String database, String iden) 
		{ this.crossRefs.put(database, iden); }
	
	public void addAuthor(Author aut) 
		{ this.authors.add(aut); }
	
	public void addGroup(String group) 
		{ this.groups.add(group); } 
	
	public void addLocation(String location) 
		{ this.locations.add(location); } 
	
	private static String processAuthors(List<Author> authors, boolean geneBank) {
		int cutter			= ((geneBank)?ComposUtils.GENBANK_CUTTER:ComposUtils.EMBL_CUTTER);
		String separator	= ComposUtils.AUTHOR_SEPARATOR_COMMA;
		// ((geneBank)?ComposUtils.AUTHOR_SEPARATOR_COMMA:" "); 
		// ComposUtils.AUTHOR_SEPARATOR_COMMA; 
		// ((geneBank)?ComposUtils.AUTHOR_SEPARATOR_COMMA:" ");
		// ((geneBank)?separator:" ");
		String toReturn		= ((geneBank)?ComposUtils.GENBANK_TAG_AUTHORS:"RA   ");
		if (authors.size() == 1) { 
			toReturn += // authors.get(0).toStringBrev()+"\n";
					((geneBank)?
							authors.get(0).toStringBrev():
							authors.get(0).toStringSpace()+";")+"\n";
		} else {
			String preprocessing = new String("");
			for (int i = 0 ; i < authors.size() ; i++) {
				String currentAuth = ((geneBank)?
							authors.get(i).toStringBrev():
							authors.get(i).toStringSpace());
				if (i < authors.size()-2)
					{ preprocessing += currentAuth+separator; }
				else if (i != authors.size()-1) 
					{ preprocessing += currentAuth; }
				else { preprocessing += 
						((geneBank)?ComposUtils.AUTHOR_SEPARATOR_AND:", ")
						+currentAuth; }
			}
			/** DONE re-cut after if more than 80 chars by 'line'. */
			if (preprocessing.length() > cutter) {
				int max		= 0;
				int prevMax = 0;
				int counter	= 0;
				String[] preprocTable	= preprocessing.split(separator);
				while (max < preprocTable.length) {
					String append01		= new String("");
					String append02		= new String("");
					
					/** Determine <max> append. */
					for ( ; (max < preprocTable.length) 
							&& (append01.length() < cutter) ; max++) 
						{ append01 += preprocTable[max]+
							((max < preprocTable.length-1)?separator:""); }
					if (append01.matches("^(.*?) and (.*?)$")) 
						{ append01 = append01.substring(0, append01.length()-2); }
					if (append01.matches("^(.*?),;$"))
						{ append01 = append01.substring(0, append01.length()-2); }
					/** Use <max>... */
					if (max < preprocTable.length) {
						if (append01.length() <= cutter) { append02 = ""; } 
						else {
							for (int i = prevMax ; i < max-1 ; i++) 
								{ append02 += preprocTable[i]+separator; }
							max--;
						}
					}
					
					String toAdd	= ((append02.equals(""))?append01:append02);
					int indexLast	= toAdd.length()-1;
					if (toAdd.charAt(indexLast) == ' ') 
						{ toAdd = toAdd.substring(0, indexLast); }
					if (toAdd.matches("^(.*?),,(.*?)$")) 
						{ toAdd = toAdd.replaceAll("^(.*?),,(.*?)$", "$1,$2"); }
					/** if (toAdd.matches("^(.*?),$")) 
						{ toAdd = toAdd.replaceAll("^(.*?),$", "$1"); } */
					
					if (counter == 0) { toReturn += toAdd+"\n"; }
					else { toReturn += 
							((geneBank)?ComposUtils.BEGIN_EMPTY_LINE:"RA   ")
							+toAdd+((geneBank)?"":";")+"\n"; }
					
					prevMax = max;
					
					counter++;
				} /** END 'while (max < preprocTable.length)' */
			} else { toReturn += preprocessing+((geneBank)?"":";")+"\n"; }
		} /** END ELSE of 'if (this.authors.size() == 1)'. */
		
		return toReturn;
	}
	
	/** @deprecated Use another toString*() !! */
	public String toString() { return this.toStringGeneBank(); }
	
	public String toStringEMBL() {
		String toReturn = new String("");
		
		/** RN, RC, RP, RX, RG, RA, RT, RL */
		toReturn += "RN   ["+this.numbers[0]+"]\n";
		if (this.someDatas[2] != null) 
			{ toReturn += "RC   "+this.someDatas[2]+"\n"; }
		
		if ( (this.numbers[1] != 0) && (this.numbers[2] != 0) )
				{ toReturn += "RP   "+this.numbers[1]+"-"+this.numbers[2]+"\n"; }
		
		if (this.crossRefs.size() > 0) {
			String[] keySet = this.crossRefs.keySet().toArray(new String[0]);
			for (int i = 0 ; i < keySet.length ; i++) 
				{ toReturn += "RX   "+keySet[i]+"; "
						+this.crossRefs.get(keySet[i])+".\n"; }
		} /** END 'if (this.crossRefs.size() > 0)' */
		
		for (int i = 0 ; i < this.groups.size() ; i++) 
			{ toReturn += "RG   "+this.groups.get(i)+"\n"; }
		
		toReturn += /** RA */Reference.processAuthors(this.authors, false);
		
		String[] partsOfTitle = this.someDatas[0].split("\n");
		if (partsOfTitle.length == 0) { toReturn += "RT   ;\n"; }
		else {
			for (int  i = 0 ; i < partsOfTitle.length ; i++) 
				{ toReturn += "RT   "+partsOfTitle[i]+
						((i == partsOfTitle.length-1)?";":"")+"\n"; }
		} /** END 'if (partsOfTitle.length == 0)' */

		for (int  i = 0 ; i < this.locations.size() ; i++) 
			{ toReturn += "RL   "+this.locations.get(i)+"\n"; }
		
		return toReturn;
	}
	
	public String toStringGeneBank() {
		String toReturn = new String("");
		
		toReturn += ComposUtils.GENBANK_TAG_REFERENCE+this.numbers[0];
		toReturn += ((this.numbers[0] < 100)?" "
						+((this.numbers[0] < 10)?" ":""):"");
		toReturn += "(bases "+this.numbers[1]+" to "+this.numbers[2]+")\n";
		
		toReturn += Reference.processAuthors(this.authors, true);
		
		String[] partsOfTitle = this.someDatas[0].split("\n");
		toReturn += ComposUtils.GENBANK_TAG_TITLE+partsOfTitle[0]+"\n";
		for (int  i = 1 ; i < partsOfTitle.length ; i++) 
			{ toReturn += ComposUtils.BEGIN_EMPTY_LINE
							+partsOfTitle[i]+"\n"; }
		
		toReturn += ComposUtils.GENBANK_TAG_JOURNAL+this.someDatas[1]+"\n";
		if (this.crossRefs.containsKey(Reference.PUBMED_HASH_KEY))
			{ toReturn += ComposUtils.GENBANK_TAG_PUBMED
				+this.crossRefs.get(Reference.PUBMED_HASH_KEY)+"\n"; }
		if (this.someDatas[2] != null) 
			{ toReturn += ComposUtils.GENBANK_TAG_REMARK+this.someDatas[2]+"\n"; }
		if (this.crossRefs.containsKey(Reference.MEDLIN_HASH_KEY))
			{ toReturn += ComposUtils.GENBANK_TAG_MEDLINE
				+this.crossRefs.get(Reference.MEDLIN_HASH_KEY)+"\n"; }
		
		return toReturn;
	}
}
