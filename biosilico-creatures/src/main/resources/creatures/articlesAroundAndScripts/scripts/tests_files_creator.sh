#!/bin/bash

# valid_genome1.gen
echo -n -e '\x67\x65\x6E\x65\x02\x01\x01\x00\x0A\x01\x02\x03\x04\x05\x06\x07\x08\x09\x0A' > test_genomes/valid_genome1.gen

# valid_genome2.gen
echo -n -e '\x67\x65\x6E\x65\x01\x02\x02\x00\x09\x01\x00\x02\x01\x03\x04\x05\x06\x01\x0A' > test_genomes/valid_genome2.gen

# minimal_genome.gen
echo -n -e '\x67\x65\x6E\x65\x00\x00\x00\x00\x00' > test_genomes/minimal_genome.gen

# corrupted_header.gen
echo -n -e '\x47\x45\x4E\x45\x01\x01\x01\x00\x01\x00' > test_genomes/corrupted_header.gen

# corrupted_data.gen
echo -n -e '\x67\x65\x6E\x65\x01\x01\x01\x00\x02\x01' > test_genomes/corrupted_data.gen

# empty.gen (fichier vide)
touch test_genomes/empty.gen
