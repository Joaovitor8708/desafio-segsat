package com.joaovitor.postsapi.dto;

public record PostDTO(
        Long id,
        Long userId,
        String title,
        String body
) {}