package gabywald.crypto.launcher;

import picocli.CommandLine;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public class CryptoLauncher {

	public static void main(String[] args) {
		int exitCode = new CommandLine(new BioSilicoCryptoCommand())
		.execute(args);
		// System.exit(exitCode);
		System.out.println( exitCode );
	}
	
}
