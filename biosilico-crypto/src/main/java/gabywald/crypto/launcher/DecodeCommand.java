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
        System.out.printf("SubCommand1: content=%s, filePath=%s, directoryPath=%s%n",
        		mainCommand.getContent(), mainCommand.getFilePath(), mainCommand.getDirectoryPath());
        // TODO add GT
    	// TODO add encryption type
	}

}
