package gabywald.biosilico.view;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.BrainLobeGene;
import gabywald.biosilico.genetics.EmitterReceptor;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.genetics.Instinct;
import gabywald.biosilico.genetics.StimulusDecision;
import gabywald.global.view.graph.CardJPanel;

/**
 * This kind of JPanel provides a Card Layout JPanel about Gene's and their parameters. 
 *  <br><i>Design-Pattern Singleton. </i>
 *  @author Gabriel Chandesris (2010, 2020, 2022)
 */
@SuppressWarnings("serial")
public class GeneParametersViewer extends CardJPanel {
	/** Unique instance of this view. */
	private static GeneParametersViewer instance = null;
	
	/** Default Constructor. */
	private GeneParametersViewer() { 
		super(GeneParametersViewerEnum.getTypeNames(), GeneParametersViewerEnum.getTypePanels());
	}
	
	/**
	 * To get the current instance of GeneJPanel chooser. 
	 * @return (GeneParametersViewer)
	 */
	public static GeneParametersViewer getInstance() {
		if (GeneParametersViewer.instance == null) 
			{ GeneParametersViewer.instance = new GeneParametersViewer(); }
		return GeneParametersViewer.instance;
	}
	
	public void setCompiledParameters(Gene gene, int type) {
		// ***** code ; name ;  type ; mutate ; duplicate ; delete ; active ; minimal age ; maximal age ; sex ; mutation rate ; others... 
		((GeneJPanel<?>)this.getCard(type)).setPanelSpecificValueWithGene(gene);
	}
	
