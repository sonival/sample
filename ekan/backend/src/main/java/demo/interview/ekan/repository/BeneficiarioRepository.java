package demo.interview.ekan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.interview.ekan.model.Beneficiario;

@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario,Long> {

    @Query( "select b from Beneficiario b where b.nome = :nome")
    Beneficiario findByName(@Param("nome") String name);


}
