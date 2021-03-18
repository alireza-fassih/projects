package ir.fassih.homework.certmanager.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "CERT_USER")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="USERNAME")
    private String username;

    @Column(name="PASSWORD")
    private String password;

}
