package gabywald.cellmodel.model;

import java.util.ArrayList;
import java.util.List;

import gabywald.global.structures.ObservableObject;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Cellule extends ObservableObject {
	private Noyau noyau;
	private Cytoplasme cytoplasme;
	private AppareilDeGolgi golgi;
	private List<Vesicule> transport;
	private Exocytose exterieur;
	private Membrane membrane;
	private Mitochondrie mitochondrie;
	
	private static int MAX_LIFE = 10000;
	private static int MAX_STOCK_LEVEL = 200;
	
	private static Cellule instance = null;
	
	private boolean threadSuspended;
	
	private Cellule() {
		this.noyau				= new Noyau();
		this.cytoplasme			= new Cytoplasme();
		this.golgi				= new AppareilDeGolgi();
		this.transport			= new ArrayList<Vesicule>();
		this.exterieur			= new Exocytose();
		this.membrane			= new Membrane();
		this.mitochondrie		= new Mitochondrie();
		this.threadSuspended	= false;
	}
	
	public static Cellule getCelluleObservable() {
		if (Cellule.instance == null) { Cellule.instance = new Cellule(); }
		return Cellule.instance;
	}
	
	public Noyau getNoyauObservable()				{ return this.noyau; }
	public Cytoplasme getCytoplasmeObservable()		{ return this.cytoplasme; }
	public AppareilDeGolgi getAppareilDeGolgiObservable() { return this.golgi; }
	public Exocytose getExocytose()					{ return this.exterieur; }
	public List<Vesicule> getTransport()			{ return this.transport; }
	public Membrane getMembrane()					{ return this.membrane; }
	public Mitochondrie getMitochondrieObservable()	{ return this.mitochondrie; }
	
	public void transfertARN() {
		ARN tmp = this.noyau.popARN();
		while(tmp != null) {
			this.cytoplasme.addARN(tmp);
			tmp = this.noyau.popARN();
		}
	}
	
	public void transfertProtein() {
		List<Vesicule> liste = this.cytoplasme.transport();
		for (int i = 0 ; i < liste.size() ; i++) 
			{ this.transport.add(liste.get(i)); }
		List<Vesicule> liste_bis = this.golgi.transport();
		for (int i = 0 ; i < liste_bis.size() ; i++) 
			{ this.transport.add(liste_bis.get(i)); }
		
	}
	
	public void deplaceVesicule() {
		if (this.transport.size() > Vesicule.VESICULE_LEVEL/2) {
			for (int i = 0 ; i < this.transport.size()/3 ; i++) {
				this.transport.get(0)
					.fusion(this.cytoplasme, this.golgi, this.exterieur, this.membrane);
				this.transport.remove(0);
			}
		}
	}
	
	
	public void run () {
		Thread noyauThread = new Thread(this.noyau);
		Thread cytoplasmeThread = new Thread(this.cytoplasme);
		Thread golgiThread = new Thread(this.golgi);
		Thread mitochThread = new Thread(this.mitochondrie);
		noyauThread.start();
		cytoplasmeThread.start();
		golgiThread.start();
		mitochThread.start();
		for (int i = 0 ; i < Cellule.MAX_LIFE ; i++) {
			this.setState("");
			this.transfertARN();
			this.transfertProtein();
			this.deplaceVesicule();
			/*
			if ( (this.transport.length() < 10) && (this.getPriority() > 1) )
				{ this.setPriority(this.getPriority()-1); }
			if ( (this.transport.length() > 20) && (this.getPriority() < 10) ) 
				{ this.setPriority(this.getPriority()+1); }
			 */
			if ( (this.getExocytose().length() > Cellule.MAX_STOCK_LEVEL) 
					&& (noyauThread.getPriority() > 1) )
				{ noyauThread.setPriority(noyauThread.getPriority()-1); }
			if ( (this.getExocytose().length() < Cellule.MAX_STOCK_LEVEL) 
					&& (noyauThread.getPriority() < 10) )
				{ noyauThread.setPriority(noyauThread.getPriority()+1); }
			if ( (this.noyau.length() < 10) && (noyauThread.getPriority() < 10) )
				{ noyauThread.setPriority(noyauThread.getPriority()+1); }
			if ( (this.noyau.length() > 50) && (noyauThread.getPriority() > 1) )
				{ noyauThread.setPriority(noyauThread.getPriority()-1); }
			
			if ( (this.cytoplasme.lengthARN() < 10) && (cytoplasmeThread.getPriority() > 1) )
				{ cytoplasmeThread.setPriority(cytoplasmeThread.getPriority()-1); }
			if ( (this.cytoplasme.lengthARN() > 50) && (cytoplasmeThread.getPriority() < 10) ) 
				{ cytoplasmeThread.setPriority(cytoplasmeThread.getPriority()+1); }
			
			
			if ( ( (this.mitochondrie.length_arn() < Mitochondrie.USURE_PROTEIQUE/10) 
					&& (this.mitochondrie.length_pro() < Mitochondrie.USURE_PROTEIQUE/10) ) 
					&& (mitochThread.getPriority() > 1) )
				{ mitochThread.setPriority(mitochThread.getPriority()-1); }
			if ( ( (this.mitochondrie.length_arn() > Mitochondrie.USURE_PROTEIQUE/10) 
					|| (this.mitochondrie.length_pro() > Mitochondrie.USURE_PROTEIQUE/10) ) 
					&& (mitochThread.getPriority() < 10) ) 
				{ mitochThread.setPriority(mitochThread.getPriority()+1); }
			
			if (this.getExocytose().length() > 2*(Cellule.MAX_STOCK_LEVEL) ) { 
				this.getExocytose().transport();
				this.addState("*****\n*****\n*****\n*****");
			}
			this.addState(" Cellule "
					+this.getTransport().size()+":"
					+this.getExocytose().length()+":"
					+this.getMembrane().length()+"\n\t"
					+this.noyau.getState()+"\n\t"
					+this.cytoplasme.getState()+"\n\t"
					+this.golgi.getState()+"\n\t"
					+this.mitochondrie.getState()+"\n\t"
					+"\n------------");
			try { 
				Thread.sleep(1000);
				synchronized(this) {
					while(this.threadSuspended) {
						this.wait();
						this.noyau.event(this.threadSuspended);
						this.cytoplasme.event(this.threadSuspended);
						this.golgi.event(this.threadSuspended);
						this.mitochondrie.event(this.threadSuspended);
					}
					this.notifyAll();
				}
			} 
			catch (InterruptedException e) { e.printStackTrace(); }
			this.change();
		}
		this.noyau.kill();
		this.cytoplasme.kill();
		this.golgi.kill();
		this.mitochondrie.kill();
	}
	
	public synchronized void event(boolean threadSuspended) {
		this.threadSuspended = !threadSuspended;
		System.out.println(this.threadSuspended+" <= C");
		// try {
			if (threadSuspended) { 
				this.notifyAll();
				// this.noyau.notify();
				/**this.noyau.event(this.threadSuspended);
				this.cytoplasme.event(this.threadSuspended);
				this.golgi.event(this.threadSuspended);
				this.mitochondrie.event(this.threadSuspended);*/
			} 
			// else { this.wait(); }
			// this.noyau.event(threadSuspended);
			// this.cytoplasme.event(threadSuspended);
			// this.golgi.event(threadSuspended);
			// this.mitochondrie.event(threadSuspended);
		// } catch (InterruptedException e) { e.printStackTrace(); }
		
	}
}
