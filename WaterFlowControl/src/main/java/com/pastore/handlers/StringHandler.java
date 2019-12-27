package com.pastore.handlers;

public class StringHandler {

	public StringHandler() {}
	
	public String convertInt(int i)
	{
		return i + "";
	}
	
	public int convertToString(String i)
	{
		return Integer.parseInt(i);
	}
}
