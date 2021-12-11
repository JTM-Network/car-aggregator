package com.jtm.aggregator.data.service

import com.jtm.aggregator.core.domain.entity.Trim
import com.jtm.aggregator.core.domain.exceptions.TrimFound
import com.jtm.aggregator.core.domain.exceptions.TrimNotFound
import com.jtm.aggregator.core.usecase.repository.TrimRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class TrimService @Autowired constructor(private val trimRepository: TrimRepository) {

    fun insertTrim(trim: String, model: String): Mono<Trim> {
        return trimRepository.findByTrimAndModel(trim, model)
                .flatMap<Trim?> { Mono.defer { Mono.error(TrimFound()) } }
                .switchIfEmpty(Mono.defer { trimRepository.save(Trim(trim = trim, model = model)) })
    }

    fun getTrims(model: String): Flux<Trim> {
        return trimRepository.findAll().filter { it.model == model }
    }

    fun deleteTrim(id: UUID): Mono<Trim> {
        return trimRepository.findById(id)
                .switchIfEmpty(Mono.defer { Mono.error(TrimNotFound()) })
                .flatMap { trimRepository.delete(it).thenReturn(it) }
    }
}