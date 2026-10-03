package com.joaovitor.postsapi.service;

import com.joaovitor.postsapi.dto.PostDTO;
import com.joaovitor.postsapi.exception.PostNotFoundException;
import com.joaovitor.postsapi.mapper.PostMapper;
import com.joaovitor.postsapi.model.Post;
import com.joaovitor.postsapi.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    private PostService postService;

    @BeforeEach
    void configurar() {
        postService = new PostService(
                postRepository,
                new PostMapper()
        );
    }

    @Test
    void deveRetornarPostQuandoIdExistir() {
        Post post = new Post(
                7L,
                3L,
                "Título de exemplo",
                "Conteúdo de exemplo"
        );

        when(postRepository.findById(7L))
                .thenReturn(Optional.of(post));

        PostDTO resposta = postService.buscarPostPorId(7L);

        assertThat(resposta).isEqualTo(
                new PostDTO(
                        7L,
                        3L,
                        "Título de exemplo",
                        "Conteúdo de exemplo"
                )
        );
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExistir() {
        when(postRepository.findById(999L))
                .thenReturn(Optional.empty());

        PostNotFoundException exception = assertThrows(
                PostNotFoundException.class,
                () -> postService.buscarPostPorId(999L)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Post não encontrado com o ID: 999");
    }
}