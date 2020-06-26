package gabywald.global.structures;

import java.util.Collections;
import java.util.HashMap;

/**
 * This class defines synchronized HashMap. Aim is to give a synchronized model. 
 * <br>Inheritant classes have to re-define {@linkplain ObjectHash#put(String, Object)} 
 * and {@linkplain ObjectHash#put(String, Object)} which associate String and Object. 
 * @author Gabriel Chandesris (2010)
 */
public abstract class ObjectHash extends HashMap {
	/** To Avoid a Warning. */
	// private static final long serialVersionUID = 601L;
	
	/** Default Constructor. */
	public ObjectHash() 
		{ super();ObjectHash.synchronize(this); }
	
	/**
	 * Constructor with initial capacity. 
	 * @param initialCapacity (int)
	 */
	public ObjectHash(int initialCapacity) 
		{ super(initialCapacity);ObjectHash.synchronize(this); }

	/**
	 * This static method to synchronize the ObjectHash. 
	 * @param oh (ObjectHash)
	 */
	private static void synchronize(ObjectHash oh) 
		{ /** oh = (ObjectHash)*/Collections.synchronizedMap(oh); }
	
//	/**
//	 * @deprecated Use {@linkplain ObjectHash#put(String, Object)} instead !
//	 */
//	public Object put(Object key, Object value) 
//		{ return super.put(key, value); }
//	
//	/**
//	 * @deprecated Use {@linkplain ObjectHash#get(String)} instead !
//	 */
//	public Object get(Object key) { return super.get(key); }
	
	/**
	 * This class must be inherited and modified to match specific 'Object' Class. 
	 * @param key (String)
	 * @param value (Object)
	 * @return (Object) previous object associated with the key-String. 
	 * @deprecated Re-define this method. 
	 */
	public Object put(String key, Object value) { return super.get(key); }
	
	/**
	 * This class must be inherited and modified to match specific 'Object' Class. 
	 * @param key (String)
	 * @return (Object) object associated with the key-String. 
	 * @deprecated Re-define this method. 
	 */
	// public Object get(String key) { return super.get(key); }
	
}
