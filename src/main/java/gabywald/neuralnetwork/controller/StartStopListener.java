package gabywald.neuralnetwork.controller;

import gabywald.global.controller.BooleanActionListener;
import gabywald.neuralnetwork.view.version2.BrainFrame;
import java.awt.event.ActionEvent;

public class StartStopListener extends BooleanActionListener {
	public StartStopListener(boolean b) 
		{ super(b); }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (this.isAware)	/** start button */
			{ BrainFrame.getInstance().getBrain().begin(); }
		else 				/** stop button */
			{ BrainFrame.getInstance().getBrain().endin(); }

	}
}
