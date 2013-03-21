package com.dungeontiger.Persist;

import java.lang.reflect.Field;

public class PersistStreamSimple implements PersisterStream {
	
	// change to string stream
	private String stream = new String();
	
	static final private String NEW_OBJECT = "\n";
	static final private String END_OBJECT = ";";
	static final private String SEPARATOR = ":";
	static final private String ESCAPE = "%";
	static final private String NULL = "NULL";
	
	static final private String INT = "int";
	static final private String STRING = "STRING";
	
	
	public PersistStreamSimple()
	{
	}
	
	public PersistStreamSimple(String newStream)
	{
		stream = newStream;
	}

	@Override
	public void startWriteObject(String className, int id) {
		stream += className + SEPARATOR + id + SEPARATOR;
	}

	@Override
	public void endWriteObject() {
		stream += END_OBJECT + NEW_OBJECT;
	}
	
	public String getStream()
	{
		return stream;
	}
	
	@Override
	public Object readObject() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException
	{
		// beginning of the stream is the class name
		int index = stream.indexOf(SEPARATOR);
		String className = stream.substring(0, index);
		Class<?> theClass = Class.forName(className);
		Object obj = theClass.newInstance();
		
		// skip over the id
		index = stream.indexOf(SEPARATOR, index + 1);
		if (index != -1)
		{
			int endIndex = stream.indexOf(SEPARATOR, index + 1);
			if (endIndex != -1)
			{
				String fieldName = stream.substring(index + 1, endIndex);
				index = endIndex;
				endIndex = stream.indexOf(SEPARATOR, index + 1);
				String type = stream.substring(index + 1, endIndex);
				index = endIndex;
				endIndex = stream.indexOf(SEPARATOR, index + 1);
				String value = stream.substring(index + 1, endIndex);
				Field field = theClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				if (field.getType() == int.class)
				{
					field.setInt(obj, Integer.parseInt(value));
				}
				else if (field.getType() == String.class)
				{
					if (value.compareTo(ESCAPE + NULL) == 0)
					{
						field.set(obj, null);
					}
					else
					{
						field.set(obj, decodeString(value));
					}
				}
			}
		}
		
		return obj;
	}

	@Override
	public void writeIntField(String fieldName, int value) {
		stream += fieldName + SEPARATOR + INT + SEPARATOR + value + SEPARATOR;
	}
	
	@Override
	public void writeStringField(String fieldName, String value) {
		if (value == null)
		{
			stream += fieldName + SEPARATOR + STRING + SEPARATOR + ESCAPE + NULL + SEPARATOR;
		}
		else
		{
			stream += fieldName + SEPARATOR + STRING + SEPARATOR + encodeString(value) + SEPARATOR;
		}
	}

	private String encodeString(String toEncode)
	{
		// first encode the escape character
		String encoded = toEncode.replaceAll(ESCAPE, ESCAPE + ESCAPE);
		
		// now encode the separators
		encoded = encoded.replaceAll(SEPARATOR, ESCAPE + SEPARATOR);
		
		return encoded;
	}
	
	private String decodeString(String toDecode)
	{
		// first  decode the separators
		String decoded = toDecode.replaceAll(ESCAPE + SEPARATOR, SEPARATOR);
		
		// now decode escape characters
		decoded = decoded.replaceAll(ESCAPE + ESCAPE, ESCAPE);
		
		return decoded;
	}

}
