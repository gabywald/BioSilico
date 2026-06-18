package gabywald.creatures.launcher;

import java.util.Arrays;
import java.util.List;

import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.genetics.builds.CreatureGeneFactory;
import gabywald.creatures.genetics.builds.CreatureGeneListHelper;
import gabywald.creatures.genetics.builds.ICreatureGene;
import gabywald.creatures.launcher.CreaturesLauncher.GeneDenomination;
import gabywald.global.data.File;
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
		name = "biosilico-creatures",
		version = "1.0",
		description = "Application CLI with picocli.", 
		// subcommands = { EncodeCommand.class, DecodeCommand.class }, 
		mixinStandardHelpOptions = true)
public class BioSilicoCreaturesCommand implements Runnable {
	
	@Option(names = {"-g", "--geneticfile"}, arity = "1", required = true, 
			description = "Creatures Genetic (.gen) file to analyse.  ")
	String creaturesGENfile;
	
	@Option(names = {"-c", "--compare2file"}, arity = "1", required = false, 
			description = "Creatures Genetic (.gen) file to analyse and compare with first.  ")
	String creaturesGENfile2compare;
	
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
	
	@Override
	public void run() {
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "{" + this.creaturesGENfile + "}");
		
		List<ICreatureGene> cGENread1 = BioSilicoCreaturesCommand.analyseFile(this.creaturesGENfile);
		
		// ***** if second file : comparison !
		if (this.creaturesGENfile2compare != null) {
			List<ICreatureGene> cGENread2 = BioSilicoCreaturesCommand.analyseFile(this.creaturesGENfile2compare);
		}
	}
	
	public String getCreaturesGENfile()			{ return this.creaturesGENfile; }
	public String getCreaturesGENfile2compare()	{ return this.creaturesGENfile2compare; }
	
	public static List<ICreatureGene> analyseFile(String cGENfile) {
		File testIFexists = new File( cGENfile );
		if ( ! testIFexists.fileExists()) { 
			Logger.printlnLog(LoggerLevel.LL_FORUSER, "File {" + cGENfile + "} does not exists !");
			System.exit(1);
		}
		
		GeneticFileContent gfc = new GeneticFileContent( cGENfile );
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + cGENfile + "] -- {" + gfc.getFileName() + " }");
		
		List<ICreatureGene> genomeREAD = CreatureGeneFactory.readGenome( cGENfile );
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + cGENfile + "] -- {" + genomeREAD.size() + " genes}");
		
		// DONE a function to count a type / subtype of genes
		for (GeneDenomination gd : GeneDenomination.values()) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, gd.getType(), gd.getSubt()) + " " + gd.getName() + " genes}");
		}
		// DONE Sum up / comparison of size results ?! Reporting ? 
		int sumup = Arrays.asList(GeneDenomination.values()).stream()
				.map( gd -> CreatureGeneListHelper.getCountOf(genomeREAD, gd.getType(), gd.getSubt()) )
				.reduce(0, Integer::sum).intValue();
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + cGENfile + "] -- {" + sumup + " SumUp genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + cGENfile + "] -- " + ((genomeREAD.size() == sumup)?"OK all. ":"!! Missing or More ?? ") );
		
		// TODO graphical representation of Brain / Brain Lobes
		
		// TODO graphical representation of Instincts
		
		// TODO graphical representation of Stimulus
		
		// TODO exports of genome
		
		// TODO to modify genomes (?)
		
		return genomeREAD;
	}
	
}
