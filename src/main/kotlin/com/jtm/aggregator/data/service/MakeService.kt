package com.jtm.aggregator.data.service

import com.jtm.aggregator.core.domain.entity.Make
import com.jtm.aggregator.core.domain.exceptions.MakeFound
import com.jtm.aggregator.core.domain.exceptions.MakeNotFound
import com.jtm.aggregator.core.usecase.repository.MakeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class MakeService @Autowired constructor(private val makeRepository: MakeRepository) {

    fun insertMake(make: String): Mono<Make> {
        return makeRepository.findByMake(make)
                .flatMap<Make?> { Mono.error(MakeFound()) }
                .switchIfEmpty(Mono.defer { makeRepository.save(Make(make = make)) })
    }

    fun getMakes(): Flux<Make> {
        return makeRepository.findAll()
    }

    fun deleteMake(id: UUID): Mono<Make> {
        return makeRepository.findById(id)
                .switchIfEmpty(Mono.defer { Mono.error(MakeNotFound()) })
                .flatMap { makeRepository.delete(it).thenReturn(it) }
    }
}