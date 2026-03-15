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
        System.out.printf("SubCommand1: content=%s, filePath=%s, directoryPath=%s%n",
        		mainCommand.getContent(), mainCommand.getFilePath(), mainCommand.getDirectoryPath());
        // TODO add GT
    	// TODO add encryption type
	}

}
