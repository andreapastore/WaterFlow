package com.pastore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ATTESATIMER")
public class AttesaTimer {

	@Id
	@Column(name = "nome_timer")
	private String nome_timer;
	
	@Column(name = "minuti")
	private int minuti;
	
	public AttesaTimer() {}

	public String getNome_timer() {
		return nome_timer;
	}

	public void setNome_timer(String nome_timer) {
		this.nome_timer = nome_timer;
	}

	public int getMinuti() {
		return minuti;
	}

	public void setMinuti(int minuti) {
		this.minuti = minuti;
	}
}
