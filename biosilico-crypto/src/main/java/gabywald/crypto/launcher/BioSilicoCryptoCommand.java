package gabywald.crypto.launcher;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.model.GeneticTranslator;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
@Command(
	    name = "biosilico-crypto",
	    version = "1.0",
	    description = "Application CLI with picocli.", 
		// subcommands = { EncodeCommand.class, DecodeCommand.class }, 
		mixinStandardHelpOptions = true)
public class BioSilicoCryptoCommand implements Runnable {
	
    @Option(
    		names = {"-v", "--verbose"},
    		description = "Active verbose mode.")
    private boolean verbose;
	
	@Option(names = {"-c", "--content"}, 
			description = "Content (if only direct content)")
    private String content;
	
	@Option(names = {"-f", "--file"}, 
			description = "File Path (if only direct content), file content and path")
    private String filePath;
	
	@Option(names = {"-d", "--directory"}, 
			description = "Directory Path (if only direct content), all files contents and pathes")
    private String directoryPath;
	
	@Option(names = {"-y", "--cryptofileindex"}, 
			description = "Crypto File Index", 
			defaultValue = "0", 
			hidden = true)
	private int cryptoFileIndex;
	private GeneticTranslator gt = null;
	
	// TODO Simple, More or Rand encryption ?

	@Override
	public void run() {
		System.out.println(this.toString());
		System.out.println("\t" + "verbose: " + this.verbose);
		System.out.println("\t" + "content: " + this.content);
		System.out.println("\t" + "filePath: " + this.filePath);
		System.out.println("\t" + "directoryPath: " + this.directoryPath);
		System.out.println("\t" + "cryptoFileIndex: " + this.cryptoFileIndex);
		
		this.loadGeneticTranslator();
	}
	
	private void loadGeneticTranslator() {
		try { this.gt = BiologicalUtils.getGenericCrypto(this.cryptoFileIndex); }
		catch (NullPointerException npe) 
			{ System.out.println("BAD CRYPTO FILE LOAD {" + this.cryptoFileIndex + "} !!"); }
		catch (ArrayIndexOutOfBoundsException aioobe) 
			{ System.out.println("BAD CRYPTO FILE INDEX {" + this.cryptoFileIndex + "} !!"); }
		if (this.gt == null) { System.out.println("GT NOT DEFINED !!"); }
	}
	
	public boolean isVerbose() { return verbose; }

	public String getContent() { return this.content; }

	public String getFilePath() { return this.filePath; }

	public String getDirectoryPath() { return this.directoryPath; }

	public GeneticTranslator getGeneticTranslator() { this.loadGeneticTranslator();return this.gt; }

}
