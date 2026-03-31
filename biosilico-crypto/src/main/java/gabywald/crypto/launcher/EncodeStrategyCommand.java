package gabywald.crypto.launcher;

import java.io.IOException;
import java.util.Arrays;

import gabywald.crypto.data.ioput.BiologicalFileCreator;
import gabywald.crypto.data.ioput.DirectFileCreator;
import gabywald.crypto.data.ioput.EmblFileCreator;
import gabywald.crypto.data.ioput.FastaFileCreator;
import gabywald.crypto.data.ioput.GenBankFileCreator;
import gabywald.crypto.data.ioput.IFileCryptoCreator;
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
 * Encode a content, file of directory. 
 * @author Gabriel Chandesris (2026)
 */
public class EncodeStrategyCommand implements IStrategyCommand {

	@Override
	public int execute(String data, CodeLevel codLevel, CodeMethod codMethod, OutputType output, LogLevel logLevel, GeneticTranslator gt) {
		Logger.printlnLog(LoggerLevel.LL_DEBUG, this.getClass().getName());
		
		BiologicalFileCreator ifcc = 
				(output.actualValue == OutputType.TheEnum.direct) ? new DirectFileCreator() : 
				(output.actualValue == OutputType.TheEnum.fasta) ? new FastaFileCreator() : 
				(output.actualValue == OutputType.TheEnum.embl) ? new EmblFileCreator() : 
				(output.actualValue == OutputType.TheEnum.genbank) ? new GenBankFileCreator() : 
					new DirectFileCreator();
		
		switch(codLevel.actualValue) {
		case content: ifcc.addPathAndContent("", data);break;
		case filePath: EncodeStrategyCommand.apply4aFile(data, ifcc);break;
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
				EncodeStrategyCommand.apply4aFile(str, ifcc);
			});
			break;
		default: Logger.printlnLog(LoggerLevel.LL_ERROR, "CODELEVEL INCORRECT!!");
		}
		
		if (Logger.isLogLevelAccurate(LoggerLevel.LL_DEBUG)) {
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "PATHES: ");
			ifcc.getEncodedPath().stream().map(str -> "\t"+str ).forEach(System.out::println);
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "CONTENTS: ");
			ifcc.getEncodedCont().stream().map(str -> "\t"+str ).forEach(System.out::println);
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "FULL ENCRYPTION: ");
			Logger.printlnLog(LoggerLevel.LL_DEBUG, ifcc.getFullEncryption());
		}
		System.out.println(ifcc.getFullEncryption());
		return 0;
	}
	
	public static void apply4aFile(String path, IFileCryptoCreator ifcc) {
		File toLoad = new File( path );
		try { toLoad.load(); } 
		catch (IOException e) { Logger.printlnLog(LoggerLevel.LL_ERROR, "File {" + toLoad.getName() + "} not found !"); }
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "DATA: '" + path + "' (" + toLoad.getChampsToString().length() + ")");
		ifcc.addPathAndContent(path, toLoad.getChampsToString());
	}

}
