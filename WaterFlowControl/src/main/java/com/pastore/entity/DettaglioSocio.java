package com.pastore.entity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DETTAGLIOSOCIO")
public class DettaglioSocio implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "data_attivazione_slot")
	private String data_attivazione_slot;
	
	@Column(name = "apertura")
	private String apertura;
	
	@Column(name = "chiusura")
	private String chiusura;
	
	@Column(name = "minuti")
	private int minuti;
	
	@Column(name = "minuti_totali")
	private int minuti_totali;
	
	@Column(name = "quantita_acqua")
	private int quantita_acqua;
	
	@Column(name = "username")
	private String socio_username;
	
	public DettaglioSocio () {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData_attivazione_slot() {
		return data_attivazione_slot;
	}

	public void setData_attivazione_slot(String data_attivazione_slot) {
		this.data_attivazione_slot = data_attivazione_slot;
	}

	public String getApertura() {
		return apertura;
	}

	public void setApertura(String apertura) {
		this.apertura = apertura;
	}

	public String getChiusura() {
		return chiusura;
	}

	public void setChiusura(String chiusura) {
		this.chiusura = chiusura;
	}

	public int getMinuti() {
		return minuti;
	}

	public void setMinuti(int minuti) {
		this.minuti = minuti;
	}

	public int getMinuti_totali() {
		return minuti_totali;
	}

	public void setMinuti_totali(int minuti_totali) {
		this.minuti_totali = minuti_totali;
	}

	public int getQuantita_acqua() {
		return quantita_acqua;
	}

	public void setQuantita_acqua(int quantita_acqua) {
		this.quantita_acqua = quantita_acqua;
	}

	public String getSocio_username() {
		return socio_username;
	}

	public void setSocio_username(String socio_username) {
		this.socio_username = socio_username;
	}

	
	
	
}
