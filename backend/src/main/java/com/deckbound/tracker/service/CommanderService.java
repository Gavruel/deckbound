package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.CreateCommanderRequest;
import com.deckbound.tracker.dto.response.CommanderResponse;
import com.deckbound.tracker.model.entity.Commander;
import com.deckbound.tracker.repository.CommanderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommanderService {

    private final CommanderRepository commanderRepository;

    @Transactional
    public CommanderResponse salvarOuBuscar(CreateCommanderRequest request) {
        // Se vier scryfallId, evita duplicatas
        if (request.scryfallId() != null && !request.scryfallId().isBlank()) {
            return commanderRepository.findByScryfallId(request.scryfallId())
                .map(CommanderResponse::from)
                .orElseGet(() -> CommanderResponse.from(commanderRepository.save(
                    Commander.builder()
                        .nome(request.nome())
                        .imageUrl(request.imageUrl())
                        .scryfallId(request.scryfallId())
                        .build()
                )));
        }

        Commander commander = Commander.builder()
            .nome(request.nome())
            .imageUrl(request.imageUrl())
            .build();
        return CommanderResponse.from(commanderRepository.save(commander));
    }

    @Transactional(readOnly = true)
    public List<CommanderResponse> buscarPorNome(String nome) {
        return commanderRepository.findByNomeContainingIgnoreCase(nome)
            .stream()
            .map(CommanderResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<CommanderResponse> listarTodos() {
        return commanderRepository.findAll().stream()
            .map(CommanderResponse::from)
            .toList();
    }
}
