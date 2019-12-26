package com.pastore.restController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.Socio;
import com.pastore.service.SocioService;

@RestController
@RequestMapping(value = "/api/socio")
public class RestSocio {

	@Autowired
	SocioService socioService;
	
	@PostMapping(value = "/insert", produces = "application/json")
	public ResponseEntity insertSocio(@RequestBody Socio socio)
	{
		try
		{
			socioService.insertSocio(socio.getUsername(), socio.getAbilitato(), socio.getBarca(), socio.getPassword(), socio.getPostazione(), socio.getProfilo());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/ricercatutto", produces = "application/json")
	public ResponseEntity<Iterable<Socio>> ricercaTutto()
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
	
	@GetMapping(value = "/ricerca/{username}", produces = "application/json")
	public ResponseEntity<Socio> ricercaSocioByUsername(@PathVariable("username") String username)
	{
		try 
		{
			Optional<Socio> socio = socioService.ricercaSocioByUsername(username);
			
			if (socio != null)
			{
				return new ResponseEntity<Socio>(socio.get(), HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<Socio>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<Socio>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
}
