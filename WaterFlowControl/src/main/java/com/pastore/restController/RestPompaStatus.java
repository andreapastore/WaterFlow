package com.pastore.restController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pastore.entity.PompaStatus;
import com.pastore.entity.RispostaLoggedIn;
import com.pastore.entity.Socio;
import com.pastore.service.PompaStatusService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/pompaStatus")
public class RestPompaStatus 
{
	@Autowired
	private PompaStatusService pompaStatusService;
	
	@GetMapping(value = "/getPompaStatus/{id}", produces = "application/json")
	public PompaStatus getPompaStatusById(@PathVariable ("id") int id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			try 
			{
				PompaStatus p = pompaStatusService.getPompaStatusById(id);
				if(p != null)
				{
					return p;
				}
				else
				{
					return null;
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	@PutMapping(value = "/insertPompaStatus", produces = "application/json")
	public RispostaLoggedIn insertPompaStatus(@RequestBody PompaStatus p, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{	
			try 
			{
				pompaStatusService.insertPompaStatus(p.getId(), p.getStatus());
				return new RispostaLoggedIn("true");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return new RispostaLoggedIn("false");
			}
		}
		else
		{
			return new RispostaLoggedIn("false");
		}
	}
	
	@PostMapping(value = "/updatePompaStatus", produces = "application/json")
	public RispostaLoggedIn updatePompaStatus(@RequestBody PompaStatus p, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			try 
			{
				pompaStatusService.updateStatus(p.getStatus(), p.getId());
				return new RispostaLoggedIn("true");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return new RispostaLoggedIn("false");
			}
		}
		else
		{
			return new RispostaLoggedIn("false");
		}
	}
}
