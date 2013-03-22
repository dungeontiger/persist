package com.dungeontiger.Persist;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * This class is used to store and retrieve objects that have Persistant class members
 * @author gibsons
 *
 */
public final class Storage {
	
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
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void write(Object object) throws StorageException, IllegalArgumentException, IllegalAccessException
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
	
	/*
	 * Writes out an object
	 */
	private void writeObject(Object object, String instanceName) throws StorageException, IllegalArgumentException, IllegalAccessException
	{
		// TODO need to add this object to the map
		Class<? extends Object> objectClass = object.getClass();

		stream.startObject(objectClass.getName(), instanceName);
		processFields(objectClass, object);
		stream.endObject();
	}
	
	/*
	 * Writes out a field, may call writeObject if the field contains a complex object
	 */
	private void writeField(Field field, Object parent) throws StorageException, IllegalArgumentException, IllegalAccessException
	{
		if (isSimpleType(field))
		{
			// just write the simple field to the stream
			stream.writeField(field, parent);
		}
		else if (isCollection(field))
		{
			// TODO: write collection
//			stream.startCollection(objectClass.getName(), instanceName);
//			processFields(objectClass, parent);
//			stream.endCollection();
		}
		else if (isObjectReference(field))
		{
			// TODO: check in object map
		}
		else if (isObject(field))
		{
			// call writeObject
			writeObject(field.get(parent), field.getName());
		}
		else
		{
			// should be impossible to get here
			throw new StorageException("Attempted to write unknown object.");
		}
	}
	
	/*
	 * Iterate over all contained fields.
	 */
	private void processFields(Class<? extends Object> objectClass, Object object) throws StorageException, IllegalArgumentException, IllegalAccessException
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
	
	/*
	 * Returns true if the field is a primative object or a captial type
	 */
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
			field.getType() == Float.class || 
			field.getType() == String.class)
		{
			return true;
		}
		return false;
	}
	/*
	 * Returns true if the field is a collection, basic array or one of the collection type
	 */
	private boolean isCollection(Object object)
	{
		return false;
	}
	
	/*
	 * Return true if this object is a reference to an existing one
	 */
	private boolean isObjectReference(Object object)
	{
		// check to see if an instance in the object map matches this one, if it does return true
		return false;
	}
	
	/*
	 * Returns true if this object is one that can be persisted, i.e., from a package we know?
	 */
	private boolean isObject(Object object)
	{
		return false;
	}
	
	/*
	 * Returns true if the field has the @Persistant annotation
	 */
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
