package com.blog.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.repository.CategoryRepository;
import com.blog.api.repository.PostRepository;
import com.blog.api.repository.UserRepository;
import com.blog.api.services.PostService;
@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostRepository postRepository;
	@Autowired
	public ModelMapper modelMapper;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " Id", userId));
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", " Id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postRepository.save(post);
		
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postid) {
		Post post =this.postRepository.findById(postid).orElseThrow(()-> 
		new ResourceNotFoundException("Post", "Id", postid));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatePost = this.postRepository.save(post);
		
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void deletePost(int postId) {
		Post post =this.postRepository.findById(postId).orElseThrow(()-> 
		new ResourceNotFoundException("Post", "Id", postId));
		this.postRepository.delete(post);
		
	}
	@Override
	public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy) {
//		Pageable page = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		Pageable page = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending());

		
		Page<Post> pagePost = this.postRepository.findAll(page);
		List<Post>allPosts = pagePost.getContent();
		List<PostDto> postDtos = allPosts.stream().map((post)-> 
		this.modelMapper.map(post, PostDto.class)).
				collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}
//	@Override
//	public List<PostDto> getAllPost(int pageNumber, int pageSize) {
//		Pageable page = PageRequest.of(pageNumber, pageSize);
//		
//		Page<Post> pagePost = this.postRepository.findAll(page);
//		List<Post>allPosts = pagePost.getContent();
//		List<PostDto> postDtos = allPosts.stream().map((post)-> 
//		this.modelMapper.map(post, PostDto.class)).
//				collect(Collectors.toList());
//		return postDtos;
//	}



	@Override
	public PostDto getPostById(int postId) {
		Post post =this.postRepository.findById(postId).orElseThrow(()-> new
				ResourceNotFoundException("Post", " Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(int categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", " Id", categoryId));
		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(int userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " Id", userId));
		List<Post> posts = this.postRepository.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts =this.postRepository.findByTitleContaining(keyword);
		List<PostDto> postDto = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}
	
	// get all post
	
//	@Override
//	public List<PostDto> getAllPost() {
//		List<Post> allPosts = this.postRepository.findAll();
//		List<PostDto> postDtos = allPosts.stream().map((post)-> 
//		this.modelMapper.map(post, PostDto.class)).
//				collect(Collectors.toList());
//		return postDtos;
//	}
	
}
