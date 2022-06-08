package gabywald.javabio.data.composition.tests;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author Gabriel Chandesris (2020, 2022)
 */
public class TestsHelper {

	static String getDataFromFile(ClassLoader cl, String path) {
		try (InputStream inputStream = cl.getResourceAsStream( path )) {
			String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			System.out.println(result);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
