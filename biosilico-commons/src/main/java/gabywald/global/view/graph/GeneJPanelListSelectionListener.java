package gabywald.global.view.graph;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gabywald.utilities.logger.Logger.LoggerLevel;

import gabywald.utilities.logger.Logger;

/**
 * 
 * @author Gabriel Chandesris (2022)
 * @deprecated (Only for test / debug purposes)
 */
public class GeneJPanelListSelectionListener implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Logger.printlnLog(LoggerLevel.LL_WARNING, e.getFirstIndex() + "::" + e.getSource().toString());
	}

}
