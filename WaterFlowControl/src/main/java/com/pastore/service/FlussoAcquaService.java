package com.pastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastore.builders.DettaglioSocioBuilder;
import com.pastore.entity.DettaglioSocio;
import com.pastore.timers.FlussoAcquaTimer;
import com.pastore.timers.ScansioneQrCodeTimer;

@Service
public class FlussoAcquaService 
{
	@Autowired
	private ScansioneQrCodeTimer codeTimer;
	
	@Autowired
	private PompaStatusService pompaStatusService;
	
	private FlussoAcquaTimer acquaTimer;
	
	@Autowired
	private DettaglioSocioService dettaglioSocioService;
	
	@Autowired
	private QrCodeService qrCodeService;
	
	private boolean chiusa_da_utente;
	private DettaglioSocioBuilder dettaglioSocioBuilder;
	private boolean dettagliosocio_esistente;
	
	public void apri() 
	{
		chiusa_da_utente = false;
		disattivaTimerQrCode();
		attivaTimerFlussoAcqua();
		inizializzaDettaglioSocio();
	}
	
	public void chiudi()
	{
		if(chiusa_da_utente)
		{
			disattivaTimerAcqua();
			DettaglioSocio dettaglioSocio = dettaglioSocioBuilder.getDettaglioSocioAggiornato();
			if (dettagliosocio_esistente)
			{
				dettaglioSocioService.updateDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getId());
			}
			else
			{
				dettaglioSocioService.saveDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getId());
		
			}
			qrCodeService.disattivaPompa();
		}
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
		acquaTimer = new FlussoAcquaTimer(this);
		acquaTimer.start();
	}
	
	private void disattivaTimerAcqua()
	{
		acquaTimer.setFlusso_chiuso_da_utente(true);
	}

	public void chiudiDaTimer() 
	{
		if(!chiusa_da_utente)
		{
			DettaglioSocio dettaglioSocio = dettaglioSocioBuilder.getDettaglioSocioAggiornato();
			if (dettagliosocio_esistente)
			{
				dettaglioSocioService.updateDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getId());
			}
			else
			{
				dettaglioSocioService.saveDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getId());
		
			}

			qrCodeService.disattivaPompa();
		}
	}

	public void inizializzaDettaglioSocio()
	{
		DettaglioSocio dettaglioSocio = null;
		dettaglioSocio = dettaglioSocioService.getDettaglioSocioById("andreapastore");
		if (dettaglioSocio == null)
		{
			dettaglioSocioBuilder = new DettaglioSocioBuilder("andreapastore", 0);
			dettagliosocio_esistente = false;
		}
		else
		{
			dettagliosocio_esistente = true;
			dettaglioSocioBuilder = new DettaglioSocioBuilder(dettaglioSocio.getId(), dettaglioSocio.getMinuti_totali());
		}
	}
}
