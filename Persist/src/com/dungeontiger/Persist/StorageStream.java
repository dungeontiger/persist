package com.dungeontiger.Persist;

import java.lang.reflect.Field;

/**
 * Interface for the stream that is used for storing Persistant class members
 * @author gibsons
 *
 */
public interface StorageStream {
	
	public void startObject(String className, String instanceName);
	public void endObject();
	
	public void writeField(Field field);

}
