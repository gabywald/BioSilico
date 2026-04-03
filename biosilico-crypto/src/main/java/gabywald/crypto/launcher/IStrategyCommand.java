package gabywald.crypto.launcher;

import gabywald.crypto.launcher.BioSilicoCryptoCommand.CodeLevel;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.CodeMethod;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.LogLevel;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.OutputType;
import gabywald.crypto.model.GeneticTranslator;

/**
 * Command Design Pattern || Strategy Design Pattern
 * @author Gabriel Chandesris (2020)
 */
public interface IStrategyCommand {
	/**
	 * 
	 * @param data
	 * @param codLevel
	 * @param codMethod
	 * @param output
	 * @param logLevel
	 * @param gtFile
	 * @param gtPath
	 * @return (exit code)
	 */
	public int execute(	String data, CodeLevel codLevel, CodeMethod codMethod, 
						OutputType output, LogLevel logLevel, 
						GeneticTranslator gtFile, GeneticTranslator gtPath);
}
