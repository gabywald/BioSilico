package gabywald.javabio.data.composition.tests;

public abstract class EmblTestsHelper {
	public static final String DATA01 = 
		"ID   SC10H5 standard; DNA; PRO; 4870 BP.\n"+
		"XX\n"+
		"AC   AL031232; Y00001; X00001-X00005; X00008; Z00001-Z00005;\n"+
		"XX\n"+
		"DE   Streptomyces coelicolor cosmid 10H5.\n"+
		"XX\n"+
		"KW   integral membrane protein.\n"+
		"XX\n"+
		"OS   Streptomyces coelicolor\n"+
		"OC   Eubacteria; Firmicutes; Actinomycetes; Streptomycetes;\n"+
		"OC   Streptomycetaceae; Streptomyces.\n"+
		"XX\n"+
		"RN   [1]\n"+
		"RP   1-4870\n"+
		"RA   Oliver K., Harris D.;\n"+
		"RT   ;\n"+
		"RL   Unpublished.\n"+
		"XX\n"+
		"RN   [2]\n"+
		"RP   1-4870\n"+
		"RA   Parkhill J., Barrell B.G., Rajandream M.A.;\n"+
		"RT   ;\n"+
		"RL   Submitted (10-AUG-1998) to the EMBL/GenBank/DDBJ databases.\n"+
		"RL   Streptomyces coelicolor sequencing project,\n"+
		"RL   Sanger Centre, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SA\n"+
		"RL   E-mail: barrell@sanger.ac.uk\n"+
		"RL   Cosmids supplied by Prof. David A. Hopwood, [3]\n"+
		"RL   John Innes Centre, Norwich Research Park, Colney,\n"+
		"RL   Norwich, Norfolk NR4 7UH, UK.\n"+
		"XX\n"+
		"RN   [3]\n"+
		"RP   1-4870\n"+
		"RA   Redenbach M., Kieser H.M., Denapaite D., Eichner A.,\n"+
		"RA   Cullum J., Kinashi H., Hopwood D.A.;\n"+
		"RT   \"A set of ordered cosmids and a detailed genetic and physical\n"+
		"RT   map for the 8 Mb Streptomyces coelicolor A3(2) chromosome.\";\n"+
		"RL   Mol. Microbiol. 21(1):77-96(1996).\n"+
		"XX\n"+
		"CC   Notes:\n"+
		"CC\n"+
		"CC   Streptomyces coelicolor sequencing at The Sanger Centre is funded \n"+
		"CC   by the BBSRC.\n"+
		"CC\n"+
		"CC   Details of S. coelicolor sequencing at the Sanger Centre \n"+
		"CC   are available on the World Wide Web. \n"+
		"CC   (URL; http://www.sanger.ac.uk/Projects/S_coelicolor/)\n"+
		"CC\n"+
		"CC   CDS are numbered using the following system eg SC7B7.01c. \n"+
		"CC   SC (S. coelicolor), 7B7 (cosmid name), .01 (first CDS), \n"+
		"CC   c (complementary strand).\n"+
		"CC\n"+
		"CC   The more significant matches with motifs in the PROSITE\n"+
		"CC   database are also included but some of these may be fortuitous.\n"+
		"CC\n"+
		"CC   The length in codons is given for each CDS.\n"+
		"CC\n"+
		"CC   Usually the highest scoring match found by fasta -o is given for\n"+
		"CC   CDS which show significant similarity to other CDS in the database.\n"+
		"CC   The position of possible ribosome binding site sequences are\n"+
		"CC   given where these have been used to deduce the initiation codon.\n"+
		"CC   \n"+
		"CC   Gene prediction is based on positional base preference in codons \n"+
		"CC   using a specially developed Hidden Markov Model (Krogh et al., \n"+
		"CC   Nucleic Acids Research, 22(22):4768-4778(1994)) and the FramePlot \n"+
		"CC   program of Bibb et al., Gene 30:157-66(1984) as implemented at \n"+
		"CC   http://www.nih.go.jp/~jun/cgi-bin/frameplot.pl. CAUTION:  We may  \n"+
		"CC   not have predicted the correct initiation codon.  Where possible \n"+
		"CC   we choose an initiation codon (atg, gtg, ttg or (att)) which is \n"+
		"CC   preceded by an upstream ribosome binding site sequence (optimally \n"+
		"CC   5-13bp before the initiation codon).  If this cannot be identified\n"+
		"CC   we choose the most upstream initiation codon.\n"+
		"CC     \n"+
		"CC   IMPORTANT: This sequence MAY NOT be the entire insert of\n"+
		"CC   the sequenced clone.  It may be shorter because we only\n"+
		"CC   sequence overlapping sections once, or longer, because we\n"+
		"CC   arrange for a small overlap between neighbouring submissions.\n"+
		"CC\n"+
		"CC   Cosmid 10H5 lies to the right of 3A7 on the AseI-B genomic restriction \n"+
		"CC   fragment.\n"+
		"XX\n"+
		"FH   Key             Location/Qualifiers\n"+
		"FH\n"+
		"FT   source          1..4870\n"+
		"FT                   /organism=\"Streptomyces coelicolor\"\n"+
		"FT                   /strain=\"A3(2)\"\n"+
		"FT                   /clone=\"cosmid 10H5\"\n"+
		"FT   CDS             complement(<1..327)\n"+
		"FT                   /note=\"SC10H5.01c, unknown, partial CDS, len >109 aa;\n"+
		"FT                   possible integral membrane protein\"\n"+
		"FT                   /gene=\"SC10H5.01c\"\n"+
		"FT                   /product=\"hypothetical protein SC10H5.01c\"\n"+
		"FT   CDS             complement(350..805)\n"+
		"FT                   /note=\"SC10H5.02c, probable integral membrane protein, len:\n"+
		"FT                   151 aa; similar to S. coelicolor hypothetical protein\n"+
		"FT                   TR:O54194 (EMBL:AL021411) SC7H1.35 (155 aa), fasta scores;\n"+
		"FT                   opt: 431 z-score: 749.8 E(): 0, 53.5% identity in 114 aa\n"+
		"FT                   overlap.\"\n"+
		"FT                   /product=\"putative integral membrane protein\"\n"+
		"FT                   /gene=\"SC10H5.02c\"\n"+
		"FT   RBS             complement(812..815)\n"+
		"FT                   /note=\"possible RBS upstream of SC10H5.02c\"\n"+
		"FT   CDS             complement(837..1301)\n"+
		"FT                   /note=\"SC10H5.03c, probable integral membrane protein, len:\n"+
		"FT                   154 aa\"\n"+
		"FT                   /product=\"putative integral membrane protein\"\n"+
		"FT                   /gene=\"SC10H5.03c\"\n"+
		"FT   RBS             complement(1308..1312)\n"+
		"FT                   /note=\"possible RBS upstream of SC10H5.03c\"\n"+
		"FT   CDS             complement(1427..1735)\n"+
		"FT                   /note=\"SC10H5.04c, unknown, len: 103 aa; possible membrane\"\n"+
		"FT                   /gene=\"SC10H5.04c\"\n"+
		"FT                   /product=\"hypothetical protein SC10H5.04c\"\n"+
		"FT   RBS             complement(1738..1741)\n"+
		"FT                   /note=\"possible RBS upstream of SC10H5.05c\"\n"+
		"FT   misc_feature    1800^1801\n"+
		"FT                   /note=\"Zero-length feature added to test Bioperl parsing\"\n"+
		"FT   CDS             1933..2022\n"+
		"FT                   /note=\"SC10H5.05, questionable ORF, len: 29 aa\"\n"+
		"FT                   /gene=\"SC10H5.05\"\n"+
		"FT                   /product=\"hypothetical protein SC10H5.05\"\n"+
		"FT   CDS             2019..2642\n"+
		"FT                   /note=\"SC10H5.06, probable membrane protein, len: 207 aa;\n"+
		"FT                   similar to S. coelicolor TR:O54192 SC7H1.33c (191 aa),\n"+
		"FT                   fasta scores; opt: 312 z-score: 355.2 E(): 1.6e-12, 36.8%\n"+
		"FT                   identity in 182 aa overlap\"\n"+
		"FT                   /product=\"putative membrane protein\"\n"+
		"FT                   /gene=\"SC10H5.06\"\n"+
		"FT   RBS             2627..2631\n"+
		"FT                   /note=\"possible RBS upstream of SC10H5.07\"\n"+
		"FT   CDS             2639..4048\n"+
		"FT                   /note=\"SC10H5.07, unknown, len: 469 aa\"\n"+
		"FT                   /gene=\"SC10H5.07\"\n"+
		"FT                   /product=\"hypothetical protein SC10H5.07\"\n"+
		"FT   CDS             complement(4100..4297)\n"+
		"FT                   /note=\"SC10H5.08c, unknown, len: 65 aa\"\n"+
		"FT                   /gene=\"SC10H5.08c\"\n"+
		"FT                   /product=\"hypothetical protein SC10H5.08c\"\n"+
		"FT   RBS             complement(4314..4319)\n"+
		"FT                   /note=\"possible RBS upstream of SC10H5.08c\"\n"+
		"FT   CDS             complement(4439..>4870)\n"+
		"FT                   /note=\"SC10H5.09c, probable integral membrane protein,\n"+
		"FT                   partial CDS len: >143 aa; some similarity in C-terminus to\n"+
		"FT                   S. coelicolor hypothetical protein TR:O54106\n"+
		"FT                   (EMBL:AL021529) SC10A5.15 (114 aa), fasta scores; opt: 145\n"+
		"FT                   z-score: 233.8 E(): 9.2e-06, 33.3% identity in 81 aa\n"+
		"FT                   overlap. Overlaps and extends SC3A7.01c\"\n"+
		"FT                   /product=\"putative integral membrane protein\"\n"+
		"FT                   /gene=\"SC10H5.09c\"\n"+
		"FT   misc_feature    4769..4870\n"+
		"FT                   /note=\"overlap with cosmid 3A7 from 1 to 102\"\n"+
		"XX\n"+
		"SQ   Sequence 4870 BP; 769 A; 1717 C; 1693 G; 691 T; 0 other;\n"+
		"     gatcagtaga cccagcgaca gcagggcggg gcccagcagg ccggccgtgg cgtagagcgc        60\n"+
		"     gaggacggcg accggcgtgg ccaccgacag gatggctgcg gcgacgcgga cgacaccgga       120\n"+
		"     gtgtgccagg gcccaccaca cgccgatggc cgcgagcgcg agtcccgcgc tgccgaacag       180\n"+
		"     ggcccacagc acactgcgca gaccggcggc cacgagtggc gccaggacgg tgcccagcag       240\n"+
		"     gagcagcagg gtgacgtggg cgcgcgctgc actgtggccg ccccgtccgc ccgacgcgcg       300\n"+
		"     cggctcgtca tctcgcggtc ccaccaccgg tcggccccat tactcgtcct caaccctgtg       360\n"+
		"     gcgactgacg ttccccggac aggtcgtacc gattgccgcc acgccccacc acgcacaggg       420\n"+
		"     cccagacgac gaagcctgac atggtgatca tgacgacgga ccacaccggg tagtacggca       480\n"+
		"     gcgagaggaa gttggcgatg atcaccagcc cggcgatggc gaccccggtg acacgtgccc       540\n"+
		"     acatcgccgt tttgagcagc ccggcgctga cgaccatggc gagcgcgccg agcgcgagat       600\n"+
		"     ggatccaccc ccacccggtg agatcgaact ggaaaacgta gttgggcgtg gtgacgaaga       660\n"+
		"     cgtcgtcctc ggcgatggcc atgatgcccc ggaagaggct gagcagcccg gcgaggaaga       720\n"+
		"     gcatcaccgc cgcgaaggcg gtaaggcccg tcgcccattc ctgcctcgcg gtgtgtgccg       780\n"+
		"     ggtggtgggt atgtgacgtg gtcatctcgg acctcgtttc gtggaatgcg gatgcttcag       840\n"+
		"     cgagcggagg cgccggtgcc cgccgcgccc gtgtgccctg ccgggccgtg accggacagg       900\n"+
		"     accaattcct tcgccttgcg gaactcctcg tccgtgatgg caccccggtc tcggatctcg       960\n"+
		"     gagagccggg ccagctcgtc gacgctgctg gacccgccgc ccacggtctt cctgatgtag      1020\n"+
		"     gcgtcgaact cctcctgctg agcccgtgcc cgcgttgtct cccggctgcc catgttcttg      1080\n"+
		"     ccgcgagcga tcacgtagac gaaaacgccc aggaagggca ggaggatgca gaacaccaac      1140\n"+
		"     cagccggcct tcgcccagcc actcagtccg tcgtcccgga agatgtcggt gacgacgcgg      1200\n"+
		"     aagagcagga cgaaccacat gatccacagg aagatcatca gcatcgtcca gaaggcaccc      1260\n"+
		"     agcagtgggt agtcgtacgc caggtaggtc tgtgcactca tgtccgtcct ccgtcctccg      1320\n"+
		"     gggcgcggcc cggcggccct cgttccgtac tgacatcagg gtggtcacgg gtcccaccgg      1380\n"+
		"     tcggcatcac ccggcacggg tgagtggggc gccgaggccg tcgtggtcag gcccgggaca      1440\n"+
		"     ccggtgtgac cctggtggaa ggacgcgtcc cgtggggcac gcaccgccgg ccgagggcga      1500\n"+
		"     ccaccgcctc ggtcagtccg agcaggccca gccacaggcc gagaagtcgg gtcagggcac      1560\n"+
		"     gggccgactc ggcgggcagc gcgaggacga cgattccggc gacgtcgacg gccagcgggt      1620\n"+
		"     tgcgcaggcc cagcactccg gccggggcgc ccggcaccag cgtggcgagg gccgatgcca      1680\n"+
		"     tgagccaggt ccaggaaccc ccaagcctgg cgaggacgtg cgccggatcg ctcaatgctc      1740\n"+
		"     cggtgaccgc cccgcccgac ccgtctccct tgtcggcagg ttccgccgca tcacgcggaa      1800\n"+
		"     cggagatggc tcccctgtgg atcgggcggc cgctgcgggg ccgcccggtt ggtcggtcgg      1860\n"+
		"     tgagcgccgg actccccctt cagctcttcc agggtcgggg tcgacaccga ggtcctggat      1920\n"+
		"     cacccgtcag gggtgatccg ggcatgccgt cgtggcggtg aggtgggata cgggaacgat      1980\n"+
		"     cggcccacgg gggaccggac gagacgaaga gacgtgagat gagcgatacg aactcgggcg      2040\n"+
		"     gcgggcgcca ggccgcttcc ggaccggccc cacgtggccg actccctttc cgccggcgcg      2100\n"+
		"     tggccctggt cgctgtcgca cgtcccctga tcgtcacggt cggtctcgtc accgcctact      2160\n"+
		"     acctgcttcc cctggacgag agactcagcg ccggcaccct ggtgtcgctg gtgtgcggac      2220\n"+
		"     tgctcgcagt ccttctggtg ttctgctggg aggtgcgggc catcacgcgc tccccgcatc      2280\n"+
		"     cgcgtctgag agcgatcgag ggcctggccg ccacgctggt gctgttcctg gtcctcttcg      2340\n"+
		"     ccggctccta ctacctgctg ggtcgctccg cgcccggctc cttcagcgag ccgctgaaca      2400\n"+
		"     ggacggacgc gctgtacttc actctgacca cgttcgccac cgtcggcttc ggggacatca      2460\n"+
		"     ccgcacgctc cgagaccggg cggatcctca cgatggcgca gatgacggga gggctactgc      2520\n"+
		"     tcgtcggagt cgccgcccgg gtgctggcga gcgcagtgca ggcggggctg caccgacagg      2580\n"+
		"     gccggggacc ggcggcatcg ccacgctccg gtgctgcgga ggagccggag gccggaccat      2640\n"+
		"     gaccgtaccc ggtggcttca ccgcctccct gccgccggcc gagcgagccg cgtacggcag      2700\n"+
		"     gaaggcccgt aaaagggcct cacgttcgtg ccacggctgg tacgagccgg ggcagcggcg      2760\n"+
		"     gcctgacccc gtcgacctgc tggagcgcca gtccggcgag cgtgtcccgg cactcgtgcc      2820\n"+
		"     catccgctac ggtcgcatgc tggagtcgcc gttccgcttc taccgcggtg cggcagcgat      2880\n"+
		"     catggcggcg gacctggcac ccctgcccag cagcggactc caggtgcaat tgtgcgggga      2940\n"+
		"     cgcgcacccg ttgaacttcc ggctcctggc ctcaccggag cgccggctgg tcttcgacat      3000\n"+
		"     caacgacttc gacgagacgc tgcccggccc cttcgagtgg gacgtcaaac ggctggcggc      3060\n"+
		"     cggattcgtg atcgcggccc ggtcgaacgg cttctcgtcc aaggaacaga accgcaccgt      3120\n"+
		"     tcgggcctgt gtgcgggcct accgggagcg catgagggag ttcgccgtca tgccgaccct      3180\n"+
		"     ggacatctgg tacgcccagg acgacgccga ccacgtacgg caactgctgg ctacggaggc      3240\n"+
		"     cagaggagaa gctgagcagc ggctcaggga cgcggctgcg aaggcccgca cacgcaccca      3300\n"+
		"     catgagggcg ttcgcgaagc tcacccgcgt cacggccgag ggccggcgca tcacccccga      3360\n"+
		"     cccgccgctg atcaccccac tcggcgatct gctcaccgac ccggccgaag ccggccggga      3420\n"+
		"     ggaggaactg cggtccgtcg tgaacggcta cgcacggtcc ctgccgcccg agcgccggca      3480\n"+
		"     cctgctgcgt cactaccggc ttgtggacat ggcgcgcaag gtggtcggcg tcggcagtgt      3540\n"+
		"     cggcacccgc tgctgggtac tgcttctgct cggcagggac gacgacgatc ctctgctgct      3600\n"+
		"     ccaggccaag gaagcctcgg aatcggtgct ggcggcccac acgggcggcg aacgctacga      3660\n"+
		"     ccatcagggc cgcagggtcg tggccggcca gcgtctgatc cagaccaccg gtgacatctt      3720\n"+
		"     tctcggctgg gcgcgcgtca ccggcttcga cggaaaggcc cgggacttct acgtgcgtca      3780\n"+
		"     actgtgggac tggaagggcg tcgcgcggcc ggaaaccatg gggcccgacc tgctctccct      3840\n"+
		"     cttcgcccgg ctgtgcggtg cctgcctggc gagggcccac gcccgttccg gtgaccccgt      3900\n"+
		"     cgcgctcgcc gcgtacctgg gcggcagcga ccgcttcgac ggcgcgctca ccgagttcgc      3960\n"+
		"     ccagtcctac gccgatcaga atgaacgcga ccacgaagct ctgctggcgg cctgccgctc      4020\n"+
		"     cggcagggtc acggccgccc gtttgtgagg ccgacccggg aacggccggc gggctggcac      4080\n"+
		"     acaccgccgc cggtcggcgt cattccggaa gctgccgcat ctccaggacg cgcaggccca      4140\n"+
		"     gcgactggca gcgggtgagc aacccgtaca gatgggcctc gtcgatcacc gtgccgaaca      4200\n"+
		"     gcacggtctg gccggacatg acgacgtgct ccagctccgg gaacgcgttg gccagcgtcc      4260\n"+
		"     gtgacaggtg tccctcgacg cggatctcgt agcgcacgag cggtcctttc accgtaggag      4320\n"+
		"     ctcgggacac cgcccggggc tccgggtcgg acggtgctct tggtgacgag cctgcgcctc      4380\n"+
		"     gtcgccctcc ggtgccctca cccagcacag gtgactccaa ccgcagtgtc agtgcctttc      4440\n"+
		"     agtgcgtcac tgtgatcttg acgacgacga tcaccaggcc gagcagtacg ttgaccgtcg      4500\n"+
		"     cggtgacggc caccagtcgt cgcgaggcgc ccgcgcggtg cgccgcggcg acggaccagc      4560\n"+
		"     ccacctgacc ggcgacggcg acggacagcg ccagccacag ggtgcccggg acgtccagcc      4620\n"+
		"     ccagtacggg gctgacggcg atggccgcgg ccggaggcac ggcggccttg acgatcggcc      4680\n"+
		"     actcctcgcg gcacacacgc agaatcaccc gccggtccgg agtgtgccgc gcgagacgcg      4740\n"+
		"     ctccgaacag ttcggcgtgg acgtgagcga tccagaacac caagctggtg agcaacagca      4800\n"+
		"     gaagaaccag ttcggcgcgg gggaacgagc ccagggtgcc ggcgccgatc acgacggagg      4860\n"+
		"     ctgcgagcat                                                             4870\n"+
		"//";
	
