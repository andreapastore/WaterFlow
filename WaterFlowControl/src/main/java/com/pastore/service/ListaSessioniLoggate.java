package com.pastore.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListaSessioniLoggate 
{
	private List<String> sessioni;
	
	public void inserisciNuovaSessione(String id)
	{
		if(sessioni == null)
		{
			sessioni = new ArrayList<String>();
		}
		
		sessioni.add(id);
	}
	
	public boolean controllaPresenzaSessione(String id)
	{
		if(sessioni != null)
			return sessioni.contains(id);
		else
			return false;
	}
	
	public void eliminaSessione(String id)
	{
		sessioni.remove(id);
	}
}
