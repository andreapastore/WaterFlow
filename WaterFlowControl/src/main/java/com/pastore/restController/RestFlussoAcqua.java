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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.Socio;
import com.pastore.service.FlussoAcquaService;
import com.pastore.service.LedService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/flussoAcqua")
public class RestFlussoAcqua {
	
	//@SuppressWarnings("unused")
	//@Autowired
	//private LedService ledService;
	
	@Autowired
	private FlussoAcquaService flussoAcquaService;
	
	@GetMapping(value = "/apri", produces = "application/json")
	public ResponseEntity<HttpStatus> apri(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		try
		{
			flussoAcquaService.apri(request.getSession());
			//ledService.lightOn();
			System.out.println("il socio " + s.getUsername() + " è entrato nella apri");
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/chiudi", produces = "application/json")
	public ResponseEntity<HttpStatus> chiudi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		try
		{
			flussoAcquaService.chiudi(request.getSession());
			//ledService.lightOff();
			System.out.println("il socio " + s.getUsername() + " è entrato nella chiudi");
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
