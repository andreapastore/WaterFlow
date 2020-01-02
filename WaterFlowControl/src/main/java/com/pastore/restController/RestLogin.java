package com.pastore.restController;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.Socio;

@RestController
@RequestMapping(value = "/api")
public class RestLogin 
{
	@GetMapping(value = "/login", produces = "application/json")
	public ResponseEntity<Socio> socioLogin(Principal principal)
	{
		System.out.println("SONO NELLA LOGIN");
		try 
		{
			//principal.
		} 
		catch (Exception e) 
		{
			
		}
		return null;
	}
}
