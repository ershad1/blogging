package com.simple.blogging.model.comment;

import com.simple.blogging.model.post.Post;
import com.simple.blogging.model.appuser.AppUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "comment", indexes = {@Index(columnList = "id", name = "pk_comment_id")})
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    private Long id;

//    @Column(columnDefinition = "text")
    @Lob
    private String content;

    @CreationTimestamp
    private Date postedDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_app_user_id"))
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_post_id"))
    private Post post;
}
