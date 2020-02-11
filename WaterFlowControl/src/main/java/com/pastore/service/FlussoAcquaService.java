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

	@Autowired
	private LedService ledService;
	
	@Autowired
	private AttesaTimerService attesaTimerService;
	
	private List<FlussoAcquaTimer> flussoAcquaTimers;
	private int numero_minuti_di_attesa = 0;
	
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
		dettaglioSocioService.saveDettaglioSocio(dettaglioSocioService.tornaIdMax()+1, dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), s.getUsername());
		PompaStatus p = memorizzatorePompaSocio.getPompa(s.getUsername());
		memorizzatoreDettaglioSocioBuilder.deleteDettaglioSocioBuilder(dettaglioSocio.getSocio_username());
		qrCodeService.disattivaPompa(p);
		
	}

	private void disattivaTimerQrCode()
	{
		System.out.println("BOOLEANA DEL TIMER QR CODE MESSA A TRUE");
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
			FlussoAcquaTimer acquaTimer = new FlussoAcquaTimer(this, memorizzatorePompaSocio.getPompa(s.getUsername()), dettaglioSocioBuilder, this.attesaTimerService.ritornaMinutiDiAttesaFlussoAcqua());
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
		//ledService.lightOff();
		System.out.println("SONO QUI DENTRO");
		DettaglioSocio dettaglioSocio = memorizzatoreDettaglioSocioBuilder.getDettaglioSocioBuilder(d.getUsernameSocio()).getDettaglioSocioAggiornato();
		System.out.println("sono dettaglio socio nella chiudi da timer " + dettaglioSocio);
		dettaglioSocioService.saveDettaglioSocio(dettaglioSocioService.tornaIdMax()+1, dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), d.getUsernameSocio());
		qrCodeService.disattivaPompa(p);
		memorizzatoreDettaglioSocioBuilder.deleteDettaglioSocioBuilder(dettaglioSocio.getSocio_username());
		
	}

	public void inizializzaDettaglioSocio(HttpSession session)
	{
		Socio s = (Socio) session.getAttribute(session.getId().toString());
		DettaglioSocio dettaglioSocio = null;
		dettaglioSocio = dettaglioSocioService.getDettaglioSocioById(s.getUsername());
		DettaglioSocioBuilder dettaglioSocioBuilder;
		if (dettaglioSocio.getSocio_username() == null)
		{
			dettaglioSocioBuilder = new DettaglioSocioBuilder(dettaglioSocioService.tornaIdMax()+1, 0);
			dettaglioSocioBuilder.setUsernameSocio(s.getUsername());
			memorizzatoreDettaglioSocioBuilder.inserisciDettaglioSocio(s.getUsername(), dettaglioSocioBuilder);
		}
		else
		{
			dettaglioSocioBuilder = new DettaglioSocioBuilder(dettaglioSocioService.tornaIdMax()+1, dettaglioSocio.getMinuti_totali());
			dettaglioSocioBuilder.setUsernameSocio(s.getUsername());
			memorizzatoreDettaglioSocioBuilder.inserisciDettaglioSocio(dettaglioSocio.getSocio_username(), dettaglioSocioBuilder);
		}
		attivaTimerFlussoAcqua(session, dettaglioSocioBuilder);
	}

	public int getNumero_minuti_di_attesa() 
	{
		return numero_minuti_di_attesa = this.attesaTimerService.ritornaMinutiDiAttesaFlussoAcqua();
	}

	public void setNumero_minuti_di_attesa(int numero_minuti_di_attesa) 
	{
		this.numero_minuti_di_attesa = numero_minuti_di_attesa;
	}

	
}
