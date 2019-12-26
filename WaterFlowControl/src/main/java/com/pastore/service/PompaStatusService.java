package com.pastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastore.repository.PompaStatusRepository;

@Service
public class PompaStatusService {

	@Autowired
	private PompaStatusRepository pompaStatusRepository;
	
	public int getPompaStatusId(int id)
	{
		return pompaStatusRepository.getPompaStatusId(id);
	}
	
	public void updateStatus(String status, int id)
	{
		pompaStatusRepository.updateStatus(status, id);
	}
}