	public static final String DATA02 = 
		"ID   X56734; SV 1; linear; mRNA; STD; PLN; 1859 BP.\n"+
		"XX\n"+
		"AC   X56734; S46826;\n"+
		"XX\n"+
		"DT   12-SEP-1991 (Rel. 29, Created)\n"+
		"DT   25-NOV-2005 (Rel. 85, Last updated, Version 11)\n"+
		"XX\n"+
		"DE   Trifolium repens mRNA for non-cyanogenic beta-glucosidase\n"+
		"XX\n"+
		"KW   beta-glucosidase.\n"+
		"XX\n"+
		"OS   Trifolium repens (white clover)\n"+
		"OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;\n"+
		"OC   Spermatophyta; Magnoliophyta; eudicotyledons; core eudicotyledons; rosids;\n"+
		"OC   eurosids I; Fabales; Fabaceae; Papilionoideae; Trifolieae; Trifolium.\n"+
		"XX\n"+
		"RN   [5]\n"+
		"RP   1-1859\n"+
		"RX   PUBMED; 1907511.\n"+
		"RA   Oxtoby E., Dunn M.A., Pancoro A., Hughes M.A.;\n"+
		"RT   \"Nucleotide and derived amino acid sequence of the cyanogenic\n"+
		"RT   beta-glucosidase (linamarase) from white clover (Trifolium repens L.)\";\n"+
		"RL   Plant Mol. Biol. 17(2):209-219(1991).\n"+
		"XX\n"+
		"RN   [6]\n"+
		"RP   1-1859\n"+
		"RA   Hughes M.A.;\n"+
		"RT   ;\n"+
		"RL   Submitted (19-NOV-1990) to the EMBL/GenBank/DDBJ databases.\n"+
		"RL   Hughes M.A., University of Newcastle Upon Tyne, Medical School, Newcastle\n"+
		"RL   Upon Tyne, NE2 4HH, UK\n"+
		"XX\n"+
		"FH   Key             Location/Qualifiers\n"+
		"FH\n"+
		"FT   source          1..1859\n"+
		"FT                   /organism=\"Trifolium repens\"\n"+
		"FT                   /mol_type=\"mRNA\"\n"+
		"FT                   /clone_lib=\"lambda gt10\"\n"+
		"FT                   /clone=\"TRE361\"\n"+
		"FT                   /tissue_type=\"leaves\"\n"+
		"FT                   /db_xref=\"taxon:3899\"\n"+
		"FT   CDS             14..1495\n"+
		"FT                   /product=\"beta-glucosidase\"\n"+
		"FT                   /EC_number=\"3.2.1.21\"\n"+
		"FT                   /note=\"non-cyanogenic\"\n"+
		"FT                   /db_xref=\"GOA:P26204\"\n"+
		"FT                   /db_xref=\"HSSP:P26205\"\n"+
		"FT                   /db_xref=\"InterPro:IPR001360\"\n"+
		"FT                   /db_xref=\"UniProtKB/Swiss-Prot:P26204\"\n"+
		"FT                   /protein_id=\"CAA40058.1\"\n"+
		"FT                   /translation=\"MDFIVAIFALFVISSFTITSTNAVEASTLLDIGNLSRSSFPRGFI\n"+
		"FT                   FGAGSSAYQFEGAVNEGGRGPSIWDTFTHKYPEKIRDGSNADITVDQYHRYKEDVGIMK\n"+
		"FT                   DQNMDSYRFSISWPRILPKGKLSGGINHEGIKYYNNLINELLANGIQPFVTLFHWDLPQ\n"+
		"FT                   VLEDEYGGFLNSGVINDFRDYTDLCFKEFGDRVRYWSTLNEPWVFSNSGYALGTNAPGR\n"+
		"FT                   CSASNVAKPGDSGTGPYIVTHNQILAHAEAVHVYKTKYQAYQKGKIGITLVSNWLMPLD\n"+
		"FT                   DNSIPDIKAAERSLDFQFGLFMEQLTTGDYSKSMRRIVKNRLPKFSKFESSLVNGSFDF\n"+
		"FT                   IGINYYSSSYISNAPSHGNAKPSYSTNPMTNISFEKHGIPLGPRAASIWIYVYPYMFIQ\n"+
		"FT                   EDFEIFCYILKINITILQFSITENGMNEFNDATLPVEEALLNTYRIDYYYRHLYYIRSA\n"+
		"FT                   IRAGSNVKGFYAWSFLDCNEWFAGFTVRFGLNFVD\"\n"+
		"FT   mRNA            1..1859\n"+
		"FT                   /experiment=\"experimental evidence, no additional details\n"+
		"FT                   recorded\"\n"+
		"XX\n"+
		"SQ   Sequence 1859 BP; 609 A; 314 C; 355 G; 581 T; 0 other;\n"+
		"     aaacaaacca aatatggatt ttattgtagc catatttgct ctgtttgtta ttagctcatt        60\n"+
		"     cacaattact tccacaaatg cagttgaagc ttctactctt cttgacatag gtaacctgag       120\n"+
		"     tcggagcagt tttcctcgtg gcttcatctt tggtgctgga tcttcagcat accaatttga       180\n"+
		"     aggtgcagta aacgaaggcg gtagaggacc aagtatttgg gataccttca cccataaata       240\n"+
		"     tccagaaaaa ataagggatg gaagcaatgc agacatcacg gttgaccaat atcaccgcta       300\n"+
		"     caaggaagat gttgggatta tgaaggatca aaatatggat tcgtatagat tctcaatctc       360\n"+
		"     ttggccaaga atactcccaa agggaaagtt gagcggaggc ataaatcacg aaggaatcaa       420\n"+
		"     atattacaac aaccttatca acgaactatt ggctaacggt atacaaccat ttgtaactct       480\n"+
		"     ttttcattgg gatcttcccc aagtcttaga agatgagtat ggtggtttct taaactccgg       540\n"+
		"     tgtaataaat gattttcgag actatacgga tctttgcttc aaggaatttg gagatagagt       600\n"+
		"     gaggtattgg agtactctaa atgagccatg ggtgtttagc aattctggat atgcactagg       660\n"+
		"     aacaaatgca ccaggtcgat gttcggcctc caacgtggcc aagcctggtg attctggaac       720\n"+
		"     aggaccttat atagttacac acaatcaaat tcttgctcat gcagaagctg tacatgtgta       780\n"+
		"     taagactaaa taccaggcat atcaaaaggg aaagataggc ataacgttgg tatctaactg       840\n"+
		"     gttaatgcca cttgatgata atagcatacc agatataaag gctgccgaga gatcacttga       900\n"+
		"     cttccaattt ggattgttta tggaacaatt aacaacagga gattattcta agagcatgcg       960\n"+
		"     gcgtatagtt aaaaaccgat tacctaagtt ctcaaaattc gaatcaagcc tagtgaatgg      1020\n"+
		"     ttcatttgat tttattggta taaactatta ctcttctagt tatattagca atgccccttc      1080\n"+
		"     acatggcaat gccaaaccca gttactcaac aaatcctatg accaatattt catttgaaaa      1140\n"+
		"     acatgggata cccttaggtc caagggctgc ttcaatttgg atatatgttt atccatatat      1200\n"+
		"     gtttatccaa gaggacttcg agatcttttg ttacatatta aaaataaata taacaatcct      1260\n"+
		"     gcaattttca atcactgaaa atggtatgaa tgaattcaac gatgcaacac ttccagtaga      1320\n"+
		"     agaagctctt ttgaatactt acagaattga ttactattac cgtcacttat actacattcg      1380\n"+
		"     ttctgcaatc agggctggct caaatgtgaa gggtttttac gcatggtcat ttttggactg      1440\n"+
		"     taatgaatgg tttgcaggct ttactgttcg ttttggatta aactttgtag attagaaaga      1500\n"+
		"     tggattaaaa aggtacccta agctttctgc ccaatggtac aagaactttc tcaaaagaaa      1560\n"+
		"     ctagctagta ttattaaaag aactttgtag tagattacag tacatcgttt gaagttgagt      1620\n"+
		"     tggtgcacct aattaaataa aagaggttac tcttaacata tttttaggcc attcgttgtg      1680\n"+
		"     aagttgttag gctgttattt ctattatact atgttgtagt aataagtgca ttgttgtacc      1740\n"+
		"     agaagctatg atcataacta taggttgatc cttcatgtat cagtttgatg ttgagaatac      1800\n"+
		"     tttgaattaa aagtcttttt ttattttttt aaaaaaaaaa aaaaaaaaaa aaaaaaaaa       1859\n"+
		"//";
	
