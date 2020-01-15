package com.pastore.restController;

import java.util.Optional;

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

import com.pastore.entity.Socio;
import com.pastore.service.SocioService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/socio")
public class RestSocio {

	@Autowired
	SocioService socioService;
	
	@PostMapping(value = "/insert", produces = "application/json")
	public ResponseEntity<HttpStatus> insertSocio(@RequestBody Socio socio)//ok
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
	public ResponseEntity<Iterable<Socio>> ricercaTutto()//ok
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
	public ResponseEntity<Socio> ricercaSocioByUsername(@PathVariable("username") String username)//ok
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
		} catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<Socio>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}

	@DeleteMapping(value = "/cancella/{username}")
	public ResponseEntity<HttpStatus> cancellaSocioByUsername(@PathVariable("username") String username) //ok
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
	
	//nel json i campi devono essere già aggiornati
	@PutMapping(value = "/modifica/{username}", produces = "application/json")
	public ResponseEntity<HttpStatus> updateSocio(@RequestBody Socio socio, @PathVariable("username") String username) //ok
	{
		try 
		{
			socioService.updateSocio(username, socio.getAbilitato(), socio.getBarca(), socio.getPassword(), socio.getPostazione(), socio.getProfilo());
			return new ResponseEntity<>(HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/updatePass", produces = "application/json")
	public ResponseEntity<HttpStatus> updatePassword(@RequestBody Socio socio) //ok
	{
		try 
		{
			socioService.updateSocio(socio.getUsername(), socio.getAbilitato(), socio.getBarca(), socio.getPassword(), socio.getPostazione(), socio.getProfilo());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/updatePass/{username}")
	public ResponseEntity<HttpStatus> updatePasswordConUser(@RequestBody Socio socio, @PathVariable("username") String username) //ok
	{
		try 
		{
			socioService.updateSocio(username, socio.getAbilitato(), socio.getBarca(), socio.getPassword(), socio.getPostazione(), socio.getProfilo());
			return new ResponseEntity<>(HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
