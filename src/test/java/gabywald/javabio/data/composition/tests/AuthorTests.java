package gabywald.javabio.data.composition.tests;

import java.util.ArrayList;
import java.util.List;

import gabywald.javabio.data.composition.Author;
import gabywald.javabio.data.composition.ComposUtils;

import junit.framework.TestCase;

public class AuthorTests extends TestCase {
	private static final String DATA01 = "Mougey,R.";
	private static final String DATA02 = 
		"Blasko,B., Banlaki,Z., Gyapay,G., Pozsonyi,E., Sasvari-Szekely,M., "
		+"Rajczy,K., Fust,G. and Szilagyi,A.";
	private static final String DATA03 = 
		"Yang,Y., Chung,E.K., Wu,Y.L., Savelli,S.L., Nagaraja,H.N., Zhou,B., "
        +"Hebert,M., Jones,K.N., Shu,Y., Kitzmiller,K., Blanchong,C.A., "
        +"McBride,K.L., Higgins,G.C., Rennebohm,R.M., Rice,R.R., "
        +"Hackshaw,K.V., Roubey,R.A., Grossman,J.M., Tsao,B.P., "
        +"Birmingham,D.J., Rovin,B.H., Hebert,L.A. and Yu,C.Y.";
	private static final String DATA04 = "Fust,G. and Szilagyi,A.";
	
	private static final String DATA11 = "Mougey R.;";
	private static final String DATA12 = 
		"Blasko B., Banlaki Z., Gyapay G., Pozsonyi E., Sasvari-Szekely M., "
		+"Rajczy K., Fust G., Szilagyi A.;";
	private static final String DATA13 = 
		"Yang Y., Chung E.K., Wu Y.L., Savelli S.L., Nagaraja H.N., Zhou B., "
        +"Hebert M., Jones K.N., Shu Y., Kitzmiller K., Blanchong C.A., "
        +"McBride K.L., Higgins G.C., Rennebohm R.M., Rice R.R., "
        +"Hackshaw K.V., Roubey R.A., Grossman J.M., Tsao B.P., "
        +"Birmingham D.J., Rovin B.H., Hebert L.A., Yu C.Y.;";
	private static final String DATA14 = "Fust G., Szilagyi A.;";
	
	
	private static final String RESU01 = 
		 "  AUTHORS   Mougey,R.";
	private static final String RESU02 = 
		 "  AUTHORS   Blasko,B., Banlaki,Z., Gyapay,G., Pozsonyi,E., Sasvari-Szekely,M.,\n"
		+"            Rajczy,K., Fust,G. and Szilagyi,A.";
	private static final String RESU03 = 
		 "  AUTHORS   Yang,Y., Chung,E.K., Wu,Y.L., Savelli,S.L., Nagaraja,H.N., Zhou,B.,\n"
		+"            Hebert,M., Jones,K.N., Shu,Y., Kitzmiller,K., Blanchong,C.A.,\n"
		+"            McBride,K.L., Higgins,G.C., Rennebohm,R.M., Rice,R.R.,\n"
		+"            Hackshaw,K.V., Roubey,R.A., Grossman,J.M., Tsao,B.P.,\n"
		+"            Birmingham,D.J., Rovin,B.H., Hebert,L.A. and Yu,C.Y.";
	private static final String RESU04 = 
		 "  AUTHORS   Fust,G. and Szilagyi,A.";
	
	protected void setUp() throws Exception		{ super.setUp(); }
	protected void tearDown() throws Exception	{ super.tearDown();System.out.println(); }



