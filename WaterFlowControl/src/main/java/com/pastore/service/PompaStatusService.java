package com.pastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastore.entity.PompaStatus;
import com.pastore.repository.PompaStatusRepository;

@Service
public class PompaStatusService {

	@Autowired
	private PompaStatusRepository pompaStatusRepository;
	
	public String getPompaStatusId(int id)
	{
		return pompaStatusRepository.getPompaStatusId(id);
	}
	
	public void updateStatus(String status, int id)
	{
		pompaStatusRepository.updateStatus(status, id);
	}

	public PompaStatus getPompaStatusById(int id) 
	{
		return pompaStatusRepository.getPompaStatusById(id);
	}
}
