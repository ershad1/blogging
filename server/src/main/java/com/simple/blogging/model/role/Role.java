package com.simple.blogging.model.role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "role", indexes = {@Index(columnList = "id", name = "pk_role_id")})
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    private Long id;

    private String fullName;
}
