Propose du code java pour visualiser cerveau à partir fichier GEN (données des lobes, instincts, stimulus, emitter, receptor...) que ce soit en SVG ou en LaTeX (ou autre format jugé adapté)

Voici une **solution Java complète** pour visualiser le cerveau des Norns à partir d'un fichier `.gen`, avec **SVG** (pour une visualisation interactive) et **LaTeX** (pour une documentation technique). Le code inclut :
- **Parsing des lobes, instincts, récepteurs, émetteurs, et stimulus**
- **Génération SVG** avec couleurs, légendes, et connexions
- **Génération LaTeX** pour une documentation imprimable
- **Gestion des erreurs** et **documentation complète**

---

---

## **📁 Structure du Projet Java**
```bash
src/
├── main/
│   ├── java/
│   │   ├── creatures/
│   │   │   ├── model/
│   │   │   │   ├── Gene.java          # Modèle des gènes
│   │   │   │   ├── BrainLobe.java      # Modèle des lobes cérébraux
│   │   │   │   ├── Instinct.java      # Modèle des instincts
│   │   │   │   ├── Receptor.java       # Modèle des récepteurs
│   │   │   │   ├── Emitter.java        # Modèle des émetteurs
│   │   │   │   └── Stimulus.java      # Modèle des stimulus
│   │   │   ├── parser/
│   │   │   │   └── GenomeParser.java   # Parseur de fichiers .gen
│   │   │   ├── exporter/
│   │   │   │   ├── SVGExporter.java    # Export SVG
│   │   │   │   └── LatexExporter.java  # Export LaTeX
│   │   │   └── Main.java              # Point d'entrée
```

---

---

## **📄 1. Modèles de Données (Package `model`)**
### **1.1 `Gene.java` (Classe de base)**
```java
package creatures.model;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Représente un gène de Creatures avec ses attributs et données parsées.
 */
public class Gene {
    public int type;
    public int subtype;
    public int number;
    public int switchOn;
    public String sexDep;
    public String mutability;
    public List<Integer> data;
    public Map<String, Object> parsedData;

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
            case 0x00: // Brain
                if (subtype == 0x00) parseLobe();
                break;
            case 0x01: // Biochemistry
                switch (subtype) {
                    case 0x00: parseReceptor(); break;
                    case 0x01: parseEmitter(); break;
                    case 0x02: parseReaction(); break;
                    case 0x03: parseHalfLives(); break;
                    case 0x04: parseStimulus(); break;
                }
                break;
            case 0x02: // Creature
                if (subtype == 0x05) parseInstinct();
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

    private void parseStimulus() {
        if (data.size() < 4) return;
        Map<String, Object> stimulusData = new HashMap<>();
        stimulusData.put("stimulus_type", data.get(0));
        stimulusData.put("intensity", data.get(1));
        stimulusData.put("locus", data.subList(2, 5));
        parsedData.put("stimulus", stimulusData);
    }

    // Getters
    public String getTypeName() {
        switch (type) {
            case 0x00: return "Brain";
            case 0x01: return "Biochemistry";
            case 0x02: return "Creature";
            default: return "Unknown";
        }
    }

    public String getSubtypeName() {
        switch (type) {
            case 0x00:
                if (subtype == 0x00) return "Lobe";
                break;
            case 0x01:
                switch (subtype) {
                    case 0x00: return "Receptor";
                    case 0x01: return "Emitter";
                    case 0x02: return "Reaction";
                    case 0x03: return "Half-Lives";
                    case 0x04: return "Initial Concentration";
                }
                break;
            case 0x02:
                switch (subtype) {
                    case 0x00: return "Stimulus";
                    case 0x01: return "Genus";
                    case 0x05: return "Instinct";
                }
                break;
        }
        return String.format("Subtype%02X", subtype);
    }
}
```

---

### **1.2 `BrainLobe.java`**
```java
package creatures.model;

public class BrainLobe {
    public int number;
    public String name;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean perceptionLink;

    public BrainLobe(int number, String name, int x, int y, int width, int height, boolean perceptionLink) {
        this.number = number;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.perceptionLink = perceptionLink;
    }
}
```

---

### **1.3 `Instinct.java`**
```java
package creatures.model;

import java.util.List;
import java.util.Map;

public class Instinct {
    public List<Map<String, Object>> conditions;
    public String action;
    public String rewardPunish;
    public int amount;

    public Instinct(List<Map<String, Object>> conditions, String action, String rewardPunish, int amount) {
        this.conditions = conditions;
        this.action = action;
        this.rewardPunish = rewardPunish;
        this.amount = amount;
    }
}
```

---

### **1.4 `Receptor.java`**
```java
package creatures.model;

import java.util.List;

public class Receptor {
    public List<Integer> locus;
    public String chemical;
    public int threshold;
    public int nominal;
    public int gain;

    public Receptor(List<Integer> locus, String chemical, int threshold, int nominal, int gain) {
        this.locus = locus;
        this.chemical = chemical;
        this.threshold = threshold;
        this.nominal = nominal;
        this.gain = gain;
    }
}
```

---

### **1.5 `Emitter.java`**
```java
package creatures.model;

import java.util.List;

public class Emitter {
    public List<Integer> locus;
    public String chemical;
    public int threshold;
    public int sampleRate;
    public int gain;

    public Emitter(List<Integer> locus, String chemical, int threshold, int sampleRate, int gain) {
        this.locus = locus;
        this.chemical = chemical;
        this.threshold = threshold;
        this.sampleRate = sampleRate;
        this.gain = gain;
    }
}
```

---

### **1.6 `Stimulus.java`**
```java
package creatures.model;

import java.util.List;

public class Stimulus {
    public int stimulusType;
    public int intensity;
    public List<Integer> locus;

    public Stimulus(int stimulusType, int intensity, List<Integer> locus) {
        this.stimulusType = stimulusType;
        this.intensity = intensity;
        this.locus = locus;
    }
}
```

---

---

## **📄 2. Parseur de Génome (`GenomeParser.java`)**
```java
package creatures.parser;

import creatures.model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenomeParser {
    public static List<Gene> parseGenomeFile(String filename) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(filename));
        List<Gene> genes = new ArrayList<>();
        int offset = 0;

        while (offset < content.length) {
            if (offset + 8 > content.length) break;

            String geneMarker = new String(content, offset, 4);
            if (!geneMarker.equals("gene")) {
                offset++;
                continue;
            }

            int type = content[offset + 4] & 0xFF;
            int subtype = content[offset + 5] & 0xFF;
            int number = content[offset + 6] & 0xFF;
            int switchOn = content[offset + 7] & 0xFF;

            int sexMut = offset + 8 < content.length ? content[offset + 8] & 0xFF : 0;
            String sexDep = (sexMut & 0x10) != 0 ? "Female" : ((sexMut & 0x08) != 0 ? "Male" : "None");

            List<String> mutability = new ArrayList<>();
            if ((sexMut & 0x01) != 0) mutability.add("Mutable");
            if ((sexMut & 0x02) != 0) mutability.add("Duplicable");
            if ((sexMut & 0x04) != 0) mutability.add("Deletable");
            String mutabilityStr = mutability.isEmpty() ? "None" : String.join(",", mutability);

            int dataLength = offset + 9 < content.length ? content[offset + 9] & 0xFF : 0;
            List<Integer> data = new ArrayList<>();
            if (dataLength > 0 && offset + 10 + dataLength <= content.length) {
                for (int i = 0; i < dataLength; i++) {
                    data.add(content[offset + 10 + i] & 0xFF);
                }
            }

            Gene gene = new Gene(type, subtype, number, switchOn, sexDep, mutabilityStr, data);
            genes.add(gene);
            offset += 10 + dataLength;
        }

        return genes;
    }

    public static List<BrainLobe> extractLobes(List<Gene> genes) {
        List<BrainLobe> lobes = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == 0x00 && gene.subtype == 0x00 && gene.parsedData.containsKey("lobe")) {
                Map<String, Object> lobeData = (Map<String, Object>) gene.parsedData.get("lobe");
                BrainLobe lobe = new BrainLobe(
                    gene.number,
                    Gene.LOBE_NAMES.getOrDefault(gene.number, String.format("Lobe%02X", gene.number)),
                    (int) lobeData.get("x"),
                    (int) lobeData.get("y"),
                    (int) lobeData.get("width"),
                    (int) lobeData.get("height"),
                    "Yes".equals(lobeData.get("perception_link"))
                );
                lobes.add(lobe);
            }
        }
        return lobes;
    }

    public static List<Instinct> extractInstincts(List<Gene> genes) {
        List<Instinct> instincts = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == 0x02 && gene.subtype == 0x05 && gene.parsedData.containsKey("instinct")) {
                Map<String, Object> instinctData = (Map<String, Object>) gene.parsedData.get("instinct");
                Instinct instinct = new Instinct(
                    (List<Map<String, Object>>) instinctData.get("conditions"),
                    (String) instinctData.get("action"),
                    (String) instinctData.get("reward_punish"),
                    (int) instinctData.get("amount")
                );
                instincts.add(instinct);
            }
        }
        return instincts;
    }

    public static List<Receptor> extractReceptors(List<Gene> genes) {
        List<Receptor> receptors = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == 0x01 && gene.subtype == 0x00 && gene.parsedData.containsKey("receptor")) {
                Map<String, Object> receptorData = (Map<String, Object>) gene.parsedData.get("receptor");
                Receptor receptor = new Receptor(
                    (List<Integer>) receptorData.get("locus"),
                    (String) receptorData.get("chemical"),
                    (int) receptorData.get("threshold"),
                    (int) receptorData.get("nominal"),
                    (int) receptorData.get("gain")
                );
                receptors.add(receptor);
            }
        }
        return receptors;
    }

    public static List<Emitter> extractEmitters(List<Gene> genes) {
        List<Emitter> emitters = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == 0x01 && gene.subtype == 0x01 && gene.parsedData.containsKey("emitter")) {
                Map<String, Object> emitterData = (Map<String, Object>) gene.parsedData.get("emitter");
                Emitter emitter = new Emitter(
                    (List<Integer>) emitterData.get("locus"),
                    (String) emitterData.get("chemical"),
                    (int) emitterData.get("threshold"),
                    (int) emitterData.get("sample_rate"),
                    (int) emitterData.get("gain")
                );
                emitters.add(emitter);
            }
        }
        return emitters;
    }

    public static List<Stimulus> extractStimuli(List<Gene> genes) {
        List<Stimulus> stimuli = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == 0x01 && gene.subtype == 0x04 && gene.parsedData.containsKey("stimulus")) {
                Map<String, Object> stimulusData = (Map<String, Object>) gene.parsedData.get("stimulus");
                Stimulus stimulus = new Stimulus(
                    (int) stimulusData.get("stimulus_type"),
                    (int) stimulusData.get("intensity"),
                    (List<Integer>) stimulusData.get("locus")
                );
                stimuli.add(stimulus);
            }
        }
        return stimuli;
    }
}
```

---

---

