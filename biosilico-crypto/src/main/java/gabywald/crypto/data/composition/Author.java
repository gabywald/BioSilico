package gabywald.crypto.data.composition;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2011)
 * @see gabywald.crypto.data.GenBankFormat
 * @see gabywald.crypto.data.EmblFormat
 */
public class Author {
	/** Some attributes... */
	private String name, firstNames;
	
	/**
	 * Main Constructor. 
	 * @param name (String)
	 * @param firstNames (String) First names or first capital letters. 
	 */
	public Author(String name, String firstNames) {
		this.name		= name;
		this.firstNames	= firstNames;
	}

	/** Returning in Pattern "^&lt;name&gt;, &lt;first names&gt;$". */
	public String toString() 
		{ return this.name+", "+this.firstNames; }
	
	/** Returning in Pattern "^&lt;name&gt;,&lt;first names&gt;$". */
	public String toStringBrev() 
		{ return this.name+","+this.firstNames; }
	
	/** Returning in Pattern "^&lt;name&gt; &lt;first names&gt;$". */
	public String toStringSpace() 
		{ return this.name+" "+this.firstNames; }
	
	
	/**
	 * To parse authors in given String, parsing different if Genbank or EMBL...
	 * @param multiple (String)
	 * @param genBank (boolean) 'true' for GenBank, false for EMBL. 
	 * @return (List&lt;Author&gt;)
	 */
	public static List<Author> parseAuthors(String multiple, boolean genBank) {
		List<Author> toReturn	= null;
		// String authorsSeparBy	= ((genBank)?ComposUtils.AUTHOR_SEPARATOR_COMMA:" ");
		String[] composition	= multiple.split(ComposUtils.AUTHOR_SEPARATOR_COMMA);
		String sepNameFirst		= ((genBank)?",":" ");
		String splitterAND		= ComposUtils.AUTHOR_SEPARATOR_AND+"?";
		
		if (composition.length == 1) {
			String[] moreComposition = composition[0].split(splitterAND);
			if (moreComposition.length == 1) {
				toReturn		= new ArrayList<Author>(1);
				String[] names	= multiple.split(sepNameFirst);
				// if (names.length > 1) /** XXX !? ?! */ { ; }
				if ( (!genBank) && (names[1].endsWith(";")) ) 
					{ names[1] = names[1].substring(0, names[1].length()-1); }
				toReturn.add(new Author(names[0], names[1]));
				return toReturn;
			} else { /** there are two (2) authors !! */
				int capacity	= 2;
				toReturn		= new ArrayList<Author>(capacity);
				for (int i = 0 ; i < capacity ; i++) {
					String[] names	= moreComposition[i].split(sepNameFirst);
					toReturn.add(new Author(names[0], names[1]));
				}
				return toReturn;
			}
		}
		
		toReturn	= new ArrayList<Author>(composition.length+1);
		for (int i = 0 ; i < composition.length ; i++) {
			if (i < (composition.length-1) ) {
				String[] names	= composition[i].split(sepNameFirst);
				toReturn.add(new Author(names[0], names[1]));
			} else {
				String[] moreComposition = composition[i].split(splitterAND);
				/** Table length is 2 here !! */
				for (int j = 0 ; j < moreComposition.length ; j++) {
					String[] names	= moreComposition[j].split(sepNameFirst);
					// if (names.length > 1) /** XXX !? ?! */ { ; }
					if ( (!genBank) && (names[1].endsWith(";")) ) 
						{ names[1] = names[1].substring(0, names[1].length()-1); }
					toReturn.add(new Author(names[0], names[1]));
				}
			}
		}
		return toReturn;
	}
}
