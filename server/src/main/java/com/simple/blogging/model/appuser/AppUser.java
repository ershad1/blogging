package com.simple.blogging.model.appuser;

import com.simple.blogging.model.likepost.LikedPost;
import com.simple.blogging.model.post.Post;
import com.simple.blogging.model.userrole.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "app_user", indexes = {@Index(columnList = "id", name = "pk_app_user_id")})
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    private Long id;

    @Column(length = 100)
    private String fullName;

    @Column(unique = true)
    private String username;

    private String password;

    @Builder.Default
    private Boolean isActive = false;

    @Email
    @Column(unique = true, length = 100)
    private String email;

    @CreationTimestamp
    private Date createdDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> post;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser", fetch = FetchType.EAGER)
    private List<LikedPost> likedPost;

}
