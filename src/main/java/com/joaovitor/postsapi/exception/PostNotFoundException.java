package com.joaovitor.postsapi.exception;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(Long id) {
        super("Post não encontrado com o ID: " + id);
    }

}
