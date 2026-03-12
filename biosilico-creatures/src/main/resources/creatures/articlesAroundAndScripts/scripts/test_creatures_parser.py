import unittest
import os
from creatures1genomeAnalysis import parse_genome_file, export_to_json

class TestCreaturesParser(unittest.TestCase):
    def setUp(self):
        self.test_dir = "test_genomes"
        self.valid_files = ["valid_genome1.gen", "valid_genome2.gen", "minimal_genome.gen"]
        self.corrupted_files = ["corrupted_header.gen", "corrupted_data.gen"]
        self.empty_file = "empty.gen"

    def test_valid_genomes(self):
        for filename in self.valid_files:
            file_path = os.path.join(self.test_dir, filename)
            genes = parse_genome_file(file_path)
            self.assertIsInstance(genes, list)
            self.assertGreater(len(genes), 0)
            for gene in genes:
                self.assertIsNotNone(gene.type)
                self.assertIsNotNone(gene.subtype)

    def test_empty_file(self):
        file_path = os.path.join(self.test_dir, self.empty_file)
        genes = parse_genome_file(file_path)
        self.assertEqual(len(genes), 0)

    def test_corrupted_files(self):
        for filename in self.corrupted_files:
            file_path = os.path.join(self.test_dir, filename)
            genes = parse_genome_file(file_path)
            self.assertIsInstance(genes, list)  # Doit retourner une liste vide ou partielle
            print(f"Parsed {len(genes)} genes from {filename} (corrupted)")

    def test_export_json(self):
        file_path = os.path.join(self.test_dir, self.valid_files[0])
        genes = parse_genome_file(file_path)
        export_to_json(genes, "test_output.json")
        self.assertTrue(os.path.exists("test_output.json"))

if __name__ == "__main__":
    unittest.main()
