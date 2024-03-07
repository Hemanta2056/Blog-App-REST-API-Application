package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "COMMENT MODEL DESCRIPTION")
@Data
public class CommentDTO {
	
	@Schema(description = "BLOG COMMENT ID")
	private long id;
	
	@Schema(description = "BLOG COMMENT MESSAGE")
	@NotEmpty(message = "name cannot be null or empty")
	private String name;
	
	@Schema(description = "BLOG COMMENT EMAIL")
	@NotEmpty(message = "message cannot be null or empty")
	@Email
	private String email;
	
	@Schema(description = "BLOG COMMENT BODY")
	@NotEmpty
	@Size(min = 10 , message = "body should at least contain 10 character")
	private String body;

}
