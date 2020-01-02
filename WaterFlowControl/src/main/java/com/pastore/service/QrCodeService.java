package com.pastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastore.entity.QrCode;
import com.pastore.repository.QrCodeRepository;

@Service
public class QrCodeService {

	@Autowired
	private QrCodeRepository qrCodeRepository;
	
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
}
