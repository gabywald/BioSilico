
## naming WITHOUT the extensions [.tex] or [.bib]
LATEXFILE=DNAthroughEyeOfaCoder
BIBTEFILE=DNAthroughEyeOfaCoder

## the local software...
CCPDFLA=pdflatex
CCLATEX=latex
CCBIBTE=bibtex
CCPDFTE=dvipdf
CCPSTEX=dvips

all : pdflatex

pdf : $(LATEXFILE).dvi
	$(CCPDFTE) $(LATEXFILE).dvi

ps : $(LATEXFILE).dvi
	$(CCPSTEX) $(LATEXFILE).dvi

clean : $(LATEXFILE).log mrproper
	rm $(LATEXFILE).log
	## rm $(LATEXFILE).lot
	## rm $(LATEXFILE).lof
	# rm $(LATEXFILE).dvi
	# rm $(LATEXFILE).pdf

mrproper : $(LATEXFILE).aux $(LATEXFILE).toc ## $(LATEXFILE).bbl $(LATEXFILE).blg 
	rm $(LATEXFILE).aux
	## rm $(LATEXFILE).bbl
	## rm $(LATEXFILE).blg
	rm $(LATEXFILE).toc

$(LATEXFILE).dvi : $(LATEXFILE).tex ## $(BIBTEFILE).bib
	$(CCLATEX) $(LATEXFILE).tex
	## $(CCBIBTE) $(LATEXFILE)
	## $(CCLATEX) $(LATEXFILE).tex
	$(CCLATEX) $(LATEXFILE).tex
	$(CCLATEX) $(LATEXFILE).tex

pdflatex : $(LATEXFILE).tex ## $(BIBTEFILE).bib
	$(CCPDFLA) $(LATEXFILE).tex
	## $(CCBIBTE) $(BIBTEFILE)
	## $(CCPDFLA) $(LATEXFILE).tex
	$(CCPDFLA) $(LATEXFILE).tex
	$(CCPDFLA) $(LATEXFILE).tex
