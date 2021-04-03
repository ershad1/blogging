package com.simple.blogging.model.userrole;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simple.blogging.model.role.Role;
import com.simple.blogging.model.appuser.AppUser;
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
@Table(name = "user_role", indexes = {@Index(columnList = "id", name = "pk_user_role_id")})
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_role_app_user_id"))
    @JsonIgnore
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_role_role_id"))
    private Role role;
}
