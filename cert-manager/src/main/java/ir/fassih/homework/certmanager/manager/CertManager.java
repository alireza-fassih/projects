package ir.fassih.homework.certmanager.manager;

import ir.fassih.homework.certmanager.entities.CertEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CertManager {

    @Transactional
    void save(CertEntity entity);

    @Transactional(readOnly = true)
    List<CertEntity> searchByQuery(String query);

    @Transactional
    void delete(long id);

    @Transactional(readOnly = true)
    CertEntity load(long id);


    @Transactional(readOnly = true)
    CertEntity loadByCode(String code);

}
