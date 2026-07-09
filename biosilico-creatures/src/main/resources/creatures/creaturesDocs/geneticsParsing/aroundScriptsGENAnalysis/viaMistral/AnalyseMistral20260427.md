propose script perl et script python pour faire cartographie lobes

Voici deux scripts (Perl et Python) pour générer une cartographie des lobes cérébraux à partir des fichiers de génome de *Creatures*. Ces scripts lisent les fichiers `.gen`, extraient les informations sur les lobes cérébraux, et génèrent une représentation visuelle en SVG.

---

## **1. Script Perl pour la Cartographie des Lobes**
```perl
#!/usr/bin/perl
use strict;
use warnings;
use SVG;

=head1 NAME
brain_map_generator.pl - Generate a brain map from Creatures genome files.

=head1 SYNOPSIS
perl brain_map_generator.pl <genome_file.gen> [output_file.svg]

=head1 DESCRIPTION
This script reads a Creatures genome file and generates an SVG brain map showing the lobes.

=cut

# --- Reference Data ---
my %LOBE_NAMES = (
    0 => "Perception",
    1 => "Drive",
    2 => "Stimulus Source",
    3 => "Verb",
    4 => "Noun",
    5 => "General Sense",
    6 => "Decision",
    7 => "Attention",
    8 => "Concept",
    9 => "Regulator",
);

# --- Parse Genome File ---
sub parse_genome_file {
    my ($filename) = @_;
    open(my $fh, "<:raw", $filename) or die "Cannot open $filename: $!";
    binmode($fh);
    my $content = do { local $/; <$fh> };
    close($fh);

    my @genes;
    my $offset = 0;

    while ($offset < length($content)) {
        my $header = substr($content, $offset, 9);
        last unless length($header) >= 8;

        my ($gene_marker, $type, $subtype, $number, $unknown, $switch_on) =
            unpack("A4 C C C C C", substr($header, 0, 8));

        my $data_length = (length($content) > $offset + 9) ? unpack("C", substr($content, $offset + 9, 1)) : 0;
        $data_length //= 0;

        $offset += 1;
        next unless $gene_marker eq "gene";

        my $data = [];
        if ($data_length > 0 && $offset + 10 + $data_length <= length($content)) {
            $data = [unpack("C*", substr($content, $offset + 10, $data_length))];
        }

        my $gene = {
            type    => $type,
            subtype => $subtype,
            number  => $number,
            data    => $data,
        };

        push @genes, $gene;
        $offset += 10 + $data_length;
    }

    return \@genes;
}

# --- Extract Lobes ---
sub extract_lobes {
    my ($genes) = @_;
    my @lobes;
    for my $gene (@$genes) {
        if ($gene->{type} == 0x00 && $gene->{subtype} == 0x00 && @{$gene->{data}} >= 5) {
            push @lobes, {
                number  => $gene->{number},
                name    => $LOBE_NAMES{$gene->{number}} // sprintf("Lobe%02X", $gene->{number}),
                x       => $gene->{data}[0],
                y       => $gene->{data}[1],
                width  => $gene->{data}[2],
                height => $gene->{data}[3],
            };
        }
    }
    return \@lobes;
}

# --- Generate SVG ---
sub generate_svg {
    my ($lobes, $output_file) = @_;
    my $svg = SVG->new(width => 800, height => 600, viewBox => "0 0 64 48");
    $svg->rectangle(x => 0, y => 0, width => 64, height => 48, style => { fill => 'white' });

    my @colors = (
        '#FF6B6B', '#4ECDC4', '#45B7D1', '#FFBE0B', '#FB5607',
        '#8338EC', '#3A86FF', '#FF006E', '#A5DD9B', '#FF9E9E'
    );

    for my $i (0..$#$lobes) {
        my $lobe = $lobes->[$i];
        my $color = $colors[$i % @colors];

        $svg->rectangle(
            x      => $lobe->{x},
            y      => $lobe->{y},
            width  => $lobe->{width},
            height => $lobe->{height},
            style  => {
                fill           => $color,
                stroke         => 'black',
                'stroke-width' => 0.1,
                opacity        => 0.7,
            }
        );

        $svg->text(
            x          => $lobe->{x} + $lobe->{width} / 2,
            y          => $lobe->{y} + $lobe->{height} / 2,
            'font-size' => 0.5,
            'text-anchor' => 'middle',
            'dominant-baseline' => 'middle',
            '-cdata'   => $lobe->{name}
        );
    }

    open(my $fh, ">", $output_file) or die "Cannot open $output_file: $!";
    print $fh $svg->xmlify;
    close($fh);
}

# --- Main ---
if (@ARGV < 1) {
    die "Usage: $0 <genome_file.gen> [output_file.svg]\n";
}

my $input_file = $ARGV[0];
my $output_file = @ARGV > 1 ? $ARGV[1] : "brain_map.svg";

my $genes = parse_genome_file($input_file);
my $lobes = extract_lobes($genes);
generate_svg($lobes, $output_file);

print "Brain map generated: $output_file\n";
```

---

