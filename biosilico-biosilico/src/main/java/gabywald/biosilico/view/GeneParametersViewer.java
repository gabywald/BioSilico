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
 *  @author Gabriel Chandesris (2010, 2020)
 */
@SuppressWarnings("serial")
public class GeneParametersViewer extends CardJPanel {
	/** Unique instance of this view. */
	private static GeneParametersViewer instance = null;
	
	/** Default empty card then others. */
	private static final String DEFAULT_CARD	= "Choose a type of Gene";
	private static final String INITCON_CARD	= "InitialConcentration";
	private static final String BIOCHEM_CARD	= "BiochemicalReaction";
	private static final String BRAINGE_CARD	= "BrainGene";
	private static final String BRAINLO_CARD	= "BrainLobeGene";
	private static final String EMITTER_CARD	= "EmitterReceptor";
	private static final String STIMULU_CARD	= "StimulusDecision";
	private static final String INSTINC_CARD	= "Instinct";
	/** This is used by {@link GeneKitJFrame#GeneKitJFrame()} and inheritant classes. */
	public static final String geneTypeListe[] = {
		GeneParametersViewer.DEFAULT_CARD, GeneParametersViewer.INITCON_CARD, 
		GeneParametersViewer.BIOCHEM_CARD, GeneParametersViewer.BRAINGE_CARD, 
		GeneParametersViewer.BRAINLO_CARD, GeneParametersViewer.EMITTER_CARD, 
		GeneParametersViewer.STIMULU_CARD, GeneParametersViewer.INSTINC_CARD
	};
	/** List of Card JPanel's (GeneKitsGBJPanel). */
	private static final GeneKitsGBJPanel[] geneticParam = GeneParametersViewer.getLocalPanelList();
	
	/** Default Constructor. */
	private GeneParametersViewer() { 
		super(GeneParametersViewer.geneTypeListe, GeneParametersViewer.geneticParam);
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
	
	private static GeneKitsGBJPanel[] getLocalPanelList() {
		GeneKitsGBJPanel liste[] = new GeneKitsGBJPanel[GeneParametersViewer.geneTypeListe.length];
		// ***** TODO [??] Design Pattern [Abstract]?Factory 
		// ***** 0 has no fields, initialize it then start to 1 !!
		liste[0] = new GeneKitsGBJPanel();
		liste[1] = new InitConJPanel();
		liste[2] = new BiochemJPanel();
		liste[3] = new BrainGeJPanel();
		liste[4] = new BrainLoJPanel();
		liste[5] = new EmitterJPanel();
		liste[6] = new StimuluJPanel();
		liste[7] = new InstincJPanel();

		return liste;
	}
	
	public void setCompiledParameters(Gene gene, int type) {
		// ***** code ; name ;  type ; mutate ; duplicate ; delete ; active ; minimal age ; maximal age ; sex ; mutation rate ; others... 
		
		// TODO HERE replace int with an ENUM !!
		
		switch (type) {
		case(1): /** Initial Concentration */
			((InitConJPanel)this.getCard(type))
				.setPanelSpecificValueWith((InitialConcentration)gene);
			break;
		case(2): /** BiochemicalReaction */
			((BiochemJPanel)this.getCard(type))
				.setPanelSpecificValueWith((BiochemicalReaction)gene);
			break;
		case(3): /** Brain */
			((BrainGeJPanel)this.getCard(type))
				.setPanelSpecificValueWith((BrainGene)gene);
			break;
		case(4): /** Brain Lobe */
			((BrainLoJPanel)this.getCard(type))
				.setPanelSpecificValueWith((BrainLobeGene)gene);
			break;
		case(5): /** EmitterReceptor */
			((EmitterJPanel)this.getCard(type))
				.setPanelSpecificValueWith((EmitterReceptor)gene);
			break;
		case(6): /** StimulusDecision */
			((StimuluJPanel)this.getCard(type))
				.setPanelSpecificValueWith(((StimulusDecision)gene));
			break;
		case(7): /** Instinct */
			((InstincJPanel)this.getCard(type))
				.setPanelSpecificValueWith(((Instinct)gene));
			break;
		}
	}
	
	public String getCompiledParameters(String geneName, int geneType) {
		// ***** code ; name ;  type ; mutate ; duplicate ; delete ; active ; minimal age ; maximal age ; sex ; mutation rate ; others...

		// TODO HERE replace int with an ENUM !!
		
		boolean mutate = ((GeneJPanel)this.getCard(geneType)).getMutate();
		boolean duplic = ((GeneJPanel)this.getCard(geneType)).getDuplic();
		boolean delete = ((GeneJPanel)this.getCard(geneType)).getDelete();
		boolean activi = ((GeneJPanel)this.getCard(geneType)).getActivi();
		int minimalAge = ((GeneJPanel)this.getCard(geneType)).getAgeMin();
		int maximalAge = ((GeneJPanel)this.getCard(geneType)).getAgeMax();
		int sex		   = ((GeneJPanel)this.getCard(geneType)).getSex();
		int mutateRate = ((GeneJPanel)this.getCard(geneType)).getMutRat();
		
		String middleOfLine = geneName+"\t"+geneType+"\t";
		
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
			middleOfLine += (new Instinct(mutate,duplic,
					delete,activi,minimalAge,maximalAge,sex, mutateRate, 
					posxInput,posyInput,posxOutput,posyOutput,weightIN,variaIN,thresIN,
					check)).toString();
			break;
		}
		
		return middleOfLine;
	}
}
