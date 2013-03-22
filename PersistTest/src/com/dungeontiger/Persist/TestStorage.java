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
		
		// print out what was serialised
		System.out.println(stream.toString());
		
		Assert.assertNotNull(stream.toString());
	}

}
