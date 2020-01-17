package com.pastore.restController;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pastore.entity.Socio;
import com.pastore.service.ListaUtentiLoggati;
import com.pastore.service.LoginService;
import com.pastore.service.SocioService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
	public boolean socioLogin(@RequestBody Socio socio, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try 
		{
			Optional<Socio> s = socioService.ricercaSocioByUsername(socio.getUsername());
			if (s.empty() != null)
			{
				if (socio.getPassword().equals(s.get().getPassword()))
				{
					System.out.println("ho trovato il socio");
					HttpSession oldSession = request.getSession(false);
					listaUtentiLoggati.inserisciSocioAppenaLoggato(s.get());
					if(oldSession != null)
					{
						oldSession.invalidate();
					}
					HttpSession currentSession = request.getSession();
					System.out.println("SONO LOGIN " + request.getSession().getId().toString());
					currentSession.setAttribute(currentSession.getId().toString(), s.get());//associo alla sessione il nome dell'utente come chiave e l'oggetto socio come valore
					currentSession.setMaxInactiveInterval(30*60);
					
					return true;
				}
				else
				{
					System.out.println("password errata");
					return false;
				}
				
			}
			else
			{
				System.out.println("non ho trovato il socio");
				return false;
			}
			
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	@GetMapping(value = "/logout")
	public ResponseEntity<HttpStatus> socioLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
	
		if(listaUtentiLoggati.controllaPresenzaSocio(s))
		{
			listaUtentiLoggati.eliminaSocioLoggato(s);
			try 
			{
				if(request.getSession() != null)
				{
					System.out.println("SONO logout " + request.getSession().getId().toString());
				
					loginService.logout(request.getSession());
					request.getSession().invalidate();
				}
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
			return new ResponseEntity<HttpStatus>(HttpStatus.LOCKED);
		}
	}
}
