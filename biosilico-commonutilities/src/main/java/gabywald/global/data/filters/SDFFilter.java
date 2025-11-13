package gabywald.global.data.filters;

import gabywald.global.data.StringUtils;

/**
 * Specific FileFilter for SDF (Sample Description Framework ?) files...
 * @author Gabriel Chandesris (2011, 2022)
 */
public class SDFFilter extends GenericFileFilter {
	public SDFFilter() 
		{ super(StringUtils.sdf); }

	@Override
	public String getDescription() 
		{ return "SDF Files"; }
}
