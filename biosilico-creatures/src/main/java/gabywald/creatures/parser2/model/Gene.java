package gabywald.creatures.parser2.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente un gène de Creatures avec ses attributs et données parsées.
 */
public class Gene {
	// Types de gènes
	public static final int TYPE_BRAIN = 0x00;
	public static final int TYPE_BIOCHEMISTRY = 0x01;
	public static final int TYPE_CREATURE = 0x02;

	// Sous-types de gènes
	public static final int SUBTYPE_LOBE = 0x00;
	public static final int SUBTYPE_RECEPTOR = 0x00;
	public static final int SUBTYPE_EMITTER = 0x01;
	public static final int SUBTYPE_REACTION = 0x02;
	public static final int SUBTYPE_HALF_LIVES = 0x03;
	public static final int SUBTYPE_INITIAL_CONCENTRATION = 0x04;
	public static final int SUBTYPE_STIMULUS = 0x00;
	public static final int SUBTYPE_INSTINCT = 0x05;

	// Noms des lobes (source: creatures1BrainMapCells_GenesHeader.txt)
	public static final Map<Integer, String> LOBE_NAMES = new HashMap<>();
	static {
		LOBE_NAMES.put(0, "Perception");
		LOBE_NAMES.put(1, "Drive");
		LOBE_NAMES.put(2, "Stimulus Source");
		LOBE_NAMES.put(3, "Verb");
		LOBE_NAMES.put(4, "Noun");
		LOBE_NAMES.put(5, "General Sense");
		LOBE_NAMES.put(6, "Decision");
		LOBE_NAMES.put(7, "Attention");
		LOBE_NAMES.put(8, "Concept");
		LOBE_NAMES.put(9, "Regulator");
	}

	// Noms des chimies (source: creaturesDevelopmentRessources.pdf)
	public static final Map<Integer, String> CHEM_NAMES = new HashMap<>();
	static {
		CHEM_NAMES.put(0x00, "Pain");
		CHEM_NAMES.put(0x01, "Need for Pleasure");
		CHEM_NAMES.put(0x02, "Hunger");
		CHEM_NAMES.put(0x03, "Coldness");
		CHEM_NAMES.put(0x04, "Hotness");
		CHEM_NAMES.put(0x0A, "Fear");
		CHEM_NAMES.put(0x0B, "Boredom");
		CHEM_NAMES.put(0x1E, "Reward");
		CHEM_NAMES.put(0x1F, "Punishment");
	}

	// Noms des actions (source: meliwebNetCreatures.pdf)
	public static final Map<Integer, String> ACTION_NAMES = new HashMap<>();
	static {
		ACTION_NAMES.put(0, "Quiescent");
		ACTION_NAMES.put(1, "Push (Activate 1)");
		ACTION_NAMES.put(2, "Pull (Activate 2)");
		ACTION_NAMES.put(3, "Stop (Deactivate)");
		ACTION_NAMES.put(4, "Come (Approach)");
		ACTION_NAMES.put(5, "Run (Retreat)");
		ACTION_NAMES.put(6, "Get");
		ACTION_NAMES.put(7, "Drop");
		ACTION_NAMES.put(8, "Think/Say");
		ACTION_NAMES.put(9, "Sleep/Rest");
		ACTION_NAMES.put(10, "Left");
		ACTION_NAMES.put(11, "Right");
	}

	// Attributs du gène
	public int type;
	public int subtype;
	public int number;
	public int switchOn;
	public String sexDep;
	public String mutability;
	public List<Integer> data;
	public Map<String, Object> parsedData;

	public Gene(int type, int subtype, int number, int switchOn, String sexDep, String mutability, List<Integer> data) {
		this.type = type;
		this.subtype = subtype;
		this.number = number;
		this.switchOn = switchOn;
		this.sexDep = sexDep;
		this.mutability = mutability;
		this.data = data;
		this.parsedData = new HashMap<>();
		parseData();
	}

	private void parseData() {
		switch (type) {
			case TYPE_BRAIN:
				if (subtype == SUBTYPE_LOBE) parseLobe();
				break;
			case TYPE_BIOCHEMISTRY:
				switch (subtype) {
					case SUBTYPE_RECEPTOR: parseReceptor(); break;
					case SUBTYPE_EMITTER: parseEmitter(); break;
					case SUBTYPE_REACTION: parseReaction(); break;
					case SUBTYPE_HALF_LIVES: parseHalfLives(); break;
					case SUBTYPE_INITIAL_CONCENTRATION: parseInitialConcentration(); break;
					// TODO case SUBTYPE_STIMULUS: parseStimulus(); break;
				}
				break;
			case TYPE_CREATURE:
				if (subtype == SUBTYPE_INSTINCT) parseInstinct();
				break;
		}
	}

	private void parseLobe() {
		if (data.size() < 5) return;
		Map<String, Object> lobeData = new HashMap<>();
		lobeData.put("x", data.get(0));
		lobeData.put("y", data.get(1));
		lobeData.put("width", data.get(2));
		lobeData.put("height", data.get(3));
		lobeData.put("perception_link", data.get(4) != 0 ? "Yes" : "No");
		parsedData.put("lobe", lobeData);
	}