	public String getCompiledParameters(String geneName, int geneType) {
		// ***** code ; name ;  type ; mutate ; duplicate ; delete ; active ; minimal age ; maximal age ; sex ; mutation rate ; others...

		// TODO HERE replace int with an ENUM !!
		
		boolean mutate = ((GeneJPanel<?>)this.getCard(geneType)).getMutate();
		boolean duplic = ((GeneJPanel<?>)this.getCard(geneType)).getDuplic();
		boolean delete = ((GeneJPanel<?>)this.getCard(geneType)).getDelete();
		boolean activi = ((GeneJPanel<?>)this.getCard(geneType)).getActivi();
		int minimalAge = ((GeneJPanel<?>)this.getCard(geneType)).getAgeMin();
		int maximalAge = ((GeneJPanel<?>)this.getCard(geneType)).getAgeMax();
		int sex		   = ((GeneJPanel<?>)this.getCard(geneType)).getSex();
		int mutateRate = ((GeneJPanel<?>)this.getCard(geneType)).getMutRat();
		
		String middleOfLine = geneName + "\t" + geneType + "\t";
		
		switch (geneType) {
		case(1): /** Initial Concentration */
			int varia = ((InitConJPanel)this.getCard(geneType)).getVariable();
			int value = ((InitConJPanel)this.getCard(geneType)).getValue();
			middleOfLine += (new InitialConcentration(mutate,duplic,
					delete,activi,minimalAge,maximalAge,sex, mutateRate, 
					varia, value)).toString();
			break;
		case(2): /** BiochemicalReaction */
			int Achem = ((BiochemJPanel)this.getCard(geneType)).getAchem();
			int Bchem = ((BiochemJPanel)this.getCard(geneType)).getBchem();
			int Cchem = ((BiochemJPanel)this.getCard(geneType)).getCchem();
			int Dchem = ((BiochemJPanel)this.getCard(geneType)).getDchem();
			int Acoef = ((BiochemJPanel)this.getCard(geneType)).getAcoef();
			int Bcoef = ((BiochemJPanel)this.getCard(geneType)).getBcoef();
			int Ccoef = ((BiochemJPanel)this.getCard(geneType)).getCcoef();
			int Dcoef = ((BiochemJPanel)this.getCard(geneType)).getDcoef();
			int KMVM  = ((BiochemJPanel)this.getCard(geneType)).getKMVMs();
			middleOfLine += (new BiochemicalReaction(mutate,duplic,
					delete,activi,minimalAge,maximalAge,sex, mutateRate, 
					Achem,Acoef,Bchem,Bcoef,Cchem,Ccoef,Dchem,Dcoef,
					KMVM)).toString();
			break;
		case(3): /** Brain */
			int height  = ((BrainGeJPanel)this.getCard(geneType)).getBrainHeight();
			int width	= ((BrainGeJPanel)this.getCard(geneType)).getBrainWidth();
			int depth	= ((BrainGeJPanel)this.getCard(geneType)).getBrainDepth();
			int more	= ((BrainGeJPanel)this.getCard(geneType)).getBrainMore();
			middleOfLine += (new BrainGene(mutate,duplic,
				delete,activi,minimalAge,maximalAge,sex, mutateRate, 
				height,width,depth,more)).toString();
			break;
		case(4): /** Brain Lobe */
			int rest			= ((BrainLoJPanel)this.getCard(geneType)).getRestState();
			int thre			= ((BrainLoJPanel)this.getCard(geneType)).getThreshold();
			int desc			= ((BrainLoJPanel)this.getCard(geneType)).getDescent();
			int dendriticmin	= ((BrainLoJPanel)this.getCard(geneType)).getDendritMin();
			int dendriticmax	= ((BrainLoJPanel)this.getCard(geneType)).getDendritMax();
			int prox			= ((BrainLoJPanel)this.getCard(geneType)).getProximity();
			boolean repr		= ((BrainLoJPanel)this.getCard(geneType)).getReproduce();
			int repy			= ((BrainLoJPanel)this.getCard(geneType)).getReproduct();
			boolean wta			= ((BrainLoJPanel)this.getCard(geneType)).getWTA();
			int heightl			= ((BrainLoJPanel)this.getCard(geneType)).getLobeHeight();
			int widthl			= ((BrainLoJPanel)this.getCard(geneType)).getLobeWidth();
			int posxBL			= ((BrainLoJPanel)this.getCard(geneType)).getLobePosX();
			int posyBL			= ((BrainLoJPanel)this.getCard(geneType)).getLobePosY();
			boolean replace		= ((BrainLoJPanel)this.getCard(geneType)).getReplace();
			middleOfLine += (new BrainLobeGene(mutate,duplic,
					delete,activi,minimalAge,maximalAge,sex, mutateRate, 
					rest,thre,desc,dendriticmin,dendriticmax,prox,repr,repy,
					wta,heightl,widthl,posxBL,posyBL,replace)).toString();
			break;
		case(5): /** EmitterReceptor */
			int var				= ((EmitterJPanel)this.getCard(geneType)).getVariable();
			int thr				= ((EmitterJPanel)this.getCard(geneType)).getThreshold();
			int put				= ((EmitterJPanel)this.getCard(geneType)).getIOnput();
			int posxER			= ((EmitterJPanel)this.getCard(geneType)).getPosXNeurone();
			int posyER			= ((EmitterJPanel)this.getCard(geneType)).getPosYNeurone();
			boolean receptor	= ((EmitterJPanel)this.getCard(geneType)).getReceptor();
			boolean internal	= ((EmitterJPanel)this.getCard(geneType)).getInternal();
			middleOfLine += (new EmitterReceptor(mutate,duplic,
					delete,activi,minimalAge,maximalAge,sex, mutateRate, 
					var,thr,put,posxER,posyER,receptor,internal)).toString();
			break;
		case(6): /** StimulusDecision */
			boolean perception	= ((StimuluJPanel)this.getCard(geneType)).getPerception();
			boolean object		= ((StimuluJPanel)this.getCard(geneType)).getObject();
			int indicator		= ((StimuluJPanel)this.getCard(geneType)).getIndicator();
			int threshold		= ((StimuluJPanel)this.getCard(geneType)).getThreshold();
			int attribute		= ((StimuluJPanel)this.getCard(geneType)).getAttribute();
			int variaSD			= ((StimuluJPanel)this.getCard(geneType)).getVariable();
			int valueSD			= ((StimuluJPanel)this.getCard(geneType)).getValue();
			int scripSD			= ((StimuluJPanel)this.getCard(geneType)).getScript();
			middleOfLine += (new StimulusDecision(mutate,duplic,
					delete,activi,minimalAge,maximalAge,sex, mutateRate, 
					perception,object,indicator,threshold,attribute,
					variaSD,valueSD,scripSD)).toString();
			break;
		case(7): /** Instinct */
			int posxInput	= ((InstincJPanel)this.getCard(geneType)).getPosXOrg();
			int posyInput	= ((InstincJPanel)this.getCard(geneType)).getPosYOrg();
			int posxOutput	= ((InstincJPanel)this.getCard(geneType)).getPosXDes();
			int posyOutput	= ((InstincJPanel)this.getCard(geneType)).getPosYDes();
			int weightIN	= ((InstincJPanel)this.getCard(geneType)).getWeight();
			int variaIN		= ((InstincJPanel)this.getCard(geneType)).getVariable();
			int thresIN		= ((InstincJPanel)this.getCard(geneType)).getThreshold();
			boolean check	= ((InstincJPanel)this.getCard(geneType)).getCheck();
			// TODO add a field / checkbox for positivity (default is true)
			middleOfLine += (new Instinct(
					mutate, duplic, delete, activi, minimalAge, maximalAge, sex, mutateRate, 
					posxInput, posyInput, posxOutput, posyOutput, weightIN, variaIN, thresIN, 
					check, true)).toString();
			break;
		}
		
		return middleOfLine;
	}
}
