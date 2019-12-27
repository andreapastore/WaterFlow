package com.pastore.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.DettaglioSocio;
import com.pastore.service.DettaglioSocioService;

@RestController
@RequestMapping(value = "/api/dettagliosocio")
public class RestDettaglioSocio {

	@Autowired
	private DettaglioSocioService dettaglioSocioService;
	
	@PostMapping(value = "/insertdett", produces = "application/json")
	public ResponseEntity saveDettaglioSocio(@RequestBody DettaglioSocio dettaglioSocio)
	{
		try 
		{
			dettaglioSocioService.saveDettaglioSocio(dettaglioSocio.getId(), dettaglioSocio.getApertura(), dettaglioSocio.getChiusura(), dettaglioSocio.getData_attivazione_slot(), dettaglioSocio.getMinuti(), dettaglioSocio.getMinuti_totali(), dettaglioSocio.getQuantita_acqua(), dettaglioSocio.getSocio().getUsername());
			return new ResponseEntity(HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/dettaglio/{id}", produces = "application/json")
	public ResponseEntity<DettaglioSocio> getDettaglioSocioById(@PathVariable("id") String id)
	{
		try 
		{
			DettaglioSocio d = dettaglioSocioService.getDettaglioSocioById(id);
			if (d != null)
			{
				return new ResponseEntity(d, HttpStatus.OK);
			}
			else
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}