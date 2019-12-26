package com.pastore.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pastore.entity.Socio;

public interface SocioRepository extends CrudRepository<Socio, String> 
{
	@Transactional
	//@Modifying
	@Query(value = "INSERT INTO socio (username, abilitato, barca, password, postazione, profilo) values (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
	void insertSocio(@Param("username") String username, @Param("abilitato") String abilitato, @Param("barca") String barca, @Param("password") String password, @Param("postazione") String postazione, @Param("profilo") String profilo);
	
}
