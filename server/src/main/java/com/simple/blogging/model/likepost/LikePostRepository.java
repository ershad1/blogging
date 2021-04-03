package com.simple.blogging.model.likepost;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikedPost, Long> {
}
