//package com.springboot.blog.payload;
//
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
//import lombok.Data;
//
//@Data
//public class PostDtoV2 {
//	
//	
//	private  long id;
//	
//	@NotEmpty
//	@Size(min = 2 , message = "title should be of at least 2 character")
//	private String title;
//	
//	@NotEmpty
//	@Size(min = 10 , message = "description should be of at least 10 character")
//	private String description;
//	
//	@NotEmpty
//	private String content;
//	
//	private Set<CommentDTO> comments;
//
//	private List<String> tags;
//	
//
//}
