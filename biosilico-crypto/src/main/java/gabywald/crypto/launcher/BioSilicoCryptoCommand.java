package gabywald.crypto.launcher;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.model.GeneticTranslator;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
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
		void setContent(boolean b)			{ this.actualValue = TheEnum.content; }

		@Option(names = {"-f", "--file"}, 
				description = "File Path (if only direct content), file content and path")
		void setFilePath(boolean b)			{ this.actualValue = TheEnum.filePath; }

		@Option(names = {"-d", "--directory"}, 
				description = "Directory Path (if only direct content), all files contents and pathes")
		void setDirectoryPath(boolean b)	{ this.actualValue = TheEnum.directoryPath; }
		
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
		void setDebug(boolean b)	{ this.actualValue = TheEnum.debug; }

		@Option(names = "--info", description = "Sets log level to INFO.")
		void setInfo(boolean b)		{ this.actualValue = TheEnum.info; }

		@Option(names = "--warn", description = "Sets log level to WARN.")
		void setWarn(boolean b)		{ this.actualValue = TheEnum.warn; }
		
		@Option(names = "--error", description = "Sets log level to ERROR.")
		void setError(boolean b)	{ this.actualValue = TheEnum.error; }
		
		@Option(names = "--none", description = "Sets log level to NONE.")
		void setNone(boolean b)		{ this.actualValue = TheEnum.none; }
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
		void setSimple(boolean b)	{ this.actualValue = TheEnum.simple; }

		@Option(names = {"-m", "--more"}, 
				description = "More code Method. ")
		void setMore(boolean b)		{ this.actualValue = TheEnum.more; }

		@Option(names = {"-r", "--random"}, 
				description = "Random code Method")
		void setRand(boolean b)		{ this.actualValue = TheEnum.random; }
		
		boolean isSimple()	{ return (this.actualValue == TheEnum.simple); }
		boolean isMore()	{ return (this.actualValue == TheEnum.more); }
		boolean isRandom()	{ return (this.actualValue == TheEnum.random); }
	}
	@ArgGroup(exclusive = true, heading = "Code Method Options%n", multiplicity = "1")
	CodeMethod codMethod = new CodeMethod();
	
	static class CodeCommand {
		enum TheEnum { encode, decode }

		TheEnum actualValue = TheEnum.encode;

		@Option(names = {"-e", "--encode"}, 
				description = "Encode Command.")
		void setEncode(boolean b) { this.actualValue = TheEnum.encode; }

		@Option(names = {"-x", "--decode"}, 
				description = "Decode Command. ")
		void setDecode(boolean b) { this.actualValue = TheEnum.decode; }

		boolean isEncode()	{ return (this.actualValue == TheEnum.encode); }
		boolean isDecode()	{ return (this.actualValue == TheEnum.decode); }
	}
	@ArgGroup(exclusive = true, heading = "Code Method Options%n", multiplicity = "1")
	CodeCommand codCommand = new CodeCommand();
	
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
		Logger.printlnLog(LoggerLevel.LL_NONE, this.toString());
		Logger.printlnLog(LoggerLevel.LL_NONE, "\t" + "verbose: " + this.verbose);
		Logger.printlnLog(LoggerLevel.LL_NONE, "\t" + "codLevel: " + this.codLevel.actualValue);
		Logger.printlnLog(LoggerLevel.LL_NONE, "\t" + "codMethod: " + this.codMethod.actualValue);
		Logger.printlnLog(LoggerLevel.LL_NONE, "\t" + "logLevel: " + this.logLevel.actualValue);
		Logger.printlnLog(LoggerLevel.LL_NONE, "\t" + "data: " + this.dataTotranscript);
		Logger.printlnLog(LoggerLevel.LL_NONE, "\t" + "cryptoFileIndex: " + this.cryptoFileIndex);

		this.loadGeneticTranslator();
		
		Logger.printlnLog(LoggerLevel.LL_NONE,  this.codCommand.isDecode()?"DECODE !":this.codCommand.isEncode()?"ENCODE !":"UNKNOWN COMMAND !" );
		System.out.printf(	"XXcodeCommand:%n\t\tcontent=%s, filePath=%s, directoryPath=%s%n"
							+ "dataToTranscript=%s%n\t\t"
							+ "methodSimple=%s, methodMore=%s, methodRand=%s%n",
					this.codLevel.isContent(), this.codLevel.isFilePath(), this.codLevel.isDirectoryPath(), 
					this.getDataToTranscript(), 
					this.codMethod.isSimple(), this.codMethod.isMore(), this.codMethod.isRandom());
		
//		switch(this.codCommand.actualValue) {
//		case decode : Logger.printlnLog(LoggerLevel.LL_ERROR, "decode STRATEGY");break;
//		case encode : Logger.printlnLog(LoggerLevel.LL_ERROR, "encode STRATEGY");break;
//		default : Logger.printlnLog(LoggerLevel.LL_ERROR, "UNKNOWN STRATEGY");
//		}
	}
	
	private void loadGeneticTranslator() {
		try { this.gt = BiologicalUtils.getGenericCrypto(this.cryptoFileIndex); }
		catch (NullPointerException npe) 
			{ Logger.printlnLog(LoggerLevel.LL_ERROR, "BAD CRYPTO FILE LOAD {" + this.cryptoFileIndex + "} !!"); }
		catch (ArrayIndexOutOfBoundsException aioobe) 
			{ Logger.printlnLog(LoggerLevel.LL_ERROR, "BAD CRYPTO FILE INDEX {" + this.cryptoFileIndex + "} !!"); }
		if (this.gt == null) { Logger.printlnLog(LoggerLevel.LL_ERROR, "GT NOT DEFINED !!"); }
	}
	
	public boolean isVerbose() { return verbose; }
	
	public LogLevel getLogLevel() { return this.logLevel; }
	
	public CodeLevel getCodeLevel() { return this.codLevel; }
	
	public CodeMethod getCodeMethod() { return this.codMethod; }
	
	public CodeCommand getCodeCommand() { return this.codCommand; }

	public String getDataToTranscript() { return this.dataTotranscript; }

	public GeneticTranslator getGeneticTranslator() { this.loadGeneticTranslator();return this.gt; }

}