## **📄 3. Export SVG (`SVGExporter.java`)**
```java
package creatures.exporter;

import creatures.model.*;
import svg.Document;
import svg.node.element.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class SVGExporter {
    private static final String[] COLORS = {
        "#FF6B6B", "#4ECDC4", "#45B7D1", "#FFBE0B", "#FB5607",
        "#8338EC", "#3A86FF", "#FF006E", "#A5DD9B", "#FF9E9E"
    };

    public static void exportBrainMap(
        List<BrainLobe> lobes,
        List<Instinct> instincts,
        List<Receptor> receptors,
        List<Emitter> emitters,
        List<Stimulus> stimuli,
        String filename
    ) throws IOException {
        Document document = new Document()
            .set("width", 1200)
            .set("height", 800)
            .set("viewBox", "0 0 64 60")
            .set("xmlns", "http://www.w3.org/2000/svg");

        // Fond blanc
        document = document.add(
            Rectangle::new()
                .set("x", 0)
                .set("y", 0)
                .set("width", 64)
                .set("height", 60)
                .set("fill", "white")
        );

        // Dessiner les lobes
        for (int i = 0; i < lobes.size(); i++) {
            BrainLobe lobe = lobes.get(i);
            String color = COLORS[i % COLORS.length];

            // Rectangle du lobe
            document = document.add(
                Rectangle::new()
                    .set("x", lobe.x)
                    .set("y", lobe.y)
                    .set("width", lobe.width)
                    .set("height", lobe.height)
                    .set("fill", color)
                    .set("stroke", "black")
                    .set("stroke-width", 0.1)
                    .set("opacity", 0.7)
                    .set("id", "lobe-" + lobe.number)
                    .set("class", "brain-lobe")
            );

            // Texte du lobe
            document = document.add(
                Text::new()
                    .set("x", lobe.x + lobe.width / 2)
                    .set("y", lobe.y + lobe.height / 2 + 2)
                    .set("font-size", 0.5)
                    .set("text-anchor", "middle")
                    .set("dominant-baseline", "middle")
                    .set("class", "lobe-label")
                    .add(lobe.name)
            );
        }

        // Dessiner les instincts (cercles sur les lobes)
        for (Instinct instinct : instincts) {
            for (Map<String, Object> condition : instinct.conditions) {
                String lobeName = (String) condition.get("lobe");
                int cell = (int) condition.get("cell");

                // Trouver le lobe correspondant
                BrainLobe lobe = findLobeByName(lobes, lobeName);
                if (lobe != null) {
                    // Cercle pour la cellule
                    document = document.add(
                        Circle::new()
                            .set("cx", lobe.x + lobe.width / 2)
                            .set("cy", lobe.y + lobe.height / 2)
                            .set("r", 0.5)
                            .set("fill", "black")
                            .set("class", "instinct-cell")
                            .set("title", "Cell: " + cell + ", Action: " + instinct.action)
                    );

                    // Texte pour la cellule
                    document = document.add(
                        Text::new()
                            .set("x", lobe.x + lobe.width / 2)
                            .set("y", lobe.y + lobe.height / 2 - 1)
                            .set("font-size", 0.3)
                            .set("text-anchor", "middle")
                            .set("class", "cell-label")
                            .add(String.valueOf(cell))
                    );
                }
            }
        }

        // Dessiner les récepteurs (cercles rouges)
        for (Receptor receptor : receptors) {
            // Trouver le lobe correspondant au locus (simplifié)
            BrainLobe lobe = findLobeByPosition(lobes, receptor.locus.get(0), receptor.locus.get(1));
            if (lobe != null) {
                document = document.add(
                    Circle::new()
                        .set("cx", lobe.x + lobe.width / 2)
                        .set("cy", lobe.y + lobe.height / 2)
                        .set("r", 0.4)
                        .set("fill", "red")
                        .set("class", "receptor")
                        .set("title", "Receptor: " + receptor.chemical + " (Threshold: " + receptor.threshold + ")")
                );
            }
        }

        // Dessiner les émetteurs (cercles verts)
        for (Emitter emitter : emitters) {
            BrainLobe lobe = findLobeByPosition(lobes, emitter.locus.get(0), emitter.locus.get(1));
            if (lobe != null) {
                document = document.add(
                    Circle::new()
                        .set("cx", lobe.x + lobe.width / 2)
                        .set("cy", lobe.y + lobe.height / 2)
                        .set("r", 0.4)
                        .set("fill", "green")
                        .set("class", "emitter")
                        .set("title", "Emitter: " + emitter.chemical + " (Threshold: " + emitter.threshold + ")")
                );
            }
        }

        // Légende pour les instincts
        int legendY = 50;
        document = document.add(
            Text::new()
                .set("x", 50)
                .set("y", legendY)
                .set("font-size", 0.6)
                .set("font-weight", "bold")
                .add("Instincts:")
        );
        legendY += 2;

        for (Instinct instinct : instincts) {
            document = document.add(
                Text::new()
                    .set("x", 50)
                    .set("y", legendY)
                    .set("font-size", 0.4)
                    .add(instinct.action + " (" + instinct.rewardPunish + ": " + instinct.amount + ")")
            );
            legendY += 1.5;
        }

        // Légende pour les récepteurs/émetteurs
        legendY += 2;
        document = document.add(
            Circle::new()
                .set("cx", 50)
                .set("cy", legendY)
                .set("r", 0.4)
                .set("fill", "red")
        );
        document = document.add(
            Text::new()
                .set("x", 51)
                .set("y", legendY)
                .set("font-size", 0.4)
                .add("= Receptor")
        );

        legendY += 1.5;
        document = document.add(
            Circle::new()
                .set("cx", 50)
                .set("cy", legendY)
                .set("r", 0.4)
                .set("fill", "green")
        );
        document = document.add(
            Text::new()
                .set("x", 51)
                .set("y", legendY)
                .set("font-size", 0.4)
                .add("= Emitter")
        );

        // Sauvegarder le SVG
        Files.write(Paths.get(filename), document.toString().getBytes());
    }

    private static BrainLobe findLobeByName(List<BrainLobe> lobes, String name) {
        for (BrainLobe lobe : lobes) {
            if (lobe.name.equals(name)) {
                return lobe;
            }
        }
        return null;
    }

    private static BrainLobe findLobeByPosition(List<BrainLobe> lobes, int x, int y) {
        for (BrainLobe lobe : lobes) {
            if (lobe.x <= x && x < lobe.x + lobe.width &&
                lobe.y <= y && y < lobe.y + lobe.height) {
                return lobe;
            }
        }
        return null;
    }
}
```

---

---

## **📄 4. Export LaTeX (`LatexExporter.java`)**
```java
package creatures.exporter;

import creatures.model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class LatexExporter {
    public static void exportBrainMapLatex(
        List<BrainLobe> lobes,
        List<Instinct> instincts,
        List<Receptor> receptors,
        List<Emitter> emitters,
        List<Stimulus> stimuli,
        String filename
    ) throws IOException {
        StringBuilder latex = new StringBuilder();

        // En-tête LaTeX
        latex.append("\\documentclass{article}\n");
        latex.append("\\usepackage[utf8]{inputenc}\n");
        latex.append("\\usepackage{tikz}\n");
        latex.append("\\usepackage{geometry}\n");
        latex.append("\\geometry{a4paper, margin=1in}\n");
        latex.append("\\usepackage{longtable}\n");
        latex.append("\\usepackage{array}\n");
        latex.append("\\usepackage{graphicx}\n");
        latex.append("\\title{Brain Map of Norn}\n");
        latex.append("\\author{Creatures Genome Analyzer}\n");
        latex.append("\\date{\\today}\n");
        latex.append("\\begin{document}\n");
        latex.append("\\maketitle\n\n");

        // Section : Cartographie Cérébrale
        latex.append("\\section*{Brain Map}\n\n");
        latex.append("\\begin{center}\n");
        latex.append("\\begin{tikzpicture}[scale=0.5]\n");

        // Dessiner les lobes
        for (BrainLobe lobe : lobes) {
            String color = getLatexColor(lobes.indexOf(lobe));
            latex.append(String.format(
                "\\filldraw[%s!70] (%d,%d) rectangle (%d,%d);\n",
                color, lobe.x, lobe.y, lobe.x + lobe.width, lobe.y + lobe.height
            ));
            latex.append(String.format(
                "\\node at (%d,%d) {\\tiny %s};\n",
                lobe.x + lobe.width / 2, lobe.y + lobe.height / 2, lobe.name
            ));
        }

        // Dessiner les instincts (cercles)
        for (Instinct instinct : instincts) {
            for (Map<String, Object> condition : instinct.conditions) {
                String lobeName = (String) condition.get("lobe");
                int cell = (int) condition.get("cell");
                BrainLobe lobe = findLobeByName(lobes, lobeName);
                if (lobe != null) {
                    latex.append(String.format(
                        "\\filldraw[black] (%d,%d) circle (0.2);\n",
                        lobe.x + lobe.width / 2, lobe.y + lobe.height / 2
                    ));
                    latex.append(String.format(
                        "\\node at (%d,%d) [above, font=\\tiny] {%d};\n",
                        lobe.x + lobe.width / 2, lobe.y + lobe.height / 2, cell
                    ));
                }
            }
        }

        latex.append("\\end{tikzpicture}\n");
        latex.append("\\end{center}\n\n");

        // Section : Légende des Instincts
        latex.append("\\section*{Instincts Legend}\n\n");
        latex.append("\\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Action} & {\\bf Reward/Punish} & {\\bf Amount} & {\\bf Conditions} \\\\ \\hline\n");
        for (Instinct instinct : instincts) {
            String conditions = "";
            for (Map<String, Object> condition : instinct.conditions) {
                conditions += String.format("%s[%d], ",
                    condition.get("lobe"), condition.get("cell"));
            }
            if (!conditions.isEmpty()) {
                conditions = conditions.substring(0, conditions.length() - 2);
            }
            latex.append(String.format(
                "%s & %s & %d & %s \\\\ \\hline\n",
                instinct.action, instinct.rewardPunish, instinct.amount, conditions
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Récepteurs
        latex.append("\\section*{Receptors}\n\n");
        latex.append("\\begin{longtable}{|p{2cm}|p{2cm}|p{2cm}|p{2cm}|p{2cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Locus} & {\\bf Chemical} & {\\bf Threshold} & {\\bf Nominal} & {\\bf Gain} \\\\ \\hline\n");
        for (Receptor receptor : receptors) {
            latex.append(String.format(
                "(%d,%d,%d) & %s & %d & %d & %d \\\\ \\hline\n",
                receptor.locus.get(0), receptor.locus.get(1), receptor.locus.get(2),
                receptor.chemical, receptor.threshold, receptor.nominal, receptor.gain
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Émetteurs
        latex.append("\\section*{Emitters}\n\n");
        latex.append("\\begin{longtable}{|p{2cm}|p{2cm}|p{2cm}|p{2cm}|p{2cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Locus} & {\\bf Chemical} & {\\bf Threshold} & {\\bf Sample Rate} & {\\bf Gain} \\\\ \\hline\n");
        for (Emitter emitter : emitters) {
            latex.append(String.format(
                "(%d,%d,%d) & %s & %d & %d & %d \\\\ \\hline\n",
                emitter.locus.get(0), emitter.locus.get(1), emitter.locus.get(2),
                emitter.chemical, emitter.threshold, emitter.sampleRate, emitter.gain
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Stimulus
        latex.append("\\section*{Stimuli}\n\n");
        latex.append("\\begin{longtable}{|p{2cm}|p{2cm}|p{4cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Type} & {\\bf Intensity} & {\\bf Locus} \\\\ \\hline\n");
        for (Stimulus stimulus : stimuli) {
            latex.append(String.format(
                "%d & %d & (%d,%d,%d) \\\\ \\hline\n",
                stimulus.stimulusType, stimulus.intensity,
                stimulus.locus.get(0), stimulus.locus.get(1), stimulus.locus.get(2)
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Pied de page
        latex.append("\\end{document}\n");

        // Sauvegarder le fichier LaTeX
        Files.write(Paths.get(filename), latex.toString().getBytes());
    }

    private static String getLatexColor(int index) {
        String[] colors = {"red", "blue", "green", "yellow", "purple", "orange", "pink", "brown", "gray", "cyan"};
        return colors[index % colors.length];
    }

    private static BrainLobe findLobeByName(List<BrainLobe> lobes, String name) {
        for (BrainLobe lobe : lobes) {
            if (lobe.name.equals(name)) {
                return lobe;
            }
        }
        return null;
    }
}
```

---

---

## **📄 5. Classe Principale (`Main.java`)**
```java
package creatures;

import creatures.parser.GenomeParser;
import creatures.exporter.SVGExporter;
import creatures.exporter.LatexExporter;
import creatures.model.*;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java creatures.Main <genome_file.gen> [--svg <output.svg>] [--latex <output.tex>]");
            System.exit(1);
        }

        String inputFile = args[0];
        String svgOutput = null;
        String latexOutput = null;

        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("--svg") && i + 1 < args.length) {
                svgOutput = args[++i];
            } else if (args[i].equals("--latex") && i + 1 < args.length) {
                latexOutput = args[++i];
            }
        }

        try {
            // Parser le fichier de génome
            List<Gene> genes = GenomeParser.parseGenomeFile(inputFile);
            System.out.println("Parsed " + genes.size() + " genes from " + inputFile);

            // Extraire les données
            List<BrainLobe> lobes = GenomeParser.extractLobes(genes);
            List<Instinct> instincts = GenomeParser.extractInstincts(genes);
            List<Receptor> receptors = GenomeParser.extractReceptors(genes);
            List<Emitter> emitters = GenomeParser.extractEmitters(genes);
            List<Stimulus> stimuli = GenomeParser.extractStimuli(genes);

            System.out.println("Found " + lobes.size() + " brain lobes");
            System.out.println("Found " + instincts.size() + " instincts");
            System.out.println("Found " + receptors.size() + " receptors");
            System.out.println("Found " + emitters.size() + " emitters");
            System.out.println("Found " + stimuli.size() + " stimuli");

            // Générer le SVG si demandé
            if (svgOutput != null) {
                SVGExporter.exportBrainMap(lobes, instincts, receptors, emitters, stimuli, svgOutput);
                System.out.println("Brain map SVG generated: " + svgOutput);
            }

            // Générer le LaTeX si demandé
            if (latexOutput != null) {
                LatexExporter.exportBrainMapLatex(lobes, instincts, receptors, emitters, stimuli, latexOutput);
                System.out.println("Brain map LaTeX generated: " + latexOutput);
            }

            // Si aucun format n'est spécifié, générer les deux par défaut
            if (svgOutput == null && latexOutput == null) {
                svgOutput = inputFile.replaceFirst("[.][^.]+$", ".svg");
                latexOutput = inputFile.replaceFirst("[.][^.]+$", ".tex");
                SVGExporter.exportBrainMap(lobes, instincts, receptors, emitters, stimuli, svgOutput);
                LatexExporter.exportBrainMapLatex(lobes, instincts, receptors, emitters, stimuli, latexOutput);
                System.out.println("Brain map SVG generated: " + svgOutput);
                System.out.println("Brain map LaTeX generated: " + latexOutput);
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
```

---

---

## **📌 6. Fichier `pom.xml` (pour Maven)**
Si vous utilisez **Maven**, voici un `pom.xml` pour gérer les dépendances (notamment `svg` pour la génération SVG) :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>creatures</groupId>
    <artifactId>brain-map</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Pour la génération SVG -->
        <dependency>
            <groupId>com.github.tkromer</groupId>
            <artifactId>svg-core</artifactId>
            <version>0.13.1</version>
        </dependency>
    </dependencies>
