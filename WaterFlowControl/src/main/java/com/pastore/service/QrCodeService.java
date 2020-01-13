package com.pastore.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastore.entity.PompaStatus;
import com.pastore.entity.QrCode;
import com.pastore.entity.Socio;
import com.pastore.repository.QrCodeRepository;
import com.pastore.timers.ScansioneQrCodeTimer;
import com.pastore.utilities.MemorizzatorePompaSocio;

@Service
public class QrCodeService 
{
	@Autowired
	private QrCodeRepository qrCodeRepository;
	
	@Autowired
	private PompaStatusService pompaStatusService;

	private ScansioneQrCodeTimer codeTimer;
	
	@Autowired
	private SocioService socioService;
	
	@Autowired
	private MemorizzatorePompaSocio memorizzatorePompaSocio;
	
	private QrCode qrCodeTrovato;
	private int numero_pompa_occupata;
	
	public String codicePompa(int id) 
	{
		return qrCodeRepository.codicePompa(id);
	}

	public String getCodicePompaUno() 
	{
		return qrCodeRepository.getCodicePompaUno();
	}

	public String getCodicePompaDue() 
	{
		return qrCodeRepository.getCodicePompaDue();
	}

	public String getCodicePompaTre() 
	{
		return qrCodeRepository.getCodicePompaTre();
	}

	public void save(QrCode qrCode) 
	{
		qrCodeRepository.save(qrCode.getId(), qrCode.getCodice_pompa_uno(), qrCode.getCodice_pompa_due(), qrCode.getCodice_pompa_tre());
	}

	public boolean confrontaQrCode(QrCode qrCode) 
	{
		Iterable<QrCode> qrCodes = qrCodeRepository.findAll();
		
		List<QrCode> myList = Lists.newArrayList(qrCodes);
		
		for (int i = 0; i < myList.size(); i++)
		{
			if(myList.get(i).getCodice_pompa_uno().equals(qrCode.getCodice_pompa_uno()) || myList.get(i).getCodice_pompa_due().equals(qrCode.getCodice_pompa_due()) || myList.get(i).getCodice_pompa_tre().equals(qrCode.getCodice_pompa_tre()))
			{
				qrCodeTrovato = myList.get(i);
				return true;
			}
			
		}
		return false;
	}

	public QrCode findById(int id) 
	{
		return qrCodeRepository.findById(id);
	}
	
	public void updateQrCode(QrCode qrCode, int id) 
	{
		qrCodeRepository.updateQrCode(qrCode.getId(), qrCode.getCodice_pompa_uno(), qrCode.getCodice_pompa_due(), qrCode.getCodice_pompa_tre(), id);
	}

	public void startTimer(PompaStatus p)
	{
		codeTimer = new ScansioneQrCodeTimer(this, p);
		codeTimer.start();
	}
	
	public void stopTimer(boolean b)
	{
		codeTimer.setPompa_in_uso(b);
	}
	
	public int getNumero_pompa_occupata() 
	{
		return numero_pompa_occupata;
	}
	
	public void setNumero_pompa_occupata(int numero_pompa_occupata) 
	{
		this.numero_pompa_occupata = numero_pompa_occupata;
	}

	public void disattivaPompa(PompaStatus pompa) 
	{
		System.out.println("sono della disattiva Pompa e stampo l id della pompa che si sta per disattivare " + pompa.getId());
		pompaStatusService.updateStatus("disattiva", pompa.getId());
	}

	public void attivaPompa(HttpSession session)
	{		
		pompaStatusService.updateStatus("attiva", numero_pompa_occupata);
		PompaStatus p = pompaStatusService.getPompaStatusById(numero_pompa_occupata);
		Socio s = (Socio) session.getAttribute(session.getId().toString());
		memorizzatorePompaSocio.insertPompa(s.getUsername(), p);
		System.out.println("il socio " + s.getUsername() + "ha attivato la pompa tramite la lettura del qrcode " + p.getId());
		startTimer(p);
	}

	public boolean controllaDisponibilitaPompaCorrispondente(QrCode qrCode) 
	{
		trovaPompaCorrispondente(qrCode);
		if(pompaStatusService.getPompaStatusId(numero_pompa_occupata).equals("disattiva"))
		{
			return true;
		}
		return false;
	}
	
	private void trovaPompaCorrispondente(QrCode qrCode)
	{
		if(qrCode.getCodice_pompa_uno().equals(qrCodeTrovato.getCodice_pompa_uno()))
		{
			System.out.println("se non è occpuata attivo la pompa 1");
			numero_pompa_occupata = 1;
		}
		else if(qrCode.getCodice_pompa_due().equals(qrCodeTrovato.getCodice_pompa_due()))
		{
			System.out.println("se non è occpuata attivo la pompa 2");
			numero_pompa_occupata = 2;
		}
		else if(qrCode.getCodice_pompa_tre().equals(qrCodeTrovato.getCodice_pompa_tre()))
		{
			System.out.println("se non è occpuata attivo la pompa 3");
			numero_pompa_occupata = 3;
		}
	}

	public void abilitaSocio(Socio s) 
	{
		socioService.updateSocio(s.getUsername(), "si", s.getBarca(), s.getPassword(), s.getPostazione(), s.getProfilo());
	}
	
	public void disabilitaSocio(Socio s)
	{
		socioService.updateSocio(s.getUsername(), "no", s.getBarca(), s.getPassword(), s.getPostazione(), s.getProfilo());
	}

	public void disattivaPompaDaTimer(PompaStatus pompa) 
	{
		pompaStatusService.updateStatus("disattiva", pompa.getId());
		System.out.println("la pompa " + pompa.getId() + " è stata disattivata dal QrCodeTimer");
	}
	
	
	
}
