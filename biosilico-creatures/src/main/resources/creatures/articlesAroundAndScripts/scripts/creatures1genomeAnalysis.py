import struct
import json
import csv
import os
from dataclasses import dataclass, field
from typing import List, Dict, Any, Optional

# --- Base de données des noms lisibles ---
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
    9: "Regulator",  # Creatures 2+
}

CHEM_NAMES = {
    0x00: "Pain",
    0x01: "Need for Pleasure",
    0x02: "Hunger",
    0x03: "Coldness",
    0x04: "Hotness",
    0x0A: "Fear",
    0x0B: "Boredom",
    0x1E: "Reward",
    0x1F: "Punishment",
    # Ajouter d'autres chimies ici
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

TYPE_NAMES = {
    0x00: "Brain",
    0x01: "Biochemistry",
    0x02: "Creature",
    # 0x03: "Body",  # Pour Creatures 2/3
}

SUBTYPE_NAMES = {
    0x00: {0x00: "Lobe"},
    0x01: {
        0x00: "Receptor",
        0x01: "Emitter",
        0x02: "Reaction",
        0x03: "Half-Lives",
        0x04: "Initial Concentration",
    },
    0x02: {
        0x00: "Stimulus",
        0x01: "Genus",
        0x02: "Appearance",
        0x03: "Pose",
        0x04: "Gait",
        0x05: "Instinct",
        0x06: "Pigment",
        0x07: "Pigment Bleed",  # Creatures 2+
    },
    # 0x03: { 0x00: "Organ" },  # Pour Creatures 2/3
}

STAGE_NAMES = ["Embryo", "Child", "Youth", "Adolescent", "Adult", "Senior", "Old"]

@dataclass
class Gene:
    type: int
    subtype: int
    number: int
    switch_on: int
    sex_dep: str
    mutability: str
    data: List[int]
    parsed_data: Dict[str, Any] = field(default_factory=dict)

    def parse_reaction(self) -> None:
        if self.type != 0x01 or self.subtype != 0x02:
            return
        if len(self.data) < 4:  # Minimum 2 réactifs + 2 produits
            return

        # Compléter les données si nécessaire
        data = self.data[:8]  # Limiter à 8 bytes pour éviter les erreurs
        while len(data) % 2 != 0:
            data.append(0)

        reactants = []
        products = []
        for i in range(0, min(4, len(data)), 2):
            chem = data[i + 1] if i + 1 < len(data) else 0
            proportion = data[i] if i < len(data) else 0
            container = reactants if i < 2 else products
            chem_name = CHEM_NAMES.get(chem, f"Chem{chem:02X}")
            container.append({
                "chem": chem,
                "proportion": proportion,
                "chem_name": chem_name,
            })

        rate = data[8] if len(data) >= 9 else 1  # Valeur par défaut
        self.parsed_data = {
            "reactants": reactants,
            "products": products,
            "rate": rate,
        }

    def parse_instinct(self) -> None:
        if self.type != 0x02 or self.subtype != 0x05:
            return
        if len(self.data) < 9:
            return

        lobe1, cell1, lobe2, cell2, lobe3, cell3, action, reward_punish, amount = self.data[:9]
        self.parsed_data = {
            "conditions": [
                {"lobe": LOBE_NAMES.get(lobe1, f"Lobe{lobe1:02X}"), "cell": cell1},
                {"lobe": LOBE_NAMES.get(lobe2, f"Lobe{lobe2:02X}"), "cell": cell2},
                {"lobe": LOBE_NAMES.get(lobe3, f"Lobe{lobe3:02X}"), "cell": cell3},
            ],
            "action": ACTION_NAMES.get(action, f"Action{action:02X}"),
            "reward_punish": "Reward" if reward_punish == 0 else "Punish",
            "amount": amount,
        }

    def parse_lobe(self) -> None:
        if self.type != 0x00 or self.subtype != 0x00:
            return
        if len(self.data) < 5:
            return

        x, y, width, height, perception_link = self.data[:5]
        self.parsed_data = {
            "position": {"x": x, "y": y},
            "size": {"width": width, "height": height},
            "perception_link": "Yes" if perception_link else "No",
            "neurons": width * height,
        }

    def __str__(self) -> str:
        base_str = (
            f"Gene {self.number:03d}: Type=0x{self.type:02X} ({TYPE_NAMES.get(self.type, 'Unknown')}), "
            f"Subtype=0x{self.subtype:02X} ({SUBTYPE_NAMES.get(self.type, {}).get(self.subtype, 'Unknown')}), "
            f"SwitchOn={self.switch_on} ({STAGE_NAMES[self.switch_on] if self.switch_on < len(STAGE_NAMES) else 'Unknown'}), "
            f"SexDep={self.sex_dep}, Mutability={self.mutability}"
        )

        if self.type == 0x01 and self.subtype == 0x02 and "reactants" in self.parsed_data:
            reaction = self.parsed_data
            base_str += (
                f"\n  Reaction: {' + '.join(f'{r["proportion"]} {r["chem_name"]}' for r in reaction['reactants'])} → "
                f"{' + '.join(f'{p["proportion"]} {p["chem_name"]}' for p in reaction['products'])} "
                f"(Rate: {reaction['rate']})"
            )
        elif self.type == 0x02 and self.subtype == 0x05 and "conditions" in self.parsed_data:
            instinct = self.parsed_data
            base_str += (
                f"\n  Instinct: IF {' AND '.join(f'{c["lobe"]}[{c["cell"]}]' for c in instinct['conditions'])} "
                f"THEN {instinct['action']} ({instinct['reward_punish']}: {instinct['amount']})"
            )
        elif self.type == 0x00 and self.subtype == 0x00 and "position" in self.parsed_data:
            lobe = self.parsed_data
            base_str += (
                f"\n  Lobe: {LOBE_NAMES.get(self.number, f'Lobe{self.number:02X}')} "
                f"at ({lobe['position']['x']},{lobe['position']['y']}), "
                f"Size: {lobe['size']['width']}x{lobe['size']['height']}, "
                f"Neurons: {lobe['neurons']}, Perception Link: {lobe['perception_link']}"
            )

        return base_str

def parse_genome_file(filename: str) -> List[Gene]:
    genes = []
    with open(filename, "rb") as f:
        content = f.read()

    offset = 0
    while offset < len(content):
        header = content[offset:offset+8]
        if len(header) < 7:  # 4s (4) + 3x B (3) = 7 bytes minimum
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

        gene = Gene(
            type=type_,
            subtype=subtype,
            number=number,
            switch_on=switch_on,
            sex_dep=sex_dep,
            mutability=mutability_str,
            data=data,
        )

        gene.parse_reaction()
        gene.parse_instinct()
        gene.parse_lobe()

        genes.append(gene)
        offset += 10 + data_length  # 9 (header) + 1 (data_length) + data_length

    return genes

def export_to_json(genes: List[Gene], filename: str) -> None:
    json_genes = []
    for gene in genes:
        json_gene = {
            "type": f"0x{gene.type:02X}",
            "type_name": TYPE_NAMES.get(gene.type, "Unknown"),
            "subtype": f"0x{gene.subtype:02X}",
            "subtype_name": SUBTYPE_NAMES.get(gene.type, {}).get(gene.subtype, "Unknown"),
            "number": gene.number,
            "switch_on": gene.switch_on,
            "switch_on_name": STAGE_NAMES[gene.switch_on] if gene.switch_on < len(STAGE_NAMES) else "Unknown",
            "sex_dep": gene.sex_dep,
            "mutability": gene.mutability,
            "data": gene.data,
        }
        if gene.parsed_data:
            json_gene["parsed_data"] = gene.parsed_data
        json_genes.append(json_gene)

    with open(filename, "w") as f:
        json.dump(json_genes, f, indent=2)

def export_to_csv(genes: List[Gene], filename: str) -> None:
    with open(filename, "w", newline='') as f:
        writer = csv.writer(f)
        writer.writerow([
            "Number", "Type", "Subtype", "SwitchOn", "SexDep", "Mutability",
            "Data", "ParsedData"
        ])
        for gene in genes:
            writer.writerow([
                gene.number,
                f"0x{gene.type:02X} ({TYPE_NAMES.get(gene.type, 'Unknown')})",
                f"0x{gene.subtype:02X} ({SUBTYPE_NAMES.get(gene.type, {}).get(gene.subtype, 'Unknown')})",
                f"{gene.switch_on} ({STAGE_NAMES[gene.switch_on] if gene.switch_on < len(STAGE_NAMES) else 'Unknown'})",
                gene.sex_dep,
                gene.mutability,
                " ".join(map(str, gene.data)),
                str(gene.parsed_data) if gene.parsed_data else "",
            ])

def display_genes(genes: List[Gene]) -> None:
    for gene in genes:
        print(gene)
        print()

if __name__ == "__main__":
    # genes_dad = parse_genome_file("dad1.txt")
    # genes_mum = parse_genome_file("mum1.txt")
    # print("=== Genome DAD ===")
    # display_genes(genes_dad)
    # export_to_json(genes_dad, "dad1.json")
    # export_to_csv(genes_dad, "dad1.csv")
    # print("\n=== Genome MUM ===")
    # display_genes(genes_mum)
    # export_to_json(genes_mum, "mum1.json")
    # export_to_csv(genes_mum, "mum1.csv")
    import argparse
    parser = argparse.ArgumentParser(description="Parse Creatures genome files.")
    parser.add_argument("file", help="Genome file to parse")
    parser.add_argument("--json", help="Export to JSON file")
    parser.add_argument("--csv", help="Export to CSV file")
    args = parser.parse_args()

    genes = parse_genome_file(args.file)
    display_genes(genes)

    if args.json:
        export_to_json(genes, args.json)
    if args.csv:
        export_to_csv(genes, args.csv)
