package demo.interview.ekan.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.interview.ekan.model.Documento;

@Repository
public interface DocumentoRepository  extends JpaRepository<Documento,Long> {
    @Query(value =  "select b.* from Documento b where b.BENEFICIARIO_ID in (:id)", nativeQuery = true)
    List<Documento> getAllDocumentsByBeneficiarioId(@Param("id") Long id);
}
