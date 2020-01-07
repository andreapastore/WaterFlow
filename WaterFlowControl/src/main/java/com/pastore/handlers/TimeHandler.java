package com.pastore.handlers;

import java.time.Instant;

public class TimeHandler 
{
	private String apertura;
	
	public TimeHandler()
	{
		orarioApertura();
	}
	
	private void orarioApertura()
	{
		String a = Instant.now().toString();
		apertura = (String) a.subSequence(11, 19);
	}
	
	public String getOrarioChiusura()
	{
		String orario = Instant.now().toString();
		return (String) orario.subSequence(11, 19);
	}

	public String getApertura() 
	{
		return apertura;
	}

}
