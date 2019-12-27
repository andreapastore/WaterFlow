package com.pastore.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pastore.entity.DettaglioSocio;

public interface DettaglioSocioRepository extends CrudRepository<DettaglioSocio, String>
{
	@Transactional
	@Query(value = "INSERT INTO dettagliosocio (id, apertura, chiusura, data_attivazione_slot, minuti, minuti_totali, quantita_acqua, username) values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)", nativeQuery = true)
	public void saveDettaglioSocio(@Param ("id") String username, @Param("apertura") String apertura, @Param("chiusura") String chiusura, @Param("data_attivazione_slot") String data_attivazione_slot, @Param("minuti") int minuti, @Param("minuti_totali") int minuti_totali, @Param("quantita_acqua") int quantita_acqua, @Param("username") String socio_username);
	
	//public void updateDettaglioSocio();
	
	@Query(value = "SELECT * FROM dettagliosocio WHERE id = ?1", nativeQuery = true)
	public DettaglioSocio getDettaglioSocioByUsername(@Param("id") String id);
	
}