package com.joaovitor.postsapi.service;

import com.joaovitor.postsapi.dto.PostDTO;
import com.joaovitor.postsapi.exception.PostNotFoundException;
import com.joaovitor.postsapi.model.Post;
import com.joaovitor.postsapi.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDTO> listarPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public PostDTO buscarPostPorId(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return converterParaDTO(post);
    }

    private PostDTO converterParaDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getUserId(),
                post.getTitle(),
                post.getBody()
        );
    }
}