package com.simple.blogging.model.likepost;

import com.simple.blogging.model.appuser.AppUser;
import com.simple.blogging.model.post.Post;
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
@Table(name = "like_post", indexes = {@Index(columnList = "id", name = "pk_liked_post_id")})
@Entity
public class LikedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_liked_post_post_id"))
    Post post;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_liked_post_app_user_id"))
    AppUser appUser;
}
