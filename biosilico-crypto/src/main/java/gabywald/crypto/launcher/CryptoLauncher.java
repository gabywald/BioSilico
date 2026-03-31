package gabywald.crypto.launcher;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import picocli.CommandLine;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public class CryptoLauncher {

	public static void main(String[] args) {
		int exitCode = new CommandLine(new BioSilicoCryptoCommand()).execute(args);
		// System.exit(exitCode);
		Logger.printlnLog(LoggerLevel.LL_FORUSER, exitCode + "" );
	}
	
}
