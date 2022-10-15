package com.avacorp.blog.service;

import com.avacorp.blog.payload.PostDto;
import com.avacorp.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

     void deletePost(long id);
}
