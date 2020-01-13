package com.pastore.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastore.entity.DettaglioSocio;

@Repository
public interface DettaglioSocioRepository extends CrudRepository<DettaglioSocio, String>
{
	@Transactional
	@Query(value = "INSERT INTO dettagliosocio (id, apertura, chiusura, data_attivazione_slot, minuti, minuti_totali, quantita_acqua, username) values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)", nativeQuery = true)
	public void saveDettaglioSocio(@Param ("id") String username, @Param("apertura") String apertura, @Param("chiusura") String chiusura, @Param("data_attivazione_slot") String data_attivazione_slot, @Param("minuti") int minuti, @Param("minuti_totali") int minuti_totali, @Param("quantita_acqua") int quantita_acqua, @Param("username") String socio_username);
	
	@Query(value = "SELECT * FROM dettagliosocio WHERE id = ?1", nativeQuery = true)
	public DettaglioSocio getDettaglioSocioByUsername(@Param("id") String id);
	
	@Query(value = "SELECT minuti_totali FROM dettagliosocio WHERE id = ?1", nativeQuery = true)
	public int getMinutiTotali(@Param("id") String id);

	@Transactional
	@Query(value = "UPDATE dettagliosocio SET username = ?1, apertura = ?2, chiusura = ?3, data_attivazione_slot = ?4, minuti = ?5, minuti_totali =?6, quantita_acqua = ?7, username = ?8 WHERE id = ?1", nativeQuery = true)
	public void updateDettaglioSocio(@Param ("id") String username, @Param("apertura") String apertura, @Param("chiusura") String chiusura, @Param("data_attivazione_slot") String data_attivazione_slot, @Param("minuti") int minuti, @Param("minuti_totali") int minuti_totali, @Param("quantita_acqua") int quantita_acqua, @Param("username") String socio_username);
	
}
