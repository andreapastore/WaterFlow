package com.pastore.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pastore.entity.Socio;

public interface SocioRepository extends CrudRepository<Socio, String> 
{
	@Transactional
	@Query(value = "INSERT INTO socio (username, abilitato, barca, password, postazione, profilo) values (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
	public void insertSocio(@Param("username") String username, @Param("abilitato") String abilitato, @Param("barca") String barca, @Param("password") String password, @Param("postazione") String postazione, @Param("profilo") String profilo);
	
	@Query(value = "SELECT * FROM socio", nativeQuery = true)
	public Iterable<Socio> findAll();
	
	@Query(value = "SELECT * FROM socio WHERE username = ?1", nativeQuery = true)
	public Optional<Socio> findById(@Param("username") String username);
	

}
