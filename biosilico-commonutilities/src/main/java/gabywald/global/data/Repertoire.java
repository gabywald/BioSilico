package gabywald.global.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import gabywald.global.data.filters.GenericFileFilter;


/**
 * This class to manipulate directories. 
 * @author Gabriel Chandesris (2011, 2020)
 * XXX checking path...
 * @deprecated Use @see {@link Directory}
 */
@SuppressWarnings("serial")
public class Repertoire extends File {

	/**
	 * Default constructor with given path. 
	 * @param path (String)
	 */
	public Repertoire(String path) {
		super(path);
	}
	
	/**
	 * Gives the content of the path, file names. 
	 * @return (String[])
	 */
	public String[] listContent() 
		{ return this.list(); }
	
	/**
	 * Gives the content of the path, file names ; according to a filter. 
	 * @param filter (GenericFileFilter)
	 * @return (String[])
	 */
	public String[] listContent(GenericFileFilter filter) { 
		List<String> toReturn	= new ArrayList<String>();
		Fichier[] files			= this.getFichiers(filter);
		for (int i = 0 ; i < files.length ; i++) 
			{ toReturn.add(files[i].getName()); }
		return toReturn.toArray(new String[0]);
	}

	public Fichier[] getFichiers() {
		Vector<Fichier> files	= new Vector<Fichier>();
		String[] lis			= this.list();
		for (int i = 0 ; i < lis.length ; i++) {
			File test = new File(this.getPath()+lis[i]);
			if (test.isFile()) 
				{ files.add(new Fichier(this.getPath()+lis[i])); }
		}
		Fichier[] result = new Fichier[files.size()];
		for (int i = 0 ; i < files.size() ; i++) 
			{ result[i] = files.elementAt(i); }
		return result;
	}
	
	public Fichier[] getFichiers(GenericFileFilter filter) {
		Vector<Fichier> files	= new Vector<Fichier>();
		String[] lis			= this.list();
		for (int i = 0 ; i < lis.length ; i++) {
			String pathAndName = this.getPath()+"/"+lis[i];
			File test = new File(pathAndName);
			if ( (test.isFile()) && (filter.accept(test)) )
				{ files.add(new Fichier(pathAndName)); }
		}
		Fichier[] result = new Fichier[files.size()];
		for (int i = 0 ; i < files.size() ; i++) 
			{ result[i] = files.elementAt(i); }
		return result;
	}
	
	public static String[] removeSVNdir(String[] res) {
		boolean isPresent = false;
		for (int i = 0 ; (i < res.length) && (!isPresent) ; i++) 
			{ isPresent = (res[i].equals(".svn")); }
		if (isPresent) {
			String[] nextRes = new String[res.length-1];
			int i = 0;
			for ( ; (i < res.length) 
						&& (!res[i].equals(".svn")) ; i++) 
				{ nextRes[i] = res[i]; }
			i++;
			for ( ; i < res.length ; i++) 
				{ nextRes[i-1] = res[i]; }
			return nextRes;
		} else { return res; }
	}
	
}