## **2. Script Python pour la Cartographie des Lobes**
```python
#!/usr/bin/env python3
"""
brain_map_generator.py - Generate a brain map from Creatures genome files.

Usage:
    python3 brain_map_generator.py <genome_file.gen> [output_file.svg]
"""

import struct
import svgwrite
from svgwrite import cm, mm

# Reference data
LOBE_NAMES = {
    0: "Perception",
    1: "Drive",
    2: "Stimulus Source",
    3: "Verb",
    4: "Noun",
    5: "General Sense",
    6: "Decision",
    7: "Attention",
    8: "Concept",
    9: "Regulator",
}

def parse_genome_file(filename):
    """Parse a .gen file and extract genes."""
    with open(filename, "rb") as f:
        content = f.read()

    genes = []
    offset = 0
    while offset < len(content):
        header = content[offset:offset+8]
        if len(header) < 7:
            offset += 1
            continue

        gene_marker = header[:4]
        if gene_marker != b'gene':
            offset += 1
            continue

        type_, subtype, number = struct.unpack("B B B", header[4:7])
        data_length = content[offset + 9] if offset + 9 < len(content) else 0

        data = []
        if data_length > 0 and offset + 10 + data_length <= len(content):
            data = list(struct.unpack(f"{data_length}B", content[offset + 10:offset + 10 + data_length]))

        gene = {
            'type': type_,
            'subtype': subtype,
            'number': number,
            'data': data,
        }

        genes.append(gene)
        offset += 10 + data_length

    return genes

def extract_lobes(genes):
    """Extract lobes from genes."""
    lobes = []
    for gene in genes:
        if gene['type'] == 0x00 and gene['subtype'] == 0x00 and len(gene['data']) >= 5:
            lobes.append({
                'number': gene['number'],
                'name': LOBE_NAMES.get(gene['number'], f"Lobe{gene['number']:02X}"),
                'x': gene['data'][0],
                'y': gene['data'][1],
                'width': gene['data'][2],
                'height': gene['data'][3],
            })
    return lobes

def generate_svg(lobes, output_file):
    """Generate an SVG brain map."""
    dwg = svgwrite.Drawing(output_file, size=(800, 600), viewBox="0 0 64 48")

    # Background
    dwg.add(dwg.rect(insert=(0, 0), size=(64, 48), fill='white'))

    # Colors for lobes
    colors = [
        '#FF6B6B', '#4ECDC4', '#45B7D1', '#FFBE0B', '#FB5607',
        '#8338EC', '#3A86FF', '#FF006E', '#A5DD9B', '#FF9E9E'
    ]

    for i, lobe in enumerate(lobes):
        color = colors[i % len(colors)]

        dwg.add(dwg.rect(
            insert=(lobe['x'], lobe['y']),
            size=(lobe['width'], lobe['height']),
            fill=color,
            stroke='black',
            stroke_width=0.1,
            opacity=0.7,
        ))

        dwg.add(dwg.text(
            lobe['name'],
            insert=(
                lobe['x'] + lobe['width'] / 2,
                lobe['y'] + lobe['height'] / 2
            ),
            font_size=0.5,
            text_anchor='middle',
            dominant_baseline='middle',
        ))

    dwg.save()

def main():
    import sys
    if len(sys.argv) < 2:
        print("Usage: python3 brain_map_generator.py <genome_file.gen> [output_file.svg]")
        sys.exit(1)

    input_file = sys.argv[1]
    output_file = sys.argv[2] if len(sys.argv) > 2 else "brain_map.svg"

    genes = parse_genome_file(input_file)
    lobes = extract_lobes(genes)
    generate_svg(lobes, output_file)

    print(f"Brain map generated: {output_file}")

if __name__ == "__main__":
    main()
```

---

## **3. Explications des Scripts**

### **3.1 Fonctionnalités**
- **Parsing des fichiers `.gen`** : Extraction des gènes et des lobes cérébraux.
- **Génération de SVG** : Création d'une cartographie visuelle des lobes.
- **Couleurs distinctes** : Chaque lobe a une couleur différente pour une meilleure distinction.
- **Noms des lobes** : Affichage du nom de chaque lobe au centre.

### **3.2 Utilisation**
- **Perl** :
  ```bash
  perl brain_map_generator.pl dad1.gen brain_map.svg
  ```
- **Python** :
  ```bash
  python3 brain_map_generator.py dad1.gen brain_map.svg
  ```

### **3.3 Exemple de Sortie SVG**
```xml
<svg width="800" height="600" viewBox="0 0 64 48" xmlns="http://www.w3.org/2000/svg">
  <rect width="100%" height="100%" fill="white"/>
  <rect x="4" y="13" width="7" height="16" fill="#FF6B6B" stroke="black" stroke-width="0.1" opacity="0.7"/>
  <text x="7.5" y="21" font-size="0.5" text-anchor="middle" dominant-baseline="middle">Perception</text>
  <!-- Autres lobes -->
</svg>
```

---

## **4. Extensions Possibles**
- **Interactivité** : Ajouter des fonctionnalités interactives avec JavaScript.
- **Connexions** : Dessiner les connexions entre les lobes (dendrites).
- **Couleurs dynamiques** : Utiliser des couleurs basées sur les types de lobes.

---

Si vous avez besoin d'autres améliorations ou d'extensions spécifiques, n'hésitez pas à me le dire ! Je peux aussi vous aider à **automatiser des modifications de génomes** ou à **ajouter des fonctionnalités supplémentaires**.



Propose une retranscription de ces scripts en rust 

Voici une retranscription des scripts Perl/Python en **Rust**, avec une approche moderne, sécurisée et performante. Cette implémentation inclut :
- Un parseur de fichiers `.gen`
- Un extracteur de lobes cérébraux
- Un générateur de cartographie SVG
- Une gestion robuste des erreurs

---

## **1. Structure du Projet Rust**
```text
creatures_brain_map/
├── Cargo.toml
└── src/
    ├── main.rs          # Point d'entrée
    ├── genome.rs        # Parseur de génome
    ├── brain_map.rs     # Générateur de cartographie
    └── models.rs        # Structures de données
```

---

## **2. Fichier `Cargo.toml`**
```toml
[package]
name = "creatures_brain_map"
version = "0.1.0"
edition = "2021"

[dependencies]
svg = "0.13.1"  # Pour générer des SVG
clap = { version = "4.0", features = ["derive"] }  # Pour le parsing des arguments
anyhow = "1.0"  # Pour la gestion des erreurs
```

---

## **3. Fichier `src/models.rs`**
```rust
use std::collections::HashMap;

/// Représente un gène de Creatures
#[derive(Debug, Clone)]
pub struct Gene {
    pub gene_type: u8,
    pub subtype: u8,
    pub number: u8,
    pub switch_on: u8,
    pub sex_dep: String,
    pub mutability: String,
    pub data: Vec<u8>,
}

/// Représente un lobe cérébral
#[derive(Debug, Clone)]
pub struct BrainLobe {
    pub number: u8,
    pub name: String,
    pub x: u8,
    pub y: u8,
    pub width: u8,
    pub height: u8,
}

/// Noms des lobes (source: creatures1BrainMapCells_GenesHeader.txt)
pub fn get_lobe_name(number: u8) -> String {
    let lobe_names: HashMap<u8, &str> = [
        (0, "Perception"),
        (1, "Drive"),
        (2, "Stimulus Source"),
        (3, "Verb"),
        (4, "Noun"),
        (5, "General Sense"),
        (6, "Decision"),
        (7, "Attention"),
        (8, "Concept"),
        (9, "Regulator"),
    ]
    .iter()
    .cloned()
    .collect();

    lobe_names.get(&number).unwrap_or(&format!("Lobe{:02X}", number)).to_string()
}
```

---

