package gabywald.crypto.launcher;

import gabywald.crypto.launcher.BioSilicoCryptoCommand.CodeLevel;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.CodeMethod;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.LogLevel;
import gabywald.crypto.model.GeneticTranslator;

/**
 * Command Design Pattern || Strategy Design Pattern
 * @author Gabriel Chandesris (2020)
 */
public interface IStrategyCommand {
	public int execute(String data, CodeLevel codLevel, CodeMethod codMethod, LogLevel logLevel, GeneticTranslator gt);
}
