package com.pastore.timers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pastore.service.FlussoAcquaService;

@Component
public class FlussoAcquaTimer extends Thread
{
	@Autowired
	private FlussoAcquaService acquaService;
	
	private boolean flusso_chiuso_da_utente;
	
	public boolean isFlusso_chiuso_da_utente() 
	{
		return flusso_chiuso_da_utente;
	}

	public void setFlusso_chiuso_da_utente(boolean flusso_chiuso_da_utente) 
	{
		this.flusso_chiuso_da_utente = flusso_chiuso_da_utente;
	}
	
	@Override
	public void run() 
	{
		flusso_chiuso_da_utente = false;
		System.out.println("timer flusso acqua partito");
		try 
		{
			Thread.sleep(120000);
			if(flusso_chiuso_da_utente)
			{
				System.out.println("l'utente ha chiuso il flusso d'acqua");
			}
			else
			{
				System.out.println("l'utente NON ha chiuso il flusso acqua quindi chiamo io la chiudi di FlussoAcquaService");
				acquaService.chiudiDaTimer();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
}
