package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CommentDTO;

public interface CommentService {

	
	CommentDTO createComment(long postId ,CommentDTO commentDTO );
	
	List<CommentDTO> getCommentsByPostId(long postId);
	
	CommentDTO getCommentsById(long postId ,long commentId);
	
	CommentDTO updateComments(long postId ,long commentId ,CommentDTO commentRequest);
	
	void deleteComment(long postId ,long commentId);
	
}
