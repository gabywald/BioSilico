package gabywald.javabio.data.composition.tests;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2022, 2026)
 */
public class TestsHelper {

	static String getDataFromFile(ClassLoader cl, String path) {
		try (InputStream inputStream = cl.getResourceAsStream( path )) {
			String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			Logger.printlnLog(LoggerLevel.LL_NONE, result);
			return result;
		} catch (IOException e) {
			Logger.printlnLog(LoggerLevel.LL_ERROR, e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
}
