package com.pastore.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastore.builders.DettaglioSocioBuilder;
import com.pastore.entity.DettaglioSocio;
import com.pastore.entity.PompaStatus;
import com.pastore.entity.Socio;
import com.pastore.timers.FlussoAcquaTimer;
import com.pastore.utilities.MemorizzatoreDettaglioSocioBuilder;
import com.pastore.utilities.MemorizzatorePompaSocio;

@Service
public class FlussoAcquaService 
{
	@Autowired
	private DettaglioSocioService dettaglioSocioService;
	
	@Autowired
	private QrCodeService qrCodeService;
	
	@Autowired
	private MemorizzatorePompaSocio memorizzatorePompaSocio;
	
	@Autowired
	private MemorizzatoreDettaglioSocioBuilder memorizzatoreDettaglioSocioBuilder;

	//@Autowired
	//private LedService ledService;
	
	private List<FlussoAcquaTimer> flussoAcquaTimers;
	
	public void apri(HttpSession session) 
	{
		disattivaTimerQrCode();
		inizializzaDettaglioSocio(session);
		
	}
	
	public void chiudi(HttpSession currentSession)
	{
		disattivaTimerAcqua(currentSession);
		Socio s = (Socio) currentSession.getAttribute(currentSession.getId().toString());
		DettaglioSocio dettaglioSocio = memorizzatoreDettaglioSocioBuilder.getDettaglioSocioBuilder(s.getUsername()).getDettaglioSocioAggiornato();
		DettaglioSocio d = dettaglioSocioService.getDettaglioSocioById(s.getUsername());
		if (d != null)
		{
			dettaglioSocioService.updateDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getId());
		}
		else
		{
			dettaglioSocioService.saveDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getId());
		}
		PompaStatus p = memorizzatorePompaSocio.getPompa(s.getUsername());
		qrCodeService.disattivaPompa(p);
		
	}

	private void disattivaTimerQrCode()
	{
		qrCodeService.getCodeTimer().setPompa_in_uso(true);
	}
	
	private void attivaTimerFlussoAcqua(HttpSession session, DettaglioSocioBuilder dettaglioSocioBuilder)
	{
		if (flussoAcquaTimers == null)
		{
			flussoAcquaTimers = new ArrayList<>();
		}
		Socio s = (Socio) session.getAttribute(session.getId().toString());
		if (s != null)
		{
			
			FlussoAcquaTimer acquaTimer = new FlussoAcquaTimer(this, memorizzatorePompaSocio.getPompa(s.getUsername()), dettaglioSocioBuilder);
			acquaTimer.start();
			flussoAcquaTimers.add(acquaTimer);
		}
	}
	
	private void disattivaTimerAcqua(HttpSession session)
	{
		Socio s = (Socio) session.getAttribute(session.getId().toString());
		
		for (int i = 0; i < flussoAcquaTimers.size(); i++)
		{
			if(flussoAcquaTimers.get(i).getUsernameSocio() == s.getUsername())
			{
				flussoAcquaTimers.get(i).setFlusso_chiuso_da_utente(true);
				flussoAcquaTimers.remove(i);
				break;
			}
		}
	}

	public void chiudiDaTimer(PompaStatus p, DettaglioSocioBuilder d) 
	{
	//	ledService.lightOff();
		DettaglioSocio dettaglioSocio = memorizzatoreDettaglioSocioBuilder.getDettaglioSocioBuilder(d.getUser()).getDettaglioSocioAggiornato();
		DettaglioSocio dett = dettaglioSocioService.getDettaglioSocioById(d.getUser());
		if (dett != null)
		{
			dettaglioSocioService.updateDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getId());
		}
		else
		{
			dettaglioSocioService.saveDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getId());
		}
		qrCodeService.disattivaPompa(p);
		memorizzatoreDettaglioSocioBuilder.deleteDettaglioSocioBuilder(dettaglioSocio.getId());
	}

	public void inizializzaDettaglioSocio(HttpSession session)
	{
		Socio s = (Socio) session.getAttribute(session.getId().toString());
		DettaglioSocio dettaglioSocio = null;
		dettaglioSocio = dettaglioSocioService.getDettaglioSocioById(s.getUsername());
		DettaglioSocioBuilder dettaglioSocioBuilder;
		if (dettaglioSocio == null)
		{
			dettaglioSocioBuilder = new DettaglioSocioBuilder(s.getUsername(), 0);
			memorizzatoreDettaglioSocioBuilder.inserisciDettaglioSocio(s.getUsername(), dettaglioSocioBuilder);
		}
		else
		{
			dettaglioSocioBuilder = new DettaglioSocioBuilder(dettaglioSocio.getId(), dettaglioSocio.getMinuti_totali());
			memorizzatoreDettaglioSocioBuilder.inserisciDettaglioSocio(dettaglioSocio.getId(), dettaglioSocioBuilder);
		}
		attivaTimerFlussoAcqua(session, dettaglioSocioBuilder);
	}

}
