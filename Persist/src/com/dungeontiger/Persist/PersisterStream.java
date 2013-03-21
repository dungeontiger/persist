package com.dungeontiger.Persist;

import java.lang.reflect.Field;

/**
 * Interface to stream persistant objects.  Concrete classes of this interface will
 * stream to whatever they like, XML, JSON or whatever.
 * @author gibsons
 *
 */
public interface PersisterStream {
	
	/**
	 * Start writing out an object
	 */
	public void startWriteObject(String className, int id);
	
	/**
	 * Indicates that an object is complete
	 */
	public void endWriteObject();
	
	/**
	 * Write out the int field and its value
	 * @param field 
	 */
	public void writeIntField(String fieldName, int value);
	
	/**
	 * Write out the String field and its value
	 * @param fieldName
	 * @param value
	 */
	public void writeStringField(String fieldName, String value);
	
	/**
	 * Reads an object from the stream,
	 * @return the object which was read
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public Object readObject() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException;

}
