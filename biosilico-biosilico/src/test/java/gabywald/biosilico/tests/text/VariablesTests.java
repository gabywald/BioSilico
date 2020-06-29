package gabywald.biosilico.tests.text;

import java.util.List;

import gabywald.biosilico.model.Chemicals;
import gabywald.global.structures.StringCouple;
import gabywald.global.view.text.Terminal;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020)
 */
public class VariablesTests {

	public static void main(String[] args) {
		// Variables tests = new Variables();
		List<StringCouple> res = Chemicals.getChemicalListe();
		Terminal.ecrireStringln(res.toString());
	}

}