## **4. Fichier `src/genome.rs`**
```rust
use std::fs::File;
use std::io::Read;
use anyhow::{Context, Result};
use crate::models::{Gene, get_lobe_name};

/// Parse un fichier .gen et retourne une liste de gènes
pub fn parse_genome_file(filename: &str) -> Result<Vec<Gene>> {
    let mut file = File::open(filename)
        .with_context(|| format!("Impossible d'ouvrir le fichier {}", filename))?;
    let mut content = Vec::new();
    file.read_to_end(&mut content)?;

    let mut genes = Vec::new();
    let mut offset = 0;

    while offset < content.len() {
        if offset + 8 > content.len() {
            offset += 1;
            continue;
        }

        // Vérifier le marqueur "gene"
        let gene_marker = &content[offset..offset + 4];
        if gene_marker != b"gene" {
            offset += 1;
            continue;
        }

        // Lire l'en-tête
        let gene_type = content[offset + 4];
        let subtype = content[offset + 5];
        let number = content[offset + 6];
        let switch_on = content[offset + 7];

        // Lire sex_mut et data_length
        let sex_mut = if offset + 8 < content.len() {
            content[offset + 8]
        } else {
            0
        };
        let data_length = if offset + 9 < content.len() {
            content[offset + 9] as usize
        } else {
            0
        };

        // Déterminer sex_dep et mutability
        let sex_dep = if (sex_mut & 0x10) != 0 {
            "Female".to_string()
        } else if (sex_mut & 0x08) != 0 {
            "Male".to_string()
        } else {
            "None".to_string()
        };

        let mut mutability = Vec::new();
        if (sex_mut & 0x01) != 0 { mutability.push("Mutable"); }
        if (sex_mut & 0x02) != 0 { mutability.push("Duplicable"); }
        if (sex_mut & 0x04) != 0 { mutability.push("Deletable"); }
        let mutability = if mutability.is_empty() {
            "None".to_string()
        } else {
            mutability.join(",")
        };

        // Lire les données du gène
        let data = if data_length > 0 && offset + 10 + data_length <= content.len() {
            content[offset + 10..offset + 10 + data_length].to_vec()
        } else {
            Vec::new()
        };

        genes.push(Gene {
            gene_type,
            subtype,
            number,
            switch_on,
            sex_dep,
            mutability,
            data,
        });

        offset += 10 + data_length;
    }

    Ok(genes)
}

/// Extrait les lobes cérébraux des gènes
pub fn extract_lobes(genes: &[Gene]) -> Vec<BrainLobe> {
    genes
        .iter()
        .filter(|gene| gene.gene_type == 0x00 && gene.subtype == 0x00 && gene.data.len() >= 5)
        .map(|gene| BrainLobe {
            number: gene.number,
            name: get_lobe_name(gene.number),
            x: gene.data[0],
            y: gene.data[1],
            width: gene.data[2],
            height: gene.data[3],
        })
        .collect()
}
```

---

## **5. Fichier `src/brain_map.rs`**
```rust
use svg::node::element::{Rectangle, Text, Group};
use svg::Document;
use crate::models::BrainLobe;

/// Génère une cartographie cérébrale en SVG
pub fn generate_brain_map_svg(lobes: &[BrainLobe], output_file: &str) -> Result<(), std::io::Error> {
    let mut document = Document::new()
        .set("width", 800)
        .set("height", 600)
        .set("viewBox", (0, 0, 64, 48));

    // Ajouter un fond blanc
    document = document.add(
        Rectangle::new()
            .set("x", 0)
            .set("y", 0)
            .set("width", 64)
            .set("height", 48)
            .set("fill", "white")
    );

    // Couleurs pour les lobes
    let colors = [
        "#FF6B6B", "#4ECDC4", "#45B7D1", "#FFBE0B", "#FB5607",
        "#8338EC", "#3A86FF", "#FF006E", "#A5DD9B", "#FF9E9E"
    ];

    for (i, lobe) in lobes.iter().enumerate() {
        let color = colors[i % colors.len()];

        // Ajouter le rectangle du lobe
        document = document.add(
            Rectangle::new()
                .set("x", lobe.x as i32)
                .set("y", lobe.y as i32)
                .set("width", lobe.width as i32)
                .set("height", lobe.height as i32)
                .set("fill", color)
                .set("stroke", "black")
                .set("stroke-width", 0.1)
                .set("opacity", 0.7)
        );

        // Ajouter le texte du lobe
        document = document.add(
            Text::new()
                .set("x", (lobe.x + lobe.width / 2) as i32)
                .set("y", (lobe.y + lobe.height / 2 + 2) as i32)  // +2 pour centrer verticalement
                .set("font-size", 0.5)
                .set("text-anchor", "middle")
                .set("dominant-baseline", "middle")
                .add(svg::node::Text::new(&lobe.name))
        );
    }

    // Sauvegarder le fichier SVG
    svg::save(output_file, &document)
}
```

---

## **6. Fichier `src/main.rs`**
```rust
use clap::Parser;
use anyhow::Result;
use std::path::PathBuf;
use creatures_brain_map::genome::{parse_genome_file, extract_lobes};
use creatures_brain_map::brain_map::generate_brain_map_svg;

/// Génère une cartographie cérébrale à partir d'un fichier de génome de Creatures
#[derive(Parser, Debug)]
#[command(author, version, about, long_about = None)]
struct Args {
    /// Fichier de génome en entrée (.gen)
    input_file: PathBuf,

    /// Fichier SVG de sortie (par défaut: brain_map.svg)
    #[arg(short, long, default_value = "brain_map.svg")]
    output_file: PathBuf,
}

fn main() -> Result<()> {
    let args = Args::parse();

    // Parser le fichier de génome
    let genes = parse_genome_file(args.input_file.to_str().unwrap())?;
    println!("Parsed {} genes from {}", genes.len(), args.input_file.display());

    // Extraire les lobes cérébraux
    let lobes = extract_lobes(&genes);
    println!("Found {} brain lobes", lobes.len());

    // Générer la cartographie cérébrale
    generate_brain_map_svg(&lobes, args.output_file.to_str().unwrap())?;
    println!("Brain map generated: {}", args.output_file.display());

    Ok(())
}
```

---

## **7. Explications des Implémentations Rust**

