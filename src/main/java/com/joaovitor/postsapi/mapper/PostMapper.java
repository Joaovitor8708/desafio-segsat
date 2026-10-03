package com.joaovitor.postsapi.mapper;

import com.joaovitor.postsapi.dto.PostDTO;
import com.joaovitor.postsapi.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO paraDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getUserId(),
                post.getTitle(),
                post.getBody()
        );
    }

    public Post paraEntidade(PostDTO dto) {
        return new Post(
                dto.id(),
                dto.userId(),
                dto.title(),
                dto.body()
        );
    }
}