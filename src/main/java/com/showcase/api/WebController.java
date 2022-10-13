package com.showcase.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showcase.model.PersonOas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
@RequestMapping(
        value = "/web"
)
public class WebController {
    private final ShowcaseClient showcaseClient;

    @GetMapping("/showcase/admin")
    public Mono<String> getAllPerson(Model model) throws JsonProcessingException {
        Flux<PersonOas> personOasFlux = showcaseClient.getAllPerson();
        return personOasFlux.collectList()
                .map(pl -> model.addAttribute("personList", pl))
                .thenReturn("admin/person-list"); // admin/person-list.html
    }

    @GetMapping("/showcase/register")
    public Mono<String> registerPerson(Model model) {
        return Mono.just(model)
                .map(m -> m.addAttribute("personOas", PersonOas.builder().build()))
                .thenReturn("main"); // main.html
    }

    @PostMapping("/showcase/submit")
    public Mono<String> submitPerson(@ModelAttribute PersonOas personOas, Model model) throws JsonProcessingException {
        return showcaseClient.createPerson(personOas)
                .then(registerPerson(model));
    }

}
