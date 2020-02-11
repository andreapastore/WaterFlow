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
	private int minuti;
	
	public FlussoAcquaTimer(FlussoAcquaService f, PompaStatus p, DettaglioSocioBuilder d, int m)
	{
		this.acquaService = f;
		this.pompa = p;
		this.dettaglioSocioBuilder = d;
		m *= 60000;
		this.minuti = m;
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
		return dettaglioSocioBuilder.getUsernameSocio();
	}
	
	@Override
	public void run() 
	{
		flusso_chiuso_da_utente = false;
		System.out.println("timer flusso acqua partito");
		try 
		{
			Thread.sleep(minuti);
			if(flusso_chiuso_da_utente)
			{
				System.out.println("l'utente ha chiuso il flusso d'acqua");
			}
			else
			{
				System.out.print("id della pompa che il timer flusso acqua sta per chiudere: " + pompa.getId());
				System.out.println(" perchè l'utente " + dettaglioSocioBuilder.getUsernameSocio() + " non ha chiuso il flusso, durata del timer: " + this.minuti);
				acquaService.chiudiDaTimer(pompa, dettaglioSocioBuilder);
				//System.out.println("sono nel timer e sto checcando che la roba non sia null");
				//System.out.println("acquaservice " + this.acquaService);
				//System.out.println("pompa " + this.pompa);
				//System.out.println("dettagliosociobuilder " + this.dettaglioSocioBuilder);
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
}
