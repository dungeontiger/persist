package com.dungeontiger.Persist;

import java.lang.reflect.Field;

/**
 * Interface for the stream that is used for storing Persistant class members
 * @author gibsons
 *
 */
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
	 * Get a string representation of the stream
	 * @return
	 */
	public String toString();

}
