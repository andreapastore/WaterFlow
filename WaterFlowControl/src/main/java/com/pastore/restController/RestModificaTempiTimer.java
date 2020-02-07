package com.pastore.restController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pastore.entity.RispostaLoggedIn;
import com.pastore.entity.Socio;
import com.pastore.service.AttesaTimerService;
import com.pastore.service.FlussoAcquaService;
import com.pastore.service.QrCodeService;

@RestController
@RequestMapping(value = "/api/modificatimer")
public class RestModificaTempiTimer {

	@Autowired
	private QrCodeService qrCodeService;
	
	@Autowired
	private FlussoAcquaService flussoAcquaService;
	
	@Autowired
	private AttesaTimerService attesaTimerService;
	
	@GetMapping(value = "/timerqrcode/{numero}")
	public RispostaLoggedIn modificaTimerQrCode(@PathVariable("numero") int numero, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			if (s.getProfilo().equals("admin"))
			{
				qrCodeService.setMinuti_attesa_timer_flusso_acqua(numero);
				return new RispostaLoggedIn("true");
			}
		}
		return new RispostaLoggedIn("false");
		
	}
	
	@GetMapping(value = "/timerflussoacqua/{numero}")
	public RispostaLoggedIn modificaTimerFlussoAcqua(@PathVariable("numero") int numero, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			if (s.getProfilo().equals("admin"))
			{
				flussoAcquaService.setNumero_minuti_di_attesa(numero);
				return new RispostaLoggedIn("true");
			}
		}
		return new RispostaLoggedIn("false");
	}
	
	@GetMapping(value = "/gettimerqrcode", produces = "application/json")
	public RispostaLoggedIn getTimerQrCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			if (s.getProfilo().equals("admin"))
			{
				RispostaLoggedIn r = new RispostaLoggedIn("true");
				r.setTimerqrcode(attesaTimerService.ritornaMinutiDiAttesaQrCode());
				return r;
			}
		}
		return new RispostaLoggedIn("false");
	}
	
	@GetMapping(value = "/gettimerflussoacqua", produces = "application/json")
	public RispostaLoggedIn getTimerFlussoAcqua(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			if (s.getProfilo().equals("admin"))
			{
				RispostaLoggedIn r = new RispostaLoggedIn("true");
				r.setTimerflussoacqua(attesaTimerService.ritornaMinutiDiAttesaFlussoAcqua());
				return r;
			}
		}
		return new RispostaLoggedIn("false");
	}
	
	@GetMapping(value = "/qrcode/{numero}")
	public RispostaLoggedIn modificaTimerQrCodeDB(@PathVariable("numero") int numero, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			if (s.getProfilo().equals("admin"))
			{
				this.attesaTimerService.setMinutiDiAttesaQrCode(numero);
				return new RispostaLoggedIn("true");
			}
		}
		return new RispostaLoggedIn("false");
		
	}
	
	@GetMapping(value = "/flussoacqua/{numero}")
	public RispostaLoggedIn modificaTimerFlussoAcquaDB(@PathVariable("numero") int numero, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException
	{
		Socio s = (Socio) request.getSession().getAttribute(request.getSession().getId().toString());
		if (s != null)
		{
			if (s.getProfilo().equals("admin"))
			{
				this.attesaTimerService.setMinutiDiAttesaFlussoAcqua(numero);
				return new RispostaLoggedIn("true");
			}
		}
		return new RispostaLoggedIn("false");
	}
}
