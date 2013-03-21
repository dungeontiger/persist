package com.dungeontiger.Persist;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Class used to persist objects
 * @author gibsons
 *
 */
public class Persister {

	private PersisterStream stream;
	
	/**
	 * Constructor
	 */
	public Persister(PersisterStream theStream)
	{
		stream = theStream;
	}
	
	/**
	 * Persists an object
	 * @param obj object to persist
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void write(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		Class<? extends Object> objectClass = obj.getClass();

		// TODO: Need to set access control somewhere to be able to get values of private fields
		
		// start the object
		stream.startWriteObject(objectClass.getName(), obj.hashCode());
		
		// look at each declared field and persist the ones that are annotated
		Field[] fields = objectClass.getDeclaredFields();
		for (Field field : fields)
		{
			if (toPersist(field))
			{
				field.setAccessible(true);
				if (field.getType() == int.class)
				{
					stream.writeIntField(field.getName(), field.getInt(obj));
				}
				else if (field.getType() == String.class)
				{
					String value = (String)field.get(obj);
					stream.writeStringField(field.getName(), value);
				}
			}
		}
		
		// end the object
		stream.endWriteObject();
	}

	/**
	 * Reads an object, constructs and returns it
	 * @return object that was created
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public Object read() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException
	{
		return stream.readObject();
	}
	
	private boolean toPersist(Field field)
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
