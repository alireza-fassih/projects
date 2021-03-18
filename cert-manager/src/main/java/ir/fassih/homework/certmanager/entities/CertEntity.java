package ir.fassih.homework.certmanager.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "CERT_CERTES")
public class CertEntity {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="CODE")
    private String code;

    @Lob
    @Column(name = "IMG_DATA")
    private byte[] data;

}
