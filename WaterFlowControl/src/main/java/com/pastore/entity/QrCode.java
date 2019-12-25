package com.pastore.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QRCODE")
public class QrCode implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "codice_pompa_uno")
	private String codice_pompa_uno;
	
	@Column(name = "codice_pompa_due")
	private String codie_pompa_due;
	
	@Column(name = "codice_pompa_tre")
	private String codice_pompa_tre;
	
	public QrCode() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodice_pompa_uno() {
		return codice_pompa_uno;
	}

	public void setCodice_pompa_uno(String codice_pompa_uno) {
		this.codice_pompa_uno = codice_pompa_uno;
	}

	public String getCodie_pompa_due() {
		return codie_pompa_due;
	}

	public void setCodie_pompa_due(String codie_pompa_due) {
		this.codie_pompa_due = codie_pompa_due;
	}

	public String getCodice_pompa_tre() {
		return codice_pompa_tre;
	}

	public void setCodice_pompa_tre(String codice_pompa_tre) {
		this.codice_pompa_tre = codice_pompa_tre;
	}
	
	
}
