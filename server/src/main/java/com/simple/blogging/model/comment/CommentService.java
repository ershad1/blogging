package com.simple.blogging.model.comment;

import com.simple.blogging.model.appuser.AppUser;
import com.simple.blogging.model.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class CommentService {

	@Autowired
	CommentRepository commentRepository;

	public void saveComment(Post post, AppUser appUser, String content) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setAppUser(appUser);
		comment.setPostedDate(new Date());
        comment.setPost(post);
		commentRepository.save(comment);
	}

}
