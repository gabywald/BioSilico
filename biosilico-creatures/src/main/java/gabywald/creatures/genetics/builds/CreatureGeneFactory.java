package gabywald.creatures.genetics.builds;

import java.util.ArrayList;
import java.util.List;

import gabywald.creatures.exceptions.CreatureGeneException;
import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.genetics.AppearanceGene;
import gabywald.creatures.genetics.BioChemicalEmitterGene;
import gabywald.creatures.genetics.BioChemicalReactionGene;
import gabywald.creatures.genetics.BioChemicalReceptorGene;
import gabywald.creatures.genetics.BrainLobeGene;
import gabywald.creatures.genetics.GaitGene;
import gabywald.creatures.genetics.GenusGene;
import gabywald.creatures.genetics.HalfLivesGene;
import gabywald.creatures.genetics.InitialConcentrationGene;
import gabywald.creatures.genetics.InstinctGene;
import gabywald.creatures.genetics.PigmentBleedingGene;
import gabywald.creatures.genetics.PigmentGene;
import gabywald.creatures.genetics.PoseGene;
import gabywald.creatures.genetics.StimulusGene;
import gabywald.creatures.model.UnsignedByte;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2013, 2026)
 */
public abstract class CreatureGeneFactory {

	public static void setHeaderInformations(	ICreatureGene cg, 
												int iden, 
												int numg, 
												int agee, 
												int flags, 
												int mutr) {
		cg.setSequenceNumber(iden);
		cg.setDuplicateNumber(numg);
		cg.setSwitchStage(agee);
		cg.setFlags(flags);
		cg.setMutationRate(mutr);
	}

	public static void setHeaderInformations(	ICreatureGene cg, 
												UnsignedByte iden, 
												UnsignedByte numg, 
												UnsignedByte agee, 
												UnsignedByte flags, 
												UnsignedByte mutr) {
		cg.setSequenceNumber(iden);
		cg.setDuplicateNumber(numg);
		cg.setSwitchStage(agee);
		cg.setFlags(flags);
		cg.setMutationRate(mutr);
	}
	
