package com.pastore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastore.entity.Socio;
import com.pastore.repository.SocioRepository;

@Service
public class SocioService 
{
	@Autowired
	private SocioRepository socioRepository;
	
	public void insertSocio(String username, String abilitato, String barca, String password, String postazione, String profilo)
	{
		socioRepository.insertSocio(username, abilitato, barca, password, postazione, profilo);
	}

	public Iterable<Socio> ricercaTutto() 
	{
		return socioRepository.findAll();
	}

	public Optional<Socio> ricercaSocioByUsername(String username) 
	{
		return socioRepository.findById(username);
	}

	public void cancellaSocioByUsername(String username) 
	{
		socioRepository.deleteById(username);
	}

	public void updateSocio(String username, String abilitato, String barca, String password, String postazione, String profilo) 
	{
		socioRepository.updateSocio(username, abilitato, barca, password, postazione, profilo);	
	}


}
