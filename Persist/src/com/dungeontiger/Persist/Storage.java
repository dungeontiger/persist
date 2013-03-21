package com.dungeontiger.Persist;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * This class is used to store and retrieve objects that have Persistant class members
 * @author gibsons
 *
 */
public class Storage {
	
	private StorageStream stream;
	
	/**
	 * Constructor for the storage class.
	 * @param s the stream to use for object storage
	 */
	public Storage(StorageStream s)
	{
		stream = s;
	}
	
	/**
	 * Write the object to the stream
	 * @param object to write
	 * @throws StorageException 
	 */
	public void write(Object object) throws StorageException
	{
		writeObject(object, null);
	}
	
	/**
	 * Read an object from stream
	 * @return object that was read
	 */
	public Object read()
	{
		return null;
	}
	
	private void writeObject(Object object, String instanceName) throws StorageException
	{
		Class<? extends Object> objectClass = object.getClass();

		stream.startObject(objectClass.getName(), instanceName);
		processFields(objectClass, object);
		stream.endObject();
	}
	
	private void writeField(Field field, Object parent) throws StorageException
	{
		if (isSimpleType(field))
		{
			stream.writeField(field);
		}
		else if (isCollection(field))
		{
			stream.startCollection(objectClass.getName(), instanceName);
			processFields(objectClass, parent);
			stream.endCollection();
		}
		else if (isObjectReference(field))
		{
			
		}
		else if (isObject(field))
		{
			writeObject(field.get(parent), field.getName());
		}
		else
		{
			// should be impossible to get here
			throw new StorageException("Attempted to write unknown object.");
		}
	}
	
	private void processFields(Class<? extends Object> objectClass, Object object) throws StorageException
	{
		Field[] fields = objectClass.getDeclaredFields();
		for (Field field : fields)
		{
			if (isPersistant(field))
			{
				field.setAccessible(true);
				writeField(field, object);
			}
		}
	}
	
	private boolean isSimpleType(Field field)
	{
		if (field.getType() == int.class ||
			field.getType() == long.class ||
			field.getType() == double.class ||
			field.getType() == float.class ||
			field.getType() == boolean.class ||
			field.getType() == Integer.class ||
			field.getType() == Double.class ||
			field.getType() == Boolean.class ||
			field.getType() == Long.class ||
			field.getType() == Float.class)
		{
			return true;
		}
		return false;
	}
	
	private boolean isCollection(Object object)
	{
		return false;
	}
	
	private boolean isObjectReference(Object object)
	{
		// check to see if an instance in the object map matches this one, if it does return true
		return false;
	}
	
	private boolean isObject(Object object)
	{
		return false;
	}
	
	private boolean isPersistant(Field field)
	{
		// if has the @Persistant annotation return true
		Annotation[] annotations = field.getDeclaredAnnotations();
		for (Annotation annotation : annotations)
		{
			if (annotation instanceof Persistant)
			{
				return true;
			}
		}
		return false;
	}
}
