//package com.pastore.repository;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.pastore.entity.Sessione;
//
//@Repository
//public interface SessioneRepository extends CrudRepository<Sessione, String>
//{
//	
//	@Query(value = "SELECT username FROM sessione WHERE id = ?1", nativeQuery = true)
//	public String getUserInSessione(@Param ("id") String id);
//	
//	@Query(value = "INSERT INTO sessione (id, username) VALUES (?1, ?2) ", nativeQuery = true)
//	public void insertSessione(@Param("id") String id, @Param("username") String username);
//	
//	@Query(value = "DELETE FROM sessione WHERE id = ?1", nativeQuery = true)
//	public void deleteSessione(@Param("id") String id);
//}
