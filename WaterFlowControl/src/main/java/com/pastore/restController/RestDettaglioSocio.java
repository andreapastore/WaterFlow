package com.pastore.restController;

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
import com.pastore.entity.Socio;
import com.pastore.service.DettaglioSocioService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/dettagliosocio")
public class RestDettaglioSocio {

	@Autowired
	private DettaglioSocioService dettaglioSocioService;
	
	@PostMapping(value = "/insertdett", produces = "application/json")
	public ResponseEntity<HttpStatus> saveDettaglioSocio(@RequestBody DettaglioSocio dettaglioSocio) //ok
	{
		try 
		{
			dettaglioSocioService.saveDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getSocio().getUsername());
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/dettaglio/{id}", produces = "application/json")
	public ResponseEntity<DettaglioSocio> getDettaglioSocioById(@PathVariable("id") String id) //ok
	{
		try 
		{
			DettaglioSocio d = dettaglioSocioService.getDettaglioSocioById(id);
			if (d != null)
			{
				return new ResponseEntity<DettaglioSocio>(d, HttpStatus.OK);
			}
			else
				return new ResponseEntity<DettaglioSocio>(HttpStatus.NO_CONTENT);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/ricercatutto", produces = "application/json")
	public ResponseEntity<Iterable<DettaglioSocio>> ricercaTutto()//ok
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
}