	public static final String DATA03 = 
		"ID   BN000319; SV 1; linear; mRNA; STD; ROD; 912 BP.\n"+
		"XX\n"+
		"AC   BN000319;\n"+
		"XX\n"+
		"DT   27-APR-2004 (Rel. 79, Created)\n"+
		"DT   25-APR-2006 (Rel. 87, Last updated, Version 2)\n"+
		"XX\n"+
		"DE   TPA: Rattus norvegicus mRNA for HIV-induced protein-7-like protease (hshin7\n"+
		"DE   gene)\n"+
		"XX\n"+
		"KW   HIV-induced protein-7-like protease; hshin7 gene; Third Party Annotation;\n"+
		"KW   TPA; TPA:inferential.\n"+
		"XX\n"+
		"OS   Rattus norvegicus (Norway rat)\n"+
		"OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi; Mammalia;\n"+
		"OC   Eutheria; Euarchontoglires; Glires; Rodentia; Sciurognathi; Muroidea;\n"+
		"OC   Muridae; Murinae; Rattus.\n"+
		"XX\n"+
		"RN   [1]\n"+
		"RP   1-912\n"+
		"RA   Puente X.S.;\n"+
		"RT   ;\n"+
		"RL   Submitted (02-OCT-2003) to the INSDC.\n"+
		"RL   Puente X.S., Biomiquimica y Biologia Molecular-IUOPA, Universidad de\n"+
		"RL   Oviedo, Fernando Bongera, 6, Oviedo, 33006, SPAIN.\n"+
		"XX\n"+
		"RN   [2]\n"+
		"RX   DOI; 10.1101/gr.1946304.\n"+
		"RX   PUBMED; 15060002.\n"+
		"RA   Puente X.S., Lopez-Otin C.;\n"+
		"RT   \"A genomic analysis of rat proteases and protease inhibitors\";\n"+
		"RL   Genome Res. 14(4):609-622(2004).\n"+
		"XX\n"+
		"DR   Ensembl-Gn; ENSRNOG00000025704; Rattus_norvegicus.\n"+
		"DR   Ensembl-Tr; ENSRNOT00000030476; Rattus_norvegicus.\n"+
		"XX\n"+
		"AH   LOCAL_SPAN      PRIMARY_IDENTIFIER   PRIMARY_SPAN   COMP\n"+
		"AS   1-208           AC111727.4           81982-82189    c   \n"+
		"AS   209-912         AC111727.4           80472-81175    c   \n"+
		"XX\n"+
		"FH   Key             Location/Qualifiers\n"+
		"FH\n"+
		"FT   source          1..912\n"+
		"FT                   /organism=\"Rattus norvegicus\"\n"+
		"FT                   /chromosome=\"13\"\n"+
		"FT                   /map=\"13q13\"\n"+
		"FT                   /strain=\"Sprague-Dawley\"\n"+
		"FT                   /mol_type=\"mRNA\"\n"+
		"FT                   /db_xref=\"taxon:10116\"\n"+
		"FT   CDS             1..912\n"+
		"FT                   /gene=\"hshin7\"\n"+
		"FT                   /product=\"HIV-induced protein-7-like protease\"\n"+
		"FT                   /function=\"cystein protease\"\n"+
		"FT                   /db_xref=\"GOA:Q32Q05\"\n"+
		"FT                   /db_xref=\"InterPro:IPR003323\"\n"+
		"FT                   /db_xref=\"InterPro:IPR007087\"\n"+
		"FT                   /db_xref=\"InterPro:IPR015880\"\n"+
		"FT                   /db_xref=\"UniProtKB/Swiss-Prot:Q32Q05\"\n"+
		"FT                   /protein_id=\"CAE48374.1\"\n"+
		"FT                   /translation=\"MWRLRCKAKGGTHVLQGLSNRTRLRELQGQIAAITGIAPGSQRIL\n"+
		"FT                   VGYPPECLDLSDRDITLGDLPIQSGDMLIVEEDQTRPKASPAFSKHGAPSYVRETLPVL\n"+
		"FT                   TRTAVPADNSCLFTSVYYVVEGGVLNPACAPEMRRLIAQIVASDPDLYSEAILGKTNEE\n"+
		"FT                   YCDWIRRDDTWGGAIEISILSKFYQCEICVVDTQTVRIDRFGEDAGYTKRVLLIYDGIH\n"+
		"FT                   YDPLQRNFPDPDTPPLTIFSSNDDIVLVQALELADEARRKRQFTDVNRFTLRCMLCQKG\n"+
		"FT                   LTGQAEARDHARETGHTNFGEV\"\n"+
		"XX\n"+
		"SQ   Sequence 912 BP; 245 A; 227 C; 231 G; 209 T; 0 other;\n"+
		"     atgtggcggc tccgctgcaa ggccaagggt ggcacccacg ttttgcaggg tctttccaac        60\n"+
		"     cggacccgct tacgggaact gcagggccaa atcgccgcta tcaccggcat cgctcctggc       120\n"+
		"     agtcagcgaa tcctcgtcgg ctacccacca gagtgcctgg atctcagcga ccgggacatc       180\n"+
		"     actctcgggg acctgcccat ccagtcaggt gacatgctga ttgttgaaga agaccaaacc       240\n"+
		"     agaccaaaag cttcacctgc gttttcaaaa catggtgctc ctagttatgt cagggaaact       300\n"+
		"     ctgcctgtgc ttaccagaac cgcagtccca gcagacaact cttgcctctt taccagtgtg       360\n"+
		"     tactatgtag ttgaaggagg agtcttgaat ccagcttgtg cccctgagat gagacgcctc       420\n"+
		"     atagcacaaa ttgtagccag tgatccagac ttgtatagtg aggcaatact gggaaagaca       480\n"+
		"     aacgaagagt actgtgattg gatcagaagg gatgatactt gggggggagc aattgagata       540\n"+
		"     tcaatcctgt ccaagtttta tcaatgtgaa atatgtgtag tagatacaca gacagtcaga       600\n"+
		"     attgatcgtt ttggggaaga tgcaggctat accaaaaggg ttctactcat ctacgatggc       660\n"+
		"     attcactacg atccgcttca gcgaaacttc cctgatccgg atacccctcc tctgaccatt       720\n"+
		"     ttctcctcaa atgatgatat tgttctcgta caagcactgg aattagctga tgaagctaga       780\n"+
		"     agaaagagac agttcactga tgtaaaccgc ttcaccctga gatgcatgct gtgtcagaag       840\n"+
		"     ggcctaaccg gacaagctga agcaagggac catgccaggg agacaggcca taccaacttt       900\n"+
		"     ggagaggtgt ga                                                           912\n"+
		"//\n"+
		"";
}
