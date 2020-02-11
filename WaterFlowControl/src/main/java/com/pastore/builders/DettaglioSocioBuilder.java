package com.pastore.builders;

import com.pastore.entity.DettaglioSocio;
import com.pastore.handlers.DateHandler;
import com.pastore.handlers.TimeHandler;

public class DettaglioSocioBuilder 
{
	private DettaglioSocio dettaglioSocio;
	private int user;
	private int minuti_tot;
	private DateHandler dateHandler;
	private TimeHandler timeHandler;
	private String username_socio;
	
	public DettaglioSocioBuilder(int username, int minuti_totali)
	{
		this.user = username;
		this.minuti_tot = minuti_totali;
		dateHandler = new DateHandler();
		timeHandler = new TimeHandler();
	}
	
	public DettaglioSocio getDettaglioSocioAggiornato()
	{
		dettaglioSocio = new DettaglioSocio();
		dettaglioSocio.setId(user);
		dettaglioSocio.setApertura(timeHandler.getApertura());
		dettaglioSocio.setChiusura(timeHandler.getOrarioChiusura());
		dettaglioSocio.setData_attivazione_slot(dateHandler.getDataAggiornata());
		dettaglioSocio.setMinuti(dateHandler.differenzaTraDate());
		dettaglioSocio.setMinuti_totali(dettaglioSocio.getMinuti() + minuti_tot);
		dettaglioSocio.setQuantita_acqua(0);
		
		return dettaglioSocio;
	}

	public int getUser() 
	{
		return user;
	}
	
	public String getUsernameSocio()
	{
		return this.username_socio;
	}
	
	public void setUsernameSocio(String s)
	{
		this.username_socio = s;
	}

}
