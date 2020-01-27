package com.pastore.timers;

import com.pastore.entity.PompaStatus;
import com.pastore.service.QrCodeService;

public class ScansioneQrCodeTimer extends Thread
{
	private QrCodeService qrCodeService;
	private PompaStatus pompa;
	private boolean pompa_in_uso;
	private int minuti;

	public ScansioneQrCodeTimer(QrCodeService q, PompaStatus p, int m) 
	{
		this.qrCodeService = q;
		this.pompa = p;
		m *= 60000;
		this.minuti = m;
	}
	
	@Override
	public void run() 
	{
		System.out.println("timer scansione qrcode partito");
		try 
		{
			Thread.sleep(minuti);
			if(pompa_in_uso)
			{
				System.out.println("l'acqua è stata aperta, la pompa è in uso, timer scansione qrcode disattivato");
			}
			else
			{
				System.out.println("l'acqua NON è stata aperta, pompa in disuso, torna disattiva");
				qrCodeService.disattivaPompaDaTimer(pompa);
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
