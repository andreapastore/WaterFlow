package com.pastore.handlers;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public class DateHandler 
{
	private LocalDateTime data_apertura;
	private String apertura;
	
	public DateHandler()
	{
		data_apertura = LocalDateTime.now();
		apertura = getDataAggiornata();
	}
	
	public int differenzaTraDate()
	{
		LocalDateTime data_chiusura = LocalDateTime.now();
		Duration duration = Duration.between(data_apertura, data_chiusura);
		return (int) duration.toMinutes();	
	}
	
	public String getDataAggiornata()
	{
		Date d = new Date();
		DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
		return formatoData.format(d);
	}

	public String getApertura() 
	{
		return apertura;
	}

	public void setApertura(String apertura) 
	{
		this.apertura = apertura;
	}
	
}
