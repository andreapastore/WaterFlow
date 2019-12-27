package com.pastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastore.entity.DettaglioSocio;
import com.pastore.repository.DettaglioSocioRepository;

@Service
public class DettaglioSocioService 
{
	@Autowired
	DettaglioSocioRepository dettaglioSocioRepository;
	
	public void saveDettaglioSocio(String username, String apertura, String chiusura, String data_attivazione_slot, int minuti, int minuti_totali, int quantita_acqua, String socio_username)
	{
		dettaglioSocioRepository.saveDettaglioSocio(username, apertura, chiusura, data_attivazione_slot, minuti, minuti_totali, quantita_acqua, socio_username);
	}
	
	public void updateDettaglioSocio()
	{
		
	}
	
	public DettaglioSocio getDettaglioSocioById(String id)
	{
		return dettaglioSocioRepository.getDettaglioSocioByUsername(id);
	}
}