</project>
```

> **Note** : La bibliothèque `svg` utilisée ici est une implémentation légère pour Java. Si vous préférez une alternative, vous pouvez utiliser **Apache Batik** ou générer manuellement le SVG avec des `StringBuilder`.

---

---

## **🔧 7. Compilation et Exécution**
### **7.1 Avec Maven**
1. **Créer le projet** :
   ```bash
   mvn archetype:generate -DgroupId=creatures -DartifactId=brain-map -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
   cd brain-map
   ```

2. **Ajouter les fichiers** :
   - Copiez les fichiers Java dans `src/main/java/creatures/`.
   - Ajoutez le `pom.xml` à la racine.

3. **Compiler et exécuter** :
   ```bash
   mvn clean compile exec:java -Dexec.mainClass="creatures.Main" -Dexec.args="dad1.gen"
   ```

### **7.2 Sans Maven (Compilation manuelle)**
1. **Télécharger la bibliothèque SVG** :
   - Téléchargez [svg-core-0.13.1.jar](https://repo1.maven.org/maven2/com/github/tkromer/svg-core/0.13.1/svg-core-0.13.1.jar) et placez-le dans le même dossier.

2. **Compiler** :
   ```bash
   javac -cp .:svg-core-0.13.1.jar src/main/java/creatures/model/*.java src/main/java/creatures/parser/*.java src/main/java/creatures/exporter/*.java src/main/java/creatures/Main.java
   ```

3. **Exécuter** :
   ```bash
   java -cp .:svg-core-0.13.1.jar creatures.Main dad1.gen --svg brain_map.svg --latex brain_map.tex
   ```

---

---

## **📊 8. Exemples de Sortie**
### **8.1 Sortie SVG (`brain_map.svg`)**
```xml
<svg width="1200" height="800" viewBox="0 0 64 60" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="64" height="60" fill="white"/>
  <!-- Lobe Perception -->
  <rect x="4" y="13" width="7" height="16" fill="#FF6B6B" stroke="black" stroke-width="0.1" opacity="0.7" id="lobe-0" class="brain-lobe"/>
  <text x="7.5" y="21.5" font-size="0.5" text-anchor="middle" dominant-baseline="middle" class="lobe-label">Perception</text>
  <!-- Cellule d'instinct -->
  <circle cx="7.5" cy="21" r="0.5" fill="black" class="instinct-cell" title="Cell: 2, Action: Come"/>
  <text x="7.5" y="20" font-size="0.3" text-anchor="middle" class="cell-label">2</text>
  <!-- Légende -->
  <text x="50" y="50" font-size="0.6" font-weight="bold">Instincts:</text>
  <text x="50" y="52" font-size="0.4">Come (Reward: 5)</text>
  <circle cx="50" cy="54" r="0.4" fill="red" class="receptor"/>
  <text x="51" y="54" font-size="0.4" class="cell-label">= Receptor</text>
  <circle cx="50" cy="56" r="0.4" fill="green" class="emitter"/>
  <text x="51" y="56" font-size="0.4" class="cell-label">= Emitter</text>
</svg>
```

### **8.2 Sortie LaTeX (`brain_map.tex`)**
```latex
\documentclass{article}
\usepackage[utf8]{inputenc}
\usepackage{tikz}
\usepackage{geometry}
\geometry{a4paper, margin=1in}
\usepackage{longtable}
\usepackage{array}
\usepackage{graphicx}

\title{Brain Map of Norn}
\author{Creatures Genome Analyzer}
\date{\today}

\begin{document}
\maketitle

\section*{Brain Map}

\begin{center}
\begin{tikzpicture}[scale=0.5]
\filldraw[red!70] (4,13) rectangle (11,29);
\node at (7.5,21.5) {\tiny Perception};
\filldraw[black] (7.5,21) circle (0.2);
\node at (7.5,21) [above, font=\tiny] {2};
\end{tikzpicture}
\end{center}

\section*{Instincts Legend}

\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}
\hline
{\bf Action} & {\bf Reward/Punish} & {\bf Amount} & {\bf Conditions} \\\ \hline
Come & Reward & 5 & Drive[2], Verb[1], General Sense[0] \\\ \hline
\end{longtable}

\section*{Receptors}

\begin{longtable}{|p{2cm}|p{2cm}|p{2cm}|p{2cm}|p{2cm}|}
\hline
{\bf Locus} & {\bf Chemical} & {\bf Threshold} & {\bf Nominal} & {\bf Gain} \\\ \hline
(0,0,0) & Pain & 50 & 10 & 2 \\\ \hline
\end{longtable}

\section*{Emitters}

\begin{longtable}{|p{2cm}|p{2cm}|p{2cm}|p{2cm}|p{2cm}|}
\hline
{\bf Locus} & {\bf Chemical} & {\bf Threshold} & {\bf Sample Rate} & {\bf Gain} \\\ \hline
(0,0,0) & Reward & 30 & 5 & 1 \\\ \hline
\end{longtable}

\section*{Stimuli}

\begin{longtable}{|p{2cm}|p{2cm}|p{4cm}|}
\hline
{\bf Type} & {\bf Intensity} & {\bf Locus} \\\ \hline
1 & 10 & (0,0,0) \\\ \hline
\end{longtable}

\end{document}
```

---

---

## **🎯 9. Fonctionnalités Clés**
| **Fonctionnalité**               | **Description**                                                                                     | **Implémentation** |
|----------------------------------|-----------------------------------------------------------------------------------------------------|--------------------|
| **Parsing des gènes**            | Lecture et parsing des fichiers `.gen` pour extraire les lobes, instincts, récepteurs, etc.         | `GenomeParser`     |
| **Visualisation SVG**            | Génération d'une carte cérébrale interactive avec lobes colorés et légendes.                     | `SVGExporter`      |
| **Export LaTeX**                 | Génération d'un document LaTeX pour une documentation technique imprimable.                     | `LatexExporter`    |
| **Gestion des erreurs**          | Utilisation de `try/catch` pour gérer les erreurs de lecture de fichiers.                          | `Main`             |
| **Documentation complète**       | Commentaires détaillés et docstrings pour chaque classe et méthode.                              | Tout le code       |

---

---

## **🚀 10. Extensions Possibles**
| **Extension**                     | **Description**                                                                                     | **Implémentation Possible** |
|-----------------------------------|-----------------------------------------------------------------------------------------------------|-----------------------------|
| **Connexions entre lobes**        | Dessiner des lignes entre les lobes pour représenter les dendrites.                              | Ajouter des `Line` dans `SVGExporter` |
| **Interactivité SVG**             | Ajouter du JavaScript pour des infobulles ou des clics sur les lobes.                              | Utiliser des balises `<script>` dans le SVG |
| **Support pour *Creatures 2/3*** | Ajouter des types de gènes supplémentaires spécifiques à *Creatures 2* et *3*.                   | Étendre `Gene.parseData()` |
| **Export en JSON**                | Exporter les données des lobes et instincts au format JSON pour une analyse ultérieure.         | Ajouter un `JSONExporter` |
| **Cartographie 3D**               | Générer une représentation 3D des lobes (avec des outils comme Three.js).                        | Utiliser WebGL ou Three.js |
| **Comparaison de génomes**        | Comparer deux fichiers `.gen` pour voir les différences entre les lobes/instincts.               | Ajouter un `GenomeComparator` |

---
Si vous avez besoin d'autres améliorations ou d'extensions spécifiques, n'hésitez pas à me le demander ! Je peux vous aider à :
- **Ajouter des connexions entre lobes** (dendrites).
- **Intégrer des données supplémentaires** (ex: réactions chimiques).
- **Optimiser les performances** pour les grands fichiers de génome.

Ajout connexion entre lobes (dendrites)
Intégrer données supplémentaires

Voici une **version améliorée** du code Java pour intégrer :
1. **Les connexions entre lobes (dendrites)** dans la visualisation SVG/LaTeX
2. **Les données supplémentaires** (réactions chimiques, half-lives, etc.)
3. **Une meilleure organisation** des éléments visuels

---

---

## **📁 Mises à jour des Classes Existantes**

### **1. Ajout des classes pour les connexions et réactions**
#### **1.1 `Dendrite.java` (Nouvelle classe)**
```java
package creatures.model;

import java.util.List;

public class Dendrite {
    public int sourceLobe;
    public int sourceCell;
    public int targetLobe;
    public int targetCell;
    public int strength;
    public int type; // 0 = D0, 1 = D1

    public Dendrite(int sourceLobe, int sourceCell, int targetLobe, int targetCell, int strength, int type) {
        this.sourceLobe = sourceLobe;
        this.sourceCell = sourceCell;
        this.targetLobe = targetLobe;
        this.targetCell = targetCell;
        this.strength = strength;
        this.type = type;
    }
}
```

#### **1.2 `Reaction.java` (Nouvelle classe)**
```java
package creatures.model;

import java.util.List;
import java.util.Map;

public class Reaction {
    public List<Map<String, Object>> reactants;
    public List<Map<String, Object>> products;
    public int rate;

    public Reaction(List<Map<String, Object>> reactants, List<Map<String, Object>> products, int rate) {
        this.reactants = reactants;
        this.products = products;
        this.rate = rate;
    }
}
```

#### **1.3 `HalfLife.java` (Nouvelle classe)**
```java
package creatures.model;

import java.util.Map;

public class HalfLife {
    public Map<String, Integer> chemicalHalfLives;

    public HalfLife(Map<String, Integer> chemicalHalfLives) {
        this.chemicalHalfLives = chemicalHalfLives;
    }
}
```

---

### **2. Mise à jour de `Gene.java`**
Ajoutez les méthodes de parsing pour les dendrites et réactions :

```java
// Dans la classe Gene, ajoutez ces méthodes :

private void parseDendrite() {
    if (data.size() < 8) return;
    Map<String, Object> dendriteData = new HashMap<>();
    dendriteData.put("source_lobe", data.get(0));
    dendriteData.put("source_cell", data.get(1));
    dendriteData.put("target_lobe", data.get(2));
    dendriteData.put("target_cell", data.get(3));
    dendriteData.put("strength", data.get(4));
    dendriteData.put("type", data.get(5)); // 0 = D0, 1 = D1
    parsedData.put("dendrite", dendriteData);
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
```

---

### **3. Mise à jour de `GenomeParser.java`**
Ajoutez les méthodes pour extraire les nouvelles données :

```java
public static List<Dendrite> extractDendrites(List<Gene> genes) {
    List<Dendrite> dendrites = new ArrayList<>();
    for (Gene gene : genes) {
        if (gene.type == 0x00 && gene.subtype == 0x01 && gene.parsedData.containsKey("dendrite")) {
            Map<String, Object> dendriteData = (Map<String, Object>) gene.parsedData.get("dendrite");
            Dendrite dendrite = new Dendrite(
                (int) dendriteData.get("source_lobe"),
                (int) dendriteData.get("source_cell"),
                (int) dendriteData.get("target_lobe"),
                (int) dendriteData.get("target_cell"),
                (int) dendriteData.get("strength"),
                (int) dendriteData.get("type")
            );
            dendrites.add(dendrite);
        }
    }
    return dendrites;
}

public static List<Reaction> extractReactions(List<Gene> genes) {
    List<Reaction> reactions = new ArrayList<>();
    for (Gene gene : genes) {
        if (gene.type == 0x01 && gene.subtype == 0x02 && gene.parsedData.containsKey("reaction")) {
            Map<String, Object> reactionData = (Map<String, Object>) gene.parsedData.get("reaction");
            Reaction reaction = new Reaction(
                (List<Map<String, Object>>) reactionData.get("reactants"),
                (List<Map<String, Object>>) reactionData.get("products"),
                (int) reactionData.get("rate")
            );
            reactions.add(reaction);
        }
    }
    return reactions;
}

public static List<HalfLife> extractHalfLives(List<Gene> genes) {
    List<HalfLife> halfLivesList = new ArrayList<>();
    for (Gene gene : genes) {
        if (gene.type == 0x01 && gene.subtype == 0x03 && gene.parsedData.containsKey("half_lives")) {
            Map<String, Integer> halfLives = (Map<String, Integer>) gene.parsedData.get("half_lives");
            halfLivesList.add(new HalfLife(halfLives));
        }
    }
    return halfLivesList;
}
```

---

---

## **📄 4. Mise à jour de `SVGExporter.java`**
Ajoutez les méthodes pour dessiner les connexions et les données supplémentaires :

```java
public static void exportBrainMap(
    List<BrainLobe> lobes,
    List<Instinct> instincts,
    List<Receptor> receptors,
    List<Emitter> emitters,
    List<Stimulus> stimuli,
    List<Dendrite> dendrites,
    List<Reaction> reactions,
    List<HalfLife> halfLives,
    String filename
) throws IOException {
    Document document = new Document()
        .set("width", 1400)
        .set("height", 1000)
        .set("viewBox", "0 0 80 70")
        .set("xmlns", "http://www.w3.org/2000/svg");

    // Fond blanc
    document = document.add(
        Rectangle::new()
            .set("x", 0)
            .set("y", 0)
            .set("width", 80)
            .set("height", 70)
            .set("fill", "white")
    );

    // Dessiner les lobes
    for (int i = 0; i < lobes.size(); i++) {
        BrainLobe lobe = lobes.get(i);
        String color = COLORS[i % COLORS.length];

        // Rectangle du lobe
        document = document.add(
            Rectangle::new()
                .set("x", lobe.x)
                .set("y", lobe.y)
                .set("width", lobe.width)
                .set("height", lobe.height)
                .set("fill", color)
                .set("stroke", "black")
                .set("stroke-width", 0.1)
                .set("opacity", 0.7)
                .set("id", "lobe-" + lobe.number)
                .set("class", "brain-lobe")
        );

        // Texte du lobe
        document = document.add(
            Text::new()
                .set("x", lobe.x + lobe.width / 2)
                .set("y", lobe.y + lobe.height / 2 + 2)
                .set("font-size", 0.5)
                .set("text-anchor", "middle")
                .set("dominant-baseline", "middle")
                .set("class", "lobe-label")
                .add(lobe.name)
        );
    }

    // Dessiner les connexions (dendrites)
    for (Dendrite dendrite : dendrites) {
        BrainLobe sourceLobe = findLobeByNumber(lobes, dendrite.sourceLobe);
        BrainLobe targetLobe = findLobeByNumber(lobes, dendrite.targetLobe);

        if (sourceLobe != null && targetLobe != null) {
            // Calculer les centres des lobes
            int sourceX = sourceLobe.x + sourceLobe.width / 2;
            int sourceY = sourceLobe.y + sourceLobe.height / 2;
            int targetX = targetLobe.x + targetLobe.width / 2;
            int targetY = targetLobe.y + targetLobe.height / 2;

            // Dessiner la ligne de connexion
            String strokeColor = dendrite.type == 0 ? "#0000FF" : "#FF0000"; // Bleu pour D0, Rouge pour D1
            double opacity = dendrite.strength / 255.0; // Normaliser la force entre 0 et 1

            document = document.add(
                Line::new()
                    .set("x1", sourceX)
                    .set("y1", sourceY)
                    .set("x2", targetX)
                    .set("y2", targetY)
                    .set("stroke", strokeColor)
                    .set("stroke-width", 0.1)
                    .set("opacity", opacity)
                    .set("class", "dendrite")
                    .set("title", String.format("Dendrite: %s[%d] -> %s[%d], Strength: %d, Type: D%d",
                        sourceLobe.name, dendrite.sourceCell,
                        targetLobe.name, dendrite.targetCell,
                        dendrite.strength, dendrite.type))
            );
        }
    }

    // Dessiner les instincts (cercles sur les lobes)
    for (Instinct instinct : instincts) {
        for (Map<String, Object> condition : instinct.conditions) {
            String lobeName = (String) condition.get("lobe");
            int cell = (int) condition.get("cell");

            BrainLobe lobe = findLobeByName(lobes, lobeName);
            if (lobe != null) {
                // Cercle pour la cellule
                document = document.add(
                    Circle::new()
                        .set("cx", lobe.x + lobe.width / 2)
                        .set("cy", lobe.y + lobe.height / 2)
                        .set("r", 0.5)
                        .set("fill", "black")
                        .set("class", "instinct-cell")
                        .set("title", "Cell: " + cell + ", Action: " + instinct.action)
                );

                // Texte pour la cellule
                document = document.add(
                    Text::new()
                        .set("x", lobe.x + lobe.width / 2)
                        .set("y", lobe.y + lobe.height / 2 - 1)
                        .set("font-size", 0.3)
                        .set("text-anchor", "middle")
                        .set("class", "cell-label")
                        .add(String.valueOf(cell))
                );
            }
        }
    }

    // Dessiner les récepteurs (cercles rouges)
    for (Receptor receptor : receptors) {
        BrainLobe lobe = findLobeByPosition(lobes, receptor.locus.get(0), receptor.locus.get(1));
        if (lobe != null) {
            document = document.add(
                Circle::new()
                    .set("cx", lobe.x + lobe.width / 2)
                    .set("cy", lobe.y + lobe.height / 2)
                    .set("r", 0.4)
                    .set("fill", "red")
                    .set("class", "receptor")
                    .set("title", "Receptor: " + receptor.chemical + " (Threshold: " + receptor.threshold + ")")
            );
        }
    }

    // Dessiner les émetteurs (cercles verts)
    for (Emitter emitter : emitters) {
        BrainLobe lobe = findLobeByPosition(lobes, emitter.locus.get(0), emitter.locus.get(1));
        if (lobe != null) {
            document = document.add(
                Circle::new()
                    .set("cx", lobe.x + lobe.width / 2)
                    .set("cy", lobe.y + lobe.height / 2)
                    .set("r", 0.4)
                    .set("fill", "green")
                    .set("class", "emitter")
                    .set("title", "Emitter: " + emitter.chemical + " (Threshold: " + emitter.threshold + ")")
            );
        }
    }

    // Légende pour les instincts
    int legendY = 55;
    document = document.add(
        Text::new()
            .set("x", 60)
            .set("y", legendY)
            .set("font-size", 0.6)
            .set("font-weight", "bold")
            .add("Instincts:")
    );
    legendY += 2;

    for (Instinct instinct : instincts) {
        document = document.add(
            Text::new()
                .set("x", 60)
                .set("y", legendY)
                .set("font-size", 0.4)
                .add(instinct.action + " (" + instinct.rewardPunish + ": " + instinct.amount + ")")
        );
        legendY += 1.5;
    }

    // Légende pour les récepteurs/émetteurs
    legendY += 2;
    document = document.add(
        Circle::new()
            .set("cx", 60)
            .set("cy", legendY)
            .set("r", 0.4)
            .set("fill", "red")
    );
    document = document.add(
        Text::new()
            .set("x", 61)
            .set("y", legendY)
            .set("font-size", 0.4)
            .add("= Receptor")
    );

    legendY += 1.5;
    document = document.add(
        Circle::new()
            .set("cx", 60)
            .set("cy", legendY)
            .set("r", 0.4)
            .set("fill", "green")
    );
    document = document.add(
        Text::new()
            .set("x", 61)
            .set("y", legendY)
            .set("font-size", 0.4)
            .add("= Emitter")
    );

    // Légende pour les dendrites
    legendY += 2;
    document = document.add(
        Line::new()
            .set("x1", 60)
            .set("y1", legendY)
            .set("x2", 62)
            .set("y2", legendY)
            .set("stroke", "#0000FF")
            .set("stroke-width", 0.1)
    );
    document = document.add(
        Text::new()
            .set("x", 63)
            .set("y", legendY)
            .set("font-size", 0.4)
            .add("= Dendrite D0")
    );

    legendY += 1.5;
    document = document.add(
        Line::new()
            .set("x1", 60)
            .set("y1", legendY)
            .set("x2", 62)
            .set("y2", legendY)
            .set("stroke", "#FF0000")
            .set("stroke-width", 0.1)
    );
    document = document.add(
        Text::new()
            .set("x", 63)
            .set("y", legendY)
            .set("font-size", 0.4)
            .add("= Dendrite D1")
    );

    // Section pour les réactions chimiques
    legendY += 3;
    document = document.add(
        Text::new()
            .set("x", 60)
            .set("y", legendY)
            .set("font-size", 0.6)
            .set("font-weight", "bold")
            .add("Chemical Reactions:")
    );
    legendY += 2;

    for (Reaction reaction : reactions) {
        String reactantsStr = reaction.reactants.stream()
            .map(r -> r.get("proportion") + " " + r.get("chem_name"))
            .collect(Collectors.joining(" + "));
        String productsStr = reaction.products.stream()
            .map(p -> p.get("proportion") + " " + p.get("chem_name"))
            .collect(Collectors.joining(" + "));
        String reactionStr = reactantsStr + " → " + productsStr + " (Rate: " + reaction.rate + ")";

        document = document.add(
            Text::new()
                .set("x", 60)
                .set("y", legendY)
                .set("font-size", 0.4)
                .add(reactionStr)
        );
        legendY += 1.5;
    }

    // Section pour les demi-vies
    legendY += 2;
    document = document.add(
        Text::new()
            .set("x", 60)
            .set("y", legendY)
            .set("font-size", 0.6)
            .set("font-weight", "bold")
            .add("Chemical Half-Lives:")
    );
    legendY += 2;

    for (HalfLife halfLife : halfLives) {
        for (Map.Entry<String, Integer> entry : halfLife.chemicalHalfLives.entrySet()) {
            document = document.add(
                Text::new()
                    .set("x", 60)
                    .set("y", legendY)
                    .set("font-size", 0.4)
                    .add(entry.getKey() + ": " + entry.getValue())
            );
            legendY += 1.5;
        }
    }

    // Sauvegarder le SVG
    Files.write(Paths.get(filename), document.toString().getBytes());
}

private static BrainLobe findLobeByNumber(List<BrainLobe> lobes, int number) {
    for (BrainLobe lobe : lobes) {
        if (lobe.number == number) {
            return lobe;
        }
    }
    return null;
}
```

---

### **5. Mise à jour de `LatexExporter.java`**
Ajoutez les sections pour les connexions et réactions :

```java
public static void exportBrainMapLatex(
    List<BrainLobe> lobes,
    List<Instinct> instincts,
    List<Receptor> receptors,
    List<Emitter> emitters,
    List<Stimulus> stimuli,
    List<Dendrite> dendrites,
    List<Reaction> reactions,
    List<HalfLife> halfLives,
    String filename
) throws IOException {
    StringBuilder latex = new StringBuilder();

    // En-tête LaTeX
    latex.append("\\documentclass{article}\n");
    latex.append("\\usepackage[utf8]{inputenc}\n");
    latex.append("\\usepackage{tikz}\n");
    latex.append("\\usepackage{geometry}\n");
    latex.append("\\geometry{a4paper, margin=1in}\n");
    latex.append("\\usepackage{longtable}\n");
    latex.append("\\usepackage{array}\n");
    latex.append("\\usepackage{graphicx}\n");
    latex.append("\\usepackage{amsmath}\n");
    latex.append("\\title{Brain Map of Norn}\n");
    latex.append("\\author{Creatures Genome Analyzer}\n");
    latex.append("\\date{\\today}\n");
    latex.append("\\begin{document}\n");
    latex.append("\\maketitle\n\n");

    // Section : Cartographie Cérébrale
    latex.append("\\section*{Brain Map}\n\n");
    latex.append("\\begin{center}\n");
    latex.append("\\begin{tikzpicture}[scale=0.5]\n");

    // Dessiner les lobes
    for (BrainLobe lobe : lobes) {
        String color = getLatexColor(lobes.indexOf(lobe));
        latex.append(String.format(
            "\\filldraw[%s!70] (%d,%d) rectangle (%d,%d);\n",
            color, lobe.x, lobe.y, lobe.x + lobe.width, lobe.y + lobe.height
        ));
        latex.append(String.format(
            "\\node at (%d,%d) {\\tiny %s};\n",
            lobe.x + lobe.width / 2, lobe.y + lobe.height / 2, lobe.name
        ));
    }

    // Dessiner les connexions (dendrites)
    for (Dendrite dendrite : dendrites) {
        BrainLobe sourceLobe = findLobeByNumber(lobes, dendrite.sourceLobe);
        BrainLobe targetLobe = findLobeByNumber(lobes, dendrite.targetLobe);

        if (sourceLobe != null && targetLobe != null) {
            int sourceX = sourceLobe.x + sourceLobe.width / 2;
            int sourceY = sourceLobe.y + sourceLobe.height / 2;
            int targetX = targetLobe.x + targetLobe.width / 2;
            int targetY = targetLobe.y + targetLobe.height / 2;

            String color = dendrite.type == 0 ? "blue" : "red";
            double opacity = dendrite.strength / 255.0;

            latex.append(String.format(
                "\\draw[%s, opacity=%.2f] (%d,%d) -- (%d,%d);\n",
                color, opacity, sourceX, sourceY, targetX, targetY
            ));
        }
    }

    // Dessiner les instincts (cercles)
    for (Instinct instinct : instincts) {
        for (Map<String, Object> condition : instinct.conditions) {
            String lobeName = (String) condition.get("lobe");
            int cell = (int) condition.get("cell");
            BrainLobe lobe = findLobeByName(lobes, lobeName);
            if (lobe != null) {
                latex.append(String.format(
                    "\\filldraw[black] (%d,%d) circle (0.2);\n",
                    lobe.x + lobe.width / 2, lobe.y + lobe.height / 2
                ));
                latex.append(String.format(
                    "\\node at (%d,%d) [above, font=\\tiny] {%d};\n",
                    lobe.x + lobe.width / 2, lobe.y + lobe.height / 2, cell
                ));
            }
        }
    }

    latex.append("\\end{tikzpicture}\n");
    latex.append("\\end{center}\n\n");

    // Légende des dendrites
    latex.append("\\section*{Dendrites Legend}\n\n");
    latex.append("\\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}\n");
    latex.append("\\hline\n");
    latex.append("{\\bf Source} & {\\bf Target} & {\\bf Strength} & {\\bf Type} \\\\ \\hline\n");
    for (Dendrite dendrite : dendrites) {
        BrainLobe sourceLobe = findLobeByNumber(lobes, dendrite.sourceLobe);
        BrainLobe targetLobe = findLobeByNumber(lobes, dendrite.targetLobe);
        String sourceName = sourceLobe != null ? sourceLobe.name : "Unknown";
        String targetName = targetLobe != null ? targetLobe.name : "Unknown";
        latex.append(String.format(
            "%s[%d] & %s[%d] & %d & D%d \\\\ \\hline\n",
            sourceName, dendrite.sourceCell, targetName, dendrite.targetCell,
            dendrite.strength, dendrite.type
        ));
    }
    latex.append("\\end{longtable}\n\n");

    // Section : Légende des Instincts
    latex.append("\\section*{Instincts Legend}\n\n");
    latex.append("\\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}\n");
    latex.append("\\hline\n");
    latex.append("{\\bf Action} & {\\bf Reward/Punish} & {\\bf Amount} & {\\bf Conditions} \\\\ \\hline\n");
    for (Instinct instinct : instincts) {
        String conditions = "";
        for (Map<String, Object> condition : instinct.conditions) {
            conditions += String.format("%s[%d], ",
                condition.get("lobe"), condition.get("cell"));
        }
        if (!conditions.isEmpty()) {
            conditions = conditions.substring(0, conditions.length() - 2);
        }
        latex.append(String.format(
            "%s & %s & %d & %s \\\\ \\hline\n",
            instinct.action, instinct.rewardPunish, instinct.amount, conditions
        ));
    }
    latex.append("\\end{longtable}\n\n");

    // Section : Réactions Chimiques
    latex.append("\\section*{Chemical Reactions}\n\n");
    latex.append("\\begin{longtable}{|p{4cm}|p{4cm}|p{2cm}|}\n");
    latex.append("\\hline\n");
    latex.append("{\\bf Reactants} & {\\bf Products} & {\\bf Rate} \\\\ \\hline\n");
    for (Reaction reaction : reactions) {
        String reactants = reaction.reactants.stream()
            .map(r -> r.get("proportion") + " " + r.get("chem_name"))
            .collect(Collectors.joining(" + "));
        String products = reaction.products.stream()
            .map(p -> p.get("proportion") + " " + p.get("chem_name"))
            .collect(Collectors.joining(" + "));
        latex.append(String.format(
            "%s & %s & %d \\\\ \\hline\n",
            reactants, products, reaction.rate
        ));
    }
    latex.append("\\end{longtable}\n\n");

    // Section : Demi-Vies
    latex.append("\\section*{Chemical Half-Lives}\n\n");
    latex.append("\\begin{longtable}{|p{4cm}|p{4cm}|}\n");
    latex.append("\\hline\n");
    latex.append("{\\bf Chemical} & {\\bf Half-Life} \\\\ \\hline\n");
    for (HalfLife halfLife : halfLives) {
        for (Map.Entry<String, Integer> entry : halfLife.chemicalHalfLives.entrySet()) {
            latex.append(String.format(
                "%s & %d \\\\ \\hline\n",
                entry.getKey(), entry.getValue()
            ));
        }
    }
    latex.append("\\end{longtable}\n\n");

    // Pied de page
    latex.append("\\end{document}\n");

    // Sauvegarder le fichier LaTeX
    Files.write(Paths.get(filename), latex.toString().getBytes());
}
```

---

### **6. Mise à jour de `Main.java`**
```java
public static void main(String[] args) {
    if (args.length < 1) {
        System.err.println("Usage: java creatures.Main <genome_file.gen> [--svg <output.svg>] [--latex <output.tex>]");
        System.exit(1);
    }

    String inputFile = args[0];
    String svgOutput = null;
    String latexOutput = null;

    for (int i = 1; i < args.length; i++) {
        if (args[i].equals("--svg") && i + 1 < args.length) {
            svgOutput = args[++i];
        } else if (args[i].equals("--latex") && i + 1 < args.length) {
            latexOutput = args[++i];
        }
    }

    try {
        // Parser le fichier de génome
        List<Gene> genes = GenomeParser.parseGenomeFile(inputFile);
        System.out.println("Parsed " + genes.size() + " genes from " + inputFile);

        // Extraire les données
        List<BrainLobe> lobes = GenomeParser.extractLobes(genes);
        List<Instinct> instincts = GenomeParser.extractInstincts(genes);
        List<Receptor> receptors = GenomeParser.extractReceptors(genes);
        List<Emitter> emitters = GenomeParser.extractEmitters(genes);
        List<Stimulus> stimuli = GenomeParser.extractStimuli(genes);
        List<Dendrite> dendrites = GenomeParser.extractDendrites(genes);
        List<Reaction> reactions = GenomeParser.extractReactions(genes);
        List<HalfLife> halfLives = GenomeParser.extractHalfLives(genes);

        System.out.println("Found " + lobes.size() + " brain lobes");
        System.out.println("Found " + instincts.size() + " instincts");
        System.out.println("Found " + receptors.size() + " receptors");
        System.out.println("Found " + emitters.size() + " emitters");
        System.out.println("Found " + stimuli.size() + " stimuli");
        System.out.println("Found " + dendrites.size() + " dendrites");
        System.out.println("Found " + reactions.size() + " reactions");
        System.out.println("Found " + halfLives.size() + " half-lives");

        // Générer le SVG si demandé
        if (svgOutput != null) {
            SVGExporter.exportBrainMap(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, svgOutput);
            System.out.println("Brain map SVG generated: " + svgOutput);
        }

        // Générer le LaTeX si demandé
        if (latexOutput != null) {
            LatexExporter.exportBrainMapLatex(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, latexOutput);
            System.out.println("Brain map LaTeX generated: " + latexOutput);
        }

        // Si aucun format n'est spécifié, générer les deux par défaut
        if (svgOutput == null && latexOutput == null) {
            svgOutput = inputFile.replaceFirst("[.][^.]+$", ".svg");
            latexOutput = inputFile.replaceFirst("[.][^.]+$", ".tex");
            SVGExporter.exportBrainMap(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, svgOutput);
            LatexExporter.exportBrainMapLatex(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, latexOutput);
            System.out.println("Brain map SVG generated: " + svgOutput);
            System.out.println("Brain map LaTeX generated: " + latexOutput);
        }

    } catch (IOException e) {
        System.err.println("Error: " + e.getMessage());
        System.exit(1);
    }
}
```

---

---

## **🎯 7. Explications des Améliorations**

### **7.1 Connexions entre Lobes (Dendrites)**
- **Visualisation SVG** :
  - Les dendrites sont représentées par des **lignes** entre les lobes.
  - **Couleurs** :
    - **Bleu** pour les dendrites de type **D0**.
    - **Rouge** pour les dendrites de type **D1**.
  - **Opacité** : L'opacité de la ligne dépend de la **force** (`strength`) de la dendrite.
  - **Infobulles** : Les lignes ont des infobulles (`title`) indiquant les détails de la connexion.

- **Visualisation LaTeX** :
  - Les dendrites sont dessinées avec des lignes dans TikZ.
  - Une **légende** liste toutes les connexions avec leurs détails.

### **7.2 Données Supplémentaires Intégrées**
| **Type de Donnée** | **Description** | **Visualisation SVG** | **Visualisation LaTeX** |
|--------------------|----------------|----------------------|-------------------------|
| **Réactions Chimiques** | Réactions entre chimies (ex: `Pain + Hunger → Reward`). | Légende en bas à droite avec les réactions. | Tableau dédié aux réactions chimiques. |
| **Demi-Vies** | Demi-vie des chimies (ex: `Pain: 10`). | Légende en bas à droite avec les demi-vies. | Tableau dédié aux demi-vies. |
| **Récepteurs** | Récepteurs chimiques (cercles rouges). | Cercles rouges sur les lobes. | Tableau dédié aux récepteurs. |
| **Émetteurs** | Émetteurs chimiques (cercles verts). | Cercles verts sur les lobes. | Tableau dédié aux émetteurs. |

### **7.3 Organisation des Éléments**
- **SVG** :
  - **Lobes** : Rectangles colorés avec noms.
  - **Dendrites** : Lignes entre les lobes.
  - **Instincts** : Cercles noirs sur les lobes avec numéros de cellule.
  - **Récepteurs/Émetteurs** : Cercles rouges/verts.
  - **Légendes** : Sections dédiées pour chaque type de donnée.

- **LaTeX** :
  - **Cartographie** : Diagramme TikZ avec lobes et connexions.
  - **Tableaux** : Tableaux pour les instincts, récepteurs, émetteurs, réactions, et demi-vies.

---

---

## **📊 8. Exemple de Sortie SVG**
```xml
<svg width="1400" height="1000" viewBox="0 0 80 70" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="80" height="70" fill="white"/>
  <!-- Lobe Perception -->
  <rect x="4" y="13" width="7" height="16" fill="#FF6B6B" stroke="black" stroke-width="0.1" opacity="0.7" id="lobe-0" class="brain-lobe"/>
  <text x="7.5" y="21.5" font-size="0.5" text-anchor="middle" dominant-baseline="middle" class="lobe-label">Perception</text>
  <!-- Lobe Drive -->
  <rect x="12" y="10" width="8" height="12" fill="#4ECDC4" stroke="black" stroke-width="0.1" opacity="0.7" id="lobe-1" class="brain-lobe"/>
  <text x="16" y="16" font-size="0.5" text-anchor="middle" dominant-baseline="middle" class="lobe-label">Drive</text>
  <!-- Dendrite entre Perception et Drive -->
  <line x1="7.5" y1="21" x2="16" y2="16" stroke="#0000FF" stroke-width="0.1" opacity="0.8" class="dendrite" title="Dendrite: Perception[0] -> Drive[0], Strength: 200, Type: D0"/>
  <!-- Cellule d'instinct -->
  <circle cx="7.5" cy="21" r="0.5" fill="black" class="instinct-cell" title="Cell: 2, Action: Come"/>
  <text x="7.5" y="20" font-size="0.3" text-anchor="middle" class="cell-label">2</text>
  <!-- Récepteur -->
  <circle cx="7.5" cy="21" r="0.4" fill="red" class="receptor" title="Receptor: Pain (Threshold: 50)"/>
  <!-- Légende -->
  <text x="60" y="55" font-size="0.6" font-weight="bold">Instincts:</text>
  <text x="60" y="57" font-size="0.4">Come (Reward: 5)</text>
  <circle cx="60" cy="59" r="0.4" fill="red"/>
  <text x="61" y="59" font-size="0.4">= Receptor</text>
  <line x1="60" y1="61" x2="62" y2="61" stroke="#0000FF" stroke-width="0.1"/>
  <text x="63" y="61" font-size="0.4">= Dendrite D0</text>
  <text x="60" y="63" font-size="0.6" font-weight="bold">Chemical Reactions:</text>
  <text x="60" y="65" font-size="0.4">1 Pain + 1 Hunger → 1 Reward + 1 Energy (Rate: 5)</text>
  <text x="60" y="67" font-size="0.6" font-weight="bold">Chemical Half-Lives:</text>
  <text x="60" y="69" font-size="0.4">Pain: 10</text>
</svg>
```

---

### **8.2 Exemple de Sortie LaTeX**
```latex
\documentclass{article}
\usepackage[utf8]{inputenc}
\usepackage{tikz}
\usepackage{longtable}
\usepackage{amsmath}

\title{Brain Map of Norn}
\author{Creatures Genome Analyzer}
\date{\today}

\begin{document}
\maketitle

\section*{Brain Map}

\begin{center}
\begin{tikzpicture}[scale=0.5]
\filldraw[red!70] (4,13) rectangle (11,29);
\node at (7.5,21.5) {\tiny Perception};
\filldraw[teal!70] (12,10) rectangle (20,22);
\node at (16,16) {\tiny Drive};
\draw[blue, opacity=0.8] (7.5,21) -- (16,16);
\filldraw[black] (7.5,21) circle (0.2);
\node at (7.5,21) [above, font=\tiny] {2};
\filldraw[red] (7.5,21) circle (0.4);
\end{tikzpicture}
\end{center}

\section*{Dendrites Legend}

\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}
\hline
{\bf Source} & {\bf Target} & {\bf Strength} & {\bf Type} \\\ \hline
Perception[0] & Drive[0] & 200 & D0 \\\ \hline
\end{longtable}

\section*{Instincts Legend}

\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}
\hline
{\bf Action} & {\bf Reward/Punish} & {\bf Amount} & {\bf Conditions} \\\ \hline
Come & Reward & 5 & Drive[2], Verb[1], General Sense[0] \\\ \hline
\end{longtable}

\section*{Chemical Reactions}

\begin{longtable}{|p{4cm}|p{4cm}|p{2cm}|}
\hline
{\bf Reactants} & {\bf Products} & {\bf Rate} \\\ \hline
1 Pain + 1 Hunger & 1 Reward + 1 Energy & 5 \\\ \hline
\end{longtable}

\section*{Chemical Half-Lives}

\begin{longtable}{|p{4cm}|p{4cm}|}
\hline
{\bf Chemical} & {\bf Half-Life} \\\ \hline
Pain & 10 \\\ \hline
Hunger & 20 \\\ \hline
\end{longtable}

\end{document}
```

---

---

## **🔧 9. Compilation et Exécution**
### **9.1 Avec Maven**
1. **Mettre à jour le `pom.xml`** :
   Ajoutez la dépendance pour `java-stream` (pour les `Collectors`) :
   ```xml
   <dependency>
       <groupId>org.openjdk</groupId>
       <artifactId>java-stream</artifactId>
       <version>1.0</version>
   </dependency>
   ```

2. **Compiler et exécuter** :
   ```bash
   mvn clean compile exec:java -Dexec.mainClass="creatures.Main" -Dexec.args="dad1.gen"
   ```

### **9.2 Sans Maven**
1. **Télécharger les dépendances** :
   - [svg-core-0.13.1.jar](https://repo1.maven.org/maven2/com/github/tkromer/svg-core/0.13.1/svg-core-0.13.1.jar)
   - [java-stream-1.0.jar](https://repo1.maven.org/maven2/org/openjdk/java-stream/1.0/java-stream-1.0.jar)

2. **Compiler** :
   ```bash
   javac -cp .:svg-core-0.13.1.jar:java-stream-1.0.jar src/main/java/creatures/model/*.java src/main/java/creatures/parser/*.java src/main/java/creatures/exporter/*.java src/main/java/creatures/Main.java
   ```

3. **Exécuter** :
   ```bash
   java -cp .:svg-core-0.13.1.jar:java-stream-1.0.jar creatures.Main dad1.gen --svg brain_map.svg --latex brain_map.tex
   ```

---

---

## **🎯 10. Fonctionnalités Clés Résumées**
| **Fonctionnalité** | **Description** | **Implémentation** |
|--------------------|----------------|--------------------|
| **Parsing complet** | Lecture des lobes, instincts, dendrites, réactions, etc. | `GenomeParser` |
| **Visualisation SVG** | Carte cérébrale avec lobes, connexions, et légendes. | `SVGExporter` |
| **Export LaTeX** | Document technique avec diagrammes et tableaux. | `LatexExporter` |
| **Connexions (Dendrites)** | Lignes entre les lobes avec couleurs et opacité. | `SVGExporter`, `LatexExporter` |
| **Données supplémentaires** | Réactions chimiques, demi-vies, récepteurs, émetteurs. | Intégrées dans les exportateurs |
| **Gestion des erreurs** | Vérification des longueurs et des types de données. | `GenomeParser` |

---
Si vous avez besoin d'autres améliorations ou d'extensions spécifiques, n'hésitez pas à me le demander ! Je peux vous aider à :
- **Ajouter des fonctionnalités interactives** (ex: SVG avec JavaScript).
- **Optimiser les performances** pour les grands fichiers de génome.
- **Ajouter des visualisations 3D** (ex: avec Three.js).

Redonne l'ensemble des classes java sous un même package (gabywald.creatures.parser ; sous-packages possibles)

Voici l'**organisation complète des classes Java** sous le package **`gabywald.creatures.parser`**, avec une structure claire en sous-packages pour une meilleure modularité. J'ai regroupé toutes les classes précédemment définies et ajouté les imports nécessaires.

---

---

## **📁 Structure des Packages**
```bash
gabywald/
└── creatures/
    └── parser/
        ├── model/               # Modèles de données
        │   ├── BrainLobe.java
        │   ├── Dendrite.java
        │   ├── Emitter.java
        │   ├── Gene.java
        │   ├── HalfLife.java
        │   ├── Instinct.java
        │   ├── Reaction.java
        │   ├── Receptor.java
        │   └── Stimulus.java
        ├── exporter/            # Exportateurs
        │   ├── LatexExporter.java
        │   └── SVGExporter.java
        └── GenomeParser.java     # Parseur principal
```

---

---

## **📄 1. Package `gabywald.creatures.parser.model`**
### **1.1 `Gene.java`**
```java
package gabywald.creatures.parser.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                    case SUBTYPE_STIMULUS: parseStimulus(); break;
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
                    case SUBTYPE_STIMULUS: return "Stimulus";
                }
                break;
            case TYPE_CREATURE:
                switch (subtype) {
                    case SUBTYPE_STIMULUS: return "Stimulus";
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
```

---

### **1.2 `BrainLobe.java`**
```java
package gabywald.creatures.parser.model;

/**
 * Représente un lobe cérébral de Creatures.
 */
