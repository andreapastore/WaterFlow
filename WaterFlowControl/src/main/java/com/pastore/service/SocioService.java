package com.pastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