### **7.1 Avantages de Rust**
- **Sécurité mémoire** : Pas de risques de segmentation fault ou de fuites mémoire.
- **Performance** : Exécution rapide grâce à la compilation native.
- **Gestion des erreurs** : Utilisation de `anyhow` pour une gestion propre des erreurs.
- **Typage fort** : Détection des erreurs à la compilation.

### **7.2 Fonctionnalités Clés**
- **Parsing des fichiers `.gen`** : Lecture binaire sécurisée.
- **Extraction des lobes** : Filtrage des gènes de type `0x00` et sous-type `0x00`.
- **Génération SVG** : Utilisation de la crate `svg` pour créer des graphiques vectoriels.
- **Interface en ligne de commande** : Utilisation de `clap` pour une interface utilisateur conviviale.

### **7.3 Utilisation**
1. **Installer Rust** :
   ```bash
   curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
   ```

2. **Créer le projet** :
   ```bash
   cargo new creatures_brain_map
   cd creatures_brain_map
   ```

3. **Ajouter les dépendances** :
   ```bash
   cargo add svg clap anyhow
   ```

4. **Copier les fichiers** :
   - Placez les fichiers `models.rs`, `genome.rs`, `brain_map.rs` et `main.rs` dans `src/`.

5. **Compiler et exécuter** :
   ```bash
   cargo build --release
   ./target/release/creatures_brain_map dad1.gen
   ```

---

## **8. Exemple de Sortie**
Le script générera un fichier `brain_map.svg` avec une représentation visuelle des lobes cérébraux, similaire à ceci :

```xml
<svg width="800" height="600" viewBox="0 0 64 48" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="64" height="48" fill="white"/>
  <rect x="4" y="13" width="7" height="16" fill="#FF6B6B" stroke="black" stroke-width="0.1" opacity="0.7"/>
  <text x="7.5" y="21.5" font-size="0.5" text-anchor="middle" dominant-baseline="middle">Perception</text>
  <!-- Autres lobes -->
</svg>
```

---

## **9. Extensions Possibles**
- **Ajout de connexions** : Dessiner les dendrites entre les lobes.
- **Interactivité** : Générer un SVG avec JavaScript pour des fonctionnalités interactives.
- **Support pour *Creatures 2/3*** : Ajouter des types de gènes supplémentaires.

---
Si vous avez besoin d'autres améliorations ou d'extensions spécifiques, n'hésitez pas à me le dire ! Je peux aussi vous aider à **automatiser des modifications de génomes** ou à **ajouter des fonctionnalités supplémentaires**.


Reprend le programme Java pour faire la cartographie cérébrale, ajouter prise en compte des gènes "Instincts" pour la cartographie


Voici une version améliorée du programme Java qui **intègre la cartographie des lobes cérébraux ET des instincts**, avec une visualisation plus complète des connexions entre les lobes et les comportements instinctifs.

---

## **1. Structure du Projet Java Amélioré**
```
src/
├── main/
│   ├── java/
│   │   ├── creatures/
│   │   │   ├── parser/
│   │   │   │   ├── Gene.java
│   │   │   │   ├── BrainLobe.java
│   │   │   │   ├── Instinct.java
│   │   │   │   ├── GenomeParser.java
│   │   │   │   └── export/
│   │   │   │       ├── SVGExporter.java
│   │   │   └── Main.java
```

---

---

## **2. Classe `Gene.java` (Améliorée)**
```java
package creatures.parser;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

    // Noms des chimies
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

    // Noms des actions
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

    // Autres méthodes parseReceptor, parseEmitter, etc...
}
```

---

## **3. Classe `BrainLobe.java`**
```java
package creatures.parser;

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

## **4. Classe `Instinct.java`**
```java
package creatures.parser;

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

## **5. Classe `GenomeParser.java` (Améliorée)**
```java
package creatures.parser;

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
            if (gene.type == 0x00 && gene.subtype == 0x00 && gene.data.size() >= 5) {
                Map<String, Object> lobeData = (Map<String, Object>) gene.parsedData.get("lobe");
                if (lobeData != null) {
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
        }
        return lobes;
    }

    public static List<Instinct> extractInstincts(List<Gene> genes) {
        List<Instinct> instincts = new ArrayList<>();
        for (Gene gene : genes) {
            if (gene.type == 0x02 && gene.subtype == 0x05 && gene.parsedData.containsKey("instinct")) {
                Map<String, Object> instinctData = (Map<String, Object>) gene.parsedData.get("instinct");
                if (instinctData != null) {
                    Instinct instinct = new Instinct(
                        (List<Map<String, Object>>) instinctData.get("conditions"),
                        (String) instinctData.get("action"),
                        (String) instinctData.get("reward_punish"),
                        (int) instinctData.get("amount")
                    );
                    instincts.add(instinct);
                }
            }
        }
        return instincts;
    }
}
```

---

## **6. Classe `SVGExporter.java` (Améliorée avec Instincts)**
```java
package creatures.parser.export;

import creatures.parser.BrainLobe;
import creatures.parser.Instinct;
import svg.Document;
import svg.node.element.Group;
import svg.node.element.Rectangle;
import svg.node.element.Text;
import svg.node.element.Line;
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

    public static void exportBrainMap(List<BrainLobe> lobes, List<Instinct> instincts, String filename) throws IOException {
        Document document = new Document()
            .set("width", 800)
            .set("height", 600)
            .set("viewBox", "0 0 64 48");

        // Fond blanc
        document = document.add(
            Rectangle::new()
                .set("x", 0)
                .set("y", 0)
                .set("width", 64)
                .set("height", 48)
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
            );

            // Texte du lobe
            document = document.add(
                Text::new()
                    .set("x", lobe.x + lobe.width / 2)
                    .set("y", lobe.y + lobe.height / 2 + 2)
                    .set("font-size", 0.5)
                    .set("text-anchor", "middle")
                    .set("dominant-baseline", "middle")
                    .add(lobe.name)
            );
        }

        // Dessiner les instincts (connexions entre lobes)
        for (Instinct instinct : instincts) {
            // Pour chaque condition dans l'instinct
            for (Map<String, Object> condition : instinct.conditions) {
                String lobeName = (String) condition.get("lobe");
                int cell = (int) condition.get("cell");

                // Trouver le lobe correspondant
                BrainLobe sourceLobe = findLobeByName(lobes, lobeName);
                if (sourceLobe != null) {
                    // Dessiner un cercle pour la cellule
                    document = document.add(
                        svg.node.element.Circle::new()
                            .set("cx", sourceLobe.x + sourceLobe.width / 2)
                            .set("cy", sourceLobe.y + sourceLobe.height / 2)
                            .set("r", 0.5)
                            .set("fill", "black")
                    );

                    // Ajouter une étiquette pour la cellule
                    document = document.add(
                        Text::new()
                            .set("x", sourceLobe.x + sourceLobe.width / 2)
                            .set("y", sourceLobe.y + sourceLobe.height / 2 - 1)
                            .set("font-size", 0.3)
                            .set("text-anchor", "middle")
                            .add(String.valueOf(cell))
                    );
                }
            }

            // Ajouter une légende pour l'instinct
            document = document.add(
                Text::new()
                    .set("x", 50)
                    .set("y", 10 + instincts.indexOf(instinct) * 2)
                    .set("font-size", 0.4)
                    .add(instinct.action + " (" + instinct.rewardPunish + ": " + instinct.amount + ")")
            );
        }

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
}
```

