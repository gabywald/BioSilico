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
    parser.add_argument("--output", default="brain_map_py.svg", help="Output SVG file (default: brain_map.svg)")
    args = parser.parse_args()

    genes = parse_genome_file(args.input)
    lobes = extract_lobes(genes)
    instincts = extract_instincts(genes)

    generate_svg(lobes, instincts, args.output)
    print(f"Brain map generated: {args.output}")

if __name__ == "__main__":
    main()
