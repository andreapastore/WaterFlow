package com.pastore.restController;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.PompaStatus;
import com.pastore.entity.RispostaLoggedIn;
import com.pastore.entity.Socio;
import com.pastore.service.PompaStatusService;
import com.pastore.service.SocioService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/manutenzione")
public class RestManutenzione 
{
	@Autowired
	SocioService socioService;
	
	@Autowired
	private PompaStatusService pompaStatusService;
	
	@PostMapping(value = "/insertSocio", produces = "application/json")
	public RispostaLoggedIn insertSocio(@RequestBody Socio socio, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			try
			{
				socioService.insertSocio(socio.getUsername(), socio.getAbilitato(), socio.getBarca(), socio.getPassword(), socio.getPostazione(), socio.getProfilo());
				return new RispostaLoggedIn("true");
			}
			catch (Exception e) {
				
				e.printStackTrace();
				return new RispostaLoggedIn("false");
			}
		}
		else
		{
			return new RispostaLoggedIn("false");
		}
	}
	
	@GetMapping(value = "/ricercatuttisoci", produces = "application/json")
	public ResponseEntity<Iterable<Socio>> ricercaTutto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
		if (s != null)
		{	
			try 
			{
				Iterable<Socio> soci = socioService.ricercaTutto();
				return new ResponseEntity<Iterable<Socio>>(soci, HttpStatus.OK);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return new ResponseEntity<Iterable<Socio>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
			return new ResponseEntity<Iterable<Socio>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/ricercasocioconusername/{username}", produces = "application/json")
	public ResponseEntity<Socio> ricercaSocioByUsername(@PathVariable("username") String username, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
		if (s != null)
		{	
			try 
			{
				Optional<Socio> socio = socioService.ricercaSocioByUsername(username);
				
				if (!socio.isEmpty())
				{
					return new ResponseEntity<Socio>(socio.get(), HttpStatus.OK);
				}
				else
				{
					return new ResponseEntity<Socio>(HttpStatus.NO_CONTENT);
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return new ResponseEntity<Socio>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
			return new ResponseEntity<Socio>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping(value = "/cancellasocioconusername/{username}")
	public ResponseEntity<HttpStatus> cancellaSocioByUsername(@PathVariable("username") String username, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
		if (s != null)
		{	
			try
			{
				socioService.cancellaSocioByUsername(username);
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			}
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//nel json i campi devono essere già aggiornati
		@PutMapping(value = "/modificasocioconusername/{username}", produces = "application/json")
	public RispostaLoggedIn updateSocio(@RequestBody Socio socio, @PathVariable("username") String username, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
		{
			Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
			if (s != null)
			{
				try 
				{
					socioService.updateSocio(username, socio.getAbilitato(), socio.getBarca(), socio.getPassword(), socio.getPostazione(), socio.getProfilo());
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
		
		@PostMapping(value = "/updatepasswordsocio", produces = "application/json")
	public RispostaLoggedIn updatePassword(@RequestBody Socio socio, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
		{
			Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
			if (s != null)
			{
				try 
				{
					socioService.updateSocio(socio.getUsername(), socio.getAbilitato(), socio.getBarca(), socio.getPassword(), socio.getPostazione(), socio.getProfilo());
					return new RispostaLoggedIn("true");
				} catch (Exception e) 
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
		
		@PostMapping(value = "/updatepasswordsocioconusername/{username}")
	public RispostaLoggedIn updatePasswordConUser(@RequestBody Socio socio, @PathVariable("username") String username, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
		{
			Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
			if (s != null)
			{
				try 
				{
					socioService.updateSocio(username, socio.getAbilitato(), socio.getBarca(), socio.getPassword(), socio.getPostazione(), socio.getProfilo());
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

	@GetMapping(value = "/getPompaStatus/{id}", produces = "application/json")
	public PompaStatus getPompaStatusById(@PathVariable ("id") int id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			try 
			{
				PompaStatus p = pompaStatusService.getPompaStatusById(id);
				if(p != null)
				{
					return p;
				}
				else
				{
					return null;
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return null;
		}
	}
		
	@PutMapping(value = "/insertPompaStatus", produces = "application/json")
	public RispostaLoggedIn insertPompaStatus(@RequestBody PompaStatus p, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{	
			try 
			{
				pompaStatusService.insertPompaStatus(p.getId(), p.getStatus());
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
		
	@PostMapping(value = "/updatePompaStatus", produces = "application/json")
	public RispostaLoggedIn updatePompaStatus(@RequestBody PompaStatus p, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			try 
			{
				pompaStatusService.updateStatus(p.getStatus(), p.getId());
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
}
