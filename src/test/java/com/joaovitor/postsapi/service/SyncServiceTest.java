package com.joaovitor.postsapi.service;

import com.joaovitor.postsapi.client.JsonPlaceholderClient;
import com.joaovitor.postsapi.dto.PostDTO;
import com.joaovitor.postsapi.dto.SyncResponse;
import com.joaovitor.postsapi.exception.ExternalServiceException;
import com.joaovitor.postsapi.mapper.PostMapper;
import com.joaovitor.postsapi.model.Post;
import com.joaovitor.postsapi.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SyncServiceTest {

    @Mock
    private JsonPlaceholderClient client;

    @Mock
    private PostRepository postRepository;

    @Captor
    private ArgumentCaptor<List<Post>> postsCaptor;

    private SyncService syncService;

    @BeforeEach
    void configurar() {
        syncService = new SyncService(
                client,
                postRepository,
                new PostMapper()
        );
    }

    @Test
    void deveEnviarPostsParaPersistenciaERetornarTotalProcessado() {
        List<PostDTO> postsRecebidos = List.of(
                new PostDTO(1L, 10L, "Primeiro título", "Primeiro conteúdo"),
                new PostDTO(2L, 20L, "Segundo título", "Segundo conteúdo")
        );

        when(client.buscarPosts()).thenReturn(postsRecebidos);

        SyncResponse resposta = syncService.sincronizarPosts();

        verify(postRepository).saveAll(postsCaptor.capture());

        assertThat(postsCaptor.getValue())
                .extracting(
                        Post::getId,
                        Post::getUserId,
                        Post::getTitle,
                        Post::getBody
                )
                .containsExactly(
                        tuple(1L, 10L, "Primeiro título", "Primeiro conteúdo"),
                        tuple(2L, 20L, "Segundo título", "Segundo conteúdo")
                );

        assertThat(resposta.totalProcessado()).isEqualTo(2);
    }

    @Test
    void naoDeveAcessarRepositoryQuandoApiExternaFalhar() {
        ExternalServiceException falha =
                new ExternalServiceException("API externa indisponível.");

        when(client.buscarPosts()).thenThrow(falha);

        ExternalServiceException recebida = assertThrows(
                ExternalServiceException.class,
                () -> syncService.sincronizarPosts()
        );

        assertThat(recebida).isSameAs(falha);

        verifyNoInteractions(postRepository);
    }
}