package gabywald.biosilico.anthill.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import gabywald.biosilico.interfaces.functionnals.INamedElement;
import gabywald.global.view.graph.GenericJScroll;

/**
 * For {@code AntHillGraphicalFrame} : "Generic JList / JScroll" with Named Elements (Agents, World...). 
 * @author Gabriel Chandesris (2022)
 * @param <T> (extends INamedElement)
 */
@SuppressWarnings("serial")
public class AntHillGraphicalJScroll<T extends INamedElement>	extends GenericJScroll 
																implements ActionListener {
	
	private List<T> currentList = null;
	
	public AntHillGraphicalJScroll(List<T> elements) {
		super(elements.stream()	.map( elt -> elt.getName() )
								.collect(Collectors.toList()) );
		this.currentList = elements;
		this.init();
	}
	
	private void init() {
		this.setSize(100, 150);
		this.setPreferredSize(new Dimension(100, 150));
	}
	
	public List<String> getListOfName() 
		{ return Collections.unmodifiableList( 
				this.currentList.stream().map( elt -> elt.getName() ).
				collect(Collectors.toList()) ); }
	
	public T getElement(String name) {
		for (T elt : this.currentList) {
			if (elt.getName().equals(name)) 
				{ return elt; }
		}
		return null;
	}
	
	public T getElement(int index) {
		return this.currentList.get( index );
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
