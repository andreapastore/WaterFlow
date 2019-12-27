package com.pastore.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.handlers.StringHandler;
import com.pastore.service.QrCodeService;

@RestController
@RequestMapping(value = "/api/codice")
public class RestQrCode 
{
	@Autowired
	private QrCodeService qrCodeService;
	
	@GetMapping(value = "/codice/{numero}", produces = "application/json")
	public String getCodicePompa(@PathVariable("numero") String numero)
	{
		try 
		{
			String result = null;
			
			switch (numero) {
			case "1":
				result = qrCodeService.getCodicePompaUno();
				break;
			case "2": 
				result = qrCodeService.getCodicePompaDue();
				break;
			case "3": 
				result = qrCodeService.getCodicePompaTre();
				break;
			default:
				break;
			}
			
			if (result == null)
			{
				return "no content";
			}
			else
			{
				return result;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "no content";
		}
	}
}