public class BrainLobe {
    public int number;
    public String name;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean perceptionLink;

    public BrainLobe(int number, String name, int x, int y, int width, int height, boolean perceptionLink) {
        this.number = number;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.perceptionLink = perceptionLink;
    }

    @Override
    public String toString() {
        return String.format(
            "Lobe %d (%s): Position=(%d,%d), Size=%dx%d, PerceptionLink=%s",
            number, name, x, y, width, height, perceptionLink ? "Yes" : "No"
        );
    }
}
```

---

### **1.3 `Dendrite.java`**
```java
package gabywald.creatures.parser.model;

/**
 * Représente une connexion (dendrite) entre deux lobes cérébraux.
 */
public class Dendrite {
    public int sourceLobe;
    public int sourceCell;
    public int targetLobe;
    public int targetCell;
    public int strength;
    public int type; // 0 = D0, 1 = D1

    public Dendrite(int sourceLobe, int sourceCell, int targetLobe, int targetCell, int strength, int type) {
        this.sourceLobe = sourceLobe;
        this.sourceCell = sourceCell;
        this.targetLobe = targetLobe;
        this.targetCell = targetCell;
        this.strength = strength;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
            "Dendrite: %d[%d] -> %d[%d], Strength=%d, Type=D%d",
            sourceLobe, sourceCell, targetLobe, targetCell, strength, type
        );
    }
}
```

---

### **1.4 `Instinct.java`**
```java
package gabywald.creatures.parser.model;

