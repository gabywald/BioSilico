package gabywald.crypto.launcher;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.model.GeneticTranslator;
import picocli.CommandLine.ArgGroup;
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
	
    @Option(names = {"-v", "--verbose"},
    		description = "Active verbose mode.")
    private boolean verbose;
    
    /**
     * To define Code Level (i.e. if on single string, a file and its oath or a directory and all files inside). 
     */
    static class CodeLevel {
        enum TheEnum { content, filePath, directoryPath }

        TheEnum actualValue = TheEnum.content;

    	@Option(names = {"-c", "--content"}, 
    			description = "Content (if only direct content)")
        void setContent(boolean value)			{ actualValue = TheEnum.content; }

    	@Option(names = {"-f", "--file"}, 
    			description = "File Path (if only direct content), file content and path")
        void setFilePath(boolean value)			{ actualValue = TheEnum.filePath; }

    	@Option(names = {"-d", "--directory"}, 
    			description = "Directory Path (if only direct content), all files contents and pathes")
        void setDirectoryPath(boolean value)	{ actualValue = TheEnum.directoryPath; }
    	
    	boolean isContent()			{ return (this.actualValue == TheEnum.content); }
    	boolean isFilePath()		{ return (this.actualValue == TheEnum.filePath); }
    	boolean isDirectoryPath()	{ return (this.actualValue == TheEnum.directoryPath); }
    }
    @ArgGroup(exclusive = true, heading = "Code Transcription Options%n", multiplicity = "1")
    CodeLevel codLevel = new CodeLevel();

    /**
     * Log Level. 
     */
    static class LogLevel {
        enum TheEnum { debug, info, warn, error, none }

        TheEnum actualValue = TheEnum.none;

        @Option(names = "--debug", description = "Sets log level to DEBUG.")
        void setDebug(boolean value)	{ actualValue = TheEnum.debug; }

        @Option(names = "--info", description = "Sets log level to INFO.")
        void setInfo(boolean value)		{  actualValue = TheEnum.info; }

        @Option(names = "--warn", description = "Sets log level to WARN.")
        void setWarn(boolean value)		{ actualValue = TheEnum.warn; }
        
        @Option(names = "--error", description = "Sets log level to ERROR.")
        void setError(boolean value)	{ actualValue = TheEnum.error; }
        
        @Option(names = "--none", description = "Sets log level to NONE.")
        void setNone(boolean value)		{ actualValue = TheEnum.none; }
    }
    @ArgGroup(exclusive = true, heading = "Log Level Options%n", multiplicity = "0..1")
    LogLevel logLevel = new LogLevel();
    
    /**
     * Code Method to use. 
     */
    static class CodeMethod {
        enum TheEnum { simple, more, random }

        TheEnum actualValue = TheEnum.simple;

    	@Option(names = {"-s", "--simple"}, 
    			description = "Simple code Method.")
        void setContent(boolean value)			{ actualValue = TheEnum.simple; }

    	@Option(names = {"-m", "--more"}, 
    			description = "More code Method. ")
        void setFilePath(boolean value)			{ actualValue = TheEnum.more; }

    	@Option(names = {"-r", "--random"}, 
    			description = "Random code Method")
        void setDirectoryPath(boolean value)	{ actualValue = TheEnum.random; }
    	
    	boolean isSimple()	{ return (this.actualValue == TheEnum.simple); }
    	boolean isMore()	{ return (this.actualValue == TheEnum.more); }
    	boolean isRandom()	{ return (this.actualValue == TheEnum.random); }
    }
    @ArgGroup(exclusive = true, heading = "Code Method Options%n", multiplicity = "1")
    CodeMethod codMethod = new CodeMethod();
    
	@Option(names = {"-y", "--cryptofileindex"}, 
			description = "Crypto File Index", 
			defaultValue = "0", 
			hidden = true)
	private int cryptoFileIndex;
	GeneticTranslator gt = null;
	
	@Option(names = {"-D", "--DATA"}, arity = "1", required = true, 
			description = "Data to transcript (content, path to file or directory. ")
	String dataTotranscript;
	
	@Override
	public void run() {
		System.out.println(this.toString());
		System.out.println("\t" + "verbose: " + this.verbose);
		System.out.println("\t" + "codLevel: " + this.codLevel.actualValue);
		System.out.println("\t" + "codMethod: " + this.codMethod.actualValue);
		System.out.println("\t" + "logLevel: " + this.logLevel.actualValue);
		System.out.println("\t" + "data: " + this.dataTotranscript);
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

	public CodeLevel getCodLevel() { return this.codLevel; }
	
	public LogLevel getLogLevel() { return this.logLevel; }
	
	public CodeMethod getCodeMethod() { return this.codMethod; }
	
	public String getDataToTranscript() { return this.dataTotranscript; }

	public GeneticTranslator getGeneticTranslator() { this.loadGeneticTranslator();return this.gt; }

	

}
