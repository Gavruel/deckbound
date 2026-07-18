package com.deckbound.tracker.controller;

import com.deckbound.tracker.dto.request.CreatePlaygroupRequest;
import com.deckbound.tracker.dto.request.JoinPlaygroupRequest;
import com.deckbound.tracker.dto.response.PlaygroupResponse;
import com.deckbound.tracker.service.PlaygroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/playgroups")
@RequiredArgsConstructor
public class PlaygroupController {

    private final PlaygroupService playgroupService;

    @PostMapping
    public ResponseEntity<PlaygroupResponse> create(
            @Valid @RequestBody CreatePlaygroupRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(playgroupService.create(request));
    }

    @GetMapping("/{playgroupId}")
    public ResponseEntity<PlaygroupResponse> getById(@PathVariable UUID playgroupId) {
        return ResponseEntity.ok(playgroupService.getById(playgroupId));
    }

    @PostMapping("/join")
    public ResponseEntity<PlaygroupResponse> join(@RequestBody @Valid JoinPlaygroupRequest request) {
        return ResponseEntity.ok(playgroupService.join(request));
    }
}