	public void testParseAuthors01() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA01, true);
		// for (int i = 0 ; i < table.size() ; i++)
		// 	{ System.out.println(table.get(i).toString()); }
		TestCase.assertEquals(1, table.size());
	}
	
	public void testParseAuthors02() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA02, true);
		// for (int i = 0 ; i < table.size() ; i++)
		// 	{ System.out.println(table.get(i).toString()); }
		TestCase.assertEquals(8, table.size());
	}
	
	public void testParseAuthors03() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA03, true);
		// for (int i = 0 ; i < table.size() ; i++)
		// 	{ System.out.println(table.get(i).toString()); }
		TestCase.assertEquals(23, table.size());
	}
	
	public void testParseAuthors04() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA04, true);
		// for (int i = 0 ; i < table.size() ; i++)
		// 	{ System.out.println(table.get(i).toString()); }
		TestCase.assertEquals(2, table.size());
	}
	
	private static String processAuthors(List<Author> authors) {
		int cutter			= 80-12; /** Max length of a 'line', excluding 'title'. */
		String separator	= ComposUtils.AUTHOR_SEPARATOR_COMMA;
		String toReturn		= ComposUtils.GENBANK_TAG_AUTHORS;
		if (authors.size() == 1) 
			{ toReturn += authors.get(0).toStringBrev(); }
		else {
			String preprocessing = new String("");
			for (int i = 0 ; i < authors.size() ; i++) {
				String currentAuth = authors.get(i).toStringBrev();
				if (i < authors.size()-2)
					{ preprocessing += currentAuth+separator; }
				else if (i != authors.size()-1) 
					{ preprocessing += currentAuth; }
				else { preprocessing += ComposUtils.AUTHOR_SEPARATOR_AND
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
						{ append01 += preprocTable[max]+separator; }
					if (append01.matches("^(.*?) and (.*?)$")) 
						{ append01 = append01.substring(0, append01.length()-2); }
					/** Use <max>... */
					if (max < preprocTable.length) {
//						int indexLast01 = append01.length()-1;
//						if (append01.charAt(indexLast01) == ' ') 
//							{ append01 = append01.substring(0, indexLast01); } 
						// System.out.println("'"+append01+"'\t"+append01.length()+" / "+cutter);
						if (append01.length() <= cutter) { append02 = ""; } 
						else {
							for (int i = prevMax ; i < max-1 ; i++) 
								{ append02 += preprocTable[i]+separator; }
							max--;
						}
						// System.out.println("'"+append02+"'\t"+append02.length()+" / "+cutter);
					}
					
					String toAdd	= ((append02.equals(""))?append01:append02);
					int indexLast	= toAdd.length()-1;
					if (toAdd.charAt(indexLast) == ' ') 
						{ toAdd = toAdd.substring(0, indexLast); }
					if (counter == 0)	{ toReturn += toAdd+"\n"; }
					else { toReturn += ComposUtils.BEGIN_EMPTY_LINE+toAdd+"\n"; }
					
					prevMax = max;
					
					counter++;
				} /** END 'while (max < preprocTable.length)' */
			} else { toReturn += preprocessing+"\n"; }
		} /** END ELSE of 'if (this.authors.size() == 1)'. */
		
		return toReturn;
	}
	
	public void testProcessAuthors01() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA01, true);
		String result = AuthorTests.processAuthors(new ArrayList<Author>(table));
		
		String[] compRES = AuthorTests.RESU01.split("\n");
		String[] COMPres = result.split("\n");
		boolean test	 = (compRES.length == COMPres.length);
		if (!test) { System.out.println("\tFailed on LEN"); }
		for (int i = 0 ; (i < compRES.length) && (test) ; i++ ) {
			System.out.println("expected : '"+compRES[i]+"'");
			System.out.println("produced : '"+COMPres[i]+"'");
			test = compRES[i].equals(COMPres[i]);
			if (!test) { System.out.println("\tFailed on line ("+i+")"); }
		}
		
		TestCase.assertTrue(test);
	}
	
	
	public void testProcessAuthors02() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA02, true);
		String result = AuthorTests.processAuthors(new ArrayList<Author>(table));
		
		String[] compRES = AuthorTests.RESU02.split("\n");
		String[] COMPres = result.split("\n");
		boolean test	 = (compRES.length == COMPres.length);
		if (!test) { System.out.println("\tFailed on LEN"); }
		for (int i = 0 ; (i < compRES.length) && (test) ; i++ ) {
			System.out.println("expected : '"+compRES[i]+"'");
			System.out.println("produced : '"+COMPres[i]+"'");
			test = compRES[i].equals(COMPres[i]);
			if (!test) { System.out.println("\tFailed on line ("+i+")"); }
		}
		
		TestCase.assertTrue(test);
	}
	
	
	public void testProcessAuthors03() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA03, true);
		String result = AuthorTests.processAuthors(new ArrayList<Author>(table));
		
		String[] compRES = AuthorTests.RESU03.split("\n");
		String[] COMPres = result.split("\n");
		boolean test	 = (compRES.length == COMPres.length);
		if (!test) { System.out.println("\tFailed on LEN"); }
		for (int i = 0 ; (i < compRES.length) && (test) ; i++ ) {
			System.out.println("expected : '"+compRES[i]+"'");
			System.out.println("produced : '"+COMPres[i]+"'");
			test = compRES[i].equals(COMPres[i]);
			if (!test) { System.out.println("\tFailed on line ("+i+")"); }
		}
		
		TestCase.assertTrue(test);
	}
	
	
	public void testProcessAuthors04() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA04, true);
		String result = AuthorTests.processAuthors(new ArrayList<Author>(table));
		
		String[] compRES = AuthorTests.RESU04.split("\n");
		String[] COMPres = result.split("\n");
		boolean test	 = (compRES.length == COMPres.length);
		if (!test) { System.out.println("\tFailed on LEN"); }
		for (int i = 0 ; (i < compRES.length) && (test) ; i++ ) {
			System.out.println("expected : '"+compRES[i]+"'");
			System.out.println("produced : '"+COMPres[i]+"'");
			test = compRES[i].equals(COMPres[i]);
			if (!test) { System.out.println("\tFailed on line ("+i+")"); }
		}
		
		TestCase.assertTrue(test);
	}
	
	
	
	
	public void testParseAuthors11() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA11, false);
		 for (int i = 0 ; i < table.size() ; i++)
		 	{ System.out.println(table.get(i).toString()); }
		TestCase.assertEquals(1, table.size());
	}
	
	public void testParseAuthors12() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA12, false);
		 for (int i = 0 ; i < table.size() ; i++)
		 	{ System.out.println(table.get(i).toString()); }
		TestCase.assertEquals(8, table.size());
	}
	
	public void testParseAuthors13() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA13, false);
		 for (int i = 0 ; i < table.size() ; i++)
		 	{ System.out.println(table.get(i).toString()); }
		TestCase.assertEquals(23, table.size());
	}
	
	public void testParseAuthors14() {
		List<Author> table = Author.parseAuthors(AuthorTests.DATA14, false);
		 for (int i = 0 ; i < table.size() ; i++)
		 	{ System.out.println(table.get(i).toString()); }
		TestCase.assertEquals(2, table.size());
	}
	
	
}
