package gabywald.crypto.launcher;

import java.io.IOException;
import java.util.Arrays;

import gabywald.crypto.data.ioput.GenBankFileCreator;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.CodeLevel;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.CodeMethod;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.LogLevel;
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
	public int execute(String data, CodeLevel codLevel, CodeMethod codMethod, LogLevel logLevel, GeneticTranslator gt) {
		Logger.printlnLog(LoggerLevel.LL_DEBUG, this.getClass().getName());
		
		GenBankFileCreator gbfc = new GenBankFileCreator();
		// GenBankFileReader gbfr;
		switch(codLevel.actualValue) {
		case content: gbfc.addPathAndContent("", data);break;
		case filePath: 
			EncodeStrategyCommand.apply4aFile(data, gbfc);
			break;
		case directoryPath: 
			
			// List<String> filesListe = new ArrayList<String>();
			String shortPath		= data.substring(data.lastIndexOf("\\")+1);
			Directory repDir		= new Directory( shortPath );
			// String[] listOfFiles	= repDir.list();
			System.out.println("DATA: '" + data + "' / '" + shortPath + "'");
			Arrays.asList( repDir.list() ).stream().forEach( System.out::println );
			// Arrays.asList( repDir.list() ).stream().map( str -> shortPath + str ).forEach( System.out::println );
			
			Arrays.asList( repDir.list() ).stream().map( str -> shortPath + str ).forEach( str -> {
				System.out.println( str );
				EncodeStrategyCommand.apply4aFile(str, gbfc);
			});
			
			// StringBuilder sbContent = new StringBuilder();
			// filesListe.stream().forEach(str -> sbContent.append(str));
			
			// gbfc.addPathAndContent(data, sbContent.toString());
			
			break;
		default: Logger.printlnLog(LoggerLevel.LL_ERROR, "CODELEVEL !!");
		}
		
		System.out.println("PATHES: ");
		gbfc.getEncodedPath().stream().map(str -> "\t"+str ).forEach(System.out::println);
		System.out.println("CONTENTS: ");
		gbfc.getEncodedCont().stream().map(str -> "\t"+str ).forEach(System.out::println);
		System.out.println("FULL ENCRYPTION: ");
		System.out.println(gbfc.getFullEncryption());
		return 0;
	}
	
	public static void apply4aFile(String path, GenBankFileCreator gbfc) {
		File toLoad = new File( path );
		try { toLoad.load(); } 
		catch (IOException e) { Logger.printlnLog(LoggerLevel.LL_ERROR, "File {" + toLoad.getName() + "} not found !"); }
		System.out.println("DATA: '" + path + "' (" + toLoad.getChampsToString().length() + ")");
		gbfc.addPathAndContent(path, toLoad.getChampsToString());
	}

}
