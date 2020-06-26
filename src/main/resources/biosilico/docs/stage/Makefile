
## naming WITHOUT the extensions [.tex] or [.bib]
LATEXFILE=proposition_stage
BIBTEFILE=proposition_stage

## the local software...
CCLATEX=latex
CCBIBTE=bibtex
CCPDFTE=dvipdf
CCPSTEX=dvips





all : pdf

pdf : $(LATEXFILE).dvi
	$(CCPDFTE) $(LATEXFILE).dvi

ps : $(LATEXFILE).dvi
	$(CCPSTEX) $(LATEXFILE).dvi

clean : $(LATEXFILE).dvi $(LATEXFILE).log mrproper
	rm $(LATEXFILE).log
	rm $(LATEXFILE).dvi
	# rm $(LATEXFILE).pdf

mrproper : $(LATEXFILE).aux $(LATEXFILE).bbl $(LATEXFILE).blg
	rm $(LATEXFILE).aux
	rm $(LATEXFILE).bbl
	rm $(LATEXFILE).blg

$(LATEXFILE).dvi : $(LATEXFILE).tex $(BIBTEFILE).bib
	$(CCLATEX) $(LATEXFILE).tex
	$(CCBIBTE) $(LATEXFILE)
	$(CCLATEX) $(LATEXFILE).tex
	$(CCLATEX) $(LATEXFILE).tex
	$(CCLATEX) $(LATEXFILE).tex
