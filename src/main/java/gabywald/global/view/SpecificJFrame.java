package gabywald.global.view;

public class SpecificJFrame extends GenericJFrame {

	protected SpecificJFrame(String title) {
		super(title);
		this.add(SpecificNorthPanel.getInstance(), "North");
		this.add(SpecificSouthPanel.getInstance( false ), "South");
		SpecificNorthPanel.getInstance().setMainTitle(this.getTitle());
	}

}
