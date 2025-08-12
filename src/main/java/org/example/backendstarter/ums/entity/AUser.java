package org.example.backendstarter.ums.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.example.backendstarter.common.Auditable;

import java.util.List;

@Entity(name = "users")
@Getter
@Setter
public class AUser extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private String phone;
    private String cin;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

}