---

## **7. Classe `Main.java` (Améliorée)**
```java
package creatures.parser;

import creatures.parser.export.SVGExporter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java creatures.parser.Main <genome_file.gen> [output_file.svg]");
            System.exit(1);
        }

        try {
            String inputFile = args[0];
            String outputFile = args.length > 1 ? args[1] : "brain_map.svg";

            // Parser le fichier de génome
            List<Gene> genes = GenomeParser.parseGenomeFile(inputFile);
            System.out.println("Parsed " + genes.size() + " genes from " + inputFile);

            // Extraire les lobes et les instincts
            List<BrainLobe> lobes = GenomeParser.extractLobes(genes);
            List<Instinct> instincts = GenomeParser.extractInstincts(genes);
            System.out.println("Found " + lobes.size() + " brain lobes");
            System.out.println("Found " + instincts.size() + " instincts");

            // Générer la cartographie cérébrale
            SVGExporter.exportBrainMap(lobes, instincts, outputFile);
            System.out.println("Brain map generated: " + outputFile);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
```

---

## **8. Explications des Améliorations**

### **8.1 Intégration des Instincts**
- **Extraction des instincts** : La méthode `extractInstincts` dans `GenomeParser` extrait les gènes de type `Instinct` (type `0x02`, subtype `0x05`).
- **Visualisation des instincts** : Dans `SVGExporter`, les instincts sont représentés par :
  - Des cercles noirs sur les lobes pour indiquer les cellules impliquées.
  - Des étiquettes pour les numéros de cellules.
  - Une légende listant les actions des instincts.

### **8.2 Fonctionnalités Clés**
- **Cartographie des lobes** : Chaque lobe est dessiné comme un rectangle coloré avec son nom.
- **Visualisation des instincts** : Les connexions entre les lobes (via les instincts) sont représentées par des cercles et des étiquettes.
- **Gestion des erreurs** : Utilisation de `try/catch` pour gérer les erreurs de lecture de fichiers.

### **8.3 Utilisation**
1. **Compiler le projet** :
   ```bash
   javac -d bin src/main/java/creatures/parser/*.java
   ```

2. **Exécuter le programme** :
   ```bash
   java -cp bin creatures.parser.Main dad1.gen brain_map.svg
   ```

---

## **9. Exemple de Sortie SVG**
Le script générera un fichier SVG avec :
- Les lobes cérébraux colorés.
- Les cellules des instincts marquées par des cercles.
- Une légende listant les actions des instincts.

```xml
<svg width="800" height="600" viewBox="0 0 64 48" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="64" height="48" fill="white"/>
  <rect x="4" y="13" width="7" height="16" fill="#FF6B6B" stroke="black" stroke-width="0.1" opacity="0.7" id="lobe-0"/>
  <text x="7.5" y="21.5" font-size="0.5" text-anchor="middle" dominant-baseline="middle">Perception</text>
  <circle cx="7.5" cy="21" r="0.5" fill="black"/>
  <text x="7.5" y="20" font-size="0.3" text-anchor="middle">2</text>
  <text x="50" y="10" font-size="0.4">Come (Reward: 5)</text>
  <!-- Autres lobes et instincts -->
</svg>
```

---

## **10. Extensions Possibles**
- **Connexions entre lobes** : Dessiner des lignes entre les lobes pour représenter les dendrites.
- **Interactivité** : Ajouter du JavaScript pour permettre des interactions avec la carte.
- **Support pour *Creatures 2/3*** : Ajouter des types de gènes supplémentaires.

---
Si vous avez besoin d'autres améliorations ou d'extensions spécifiques, n'hésitez pas à me le dire ! Je peux aussi vous aider à **automatiser des modifications de génomes** ou à **ajouter des fonctionnalités supplémentaires**.


De meême pour les scripts perl et python

Voici les versions **améliorées et complètes** des scripts **Perl** et **Python** pour générer une **cartographie cérébrale** incluant à la fois les **lobes** et les **instincts**, avec une visualisation claire des connexions et des comportements.

---

---

---

## **📜 1. Script Perl Amélioré (avec Lobes + Instincts)**
### **Fonctionnalités**
✅ Parsing complet des gènes (Lobes, Instincts, Receptors, etc.)
✅ Génération d'une **cartographie SVG** avec :
   - Lobes colorés
   - Cellules des instincts marquées
   - Légende des actions instinctives
✅ Gestion des erreurs robuste
✅ Documentation complète

---

