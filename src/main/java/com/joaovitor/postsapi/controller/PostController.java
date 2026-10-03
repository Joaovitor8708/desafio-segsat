package com.joaovitor.postsapi.controller;

import com.joaovitor.postsapi.dto.PostDTO;
import com.joaovitor.postsapi.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDTO> listarPosts() {
        return postService.listarPosts();
    }

    @GetMapping("/{id}")
    public PostDTO buscarPostPorId(@PathVariable("id") Long id) {
        return postService.buscarPostPorId(id);
    }
}