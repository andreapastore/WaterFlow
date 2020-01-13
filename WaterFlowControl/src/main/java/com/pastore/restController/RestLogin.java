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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pastore.entity.Socio;
import com.pastore.service.SocioService;

@RestController
@RequestMapping(value = "/api")
public class RestLogin 
{
	@Autowired
	private SocioService socioService;
	
	@GetMapping(value = "/login", produces = "application/json")
	public ResponseEntity<Socio> socioLogin(@RequestBody Socio socio, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try 
		{
			Optional<Socio> s = socioService.ricercaSocioByUsername(socio.getUsername());
			if (!s.isEmpty())
			{
				if (socio.getPassword().equals(s.get().getPassword()))
				{
					System.out.println("ho trovato il socio");
					HttpSession oldSession = request.getSession(false);
					if(oldSession != null)
					{
						oldSession.invalidate();
					}
					HttpSession currentSession = request.getSession();
					currentSession.setAttribute(currentSession.getId().toString(), s.get());//associo alla sessione il nome dell'utente come chiave e l'oggetto socio come valore
					currentSession.setMaxInactiveInterval(30*60);
					return new ResponseEntity<>(s.get(), HttpStatus.OK);
				}
				else
				{
					System.out.println("password errata");
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
				
			}
			else
			{
				System.out.println("non ho trovato il socio");
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/logout")
	public ResponseEntity<HttpStatus> socioLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Socio s = (Socio) session.getAttribute(session.getId().toString());
		
		try 
		{
			if(session != null)
			{
				System.out.println("logout del socio " + s.getUsername() + " andato a buon fine");
				session.invalidate();
			}
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
