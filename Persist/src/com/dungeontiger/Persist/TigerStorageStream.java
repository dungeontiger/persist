package com.dungeontiger.Persist;

import java.lang.reflect.Field;

public class TigerStorageStream implements StorageStream {

	private String stream = new String();
	private static final String DEF_SEPARATOR = ",";
	private static final String INTERNAL_SEPARATOR = ":";
	private static final String OPEN_OBJ = "{";
	private static final String CLOSE_OBJ = "}";
	private static final String NULL = "null";
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String QUOTE = "\"";
	
	// myClass:myObject{....}
	
	@Override
	public void startObject(String className, String instanceName) {
		stream += className + INTERNAL_SEPARATOR;
		if (instanceName != null)
		{
			stream += instanceName;
		}
		stream += OPEN_OBJ;
	}

	@Override
	public void endObject() {
		stream += CLOSE_OBJ;
	}

	// int:,
	
	@Override
	public void writeField(Field field, Object parent) throws IllegalArgumentException, IllegalAccessException {
		stream += field.getType().getName() + INTERNAL_SEPARATOR;
		
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
		stream += DEF_SEPARATOR;
	}
	
	@Override
	public String toString() {
		return stream;
	}
}
