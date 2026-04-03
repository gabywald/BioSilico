package gabywald.crypto.launcher;

import java.util.HashMap;
import java.util.Map;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.model.GeneticTranslator;
import gabywald.crypto.model.ITranslator;
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
	
	private static Map<CodeCommand.TheEnum, IStrategyCommand> strategies = new HashMap<>();
	static {
		BioSilicoCryptoCommand.strategies.put(CodeCommand.TheEnum.encode, new EncodeStrategyCommand());
		BioSilicoCryptoCommand.strategies.put(CodeCommand.TheEnum.decode, new DecodeStrategyCommand());
	}
	
	/**
	 * Code Command. 
	 */
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
	 * Code Method to use (simple, more, random). 
	 */
	static class CodeMethod {

		ITranslator.TranslatorEnum actualValue = ITranslator.TranslatorEnum.simple;

		@Option(names = {"-s", "--simple"}, 
				description = "Simple code Method.")
		void setSimple(boolean b)	{ this.actualValue = ITranslator.TranslatorEnum.simple; }

		@Option(names = {"-m", "--more"}, 
				description = "More code Method. ")
		void setMore(boolean b)		{ this.actualValue = ITranslator.TranslatorEnum.more; }

		@Option(names = {"-r", "--random"}, 
				description = "Random code Method. ")
		void setRand(boolean b)		{ this.actualValue = ITranslator.TranslatorEnum.random; }
		
		boolean isSimple()	{ return (this.actualValue == ITranslator.TranslatorEnum.simple); }
		boolean isMore()	{ return (this.actualValue == ITranslator.TranslatorEnum.more); }
		boolean isRandom()	{ return (this.actualValue == ITranslator.TranslatorEnum.random); }
	}
	@ArgGroup(exclusive = true, heading = "Code Method Options%n", multiplicity = "1")
	CodeMethod codMethod = new CodeMethod();
	
	static class OutputType {
		enum TheEnum { direct, fasta, embl, genbank }

		TheEnum actualValue = TheEnum.direct;

		@Option(names = {"-t", "--direct"}, 
				description = "Direct Output Type.")
		void setDirect(boolean b)	{ this.actualValue = TheEnum.direct; }
		
		@Option(names = {"-a", "--fasta"}, 
				description = "FASTA Output Type. ")
		void setFasta(boolean b)	{ this.actualValue = TheEnum.fasta; }

		@Option(names = {"-l", "--embl"}, 
				description = "EMBL Output Type. ")
		void setEMBL(boolean b)		{ this.actualValue = TheEnum.embl; }

		@Option(names = {"-k", "--genbank"}, 
				description = "GENBANK Output Type. ")
		void setGenBank(boolean b)	{ this.actualValue = TheEnum.genbank; }
		
		boolean isDirect()	{ return (this.actualValue == TheEnum.direct); }
		boolean isFasta()	{ return (this.actualValue == TheEnum.fasta); }
		boolean isEMBL()	{ return (this.actualValue == TheEnum.embl); }
		boolean isGenBank()	{ return (this.actualValue == TheEnum.genbank); }
	}
	@ArgGroup(exclusive = true, heading = "Output Type Options%n", multiplicity = "0..1")
	OutputType outputType = new OutputType();

	/**
	 * Log Level. 
	 */
	public static class LogLevel {
		// public enum TheEnum { none, error, warn, info, debug, trace }
		public enum TheEnum { trace, debug, info, warn, error, none }

		TheEnum actualValue = TheEnum.none;

		@Option(names = "--debug", description = "Sets log level to DEBUG.")
		void setDebug(boolean b)	{ this.actualValue = TheEnum.debug; }

		@Option(names = "--info", description = "Sets log level to INFO.")
		void setInfo(boolean b)		{ this.actualValue = TheEnum.info; }

		@Option(names = "--warn", description = "Sets log level to WARN.")
		void setWarn(boolean b)		{ this.actualValue = TheEnum.warn; }
		
		@Option(names = "--error", description = "Sets log level to ERROR.")
		void setError(boolean b)	{ this.actualValue = TheEnum.error; }
		
		@Option(names = "--trace", description = "Sets log level to NONE.")
		void setTrace(boolean b)	{ this.actualValue = TheEnum.trace; }
		
		@Option(names = "--none", description = "Sets log level to NONE.")
		void setNone(boolean b)		{ this.actualValue = TheEnum.none; }
		
	}
	@ArgGroup(exclusive = true, heading = "Log Level Options%n", multiplicity = "0..1")
	LogLevel logLevel = new LogLevel();
	
	public boolean isLogEnabled(LogLevel.TheEnum checkValue) {
		return checkValue.ordinal() >= logLevel.actualValue.ordinal();
	}
	
	public LogLevel.TheEnum setLogLevel(LogLevel.TheEnum nextValue) { 
		LogLevel.TheEnum prevValue = this.logLevel.actualValue;
		this.logLevel.actualValue = nextValue;
		return prevValue;
	}
	
	@Option(names = {"-i", "--cryptofileindex"}, 
			description = "Crypto File Index", 
			defaultValue = "0", 
			hidden = true)
	private int cryptoFileIndex;
	GeneticTranslator gtFile = null;
	
	@Option(names = {"-j", "--cryptopathindex"}, 
			description = "Crypto Path Index", 
			defaultValue = "0", 
			hidden = true)
	private int cryptoPathIndex;
	GeneticTranslator gtPath = null;
	
	@Option(names = {"-D", "--DATA"}, arity = "1", required = true, 
			description = "Data to transcript (content, path to file or path to directory). ")
	String dataTotranscript;
	
	@Override
	public void run() {
		if (this.isLogEnabled(LogLevel.TheEnum.debug)) { Logger.setLogLevel(LoggerLevel.LL_DEBUG); }
		if (this.isLogEnabled(LogLevel.TheEnum.info)) { Logger.setLogLevel(LoggerLevel.LL_INFO); }
		if (this.isLogEnabled(LogLevel.TheEnum.warn)) { Logger.setLogLevel(LoggerLevel.LL_WARNING); }
		if (this.isLogEnabled(LogLevel.TheEnum.error)) { Logger.setLogLevel(LoggerLevel.LL_ERROR); }
		if (this.isLogEnabled(LogLevel.TheEnum.none)) { Logger.setLogLevel(LoggerLevel.LL_ERROR); }
		
		if ( (this.dataTotranscript.startsWith("\"")) && (this.dataTotranscript.endsWith("\"")) )
				{ this.dataTotranscript = this.dataTotranscript.substring(1, this.dataTotranscript.length() - 1); }
		
		if (this.isLogEnabled(LogLevel.TheEnum.debug)) {
			Logger.printlnLog(LoggerLevel.LL_DEBUG, this.toString());
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t" + "codLevel: " + this.codLevel.actualValue);
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t" + "codMethod: " + this.codMethod.actualValue);
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t" + "logLevel: " + this.logLevel.actualValue);
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t" + "data: " + this.dataTotranscript);
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t" + "cryptoFileIndex: " + this.cryptoFileIndex);
			
			Logger.printlnLog(LoggerLevel.LL_DEBUG, this.codCommand.isDecode()?"DECODE !":this.codCommand.isEncode()?"ENCODE !":"UNKNOWN COMMAND !" );
		}

		this.loadGeneticTranslators();
		
		if (this.isLogEnabled(LogLevel.TheEnum.info)) {
			String formatted = String.format( "XXcodeCommand:%n\t\tcontent=%s, filePath=%s, directoryPath=%s, %n"
								+ "\t\tdataToTranscript=%s,subcommand=%s, %n"
								+ "\t\tmethodSimple=%s, methodMore=%s, methodRand=%s%n",
						this.codLevel.isContent(), this.codLevel.isFilePath(), this.codLevel.isDirectoryPath(), 
						this.getDataToTranscript(), this.codCommand.actualValue, 
						this.codMethod.isSimple(), this.codMethod.isMore(), this.codMethod.isRandom());
			Logger.printlnLog(LoggerLevel.LL_FORUSER, formatted );
		}
		if (this.isLogEnabled(LogLevel.TheEnum.debug)) 
			{ Logger.printlnLog(LoggerLevel.LL_DEBUG, this.codCommand.actualValue + ""); }
		
		if (BioSilicoCryptoCommand.strategies.containsKey(this.codCommand.actualValue)) 
			{ BioSilicoCryptoCommand.strategies.get(this.codCommand.actualValue).execute
				(this.dataTotranscript, this.codLevel, this.codMethod, this.outputType, this.logLevel, this.gtFile, this.gtPath); }
		else { Logger.printlnLog(LoggerLevel.LL_ERROR, "UNKNOWN STRATEGY / COMMAND !!"); }
	}
	
	private void loadGeneticTranslators() {
		this.gtFile = BioSilicoCryptoCommand.loadGT(this.cryptoFileIndex, true);
		this.gtPath = BioSilicoCryptoCommand.loadGT(this.cryptoPathIndex, false);
	}
	
	private static GeneticTranslator loadGT(int index, boolean fileORpath) {
		GeneticTranslator gtToReturn = null;
		try { gtToReturn = BiologicalUtils.getGenericCrypto( index ); }
		catch (NullPointerException npe) 
			{ Logger.printlnLog(LoggerLevel.LL_ERROR, "BAD CRYPTO " + (fileORpath?"FILE":"PATH") + " LOAD {" + index + "} !!"); }
		catch (ArrayIndexOutOfBoundsException aioobe) 
			{ Logger.printlnLog(LoggerLevel.LL_ERROR, "BAD CRYPTO " + (fileORpath?"FILE":"PATH") + " INDEX {" + index + "} !!"); }
		if (gtToReturn == null) { Logger.printlnLog(LoggerLevel.LL_ERROR, "GT" + (fileORpath?"FILE":"PATH") + " NOT DEFINED !!"); }
		return gtToReturn;
	}
	
	public LogLevel getLogLevel() { return this.logLevel; }
	
	public CodeLevel getCodeLevel() { return this.codLevel; }
	
	public CodeMethod getCodeMethod() { return this.codMethod; }
	
	public CodeCommand getCodeCommand() { return this.codCommand; }

	public String getDataToTranscript() { return this.dataTotranscript; }

	public GeneticTranslator getGTFile() { return this.gtFile; }
	
	public GeneticTranslator getGTPath() { return this.gtPath; }

}
