package com.pastore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pastore.entity.Socio;

@Service
public class ListaUtentiLoggati 
{
	private List<Socio> soci_loggati;
	
	public void inserisciSocioAppenaLoggato(Socio s)
	{
		if(soci_loggati == null)
		{
			soci_loggati = new ArrayList<Socio>();
		}
		
		soci_loggati.add(s);
		System.out.println("ho aggiunto il socio " + s.getUsername()+ " alla lista degli utenti loggati");
	}
	
	public boolean controllaPresenzaSocio(Socio s)
	{
		System.out.println("ho controllato se il socio " + s.getUsername() + " è loggato");
		return soci_loggati.contains(s);
	}
	
	public void eliminaSocioLoggato(Socio s)
	{
		soci_loggati.remove(s);
		System.out.println("ho eliminato il socio " + s.getUsername() + " dalla lista degli utenti loggati");
	}
}
