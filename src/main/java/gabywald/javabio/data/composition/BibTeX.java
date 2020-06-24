package gabywald.javabio.data.composition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BibTeX {
	public static final String[][] AVAILABLE_FIELDS = {
		{ "address", "Publisher's address (usually just the city, but can be the full address for lesser-known publishers)" }, 
		{ "annote", "An annotation for annotated bibliography styles (not typical)" }, 
		{ "author", "The name(s) of the author(s) (in the case of more than one author, separated by and)" }, 
		{ "booktitle", "The title of the book, if only part of it is being cited" }, 
		{ "chapter", "The chapter number" }, 
		{ "crossref", "The key of the cross-referenced entry" }, 
		{ "edition", "The edition of a book, long form (such as \"first\" or \"second\")" }, 
		{ "editor", "The name(s) of the editor(s)" }, 
		{ "eprint", "A specification of an electronic publication, often a preprint or a technical report" }, 
		{ "howpublished", "How it was published, if the publishing method is nonstandard" }, 
		{ "institution", "The institution that was involved in the publishing, but not necessarily the publisher" }, 
		{ "journal", "The journal or magazine the work was published in" }, 
		{ "key", "A hidden field used for specifying or overriding the alphabetical order of entries (when the \"author\" and \"editor\" fields are missing). Note that this is very different from the key (mentioned just after this list) that is used to cite or cross-reference the entry." }, 
		{ "month", "The month of publication (or, if unpublished, the month of creation)" }, 
		{ "note", "Miscellaneous extra information" }, 
		{ "number", "The \"(issue) number\" of a journal, magazine, or tech-report, if applicable. (Most publications have a \"volume\", but no \"number\" field.)" }, 
		{ "organization", "The conference sponsor" }, 
		{ "pages", "Page numbers, separated either by commas or double-hyphens." }, 
		{ "publisher", "The publisher's name" }, 
		{ "school", "The school where the thesis was written" }, 
		{ "series", "The series of books the book was published in (e.g. \"The Hardy Boys\" or \"Lecture Notes in Computer Science\")" }, 
		{ "title", "The title of the work" }, 
		{ "type", "The type of tech-report, for example, \"Research Note\"" }, 
		{ "url", "The WWW address" }, 
		{ "volume", "The volume of a journal or multi-volume book" }, 
		{ "year", "The year of publication (or, if unpublished, the year of creation)" }
	};
	public static final String[][] MAIN_TYPE_FIELDS = {
		{ "article", "An article from a journal or magazine.", 
			"author, title, journal, year, volume, number, pages, month, note, key, howpublished" }, 
		{ "book", "A book with an explicit publisher.", 
			"author/editor, title, publisher, year, volume, series, address, edition, month, note, key" }, 
		{ "booklet", "A work that is printed and bound, but without a named publisher or sponsoring institution.", 
			"title, author, howpublished, address, month, year, note, key" }, 
		{ "conference", "The same as inproceedings, included for Scribe compatibility.", 
			"author, title, booktitle, year, editor, pages, organization, publisher, address, month, note, key" }, 
		{ "inbook", "A part of a book, usually untitled. May be a chapter (or section or whatever) and/or a range of pages.", 
			"author/editor, title, chapter/pages, publisher, year, volume, series, address, edition, month, note, key" }, 
		{ "incollection", "A part of a book having its own title.", 
			"author, title, booktitle, year, editor, pages, organization, publisher, address, month, note, key" }, 
		{ "inproceedings", "An article in a conference proceedings.", 
			"author, title, booktitle, year, editor, series, pages, organization, publisher, address, month, note, key" }, 
		{ "manual", "Technical documentation.", 
			"title, author, organization, address, edition, month, year, note, key" }, 
		{ "mastersthesis", "A Master's thesis.", 
			"author, title, school, year, address, month, note, key" }, 
		{ "misc", "For use when nothing else fits.", 
			"author, title, howpublished, month, year, note, key" }, 
		{ "phdthesis", "A Ph.D. thesis.", 
			"author, title, school, year, address, month, note, key" }, 
		{ "proceedings", "The proceedings of a conference.", 
			"title, year, editor, publisher, organization, address, month, note, key" }, 
		{ "techreport", "A report published by a school or other institution, usually numbered within a series.", 
			"author, title, institution, year, type, number, address, month, note, key" }, 
	};
	public static final boolean[][] MANDATORY_FIELD_BY_TYPE = {
		{ true, true, true, true, true, false, true, false, false, false, false }, 
		{ true, true, true, true, false, false, false, false, false, false, false }, 
		{ true, false, false, false, false, false, false, false }, 
		{ true, true, true, true, false, false, false, false, false, false, false, false }, 
		{ true, true, true, true, true, false, false, false, false, false, false, false }, 
		{ true, true, true, true, false, false, false, false, false, false, false, false }, 
		{ true, true, true, true, false, false, false, false, false, false, false, false, false }, 
		{ true, false, false, false, false, false, false, false, false }, 
		{ true, true, true, true, false, false, false, false }, 
		{ false, false, false, false, false, false, false }, 
		{ true, true, true, true, false, false, false, false }, 
		{ true, true, false, false, false, false, false, false, false }, 
		{ true, true, true, true, false, false, false, false, false, false }, 
	};
	
	private int type;
	private HashMap<String, String> content;

	public BibTeX(String type) {
		this.type = -1;
		type = type.toLowerCase();
		for (int i = 0 ; (i < BibTeX.MAIN_TYPE_FIELDS.length) 
							&& (this.type == -1) ; i++) {
			if (type.equals(BibTeX.MAIN_TYPE_FIELDS[i][0]))
				{ this.type = i; }
		}
		if (this.type == -1) { this.type = 9; } /** 'misc' by default !! */
		this.content = new HashMap<String, String>(BibTeX.MANDATORY_FIELD_BY_TYPE[this.type].length);
		boolean[] mandatTable	= BibTeX.MANDATORY_FIELD_BY_TYPE[this.type];
		String[] fieldsTable	= BibTeX.MAIN_TYPE_FIELDS[this.type][2].split(", ");
		for (int i = 0 ; i < fieldsTable.length ; i++) 
			{ this.content.put(fieldsTable[i], ((mandatTable[i])?"":null) ); }
		
		this.content.put("abstract", "" );
		this.content.put("review", "" );
		this.content.put("uniqueiden", "uniqueiden");
	}
	
	public String getUniqueIden()	{ return this.content.get("uniqueiden"); }
	public String setUniqueIden(String uniqueIden) 
		{ return this.content.put("uniqueiden", uniqueIden); }
	
	public String getAddress()		{ return this.content.get("address"); }
	public String getAnnote()		{ return this.content.get("annote"); }
	public String getAuthor()		{ return this.content.get("author"); }
	public String getBooktitle()	{ return this.content.get("booktitle"); }
	public String getChapter()		{ return this.content.get("chapter"); }
	public String getCrossref()		{ return this.content.get("crossref"); }
	public String getEdition()		{ return this.content.get("edition"); }
	public String getEditor()		{ return this.content.get("editor"); }
	public String getEprint()		{ return this.content.get("eprint"); }
	public String getHowpublished()	{ return this.content.get("howpublished"); }
	public String getInstitution()	{ return this.content.get("institution"); }
	public String getJournal()		{ return this.content.get("journal"); }
	public String getKey()			{ return this.content.get("key"); }
	public String getMonth()		{ return this.content.get("month"); }
	public String getNote()			{ return this.content.get("note"); }
	public String getNumber()		{ return this.content.get("number"); }
	public String getOrganization()	{ return this.content.get("organization"); }
	public String getPages()		{ return this.content.get("pages"); }
	public String getPublisher()	{ return this.content.get("publisher"); }
	public String getSchool()		{ return this.content.get("school"); }
	public String getSeries()		{ return this.content.get("series"); }
	public String getTitle()		{ return this.content.get("title"); }
	public String getType()			{ return this.content.get("type"); }
	public String getUrl()			{ return this.content.get("url"); }
	public String getVolume()		{ return this.content.get("volume"); }
	public String getYear()			{ return this.content.get("year"); }

	public void setAddress(String address)
		{ this.content.put("address", address); } 
	public void setAnnote(String annote)
		{ this.content.put("annote", annote); } 
	public void setAuthor(String author)
		{ this.content.put("author", author); } 
	public void setBooktitle(String booktitle)
		{ this.content.put("booktitle", booktitle); } 
	public void setChapter(String chapter)
		{ this.content.put("chapter", chapter); } 
	public void setCrossref(String crossref)
		{ this.content.put("crossref", crossref); } 
	public void setEdition(String edition)
		{ this.content.put("edition", edition); } 
	public void setEditor(String editor)
		{ this.content.put("editor", editor); } 
	public void setEprint(String eprint)
		{ this.content.put("eprint", eprint); } 
	public void setHowpublished(String howpublished)
		{ this.content.put("howpublished", howpublished); } 
	public void setInstitution(String institution)
		{ this.content.put("institution", institution); } 
	public void setJournal(String journal)
		{ this.content.put("journal", journal); } 
	public void setKey(String key)
		{ this.content.put("key", key); } 
	public void setMonth(String month)
		{ this.content.put("month", month); } 
	public void setNote(String note)
		{ this.content.put("note", note); } 
	public void setNumber(String number)
		{ this.content.put("number", number); } 
	public void setOrganization(String organization)
		{ this.content.put("organization", organization); } 
	public void setPages(String pages)
		{ this.content.put("pages", pages); } 
	public void setPublisher(String publisher)
		{ this.content.put("publisher", publisher); } 
	public void setSchool(String school)
		{ this.content.put("school", school); } 
	public void setSeries(String series)
		{ this.content.put("series", series); } 
	public void setTitle(String title)
		{ this.content.put("title", title); } 
	public void setType(String type)
		{ this.content.put("type", type); } 
	public void setUrl(String url)
		{ this.content.put("url", url); } 
	public void setVolume(String volume)
		{ this.content.put("volume", volume); } 
	public void setYear(String year)
		{ this.content.put("year", year); } 
	
	public String getAbstract()	{ return this.content.get("abstract"); }
	public String getReview()	{ return this.content.get("review"); }
	
	public void setAbstract(String abstr)
		{ this.content.put("abstract", abstr); }
	public void setReview(String review)
		{ this.content.put("review", review); }
	
	public String toStringEverything() {
		String toReturn = new String("");
		
		toReturn += "@"+BibTeX.MAIN_TYPE_FIELDS[this.type][0]+"{"
							+this.getUniqueIden()+", \n";

		String[] fields = this.content.keySet().toArray(new String[0]);
		
		for (int i = 0 ; i < fields.length ; i++) {
			String field = fields[i];
			if (this.content.get(field) == null)
				{ this.content.put(field, ""); }
			if ( (this.content.get(field) != null) 
					&& (!this.content.get(field).equals("")) ) { 
				toReturn += field
				       +" = {"+this.content.get(field)+"}, \n";
			}
		}
		
		return toReturn.substring(0, toReturn.length()-3)+"\n}\n";
	}
	
	public String toString() {
		String toReturn = new String("");
		
		toReturn += "@"+BibTeX.MAIN_TYPE_FIELDS[this.type][0]+"{"
							+this.getUniqueIden()+", \n";
		boolean[] mandatTable	= BibTeX.MANDATORY_FIELD_BY_TYPE[this.type];
		String[] fieldsTable	= BibTeX.MAIN_TYPE_FIELDS[this.type][2].split(", ");
		for (int i = 0 ; i < fieldsTable.length ; i++) {
			if ( (mandatTable[i]) && (this.content.get(fieldsTable[i]) == null) ) 
				{ this.content.put(fieldsTable[i], ""); }
			else if ( (!mandatTable[i]) && (this.content.get(fieldsTable[i]) == null) )
				{ /** Really nothing if not mandatory... */; }
			if ( (this.content.get(fieldsTable[i]) != null) 
					&& (!this.content.get(fieldsTable[i]).equals("")) )
				{ toReturn += fieldsTable[i]
				       +" = {"+this.content.get(fieldsTable[i])+"}, \n"; }
		}
		// toReturn = toReturn.substring(0, toReturn.length()-3;
		// toReturn += "\n}\n\n";
		
		toReturn += ( ( (this.content.get("url") != null) 
				&& (!this.content.get("url").equals("") ) ) )
					?"url"+" = {"+this.content.get("url")+"}, \n":""; /** !! */
		
		toReturn += "abstract"+" = {"+this.content.get("abstract")
						+"}";
		toReturn += ( ( (this.content.get("review") != null) 
					&& (!this.content.get("review").equals("") )
						?", ":"") )+"\n";
		
		toReturn += ( ( (this.content.get("review") != null) 
					&& (!this.content.get("review").equals("") ) ) )
						?"review"+" = {"+this.content.get("review")+"}\n":""; /** !! */
		toReturn += "}\n\n";
		
		return toReturn;
	}
	
	public static List<BibTeX> fromString(String content) {
		String[] cont			= content.split("\n");
		List<BibTeX> toReturn	= new ArrayList<BibTeX>();
		
		Pattern publicaType = Pattern.compile("^@(.*?)\\{(.*?),(.*)$");
		Pattern cont001Type	= Pattern.compile("^\\s+(.*?)\\s*=\\s*\\{(.*?)(\\}),?(.*)$");
		Pattern cont002Type	= Pattern.compile("^\\s+(.*?)\\s*=\\s*\\{(.*?)\\s*$");
		Pattern currentType	= Pattern.compile("^(.*?)\\s*$"); // "^\\s+(.*?)\\s*$"
		Pattern endContType = Pattern.compile("^(.*?)\\},?\\s*$"); // "^\\s+(.*?)(\\}),?\\s*$"
		
		BibTeX bibRef		= null;
		String currentField	= null;
		
		for (int i = 0 ; i < cont.length ; i++) {
			Matcher publMatch = publicaType.matcher(cont[i]);
			Matcher con1Match = cont001Type.matcher(cont[i]);
			Matcher con2Match = cont002Type.matcher(cont[i]);
			Matcher currMatch = currentType.matcher(cont[i]);
			Matcher endcMatch = endContType.matcher(cont[i]);
			if (publMatch.matches()) {
				String type = publMatch.group(1);
				String iden = publMatch.group(2);
				bibRef = new BibTeX(type);
				bibRef.setUniqueIden(iden);
			} else if ( (con1Match.matches()) && (currentField == null) ) {
				/** New SMALL field detected, and previous is parsed. */
				String fieldType	= con1Match.group(1);
				String fieldCont	= con1Match.group(2);
				bibRef.content.put(fieldType, fieldCont);
			} else if ( (con1Match.matches()) && (currentField != null) ) {
				/** New SMALL field detected, and previous is NOT parsed. */
				System.out.println("\tERROR (a) ON '"+currentField+"' not finished !!");
				currentField		= null;
				String fieldType	= con1Match.group(1);
				String fieldCont	= con1Match.group(2);
				bibRef.content.put(fieldType, fieldCont);
			} 
			else if ( (con2Match.matches()) && (currentField == null) ) {
				/** New LARGE field detected, and previous is parsed. */
				String fieldType	= con2Match.group(1);
				String fieldCont	= con2Match.group(2);
				currentField		= fieldType;
				bibRef.content.put(fieldType, fieldCont);
			} 
			else if ( (con2Match.matches()) && (currentField != null) ) {
				/** New LARGE field detected, and previous is NOT parsed. */
				System.out.println("\tERROR (b) ON '"+currentField+"' not finished !!");
				currentField		= null;
				String fieldType	= con2Match.group(1);
				String fieldCont	= con2Match.group(2);
				currentField		= fieldType;
				bibRef.content.put(fieldType, fieldCont);
			} else if ( (endcMatch.matches()) && (currentField != null) ) {
				/** Following content AND END of current field... */
				String fieldCont	= endcMatch.group(1);
				bibRef.content.put(currentField, 
						bibRef.content.get(currentField)+"\n"+fieldCont);
				currentField		= null;
			} else if ( (currMatch.matches()) && (currentField != null) ) {
				/** Following content of current field... */
				String fieldCont	= currMatch.group(1);
				if (!fieldCont.matches("^\\s*$")) 
					{ bibRef.content.put(currentField, 
						bibRef.content.get(currentField)+"\n"+fieldCont); }
			}  
			else if (cont[i].matches("^\\}$")) {
				toReturn.add(bibRef);
				System.out.println(bibRef+"***"+toReturn.size()+"***");
				bibRef = null;
			} 
			else { System.out.println(cont[i]+"\t'"+currentField+"'"); }
		}
		
		
		System.out.println(toReturn.size());
		
		return toReturn;
	}
	
//	public static BibTeX fromMedLine(PubMedElement pmelt) {
//		
//		String publicationType	= pmelt.getPublicationType();
//		String choosenType		= null;
//		int selectedType		= -1;
//		if ( (publicationType.equals("Classical Article")) 
//				|| (publicationType.equals("Introductory Journal Article"))
//				|| (publicationType.equals("Introductory Journal Article")) 
//				|| (publicationType.equals("Journal Article")) 
//				|| (publicationType.equals("JOURNAL ARTICLE"))
//				|| (publicationType.equals("Newspaper Article"))
//				|| (publicationType.equals("Review"))
//				|| (publicationType.equals("Research Support, Non-U.S. Gov't"))
//				)
//			{ selectedType = 0; }
//		
//		if (selectedType == -1 ) {
//			choosenType		= "misc";
//			selectedType	= 9;
//			System.out.println("\tBibTeX misc : '"+publicationType+"'");
//		} else 
//			{ choosenType	= BibTeX.MAIN_TYPE_FIELDS[selectedType][0]; }
//		
//		BibTeX bibref	= new BibTeX(choosenType);
//		
//		/** Getting values form fields : depends from type... */
//		String[] fields	= BibTeX.MAIN_TYPE_FIELDS[selectedType][2].split(", ");
//		for (int i = 0 ; i < fields.length ; i++) {
//			String toPutInField = BibTeX.getFieldFromMedLine
//				(pmelt, fields[i], BibTeX.MANDATORY_FIELD_BY_TYPE[selectedType][i]);
//			if ( ( (toPutInField == null) || (toPutInField.equals("")) ) 
//					&& (BibTeX.MANDATORY_FIELD_BY_TYPE[selectedType][i]) ) 
//				{ toPutInField = " -- "; /** "<empty-noData>"; */ }
//			bibref.content.put(fields[i], toPutInField);
//		}
//		
//		bibref.setUniqueIden(pmelt.getPubMedUniqueIdentifier());
//		bibref.content.put("url", BibTeX.getFieldFromMedLine(pmelt, "url", true) );
//		bibref.content.put("abstract", (pmelt.getAbstract() != null)?pmelt.getAbstract():"");
//		// bibref.content.put("review", (pmelt.getAbstract() != null)?pmelt.getAbstract():"");
//		
//		return bibref;
//	}
//	
//	public static String getFieldFromMedLine(PubMedElement pmelt, 
//											 String bibTexFieldName, 
//											 boolean mandatory) {
//		if (bibTexFieldName.equals("address") )		{
//			if (pmelt.getAffiliation() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getAffiliation(); }
//		}
//		if (bibTexFieldName.equals("annote") )		{ ; } /** ?? */
//		if (bibTexFieldName.equals("author") )		{
//			if (pmelt.getAuthor() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getAuthor(); }
//		}
//		if (bibTexFieldName.equals("booktitle") )	{
//			if (pmelt.getBookTitle() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getBookTitle(); }
//		}
//		if (bibTexFieldName.equals("chapter") )		{ ; } /** ?? see book and chapter. !! */
//		if (bibTexFieldName.equals("crossref") )	{ ; } /** ?? */
//		if (bibTexFieldName.equals("edition") )		{ ; } /** ?? see book and chapter. !! */
//		if (bibTexFieldName.equals("editor") )		{ ; } /** ?? see book and chapter. !! */
//		if (bibTexFieldName.equals("eprint") )		{
//			if (pmelt.getDateOfElectronicPublication() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getDateOfElectronicPublication(); }
//		} 
//		if (bibTexFieldName.equals("howpublished") ){
//			if (pmelt.getPublicationType() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getPublicationType(); }
//		} 
//		if (bibTexFieldName.equals("institution") )	{
//			if (pmelt.getAffiliation() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getAffiliation(); }
//		} 
//		if (bibTexFieldName.equals("journal") )		{
//			if (pmelt.getJournalTitle() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getJournalTitle(); }
//		}
//		if (bibTexFieldName.equals("key") )			{ ; } /** !? */
//		if (bibTexFieldName.equals("month") )		{
//			String analysis = new String("");
//			if (pmelt.getDateOfPublication() == null) 
//				{ return (mandatory?"":null); }
//			else { analysis = pmelt.getDateOfPublication(); }
//			if (analysis.length() < 5) { return "noMonth"; }
//			// else { System.out.println("\t'"+analysis.substring(5, 8)+"'"); }
//			if (analysis.substring(5, 8).equals("Jan")) { return "January"; }
//			if (analysis.substring(5, 8).equals("Feb")) { return "February"; }
//			if (analysis.substring(5, 8).equals("Mar")) { return "March"; }
//			if (analysis.substring(5, 8).equals("Apr")) { return "April"; }
//			if (analysis.substring(5, 8).equals("May")) { return "May"; }
//			if (analysis.substring(5, 8).equals("Jun")) { return "June"; }
//			if (analysis.substring(5, 8).equals("Jul")) { return "July"; }
//			if (analysis.substring(5, 8).equals("Aug")) { return "August"; }
//			if (analysis.substring(5, 8).equals("Sep")) { return "September"; }
//			if (analysis.substring(5, 8).equals("Oct")) { return "October"; }
//			if (analysis.substring(5, 8).equals("Nov")) { return "November"; }
//			if (analysis.substring(5, 8).equals("Dec")) { return "December"; }
//		} /** date !! */
//		if (bibTexFieldName.equals("note") )		{
//			if (pmelt.getGeneralNote() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getGeneralNote(); }
//		} 
//		if (bibTexFieldName.equals("number") )		{
//			if (pmelt.getIssue() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getIssue(); }
//		} 
//		if (bibTexFieldName.equals("organization") ){
//			String owner = new String("");
//			if (pmelt.getOwner() == null)			{ ; }
//			else { owner += pmelt.getOwner(); }
//			if (pmelt.getOtherTermOwner() == null)	{ ; }
//			else { owner += ((owner.length() > 0)?" -- ":"")
//								+pmelt.getOtherTermOwner(); }
//			if (pmelt.getGrantNumber() == null)		{ ; }
//			else { owner += ((owner.length() > 0)?" -- ":"")
//								+pmelt.getGrantNumber(); }
//			return ( (owner.length() > 0)?
//					owner:(mandatory?"":null) );
//		} 
//		if (bibTexFieldName.equals("pages") )		{
//			if (pmelt.getPagination() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getPagination(); }
//		} 
//		if (bibTexFieldName.equals("publisher") )	{ ; } /** ?? see book and chapter. !! */
//		if (bibTexFieldName.equals("school") )		{ ; } /** !? */
//		if (bibTexFieldName.equals("series") )		{ ; } /** ?? */
//		if (bibTexFieldName.equals("title") )		{
//			if (pmelt.getTitle() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getTitle(); }
//		} 
//		if (bibTexFieldName.equals("type") )		{ ; } 
//		if (bibTexFieldName.equals("url") )			{
//			if (pmelt.getPubMedUniqueIdentifier() == null) 
//				{ return (mandatory?"":null); }
//			else { return "http://www.ncbi.nlm.nih.gov/pubmed/"
//							+pmelt.getPubMedUniqueIdentifier(); }
//		} 
//		if (bibTexFieldName.equals("volume") )		{	  /** !! Volume title for books !! */
//			if (pmelt.getVolume() == null) 
//				{ return (mandatory?"":null); }
//			else { return pmelt.getVolume(); }
//		} 
//		if (bibTexFieldName.equals("year") )		{
//			String analysis = new String("");
//			if (pmelt.getDateOfPublication() == null) 
//				{ return (mandatory?"":null); }
//			else { analysis = pmelt.getDateOfPublication(); }
//			return analysis.substring(0, 4);
//		} /** date !! */
//		return (mandatory?"":null);
//	}
	

	
}
