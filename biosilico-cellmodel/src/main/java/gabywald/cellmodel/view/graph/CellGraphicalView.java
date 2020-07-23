package gabywald.cellmodel.view.graph;

import gabywald.cellmodel.model.Cellule;
import gabywald.cellmodel.model.Vesicule;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Main Graphical view of modelization of a cell. 
 * <br>Design-Pattern <i>Singleton</i> 
 * @author Gabriel Chandesris (2009, 2020)
 */
@SuppressWarnings("serial")
public class CellGraphicalView extends JFrame implements Observer {
	/** To avoid Warning */
	// private static final long serialVersionUID = 1L; // TODO serialVersionUID
	/** Unique instance of this graphical view. */
	private static CellGraphicalView instance = null;
	/** Background of the view. */
	private ImagePanel background;
	/** Start / Stop button.  */
	private JButton start_stop;
	
	private JLabel vesicule_sta;
	private JLabel vesicule_gol;
	private JLabel noyau_arn;
	private JLabel cytop_arn;
	private JLabel cytop_ribo;
	private JLabel cytop_prot;
	private JLabel golgi_prot;
	private JLabel exocy_prot;
	private JLabel membr_prot;
	private JLabel mitoc_arn;
	private JLabel mitoc_ribo;
	private JLabel mitoc_prot;
	
	/** Default Constructor. */
	private CellGraphicalView() {
		this.background = ImagePanel.getImagePanel();
		/** this.foreground = new JPanel(); */
		
		this.vesicule_sta = new JLabel();
		this.vesicule_gol = new JLabel();
		this.noyau_arn = new JLabel();
		this.cytop_arn = new JLabel();
		this.cytop_ribo = new JLabel();
		this.cytop_prot = new JLabel();
		this.golgi_prot = new JLabel();
		this.exocy_prot = new JLabel();
		this.membr_prot = new JLabel();
		this.mitoc_arn = new JLabel();
		this.mitoc_ribo = new JLabel();
		this.mitoc_prot = new JLabel();
		
		this.vesicule_sta.setLayout(null);
		this.vesicule_gol.setLayout(null);
		this.noyau_arn.setLayout(null);
		this.cytop_arn.setLayout(null);
		this.cytop_ribo.setLayout(null);
		this.cytop_prot.setLayout(null);
		this.golgi_prot.setLayout(null);
		this.exocy_prot.setLayout(null);
		this.membr_prot.setLayout(null);
		this.mitoc_arn.setLayout(null);
		this.mitoc_ribo.setLayout(null);
		this.mitoc_prot.setLayout(null);
		
		this.vesicule_sta.setOpaque(true);
		this.vesicule_gol.setOpaque(true);
		this.noyau_arn.setOpaque(true);
		this.cytop_arn.setOpaque(true);
		this.cytop_ribo.setOpaque(true);
		this.cytop_prot.setOpaque(true);
		this.golgi_prot.setOpaque(true);
		this.exocy_prot.setOpaque(true);
		this.membr_prot.setOpaque(true);
		this.mitoc_arn.setOpaque(true);
		this.mitoc_ribo.setOpaque(true);
		this.mitoc_prot.setOpaque(true);
		
		/*
		this.vesicule_sta.setBackground(new Color(0,0,0,0));
		this.vesicule_gol.setBackground(new Color(0,0,0,0));
		this.noyau_arn.setBackground(new Color(0,0,0,0));
		this.cytop_arn.setBackground(new Color(0,0,0,0));
		this.cytop_ribo.setBackground(new Color(0,0,0,0));
		this.cytop_prot.setBackground(new Color(0,0,0,0));
		this.golgi_prot.setBackground(new Color(0,0,0,0));
		this.exocy_prot.setBackground(new Color(0,0,0,0));
		this.membr_prot.setBackground(new Color(0,0,0,0));
		*/
		
		this.vesicule_sta.setBounds(250,45,30,15);
		this.vesicule_gol.setBounds(400,275,30,15);
		this.noyau_arn.setBounds(125,100,30,15);
		this.cytop_arn.setBounds(205,170,30,15);
		this.cytop_ribo.setBounds(207,190,30,15);
		this.cytop_prot.setBounds(210,210,30,15);
		this.golgi_prot.setBounds(380,180,30,15);
		this.exocy_prot.setBounds(190,350,30,15);
		this.membr_prot.setBounds(440,320,30,15);
		this.mitoc_arn.setBounds(120,305,30,15);
		this.mitoc_ribo.setBounds(160,305,30,15);
		this.mitoc_prot.setBounds(200,305,30,15);
		
		this.vesicule_sta.setText("00000000");
		this.vesicule_gol.setText("00000000");
		this.noyau_arn.setText("00000000");
		this.cytop_arn.setText("00000000");
		this.cytop_ribo.setText("00000000");
		this.cytop_prot.setText("00000000");
		this.golgi_prot.setText("00000000");
		this.exocy_prot.setText("00000000");
		this.membr_prot.setText("00000000");
		this.mitoc_arn.setText("00000000");
		this.mitoc_ribo.setText("00000000");
		this.mitoc_prot.setText("00000000");
		
		/*
		this.foreground.setLayout(null);
		this.foreground.setBounds(0, 0,503,412);
		this.foreground.setOpaque(false);
		this.foreground.setBackground(new Color(0,0,0,0));
		this.foreground.add(this.vesicule_sta);
		this.foreground.add(this.vesicule_gol);
		this.foreground.add(this.noyau_arn);
		this.foreground.add(this.cytop_arn);
		this.foreground.add(this.cytop_ribo);
		this.foreground.add(this.cytop_prot);
		this.foreground.add(this.golgi_prot);
		this.foreground.add(this.exocy_prot);
		this.foreground.add(this.membr_prot);
		*/
		
		this.background.setLayout(null);
		this.background.setOpaque(true);
		this.background.add(this.vesicule_sta);
		this.background.add(this.vesicule_gol);
		this.background.add(this.noyau_arn);
		this.background.add(this.cytop_arn);
		this.background.add(this.cytop_ribo);
		this.background.add(this.cytop_prot);
		this.background.add(this.golgi_prot);
		this.background.add(this.exocy_prot);
		this.background.add(this.membr_prot);
		this.background.add(this.mitoc_arn);
		this.background.add(this.mitoc_ribo);
		this.background.add(this.mitoc_prot);
		
		/** JFrame treatment */
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.background,"Center");
		// this.getContentPane().add(this.foreground,"Center");
		this.start_stop = new JButton("Start");
		this.start_stop.setSize(500, 15);
		this.getContentPane().add(this.start_stop,"South");
		
