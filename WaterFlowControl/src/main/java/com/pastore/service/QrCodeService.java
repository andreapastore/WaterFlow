package com.pastore.service;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastore.entity.QrCode;
import com.pastore.repository.QrCodeRepository;
import com.pastore.timers.ScansioneQrCodeTimer;

@Service
public class QrCodeService 
{
	@Autowired
	private QrCodeRepository qrCodeRepository;
	
	@Autowired
	private PompaStatusService pompaStatusService;
	
	@Autowired
	private ScansioneQrCodeTimer codeTimer;
	
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

	public void startTimer()
	{
		codeTimer.start();
	}
	

	public void occupaPompaCorrispondente(QrCode qrCode) 
	{
		if(qrCode.getCodice_pompa_uno().equals(qrCodeTrovato.getCodice_pompa_uno()))
		{
			System.out.println("se non è occpuata attivo la pompa 1");
			pompaStatusService.updateStatus("occupata", 1);
			numero_pompa_occupata = 1;
		}
		else if(qrCode.getCodice_pompa_due().equals(qrCodeTrovato.getCodice_pompa_due()))
		{
			System.out.println("se non è occpuata attivo la pompa 2");
			pompaStatusService.updateStatus("occupata", 2);
			numero_pompa_occupata = 2;
		}
		else if(qrCode.getCodice_pompa_tre().equals(qrCodeTrovato.getCodice_pompa_tre()))
		{
			System.out.println("se non è occpuata attivo la pompa 3");
			pompaStatusService.updateStatus("occupata", 3);
			numero_pompa_occupata = 3;
		}
	}

	
	public int getNumero_pompa_occupata() 
	{
		return numero_pompa_occupata;
	}
	

	public void setNumero_pompa_occupata(int numero_pompa_occupata) 
	{
		this.numero_pompa_occupata = numero_pompa_occupata;
	}

	public void disattivaPompa() 
	{
		pompaStatusService.updateStatus("disattiva", numero_pompa_occupata);
		System.out.println("la pompa" + numero_pompa_occupata + "è stata dichiarata disattiva ");
	}

	public void attivaPompa()
	{
		pompaStatusService.updateStatus("attiva", numero_pompa_occupata);
	}
}
