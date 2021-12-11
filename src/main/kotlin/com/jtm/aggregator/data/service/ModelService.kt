package com.jtm.aggregator.data.service

import com.jtm.aggregator.core.domain.entity.Model
import com.jtm.aggregator.core.domain.exceptions.ModelFound
import com.jtm.aggregator.core.domain.exceptions.ModelNotFound
import com.jtm.aggregator.core.usecase.repository.ModelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class ModelService @Autowired constructor(private val modelRepository: ModelRepository) {

    fun insertModel(model: String, make: String): Mono<Model> {
        return modelRepository.findByModelAndMake(model, make)
                .flatMap<Model?> { Mono.error(ModelFound()) }
                .switchIfEmpty(Mono.defer { modelRepository.save(Model(model = model, make = make)) })
    }

    fun getModels(make: String): Flux<Model> {
        return modelRepository.findAll().filter { it.make == make }
    }

    fun deleteModel(id: UUID): Mono<Model> {
        return modelRepository.findById(id)
                .switchIfEmpty(Mono.defer { Mono.error(ModelNotFound()) })
                .flatMap { modelRepository.delete(it).thenReturn(it) }
    }
}