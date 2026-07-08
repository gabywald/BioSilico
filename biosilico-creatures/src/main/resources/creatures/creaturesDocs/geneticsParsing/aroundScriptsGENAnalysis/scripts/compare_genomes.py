#!/usr/bin/env python3
"""
compare_genomes.py - Compare two Creatures genome files.

Usage:
    python3 compare_genomes.py --file1 <file1.gen> --file2 <file2.gen> --output <output.txt>

Description:
    This script compares two Creatures genome files and identifies added, removed, and modified genes.
"""

import struct
import argparse
from dataclasses import dataclass, field
from typing import List, Dict, Any, Optional

@dataclass
class Gene:
    """Represents a gene with basic attributes."""
    type: int
    subtype: int
    number: int
    switch_on: int
    sex_dep: str
    mutability: str
    data: List[int]

    def to_string(self) -> str:
        """Returns a human-readable string representation of the gene."""
        return (
            f"Gene {self.number:03d}: Type=0x{self.type:02X}, Subtype=0x{self.subtype:02X}, "
            f"SwitchOn={self.switch_on}, SexDep={self.sex_dep}, Mutability={self.mutability}"
        )

    def equals(self, other: 'Gene') -> bool:
        """Checks if two genes are equal."""
        return (
            self.type == other.type and
            self.subtype == other.subtype and
            self.number == other.number and
            self.switch_on == other.switch_on and
            self.sex_dep == other.sex_dep and
            self.mutability == other.mutability and
            self.data == other.data
        )

def parse_genome_file(filename: str) -> List[Gene]:
    """Parses a .gen file and returns a list of Gene objects."""
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

        gene = Gene(
            type=type_,
            subtype=subtype,
            number=number,
            switch_on=switch_on,
            sex_dep=sex_dep,
            mutability=mutability_str,
            data=data,
        )

        genes.append(gene)
        offset += 10 + data_length

    return genes

def compare_genomes(genes1: List[Gene], genes2: List[Gene]) -> Dict[str, Any]:
    """Compares two lists of genes and returns differences."""
    genes1_dict = {gene.number: gene for gene in genes1}
    genes2_dict = {gene.number: gene for gene in genes2}

    added = [gene for gene in genes2 if gene.number not in genes1_dict]
    removed = [gene for gene in genes1 if gene.number not in genes2_dict]
    common = [gene for gene in genes1 if gene.number in genes2_dict]

    modified = []
    for gene in common:
        gene1 = genes1_dict[gene.number]
        gene2 = genes2_dict[gene.number]
        if not gene1.equals(gene2):
            modified.append({"old": gene1, "new": gene2})

    return {
        "added": added,
        "removed": removed,
        "modified": modified,
        "unchanged": len(common) - len(modified),
    }

def generate_report(comparison: Dict[str, Any], file1: str, file2: str, output: str) -> None:
    """Generates a comparison report."""
    with open(output, "w") as f:
        f.write("=== Genome Comparison Report ===\n")
        f.write(f"File 1: {file1}\n")
        f.write(f"File 2: {file2}\n\n")

        f.write(f"=== Added Genes ({len(comparison['added'])}) ===\n")
        for gene in comparison["added"]:
            f.write(f"{gene.to_string()}\n")

        f.write(f"\n=== Removed Genes ({len(comparison['removed'])}) ===\n")
        for gene in comparison["removed"]:
            f.write(f"{gene.to_string()}\n")

        f.write(f"\n=== Modified Genes ({len(comparison['modified'])}) ===\n")
        for mod in comparison["modified"]:
            f.write(f"Gene {mod['old'].number}:\n")
            f.write(f"  Old: {mod['old'].to_string()}\n")
            f.write(f"  New: {mod['new'].to_string()}\n")

        f.write("\n=== Summary ===\n")
        f.write(f"Total genes in File 1: {len(genes1)}\n")
        f.write(f"Total genes in File 2: {len(genes2)}\n")
        f.write(f"Added: {len(comparison['added'])}\n")
        f.write(f"Removed: {len(comparison['removed'])}\n")
        f.write(f"Modified: {len(comparison['modified'])}\n")
        f.write(f"Unchanged: {comparison['unchanged']}\n")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Compare two Creatures genome files.")
    parser.add_argument("--file1", required=True, help="First genome file")
    parser.add_argument("--file2", required=True, help="Second genome file")
    parser.add_argument("--output", default="comparison_report.txt", help="Output report file")
    args = parser.parse_args()

    genes1 = parse_genome_file(args.file1)
    genes2 = parse_genome_file(args.file2)

    comparison = compare_genomes(genes1, genes2)
    generate_report(comparison, args.file1, args.file2, args.output)

    print(f"Comparison report generated: {args.output}")
