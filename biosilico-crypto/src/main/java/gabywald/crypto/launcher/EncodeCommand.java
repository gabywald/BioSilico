package gabywald.crypto.launcher;

import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
@Command(
		name = "encode", 
		description = "Encode a content, file of directory. ", 
		mixinStandardHelpOptions = true)
public class EncodeCommand implements Runnable {
	
    @Mixin
    private BioSilicoCryptoCommand mainCommand;
    
	@Override
	public void run() {
    	System.out.println( "ENCODE !" );
        System.out.printf(	"ENcodeCommand:%n\tcontent=%s, filePath=%s, directoryPath=%s%n\t"
							+ "dataToTranscript=%s%n\t"
							+ "methodSimple=%s, methodMore=%s, methodRand=%s%n",
					mainCommand.codLevel.isContent(), mainCommand.codLevel.isFilePath(), mainCommand.codLevel.isDirectoryPath(), 
					mainCommand.getDataToTranscript(), 
					mainCommand.codMethod.isSimple(), mainCommand.codMethod.isMore(), mainCommand.codMethod.isRandom());
        // TODO add GT
    	// TODO add encryption type
	}

}
