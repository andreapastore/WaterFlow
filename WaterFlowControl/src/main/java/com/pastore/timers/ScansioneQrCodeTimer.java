package com.pastore.timers;

import org.springframework.stereotype.Component;

@Component
public class ScansioneQrCodeTimer extends Thread
{
	private boolean pompa_in_uso;

	@Override
	public void run() 
	{
		System.out.println("sono partito");
		try 
		{
			Thread.sleep(60000);
			if(pompa_in_uso)
			{
				System.out.println("l'acqua è stata aperta, la pompa è in uso");
			}
			else
			{
				System.out.println("l'acqua NON è stata aperta, pompa in disuso");
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public boolean isPompa_in_uso() 
	{
		return pompa_in_uso;
	}

	public void setPompa_in_uso(boolean pompa_in_uso) 
	{
		this.pompa_in_uso = pompa_in_uso;
	}
}
