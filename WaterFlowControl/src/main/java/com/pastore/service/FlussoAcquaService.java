package com.pastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastore.timers.ScansioneQrCodeTimer;

@Service
public class FlussoAcquaService 
{
	@Autowired
	private ScansioneQrCodeTimer codeTimer;
	
	@Autowired
	private PompaStatusService pompaStatusService;
	
	private boolean chiusa_da_utente;
	
	public void apri() 
	{
		chiusa_da_utente = false;
		codeTimer.setPompa_in_uso(true);
		disattivatimerQrCode();
		pompaStatusService.updateStatus("attiva", 1);
		//da qui parte il timer
	}
	
	public void chiudi()
	{
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

	private void disattivatimerQrCode()
	{
		codeTimer.setPompa_in_uso(true);
	}
}
