package gabywald.global.data.filters;

import gabywald.global.data.StringUtils;

/**
 * Images FileFilter... 
 * @author Gabriel Chandesris (2011, 2020, 2022)
 */
public class ImagesFilter extends GenericFileFilter {

	public ImagesFilter() 
		{ super(new String[] { StringUtils.gif, StringUtils.jpeg, StringUtils.jpg,  
								StringUtils.tif, StringUtils.tiff, StringUtils.png}); }
	
	@Override
	public String getDescription() 
		{ return "Images Files"; }

}
