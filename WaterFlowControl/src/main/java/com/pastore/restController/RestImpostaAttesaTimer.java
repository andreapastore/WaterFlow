package com.pastore.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pastore.entity.AttesaTimer;
import com.pastore.service.AttesaTimerService;

@RestController
@RequestMapping(value ="/api/impostaattesatimer")
public class RestImpostaAttesaTimer 
{
	@Autowired
	private AttesaTimerService attesaTimerService;
	
	@PostMapping(value = "/inseriscinuovominutaggiotimeracqua", produces = "application/json")
	public void inserisciNuovoMinutaggioTimerAcqua(@RequestBody AttesaTimer attesa)
	{
		this.attesaTimerService.inserisciNuovoMinutaggioTimerAcqua(attesa);
	}
}
