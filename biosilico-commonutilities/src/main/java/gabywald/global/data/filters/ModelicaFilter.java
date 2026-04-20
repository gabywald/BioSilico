package gabywald.global.data.filters;

import gabywald.global.data.StringUtils;

/**
 * Modelica FileFilter...
 * @author Gabriel Chandesris (2011, 2020, 2022)
 */
public class ModelicaFilter extends GenericFileFilter {
	
	public ModelicaFilter() 
		{ super(StringUtils.mod); }

	@Override
	public String getDescription() 
		{ return "Modelica Files"; }

}
