package gabywald.global.data.filters;

/**
 * Specific FileFilter for TXT files...
 * @author Gabriel Chandesris (2014, 2018, 2022)
 */
public class TXTFilter extends GenericFileFilter {

	public TXTFilter() 
		{ super(new String[] { "txt" }); }
	
	@Override
	public String getDescription() 
		{ return "TXT Files"; }

}
