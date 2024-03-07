package com.springboot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "COMMENT CONTROLLLER" ,description = "CRUD REST API'S FOR COMMENT RESOURCES")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Bear Authentication")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Operation(summary = "CREATE COMMENT REST API")
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment( @PathVariable("postId") long postId,
			@Valid @RequestBody CommentDTO commentDTO) {

		return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.OK);

	}

	@Operation(summary = "GET ALL COMMENTS REST API")
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDTO> getCommentsByPostId(@PathVariable("postId") long postId) {

		return commentService.getCommentsByPostId(postId);

	}
	@Operation(summary = "GET  COMMENTS BY ID REST API")
	@GetMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDTO> getCommentsById(

			@PathVariable("postId") long postId, @PathVariable("id") long commmentId) {

		CommentDTO commentDTO = commentService.getCommentsById(postId, commmentId);

		return new ResponseEntity<>(commentDTO, HttpStatus.OK);

	}

	@Operation(summary = "UPDATE COMMENTS REST API")
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDTO> updateComments( @PathVariable("postId") long postId,
			@PathVariable("id") long commmentId, @Valid @RequestBody CommentDTO commentDTO) {

		CommentDTO updatedComment = commentService.updateComments(postId, commmentId, commentDTO);

		return new ResponseEntity<>(updatedComment, HttpStatus.OK);

	}

	@Operation(summary = "DELETE COMMENTS REST API")
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(

			@PathVariable("postId") long postId, @PathVariable("id") long commmentId

	) {

		commentService.deleteComment(postId, commmentId);

		return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);

	}

}
