package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private ModelMapper modelMapper;

	private CommentRepository commentRepository;

	private PostRepository postRepository;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {

		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}

	@Override
	public CommentDTO createComment(long postId, CommentDTO commentDTO) {

		Comment comment = mapToEntity(commentDTO);

		// retrive post entity by id

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// set post to comment entity

		comment.setPost(post);

		// save entity to db

		Comment newcomment = commentRepository.save(comment);

		return mapToDTO(newcomment);

	}

	private CommentDTO mapToDTO(Comment comment) {
		
		CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

//		CommentDTO commentDTO = new CommentDTO();
//		commentDTO.setId(comment.getId());
//		commentDTO.setName(comment.getName());
//		commentDTO.setEmail(comment.getEmail());
//		commentDTO.setBody(comment.getBody());

		return commentDTO;

	}

	private Comment mapToEntity(CommentDTO commentDTO) {
		
		Comment comment = modelMapper.map(commentDTO,Comment.class );

//		Comment comment = new Comment();
//		comment.setId(commentDTO.getId());
//		comment.setName(commentDTO.getName());
//		comment.setEmail(commentDTO.getEmail());
//		comment.setBody(commentDTO.getBody());

		return comment;
	}

	@Override
	public List<CommentDTO> getCommentsByPostId(long postId) {

		List<Comment> comments = commentRepository.findByPostId(postId);

		// convert list of comment entities to list of comment dto's

		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDTO getCommentsById(long postId, long commentId) {

		// retrive post entity by id

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (comment.getPost().getId() != post.getId()) {
		    throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		return mapToDTO(comment);
	}

	@Override
	public CommentDTO updateComments(long postId, long commentId, CommentDTO commentRequest) {
		
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (comment.getPost().getId() != post.getId()) {
		    throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		
		return mapToDTO(updatedComment);
		
		
	}

	@Override
	public void deleteComment(long postId, long commentId) {
		
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (comment.getPost().getId() != post.getId()) {
		    throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		
		commentRepository.delete(comment);
		
	}

}
