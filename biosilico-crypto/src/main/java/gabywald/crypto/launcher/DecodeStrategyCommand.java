package gabywald.crypto.launcher;

import gabywald.crypto.launcher.BioSilicoCryptoCommand.CodeLevel;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.CodeMethod;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.LogLevel;
import gabywald.crypto.model.GeneticTranslator;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

public class DecodeStrategyCommand implements IStrategyCommand {

	@Override
	public int execute(String data, CodeLevel codLevel, CodeMethod codMethod, LogLevel logLevel, GeneticTranslator gt) {
		Logger.printlnLog(LoggerLevel.LL_DEBUG, this.getClass().getName());
		
		return 0;
	}

}
