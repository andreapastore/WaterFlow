package com.pastore.restController;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.Socio;
import com.pastore.service.SocioService;

@RestController
@RequestMapping(value = "/api")
public class RestLogin 
{
	@Autowired
	private SocioService socioService;
	
	//@Autowired
	//private WebSecurityConfig webSecurityConfig;
	
	@GetMapping(value = "/login", produces = "application/json")
	public ResponseEntity<Socio> socioLogin(Principal principal)
	{
		try 
		{
			String user = "user";
			Optional<Socio> socio = socioService.ricercaSocioByUsername(user);
			if (!socio.isEmpty())
			{
				System.out.println("ho trovato il socio");
				//webSecurityConfig.salvaInConfigurazione(socio.get().getUsername(), socio.get().getPassword());
				return new ResponseEntity<>(socio.get(), HttpStatus.OK);
			}
			else
			{
				System.out.println("non ho trovato il socio");
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
