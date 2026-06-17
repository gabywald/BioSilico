package gabywald.creatures.launcher;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import picocli.CommandLine;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public class CreaturesLauncher {

	public static void main(String[] args) {
		BioSilicoCreaturesCommand bscc = new BioSilicoCreaturesCommand();
		int exitCode = new CommandLine(bscc).execute(args);
		// System.exit(exitCode);
		if (bscc.isLogEnabled(BioSilicoCreaturesCommand.LogLevel.TheEnum.info)) 
					{ Logger.printlnLog(LoggerLevel.LL_FORUSER, exitCode + "" ); }
	}
	
}
