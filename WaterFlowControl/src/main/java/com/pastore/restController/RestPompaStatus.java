package com.pastore.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.PompaStatus;
import com.pastore.service.PompaStatusService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/pompaStatus")
public class RestPompaStatus 
{
	@Autowired
	private PompaStatusService pompaStatusService;
	
	@GetMapping(value = "/getPompaStatus/{id}", produces = "application/json")
	public ResponseEntity<PompaStatus> getPompaStatusById(@PathVariable ("id") int id) //ok
	{
		try 
		{
			PompaStatus p = pompaStatusService.getPompaStatusById(id);
			if(p != null)
			{
				return new ResponseEntity<PompaStatus>(p, HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/insertPompaStatus", produces = "application/json")
	public ResponseEntity<HttpStatus> insertPompaStatus(@RequestBody PompaStatus p)
	{
		try 
		{
			pompaStatusService.insertPompaStatus(p.getId(), p.getStatus());
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/updatePompaStatus", produces = "application/json")
	public ResponseEntity<HttpStatus> updatePompaStatus(@RequestBody PompaStatus p)
	{
		try 
		{
			pompaStatusService.updateStatus(p.getStatus(), p.getId());
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
