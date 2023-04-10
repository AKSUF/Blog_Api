package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto, int userId, int categoryId);
	PostDto updatePost(PostDto postDto, int postid);
	void deletePost (int postId);
	PostResponse getAllPost(int pageNumber, int pageSize, String sortBy);

//	List<PostDto> getAllPost(int pageNumber, int pageSize);
	PostDto getPostById(int postId);
	List<PostDto> getPostsByCategory(int categoryId);
	List<PostDto> getPostsByUser(int userId);
	List<PostDto> searchPosts(String keyword);
	

}
