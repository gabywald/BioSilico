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
		BioSilicoCryptoCommand bscc = new BioSilicoCryptoCommand();
		int exitCode = new CommandLine(bscc).execute(args);
		// System.exit(exitCode);
		if (bscc.isLogEnabled(BioSilicoCryptoCommand.LogLevel.TheEnum.info)) 
					{ Logger.printlnLog(LoggerLevel.LL_FORUSER, exitCode + "" ); }
	}
	
}
