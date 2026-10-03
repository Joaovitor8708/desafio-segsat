package com.joaovitor.postsapi.service;

import com.joaovitor.postsapi.dto.PostDTO;
import com.joaovitor.postsapi.exception.PostNotFoundException;
import com.joaovitor.postsapi.mapper.PostMapper;
import com.joaovitor.postsapi.model.Post;
import com.joaovitor.postsapi.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(
            PostRepository postRepository,
            PostMapper postMapper) {

        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public List<PostDTO> listarPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::paraDTO)
                .toList();
    }

    public PostDTO buscarPostPorId(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return postMapper.paraDTO(post);
    }
}