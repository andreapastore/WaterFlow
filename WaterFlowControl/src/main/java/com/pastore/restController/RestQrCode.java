package com.pastore.restController;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.pastore.entity.QrCode;
import com.pastore.service.QrCodeService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/codice")
public class RestQrCode 
{
	@Autowired
	private QrCodeService qrCodeService;
	
	@GetMapping(value = "/ricercapompa/{codice}")
	public boolean attivaPompaDaQrCode(@PathVariable("codice") String codice, HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			if(qrCodeService.confrontaQrCodeAvendoCodice(codice))
			{
				qrCodeService.trovaPompaCorrispondenteConCodice(codice);
				if(qrCodeService.controllaDisponibilitaPompa())
				{
					HttpSession currentSession = request.getSession();
					qrCodeService.attivaPompa(currentSession);
					return true;
				}
			}
			return false;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;			
		}
		
	}
	
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
			return "internal server error";
		}
	}

	@PostMapping(value = "/insert", produces = "application/json")
	public void saveQrCode(@RequestBody QrCode qrCode) //ok
	{
		qrCodeService.save(qrCode);
	}
	
	@PostMapping(value = "/ricercaQrCode", produces = "application/json")
	public ResponseEntity<HttpStatus> confrontaQrCode(@RequestBody QrCode qrCode, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		try
		{
			if (qrCodeService.confrontaQrCode(qrCode))
			{
				System.out.println("ho trovato il qrCode corrispondente");
				//una volta trovato il qrcode devo fare il check se la pompa è libera o meno
				if(qrCodeService.controllaDisponibilitaPompaCorrispondente(qrCode))
				{
					HttpSession currentSession = request.getSession();
					qrCodeService.attivaPompa(currentSession);
					return new ResponseEntity<HttpStatus>(HttpStatus.OK);
				}
				else
				{
					System.out.println("la pompa è già stata attivata da qualche altro utente");
					return new ResponseEntity<HttpStatus>(HttpStatus.SERVICE_UNAVAILABLE);
				}
				
			}
			else
			{
				System.out.println("non ho trovato il qrcode corrispondente");
				return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
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
