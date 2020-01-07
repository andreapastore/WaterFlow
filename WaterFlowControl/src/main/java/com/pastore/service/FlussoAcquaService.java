package com.pastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastore.builders.DettaglioSocioBuilder;
import com.pastore.entity.DettaglioSocio;
import com.pastore.handlers.TimeHandler;
import com.pastore.timers.FlussoAcquaTimer;
import com.pastore.timers.ScansioneQrCodeTimer;

@Service
public class FlussoAcquaService 
{
	@Autowired
	private ScansioneQrCodeTimer codeTimer;
	
	@Autowired
	private PompaStatusService pompaStatusService;
	
	@Autowired
	private FlussoAcquaTimer acquaTimer;
	
	@Autowired
	private DettaglioSocioService dettaglioSocioService;
	
	private boolean chiusa_da_utente;
	private DettaglioSocioBuilder dettaglioSocioBuilder;
	
	public void apri() 
	{
		chiusa_da_utente = false;
		disattivaTimerQrCode();
		attivaTimerFlussoAcqua();
		pompaStatusService.updateStatus("attiva", 1);
		//prima di chiamare il dettaglio socio builder fare il check se esiste nel db o è il primo record
		dettaglioSocioBuilder = new DettaglioSocioBuilder("user", 0);
		//da qui parte il timer
	}
	
	public void chiudi()
	{
		if(chiusa_da_utente)
		{
			//allora la pompa è stata chiusa dall'utente
			//disattivare timer
			disattivatimerAcqua();
		}
		else
		{
			//pompa non chiusa dall'utente, il timer deve scattare
		}
		
		//aggiorna dettaglisocio utilizzatore
		pompaStatusService.updateStatus("disattiva", 1);
	}
	
	public boolean isChiusa_da_utente() 
	{
		return chiusa_da_utente;
	}

	public void setChiusa_da_utente(boolean chiusa_da_utente) 
	{
		this.chiusa_da_utente = chiusa_da_utente;
	}

	private void disattivaTimerQrCode()
	{
		codeTimer.setPompa_in_uso(true);
	}
	
	private void attivaTimerFlussoAcqua()
	{
		acquaTimer.start();
	}
	
	private void disattivatimerAcqua()
	{
		acquaTimer.setFlusso_chiuso_da_utente(true);
	}

	public void chiudiDaTimer() 
	{
		if(!chiusa_da_utente)
		{
			DettaglioSocio dettaglioSocio = dettaglioSocioBuilder.getDettaglioSocioAggiornato();
			dettaglioSocioService.saveDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getId());
		}
	}
}