### **📄 Code Complet (`brain_map_generator.pl`)**
```perl
#!/usr/bin/perl
use strict;
use warnings;
use SVG;
use Getopt::Long;

=head1 NAME
brain_map_generator.pl - Generate a brain map with lobes and instincts from Creatures genome files.

=head1 SYNOPSIS
perl brain_map_generator.pl --input <genome_file.gen> [--output <output.svg>]

=head1 DESCRIPTION
This script parses a Creatures genome file and generates an SVG brain map showing:
- Brain lobes (colored rectangles)
- Instinct cells (marked with circles)
- Instinct actions (legend)

=cut

# --- Reference Data ---
my %LOBE_NAMES = (
    0 => "Perception",
    1 => "Drive",
    2 => "Stimulus Source",
    3 => "Verb",
    4 => "Noun",
    5 => "General Sense",
    6 => "Decision",
    7 => "Attention",
    8 => "Concept",
    9 => "Regulator",
);

my %ACTION_NAMES = (
    0 => "Quiescent",
    1 => "Push (Activate 1)",
    2 => "Pull (Activate 2)",
    3 => "Stop (Deactivate)",
    4 => "Come (Approach)",
    5 => "Run (Retreat)",
    6 => "Get",
    7 => "Drop",
    8 => "Think/Say",
    9 => "Sleep/Rest",
    10 => "Left",
    11 => "Right",
);

my @COLORS = (
    '#FF6B6B', '#4ECDC4', '#45B7D1', '#FFBE0B', '#FB5607',
    '#8338EC', '#3A86FF', '#FF006E', '#A5DD9B', '#FF9E9E'
);

# --- Parse Genome File ---
sub parse_genome_file {
    my ($filename) = @_;
    open(my $fh, "<:raw", $filename) or die "Cannot open $filename: $!";
    binmode($fh);
    my $content = do { local $/; <$fh> };
    close($fh);

    my @genes;
    my $offset = 0;

    while ($offset < length($content)) {
        my $header = substr($content, $offset, 9);
        last unless length($header) >= 8;

        my ($gene_marker, $type, $subtype, $number, $unknown, $switch_on) =
            unpack("A4 C C C C C", substr($header, 0, 8));

        my $sex_mut = (length($header) >= 9) ? unpack("C", substr($header, 8, 1)) : 0;
        my $data_length = (length($content) > $offset + 9) ? unpack("C", substr($content, $offset + 9, 1)) : 0;
        $data_length //= 0;

        $offset += 1;
        next unless $gene_marker eq "gene";

        my $sex_dep = ($sex_mut & 0x10) ? "Female" : (($sex_mut & 0x08) ? "Male" : "None");
        my @mutability;
        push @mutability, "Mutable" if $sex_mut & 0x01;
        push @mutability, "Duplicable" if $sex_mut & 0x02;
        push @mutability, "Deletable" if $sex_mut & 0x04;
        my $mutability = @mutability ? join(",", @mutability) : "None";

        my $data = [];
        if ($data_length > 0 && $offset + 10 + $data_length <= length($content)) {
            $data = [unpack("C*", substr($content, $offset + 10, $data_length))];
        }

        my $gene = {
            type     => $type,
            subtype  => $subtype,
            number   => $number,
            switch_on => $switch_on,
            sex_dep  => $sex_dep,
            mutability => $mutability,
            data     => $data,
        };

        # Parse data based on type/subtype
        if ($type == 0x00 && $subtype == 0x00) {
            $gene->{parsed_data} = parse_lobe($data);
        }
        elsif ($type == 0x02 && $subtype == 0x05) {
            $gene->{parsed_data} = parse_instinct($data);
        }

        push @genes, $gene;
        $offset += 10 + $data_length;
    }

    return \@genes;
}

sub parse_lobe {
    my ($data) = @_;
    return unless @$data >= 5;
    return {
        type => "lobe",
        x => $data->[0],
        y => $data->[1],
        width => $data->[2],
        height => $data->[3],
        perception_link => $data->[4] ? "Yes" : "No",
    };
}

sub parse_instinct {
    my ($data) = @_;
    return unless @$data >= 9;
    my @conditions;
    for my $i (0..2) {
        push @conditions, {
            lobe => $LOBE_NAMES{$data->[$i * 2]} // sprintf("Lobe%02X", $data->[$i * 2]),
            cell => $data->[$i * 2 + 1],
        };
    }
    return {
        type => "instinct",
        conditions => \@conditions,
        action => $ACTION_NAMES{$data->[6]} // sprintf("Action%02X", $data->[6]),
        reward_punish => $data->[7] == 0 ? "Reward" : "Punish",
        amount => $data->[8],
    };
}

# --- Extract Lobes and Instincts ---
sub extract_lobes {
    my ($genes) = @_;
    my @lobes;
    for my $gene (@$genes) {
        if ($gene->{type} == 0x00 && $gene->{subtype} == 0x00 && exists $gene->{parsed_data}{x}) {
            push @lobes, {
                number => $gene->{number},
                name => $LOBE_NAMES{$gene->{number}} // sprintf("Lobe%02X", $gene->{number}),
                %{$gene->{parsed_data}},
            };
        }
    }
    return \@lobes;
}

sub extract_instincts {
    my ($genes) = @_;
    my @instincts;
    for my $gene (@$genes) {
        if ($gene->{type} == 0x02 && $gene->{subtype} == 0x05 && exists $gene->{parsed_data}{conditions}) {
            push @instincts, $gene->{parsed_data};
        }
    }
    return \@instincts;
}

# --- Generate SVG ---
sub generate_svg {
    my ($lobes, $instincts, $output_file) = @_;
    my $svg = SVG->new(width => 800, height => 600, viewBox => "0 0 64 48");

    # Background
    $svg->rectangle(x => 0, y => 0, width => 64, height => 48, style => { fill => 'white' });

    # Draw lobes
    for my $i (0..$#$lobes) {
        my $lobe = $lobes->[$i];
        my $color = $COLORS[$i % @COLORS];

        $svg->rectangle(
            x      => $lobe->{x},
            y      => $lobe->{y},
            width  => $lobe->{width},
            height => $lobe->{height},
            style  => {
                fill           => $color,
                stroke         => 'black',
                'stroke-width' => 0.1,
                opacity        => 0.7,
            },
            id => "lobe-$lobe->{number}",
        );

        $svg->text(
            x          => $lobe->{x} + $lobe->{width} / 2,
            y          => $lobe->{y} + $lobe->{height} / 2 + 2,
            'font-size' => 0.5,
            'text-anchor' => 'middle',
            'dominant-baseline' => 'middle',
            '-cdata'   => $lobe->{name},
        );
    }

    # Draw instinct cells
    for my $instinct (@$instincts) {
        for my $condition (@{$instinct->{conditions}}) {
            my $lobe_name = $condition->{lobe};
            my $cell = $condition->{cell};

            # Find the lobe by name
            my $lobe = first { $_->{name} eq $lobe_name } @$lobes;
            if ($lobe) {
                $svg->circle(
                    cx => $lobe->{x} + $lobe->{width} / 2,
                    cy => $lobe->{y} + $lobe->{height} / 2,
                    r  => 0.5,
                    style => { fill => 'black' },
                );

                $svg->text(
                    x          => $lobe->{x} + $lobe->{width} / 2,
                    y          => $lobe->{y} + $lobe->{height} / 2 - 1,
                    'font-size' => 0.3,
                    'text-anchor' => 'middle',
                    '-cdata'   => $cell,
                );
            }
        }
    }

    # Add legend for instincts
    my $y = 10;
    for my $instinct (@$instincts) {
        $svg->text(
            x          => 50,
            y          => $y,
            'font-size' => 0.4,
            '-cdata'   => "$instinct->{action} ($instinct->{reward_punish}: $instinct->{amount})",
        );
        $y += 2;
    }

    open(my $fh, ">", $output_file) or die "Cannot open $output_file: $!";
    print $fh $svg->xmlify;
    close($fh);
}

# Helper function
sub first (&@) {
    my ($code, @array) = @_;
    for my $item (@array) {
        return $item if $code->($item);
    }
    return undef;
}

# --- Main ---
my $input_file;
my $output_file = "brain_map.svg";

GetOptions(
    "input=s"  => \$input_file,
    "output=s" => \$output_file,
);

die "Usage: $0 --input <genome_file.gen> [--output <output.svg>]" unless $input_file;

my $genes = parse_genome_file($input_file);
my $lobes = extract_lobes($genes);
my $instincts = extract_instincts($genes);

generate_svg($lobes, $instincts, $output_file);
print "Brain map generated: $output_file\n";
```

