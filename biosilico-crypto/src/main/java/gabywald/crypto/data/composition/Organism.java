package gabywald.crypto.data.composition;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2011-2012)
 * @see gabywald.crypto.data.GenBankFormat
 * @see gabywald.crypto.data.EmblFormat
 */
public class Organism {
	/** Some main attribute... */
	private String sourceName, organismName;
	/** Lineage of the organism. */
	private List<String> taxonomy;
	/** Organelle (facultative). */
	private String organelle;

	/**
	 * Main Constructor. 
	 * @param source (String) Source name of the oragnism. 
	 */
	public Organism(String source) {
		this.sourceName		= source;
		this.organismName	= "";
		this.taxonomy		= new ArrayList<String>();
		this.organelle		= null;
	}
	
	public String getSourceName() { return this.sourceName; }
	public String getOrganiName() { return this.organismName; }
	
	public void setOrganism(String name) 
		{ this.organismName = name; }
	
	public void setOrganelle(String organ) 
		{ this.organelle = organ; }
	
	public void addLineage(String item) 
		{ this.taxonomy.add(item); }
	
	private static String processLineage(List<String> taxonomy, boolean geneBank) {
		int cutter			= ((geneBank)?ComposUtils.GENBANK_CUTTER:ComposUtils.EMBL_CUTTER);
		String separator	= ComposUtils.TAXONOMY_SEPARATOR;
		String toReturn		= ((geneBank)?ComposUtils.BEGIN_EMPTY_LINE:"OC   ");
		if (taxonomy.size() == 1) 
			{ toReturn += taxonomy.get(0)+".\n"; }
		else {
			String preprocessing = new String("");
			for (int i = 0 ; i < taxonomy.size() ; i++) {
				String currentAuth = taxonomy.get(i);
				if (i < taxonomy.size()-1)
					{ preprocessing += currentAuth+separator; }
				else { preprocessing += currentAuth+"."; }
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
						{ append01 += preprocTable[max]+separator; }
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
					if (max == preprocTable.length) /** To remove a separator... */
						{ toAdd = toAdd.substring(0, toAdd.length()-1); }
					if (counter == 0)	{ toReturn += toAdd+"\n"; }
					else { toReturn += ((geneBank)?ComposUtils.BEGIN_EMPTY_LINE:"OC   ")
											+toAdd+"\n"; }
					
					prevMax = max;
					
					counter++;
				} /** END 'while (max < preprocTable.length)' */
			} else { toReturn += preprocessing+"\n"; }
		} /** END ELSE of 'if (this.authors.size() == 1)'. */
		
		return toReturn;
	}
	
	/** @deprecated Use another toString*() !! */
	public String toString() { return this.toStringGeneBank(); }
	
	public String toStringEMBL() {
		String toReturn = new String("");
		toReturn  = "OS   "+this.sourceName+"\n";
		toReturn += Organism.processLineage(this.taxonomy, false);
		if (this.organelle != null) 
			{ toReturn += "OG   "+this.organelle+"\n"; }
		return toReturn;
	}
	
	public String toStringGeneBank() {
		String toReturn = new String("");
		toReturn  = ComposUtils.GENBANK_TAG_SOURCE		+this.sourceName+"\n";
		toReturn += ComposUtils.GENBANK_TAG_ORGANISM	+this.organismName+"\n";
		
		toReturn += Organism.processLineage(this.taxonomy, true);
		
		return toReturn;
	}
	
}
