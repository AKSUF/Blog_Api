package com.blog.api.services;

import com.blog.api.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment (CommentDto commentDto, int postId);
	void deleteComment(int commentId);
}