	public static ICreatureGene readGene(String content, UnsignedByte creatureVersion) {
		if (!content.startsWith("gene")) { return null; }
		
		int index	= 4;
		int type	= content.charAt(index++); // 4
		int subt	= content.charAt(index++); // 5
		int iden	= content.charAt(index++); // 6
		int numg	= content.charAt(index++); // 7
		int agee	= content.charAt(index++); // 8
		int flags	= content.charAt(index++); // 9
		/** For Creatures 2 and Creatures 3 : mutation rate. */
		int mutr	= 80; /** C1 by  default. */
		if (creatureVersion.getValue() >= 2) 
			{ mutr  = content.charAt(index++); }  // 10 // mutability
		/** For Creature 3 / Village. */
//		int varia	= 0;
//		if (creatureVersion.getValue() >= 3) 
//			{ varia = content.charAt(index++); } // 11 // variant (turn on / off among species)
		
		// System.out.println("\t[" + type + "]\t[" + subt + "]\t[" 
		// 						+ iden + "]\t[" + numg + "]\t[" 
		// 						+ agee + "]\t[" + flags + "]" );
		
		// System.out.println("\t[" + muta + "]" );
		// System.out.println("\t[" + varia + "]" );
		
		/** For log... */
		StringBuilder toTestLog	= new StringBuilder("");
		
		ICreatureGene toReturn	= null;
		StringBuilder sbToShow	= new StringBuilder();
		
		List<UnsignedByte> datas = new ArrayList<UnsignedByte>();
		for (int j = index ; j < content.length() ; j++) 
			{ datas.add(new UnsignedByte((int)content.charAt(j))); }
		
		try {
			switch(type) {
			case(0):
				switch(subt) {
				case(0):
					sbToShow.append( CreatureGeneFactory.datasToString("Brain Lobe Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(0, 0, datas, 5 + 13 + 10 + 37 + 10 + 37)) ? 
							/* size expected is 5 + 13 + 10 + 37 + 10 + 37 ! */
							new BrainLobeGene(datas) : null; 
					toReturn = new BrainLobeGene(datas);
					break;
				case(1):
					sbToShow.append( CreatureGeneFactory.datasToString("Brain Organ Gene", datas) );
					sbToShow.append( "\t\t(" + content.length() + ") - (" + index + ")" ).append("\t\t");
					for (int j = index ; j < content.length() ; j++) 
						{ sbToShow.append( "[" + (int)content.charAt(j) + "]\t" ); }
					break;
				case(2):
					sbToShow.append( CreatureGeneFactory.datasToString("Brain Tract Gene", datas) );
					sbToShow.append( "\t\t(" + content.length() + ") - (" + index + ")").append("\t\t");
					for (int j = index ; j < content.length() ; j++) 
						{ sbToShow.append( "[" + (int)content.charAt(j) + "]\t" ); }
					break;
				default:Logger.printlnLog(LoggerLevel.LL_WARNING, "\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
				} /** END "switch(subt)" */
				break;
			case(1):
				switch(subt) {
				case(0):
					sbToShow.append( CreatureGeneFactory.datasToString("Receptor Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(1, 0, datas, 8)) ? 
							new BioChemicalReceptorGene(	datas.get(0), datas.get(1), datas.get(2), datas.get(3), 
															datas.get(4), datas.get(5), datas.get(6), datas.get(7)) : null;
					break;
				case(1):
					sbToShow.append( CreatureGeneFactory.datasToString("Emitter Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(1, 1, datas, 8)) ? 
							new BioChemicalEmitterGene(	datas.get(0), datas.get(1), datas.get(2), datas.get(3), 
														datas.get(4), datas.get(5), datas.get(6), datas.get(7)) : null;
					break;
				case(2):
					sbToShow.append( CreatureGeneFactory.datasToString("Ch. Reaction Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(1, 2, datas, 9)) ? 
							new BioChemicalReactionGene(	datas.get(0), datas.get(1), datas.get(2), datas.get(3), 
															datas.get(4), datas.get(5), datas.get(6), datas.get(7), 
															datas.get(8)) : null;
					break;
				case(3):
					// Logger.printlnLog(LoggerLevel.LL_WARNING, "\t " + content.length() + " - " + datas.size() + " - " + type +" - " + subt +" - " + iden + " - " + numg + " - " + agee + " - " + flags + " - " + mutr + "");
					sbToShow.append( CreatureGeneFactory.datasToString("Half-Lives Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(1, 3, datas, 256)) ? /** C2 : 255 */
							new HalfLivesGene(datas) : null; 
				break;
				case(4):
					// Logger.printlnLog(LoggerLevel.LL_WARNING, "\t " + content.length() + " - " + datas.size() + " - " + type +" - " + subt +" - " + iden + " - " + numg + " - " + agee + " - " + flags + " - " + mutr + "");
					/** int chemic = (int)content.charAt(index++); */
					/** int amount = (int)content.charAt(index++); */
					sbToShow.append( CreatureGeneFactory.datasToString("Init. Conc. Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(1, 4, datas, 2)) ? 
							new InitialConcentrationGene(datas.get(0), datas.get(1)) : null;
					break;
				default:Logger.printlnLog(LoggerLevel.LL_WARNING, "\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
				} /** END "switch(subt)" */
				break;
			case(2):
				switch(subt) {
				case(0):
					sbToShow.append( CreatureGeneFactory.datasToString("Stimulus Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(2, 0, datas, 13)) ? 
							new StimulusGene(	datas.get( 0), datas.get( 1), datas.get( 2), 
												datas.get( 3), datas.get( 4), datas.get( 5), 
												datas.get( 6), datas.get( 7), datas.get( 8), 
												datas.get( 9), datas.get(10), datas.get(11), 
												datas.get(12) ) : null;
					break;
				case(1):
					sbToShow.append( CreatureGeneFactory.datasToString("Genus Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(2, 1, datas, 1 + 4 + 4)) ? 
							new GenusGene(	datas.get(0), 
											CreatureGeneFactory.datasSegment(datas, 1, 4), 
											CreatureGeneFactory.datasSegment(datas, 5, 8)) : null;
					/** Logger.printlnLog(LoggerLevel.LL_INFO,  "\t\t genus Gene mother => [" + ((GenusGene)toReturn).getMotherID() + "]" ); */
					/** Logger.printlnLog(LoggerLevel.LL_INFO,  "\t\t genus Gene father => [" + ((GenusGene)toReturn).getFatherID() + "]" ); */
					break;
				case(2):// 	
					/** int body = (int)content.charAt(index++); */
					/** int spri = (int)content.charAt(index++); */
					sbToShow.append( CreatureGeneFactory.datasToString("Appearance Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(2, 2, datas, 2)) ? 
							new AppearanceGene(datas.get(0), datas.get(1)) : null;
					break;
				case(3):
					/** int kind	= (int)content.charAt(index++); */
					/**
					String info	= new String(""); 
					for (int j = index ; j < content.length() ; j++) 
						{ info += content.charAt(j); } */
					sbToShow.append( CreatureGeneFactory.datasToString("Pose Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(2, 3, datas, 1 + 15)) ? 
							new PoseGene(	datas.get(0), /* NOTE Pose Gene size expected is 16 (C3 : 17) ! */
											CreatureGeneFactory.datasSegment(datas, 1, 15)) : null;
					break;
				case(4):
					sbToShow.append( CreatureGeneFactory.datasToString("Gait Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(2, 4, datas, 9)) ? 
							new GaitGene(	datas.get(0), 
											datas.get(1), datas.get(2), datas.get(3), datas.get(4), 
											datas.get(5), datas.get(6), datas.get(7), datas.get(8)) : null;
					break;
				case(5):
					sbToShow.append( CreatureGeneFactory.datasToString("Instinct Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(2, 5, datas, 9)) ? 
							new InstinctGene(	datas.get(0), 
												datas.get(1), datas.get(2), datas.get(3), datas.get(4), 
												datas.get(5), datas.get(6), datas.get(7), datas.get(8)) : null;
					break;
				case(6):
					/** int colo = (int)content.charAt(index++); */
					/** int amou = (int)content.charAt(index++); */
					sbToShow.append( CreatureGeneFactory.datasToString("Pigment Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(2, 6, datas, 2)) ? 
							new PigmentGene(datas.get(0), datas.get(1)) : null;
					break;
				case(7):
					/** int rota = (int)content.charAt(index++); */
					/** int swap = (int)content.charAt(index++); */
					sbToShow.append( CreatureGeneFactory.datasToString("Pigment bl. Gene", datas) );
					toReturn = (CreatureGeneFactory.buildGenericGene(2, 7, datas, 9)) ? 
							new PigmentBleedingGene( datas.get(0), datas.get(1) ) : null;
					break;
				default:Logger.printlnLog(LoggerLevel.LL_WARNING, "\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
				} /** END "switch(subt)" */
				break;
			case(3):
				switch(subt) {
				case(0):Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Organ Gene");		
					Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t\t(" + content.length() + ") - (" + index + ")");
					toTestLog.append("\t\t");
					for (int j = index ; j < content.length() ; j++) 
						{ toTestLog.append("[").append((int)content.charAt(j)).append("]\t"); }
					Logger.printlnLog(LoggerLevel.LL_DEBUG,  toTestLog.toString() );
					break;
				default:Logger.printlnLog(LoggerLevel.LL_WARNING, "\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
				} /** END "switch(subt)" */
				break;
			default:Logger.printlnLog(LoggerLevel.LL_WARNING, "\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
			} /** END "switch(type)" */
			Logger.printlnLog(LoggerLevel.LL_NONE, sbToShow.toString() );
		} catch (CreatureGeneException cge) { 
			Logger.printlnLog(LoggerLevel.LL_ERROR, "\t EXCEPTION [" + cge.toString() + "]");
			// cge.printStackTrace();
		}
		
		if (toReturn != null) 
			{ CreatureGeneFactory.setHeaderInformations(toReturn, iden, numg, agee, flags, mutr); }
		
		return toReturn;
	}
	
	public static List<ICreatureGene> readGenome(String filePath) {
		List<ICreatureGene> toReturn	= new ArrayList<ICreatureGene>();
		
		GeneticFileContent gtc		= new GeneticFileContent(filePath);
		
		String previous				= new String("");
		String nextNext				= new String("");
		while ( (gtc.isReadable()) && (!previous.startsWith("gend")) ) {
			char tmpChar = gtc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				nextNext += tmpChar;
				if ( (nextNext.endsWith("gene")) || (nextNext.endsWith("gend")) ) {
					previous = nextNext.substring(0, nextNext.length()-4);
					nextNext = nextNext.substring(nextNext.length()-4, nextNext.length());
					if ( ( ! previous.equals("")) && ( ! previous.startsWith("gend")) ) {
						ICreatureGene toRecord = CreatureGeneFactory.readGene(previous, new UnsignedByte(1));
						if (toRecord == null) 
							{ Logger.printlnLog(LoggerLevel.LL_ERROR, "-- CreatureGene NULL ! [" + previous + "]"); }
						else { toReturn.add(toRecord); }
					} else 
						{ Logger.printlnLog(LoggerLevel.LL_WARNING, "-- {" + nextNext + "}"); }
				} /** END "if ( (nextNext.endsWith("gene")) || (nextNext.endsWith("gend")) )" */
			} // else { Logger.printlnLog(LoggerLevel.LL_WARNING, "not a char ?"); }
		} /** END "while (gtc.isReadable()))" */
		
		return toReturn;
	}
	
	/**
	 * 
	 * @param type
	 * @param subtype
	 * @param datas
	 * @param expectedSize
	 * @return
	 * @throws CreatureGeneException
	 */
	public static boolean buildGenericGene(	int type, int subtype, 
											List<UnsignedByte> datas, 
											int expectedSize) throws CreatureGeneException {
		if (datas.size() != expectedSize) 
		 	{ throw new CreatureGeneException(type, subtype, "Bad number of datas e:" + expectedSize + " [" + datas.size() + "]"); }
		return (datas.size() == expectedSize);
	}
	
	public static String datasToString(String geneName, List<UnsignedByte> datas) {
		StringBuilder sbToReturn = new StringBuilder();
		sbToReturn.append("\t").append(" ").append(geneName).append(" ").append("[");
		datas.stream().map( data -> data + ", ").forEach( str -> sbToReturn.append(str) );
		sbToReturn.replace(sbToReturn.length() - 2, sbToReturn.length(), "]");
		return sbToReturn.toString();
	}
	
	public static String datasSegment(List<UnsignedByte> datas, int start, int stop) {
		StringBuilder sbToReturn = new StringBuilder();
		if ( (stop < start) || (start < 0) || (stop > datas.size()) ) 
			{ return sbToReturn.toString(); }
 		// for (int i = start ; i < stop ; i++) { sbToReturn.append((char)datas.get(i).getValue()); }
		datas.subList(start, stop+1).stream().map( data -> (char)data.getValue()).forEach(sbToReturn::append);
		return sbToReturn.toString();
	}
	
}
