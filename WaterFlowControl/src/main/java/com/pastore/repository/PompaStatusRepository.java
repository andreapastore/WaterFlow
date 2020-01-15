package com.pastore.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.pastore.entity.PompaStatus;

@Repository
public interface PompaStatusRepository extends CrudRepository<PompaStatus, Integer>
{

	@Query(value = "SELECT status from pompastatus WHERE id =:id", nativeQuery = true)
	public String getPompaStatusId(@Param ("id") int id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE pompastatus SET status = :status WHERE id = :id", nativeQuery = true)
	public void updateStatus(@Param("status") String status, @Param("id") int id);

	@Query(value = "SELECT * FROM pompastatus WHERE id = ?1", nativeQuery = true)
	public PompaStatus getPompaStatusById(@Param("id") int id);

	@Query(value = "INSERT INTO pompastatus (id, status) VALUES (?1 ,?2)", nativeQuery = true)
	public void insertPompaStatus(@Param("id") int id, @Param("status") String status);
	
}
