package com.pastore.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pastore.entity.QrCode;

@Repository
public interface QrCodeRepository extends CrudRepository<QrCode, Integer>
{
	@Query(value = "SELECT * FROM qrcode WHERE id = ?1", nativeQuery = true)
	public String codicePompa(@Param("id") int id);

	@Query(value = "SELECT codice_pompa_uno FROM qrcode WHERE id = 1", nativeQuery = true)
	public String getCodicePompaUno();

	@Query(value = "SELECT codice_pompa_due FROM qrcode WHERE id = 1", nativeQuery = true)
	public String getCodicePompaDue();
	
	@Query(value = "SELECT codice_pompa_tre FROM qrcode WHERE id = 1", nativeQuery = true)
	public String getCodicePompaTre();
	
	@Query(value = "INSERT INTO qrcode (id, codice_pompa_uno, codice_pompa_due, codice_pompa_tre) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
	public void save(@Param("id") int id, @Param("codice_pompa_uno") String codice_pompa_uno, @Param("codice_pompa_due") String codice_pompa_due, @Param("codice_pompa_tre") String codice_pompa_tre);
}
