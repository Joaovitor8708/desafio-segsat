package com.joaovitor.postsapi.service;

import com.joaovitor.postsapi.client.JsonPlaceholderClient;
import com.joaovitor.postsapi.dto.PostDTO;
import com.joaovitor.postsapi.dto.SyncResponse;
import com.joaovitor.postsapi.mapper.PostMapper;
import com.joaovitor.postsapi.model.Post;
import com.joaovitor.postsapi.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncService {

    private final JsonPlaceholderClient client;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public SyncService(
            JsonPlaceholderClient client,
            PostRepository postRepository,
            PostMapper postMapper) {

        this.client = client;
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public SyncResponse sincronizarPosts() {
        List<PostDTO> postsRecebidos = client.buscarPosts();

        List<Post> posts = postsRecebidos.stream()
                .map(postMapper::paraEntidade)
                .toList();

        postRepository.saveAll(posts);

        return new SyncResponse(posts.size(), "Posts sincronizados com sucesso.");
    }
}