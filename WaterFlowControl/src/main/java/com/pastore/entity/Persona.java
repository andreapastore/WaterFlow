package com.pastore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Table;
import org.springframework.data.annotation.Id;

@Entity
@javax.persistence.Table(name = "persona")
public class Persona {

	@Id
	@Column(name = "nome")
	private String nome;
	
	public Persona() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
