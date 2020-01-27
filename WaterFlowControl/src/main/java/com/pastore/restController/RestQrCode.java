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
import com.pastore.entity.RispostaLoggedIn;
import com.pastore.entity.Socio;
import com.pastore.service.ListaUtentiLoggati;
import com.pastore.service.QrCodeService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/codice")
public class RestQrCode 
{
	@Autowired
	private QrCodeService qrCodeService;
	
	@Autowired
	private ListaUtentiLoggati listaUtentiLoggati;
	
	@GetMapping(value = "/ricercapompa/{codice}")
	public ResponseEntity<RispostaLoggedIn> attivaPompaDaQrCode(@PathVariable("codice") String codice, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		
		Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			if(listaUtentiLoggati.controllaPresenzaSocio(s))
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
							return new ResponseEntity<>(new RispostaLoggedIn("true"), HttpStatus.OK);
						}
						else
						{
							System.err.println("POMPA NON DISPONIBILE");
							return new ResponseEntity<>(new RispostaLoggedIn("false"), HttpStatus.INTERNAL_SERVER_ERROR);
						}
					}
					return new ResponseEntity<>(new RispostaLoggedIn("false"), HttpStatus.INTERNAL_SERVER_ERROR);
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					return new ResponseEntity<>(new RispostaLoggedIn("false"), HttpStatus.INTERNAL_SERVER_ERROR);			
				}
			}
			else
			{
				return new ResponseEntity<>(new RispostaLoggedIn("false"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else 
		{
			return new ResponseEntity<>(new RispostaLoggedIn("false"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value = "/codice/{numero}", produces = "application/json")
	public String getCodicePompa(@PathVariable("numero") String numero, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException //ok
	{
		Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
		if (s != null)
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
		else
		{
			return "internal server error";
		}
	}

	@PostMapping(value = "/insert", produces = "application/json")
	public RispostaLoggedIn saveQrCode(@RequestBody QrCode qrCode, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException //ok
	{
		Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			qrCodeService.save(qrCode);
			return new RispostaLoggedIn("true");
		}
		return new RispostaLoggedIn("false");
	}
	
	@PostMapping(value = "/ricercaQrCode", produces = "application/json")
	public RispostaLoggedIn confrontaQrCode(@RequestBody QrCode qrCode, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		System.out.println("SONO RICERCA QRCODE " + request.getSession().getId().toString());
		Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			if(listaUtentiLoggati.controllaPresenzaSocio(s))
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
							return new RispostaLoggedIn("true");
						}
						else
						{
							System.out.println("la pompa è già stata attivata da qualche altro utente");
							return new RispostaLoggedIn("false");
						}
						
					}
					else
					{
						System.out.println("non ho trovato il qrcode corrispondente");
						return new RispostaLoggedIn("false");
					}
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
		else
		{
			return new RispostaLoggedIn("false");
		}
	}

	@GetMapping(value = "/code/{id}", produces = "application/json")
	public QrCode findById(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException//ok
	{
		Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			return qrCodeService.findById(id);
		}
		else
		{
			return null;
		}
	}

	@PutMapping(value = "/modifica/{id}", produces = "application/json")
	public RispostaLoggedIn updateQrCode(@RequestBody QrCode qrCode, @PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession(false).getAttribute(request.getSession().getId().toString());
		if (s != null)
		{	
			try 
			{
				qrCodeService.updateQrCode(qrCode, id);
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
