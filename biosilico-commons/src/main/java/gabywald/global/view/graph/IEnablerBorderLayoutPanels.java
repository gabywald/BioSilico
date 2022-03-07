package gabywald.global.view.graph;

/**
 * Interface for anbling some BorderLayout Eleemnts. 
 * @author Gabriel Chandesris (2022)
 */
public interface IEnablerBorderLayoutPanels {
	public abstract void enableWesternPanel(boolean b);
	public abstract void enableEasternPanel(boolean b);
	public abstract void enableNorthernPanel(boolean b);
	public abstract void enableSouthPanel(boolean b);
	public abstract void enableCenterPanel(boolean b);
}
