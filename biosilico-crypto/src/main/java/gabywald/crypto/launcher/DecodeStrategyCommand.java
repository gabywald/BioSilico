package gabywald.crypto.launcher;

import java.io.IOException;
import java.util.Arrays;

import gabywald.crypto.data.ioput.DirectFileReader;
import gabywald.crypto.data.ioput.EmblFileReader;
import gabywald.crypto.data.ioput.FastaFileReader;
import gabywald.crypto.data.ioput.GenBankFileReader;
import gabywald.crypto.data.ioput.IFileCryptoReader;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.CodeLevel;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.CodeMethod;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.LogLevel;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.OutputType;
import gabywald.crypto.model.GeneticTranslator;
import gabywald.global.data.Directory;
import gabywald.global.data.File;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * Decode a content, file of directory. 
 * @author Gabriel Chandesris (2026)
 */
public class DecodeStrategyCommand implements IStrategyCommand {

	@Override
	public int execute(String data, CodeLevel codLevel, CodeMethod codMethod, OutputType output, LogLevel logLevel, GeneticTranslator gt) {
		Logger.printlnLog(LoggerLevel.LL_DEBUG, this.getClass().getName());
		
		IFileCryptoReader ifcr = 
				(output.actualValue == OutputType.TheEnum.direct) ? new DirectFileReader() : 
				(output.actualValue == OutputType.TheEnum.fasta) ? new FastaFileReader() : 
				(output.actualValue == OutputType.TheEnum.embl) ? new EmblFileReader() : 
				(output.actualValue == OutputType.TheEnum.genbank) ? new GenBankFileReader() : 
					new DirectFileReader();
		
		switch(codLevel.actualValue) {
		case content: ifcr.setFileContent(data);break;
		case filePath: DecodeStrategyCommand.apply4aFile(data, ifcr);break;
		case directoryPath: 
			String shortPath		= data.substring(data.lastIndexOf("\\")+1);
			Directory repDir		= new Directory( shortPath );
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "DATA: '" + data + "' / '" + shortPath + "'");
			if (Logger.isLogLevelAccurate(LoggerLevel.LL_DEBUG)) {
				Arrays.asList( repDir.list() ).stream()
				.forEach( str -> Logger.printlnLog(LoggerLevel.LL_DEBUG, str) );
			}
			Arrays.asList( repDir.list() ).stream().map( str -> shortPath + str ).forEach( str -> {
				Logger.printlnLog(LoggerLevel.LL_DEBUG,  str );
				DecodeStrategyCommand.apply4aFile(data, ifcr);
			});
			break;
		default: Logger.printlnLog(LoggerLevel.LL_ERROR, "CODELEVEL INCORRECT!!");
		}
		
		System.out.println(ifcr.getPath());
		System.out.println(ifcr.getContent());
		return 0;
	}
	
	
	public static void apply4aFile(String path, IFileCryptoReader ifcr) {
		File toLoad = new File( path );
		try { toLoad.load(); } 
		catch (IOException e) { Logger.printlnLog(LoggerLevel.LL_ERROR, "File {" + toLoad.getName() + "} not found !"); }
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "DATA: '" + path + "' (" + toLoad.getChampsToString().length() + ")");
		ifcr.setFileContent(toLoad.getChampsToString());
	}

}
