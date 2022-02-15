package gabywald.biosilico.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * ActionListener for GeneCreatorJFrame's instance. 
 * @author Gabriel Chandesris (2022)
 * @see GeneCreatorJFrame
 */
public class GeneCreatorActionListener implements ActionListener {
	
	private GeneCreatorJFrame localFrame = null;
	
	public GeneCreatorActionListener(GeneCreatorJFrame frame) 
		{ this.localFrame = frame; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source.equals(this.localFrame.getGeneTypeSelection())) {

			// ***** Selection in Gene type menu. 
			int type = this.localFrame.getGeneTypeSelection().getSelectedIndex();

			this.localFrame.getParameterViewer().selectCard(type);
			if (type > 0) { 
				((GeneJPanel)this.localFrame.getParameterViewer().getCard(type)).setDefaultValues();
				this.localFrame.getGeneName().setText("");
				this.localFrame.getCreateGene().setEnabled(true);
				this.localFrame.getChangeGene().setEnabled(false);
				this.localFrame.getMakeNeGene().setEnabled(false);
				this.localFrame.getGeneSelection().setSelection(0);
			} else { 
				this.localFrame.getGeneName().setText("");
				this.localFrame.enableCenterPanel(false);
				this.localFrame.getGeneSelection().setSelection(0);
			}
		} else if (source.equals(this.localFrame.getGeneSelection())) {
			// ***** Selection in menu of previous defined genes. 
			if (this.localFrame.getGeneSelection().getSelected() > 0) {
				this.localFrame.getGeneName().setText(this.localFrame.getGeneSelection().getSelectedGeneName());
				int geneType = this.localFrame.getGeneSelection().getSelectedType();
				this.localFrame.setGeneTypeSelection(geneType);
				this.localFrame.getParameterViewer().selectCard(geneType);
				this.localFrame.getParameterViewer().setCompiledParameters
										(this.localFrame.getGeneSelection().getSelectedGene(),
										geneType);
				// setGeneTypeSelection(geneType);
				this.localFrame.getCreateGene().setEnabled(false);
				this.localFrame.getChangeGene().setEnabled(true);
				this.localFrame.getMakeNeGene().setEnabled(true);
				this.localFrame.getAddGene2Pathway().setEnabled(true);
			} else { 
				this.localFrame.getGeneName().setText("");
				this.localFrame.enableCenterPanel(false);
				this.localFrame.getAddGene2Pathway().setEnabled(false);
				this.localFrame.setGeneTypeSelection(0);
				this.localFrame.getGeneSelection().setSelection(0);
				this.localFrame.getParameterViewer().selectCard(0);
			}
		} else if ( (source.equals(this.localFrame.getCreateGene())) 
				|| (source.equals(this.localFrame.getMakeNeGene())) ) {
			if ( (!this.localFrame.getGeneName().getText().equals("")) 
					&& (!this.localFrame.getGeneName().getText()
							.equals(this.localFrame.getGeneSelection().getLastGeneName())) ) {
				String oneMoreGene	= this.localFrame.getParameterViewer().getCompiledParameters(
										this.localFrame.getGeneName().getText(),
										this.localFrame.getGeneTypeSelection().getSelectedIndex());
				this.localFrame.getGeneSelection().addGene(oneMoreGene);

				this.localFrame.getCreateGene().setEnabled(false);
				this.localFrame.getChangeGene().setEnabled(true);
				this.localFrame.getMakeNeGene().setEnabled(true);
			}
		} else if (source.equals(this.localFrame.getChangeGene())) {
			String oneMoreGene	= this.localFrame.getParameterViewer().getCompiledParameters(
									this.localFrame.getGeneName().getText(),
									this.localFrame.getGeneTypeSelection().getSelectedIndex());
			this.localFrame.getGeneSelection().setCurrentGene(oneMoreGene);
		} else if (source.equals(this.localFrame.getDeleteGene())) {
			int selectedGene = this.localFrame.getBuildingGene().getSelectedIndex();
			this.localFrame.getBuildingGene().removeCurrentSelection();
			this.localFrame.getGeneSelection().remGene(selectedGene);
		} else if (source.equals(this.localFrame.getAddGene2Pathway())) {
			// ******* Adding a Gene to current Pathway. 
			this.localFrame.getBuildingPathway().addString(this.localFrame.getGeneSelection().getSelectedGeneName());
			if (this.localFrame.getBuildingPathway().length() > 1) 
				{ this.localFrame.getCreatePathway().setEnabled(true); }
		} else if (source.equals(this.localFrame.getCreatePathway())) {
			// ******* Creating+Recording current Pathway. 
			String pathwayName			= this.localFrame.getPathwayName().getText();
			List<String> genesNamesList	= this.localFrame.getBuildingPathway().getStringListe();
			if ( (!pathwayName.equals("")) && (genesNamesList.size() > 1) ) {
				// ******* Name ; number of genes ; \n[\tgene\n]+ 
				String compiledPathway = pathwayName + "\t" + genesNamesList.size();
				
				for (int i = 0 ; i < genesNamesList.size() ; i++) {
					String tmpGeneLine = this.localFrame.getGeneSelection().getGeneString
											(genesNamesList.get(i));
					if (!tmpGeneLine.equals(""))
						{ compiledPathway += "\t"+tmpGeneLine; }
				}
				this.localFrame.getPathSelection().addPathway(compiledPathway);
				this.localFrame.getPathwayName().setText("");
				this.localFrame.getBuildingPathway().setStringListe(new ArrayList<String>());
			}
		}
		this.localFrame.getGeneSelection().initSelection();
		if (this.localFrame.getBuildingGene() != null) 
			{ this.localFrame.getBuildingGene().setStringListe(this.localFrame.getGeneSelection().getGeneNames()); }
		
	}

}
