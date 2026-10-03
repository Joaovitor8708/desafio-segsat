package com.joaovitor.postsapi.controller;

import com.joaovitor.postsapi.dto.SyncResponse;
import com.joaovitor.postsapi.service.SyncService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync")
public class SyncController {

    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

    @PostMapping
    public SyncResponse sincronizarPosts() {
        return syncService.sincronizarPosts();
    }
}