package com.deckbound.tracker.controller;

import com.deckbound.tracker.dto.request.CreateCommanderRequest;
import com.deckbound.tracker.dto.response.CommanderResponse;
import com.deckbound.tracker.service.CommanderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commanders")
@RequiredArgsConstructor
public class CommanderController {

    private final CommanderService commanderService;

    @GetMapping
    public ResponseEntity<List<CommanderResponse>> listarTodos() {
        return ResponseEntity.ok(commanderService.listarTodos());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommanderResponse>> buscarPorNome(@RequestParam String name) {
        return ResponseEntity.ok(commanderService.buscarPorNome(name));
    }

    @PostMapping
    public ResponseEntity<CommanderResponse> salvar(@Valid @RequestBody CreateCommanderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commanderService.salvarOuBuscar(request));
    }
}
