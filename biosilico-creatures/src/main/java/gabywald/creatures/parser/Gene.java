package gabywald.creatures.parser;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Creatures gene with all attributes and parsed data.
 * @author Gabriel Chandesris (2026)
 * @deprecated (tests of reimplementation)
 */
public class Gene {
	// Basic attributes
	private int type;
	private int subtype;
	private int number;
	private int switchOn;
	private String sexDep;
	private String mutability;
	private List<Integer> data;

	// Parsed data (specific to subtype)
	private Map<String, Object> parsedData;

	// Reference data
	public static final Map<Integer, String> LOBE_NAMES = new HashMap<>();
	public static final Map<Integer, String> CHEM_NAMES = new HashMap<>();
	public static final Map<Integer, String> ACTION_NAMES = new HashMap<>();
	public static final Map<Integer, String> TYPE_NAMES = new HashMap<>();
	public static final Map<Integer, Map<Integer, String>> SUBTYPE_NAMES = new HashMap<>();
	public static final String[] STAGE_NAMES = {"Embryo", "Child", "Youth", "Adolescent", "Adult", "Senior", "Old"};

	static {
		// Initialize reference data
		LOBE_NAMES.put(0, "Perception");
		LOBE_NAMES.put(1, "Drive");
		// ... (other lobe names)

		CHEM_NAMES.put(0x00, "Pain");
		CHEM_NAMES.put(0x01, "Need for Pleasure");
		// ... (other chemical names)

		ACTION_NAMES.put(0, "Quiescent");
		ACTION_NAMES.put(1, "Push (Activate 1)");
		// ... (other action names)

		TYPE_NAMES.put(0x00, "Brain");
		TYPE_NAMES.put(0x01, "Biochemistry");
		TYPE_NAMES.put(0x02, "Creature");

		// Initialize SUBTYPE_NAMES
		Map<Integer, String> brainSubtypes = new HashMap<>();
		brainSubtypes.put(0x00, "Lobe");
		SUBTYPE_NAMES.put(0x00, brainSubtypes);

		Map<Integer, String> bioSubtypes = new HashMap<>();
		bioSubtypes.put(0x00, "Receptor");
		bioSubtypes.put(0x01, "Emitter");
		bioSubtypes.put(0x02, "Reaction");
		bioSubtypes.put(0x03, "Half-Lives");
		bioSubtypes.put(0x04, "Initial Concentration");
		SUBTYPE_NAMES.put(0x01, bioSubtypes);

		// ... (other subtype mappings)
	}

	public Gene(int type, int subtype, int number, int switchOn, String sexDep, String mutability, List<Integer> data) {
		this.type = type;
		this.subtype = subtype;
		this.number = number;
		this.switchOn = switchOn;
		this.sexDep = sexDep;
		this.mutability = mutability;
		this.data = data;
		this.parsedData = new HashMap<>();
		this.parseData(); // Parse data based on type/subtype
	}

	private void parseData() {
		switch (type) {
			case 0x00: // Brain
				if (subtype == 0x00) { this.parseLobe(); }
				break;
			case 0x01: // Biochemistry
				switch (subtype) {
					case 0x00: this.parseReceptor(); break;
					case 0x01: this.parseEmitter(); break;
					case 0x02: this.parseReaction(); break;
					case 0x03: this.parseHalfLives(); break;
					case 0x04: this.parseInitialConcentration(); break;
				}
				break;
			case 0x02: // Creature
				if (subtype == 0x05) this.parseInstinct();
				break;
			// Add cases for other types/subtypes
		}
	}
	
	private void parseInitialConcentration() {
		// TODO ...
	}

	private void parseLobe() {
		
		new HashMap<String, Object>().put("key", null);
		
		if (data.size() < 5) return;
		Map<String, Object> lobeData = new HashMap<>();
		
		Map<String, Object> positionData = new HashMap<>();
		positionData.put("x", data.get(0));
		positionData.put("y", data.get(1));
		
		Map<String, Object> sizeData = new HashMap<>();
		sizeData.put("width", data.get(2));
		sizeData.put("height", data.get(3));
		
		lobeData.put("position", positionData);
		lobeData.put("size", sizeData);
		lobeData.put("perception_link", data.get(4) != 0 ? "Yes" : "No");
		lobeData.put("neurons", data.get(2) * data.get(3));
		parsedData.put("lobe", lobeData);
	}

	private void parseReceptor() {
		if (data.size() < 5) return;
		Map<String, Object> receptorData = new HashMap<>();
		receptorData.put("locus", data.subList(0, 3));
		receptorData.put("chemical", CHEM_NAMES.getOrDefault(data.get(3), String.format("Chem%02X", data.get(3))));
		receptorData.put("threshold", data.get(4));
		parsedData.put("receptor", receptorData);
	}

	private void parseEmitter() {
		if (data.size() < 5) return;
		Map<String, Object> emitterData = new HashMap<>();
		emitterData.put("locus", data.subList(0, 3));
		emitterData.put("chemical", CHEM_NAMES.getOrDefault(data.get(3), String.format("Chem%02X", data.get(3))));
		emitterData.put("threshold", data.get(4));
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
		for (int i = 0; i < data.size(); i++) {
			halfLives.put(CHEM_NAMES.getOrDefault(data.get(i), String.format("Chem%02X", data.get(i))), data.get(++i));
		}
		parsedData.put("half_lives", halfLives);
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

	// Getters and toString()
	@Override
	public String toString() {
		return String.format(
			"Gene %03d: Type=0x%02X (%s), Subtype=0x%02X (%s), SwitchOn=%d (%s), SexDep=%s, Mutability=%s",
			number, type, TYPE_NAMES.getOrDefault(type, "Unknown"),
			subtype, SUBTYPE_NAMES.getOrDefault(type, new HashMap<>()).getOrDefault(subtype, "Unknown"),
			switchOn, STAGE_NAMES[Math.min(switchOn, STAGE_NAMES.length - 1)],
			sexDep, mutability
		);
	}

	public int getType()	{ return this.type; }

	public int getSubtype()	{ return this.subtype; }

	public int getNumber()	{ return this.number; }

	// Additional getters and setters
	
}
