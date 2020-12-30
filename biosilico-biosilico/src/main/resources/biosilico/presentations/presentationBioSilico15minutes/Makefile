
## naming WITHOUT the extensions [.tex] or [.bib]
LATEXFILE=presentationBioSilico15minutes
BIBTEFILE=presentationBioSilico15minutes

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

clean : mrproper
	rm $(LATEXFILE).log
	rm $(LATEXFILE).nav
	rm $(LATEXFILE).out
	rm $(LATEXFILE).snm
	# rm $(LATEXFILE).lot
	# rm $(LATEXFILE).lof
	# rm $(LATEXFILE).dvi
	# rm $(LATEXFILE).pdf

mrproper : $(LATEXFILE).aux $(LATEXFILE).bbl $(LATEXFILE).blg $(LATEXFILE).toc
	rm $(LATEXFILE).aux
	rm $(LATEXFILE).toc
	rm $(LATEXFILE).bbl
	rm $(LATEXFILE).blg
	

$(LATEXFILE).dvi : $(LATEXFILE).tex # $(BIBTEFILE).bib
	$(CCLATEX) $(LATEXFILE).tex
	# $(CCBIBTE) $(BIBTEFILE)
	$(CCLATEX) $(LATEXFILE).tex
	$(CCLATEX) $(LATEXFILE).tex
	$(CCLATEX) $(LATEXFILE).tex

pdflatex : $(LATEXFILE).tex # $(BIBTEFILE).bib
	$(CCPDFLA) $(LATEXFILE).tex
	$(CCBIBTE) $(BIBTEFILE)
	$(CCPDFLA) $(LATEXFILE).tex
	$(CCPDFLA) $(LATEXFILE).tex
	$(CCPDFLA) $(LATEXFILE).tex

