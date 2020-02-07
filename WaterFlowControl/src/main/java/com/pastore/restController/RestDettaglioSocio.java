package com.pastore.restController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.DettaglioSocio;
import com.pastore.entity.RispostaLoggedIn;
import com.pastore.entity.Socio;
import com.pastore.service.DettaglioSocioService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/dettagliosocio")
public class RestDettaglioSocio {

	@Autowired
	private DettaglioSocioService dettaglioSocioService;
	
	@PostMapping(value = "/insertdett", produces = "application/json")
	public RispostaLoggedIn saveDettaglioSocio(@RequestBody DettaglioSocio dettaglioSocio, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{	
			try 
			{
				dettaglioSocioService.saveDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getSocio_username());
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
	
	@GetMapping(value = "/dettaglio/{id}", produces = "application/json")
	public DettaglioSocio getDettaglioSocioById(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			try 
			{
				DettaglioSocio d = dettaglioSocioService.getDettaglioSocioById(id);
				if (d != null)
				{
					return d;
				}
				else
					return null;
				
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
	
	@GetMapping(value = "/ricercatutto", produces = "application/json")
	public ResponseEntity<Iterable<DettaglioSocio>> ricercaTutto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{	
			try 
			{
				Iterable<DettaglioSocio> dettaglioSoci = dettaglioSocioService.ricercaTutto();
				return new ResponseEntity<Iterable<DettaglioSocio>>(dettaglioSoci, HttpStatus.OK);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return new ResponseEntity<Iterable<DettaglioSocio>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
			return new ResponseEntity<Iterable<DettaglioSocio>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