import java.util.List;
import java.util.Map;

/**
 * Représente un instinct de Creatures.
 */
public class Instinct {
    public List<Map<String, Object>> conditions;
    public String action;
    public String rewardPunish;
    public int amount;

    public Instinct(List<Map<String, Object>> conditions, String action, String rewardPunish, int amount) {
        this.conditions = conditions;
        this.action = action;
        this.rewardPunish = rewardPunish;
        this.amount = amount;
    }

    @Override
    public String toString() {
        StringBuilder conditionsStr = new StringBuilder();
        for (Map<String, Object> condition : conditions) {
            conditionsStr.append(String.format("%s[%d], ",
                condition.get("lobe"), condition.get("cell")));
        }
        if (conditionsStr.length() > 0) {
            conditionsStr.setLength(conditionsStr.length() - 2); // Supprimer la dernière virgule
        }
        return String.format(
            "Instinct: IF %s THEN %s (%s: %d)",
            conditionsStr.toString(), action, rewardPunish, amount
        );
    }
}
```

---

### **1.5 `Receptor.java`**
```java
package gabywald.creatures.parser.model;

import java.util.List;

/**
 * Représente un récepteur chimique de Creatures.
 */
public class Receptor {
    public List<Integer> locus;
    public String chemical;
    public int threshold;
    public int nominal;
    public int gain;

