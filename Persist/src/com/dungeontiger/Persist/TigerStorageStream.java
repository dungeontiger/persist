package com.dungeontiger.Persist;

import java.lang.reflect.Field;

public class TigerStorageStream implements StorageStream {

	private String stream;
	private static final String DEF_SEPARATOR = ",";
	private static final String INTERNAL_SEPARATOR = ":";
	private static final String OPEN_OBJ = "{";
	private static final String CLOSE_OBJ = "}";
	private static final String NULL = "null";
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String QUOTE = "\"";
	
	
	public TigerStorageStream()
	{
		stream = new String();
	}
	
	public TigerStorageStream(String str)
	{
		stream = str;
	}
	
	@Override
	public void startObject(String className, String instanceName) {
		// TODO: Do we need the internal separator if there is no object name
		stream += className;
		if (instanceName != null)
		{
			stream += INTERNAL_SEPARATOR + instanceName;
		}
		stream += OPEN_OBJ;
	}

	@Override
	public void endObject() {
		stream += CLOSE_OBJ;
	}

	@Override
	public void writeField(Field field, Object parent) throws IllegalArgumentException, IllegalAccessException {
	
		if (stream.endsWith("{") != true)
		{
			stream += DEF_SEPARATOR;
		}
		stream += field.getName() + INTERNAL_SEPARATOR;
		
		if (field.getType() == int.class)
		{
			 stream += field.getInt(parent);
		}
		else if (field.getType() == long.class)
		{		
			 stream += field.getLong(parent);
		}
		else if (field.getType() == double.class)
		{		
			 stream += field.getDouble(parent);
		}
		else if (field.getType() == float.class)
		{		
			 stream += field.getFloat(parent);
		}
		else if (field.getType() == boolean.class)
		{
			if (field.getBoolean(parent) == true)
			{
				stream += TRUE;
			}
			else
			{
				stream += FALSE;
			}
		}
		else if (field.getType() == Integer.class)
		{
			Integer value = (Integer)field.get(parent);
			if (value == null)
			{
				stream += NULL;
			}
			else
			{
				stream += value;
			}
		}
		else if (field.getType() == Double.class)
		{		
			Double value = (Double)field.get(parent);
			if (value == null)
			{
				stream += NULL;
			}
			else
			{
				stream += value;
			}
		}
		else if (field.getType() == Boolean.class)
		{		
			Boolean value = (Boolean)field.get(parent);
			if (value == null)
			{
				stream += NULL;
			}
			else
			{
				stream += value;
			}
		}
		else if (field.getType() == Long.class)
		{		
			Long value = (Long)field.get(parent);
			if (value == null)
			{
				stream += NULL;
			}
			else
			{
				stream += value;
			}
		}
		else if (field.getType() == Float.class) 
		{		
			Float value = (Float)field.get(parent);
			if (value == null)
			{
				stream += NULL;
			}
			else
			{
				stream += value;
			}
		}
		else if (field.getType() == String.class)
		{		
			String value = (String)field.get(parent);
			if (value == null)
			{
				stream += NULL;
			}
			else
			{
				stream += QUOTE + value + QUOTE;
			}
		}
	}
	
	@Override
	public Object read() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException
	{
		int index = stream.indexOf(OPEN_OBJ);
		String className = stream.substring(0, index);
		Class<?> theClass = Class.forName(className);
		Object obj = theClass.newInstance();
		
		int comma = stream.indexOf(DEF_SEPARATOR, index);
		String fieldString = stream.substring(index + 1, comma);
		setField(theClass, obj, fieldString);
		System.out.println(fieldString);
		return obj;
	}
	
	private void setField(Class<?> theClass, Object obj, String fieldString) throws SecurityException, NoSuchFieldException, NumberFormatException, IllegalArgumentException, IllegalAccessException
	{
		int index = fieldString.indexOf(INTERNAL_SEPARATOR);
		String fieldName = fieldString.substring(0, index);
		String value = fieldString.substring(index + 1);
		System.out.println(value);
		Field field = theClass.getDeclaredField(fieldName);
		field.setAccessible(true);
		if (field.getType() == int.class)
		{
			field.setInt(obj, Integer.parseInt(value));
		}
	}
	
	@Override
	public String toString() {
		return stream;
	}
}
