package com.simple.blogging.model.post;

import com.simple.blogging.model.appuser.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public Post savePost(AppUser user, HashMap<String, String> request, String postImageName) {
		String content = request.get("content");
		Post post = new Post();
		post.setContent(content);
		post.setAppUser(user);
		post.setPostedDate(new Date());
		postRepository.save(post);
		return post;
	}

	public List<Post> postList() {
		return postRepository.findAll();
	}
    public List<Post> activePostList() {
        return postRepository.findAllByIsActiveTrue();
    }

	public Post getPostById(Long id) {
		return postRepository.findPostById(id);
	}

	public List<Post> findPostByAppUserId(Long userId) {
		return postRepository.findPostByAppUserId(userId);
	}

	public Post deletePost(Post post) {
		try {
			postRepository.deletePostById(post.getId());
			return post;
		} catch (Exception e) {
			return null;
		}
	}
}
