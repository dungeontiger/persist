package com.dungeontiger.Persist;

/**
 * A class to persist for testing
 * @author gibsons
 *
 */
public class SubjectClass {

	@Persistant
	private int myInt;
	
	@SuppressWarnings("unused")
	private int doNotPersist;
	
	@Persistant
	private String myString;
	
	@Persistant
	private String nullString;
	
	public SubjectClass()
	{
		myInt = 5;
		doNotPersist = 10;
		myString = "This is a string with a : in it.";
		nullString = null;
	}
	
	public int getMyInt()
	{
		return myInt;
	}
	
	public String getMyString()
	{
		return myString;
	}
	
	public String getNullString()
	{
		return nullString;
	}
}
