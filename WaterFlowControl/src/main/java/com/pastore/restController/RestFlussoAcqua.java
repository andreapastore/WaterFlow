package com.pastore.restController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pastore.entity.RispostaLoggedIn;
import com.pastore.entity.Socio;
import com.pastore.service.FlussoAcquaService;
import com.pastore.service.LedService;
import com.pastore.service.ListaUtentiLoggati;

@CrossOrigin(origins = "http://10.10.100.165:4200", allowCredentials = "true")
@RestController
@RequestMapping(value = "/api/flussoAcqua")
public class RestFlussoAcqua {
	
	//@SuppressWarnings("unused")
	@Autowired
	private LedService ledService;
	
	@Autowired
	private ListaUtentiLoggati listaUtentiLoggati;
	
	@Autowired
	private FlussoAcquaService flussoAcquaService;
	
	@GetMapping(value = "/apri", produces = "application/json")
	public RispostaLoggedIn apri(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		
		if(listaUtentiLoggati.controllaPresenzaSocio(s))
		{
			try
			{
				System.out.println("Sono id session nella flussoAcqua " + request.getSession().getId().toString());
				
				flussoAcquaService.apri(request.getSession());
				//ledService.lightOn();
				System.out.println("il socio " + s.getUsername() + " è entrato nella apri");
				return new RispostaLoggedIn("true");
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return new RispostaLoggedIn("false");
			}
		}
		else
		{
			return new RispostaLoggedIn("false");
		}
	}
	
	@GetMapping(value = "/chiudi", produces = "application/json")
	public RispostaLoggedIn chiudi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		
		if(listaUtentiLoggati.controllaPresenzaSocio(s))
		{
			try
			{
				System.out.println("sono id session nella flussoacqua chiudi " + request.getSession().getId().toString());
				
				flussoAcquaService.chiudi(request.getSession());
			//	ledService.lightOff();
				System.out.println("il socio " + s.getUsername() + " è entrato nella chiudi");
				return new RispostaLoggedIn("true");
			}
			catch (Exception e) {
				System.out.println(e);
				return new RispostaLoggedIn("false");
			}
		}
		else
		{
			return new RispostaLoggedIn("false");
		}
	}

}
