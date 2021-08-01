package com.example.webfluxplay.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.example.webfluxplay.model.SomeEntity;

public interface SomeEntityRepository extends R2dbcRepository<SomeEntity, String> {

}
