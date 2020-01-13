package com.pastore.timers;

import com.pastore.builders.DettaglioSocioBuilder;
import com.pastore.entity.PompaStatus;
import com.pastore.service.FlussoAcquaService;

public class FlussoAcquaTimer extends Thread
{
	private FlussoAcquaService acquaService;
	private boolean flusso_chiuso_da_utente;
	private PompaStatus pompa;
	private DettaglioSocioBuilder dettaglioSocioBuilder;
	
	public FlussoAcquaTimer(FlussoAcquaService f, PompaStatus p, DettaglioSocioBuilder d)
	{
		this.acquaService = f;
		this.pompa = p;
		this.dettaglioSocioBuilder = d;
	}

	public boolean isFlusso_chiuso_da_utente() 
	{
		return flusso_chiuso_da_utente;
	}

	public void setFlusso_chiuso_da_utente(boolean flusso_chiuso_da_utente) 
	{
		this.flusso_chiuso_da_utente = flusso_chiuso_da_utente;
	}
	
	public String getUsernameSocio()
	{
		return dettaglioSocioBuilder.getUser();
	}
	
	@Override
	public void run() 
	{
		flusso_chiuso_da_utente = false;
		System.out.println("timer flusso acqua partito");
		try 
		{
			Thread.sleep(180000);
			if(flusso_chiuso_da_utente)
			{
				System.out.println("l'utente ha chiuso il flusso d'acqua");
			}
			else
			{
				System.out.println("id della pompa che il timer flusso acqua sta per chiudere: " + pompa.getId());
				System.out.print(" perchè l'utente " + dettaglioSocioBuilder.getUser() + " ha fatto scattare il timer");
				acquaService.chiudiDaTimer(pompa, dettaglioSocioBuilder);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
}
