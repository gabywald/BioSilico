package gabywald.global.data.filters;

import gabywald.global.data.StringUtils;

/**
 * Specific FileFilter for BibTeX files...
 * @author Gabriel Chandesris (2011, 2022)
 */
public class BibTeXFilter extends GenericFileFilter {
	
	public BibTeXFilter() 
		{ super(StringUtils.bib); }

	@Override
	public String getDescription() 
		{ return "BibTeX Files"; }
	
}
