package com.pastore.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastore.entity.PompaStatus;

@Repository
@Transactional
public interface PompaStatusRepository extends CrudRepository<PompaStatus, Integer>
{

	@Query(value = "SELECT * from pompastatus WHERE id =: id", nativeQuery = true)
	int getPompaStatusId(@Param ("id") int id);
	
	@Modifying
	@Query(value = "UPDATE pompastatus SET status = :status WHERE id = :id", nativeQuery = true)
	public void updateStatus(@Param("status") String status, @Param("id") int id);
	
}
