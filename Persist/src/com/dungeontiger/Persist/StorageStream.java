package com.dungeontiger.Persist;

import java.lang.reflect.Field;

/**
 * Interface for the stream that is used for storing Persistant class members
 * @author gibsons
 *
 */

// TODO: Consider splitting into read and write interfaces...they should be one time use

public interface StorageStream {
	
	/**
	 * Begins writing out an object
	 * @param className
	 * @param instanceName
	 */
	public void startObject(String className, String instanceName);
	
	/**
	 * Close the current object defintion
	 */
	public void endObject();
	
	/**
	 * Write out a simple field
	 * @param field
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void writeField(Field field, Object parent) throws IllegalArgumentException, IllegalAccessException;
	
	/**
	 * Reads an object from the stream
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public Object read() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException;
	
	/**
	 * Get a string representation of the stream
	 * @return
	 */
	public String toString();

}
