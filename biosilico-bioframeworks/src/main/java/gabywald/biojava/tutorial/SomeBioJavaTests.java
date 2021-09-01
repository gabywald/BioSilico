package gabywald.biojava.tutorial;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.RNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.io.GenbankReaderHelper;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import gabywald.utilities.others.PropertiesLoader;

/**
 * 
 * @author Gabriel Chandesris (2020, 2021)
 */
public class SomeBioJavaTests {

	public static void main(String[] args) {
		
		try {
			RNASequence rnaSequence = new RNASequence("GUAC");
			Logger.printlnLog(LoggerLevel.LL_INFO, rnaSequence.toString());
			
			DNASequence dnaSequence = new DNASequence("GTAC");
			Logger.printlnLog(LoggerLevel.LL_INFO, dnaSequence.toString());
			
			ProteinSequence protSequence = new ProteinSequence("MSTNPKPQRKTKRNTNRRPQDVKFPGG");
			Logger.printlnLog(LoggerLevel.LL_INFO, protSequence.toString());
			
		} catch (CompoundNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			LinkedHashMap<String, DNASequence> map = FastaReaderHelper.readFastaDNASequence( 
					PropertiesLoader.resolveFile( "src/main/resources/ls_orchid.fasta" ) // TODO do better !
					);
			for (String key : map.keySet() ) {
				Logger.printlnLog(LoggerLevel.LL_INFO, "fasta: \t" + key + "\t::\t" + map.get(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			LinkedHashMap<String, DNASequence> map = GenbankReaderHelper.readGenbankDNASequence( 
					PropertiesLoader.resolveFile( "src/main/resources/ls_orchid.gbk" ) // TODO do better !
					);
			for (String key : map.keySet() ) {
				Logger.printlnLog(LoggerLevel.LL_INFO, "genbank: \t" + key + "\t::\t" + map.get(key));
				DNASequence dnaSeq = map.get(key);
				Logger.printlnLog(LoggerLevel.LL_INFO, "\t\t\t" + dnaSeq.getGCCount() 
														+ "\t" + dnaSeq.getTaxonomy().getID() 
														+ "\t" + dnaSeq.getFeatures().size() );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
