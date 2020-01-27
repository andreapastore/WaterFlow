package com.pastore.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pastore.entity.AttesaTimer;

public interface AttesaTimerRepository extends CrudRepository<AttesaTimer, String>
{
	@Query(value = "INSERT INTO attesatimer (nome_timer, minuti) values (?1, ?2)", nativeQuery = true)
	public void inserisciMinutaggioTimer(@Param("nome_timer") String nome_timer, @Param("minuti") int minuti);

	@Query(value = "SELECT minuti FROM attesatimer WHERE nome_timer = 'qrcode'", nativeQuery = true)
	public int ritornaMinutiDiAttesaQrCode();

	@Query(value = "SELECT minuti FROM attesatimer WHERE nome_timer = 'flussoacqua'", nativeQuery = true)
	public int ritornaMinutiDiAttesaFlussoAcqua();
}
