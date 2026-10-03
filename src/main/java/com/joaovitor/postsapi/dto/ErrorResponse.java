package com.joaovitor.postsapi.dto;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String mensagem,
        Instant timestamp
) {}