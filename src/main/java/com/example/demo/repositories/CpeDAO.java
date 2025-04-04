package com.example.demo.repositories;

import com.example.demo.entities.Cpe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CpeDAO extends JpaRepository<Cpe, Long> {
    Cpe save(Cpe cpe);
    Cpe findByCpeName(String cpeName);
    /**
     * Vérifie si une association existe déjà entre un CPE et une CVE
     */
    @Query(value = "SELECT COUNT(1) > 0 FROM cpe_cve_list WHERE cpe_cpe_id = :cpeId AND cve_list_id = :cveId",
            nativeQuery = true)
    boolean existsAssociationBetweenCpeAndCve(@Param("cpeId") Long cpeId, @Param("cveId") String cveId);

    /**
     * Crée une association entre un CPE et une CVE
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cpe_cve_list (cpe_cpe_id, cve_list_id) VALUES (:cpeId, :cveId)",
            nativeQuery = true)
    void createAssociationBetweenCpeAndCve(@Param("cpeId") Long cpeId, @Param("cveId") String cveId);
}
