package gabywald.biosilico.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gabywald.biosilico.structures.Pathway;

/**
 * ActionListener for GeneticKitJFrame's instance. 
 * @author Gabriel Chandesris (2022)
 * @see GeneticKitJFrame
 */
public class GeneticKitActionListener implements ActionListener {
	
	private GeneticKitJFrame localFrame = null;
	
	public GeneticKitActionListener(GeneticKitJFrame frame) 
		{ this.localFrame = frame; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source.equals(this.localFrame.getGeneTypeSelection())) {
			int type = this.localFrame.getGeneTypeSelection().getSelectedIndex();
			this.localFrame.getParameterViewer().selectCard(type);
			if (type != 0) {
				// this.sendMessage(this.localFrame.getGeneTypeSelection().getSelectedIndex()+" G");
				this.localFrame.getAddSavGene().setEnabled(false);
				// this.localFrame.getAddSavGene().setEnabled(true);
				this.localFrame.getAddPathway().setEnabled(false);
				this.localFrame.getPathSelection().setSelection(0);
				this.localFrame.getGeneSelection().setSelection(0);
			} else { 
				this.localFrame.getAddSavGene().setEnabled(false);
				this.localFrame.getPathSelection().setSelection(0);
				this.localFrame.getGeneSelection().setSelection(0);
			}
		} else if (source.equals(this.localFrame.getPathSelection())) {
			// ***** Selection in menu of previous defined pathways. 
			// this.selectedPath = this.localFrame.getPathSelection().getSelectedIndex();
			if (this.localFrame.getPathSelection().getSelected() > 0) {
				// this.sendMessage(this.localFrame.getPathSelection().getSelectedIndex()+" M");
				this.localFrame.getAddPathway().setEnabled(true);
				this.localFrame.getAddSavGene().setEnabled(false);
				this.localFrame.getParameterViewer().selectCard(0);
				this.localFrame.setGeneTypeSelection(0);
				this.localFrame.getGeneSelection().setSelection(0);
			} else { 
				this.localFrame.getAddPathway().setEnabled(false);
				this.localFrame.setGeneTypeSelection(0);
				this.localFrame.getGeneSelection().setSelection(0);
			}
		} else if (source.equals(this.localFrame.getGeneSelection())) {
			// ***** Selection in menu of previous defined genes. 
			// this.selectedGene = this.localFrame.getGeneSelection().getSelectedIndex();
			if (this.localFrame.getGeneSelection().getSelected() > 0) {
				// System.out.println(this.localFrame.getGeneSelection().getSelected() );
				int geneType = this.localFrame.getGeneSelection().getSelectedType();
				this.localFrame.getParameterViewer().selectCard(geneType);
				this.localFrame.getParameterViewer().setCompiledParameters
							(this.localFrame.getGeneSelection().getSelectedGene(),
							geneType);
				this.localFrame.setGeneTypeSelection(geneType);
				this.localFrame.getAddSavGene().setEnabled(true);
				this.localFrame.getAddPathway().setEnabled(false);
				this.localFrame.getPathSelection().setSelection(0);
			} else {
				this.localFrame.getAddSavGene().setEnabled(false);
				this.localFrame.getParameterViewer().selectCard(0);
				this.localFrame.setGeneTypeSelection(0);
				this.localFrame.getPathSelection().setSelection(0);
			}
		} else if (source.equals(this.localFrame.getAddSavGene())) {
			// ***** Selection in menu of previous defined genes. 
			// this.selectedGene = this.existentSelection.getSelectedIndex();
			if (this.localFrame.getGeneSelection().getSelected() > 0) {
				// this.sendMessage("selected "+this.selectedGene);
				this.localFrame.getGeneScroll().addString(this.localFrame.getGeneSelection().getSelectedGeneName());
				this.localFrame.enableWesternPanel(true);
				this.localFrame.enableSouthPanel(true);
			} else { ; }
		} else if (source.equals(this.localFrame.getAddPathway())) {
			// ***** Selection in menu of previous defined pathways. 
			// this.selectedPath = this.localFrame.getPathSelection().getSelectedIndex();
			if (this.localFrame.getPathSelection().getSelected() > 0) {
				Pathway pathSelected = this.localFrame.getPathSelection().getSelectedPathway();
				for (int i = 0 ;  i < pathSelected.length() ; i++) 
					{ this.localFrame.getGeneScroll().addString(pathSelected.getGeneName(i)); }
				this.localFrame.enableWesternPanel(true);
				this.localFrame.enableSouthPanel(true);
			} else { ; }
		}
	}

}
