package com.springboot.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;

import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "POST CONTROLLLER" ,description = "CRUD REST API'S FOR POST RESOURCES")
@RestController
@RequestMapping()
@SecurityRequirement(name = "Bear Authentication")
public class PostController {
	
	 private final PostService postService;
	
//	private PostService postService;

	public PostController(PostService postService) {
		
		this.postService = postService;
	}
	
	//create blog post rest api
	
	@Operation(summary = "CREATE POST REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/v1/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		
		return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
	}
	
	
	//get all post rest api
	@Operation(summary = "GET ALL POSTS REST API")
	@GetMapping(value = "/api/v1/posts")
	public PostResponse getAllPosts(
			
			@RequestParam(value="pageNo" ,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER ,required = false) int pageNo,
			@RequestParam(value="pageSize" ,defaultValue = AppConstants.DEFAULT_PAGE_SIZE ,required = false) int pageSize,
			@RequestParam(value="sortBy" ,defaultValue = AppConstants.DEFAULT_SORT_BY ,required = false) String sortBy,
			@RequestParam(value="sortDir" , defaultValue = AppConstants.DEFAULT_SORT_DIRECTION ,required = false) String sortDir
			
			
			){
		
	
		return postService.getAllPosts(pageNo ,pageSize ,sortBy ,sortDir);
	}
	
	@Operation(summary = "GET POST BY ID REST API")
	@GetMapping(value = "/api/v1/posts/{id}" )

	public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id ) {
		
		
		return ResponseEntity.ok( postService.getPostById(id)) ;
	} 
	
//	@GetMapping(value = "/api/posts/{id}" )
//	public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id ) {
//		
//		PostDto postDto = postService.getPostById(id);
//		PostDtoV2 postDtoV2 = new PostDtoV2();
//		
//		postDtoV2.setId(postDto.getId());
//		postDtoV2.setTitle(postDto.getTitle());
//		postDtoV2.setDescription(postDto.getDescription());
//		postDtoV2.setContent(postDto.getContent());
//		
//		List<String> tags = new ArrayList<>();
//		
//		tags.add("java");
//		tags.add("springboot");
//		tags.add("rest api");
//		tags.add("spring security");
//		
//		postDtoV2.setTags(tags);
//		
//		
//		return ResponseEntity.ok(postDtoV2);
//	} 
	
	
	
	@Operation(summary = "UPDATE POST REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDto> updatedPost(@Valid @RequestBody PostDto postdto ,@PathVariable("id") long id){
		
		PostDto postResponse = postService.updatePost(postdto, id);
		
		return new ResponseEntity<>(postResponse ,HttpStatus.OK);
		
	}
	
	@Operation(summary = "DELETE POST REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/api/v1/posts/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable("id") long id){
		
	postService.deletePostById(id);
	
	return new ResponseEntity<>("post deleted successfully",HttpStatus.OK);
	}
	
	
	

}
