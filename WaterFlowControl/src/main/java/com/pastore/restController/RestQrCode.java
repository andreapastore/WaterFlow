package com.pastore.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastore.entity.QrCode;
import com.pastore.handlers.StringHandler;
import com.pastore.service.QrCodeService;

@RestController
@RequestMapping(value = "/api/codice")
public class RestQrCode 
{
	@Autowired
	private QrCodeService qrCodeService;
	
	@GetMapping(value = "/codice/{numero}", produces = "application/json")
	public String getCodicePompa(@PathVariable("numero") String numero) //ok
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

	@PostMapping(value = "/insert", produces = "application/json")
	public QrCode saveQrCode(@RequestBody QrCode qrCode) //ok
	{
		qrCodeService.save(qrCode);
		return null;
	}
	
	@PostMapping(value = "/ricercaQrCode", produces = "application/json")
	public ResponseEntity confrontaQrCode(@RequestBody QrCode qrCode) //ok
	{
		try
		{
			if (qrCodeService.confrontaQrCode(qrCode))
			{
				System.out.println("ho trovato il qrCode corrispondente");
				return new ResponseEntity(HttpStatus.OK);
			}
			else
			{
				System.out.println("non ho trovato il qrcode corrispondente");
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/code/{id}")
	public QrCode findById(@PathVariable("id") int id) //ok
	{
		return qrCodeService.findById(id);
	}

	@PutMapping(value = "/modifica/{id}", produces = "application/json")
	public ResponseEntity<HttpStatus> updateQrCode(@RequestBody QrCode qrCode, @PathVariable("id") int id)
	{
		try 
		{
			qrCodeService.updateQrCode(qrCode, id);
			return new ResponseEntity<>(HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
