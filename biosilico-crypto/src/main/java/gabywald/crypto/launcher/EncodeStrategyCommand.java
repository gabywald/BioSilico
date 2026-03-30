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
//		switch(output.actualValue) {
//		case direct: new DirectFileCreator();break;
//		case fasta: new FastaFileCreator();break;
//		case embl:new EmblFileCreator();break;
//		case genbank:new GenBankFileCreator();break;
//		default:new DirectFileCreator();
//		};
		
		// if (ifcc == null) { return 1; }
		
		// GenBankFileReader gbfr;
		switch(codLevel.actualValue) {
		case content: ifcc.addPathAndContent("", data);break;
		case filePath: 
			EncodeStrategyCommand.apply4aFile(data, ifcc);
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
				EncodeStrategyCommand.apply4aFile(str, ifcc);
			});
			
			// StringBuilder sbContent = new StringBuilder();
			// filesListe.stream().forEach(str -> sbContent.append(str));
			
			// gbfc.addPathAndContent(data, sbContent.toString());
			
			break;
		default: Logger.printlnLog(LoggerLevel.LL_ERROR, "CODELEVEL !!");
		}
		
		System.out.println("PATHES: ");
		ifcc.getEncodedPath().stream().map(str -> "\t"+str ).forEach(System.out::println);
		System.out.println("CONTENTS: ");
		ifcc.getEncodedCont().stream().map(str -> "\t"+str ).forEach(System.out::println);
		System.out.println("FULL ENCRYPTION: ");
		System.out.println(ifcc.getFullEncryption());
		return 0;
	}
	
	public static void apply4aFile(String path, IFileCryptoCreator ifcc) {
		File toLoad = new File( path );
		try { toLoad.load(); } 
		catch (IOException e) { Logger.printlnLog(LoggerLevel.LL_ERROR, "File {" + toLoad.getName() + "} not found !"); }
		System.out.println("DATA: '" + path + "' (" + toLoad.getChampsToString().length() + ")");
		ifcc.addPathAndContent(path, toLoad.getChampsToString());
	}

}
