package gabywald.utilities.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Better Logger Class. 
 * @author Gabriel Chandesris (2018)
 */
public class BetterLogger {
	public static enum LOG_LEVEL { VERB_, DEBUG, INFO, WARN, ERROR, FATAL };
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	protected LOG_LEVEL loglvl;
	
	protected BetterLogger next;
	
	public BetterLogger(LOG_LEVEL level) {
		this.loglvl	= level;
		this.next	= null;
	}
	
	public BetterLogger setNext(BetterLogger bl) {
		this.next = bl;
		return this.next;
	}
	
	public void message(String msg, LOG_LEVEL priority) {
		if ( priority.ordinal() == this.loglvl.ordinal() ) {
			this.writeMessage( msg );
		} 
		if ( this.next != null ) {
			this.next.message( msg, priority );
		}
	}
	
	protected String generateDateDefault() {
		return this.generateDate(BetterLogger.DATE_FORMAT);
	}
	
	protected String generateDate(SimpleDateFormat sdf) {
		Date now = new Date();
		return sdf.format(now);
	}
	
	// protected abstract void writeMessage(String msg);
	protected void writeMessage(String msg) {
		System.out.println("[" + this.generateDateDefault() + "] " + this.loglvl.name() + " - " + msg);
	}
	
	public static BetterLogger getDefaultLogger() {
		// BetterLogger toReturn = new BetterLogger();
		LOG_LEVEL[] vals = BetterLogger.LOG_LEVEL.values();
		BetterLogger toReturn = new BetterLogger(vals[0]);
		BetterLogger toInit = toReturn;
		for (int i = 1 ; i < vals.length ; i++) {
			toInit = toInit.setNext(new BetterLogger(vals[i]));
		}
		return toReturn;
	}
	
	public static BetterLogger getLogger(LOG_LEVEL minimal) {
		LOG_LEVEL[] vals = BetterLogger.LOG_LEVEL.values();
		BetterLogger toReturn = new BetterLogger(vals[minimal.ordinal()]);
		BetterLogger toInit = toReturn;
		if (minimal.ordinal() < LOG_LEVEL.FATAL.ordinal()) {
			for (int i = minimal.ordinal() + 1 ; i < vals.length ; i++) {
				toInit = toInit.setNext(new BetterLogger(vals[i]));
			}
		} // END "if (minimal.ordinal() < LOG_LEVEL.FATAL.ordinal())"
		return toReturn;
	}
	
	public void verbose(String msg) {
		this.message(msg, LOG_LEVEL.VERB_);
	}
	
	public void debug(String msg) {
		this.message(msg, LOG_LEVEL.DEBUG);
	}
	
	public void info(String msg) {
		this.message(msg, LOG_LEVEL.INFO);
	}
	
	public void warn(String msg) {
		this.message(msg, LOG_LEVEL.WARN);
	}
	
	public void error(String msg) {
		this.message(msg, LOG_LEVEL.ERROR);
	}
	
	public void fatal(String msg) {
		this.message(msg, LOG_LEVEL.FATAL);
	}
}
