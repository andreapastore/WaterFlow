package com.pastore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastore.entity.DettaglioSocio;
import com.pastore.repository.DettaglioSocioRepository;

@Service
public class DettaglioSocioService 
{
	@Autowired
	DettaglioSocioRepository dettaglioSocioRepository;
	
	public void saveDettaglioSocio(int id, String apertura, String chiusura, String data_attivazione_slot, int minuti, int minuti_totali, int quantita_acqua, String username)
	{
		dettaglioSocioRepository.saveDettaglioSocio(id, apertura, chiusura, data_attivazione_slot, minuti, minuti_totali, quantita_acqua, username);
	}
	
	public void updateDettaglioSocio(String username, String apertura, String chiusura, String data_attivazione_slot, int minuti, int minuti_totali, int quantita_acqua, String socio_username)
	{
		dettaglioSocioRepository.updateDettaglioSocio(username, apertura, chiusura, data_attivazione_slot, minuti, minuti_totali, quantita_acqua, socio_username);
	}
	
	public DettaglioSocio getDettaglioSocioById(String id)
	{
		Iterable<DettaglioSocio> list = dettaglioSocioRepository.findAllByUsername(id);
		DettaglioSocio d = new DettaglioSocio();
		d.setId(0);
		List<DettaglioSocio> result = new ArrayList<DettaglioSocio>();
		list.forEach(result::add);
		
		for (int i = 0; i < result.size(); i++)
		{
			if (result.get(i).getId() > d.getId())
			{
				d = result.get(i);
			}
		}
		
		return d;
	}

	public Iterable<DettaglioSocio> getDettaglioSocioByUsername(String id)
	{
		return dettaglioSocioRepository.findAllByUsername(id);
	}
	
	public int getMinutiTotali(String id)
	{
		return dettaglioSocioRepository.getMinutiTotali(id);
	}

	public Iterable<DettaglioSocio> ricercaTutto()
	{
		return dettaglioSocioRepository.findAll();
	}
	
	public int tornaIdMax()
	{
		return dettaglioSocioRepository.tornaIdMax();
	}
}
