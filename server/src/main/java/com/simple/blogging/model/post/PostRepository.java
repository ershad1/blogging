package com.simple.blogging.model.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p order by p.postedDate DESC")
    public List<Post> findAll();

    @Query("SELECT p FROM Post p WHERE p.appUser.id=:userId order by p.postedDate DESC")
    public List<Post> findPostByAppUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Post p WHERE p.id=:x")
    public Post findPostById(@Param("x") Long id);

    @Modifying
    @Query("DELETE FROM Post WHERE id=:x")
    public void deletePostById(@Param("x") Long id);
}