	private void parseInstinct() {
		if (data.size() < 9) return;
		List<Map<String, Object>> conditions = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Map<String, Object> condition = new HashMap<>();
			condition.put("lobe", LOBE_NAMES.getOrDefault(data.get(i * 2), String.format("Lobe%02X", data.get(i * 2))));
			condition.put("cell", data.get(i * 2 + 1));
			conditions.add(condition);
		}
		Map<String, Object> instinctData = new HashMap<>();
		instinctData.put("conditions", conditions);
		instinctData.put("action", ACTION_NAMES.getOrDefault(data.get(6), String.format("Action%02X", data.get(6))));
		instinctData.put("reward_punish", data.get(7) == 0 ? "Reward" : "Punish");
		instinctData.put("amount", data.get(8));
		parsedData.put("instinct", instinctData);
	}

	private void parseReceptor() {
		if (data.size() < 7) return;
		Map<String, Object> receptorData = new HashMap<>();
		receptorData.put("locus", data.subList(0, 3));
		receptorData.put("chemical", CHEM_NAMES.getOrDefault(data.get(3), String.format("Chem%02X", data.get(3))));
		receptorData.put("threshold", data.get(4));
		receptorData.put("nominal", data.get(5));
		receptorData.put("gain", data.get(6));
		parsedData.put("receptor", receptorData);
	}

	private void parseEmitter() {
		if (data.size() < 7) return;
		Map<String, Object> emitterData = new HashMap<>();
		emitterData.put("locus", data.subList(0, 3));
		emitterData.put("chemical", CHEM_NAMES.getOrDefault(data.get(3), String.format("Chem%02X", data.get(3))));
		emitterData.put("threshold", data.get(4));
		emitterData.put("sample_rate", data.get(5));
		emitterData.put("gain", data.get(6));
		parsedData.put("emitter", emitterData);
	}

	private void parseReaction() {
		if (data.size() < 4) return;
		List<Map<String, Object>> reactants = new ArrayList<>();
		List<Map<String, Object>> products = new ArrayList<>();
		for (int i = 0; i < 4; i += 2) {
			if (i + 1 >= data.size()) break;
			Map<String, Object> item = new HashMap<>();
			item.put("proportion", data.get(i));
			item.put("chem", data.get(i + 1));
			item.put("chem_name", CHEM_NAMES.getOrDefault(data.get(i + 1), String.format("Chem%02X", data.get(i + 1))));
			(i < 2 ? reactants : products).add(item);
		}
		int rate = data.size() >= 9 ? data.get(8) : 1;
		Map<String, Object> reactionData = new HashMap<>();
		reactionData.put("reactants", reactants);
		reactionData.put("products", products);
		reactionData.put("rate", rate);
		parsedData.put("reaction", reactionData);
	}

	private void parseHalfLives() {
		Map<String, Integer> halfLives = new HashMap<>();
		for (int i = 0; i < data.size(); i += 2) {
			if (i + 1 >= data.size()) break;
			int chem = data.get(i);
			halfLives.put(CHEM_NAMES.getOrDefault(chem, String.format("Chem%02X", chem)), data.get(i + 1));
		}
		parsedData.put("half_lives", halfLives);
	}

	private void parseInitialConcentration() {
		Map<String, Integer> concentrations = new HashMap<>();
		for (int i = 0; i < data.size(); i += 2) {
			if (i + 1 >= data.size()) break;
			int chem = data.get(i);
			concentrations.put(CHEM_NAMES.getOrDefault(chem, String.format("Chem%02X", chem)), data.get(i + 1));
		}
		parsedData.put("initial_concentrations", concentrations);
	}

	private void parseStimulus() {
		if (data.size() < 4) return;
		Map<String, Object> stimulusData = new HashMap<>();
		stimulusData.put("stimulus_type", data.get(0));
		stimulusData.put("intensity", data.get(1));
		stimulusData.put("locus", data.subList(2, 5));
		parsedData.put("stimulus", stimulusData);
	}

	private void parseDendrite() {
		if (data.size() < 6) return;
		Map<String, Object> dendriteData = new HashMap<>();
		dendriteData.put("source_lobe", data.get(0));
		dendriteData.put("source_cell", data.get(1));
		dendriteData.put("target_lobe", data.get(2));
		dendriteData.put("target_cell", data.get(3));
		dendriteData.put("strength", data.get(4));
		dendriteData.put("type", data.get(5)); // 0 = D0, 1 = D1
		parsedData.put("dendrite", dendriteData);
	}

	// Getters
	public String getTypeName() {
		switch (type) {
			case TYPE_BRAIN: return "Brain";
			case TYPE_BIOCHEMISTRY: return "Biochemistry";
			case TYPE_CREATURE: return "Creature";
			default: return "Unknown";
		}
	}

	public String getSubtypeName() {
		switch (type) {
			case TYPE_BRAIN:
				if (subtype == SUBTYPE_LOBE) return "Lobe";
				break;
			case TYPE_BIOCHEMISTRY:
				switch (subtype) {
					case SUBTYPE_RECEPTOR: return "Receptor";
					case SUBTYPE_EMITTER: return "Emitter";
					case SUBTYPE_REACTION: return "Reaction";
					case SUBTYPE_HALF_LIVES: return "Half-Lives";
					case SUBTYPE_INITIAL_CONCENTRATION: return "Initial Concentration";
					// TODO case SUBTYPE_STIMULUS: return "Stimulus";
				}
				break;
			case TYPE_CREATURE:
				switch (subtype) {
					// TODO case SUBTYPE_STIMULUS: return "Stimulus";
					case SUBTYPE_INSTINCT: return "Instinct";
				}
				break;
		}
		return String.format("Subtype%02X", subtype);
	}

	@Override
	public String toString() {
		return String.format(
			"Gene %03d: Type=0x%02X (%s), Subtype=0x%02X (%s), SwitchOn=%d, SexDep=%s, Mutability=%s",
			number, type, getTypeName(), subtype, getSubtypeName(), switchOn, sexDep, mutability
		);
	}
}