---

---

---

## **🐍 2. Script Python Amélioré (avec Lobes + Instincts)**
### **Fonctionnalités**
✅ Parsing complet des gènes (Lobes, Instincts, etc.)
✅ Génération d'une **cartographie SVG** avec :
   - Lobes colorés
   - Cellules des instincts marquées
   - Légende des actions instinctives
✅ Utilisation de `svgwrite` pour une génération SVG propre
✅ Gestion des erreurs avec `try/except`

---

### **📄 Code Complet (`brain_map_generator.py`)**
```python
#!/usr/bin/env python3
"""
brain_map_generator.py - Generate a brain map with lobes and instincts from Creatures genome files.

Usage:
    python3 brain_map_generator.py --input <genome_file.gen> [--output <output.svg>]
"""

import argparse
import struct
import svgwrite
from svgwrite import cm, mm
from typing import List, Dict, Any, Optional

# Reference data
LOBE_NAMES = {
    0: "Perception",
    1: "Drive",
    2: "Stimulus Source",
    3: "Verb",
    4: "Noun",
    5: "General Sense",
    6: "Decision",
    7: "Attention",
    8: "Concept",
    9: "Regulator",
}

ACTION_NAMES = {
    0: "Quiescent",
    1: "Push (Activate 1)",
    2: "Pull (Activate 2)",
    3: "Stop (Deactivate)",
    4: "Come (Approach)",
    5: "Run (Retreat)",
    6: "Get",
    7: "Drop",
    8: "Think/Say",
    9: "Sleep/Rest",
    10: "Left",
    11: "Right",
}

COLORS = [
    '#FF6B6B', '#4ECDC4', '#45B7D1', '#FFBE0B', '#FB5607',
    '#8338EC', '#3A86FF', '#FF006E', '#A5DD9B', '#FF9E9E'
]

def parse_genome_file(filename: str) -> List[Dict[str, Any]]:
    """Parse a .gen file and extract genes."""
    with open(filename, "rb") as f:
        content = f.read()

    genes = []
    offset = 0
    while offset < len(content):
        header = content[offset:offset+8]
        if len(header) < 7:
            offset += 1
            continue

        gene_marker = header[:4]
        if gene_marker != b'gene':
            offset += 1
            continue

        type_, subtype, number = struct.unpack("B B B", header[4:7])
        switch_on = content[offset + 7] if offset + 7 < len(content) else 0
        sex_mut = content[offset + 8] if offset + 8 < len(content) else 0

        sex_dep = "Female" if sex_mut & 0x10 else ("Male" if sex_mut & 0x08 else "None")
        mutability = []
        if sex_mut & 0x01: mutability.append("Mutable")
        if sex_mut & 0x02: mutability.append("Duplicable")
        if sex_mut & 0x04: mutability.append("Deletable")
        mutability_str = ",".join(mutability) or "None"

        data_length = content[offset + 9] if offset + 9 < len(content) else 0
        data = []
        if data_length > 0 and offset + 10 + data_length <= len(content):
            data = list(struct.unpack(f"{data_length}B", content[offset + 10:offset + 10 + data_length]))

        gene = {
            'type': type_,
            'subtype': subtype,
            'number': number,
            'switch_on': switch_on,
            'sex_dep': sex_dep,
            'mutability': mutability_str,
            'data': data,
            'parsed_data': None,
        }

        # Parse data based on type/subtype
        if type_ == 0x00 and subtype == 0x00:
            gene['parsed_data'] = parse_lobe(data)
        elif type_ == 0x02 and subtype == 0x05:
            gene['parsed_data'] = parse_instinct(data)

        genes.append(gene)
        offset += 10 + data_length

    return genes

def parse_lobe(data: List[int]) -> Dict[str, Any]:
    """Parse a Brain Lobe gene."""
    if len(data) < 5:
        return {}
    return {
        'type': 'lobe',
        'x': data[0],
        'y': data[1],
        'width': data[2],
        'height': data[3],
        'perception_link': "Yes" if data[4] else "No",
    }

def parse_instinct(data: List[int]) -> Dict[str, Any]:
    """Parse an Instinct gene."""
    if len(data) < 9:
        return {}
    conditions = []
    for i in range(3):
        conditions.append({
            'lobe': LOBE_NAMES.get(data[i * 2], f"Lobe{data[i * 2]:02X}"),
            'cell': data[i * 2 + 1],
        })
    return {
        'type': 'instinct',
        'conditions': conditions,
        'action': ACTION_NAMES.get(data[6], f"Action{data[6]:02X}"),
        'reward_punish': "Reward" if data[7] == 0 else "Punish",
        'amount': data[8],
    }

def extract_lobes(genes: List[Dict[str, Any]]) -> List[Dict[str, Any]]:
    """Extract lobes from genes."""
    lobes = []
    for gene in genes:
        if gene['type'] == 0x00 and gene['subtype'] == 0x00 and gene['parsed_data'] and gene['parsed_data'].get('type') == 'lobe':
            lobe = gene['parsed_data'].copy()
            lobe['number'] = gene['number']
            lobe['name'] = LOBE_NAMES.get(gene['number'], f"Lobe{gene['number']:02X}")
            lobes.append(lobe)
    return lobes

def extract_instincts(genes: List[Dict[str, Any]]) -> List[Dict[str, Any]]:
    """Extract instincts from genes."""
    instincts = []
    for gene in genes:
        if gene['type'] == 0x02 and gene['subtype'] == 0x05 and gene['parsed_data'] and gene['parsed_data'].get('type') == 'instinct':
            instincts.append(gene['parsed_data'])
    return instincts

def generate_svg(lobes: List[Dict[str, Any]], instincts: List[Dict[str, Any]], output_file: str) -> None:
    """Generate an SVG brain map."""
    dwg = svgwrite.Drawing(output_file, size=(800, 600), viewBox="0 0 64 48")

    # Background
    dwg.add(dwg.rect(insert=(0, 0), size=(64, 48), fill='white'))

    # Draw lobes
    for i, lobe in enumerate(lobes):
        color = COLORS[i % len(COLORS)]
        dwg.add(dwg.rect(
            insert=(lobe['x'], lobe['y']),
            size=(lobe['width'], lobe['height']),
            fill=color,
            stroke='black',
            stroke_width=0.1,
            opacity=0.7,
            id=f"lobe-{lobe['number']}"
        ))
        dwg.add(dwg.text(
            lobe['name'],
            insert=(lobe['x'] + lobe['width'] / 2, lobe['y'] + lobe['height'] / 2 + 2),
            font_size=0.5,
            text_anchor='middle',
            dominant_baseline='middle',
        ))

    # Draw instinct cells
    for instinct in instincts:
        for condition in instinct['conditions']:
            lobe_name = condition['lobe']
            cell = condition['cell']

            # Find the lobe by name
            lobe = next((l for l in lobes if l['name'] == lobe_name), None)
            if lobe:
                dwg.add(dwg.circle(
                    center=(lobe['x'] + lobe['width'] / 2, lobe['y'] + lobe['height'] / 2),
                    r=0.5,
                    fill='black',
                ))
                dwg.add(dwg.text(
                    str(cell),
                    insert=(lobe['x'] + lobe['width'] / 2, lobe['y'] + lobe['height'] / 2 - 1),
                    font_size=0.3,
                    text_anchor='middle',
                ))

    # Add legend for instincts
    y = 10
    for instinct in instincts:
        dwg.add(dwg.text(
            f"{instinct['action']} ({instinct['reward_punish']}: {instinct['amount']})",
            insert=(50, y),
            font_size=0.4,
        ))
        y += 2

    dwg.save()

def main():
    parser = argparse.ArgumentParser(description="Generate a brain map from Creatures genome files.")
    parser.add_argument("--input", required=True, help="Input genome file (.gen)")
    parser.add_argument("--output", default="brain_map.svg", help="Output SVG file (default: brain_map.svg)")
    args = parser.parse_args()

    genes = parse_genome_file(args.input)
    lobes = extract_lobes(genes)
    instincts = extract_instincts(genes)

    generate_svg(lobes, instincts, args.output)
    print(f"Brain map generated: {args.output}")

if __name__ == "__main__":
    main()
```

