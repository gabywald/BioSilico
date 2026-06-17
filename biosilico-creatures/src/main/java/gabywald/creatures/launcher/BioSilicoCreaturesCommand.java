package gabywald.creatures.launcher;

import java.util.List;

import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.genetics.builds.CreatureGeneFactory;
import gabywald.creatures.genetics.builds.CreatureGeneListHelper;
import gabywald.creatures.genetics.builds.ICreatureGene;
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
		
		File testIFexists = new File( this.creaturesGENfile );
		if ( ! testIFexists.fileExists()) { 
			Logger.printlnLog(LoggerLevel.LL_FORUSER, "File {" + creaturesGENfile + "} does not exists !");
			System.exit(1);
		}
		
		GeneticFileContent gfc = new GeneticFileContent( this.creaturesGENfile );
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + this.creaturesGENfile + "] -- {" + gfc.getFileName() + " }");
		
		List<ICreatureGene> genomeREAD = CreatureGeneFactory.readGenome( this.creaturesGENfile );
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + this.creaturesGENfile + "] -- {" + genomeREAD.size() + " genes}");
		
		// TODO a function to count a type / subtype of genes
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 0, 0) + " Brain Lobe genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 0, 1) + " Brain Organ genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 0, 2) + " Brain Tract genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 1, 0) + " Receptor genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 1, 1) + " Emitter genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 1, 2) + " Ch. Reaction genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 1, 3) + " Half-Lives genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 1, 4) + " Init Conc. genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 2, 0) + " Stimulus genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 2, 1) + " Genus genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 2, 2) + " Appearance genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 2, 3) + " Pose genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 2, 4) + " Gait genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 2, 5) + " Instinct genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 2, 6) + " Pigment genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 2, 7) + " Pigment bl. genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "\t{" + CreatureGeneListHelper.getCountOf(genomeREAD, 3, 0) + " Organ genes}");
		// Logger.printlnLog(LoggerLevel.LL_INFO, "\t" + CreatureGeneListHelper.getCountOf(genomeREAD, 3, 0) + " ... genes}");
		// TODO Sum up / comparison of size results ?! Reporting ? (foreach lopp with different Int Range loops ?)
		int sumup = CreatureGeneListHelper.getCountOf(genomeREAD, 0, 0) 
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 0, 1)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 0, 2)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 1, 0)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 1, 1)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 1, 2)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 1, 3)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 1, 4)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 2, 0)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 2, 1)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 2, 2)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 2, 3)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 2, 4)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 2, 5)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 2, 6)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 2, 7)
					+ CreatureGeneListHelper.getCountOf(genomeREAD, 3, 0);
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + this.creaturesGENfile + "] -- {" + sumup + " SumUp genes}");
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + this.creaturesGENfile + "] -- " + ((genomeREAD.size() == sumup)?"OK all. ":"!! Missing or More ?? ") );
		
		// TODO exports of genome
		// TODO to modify genomes (?)
	}
	
	public String getCwreaturesGENfile() { return this.creaturesGENfile; }

}
