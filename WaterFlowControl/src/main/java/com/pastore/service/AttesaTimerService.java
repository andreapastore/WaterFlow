package com.pastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastore.entity.AttesaTimer;
import com.pastore.repository.AttesaTimerRepository;

@Service
public class AttesaTimerService {

	@Autowired
	private AttesaTimerRepository attesaTimerRepository;
	
	public void inserisciNuovoMinutaggioTimerAcqua (AttesaTimer attesa)
	{
		this.attesaTimerRepository.inserisciMinutaggioTimer(attesa.getNome_timer(), attesa.getMinuti());
	}
	
	public int ritornaMinutiDiAttesaQrCode()
	{
		return this.attesaTimerRepository.ritornaMinutiDiAttesaQrCode();
	}
	
	public int ritornaMinutiDiAttesaFlussoAcqua()
	{
		return this.attesaTimerRepository.ritornaMinutiDiAttesaFlussoAcqua();
	}
	
	public void setMinutiDiAttesaQrCode(int minuti)
	{
		this.attesaTimerRepository.setMinutiDiAttesaQrCode(minuti);
	}
	
	public void setMinutiDiAttesaFlussoAcqua(int minuti)
	{
		this.attesaTimerRepository.setMinutiDiAttesaFlussoAcqua(minuti);
	}
}
