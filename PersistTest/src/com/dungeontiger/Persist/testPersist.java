package com.dungeontiger.Persist;
import org.junit.Test;
import org.junit.Assert;


public class testPersist {

	@Test
	public void testSimplePersist() throws IllegalArgumentException, IllegalAccessException 
	{
		SubjectClass testObject = new SubjectClass();
		PersistStreamSimple stream = new PersistStreamSimple();
		Persister persister = new Persister(stream);
		
		// do work
		persister.write(testObject);
		
		System.out.println(stream.getStream());
	}

	@Test
	public void testSimpleLoad() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException
	{
		PersistStreamSimple stream = new PersistStreamSimple("com.dungeontiger.Persist.SubjectClass:29132923;");
		Persister persister = new Persister(stream);
		
		// do work
		Object obj = persister.read();
		Assert.assertTrue(obj instanceof SubjectClass);
	}

	@Test
	public void testSimpleLoadOneField() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException
	{
		PersistStreamSimple stream = new PersistStreamSimple("com.dungeontiger.Persist.SubjectClass:3794357:myInt:int:26:;");
		Persister persister = new Persister(stream);
		
		// do work
		Object obj = persister.read();
		
		// test object
		Assert.assertTrue(obj instanceof SubjectClass);
		SubjectClass subject = (SubjectClass)obj;
		Assert.assertEquals(26, subject.getMyInt());
	}

}
