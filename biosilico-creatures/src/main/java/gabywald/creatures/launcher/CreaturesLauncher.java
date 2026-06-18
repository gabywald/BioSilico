package gabywald.creatures.launcher;

import gabywald.creatures.model.UnsignedByte;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import picocli.CommandLine;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public class CreaturesLauncher {

	public static void main(String[] args) {
		BioSilicoCreaturesCommand bscc = new BioSilicoCreaturesCommand();
		int exitCode = new CommandLine(bscc).execute(args);
		// System.exit(exitCode);
		if (bscc.isLogEnabled(BioSilicoCreaturesCommand.LogLevel.TheEnum.info)) 
					{ Logger.printlnLog(LoggerLevel.LL_FORUSER, exitCode + "" ); }
	}
	
	public enum GeneDenomination {
		BRAIN_LOBE_GENE	("Brain Lobe", 0, 0),
		BRAIN_ORGAN_GENE("Brain Organ", 0, 1),
		BRAIN_TRACT_GENE("Brain Tract", 0, 2),
		RECEPTOR_GENE			("Receptor", 1, 0),
		EMITTER_GENE			("Emitter", 1, 1),
		CHEMICAL_REACTION_GENE	("Ch. Reaction", 1, 2),
		HALF_LIVES_GENE			("Half-Lives", 1, 3),
		INITCONC_GENE			("Init. Conc.", 1, 4),
		STIMULUS_GENE			("Stimulus", 2, 0),
		GENUS_GENE				("Genus", 2, 1),
		APPEARANCE_GENE			("Appearance", 2, 2),
		POSE_GENE				("Pose", 2, 3),
		GAIT_GENE				("Gait", 2, 4),
		INSTINCT_GENE			("Instinct", 2, 5),
		PIGMENT_GENE			("Pigment", 2, 6),
		PIGMENT_BLEED_GENE		("Pigment bl.", 2, 7),
		ORGAN_GENE		("Organ", 3, 0);

		GeneDenomination(String name, int type, int subt) {
			this.name = name;
			this.type = new UnsignedByte(type);
			this.subt = new UnsignedByte(subt);
		}
		
		private String name = null;
		private UnsignedByte type = null;
		private UnsignedByte subt = null;
		
		public String getName()			{ return this.name; }
		public UnsignedByte getType()	{ return this.type; }
		public UnsignedByte getSubt()	{ return this.subt; }
	}
	
}
