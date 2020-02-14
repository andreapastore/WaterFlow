package com.pastore.restController;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.RispostaLoggedIn;
import com.pastore.entity.Socio;
import com.pastore.service.ListaUtentiLoggati;
import com.pastore.service.LoginService;
import com.pastore.service.SocioService;

@CrossOrigin(origins = "http://10.10.100.165:4200", allowCredentials = "true")
@RestController
@RequestMapping(value = "/api")
public class RestLogin 
{
	@Autowired
	private SocioService socioService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ListaUtentiLoggati listaUtentiLoggati;
	
	@PostMapping(value = "/login", produces = "application/json")
	public RispostaLoggedIn socioLogin(@RequestBody Socio socio, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try 
		{
			Optional<Socio> s = socioService.ricercaSocioByUsername(socio.getUsername());
			if (s.empty() != null)
			{
				if (socio.getPassword().equals(s.get().getPassword()))
				{
					System.out.println("ho trovato il socio");
					HttpSession oldSession = request.getSession();
					listaUtentiLoggati.inserisciSocioAppenaLoggato(s.get());
					if(oldSession != null)
					{
						oldSession.invalidate();
					}
					HttpSession currentSession = request.getSession();
					System.out.println("SONO LOGIN " + request.getSession().getId().toString());
					loginService.inserisciNuovaSessione(currentSession);
					currentSession.setAttribute(currentSession.getId().toString(), s.get());//associo alla sessione il nome dell'utente come chiave e l'oggetto socio come valore
					currentSession.setMaxInactiveInterval(30*60);
					RispostaLoggedIn r = new RispostaLoggedIn("true");
					r.setBarca(s.get().getPostazione());
					r.setUsername(s.get().getUsername());
					r.setProfilo(s.get().getProfilo());
					this.loginService.inserisciTimerDiAttesa(r);
					return r;
				}
				else
				{
					System.out.println("password errata");
					return new RispostaLoggedIn("false");
				}
				
			}
			else
			{
				System.out.println("non ho trovato il socio");
				return new RispostaLoggedIn("false");
			}
			
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new RispostaLoggedIn("false");
		}
	}

	@GetMapping(value = "/login/loggedIn", produces = "application/json")
	public RispostaLoggedIn controlloSocioLoggato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		Socio s = (Socio) session.getAttribute(session.getId().toString());
		if(s != null)
		{
			CharSequence a = "a";
			CharSequence b = "b";
			RispostaLoggedIn r = new RispostaLoggedIn("true");
			r.setBarca(s.getPostazione());
			r.setUsername(s.getUsername());
			if(s.getPostazione().contains(a))
			{
				r.setPosto(1);
			}
			else if (s.getPostazione().contains(b))
			{
				r.setPosto(2);
			}
			else
			{
				r.setPosto(3);
			}
			r.setProfilo(s.getProfilo());
			return r;
		}
		return new RispostaLoggedIn("false");
	
	}
	
	@GetMapping(value = "/logout")
	public RispostaLoggedIn socioLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{	
			if(listaUtentiLoggati.controllaPresenzaSocio(s))
			{
				listaUtentiLoggati.eliminaSocioLoggato(s);
				loginService.eliminaSessione(request.getSession());
				try 
				{
					if(request.getSession() != null)
					{
						System.out.println("SONO logout " + request.getSession().getId().toString());
					
						loginService.logout(request.getSession());
						request.getSession().invalidate();
					}
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
		else
		{
			return new RispostaLoggedIn("false");
		}
	}
}
