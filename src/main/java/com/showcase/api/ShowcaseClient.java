package com.showcase.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.showcase.model.PersonOas;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShowcaseClient {
    private final WebClient webClient;
    private final ObjectMapper mapper;

    public Flux<PersonOas> getAllPerson() throws JsonProcessingException {
        return webClient.get()
                .uri("api/showcase")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(PersonOas.class)
                .doOnError(e -> log.error("Error Getting persons", e));
    }

    public Mono<PersonOas> createPerson(PersonOas personOas) throws JsonProcessingException {
        return webClient.post()
                .uri("api/showcase")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(mapper.writeValueAsString(personOas))
                .retrieve()
                .bodyToMono(PersonOas.class)
                .doOnError(e -> log.error("Error creating person", e))
                .doOnSuccess(p -> log.debug("Person successfully created [{}]", p));
    }
}
