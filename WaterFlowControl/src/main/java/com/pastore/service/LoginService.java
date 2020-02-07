package com.pastore.service;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastore.entity.RispostaLoggedIn;
import com.pastore.entity.Socio;
import com.pastore.utilities.MemorizzatorePompaSocio;

@Service
public class LoginService 
{
	@Autowired
	private MemorizzatorePompaSocio memorizzatorePompaSocio;
	
	@Autowired
	private ListaSessioniLoggate listaSessioniLoggate;
	
	@Autowired
	private AttesaTimerService attesaTimerService;
	
	public void logout(HttpSession session)
	{
		Socio s = (Socio) session.getAttribute(session.getId().toString());
		
		if(memorizzatorePompaSocio.esisteSocio(s.getUsername()))
		{	
			memorizzatorePompaSocio.deletePompa(s.getUsername());
			System.out.println("logout del socio " + s.getUsername() + " andato a buon fine");
		}
		else
		{
			System.out.println("logout del socio " + s.getUsername() + " andato a buon fine e non ha usato nessuna pompa");
		}
		
	}

	public boolean controllaSocioLoggato(HttpSession session) 
	{
		return listaSessioniLoggate.controllaPresenzaSessione(session.getId().toString());
	}
	
	public void inserisciNuovaSessione(HttpSession session)
	{
		listaSessioniLoggate.inserisciNuovaSessione(session.getId().toString());
	}
	
	public void eliminaSessione(HttpSession session)
	{
		listaSessioniLoggate.eliminaSessione(session.getId().toString());
	}

	public void inserisciTimerDiAttesa(RispostaLoggedIn r) 
	{
		r.setTimerflussoacqua(attesaTimerService.ritornaMinutiDiAttesaFlussoAcqua());
		r.setTimerqrcode(attesaTimerService.ritornaMinutiDiAttesaQrCode());
	}
}
