package gabywald.global.data.filters;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileFilter;

import gabywald.global.data.StringUtils;

/**
 * Generic FileFilter...
 * @author Gabriel Chandesris (2011, 2020)
 */
public class GenericFileFilter 
		extends FileFilter 
		implements FilenameFilter {
	
	private List<String> extensions = new ArrayList<String>();
	
	public GenericFileFilter(String anExtension) { 
		this.extensions.add(anExtension); 
	}
	
	public GenericFileFilter(String[] extensions) {
		this(Arrays.asList(extensions));
	}
	
	public GenericFileFilter(List<String> extensions) { 
		this.extensions.addAll(extensions);
	}
	
	@Override
	public boolean accept(File f) {
		if (f.isDirectory())	{ return true; }

		String extension = StringUtils.getExtension( f );
		if (extension != null) {
			boolean isPresent = false;
			
			for (int i = 0 ; (i < this.extensions.size()) & (!isPresent) ; i++) 
				{ isPresent = extension.equals(this.extensions.get(i)); }
			
			if (isPresent)	{ return true; } 
			else			{ return false; }
		}
		return false;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		if (dir.isDirectory())	{ return true; }

		String extension = StringUtils.getExtension(name);
		if (extension != null) {
			boolean isPresent = false;
			for (int i = 0 ; (i < this.extensions.size()) 
								&& (!isPresent) ; i++) 
				{ isPresent = extension.equals(this.extensions.get(i)); }
			
			if (isPresent)	{ return true; } 
			else			{ return false; }
		}
		return false;
	}
	
	public String toString() {
		StringBuilder toReturn = new StringBuilder("");
		this.extensions.stream().forEach( ext -> toReturn.append( ext ).append( "\t") );
		return toReturn.toString();
	}
	
	public String getDescription() {
		StringBuilder toReturn = new StringBuilder("Accept following extensions : ");
		this.extensions.stream().forEach( ext -> toReturn.append( ext ).append(" ; ") );
		return toReturn.toString();
	}
	
	/**
	 * To know if a specific extension is recognized by current FilterUtils. 
	 * @param extension (String)
	 * @return (boolean)
	 */
	public boolean isValid(String extension) 
		{ return this.extensions.contains(extension); }
	
	protected void addExtension(String ext) 
		{ this.extensions.add(ext); }
	
}
