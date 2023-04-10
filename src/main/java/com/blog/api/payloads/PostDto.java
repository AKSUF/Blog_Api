package com.blog.api.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.api.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private int postId;
	private String title;
	private String content;
	private Date addedDate;
	private String imageName;
	private CategoryDto category;
	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();

}
