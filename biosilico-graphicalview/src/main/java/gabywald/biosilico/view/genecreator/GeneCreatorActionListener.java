package gabywald.biosilico.view.genecreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.view.GeneJPanel;

/**
 * ActionListener for GeneCreatorJFrame's instance. 
 * @author Gabriel Chandesris (2022)
 * @see GeneCreatorJFrame
 */
public class GeneCreatorActionListener implements ActionListener {
	
	private GeneCreatorInterface localKit = null;
	
	public GeneCreatorActionListener(GeneCreatorInterface frame) 
		{ this.localKit = frame; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source.equals(this.localKit.getGeneTypeSelection())) {

			// ***** Selection in Gene type menu. 
			int type = this.localKit.getGeneTypeSelection().getSelectedIndex();

			this.localKit.getParameterViewer().selectCard(type);
			// this.localKit.getParameterViewer().repaint();
			if (type > 0) { 
				((GeneJPanel<?>)this.localKit.getParameterViewer().getCard(type)).setDefaultValues();
				this.localKit.getGeneName().setText("");
				this.localKit.getCreateGene().setEnabled(true);
				this.localKit.getChangeGene().setEnabled(false);
				this.localKit.getMakeNeGene().setEnabled(false);
				this.localKit.getGeneSelection().setSelection(0);
			} else { 
				this.localKit.getGeneName().setText("");
				this.localKit.enableCenterPanel(false);
				this.localKit.getGeneSelection().setSelection(0);
			}
		} else if (source.equals(this.localKit.getGeneSelection())) {
			// ***** Selection in menu of previous defined genes. 
			if (this.localKit.getGeneSelection().getSelectedIndex() > 0) {
				this.localKit.getGeneName().setText(this.localKit.getGeneSelection().getSelectedGeneName());
				int geneType = this.localKit.getGeneSelection().getSelectedType();
				this.localKit.setGeneTypeSelection(geneType);
				this.localKit.getParameterViewer().selectCard(geneType);
				this.localKit.getParameterViewer().setCompiledParameters
										(this.localKit.getGeneSelection().getSelectedGene(),
										geneType);
				// setGeneTypeSelection(geneType);
				this.localKit.getCreateGene().setEnabled(false);
				this.localKit.getChangeGene().setEnabled(true);
				this.localKit.getMakeNeGene().setEnabled(true);
				this.localKit.getAddGene2Pathway().setEnabled(true);
			} else { 
				this.localKit.getGeneName().setText("");
				this.localKit.enableCenterPanel(false);
				this.localKit.getAddGene2Pathway().setEnabled(false);
				this.localKit.setGeneTypeSelection(0);
				this.localKit.getGeneSelection().setSelection(0);
				this.localKit.getParameterViewer().selectCard(0);
			}
		} else if ( (source.equals(this.localKit.getCreateGene())) 
				|| (source.equals(this.localKit.getMakeNeGene())) ) {
			if ( (!this.localKit.getGeneName().getText().equals("")) 
					&& (!this.localKit.getGeneName().getText()
							.equals(this.localKit.getGeneSelection().getLastGeneName())) ) {
				String oneMoreGene	= this.localKit.getParameterViewer().getCompiledParameters(
										this.localKit.getGeneName().getText(),
										this.localKit.getGeneTypeSelection().getSelectedIndex());
				this.localKit.getGeneSelection().addGene(oneMoreGene);

				this.localKit.getCreateGene().setEnabled(false);
				this.localKit.getChangeGene().setEnabled(true);
				this.localKit.getMakeNeGene().setEnabled(true);
			}
		} else if (source.equals(this.localKit.getChangeGene())) {
			String oneMoreGene	= this.localKit.getParameterViewer().getCompiledParameters(
									this.localKit.getGeneName().getText(),
									this.localKit.getGeneTypeSelection().getSelectedIndex());
			this.localKit.getGeneSelection().setCurrentGene(oneMoreGene);
		} else if (source.equals(this.localKit.getDeleteGene())) {
			int selectedGene = this.localKit.getBuildingGene().getSelectedIndex();
			this.localKit.getBuildingGene().removeCurrentSelection();
			this.localKit.getGeneSelection().remGene(selectedGene);
		} else if (source.equals(this.localKit.getAddGene2Pathway())) {
			// ******* Adding a Gene to current Pathway. 
			this.localKit.getBuildingPathway().addString(this.localKit.getGeneSelection().getSelectedGeneName());
			if (this.localKit.getBuildingPathway().length() > 1) 
				{ this.localKit.getCreatePathway().setEnabled(true); }
		} else if (source.equals(this.localKit.getCreatePathway())) {
			// ******* Creating+Recording current Pathway. 
			String pathwayName			= this.localKit.getPathwayName().getText();
			List<String> genesNamesList	= this.localKit.getBuildingPathway().getStringListe();
			if ( (!pathwayName.equals("")) && (genesNamesList.size() > 1) ) {
				// ******* Name ; number of genes ; \n[\tgene\n]+ 
				String compiledPathway = pathwayName + "\t" + genesNamesList.size();
				
				for (int i = 0 ; i < genesNamesList.size() ; i++) {
					String tmpGeneLine = this.localKit.getGeneSelection().getGeneString
											(genesNamesList.get(i));
					if (!tmpGeneLine.equals(""))
						{ compiledPathway += "\t"+tmpGeneLine; }
				}
				this.localKit.getPathSelection().addPathway(compiledPathway);
				this.localKit.getPathwayName().setText("");
				this.localKit.getBuildingPathway().setStringListe(new ArrayList<String>());
			}
		}
		this.localKit.getGeneSelection().initSelection();
		if (this.localKit.getBuildingGene() != null) 
			{ this.localKit.getBuildingGene().setStringListe(this.localKit.getGeneSelection().getGeneNames()); }
		
	}

}
