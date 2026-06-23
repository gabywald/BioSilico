SUBELTS=dad1.tex mum1.tex Gren.tex

all: $(SUBELTS)
	for f in $(SUBELTS); \
	do \
		@echo '$$f'; \
		pdflatex $$f; \
	done
	
alternate: 
	for f in *.tex; \
	do \
		@echo '$$f'; \
		pdflatex $$f; \
	done

clean: 
	rm *.aux *.log
