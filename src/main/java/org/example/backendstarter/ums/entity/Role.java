package org.example.backendstarter.ums.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.example.backendstarter.common.Auditable;
import org.example.backendstarter.common.Feature;

import java.util.Set;

@Entity(name = "roles")
@Getter
public class Role extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Feature> features;
}
