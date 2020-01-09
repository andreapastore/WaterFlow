package com.pastore.restController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.Socio;
import com.pastore.service.FlussoAcquaService;
import com.pastore.service.LedService;

@RestController
@RequestMapping(value = "/api/flussoAcqua")
public class RestFlussoAcqua {
	
	@SuppressWarnings("unused")
	@Autowired
	private LedService ledService;
	
	@Autowired
	private FlussoAcquaService flussoAcquaService;
	
	//simulo l'attivazione della pompa 1 
	@GetMapping(value = "/apri", produces = "application/json")
	public ResponseEntity<HttpStatus> apri(HttpServletRequest request, HttpServletResponse response)
	{
		/*
		Socio s = (Socio) currentSession.getAttribute("andreapastore");
		System.out.println("sono il socio restituito dalla sessione " + s.getUsername());
		*/
		try
		{
			flussoAcquaService.gestisciSessione(request.getSession());
			flussoAcquaService.apri();
			//ledService.lightOn();
			System.out.println("SONO NELLA APRIIIIIIIIIII");
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//simulo la disattivazione della pompa 1
	@GetMapping(value = "/chiudi", produces = "application/json")
	public ResponseEntity<HttpStatus> chiudi()
	{
		try
		{
			flussoAcquaService.setChiusa_da_utente(true);
			flussoAcquaService.chiudi();
			//ledService.lightOff();
			System.out.println("SONO NELLA CHIUDIIIIII");
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
