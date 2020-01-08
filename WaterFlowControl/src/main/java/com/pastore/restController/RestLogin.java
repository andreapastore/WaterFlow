package com.pastore.restController;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping(value = "/login", produces = "application/json")
	public ResponseEntity<Socio> socioLogin(@RequestBody Socio socio)
	{
		try 
		{
			Optional<Socio> s = socioService.ricercaSocioByUsername(socio.getUsername());
			if (!s.isEmpty())
			{
				if (socio.getPassword().equals(s.get().getPassword()))
				{
					System.out.println("ho trovato il socio");
					return new ResponseEntity<>(s.get(), HttpStatus.OK);
				}
				else
				{
					System.out.println("password errata");
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
				
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
