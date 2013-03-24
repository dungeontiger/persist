package com.dungeontiger.Persist;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class TestStorage {

	@Test
	public void testWriteSubjectClass() throws IllegalArgumentException, StorageException, IllegalAccessException {
		// object to save
		SubjectClass testObject = new SubjectClass();
		
		// concrete stream to store object
		TigerStorageStream stream = new TigerStorageStream();
		
		Storage storage = new Storage(stream);
		storage.write(testObject);
		Assert.assertTrue(stream.toString().compareTo("com.dungeontiger.Persist.SubjectClass{myInt:5,myString:\"This is a string with a : in it.\",nullString:null}") == 0);
	}

	@Test 
	public void testReadSubjectClass() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException
	{
		String testString = "com.dungeontiger.Persist.SubjectClass{myInt:26,myString:\"Some text\",nullString:null}";
		
		// Intialize stream with test
		TigerStorageStream stream = new TigerStorageStream(testString);
		
		Storage storage = new Storage(stream);
		
		Object obj = storage.read();
		Assert.assertTrue(obj instanceof SubjectClass);
		
		SubjectClass subject = (SubjectClass)obj;
		
		Assert.assertEquals(26, subject.getMyInt());
		
	}
}
