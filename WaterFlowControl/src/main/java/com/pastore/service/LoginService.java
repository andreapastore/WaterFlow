package com.pastore.service;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastore.entity.Socio;
import com.pastore.utilities.MemorizzatorePompaSocio;

@Service
public class LoginService 
{
	@Autowired
	private MemorizzatorePompaSocio memorizzatorePompaSocio;
	
	public void logout(HttpSession session)
	{
		Socio s = (Socio) session.getAttribute(session.getId().toString());
		memorizzatorePompaSocio.deletePompa(s.getUsername());
		System.out.println("logout del socio " + s.getUsername() + " andato a buon fine");
	}
}