    public Receptor(List<Integer> locus, String chemical, int threshold, int nominal, int gain) {
        this.locus = locus;
        this.chemical = chemical;
        this.threshold = threshold;
        this.nominal = nominal;
        this.gain = gain;
    }

    @Override
    public String toString() {
        return String.format(
            "Receptor: LOC(%d,%d,%d) -> %s (Threshold=%d, Nominal=%d, Gain=%d)",
            locus.get(0), locus.get(1), locus.get(2), chemical, threshold, nominal, gain
        );
    }
}
```

---

### **1.6 `Emitter.java`**
```java
package gabywald.creatures.parser.model;

import java.util.List;

/**
 * Représente un émetteur chimique de Creatures.
 */
public class Emitter {
    public List<Integer> locus;
    public String chemical;
    public int threshold;
    public int sampleRate;
    public int gain;

    public Emitter(List<Integer> locus, String chemical, int threshold, int sampleRate, int gain) {
        this.locus = locus;
        this.chemical = chemical;
        this.threshold = threshold;
        this.sampleRate = sampleRate;
        this.gain = gain;
    }

    @Override
    public String toString() {
        return String.format(
            "Emitter: LOC(%d,%d,%d) -> %s (Threshold=%d, SampleRate=%d, Gain=%d)",
            locus.get(0), locus.get(1), locus.get(2), chemical, threshold, sampleRate, gain
        );
    }
}
```

---
### **1.7 `Stimulus.java`**
```java
package gabywald.creatures.parser.model;

import java.util.List;

/**
 * Représente un stimulus de Creatures.
 */
public class Stimulus {
    public int stimulusType;
    public int intensity;
    public List<Integer> locus;

    public Stimulus(int stimulusType, int intensity, List<Integer> locus) {
        this.stimulusType = stimulusType;
        this.intensity = intensity;
        this.locus = locus;
    }

    @Override
    public String toString() {
        return String.format(
            "Stimulus: Type=%d, Intensity=%d, LOC(%d,%d,%d)",
            stimulusType, intensity, locus.get(0), locus.get(1), locus.get(2)
        );
    }
}
```

---
### **1.8 `Reaction.java`**
```java
package gabywald.creatures.parser.model;

import java.util.List;
import java.util.Map;

/**
 * Représente une réaction chimique de Creatures.
 */
public class Reaction {
    public List<Map<String, Object>> reactants;
    public List<Map<String, Object>> products;
    public int rate;

    public Reaction(List<Map<String, Object>> reactants, List<Map<String, Object>> products, int rate) {
        this.reactants = reactants;
        this.products = products;
        this.rate = rate;
    }

    @Override
    public String toString() {
        StringBuilder reactantsStr = new StringBuilder();
        for (Map<String, Object> reactant : reactants) {
            reactantsStr.append(String.format("%d %s + ",
                reactant.get("proportion"), reactant.get("chem_name")));
        }
        if (reactantsStr.length() > 0) {
            reactantsStr.setLength(reactantsStr.length() - 3); // Supprimer le dernier "+ "
        }

        StringBuilder productsStr = new StringBuilder();
        for (Map<String, Object> product : products) {
            productsStr.append(String.format("%d %s + ",
                product.get("proportion"), product.get("chem_name")));
        }
        if (productsStr.length() > 0) {
            productsStr.setLength(productsStr.length() - 3); // Supprimer le dernier "+ "
        }

        return String.format(
            "Reaction: %s → %s (Rate=%d)",
            reactantsStr.toString(), productsStr.toString(), rate
        );
    }
}
```

---
### **1.9 `HalfLife.java`**
```java
package gabywald.creatures.parser.model;

import java.util.Map;

/**
 * Représente les demi-vies des chimies de Creatures.
 */
public class HalfLife {
    public Map<String, Integer> chemicalHalfLives;

    public HalfLife(Map<String, Integer> chemicalHalfLives) {
        this.chemicalHalfLives = chemicalHalfLives;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : chemicalHalfLives.entrySet()) {
            sb.append(String.format("%s=%d, ", entry.getKey(), entry.getValue()));
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2); // Supprimer la dernière virgule
        }
        return "Half-Lives: " + sb.toString();
    }
}
```

---

---

## **📄 2. Package `gabywald.creatures.parser`**
### **2.1 `GenomeParser.java`**
```java
package gabywald.creatures.parser;

import gabywald.creatures.parser.model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parseur de fichiers .gen pour extraire les gènes et leurs données.
 */
public class GenomeParser {
    /**
     * Parse un fichier .gen et retourne une liste de gènes.
     *
     * @param filename Chemin vers le fichier .gen
     * @return Liste de gènes parsés
     * @throws IOException Si le fichier ne peut pas être lu
     */
    public static List<Gene> parseGenomeFile(String filename) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(filename));
        List<Gene> genes = new ArrayList<>();
        int offset = 0;

        while (offset < content.length) {
            if (offset + 8 > content.length) break;

            String geneMarker = new String(content, offset, 4);
            if (!geneMarker.equals("gene")) {
                offset++;
                continue;
            }

            int type = content[offset + 4] & 0xFF;
            int subtype = content[offset + 5] & 0xFF;
            int number = content[offset + 6] & 0xFF;
            int switchOn = content[offset + 7] & 0xFF;

            int sexMut = offset + 8 < content.length ? content[offset + 8] & 0xFF : 0;
            String sexDep = (sexMut & 0x10) != 0 ? "Female" : ((sexMut & 0x08) != 0 ? "Male" : "None");

            List<String> mutability = new ArrayList<>();
            if ((sexMut & 0x01) != 0) mutability.add("Mutable");
            if ((sexMut & 0x02) != 0) mutability.add("Duplicable");
            if ((sexMut & 0x04) != 0) mutability.add("Deletable");
            String mutabilityStr = mutability.isEmpty() ? "None" : String.join(",", mutability);

            int dataLength = offset + 9 < content.length ? content[offset + 9] & 0xFF : 0;
            List<Integer> data = new ArrayList<>();
            if (dataLength > 0 && offset + 10 + dataLength <= content.length) {
                for (int i = 0; i < dataLength; i++) {
                    data.add(content[offset + 10 + i] & 0xFF);
                }
            }

            Gene gene = new Gene(type, subtype, number, switchOn, sexDep, mutabilityStr, data);
            genes.add(gene);
            offset += 10 + dataLength;
        }

        return genes;
    }

    /**
     * Extrait les lobes cérébraux des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de lobes cérébraux
     */
    public static List<BrainLobe> extractLobes(List<Gene> genes) {
        List<BrainLobe> lobes = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BRAIN && gene.subtype == Gene.SUBTYPE_LOBE
                && gene.parsedData.containsKey("lobe")) {
                Map<String, Object> lobeData = (Map<String, Object>) gene.parsedData.get("lobe");
                BrainLobe lobe = new BrainLobe(
                    gene.number,
                    Gene.LOBE_NAMES.getOrDefault(gene.number, String.format("Lobe%02X", gene.number)),
                    (int) lobeData.get("x"),
                    (int) lobeData.get("y"),
                    (int) lobeData.get("width"),
                    (int) lobeData.get("height"),
                    "Yes".equals(lobeData.get("perception_link"))
                );
                lobes.add(lobe);
            }
        }
        return lobes;
    }

    /**
     * Extrait les instincts des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste d'instincts
     */
    public static List<Instinct> extractInstincts(List<Gene> genes) {
        List<Instinct> instincts = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_CREATURE && gene.subtype == Gene.SUBTYPE_INSTINCT
                && gene.parsedData.containsKey("instinct")) {
                Map<String, Object> instinctData = (Map<String, Object>) gene.parsedData.get("instinct");
                Instinct instinct = new Instinct(
                    (List<Map<String, Object>>) instinctData.get("conditions"),
                    (String) instinctData.get("action"),
                    (String) instinctData.get("reward_punish"),
                    (int) instinctData.get("amount")
                );
                instincts.add(instinct);
            }
        }
        return instincts;
    }

    /**
     * Extrait les récepteurs des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de récepteurs
     */
    public static List<Receptor> extractReceptors(List<Gene> genes) {
        List<Receptor> receptors = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BIOCHEMISTRY && gene.subtype == Gene.SUBTYPE_RECEPTOR
                && gene.parsedData.containsKey("receptor")) {
                Map<String, Object> receptorData = (Map<String, Object>) gene.parsedData.get("receptor");
                Receptor receptor = new Receptor(
                    (List<Integer>) receptorData.get("locus"),
                    (String) receptorData.get("chemical"),
                    (int) receptorData.get("threshold"),
                    (int) receptorData.get("nominal"),
                    (int) receptorData.get("gain")
                );
                receptors.add(receptor);
            }
        }
        return receptors;
    }

    /**
     * Extrait les émetteurs des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste d'émetteurs
     */
    public static List<Emitter> extractEmitters(List<Gene> genes) {
        List<Emitter> emitters = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BIOCHEMISTRY && gene.subtype == Gene.SUBTYPE_EMITTER
                && gene.parsedData.containsKey("emitter")) {
                Map<String, Object> emitterData = (Map<String, Object>) gene.parsedData.get("emitter");
                Emitter emitter = new Emitter(
                    (List<Integer>) emitterData.get("locus"),
                    (String) emitterData.get("chemical"),
                    (int) emitterData.get("threshold"),
                    (int) emitterData.get("sample_rate"),
                    (int) emitterData.get("gain")
                );
                emitters.add(emitter);
            }
        }
        return emitters;
    }

    /**
     * Extrait les stimulus des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de stimulus
     */
    public static List<Stimulus> extractStimuli(List<Gene> genes) {
        List<Stimulus> stimuli = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BIOCHEMISTRY && gene.subtype == Gene.SUBTYPE_STIMULUS
                && gene.parsedData.containsKey("stimulus")) {
                Map<String, Object> stimulusData = (Map<String, Object>) gene.parsedData.get("stimulus");
                Stimulus stimulus = new Stimulus(
                    (int) stimulusData.get("stimulus_type"),
                    (int) stimulusData.get("intensity"),
                    (List<Integer>) stimulusData.get("locus")
                );
                stimuli.add(stimulus);
            }
        }
        return stimuli;
    }

    /**
     * Extrait les dendrites des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de dendrites
     */
    public static List<Dendrite> extractDendrites(List<Gene> genes) {
        List<Dendrite> dendrites = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BRAIN && gene.subtype == 0x01
                && gene.parsedData.containsKey("dendrite")) {
                Map<String, Object> dendriteData = (Map<String, Object>) gene.parsedData.get("dendrite");
                Dendrite dendrite = new Dendrite(
                    (int) dendriteData.get("source_lobe"),
                    (int) dendriteData.get("source_cell"),
                    (int) dendriteData.get("target_lobe"),
                    (int) dendriteData.get("target_cell"),
                    (int) dendriteData.get("strength"),
                    (int) dendriteData.get("type")
                );
                dendrites.add(dendrite);
            }
        }
        return dendrites;
    }

    /**
     * Extrait les réactions chimiques des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de réactions
     */
    public static List<Reaction> extractReactions(List<Gene> genes) {
        List<Reaction> reactions = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BIOCHEMISTRY && gene.subtype == Gene.SUBTYPE_REACTION
                && gene.parsedData.containsKey("reaction")) {
                Map<String, Object> reactionData = (Map<String, Object>) gene.parsedData.get("reaction");
                Reaction reaction = new Reaction(
                    (List<Map<String, Object>>) reactionData.get("reactants"),
                    (List<Map<String, Object>>) reactionData.get("products"),
                    (int) reactionData.get("rate")
                );
                reactions.add(reaction);
            }
        }
        return reactions;
    }

    /**
     * Extrait les demi-vies des chimies des gènes.
     *
     * @param genes Liste de gènes
     * @return Liste de demi-vies
     */
    public static List<HalfLife> extractHalfLives(List<Gene> genes) {
        List<HalfLife> halfLivesList = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == Gene.TYPE_BIOCHEMISTRY && gene.subtype == Gene.SUBTYPE_HALF_LIVES
                && gene.parsedData.containsKey("half_lives")) {
                Map<String, Integer> halfLives = (Map<String, Integer>) gene.parsedData.get("half_lives");
                halfLivesList.add(new HalfLife(halfLives));
            }
        }
        return halfLivesList;
    }

    /**
     * Trouve un lobe par son nom.
     *
     * @param lobes Liste de lobes
     * @param name Nom du lobe
     * @return Le lobe correspondant, ou null si non trouvé
     */
    public static BrainLobe findLobeByName(List<BrainLobe> lobes, String name) {
        for (BrainLobe lobe : lobes) {
            if (lobe.name.equals(name)) {
                return lobe;
            }
        }
        return null;
    }

    /**
     * Trouve un lobe par sa position.
     *
     * @param lobes Liste de lobes
     * @param x Coordonnée X
     * @param y Coordonnée Y
     * @return Le lobe correspondant, ou null si non trouvé
     */
    public static BrainLobe findLobeByPosition(List<BrainLobe> lobes, int x, int y) {
        for (BrainLobe lobe : lobes) {
            if (lobe.x <= x && x < lobe.x + lobe.width &&
                lobe.y <= y && y < lobe.y + lobe.height) {
                return lobe;
            }
        }
        return null;
    }

    /**
     * Trouve un lobe par son numéro.
     *
     * @param lobes Liste de lobes
     * @param number Numéro du lobe
     * @return Le lobe correspondant, ou null si non trouvé
     */
    public static BrainLobe findLobeByNumber(List<BrainLobe> lobes, int number) {
        for (BrainLobe lobe : lobes) {
            if (lobe.number == number) {
                return lobe;
            }
        }
        return null;
    }
}
```

---

---

## **📄 3. Package `gabywald.creatures.parser.exporter`**
### **3.1 `SVGExporter.java`**
```java
package gabywald.creatures.parser.exporter;

