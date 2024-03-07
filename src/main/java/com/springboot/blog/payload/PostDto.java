package com.springboot.blog.payload;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "POST MODEL DESCRIPTION")
@Data
public class PostDto {
	
	@Schema(description = "BLOG POST ID")
	private  long id;
	
	@Schema(description = "BLOG POST TITLE")
	@NotEmpty
	@Size(min = 2 , message = "title should be of at least 2 character")
	private String title;
	
	@Schema(description = "BLOG POST DESCRIPTION")
	@NotEmpty
	@Size(min = 10 , message = "description should be of at least 10 character")
	private String description;
	
	@Schema(description = "BLOG POST CONTENT")
	@NotEmpty
	private String content;
	
	@Schema(description = "BLOG POST COMMENT")
	private Set<CommentDTO> comments;
	
	

}
