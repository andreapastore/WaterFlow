package com.pastore.entity;

public class RispostaLoggedIn 
{
	private String response;
	private String username;
	private int posto;
	private String barca;
	private String profilo;
	
	public RispostaLoggedIn(String r)
	{
		this.response = r;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPosto() {
		return posto;
	}

	public void setPosto(int posto) {
		this.posto = posto;
	}

	public String getBarca() {
		return barca;
	}

	public void setBarca(String barca) {
		this.barca = barca;
	}

	public String getProfilo() {
		return profilo;
	}

	public void setProfilo(String profilo) {
		this.profilo = profilo;
	}
	
	
}
