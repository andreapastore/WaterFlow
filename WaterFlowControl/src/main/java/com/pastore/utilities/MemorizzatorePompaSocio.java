package com.pastore.utilities;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.pastore.entity.PompaStatus;

@Component
public class MemorizzatorePompaSocio 
{
	private HashMap<String, PompaStatus> hashSocioPompa;
	
	public MemorizzatorePompaSocio ()
	{
		hashSocioPompa = new HashMap<>();
	}
	
	public void insertPompa(String username, PompaStatus p)
	{
		if(hashSocioPompa == null)
		{
			hashSocioPompa = new HashMap<>();
		}
		hashSocioPompa.put(username, p);
	}
	
	public PompaStatus getPompa(String username)
	{
		return hashSocioPompa.get(username);
	}
	
	public void deletePompa(String username)
	{
		hashSocioPompa.remove(username);
	}
	
	public boolean esisteSocio(String username)
	{
		return hashSocioPompa.containsKey(username);
	}
}
