package gabywald.creatures.genetics;

import java.util.ArrayList;
import java.util.List;

import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.model.UnsignedByte;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2013)
 */
public class CreatureGeneFactory {

	public static void setHeaderInformations(	CreatureGene cg, 
												int iden, 
												int numg, 
												int agee, 
												int flags) {
		cg.setSequenceNumber(iden);
		cg.setDuplicateNumber(numg);
		cg.setSwitchStage(agee);
		cg.setFlags(flags);
	}
	
	public static void setHeaderInformations(	CreatureGene cg, 
												UnsignedByte iden, 
												UnsignedByte numg, 
												UnsignedByte agee, 
												UnsignedByte flags) {
		cg.setSequenceNumber(iden);
		cg.setDuplicateNumber(numg);
		cg.setSwitchStage(agee);
		cg.setFlags(flags);
	}
	
	public static void setHeaderInformations(	CreatureGene cg, 
												int iden, 
												int numg, 
												int agee, 
												int flags, 
												int mutr) {
		CreatureGeneFactory.setHeaderInformations(cg, iden, numg, agee, flags);
		cg.setMutationRate(mutr);
	}

	public static void setHeaderInformations(	CreatureGene cg, 
												UnsignedByte iden, 
												UnsignedByte numg, 
												UnsignedByte agee, 
												UnsignedByte flags, 
												UnsignedByte mutr) {
		CreatureGeneFactory.setHeaderInformations(cg, iden, numg, agee, flags);
		cg.setMutationRate(mutr);
	}
	
