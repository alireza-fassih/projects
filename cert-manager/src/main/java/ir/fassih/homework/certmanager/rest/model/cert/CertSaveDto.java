package ir.fassih.homework.certmanager.rest.model.cert;

import lombok.Data;

import java.util.UUID;

@Data
public class CertSaveDto {

    private String code;
    private UUID   imageId;
    
}