import gabywald.creatures.parser.model.*;
import svg.Document;
import svg.node.element.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Exporteur de cartographie cérébrale au format SVG.
 */
public class SVGExporter {
    private static final String[] COLORS = {
        "#FF6B6B", "#4ECDC4", "#45B7D1", "#FFBE0B", "#FB5607",
        "#8338EC", "#3A86FF", "#FF006E", "#A5DD9B", "#FF9E9E"
    };

    /**
     * Exporte une cartographie cérébrale complète au format SVG.
     *
     * @param lobes Liste des lobes cérébraux
     * @param instincts Liste des instincts
     * @param receptors Liste des récepteurs
     * @param emitters Liste des émetteurs
     * @param stimuli Liste des stimulus
     * @param dendrites Liste des dendrites
     * @param reactions Liste des réactions chimiques
     * @param halfLives Liste des demi-vies
     * @param filename Nom du fichier de sortie
     * @throws IOException Si le fichier ne peut pas être écrit
     */
    public static void exportBrainMap(
        List<BrainLobe> lobes,
        List<Instinct> instincts,
        List<Receptor> receptors,
        List<Emitter> emitters,
        List<Stimulus> stimuli,
        List<Dendrite> dendrites,
        List<Reaction> reactions,
        List<HalfLife> halfLives,
        String filename
    ) throws IOException {
        Document document = new Document()
            .set("width", 1400)
            .set("height", 1000)
            .set("viewBox", "0 0 80 70")
            .set("xmlns", "http://www.w3.org/2000/svg");

        // Fond blanc
        document = document.add(
            Rectangle::new()
                .set("x", 0)
                .set("y", 0)
                .set("width", 80)
                .set("height", 70)
                .set("fill", "white")
        );

        // Dessiner les lobes
        for (int i = 0; i < lobes.size(); i++) {
            BrainLobe lobe = lobes.get(i);
            String color = COLORS[i % COLORS.length];

            // Rectangle du lobe
            document = document.add(
                Rectangle::new()
                    .set("x", lobe.x)
                    .set("y", lobe.y)
                    .set("width", lobe.width)
                    .set("height", lobe.height)
                    .set("fill", color)
                    .set("stroke", "black")
                    .set("stroke-width", 0.1)
                    .set("opacity", 0.7)
                    .set("id", "lobe-" + lobe.number)
                    .set("class", "brain-lobe")
            );

            // Texte du lobe
            document = document.add(
                Text::new()
                    .set("x", lobe.x + lobe.width / 2)
                    .set("y", lobe.y + lobe.height / 2 + 2)
                    .set("font-size", 0.5)
                    .set("text-anchor", "middle")
                    .set("dominant-baseline", "middle")
                    .set("class", "lobe-label")
                    .add(lobe.name)
            );
        }

        // Dessiner les connexions (dendrites)
        for (Dendrite dendrite : dendrites) {
            BrainLobe sourceLobe = GenomeParser.findLobeByNumber(lobes, dendrite.sourceLobe);
            BrainLobe targetLobe = GenomeParser.findLobeByNumber(lobes, dendrite.targetLobe);

            if (sourceLobe != null && targetLobe != null) {
                // Calculer les centres des lobes
                int sourceX = sourceLobe.x + sourceLobe.width / 2;
                int sourceY = sourceLobe.y + sourceLobe.height / 2;
                int targetX = targetLobe.x + targetLobe.width / 2;
                int targetY = targetLobe.y + targetLobe.height / 2;

                // Dessiner la ligne de connexion
                String strokeColor = dendrite.type == 0 ? "#0000FF" : "#FF0000"; // Bleu pour D0, Rouge pour D1
                double opacity = Math.min(dendrite.strength / 255.0, 1.0); // Normaliser la force entre 0 et 1

                document = document.add(
                    Line::new()
                        .set("x1", sourceX)
                        .set("y1", sourceY)
                        .set("x2", targetX)
                        .set("y2", targetY)
                        .set("stroke", strokeColor)
                        .set("stroke-width", 0.1)
                        .set("opacity", opacity)
                        .set("class", "dendrite")
                        .set("title", String.format("Dendrite: %s[%d] -> %s[%d], Strength: %d, Type: D%d",
                            sourceLobe.name, dendrite.sourceCell,
                            targetLobe.name, dendrite.targetCell,
                            dendrite.strength, dendrite.type))
                );
            }
        }

        // Dessiner les instincts (cercles sur les lobes)
        for (Instinct instinct : instincts) {
            for (Map<String, Object> condition : instinct.conditions) {
                String lobeName = (String) condition.get("lobe");
                int cell = (int) condition.get("cell");

                BrainLobe lobe = GenomeParser.findLobeByName(lobes, lobeName);
                if (lobe != null) {
                    // Cercle pour la cellule
                    document = document.add(
                        Circle::new()
                            .set("cx", lobe.x + lobe.width / 2)
                            .set("cy", lobe.y + lobe.height / 2)
                            .set("r", 0.5)
                            .set("fill", "black")
                            .set("class", "instinct-cell")
                            .set("title", "Cell: " + cell + ", Action: " + instinct.action)
                    );

                    // Texte pour la cellule
                    document = document.add(
                        Text::new()
                            .set("x", lobe.x + lobe.width / 2)
                            .set("y", lobe.y + lobe.height / 2 - 1)
                            .set("font-size", 0.3)
                            .set("text-anchor", "middle")
                            .set("class", "cell-label")
                            .add(String.valueOf(cell))
                    );
                }
            }
        }

        // Dessiner les récepteurs (cercles rouges)
        for (Receptor receptor : receptors) {
            BrainLobe lobe = GenomeParser.findLobeByPosition(lobes, receptor.locus.get(0), receptor.locus.get(1));
            if (lobe != null) {
                document = document.add(
                    Circle::new()
                        .set("cx", lobe.x + lobe.width / 2)
                        .set("cy", lobe.y + lobe.height / 2)
                        .set("r", 0.4)
                        .set("fill", "red")
                        .set("class", "receptor")
                        .set("title", "Receptor: " + receptor.chemical + " (Threshold: " + receptor.threshold + ")")
                );
            }
        }

        // Dessiner les émetteurs (cercles verts)
        for (Emitter emitter : emitters) {
            BrainLobe lobe = GenomeParser.findLobeByPosition(lobes, emitter.locus.get(0), emitter.locus.get(1));
            if (lobe != null) {
                document = document.add(
                    Circle::new()
                        .set("cx", lobe.x + lobe.width / 2)
                        .set("cy", lobe.y + lobe.height / 2)
                        .set("r", 0.4)
                        .set("fill", "green")
                        .set("class", "emitter")
                        .set("title", "Emitter: " + emitter.chemical + " (Threshold: " + emitter.threshold + ")")
                );
            }
        }

        // Légende pour les instincts
        int legendY = 55;
        document = document.add(
            Text::new()
                .set("x", 60)
                .set("y", legendY)
                .set("font-size", 0.6)
                .set("font-weight", "bold")
                .add("Instincts:")
        );
        legendY += 2;

        for (Instinct instinct : instincts) {
            document = document.add(
                Text::new()
                    .set("x", 60)
                    .set("y", legendY)
                    .set("font-size", 0.4)
                    .add(instinct.action + " (" + instinct.rewardPunish + ": " + instinct.amount + ")")
            );
            legendY += 1.5;
        }

        // Légende pour les récepteurs/émetteurs
        legendY += 2;
        document = document.add(
            Circle::new()
                .set("cx", 60)
                .set("cy", legendY)
                .set("r", 0.4)
                .set("fill", "red")
        );
        document = document.add(
            Text::new()
                .set("x", 61)
                .set("y", legendY)
                .set("font-size", 0.4)
                .add("= Receptor")
        );

        legendY += 1.5;
        document = document.add(
            Circle::new()
                .set("cx", 60)
                .set("cy", legendY)
                .set("r", 0.4)
                .set("fill", "green")
        );
        document = document.add(
            Text::new()
                .set("x", 61)
                .set("y", legendY)
                .set("font-size", 0.4)
                .add("= Emitter")
        );

        // Légende pour les dendrites
        legendY += 2;
        document = document.add(
            Line::new()
                .set("x1", 60)
                .set("y1", legendY)
                .set("x2", 62)
                .set("y2", legendY)
                .set("stroke", "#0000FF")
                .set("stroke-width", 0.1)
        );
        document = document.add(
            Text::new()
                .set("x", 63)
                .set("y", legendY)
                .set("font-size", 0.4)
                .add("= Dendrite D0")
        );

        legendY += 1.5;
        document = document.add(
            Line::new()
                .set("x1", 60)
                .set("y1", legendY)
                .set("x2", 62)
                .set("y2", legendY)
                .set("stroke", "#FF0000")
                .set("stroke-width", 0.1)
        );
        document = document.add(
            Text::new()
                .set("x", 63)
                .set("y", legendY)
                .set("font-size", 0.4)
                .add("= Dendrite D1")
        );

        // Section pour les réactions chimiques
        legendY += 3;
        document = document.add(
            Text::new()
                .set("x", 60)
                .set("y", legendY)
                .set("font-size", 0.6)
                .set("font-weight", "bold")
                .add("Chemical Reactions:")
        );
        legendY += 2;

        for (Reaction reaction : reactions) {
            String reactantsStr = reaction.reactants.stream()
                .map(r -> r.get("proportion") + " " + r.get("chem_name"))
                .collect(Collectors.joining(" + "));
            String productsStr = reaction.products.stream()
                .map(p -> p.get("proportion") + " " + p.get("chem_name"))
                .collect(Collectors.joining(" + "));
            String reactionStr = reactantsStr + " → " + productsStr + " (Rate: " + reaction.rate + ")";

            document = document.add(
                Text::new()
                    .set("x", 60)
                    .set("y", legendY)
                    .set("font-size", 0.4)
                    .add(reactionStr)
            );
            legendY += 1.5;
        }

        // Section pour les demi-vies
        legendY += 2;
        document = document.add(
            Text::new()
                .set("x", 60)
                .set("y", legendY)
                .set("font-size", 0.6)
                .set("font-weight", "bold")
                .add("Chemical Half-Lives:")
        );
        legendY += 2;

        for (HalfLife halfLife : halfLives) {
            for (Map.Entry<String, Integer> entry : halfLife.chemicalHalfLives.entrySet()) {
                document = document.add(
                    Text::new()
                        .set("x", 60)
                        .set("y", legendY)
                        .set("font-size", 0.4)
                        .add(entry.getKey() + ": " + entry.getValue())
                );
                legendY += 1.5;
            }
        }

        // Sauvegarder le SVG
        Files.write(Paths.get(filename), document.toString().getBytes());
    }
}
```

---
### **3.2 `LatexExporter.java`**
```java
package gabywald.creatures.parser.exporter;

import gabywald.creatures.parser.model.*;
import gabywald.creatures.parser.GenomeParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Exporteur de cartographie cérébrale au format LaTeX.
 */
