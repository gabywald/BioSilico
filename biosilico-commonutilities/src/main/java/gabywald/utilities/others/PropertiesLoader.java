package gabywald.utilities.others;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Properties Loader
 * @author Gabriel Chandesris (2020)
 */
public class PropertiesLoader {

	private Properties props = null;

	/**
	 * Main constructor. 
	 * @param filename Path to file
	 */
	public PropertiesLoader(String filename) {

		this.props		= new Properties();

		try (InputStream input = PropertiesLoader.openResource(filename)) {
			if (input == null) {
				System.out.println("Sorry, unable to find {" + filename + "} (2)");
				return;
			}

			this.props.load(input);

		} catch (IOException ex) {
			System.out.println("Sorry, unable to load properties {" + filename + "} (1)");
		}

	}

	public String getProperty(String key) {
		return this.props.getProperty(key);
	}

	public Properties getProperties() {
		return this.props;
	}

	/**
	 * Opens an input stream on the specified path, which is either a file path, or a resource path, depending on the context.
	 * <p>Examples of valid paths :<ul>
	 *  <li>Absolute path : <ul>
	 *      <li><code>"C:/file.xml"</code> or <code>"C:\file.xml"</code> (Windows)</li>
	 *      <li><code>"/etc/file.xml"</code> (Linux)</li>
	 *      <li><code>"~/tmp/file.xml"</code> (Linux)</li>
	 *  </ul></li>
	 *  <li>Relative path : <code>"path/to/file.xml"</code> (Windows or Linux)</li>
	 *  <li>Resource path : <code>"</code><i>[</i>/<i>]</i><code>src/main/resources/file.xml"</code> (Windows or Linux)</li>
	 * </ul>
	 * @param resourcePath The input file path (either an absolute or relative file path, or a resource path).
	 * @return an input stream on the resolved path.
	 * TODO same for OutputStream
	 */
	public static final InputStream openResource(String resourcePath) {
		// First try as a file (using the raw path)
		try {
			final File fileCandidate = new File(resourcePath);
			if (fileCandidate.isFile()) {
				return PropertiesLoader.openInputStream(fileCandidate);
			}
		} catch (IOException e) { // ignore exception and carry on...
		}
		// Next try as a file (using the resolved path)
		try {
			final File fileCandidate = new File(PropertiesLoader.resolvePath(resourcePath));
			if (fileCandidate.isFile()) {
				return PropertiesLoader.openInputStream(fileCandidate);
			}
		} catch (IOException e) { // ignore exception and carry on...
		}
		// Finally try as a resource
		resourcePath = resourcePath.replace('\\','/');
		if (resourcePath.startsWith("/")) {
			resourcePath = resourcePath.substring(1);
		}
		return PropertiesLoader.class.getClassLoader().getResourceAsStream(resourcePath);
	}

	/**
	 * Opens a {@link FileInputStream} for the specified file, providing better
	 * error messages than simply calling <code>new FileInputStream(file)</code>.
	 * <p>
	 * At the end of the method either the stream will be successfully opened,
	 * or an exception will have been thrown.
	 * <p>
	 * An exception is thrown if the file does not exist.
	 * An exception is thrown if the file object exists but is a directory.
	 * An exception is thrown if the file exists but cannot be read.
	 *
	 * @param file the file to open for input, must not be {@code null}
	 * @return a new {@link FileInputStream} for the specified file
	 * @throws FileNotFoundException if the file does not exist
	 * @throws IOException           if the file object is a directory
	 * @throws IOException           if the file cannot be read
	 */
	public static FileInputStream openInputStream(final File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}
		return new FileInputStream(file);
	}
	
	public static final OutputStream openOutputResource(String resourcePath) {
		// First try as a file (using the raw path)
		try {
			final File fileCandidate = new File(resourcePath);
			if (fileCandidate.isFile()) {
				return PropertiesLoader.openOutputStream(fileCandidate);
			}
		} catch (IOException e) { // ignore exception and carry on...
		}
		// Next try as a file (using the resolved path)
		try {
			final File fileCandidate = new File(PropertiesLoader.resolvePath(resourcePath));
			if (fileCandidate.isFile()) {
				return PropertiesLoader.openOutputStream(fileCandidate);
			}
		} catch (IOException e) { // ignore exception and carry on...
		}
		// Finally try as a resource
		resourcePath = resourcePath.replace('\\','/');
		if (resourcePath.startsWith("/")) {
			resourcePath = resourcePath.substring(1);
		}
		try {
			final File fileCandidate = new File(PropertiesLoader.resolvePath(resourcePath));
			if (fileCandidate.isFile()) {
				return PropertiesLoader.openOutputStream(fileCandidate);
			}
		} catch (IOException e) { ; }
		return null;
	}
	
	public static FileOutputStream openOutputStream(final File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException("File '" + file + "' cannot be write");
			}
		}
		return new FileOutputStream(file);
	}
	
	

	/**
	 * Get the canonical form of the specified filename.<br>
	 * This method also resolves the special <quote>"~/"</quote> header using the "user.home" property.
	 * @param fileName The name of the file/folder to be normalised.
	 * @return The file's canonical form.
	 * @see File#getCanonicalFile()
	 */
	public static final File resolveFile(String fileName) {
		try {
			return ( fileName.startsWith("~/") || fileName.startsWith("~\\") )
					? new File( System.getProperty("user.home"), fileName.substring(2) ).getCanonicalFile()
							: new File( fileName ).getCanonicalFile();
		} catch (Throwable t) {
			throw new RuntimeException("Failed to resolve file name for [" + fileName + "]", t);
		}
	}
	
	public static final String resolvePath(String fileName) {
		return PropertiesLoader.resolveFile(fileName).getPath();
	}
	
	public static final String resolvePath(File file) {
		return PropertiesLoader.resolvePath( file.getPath() );
	}
	
	public static final File resolveFile(File file) {
		return PropertiesLoader.resolveFile( file.getPath() );
	}
}
