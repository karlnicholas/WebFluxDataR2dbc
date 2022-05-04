package com.example.webfluxplay.api;

import com.example.webfluxplay.model.SomeEntity;
import com.example.webfluxplay.repository.SomeEntityRepository;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@Component
public class SomeEntityHandler {

    private final Validator validator;
    private final SomeEntityRepository someEntityRepository;

    public SomeEntityHandler(
            Validator validator,
            SomeEntityRepository someEntityRepository
    ) {
        this.validator = validator;
        this.someEntityRepository = someEntityRepository;
    }

    public Mono<ServerResponse> listSomeEntities(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(someEntityRepository.findAll(), SomeEntity.class);
    }

    public Mono<ServerResponse> createSomeEntity(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(request.bodyToMono(SomeEntity.class)
                        .doOnNext(this::validate)
                        .flatMap(someEntity -> {
                            someEntity.setIsNew(true);
                            return someEntityRepository.save(someEntity);
                        }), SomeEntity.class);
    }

    public Mono<ServerResponse> updateSomeEntity(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(request.bodyToMono(SomeEntity.class)
                        .doOnNext(this::validate)
                        .flatMap(someEntityRepository::save), SomeEntity.class);
    }

    public Mono<ServerResponse> getSomeEntity(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(someEntityRepository.findById(request.pathVariable("id")), SomeEntity.class);
    }

    public Mono<ServerResponse> deleteSomeEntity(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(someEntityRepository.deleteById(request.pathVariable("id")), SomeEntity.class);
    }

    private void validate(SomeEntity someEntity) {
        Errors errors = new BeanPropertyBindingResult(someEntity, "SomeEntity");
        validator.validate(someEntity, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }

}