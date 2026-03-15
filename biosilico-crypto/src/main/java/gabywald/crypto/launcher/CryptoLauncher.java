package gabywald.crypto.launcher;

import picocli.CommandLine;

/**
 * 
 * @author Gabriel Chandesris (2025, 2026)
 */
public class CryptoLauncher {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new BioSilicoCryptoCommand())
        .addSubcommand("decode", new DecodeCommand())
        .addSubcommand("encode", new EncodeCommand())
        .execute(args);
        System.exit(exitCode);
    }
    
    // **** Part nelow to detect if launch in IDE or in jar...
    
    public static String prefix = (CryptoLauncher.getInstance().getClass().getResource("").toString().startsWith("file:")?"":"/");
    
    private static CryptoLauncher instance;
    private static CryptoLauncher getInstance() {
		if (CryptoLauncher.instance == null) 
			{ CryptoLauncher.instance = new CryptoLauncher(); }
		return CryptoLauncher.instance;
	}

}
