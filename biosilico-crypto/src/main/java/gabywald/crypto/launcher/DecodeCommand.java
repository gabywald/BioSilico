package gabywald.crypto.launcher;

import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
@Command(
		name = "decode", 
		description = "Decode a content, file of directory. ", 
		mixinStandardHelpOptions = true)
public class DecodeCommand implements Runnable {
	
    @Mixin
    private BioSilicoCryptoCommand mainCommand;

	@Override
	public void run() {
    	System.out.println( "DECODE !" );
        System.out.printf(	"DEcodeCommand:%n%t%tcontent=%s, filePath=%s, directoryPath=%s%n"
							+ "dataToTranscript=%s%n%t%t"
							+ "methodSimple=%s, methodMore=%s, methodRand=%s%n",
					mainCommand.codLevel.isContent(), mainCommand.codLevel.isFilePath(), mainCommand.codLevel.isDirectoryPath(), 
					mainCommand.getDataToTranscript(), 
					mainCommand.codMethod.isSimple(), mainCommand.codMethod.isMore(), mainCommand.codMethod.isRandom());
        // TODO add GT
    	// TODO add encryption type
	}

}