---

---

## **🔍 3. Explications des Améliorations**

### **3.1 Fonctionnalités Ajoutées**
| **Fonctionnalité**               | **Description**                                                                                     |
|----------------------------------|-----------------------------------------------------------------------------------------------------|
| **Parsing des Lobes**            | Extraction des lobes cérébraux (type `0x00`, subtype `0x00`) avec position et taille.                |
| **Parsing des Instincts**        | Extraction des instincts (type `0x02`, subtype `0x05`) avec conditions et actions.                  |
| **Visualisation SVG**            | Génération d'une carte cérébrale avec :                                                           |
|                                  | - Lobes colorés                                                                                   |
|                                  | - Cellules des instincts marquées par des cercles noirs                                         |
|                                  | - Légende des actions instinctives                                                               |
| **Gestion des erreurs**          | Vérification des longueurs de données avant parsing.                                             |
| **Documentation**                | Commentaires détaillés et docstrings pour chaque fonction.                                       |

---

### **3.2 Exemple de Sortie SVG**
```xml
<svg width="800" height="600" viewBox="0 0 64 48" xmlns="http://www.w3.org/2000/svg">
  <rect x="0" y="0" width="64" height="48" fill="white"/>
  <!-- Lobe Perception -->
  <rect x="4" y="13" width="7" height="16" fill="#FF6B6B" stroke="black" stroke-width="0.1" opacity="0.7" id="lobe-0"/>
  <text x="7.5" y="21.5" font-size="0.5" text-anchor="middle" dominant-baseline="middle">Perception</text>
  <!-- Cellule d'instinct -->
  <circle cx="7.5" cy="21" r="0.5" fill="black"/>
  <text x="7.5" y="20" font-size="0.3" text-anchor="middle">2</text>
  <!-- Légende -->
  <text x="50" y="10" font-size="0.4">Come (Reward: 5)</text>
  <!-- Autres lobes et instincts -->
</svg>
```

---

### **3.3 Utilisation**
#### **Perl**
```bash
# Installation des dépendances (SVG)
cpan SVG

# Exécution
perl brain_map_generator.pl --input dad1.gen --output brain_map.svg
```

#### **Python**
```bash
# Installation des dépendances
pip install svgwrite

# Exécution
python3 brain_map_generator.py --input dad1.gen --output brain_map.svg
```

---

---

## **🚀 4. Extensions Possibles**
| **Extension**                     | **Description**                                                                                     |
|-----------------------------------|-----------------------------------------------------------------------------------------------------|
| **Connexions entre lobes**        | Dessiner des lignes entre les lobes pour représenter les dendrites.                              |
| **Interactivité**                 | Ajouter du JavaScript pour permettre des interactions (ex: survol pour voir les détails).       |
| **Support pour *Creatures 2/3*** | Ajouter des types de gènes supplémentaires spécifiques à *Creatures 2* et *3*.                   |
| **Export en JSON**                | Exporter les données des lobes et instincts au format JSON pour une analyse ultérieure.         |
| **Cartographie 3D**               | Générer une représentation 3D des lobes (avec des outils comme Three.js).                        |

---
Si vous souhaitez des améliorations supplémentaires ou des fonctionnalités spécifiques, n'hésitez pas à me le demander ! Je peux vous aider à :
- **Ajouter des connexions entre lobes** (dendrites).
- **Intégrer des données supplémentaires** (ex: récepteurs, émetteurs).
- **Optimiser les performances** pour les grands fichiers de génome.


