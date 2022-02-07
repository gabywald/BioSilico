package gabywald.biosilico.anthill.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import gabywald.biosilico.interfaces.functionnals.INamedElement;
import gabywald.global.view.graph.GenericJScroll;

/**
 * 
 * @author Gabriel Chandesris (2022)
 * @param <T>
 */
@SuppressWarnings("serial")
public class AntHillSelectionJScroll<T extends INamedElement>	extends GenericJScroll 
																implements ActionListener {
	
	private List<T> currentList = null;
	
	public AntHillSelectionJScroll(List<T> elements) {
		super(elements.stream()	.map( elt -> elt.getName() )
								.collect(Collectors.toList()) );
		this.currentList = elements;
		this.init();
	}
	
	private void init() {
		this.setSize(50, 200);
		this.setPreferredSize(new Dimension(50, 200));
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// Object source = ae.getSource();
		
		// TODO content in AntHillSelectionJScroll List. 
	}
	
	@Override
	public void setEnabled(boolean b) {
		// TODO AntHillSelectionJScroll setEnabled
	}

}
