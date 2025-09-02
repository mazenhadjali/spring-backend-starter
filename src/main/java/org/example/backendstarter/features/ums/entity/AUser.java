package org.example.backendstarter.features.ums.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backendstarter.features.FileAttachment.entity.FileAttachment;
import org.example.backendstarter.features.common.Auditable;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AUser extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username must be lowercase and contain only letters and digits")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    private String phone;

    @Size(max = 8, message = "CIN must not exceed 20 characters")
    private String cin;

    @OneToOne(fetch = FetchType.EAGER)
    private FileAttachment avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    // --- Auto-normalize before insert/update ---
    @PrePersist
    @PreUpdate
    private void normalizeFields() {
        if (username != null) {
            username = username.trim().toLowerCase();
        }
        if (email != null) {
            email = email.trim().toLowerCase();
        }

        if (firstName != null) {
            firstName = firstName.trim();
        }
        if (lastName != null) {
            lastName = lastName.trim();
        }
        if (phone != null) {
            phone = phone.trim();
        }
        if (cin != null) {
            cin = cin.trim();
        }
    }

}
