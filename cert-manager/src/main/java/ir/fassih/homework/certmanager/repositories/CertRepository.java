package ir.fassih.homework.certmanager.repositories;

import ir.fassih.homework.certmanager.entities.CertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertRepository extends JpaRepository<CertEntity, Long> {

    CertEntity findByCode(String code);

}
