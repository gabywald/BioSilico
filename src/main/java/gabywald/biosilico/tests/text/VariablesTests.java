package gabywald.biosilico.tests.text;

import gabywald.biosilico.model.Chemicals;
import gabywald.global.structures.StringCoupleListe;
import gabywald.global.view.text.Terminal;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class VariablesTests {

	public static void main(String[] args) {
		// Variables tests = new Variables();
		StringCoupleListe res = Chemicals.getChemicalListe();
		Terminal.ecrireStringln(res.toString());
	}

}
