package com.simple.blogging.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simple.blogging.model.comment.Comment;
import com.simple.blogging.model.appuser.AppUser;
import com.simple.blogging.model.likepost.LikedPost;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "post", indexes = {@Index(columnList = "id", name = "pk_post_id")})
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    private Long id;

    // @Column(columnDefinition = "text")
    private String content;

    @Builder.Default
    private Boolean isActive = false;
    @Lob
    @CreationTimestamp
    private Date postedDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_post_app_user_id"))
//    @JsonIgnore
    private AppUser appUser;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", fetch = FetchType.EAGER)
    private List<LikedPost> likedPosts;
}