	public static CreatureGene readGene(String content, UnsignedByte creatureVersion) {
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
		String toTest			= new String("");
		
		CreatureGene toReturn	= null;
		
		List<UnsignedByte> datas = new ArrayList<UnsignedByte>();
		for (int j = index ; j < content.length() ; j++) 
			{ datas.add(new UnsignedByte((int)content.charAt(j))); }
		
		try {
			switch(type) {
			case(0):
				switch(subt) {
				case(0):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Brain Lobe Gene");	
//					Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t\t(" + content.length() + ") - (" + index + ")");
//					for (int j = index ; j < content.length() ; j++) 
//						{ toTest += "[" + (int)content.charAt(j) + "]\t"; }
//					Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t\t" + toTest );
					toReturn = new BrainLobeGene(datas);
					break;
				case(1):Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Brain Organ Gene");	
					Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t\t(" + content.length() + ") - (" + index + ")");
					for (int j = index ; j < content.length() ; j++) 
						{ toTest += "[" + (int)content.charAt(j) + "]\t"; }
					Logger.printlnLog(LoggerLevel.LL_DEBUG,  "\t\t" + toTest );
					break;
				case(2):Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Brain Tract Gene");
					Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t\t(" + content.length() + ") - (" + index + ")");
					for (int j = index ; j < content.length() ; j++) 
						{ toTest += "[" + (int)content.charAt(j) + "]\t"; }
					Logger.printlnLog(LoggerLevel.LL_DEBUG,  "\t\t" + toTest );
					break;
				default:Logger.printlnLog(LoggerLevel.LL_WARNING, "\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
				} /** END "switch(subt)" */
				break;
			case(1):
				switch(subt) {
				case(0):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Receptor Gene");	
					toReturn = new BioChemicalReceptorGene(datas);
					break;
				case(1):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Emitter Gene");		
					toReturn = new BioChemicalEmitterGene(datas);
					break;
				case(2):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Chemical R. Gene");	
					toReturn = new BioChemicalReactionGene(datas);
					break;
				case(3):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Half-Lives Gene");	
					Logger.printlnLog(LoggerLevel.LL_WARNING, "\t " + content.length() + " - " + datas.size() + " - " + type +" - " + subt +" - " + iden + " - " + numg + " - " + agee + " - " + flags + " - " + mutr + "");
					toReturn = new HalfLivesGene(datas);
				break;
				case(4):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Init. Conc. Gene");
					Logger.printlnLog(LoggerLevel.LL_WARNING, "\t " + content.length() + " - " + datas.size() + " - " + type +" - " + subt +" - " + iden + " - " + numg + " - " + agee + " - " + flags + " - " + mutr + "");
					/** int chemic = (int)content.charAt(index++); */
					/** int amount = (int)content.charAt(index++); */
					toReturn = new InitialConcentrationGene(datas);
					break;
				default:Logger.printlnLog(LoggerLevel.LL_WARNING, "\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
				} /** END "switch(subt)" */
				break;
			case(2):
				switch(subt) {
				case(0):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Stimulus Gene");		
	//				for (int k = 0 ; k < datas.size() ; k++) 
	//					{ Logger.printlnLog(LoggerLevel.LL_DEBUG,  "\t\t" + (k+1) + " / " + datas.size() + " :: [" + datas.get(k).getValue() + "]" ); }
					toReturn = new StimulusGene(datas);
					break;
				case(1):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Genus Gene");		
					/** int speci = (int)content.charAt(index++); */
					/** String moth = content.substring(index + 0, index + 4); */
					/** String fath = content.substring(index + 4, index + 8); */
					// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t{" + moth + "}\t{" + fath + "}");
					toReturn = new GenusGene(datas);
					/** Logger.printlnLog(LoggerLevel.LL_INFO,  "\t\t genus Gene mother => [" + ((GenusGene)toReturn).getMotherID() + "]" ); */
					/** Logger.printlnLog(LoggerLevel.LL_INFO,  "\t\t genus Gene father => [" + ((GenusGene)toReturn).getFatherID() + "]" ); */
				break;
				case(2):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Appearance Gene");	
					/** int body = (int)content.charAt(index++); */
					/** int spri = (int)content.charAt(index++); */
					toReturn = new AppearanceGene(datas); /** new AppearanceGene(body, spri); */
					break;
				case(3):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Pose Gene");	
					/** int kind	= (int)content.charAt(index++); */
					/**
					String info	= new String(""); 
					for (int j = index ; j < content.length() ; j++) 
						{ info += content.charAt(j); }
					*/
					// Logger.printlnLog(LoggerLevel.LL_DEBUG,  "\t\t pose Gene info [" + info + "]" );
					toReturn = new PoseGene(datas);
					// Logger.printlnLog(LoggerLevel.LL_INFO,  "\t\t pose Gene info => [" + ((PoseGene)toReturn).getInfos() + "]" );
					break;
				case(4):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Gait Gene");			
					toReturn = new GaitGene(datas);
					break;
				case(5):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Instinct Gene");
					toReturn = new InstinctGene(datas);
					break;
				case(6):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Pigment Gene");		
					/** int colo = (int)content.charAt(index++); */
					/** int amou = (int)content.charAt(index++); */
					toReturn = new PigmentGene(datas);
					break;
				case(7):// Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Pigment bl. Gene");	
					/** int rota = (int)content.charAt(index++); */
					/** int swap = (int)content.charAt(index++); */
					toReturn = new PigmentBleedingGene(datas);
					break;
				default:Logger.printlnLog(LoggerLevel.LL_WARNING, "\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
				} /** END "switch(subt)" */
				break;
			case(3):
				switch(subt) {
				case(0):Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t Organ Gene");		
					Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t\t(" + content.length() + ") - (" + index + ")");
					for (int j = index ; j < content.length() ; j++) 
						{ toTest += "[" + (int)content.charAt(j) + "]\t"; }
					Logger.printlnLog(LoggerLevel.LL_DEBUG,  "\t\t" + toTest );
					break;
				default:Logger.printlnLog(LoggerLevel.LL_WARNING, "\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
				} /** END "switch(subt)" */
				break;
			default:Logger.printlnLog(LoggerLevel.LL_WARNING, "\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
			} /** END "switch(type)" */
		} catch (CreatureGeneException cge) { 
			Logger.printlnLog(LoggerLevel.LL_ERROR, "\t EXCEPTION [" + cge.toString() + "]");
			// cge.printStackTrace();
		}
		
		if (toReturn != null) 
			{ CreatureGeneFactory.setHeaderInformations(toReturn, iden, numg, agee, flags, mutr); }
		
		return toReturn;
	}
	
	public static List<CreatureGene> readGenome(String filePath) {
		List<CreatureGene> toReturn	= new ArrayList<CreatureGene>();
		
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
						CreatureGene toRecord = CreatureGeneFactory.readGene(previous, new UnsignedByte(1));
						if (toRecord == null) 
							{ Logger.printlnLog(LoggerLevel.LL_ERROR, "-- CreatureGene NULL ! [" + previous + "]"); }
						else { toReturn.add(toRecord); }
					} else 
						{ Logger.printlnLog(LoggerLevel.LL_WARNING, "-- {" + nextNext + "}"); }
				}
			} // else { Logger.printlnLog(LoggerLevel.LL_WARNING, "not a char ?"); }
		} /** END "while (gtc.isReadable()))" */
		
		return toReturn;
	}
	
	
}
