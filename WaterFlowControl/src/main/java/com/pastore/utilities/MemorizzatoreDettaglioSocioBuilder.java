package com.pastore.utilities;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.pastore.builders.DettaglioSocioBuilder;

@Component
public class MemorizzatoreDettaglioSocioBuilder 
{
	HashMap<String, DettaglioSocioBuilder> memorizzatoreDettagliosocio;
	
	public void inserisciDettaglioSocio(String username, DettaglioSocioBuilder d)
	{
		if (memorizzatoreDettagliosocio == null)
		{
			memorizzatoreDettagliosocio = new HashMap<>();
		}
		memorizzatoreDettagliosocio.put(username, d);
	}
	
	public DettaglioSocioBuilder getDettaglioSocioBuilder(String username)
	{
		return memorizzatoreDettagliosocio.get(username);
	}
	
	public void deleteDettaglioSocioBuilder(String username)
	{
		memorizzatoreDettagliosocio.remove(username);
	}
}
