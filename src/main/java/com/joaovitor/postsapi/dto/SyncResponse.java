package com.joaovitor.postsapi.dto;

public record SyncResponse(
        int totalProcessado,
        String mensagem
) {
}