package gabywald.utilities.others.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.utilities.others.PropertiesLoader;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class PropertiesLoaderTests {

	@Test
	public void testPropertiesLoader() {
		PropertiesLoader pl = new PropertiesLoader( "testfile.properties" );
		Assertions.assertNotNull( pl );
		Assertions.assertFalse(pl.getProperties().isEmpty());
	}
	
	@Test
	public void testPropertiesLoaderNegative() {
		PropertiesLoader pl = new PropertiesLoader( "testfile-inexistant.properties" );
		Assertions.assertNotNull( pl );
		Assertions.assertTrue(pl.getProperties().isEmpty());
	}

	@Test
	public void testGetProperty() {
		PropertiesLoader pl = new PropertiesLoader( "testfile.properties" );
		Assertions.assertNotNull( pl );
		Assertions.assertEquals("valuable", pl.getProperty( "property.test" ) );
		Assertions.assertEquals(null, pl.getProperty( "inexistant" ) );
		Assertions.assertNull( pl.getProperty( "inexistant" ) );
	}

	@Test
	public void testGetProperties() {
		PropertiesLoader pl = new PropertiesLoader( "testfile.properties" );
		Assertions.assertNotNull( pl );
		Assertions.assertNotNull( pl.getProperties() );
		Assertions.assertEquals(Properties.class, pl.getProperties().getClass());
	}

	@Test
	public void testOpenResource() {
		InputStream is = PropertiesLoader.openResource( "testfile.properties" );
		Assertions.assertNotNull( is );
	}

	@Test
	public void testResolveFileString() {
		File resolved = PropertiesLoader.resolveFile( "testfile.properties" );
		Assertions.assertNotNull( resolved );
		Assertions.assertTrue( resolved.getAbsolutePath().endsWith("utilities/testfile.properties") );
	}
	
	@Test
	public void testResolveFileFile() {
		File resolved = PropertiesLoader.resolveFile(new File( "testfile.properties" ) );
		Assertions.assertNotNull( resolved );
		Assertions.assertTrue( resolved.getAbsolutePath().endsWith("utilities/testfile.properties") );
	}

	@Test
	public void testResolvePathString() {
		String path = PropertiesLoader.resolvePath( "testfile.properties" );
		Assertions.assertNotNull( path );
		Assertions.assertTrue( path.endsWith("utilities/testfile.properties") );
	}

	@Test
	public void testResolvePathFile() {
		String path = PropertiesLoader.resolvePath(new File( "testfile.properties" ) );
		Assertions.assertNotNull( path );
		Assertions.assertTrue( path.endsWith("utilities/testfile.properties") );
	}
	
	@Test
	public void testOpenInputStream() {
		
		Assertions.assertThrows(IOException.class, () -> PropertiesLoader.openInputStream(new File( "testfile.properties" )) );
		Assertions.assertThrows(IOException.class, () -> PropertiesLoader.openInputStream(new File( "resources/testfile.properties" )) );
		Assertions.assertThrows(IOException.class, () -> PropertiesLoader.openInputStream(new File( "src/resources/testfile.properties" )) );
		
		try {
			FileInputStream fis = PropertiesLoader.openInputStream(new File( "src/test/resources/testfile.properties" ));
			Assertions.assertNotNull( fis );
		} catch (IOException e) {
			Assertions.assertTrue( false );
		}
	}

//	@Test
//	public void testOpenOutputResource() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testOpenOutputStream() {
//		fail("Not yet implemented");
//	}

}
