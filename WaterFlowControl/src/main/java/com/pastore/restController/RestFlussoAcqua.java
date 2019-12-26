package com.pastore.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.service.LedService;

@RestController
@RequestMapping(value = "/api/flussoAcqua")
public class RestFlussoAcqua {
	
	@Autowired
	private LedService ledService;
	
	@GetMapping(value = "/apri", produces = "application/json")
	public ResponseEntity<HttpStatus> apri()
	{
		try
		{
			//ledService.lightOn();
			System.out.println("SONO NELLA APRIIIIIIIIIII");
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/chiudi", produces = "application/json")
	public ResponseEntity<HttpStatus> chiudi()
	{
		try
		{
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
