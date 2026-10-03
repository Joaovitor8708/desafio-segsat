package com.joaovitor.postsapi.client;

import com.joaovitor.postsapi.dto.PostDTO;
import com.joaovitor.postsapi.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
public class JsonPlaceholderClient {

    private final RestClient restClient;

    public JsonPlaceholderClient(
            RestClient.Builder builder,
            @Value("${integracao.jsonplaceholder.base-url}") String baseUrl) {

        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public List<PostDTO> buscarPosts() {
        try {
            List<PostDTO> posts = restClient.get()
                    .uri("/posts")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<PostDTO>>() {});

            if (posts == null) {
                throw new ExternalServiceException("A API externa retornou uma resposta sem conteúdo.");
            }

            return posts;

        } catch (RestClientException exception) {
            throw new ExternalServiceException("Não foi possível buscar os posts na API externa.", exception);
        }
    }
}