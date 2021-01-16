package com.oneservereach.main.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oneservereach.main.exception.ResourceNotFoundException;
import com.oneservereach.main.model.Post;
import com.oneservereach.main.service.PostService;
import com.oneservereach.main.repository.PostRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class PostController {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private PostService postService;
	
	// get all post
	@GetMapping("/post")
	public List<Post> getAllPosts(){
		List<Post> post = postRepository.findAll();
		return post;
	}
	
	// create post rest api
	@PostMapping("/post")
	public Post createPost(@RequestBody Post post) {
		return postRepository.save(post);
	}
	
	// get post by id rest api
	@GetMapping("/post/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable Long id) throws ResourceNotFoundException {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post not exist with id :" + id));
		return ResponseEntity.ok(post);
	}
	
		// update post rest api
	
	@PutMapping("/post/{id}")
	public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails) throws ResourceNotFoundException{
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post not exist with id :" + id));
		
		post.setTitle(postDetails.getTitle());
		post.setAuthor(postDetails.getAuthor());	
		post.setBody(postDetails.getBody());
		
		Post updatedPost = postRepository.save(post);
		return ResponseEntity.ok(updatedPost);
	}
	
	// delete post rest api
	@DeleteMapping("/post/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePost(@PathVariable Long id) throws ResourceNotFoundException{
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post not exist with id :" + id));
		
		postRepository.delete(post);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	// get all friend's post
	@GetMapping("/post/connections")
	public List<Post> getAllConnectionPost() throws IOException{
		List<Post> cons = postService.getAllConnectionPosts();
		return cons;
	}
}
