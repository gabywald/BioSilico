package gabywald.biosilico.view.genetickit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gabywald.biosilico.structures.Pathway;

/**
 * ActionListener for GeneticKitJFrame's instance. 
 * @author Gabriel Chandesris (2022)
 * @see GeneticKitJFrame
 */
public class GeneticKitActionListener implements ActionListener {
	
	private GeneticKitInterface localKit = null;
	
	public GeneticKitActionListener(GeneticKitInterface frame) 
		{ this.localKit = frame; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source.equals(this.localKit.getGeneTypeSelection())) {
			int type = this.localKit.getGeneTypeSelection().getSelectedIndex();
			this.localKit.getParameterViewer().selectCard(type);
			if (type != 0) {
				// this.sendMessage(this.localKit.getGeneTypeSelection().getSelectedIndex()+" G");
				this.localKit.getAddSavGene().setEnabled(false);
				// this.localKit.getAddSavGene().setEnabled(true);
				this.localKit.getAddPathway().setEnabled(false);
				this.localKit.getPathSelection().setSelection(0);
				this.localKit.getGeneSelection().setSelection(0);
			} else { 
				this.localKit.getAddSavGene().setEnabled(false);
				this.localKit.getPathSelection().setSelection(0);
				this.localKit.getGeneSelection().setSelection(0);
				this.localKit.getCreateGene().setEnabled(true);
				
			}
		} else if (source.equals(this.localKit.getPathSelection())) {
			// ***** Selection in menu of previous defined pathways. 
			// this.selectedPath = this.localKit.getPathSelection().getSelectedIndex();
			if (this.localKit.getPathSelection().getSelected() > 0) {
				// this.sendMessage(this.localKit.getPathSelection().getSelectedIndex()+" M");
				this.localKit.getAddPathway().setEnabled(true);
				this.localKit.getAddSavGene().setEnabled(false);
				this.localKit.getParameterViewer().selectCard(0);
				this.localKit.setGeneTypeSelection(0);
				this.localKit.getGeneSelection().setSelection(0);
			} else { 
				this.localKit.getAddPathway().setEnabled(false);
				this.localKit.setGeneTypeSelection(0);
				this.localKit.getGeneSelection().setSelection(0);
			}
		} else if (source.equals(this.localKit.getGeneSelection())) {
			// ***** Selection in menu of previous defined genes. 
			// this.selectedGene = this.localKit.getGeneSelection().getSelectedIndex();
			if (this.localKit.getGeneSelection().getSelected() > 0) {
				// System.out.println(this.localKit.getGeneSelection().getSelected() );
				int geneType = this.localKit.getGeneSelection().getSelectedType();
				this.localKit.getParameterViewer().selectCard(geneType);
				this.localKit.getParameterViewer().setCompiledParameters
							(this.localKit.getGeneSelection().getSelectedGene(),
							geneType);
				this.localKit.setGeneTypeSelection(geneType);
				this.localKit.getAddSavGene().setEnabled(true);
				this.localKit.getAddPathway().setEnabled(false);
				this.localKit.getPathSelection().setSelection(0);
			} else {
				this.localKit.getAddSavGene().setEnabled(false);
				this.localKit.getParameterViewer().selectCard(0);
				this.localKit.setGeneTypeSelection(0);
				this.localKit.getPathSelection().setSelection(0);
			}
		} else if (source.equals(this.localKit.getAddSavGene())) {
			// ***** Selection in menu of previous defined genes. 
			// this.selectedGene = this.existentSelection.getSelectedIndex();
			if (this.localKit.getGeneSelection().getSelected() > 0) {
				// this.sendMessage("selected "+this.selectedGene);
				this.localKit.getGeneScroll().addString(this.localKit.getGeneSelection().getSelectedGeneName());
				this.localKit.enableWesternPanel(true);
				this.localKit.enableSouthPanel(true);
			} else { ; }
		} else if (source.equals(this.localKit.getAddPathway())) {
			// ***** Selection in menu of previous defined pathways. 
			// this.selectedPath = this.localKit.getPathSelection().getSelectedIndex();
			if (this.localKit.getPathSelection().getSelected() > 0) {
				Pathway pathSelected = this.localKit.getPathSelection().getSelectedPathway();
				for (int i = 0 ;  i < pathSelected.length() ; i++) 
					{ this.localKit.getGeneScroll().addString(pathSelected.getGeneName(i)); }
				this.localKit.enableWesternPanel(true);
				this.localKit.enableSouthPanel(true);
			} else { ; }
		}
	}

}