		this.setTitle("Simulation Cellulaire avec Thread et Observ[able|er]");
		this.setSize(510, 445);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public JButton getJButtonStartStop() { return this.start_stop; }
	
	/**
	 * To get instance of the graphical view. 
	 * @return (CellGraphicalView)
	 */
	public static CellGraphicalView getCellGraphicalView() {
		if (CellGraphicalView.instance == null) 
			{ CellGraphicalView.instance = new CellGraphicalView(); }
		return CellGraphicalView.instance;
	}

	public void update(Observable arg0, Object arg1) {
		Cellule obj_change = (Cellule)arg0;
		int sta = 0;
		int glo = 0;
		List<Vesicule> liste = obj_change.getTransport();
		for (int i = 0 ; i < liste.size() ; i++) {
			if (liste.get(i).getType() == 1) { sta += liste.get(i).length(); }
			else { glo += liste.get(i).length(); }
		}
		this.vesicule_sta.setText(sta+"p");
		this.vesicule_gol.setText(glo+"p");
		this.noyau_arn.setText(obj_change.getNoyauObservable().length()+"a");
		this.cytop_arn.setText(obj_change.getCytoplasmeObservable().lengthARN()+"a");
		this.cytop_ribo.setText(obj_change.getCytoplasmeObservable().lengthRibosome()+"r");
		this.cytop_prot.setText(obj_change.getCytoplasmeObservable().lengthProtein()+"p");
		this.golgi_prot.setText(obj_change.getAppareilDeGolgiObservable().length()+"p");
		this.exocy_prot.setText(obj_change.getExocytose().length()+"p");
		this.membr_prot.setText(obj_change.getMembrane().length()+"p");
		this.mitoc_arn.setText(obj_change.getMitochondrieObservable().length_arn()+"a");
		this.mitoc_ribo.setText(obj_change.getMitochondrieObservable().length_rib()+"r");
		this.mitoc_prot.setText(obj_change.getMitochondrieObservable().length_pro()+"p");
	}

	/**
	 * To start the simulation. 
	 * @param args
	 */
	public static void main(String args[]) {
		CellGraphicalView view = CellGraphicalView.getCellGraphicalView();
		view.getJButtonStartStop().setEnabled(false);
		view.getJButtonStartStop().setText("autorun");
		Cellule cell = Cellule.getCelluleObservable();
		cell.addObserver(view);
		Thread cellThread = new Thread(cell);
		cellThread.start();
	}

}