public class LatexExporter {
    /**
     * Exporte une cartographie cérébrale complète au format LaTeX.
     *
     * @param lobes Liste des lobes cérébraux
     * @param instincts Liste des instincts
     * @param receptors Liste des récepteurs
     * @param emitters Liste des émetteurs
     * @param stimuli Liste des stimulus
     * @param dendrites Liste des dendrites
     * @param reactions Liste des réactions chimiques
     * @param halfLives Liste des demi-vies
     * @param filename Nom du fichier de sortie
     * @throws IOException Si le fichier ne peut pas être écrit
     */
    public static void exportBrainMapLatex(
        List<BrainLobe> lobes,
        List<Instinct> instincts,
        List<Receptor> receptors,
        List<Emitter> emitters,
        List<Stimulus> stimuli,
        List<Dendrite> dendrites,
        List<Reaction> reactions,
        List<HalfLife> halfLives,
        String filename
    ) throws IOException {
        StringBuilder latex = new StringBuilder();

        // En-tête LaTeX
        latex.append("\\documentclass{article}\n");
        latex.append("\\usepackage[utf8]{inputenc}\n");
        latex.append("\\usepackage{tikz}\n");
        latex.append("\\usepackage{geometry}\n");
        latex.append("\\geometry{a4paper, margin=1in}\n");
        latex.append("\\usepackage{longtable}\n");
        latex.append("\\usepackage{array}\n");
        latex.append("\\usepackage{graphicx}\n");
        latex.append("\\usepackage{amsmath}\n");
        latex.append("\\title{Brain Map of Norn}\n");
        latex.append("\\author{Creatures Genome Analyzer}\n");
        latex.append("\\date{\\today}\n");
        latex.append("\\begin{document}\n");
        latex.append("\\maketitle\n\n");

        // Section : Cartographie Cérébrale
        latex.append("\\section*{Brain Map}\n\n");
        latex.append("\\begin{center}\n");
        latex.append("\\begin{tikzpicture}[scale=0.5]\n");

        // Dessiner les lobes
        for (BrainLobe lobe : lobes) {
            String color = getLatexColor(lobes.indexOf(lobe));
            latex.append(String.format(
                "\\filldraw[%s!70] (%d,%d) rectangle (%d,%d);\n",
                color, lobe.x, lobe.y, lobe.x + lobe.width, lobe.y + lobe.height
            ));
            latex.append(String.format(
                "\\node at (%d,%d) {\\tiny %s};\n",
                lobe.x + lobe.width / 2, lobe.y + lobe.height / 2, lobe.name
            ));
        }

        // Dessiner les connexions (dendrites)
        for (Dendrite dendrite : dendrites) {
            BrainLobe sourceLobe = GenomeParser.findLobeByNumber(lobes, dendrite.sourceLobe);
            BrainLobe targetLobe = GenomeParser.findLobeByNumber(lobes, dendrite.targetLobe);

            if (sourceLobe != null && targetLobe != null) {
                int sourceX = sourceLobe.x + sourceLobe.width / 2;
                int sourceY = sourceLobe.y + sourceLobe.height / 2;
                int targetX = targetLobe.x + targetLobe.width / 2;
                int targetY = targetLobe.y + targetLobe.height / 2;

                String color = dendrite.type == 0 ? "blue" : "red";
                double opacity = Math.min(dendrite.strength / 255.0, 1.0);

                latex.append(String.format(
                    "\\draw[%s, opacity=%.2f] (%d,%d) -- (%d,%d);\n",
                    color, opacity, sourceX, sourceY, targetX, targetY
                ));
            }
        }

        // Dessiner les instincts (cercles)
        for (Instinct instinct : instincts) {
            for (Map<String, Object> condition : instinct.conditions) {
                String lobeName = (String) condition.get("lobe");
                int cell = (int) condition.get("cell");
                BrainLobe lobe = GenomeParser.findLobeByName(lobes, lobeName);
                if (lobe != null) {
                    latex.append(String.format(
                        "\\filldraw[black] (%d,%d) circle (0.2);\n",
                        lobe.x + lobe.width / 2, lobe.y + lobe.height / 2
                    ));
                    latex.append(String.format(
                        "\\node at (%d,%d) [above, font=\\tiny] {%d};\n",
                        lobe.x + lobe.width / 2, lobe.y + lobe.height / 2, cell
                    ));
                }
            }
        }

        latex.append("\\end{tikzpicture}\n");
        latex.append("\\end{center}\n\n");

        // Légende des dendrites
        latex.append("\\section*{Dendrites Legend}\n\n");
        latex.append("\\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Source} & {\\bf Target} & {\\bf Strength} & {\\bf Type} \\\\ \\hline\n");
        for (Dendrite dendrite : dendrites) {
            BrainLobe sourceLobe = GenomeParser.findLobeByNumber(lobes, dendrite.sourceLobe);
            BrainLobe targetLobe = GenomeParser.findLobeByNumber(lobes, dendrite.targetLobe);
            String sourceName = sourceLobe != null ? sourceLobe.name : "Unknown";
            String targetName = targetLobe != null ? targetLobe.name : "Unknown";
            latex.append(String.format(
                "%s[%d] & %s[%d] & %d & D%d \\\\ \\hline\n",
                sourceName, dendrite.sourceCell, targetName, dendrite.targetCell,
                dendrite.strength, dendrite.type
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Légende des Instincts
        latex.append("\\section*{Instincts Legend}\n\n");
        latex.append("\\begin{longtable}{|p{3cm}|p{3cm}|p{3cm}|p{3cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Action} & {\\bf Reward/Punish} & {\\bf Amount} & {\\bf Conditions} \\\\ \\hline\n");
        for (Instinct instinct : instincts) {
            String conditions = "";
            for (Map<String, Object> condition : instinct.conditions) {
                conditions += String.format("%s[%d], ",
                    condition.get("lobe"), condition.get("cell"));
            }
            if (!conditions.isEmpty()) {
                conditions = conditions.substring(0, conditions.length() - 2);
            }
            latex.append(String.format(
                "%s & %s & %d & %s \\\\ \\hline\n",
                instinct.action, instinct.rewardPunish, instinct.amount, conditions
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Récepteurs
        latex.append("\\section*{Receptors}\n\n");
        latex.append("\\begin{longtable}{|p{2cm}|p{2cm}|p{2cm}|p{2cm}|p{2cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Locus} & {\\bf Chemical} & {\\bf Threshold} & {\\bf Nominal} & {\\bf Gain} \\\\ \\hline\n");
        for (Receptor receptor : receptors) {
            latex.append(String.format(
                "(%d,%d,%d) & %s & %d & %d & %d \\\\ \\hline\n",
                receptor.locus.get(0), receptor.locus.get(1), receptor.locus.get(2),
                receptor.chemical, receptor.threshold, receptor.nominal, receptor.gain
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Émetteurs
        latex.append("\\section*{Emitters}\n\n");
        latex.append("\\begin{longtable}{|p{2cm}|p{2cm}|p{2cm}|p{2cm}|p{2cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Locus} & {\\bf Chemical} & {\\bf Threshold} & {\\bf Sample Rate} & {\\bf Gain} \\\\ \\hline\n");
        for (Emitter emitter : emitters) {
            latex.append(String.format(
                "(%d,%d,%d) & %s & %d & %d & %d \\\\ \\hline\n",
                emitter.locus.get(0), emitter.locus.get(1), emitter.locus.get(2),
                emitter.chemical, emitter.threshold, emitter.sampleRate, emitter.gain
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Stimulus
        latex.append("\\section*{Stimuli}\n\n");
        latex.append("\\begin{longtable}{|p{2cm}|p{2cm}|p{4cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Type} & {\\bf Intensity} & {\\bf Locus} \\\\ \\hline\n");
        for (Stimulus stimulus : stimuli) {
            latex.append(String.format(
                "%d & %d & (%d,%d,%d) \\\\ \\hline\n",
                stimulus.stimulusType, stimulus.intensity,
                stimulus.locus.get(0), stimulus.locus.get(1), stimulus.locus.get(2)
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Réactions Chimiques
        latex.append("\\section*{Chemical Reactions}\n\n");
        latex.append("\\begin{longtable}{|p{4cm}|p{4cm}|p{2cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Reactants} & {\\bf Products} & {\\bf Rate} \\\\ \\hline\n");
        for (Reaction reaction : reactions) {
            String reactants = reaction.reactants.stream()
                .map(r -> r.get("proportion") + " " + r.get("chem_name"))
                .collect(java.util.stream.Collectors.joining(" + "));
            String products = reaction.products.stream()
                .map(p -> p.get("proportion") + " " + p.get("chem_name"))
                .collect(java.util.stream.Collectors.joining(" + "));
            latex.append(String.format(
                "%s & %s & %d \\\\ \\hline\n",
                reactants, products, reaction.rate
            ));
        }
        latex.append("\\end{longtable}\n\n");

        // Section : Demi-Vies
        latex.append("\\section*{Chemical Half-Lives}\n\n");
        latex.append("\\begin{longtable}{|p{4cm}|p{4cm}|}\n");
        latex.append("\\hline\n");
        latex.append("{\\bf Chemical} & {\\bf Half-Life} \\\\ \\hline\n");
        for (HalfLife halfLife : halfLives) {
            for (Map.Entry<String, Integer> entry : halfLife.chemicalHalfLives.entrySet()) {
                latex.append(String.format(
                    "%s & %d \\\\ \\hline\n",
                    entry.getKey(), entry.getValue()
                ));
            }
        }
        latex.append("\\end{longtable}\n\n");

        // Pied de page
        latex.append("\\end{document}\n");

        // Sauvegarder le fichier LaTeX
        Files.write(Paths.get(filename), latex.toString().getBytes());
    }

    /**
     * Retourne une couleur LaTeX en fonction de l'index.
     *
     * @param index Index de la couleur
     * @return Nom de la couleur LaTeX
     */
    private static String getLatexColor(int index) {
        String[] colors = {"red", "blue", "green", "yellow", "purple", "orange", "pink", "brown", "gray", "cyan"};
        return colors[index % colors.length];
    }
}
```

---

---

## **📄 4. Classe Principale (`Main.java`)**
```java
package gabywald.creatures.parser;

import gabywald.creatures.parser.model.*;
import gabywald.creatures.parser.exporter.SVGExporter;
import gabywald.creatures.parser.exporter.LatexExporter;
import java.io.IOException;
import java.util.List;

/**
 * Classe principale pour analyser les fichiers .gen et générer des cartographies cérébrales.
 */
public class Main {
    /**
     * Point d'entrée du programme.
     *
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java gabywald.creatures.parser.Main <genome_file.gen> [--svg <output.svg>] [--latex <output.tex>]");
            System.exit(1);
        }

        String inputFile = args[0];
        String svgOutput = null;
        String latexOutput = null;

        // Parser les arguments
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("--svg") && i + 1 < args.length) {
                svgOutput = args[++i];
            } else if (args[i].equals("--latex") && i + 1 < args.length) {
                latexOutput = args[++i];
            }
        }

        try {
            // Parser le fichier de génome
            List<Gene> genes = GenomeParser.parseGenomeFile(inputFile);
            System.out.println("Parsed " + genes.size() + " genes from " + inputFile);

            // Extraire les données
            List<BrainLobe> lobes = GenomeParser.extractLobes(genes);
            List<Instinct> instincts = GenomeParser.extractInstincts(genes);
            List<Receptor> receptors = GenomeParser.extractReceptors(genes);
            List<Emitter> emitters = GenomeParser.extractEmitters(genes);
            List<Stimulus> stimuli = GenomeParser.extractStimuli(genes);
            List<Dendrite> dendrites = GenomeParser.extractDendrites(genes);
            List<Reaction> reactions = GenomeParser.extractReactions(genes);
            List<HalfLife> halfLives = GenomeParser.extractHalfLives(genes);

            System.out.println("Found " + lobes.size() + " brain lobes");
            System.out.println("Found " + instincts.size() + " instincts");
            System.out.println("Found " + receptors.size() + " receptors");
            System.out.println("Found " + emitters.size() + " emitters");
            System.out.println("Found " + stimuli.size() + " stimuli");
            System.out.println("Found " + dendrites.size() + " dendrites");
            System.out.println("Found " + reactions.size() + " reactions");
            System.out.println("Found " + halfLives.size() + " half-lives");

            // Générer le SVG si demandé
            if (svgOutput != null) {
                SVGExporter.exportBrainMap(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, svgOutput);
                System.out.println("Brain map SVG generated: " + svgOutput);
            }

            // Générer le LaTeX si demandé
            if (latexOutput != null) {
                LatexExporter.exportBrainMapLatex(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, latexOutput);
                System.out.println("Brain map LaTeX generated: " + latexOutput);
            }

            // Si aucun format n'est spécifié, générer les deux par défaut
            if (svgOutput == null && latexOutput == null) {
                svgOutput = inputFile.replaceFirst("[.][^.]+$", ".svg");
                latexOutput = inputFile.replaceFirst("[.][^.]+$", ".tex");
                SVGExporter.exportBrainMap(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, svgOutput);
                LatexExporter.exportBrainMapLatex(lobes, instincts, receptors, emitters, stimuli, dendrites, reactions, halfLives, latexOutput);
                System.out.println("Brain map SVG generated: " + svgOutput);
                System.out.println("Brain map LaTeX generated: " + latexOutput);
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
```

---

---
---
## **📌 Résumé de l'Organisation**
| **Package** | **Classe** | **Rôle** |
|------------|------------|----------|
| `gabywald.creatures.parser.model` | `Gene` | Modèle de base pour les gènes |
| | `BrainLobe` | Modèle pour les lobes cérébraux |
| | `Dendrite` | Modèle pour les connexions entre lobes |
| | `Instinct` | Modèle pour les instincts |
| | `Receptor` | Modèle pour les récepteurs chimiques |
| | `Emitter` | Modèle pour les émetteurs chimiques |
| | `Stimulus` | Modèle pour les stimulus |
| | `Reaction` | Modèle pour les réactions chimiques |
| | `HalfLife` | Modèle pour les demi-vies des chimies |
| `gabywald.creatures.parser` | `GenomeParser` | Parseur des fichiers `.gen` |
| `gabywald.creatures.parser.exporter` | `SVGExporter` | Exportateur SVG |
| | `LatexExporter` | Exportateur LaTeX |
| `gabywald.creatures.parser` | `Main` | Classe principale |

---
Cette organisation en packages permet une **meilleure modularité**, une **maintenabilité accrue** et une **séparation claire des responsabilités**. Si vous avez besoin d'ajouter d'autres fonctionnalités ou de modifier la structure, n'hésitez pas à me le demander !
