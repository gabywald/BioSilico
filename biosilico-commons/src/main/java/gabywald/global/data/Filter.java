package gabywald.global.data;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Global and abstract File Filter definition. 
 * @author Gabriel Chandesris (2008)
 */
public abstract class Filter extends FileFilter {
	public abstract String getDescription();
	public abstract boolean accept(File fich);
}
