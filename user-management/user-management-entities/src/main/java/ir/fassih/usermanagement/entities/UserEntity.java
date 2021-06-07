package ir.fassih.usermanagement.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UMM_USER")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 5681974718067546285L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    private Long id;

    @Column(name = "USERNAME")
    private String username;


    @Column(name = "PASSWORD")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
