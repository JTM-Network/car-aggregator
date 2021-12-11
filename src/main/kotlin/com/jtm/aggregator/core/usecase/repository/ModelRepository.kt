package com.jtm.aggregator.core.usecase.repository

import com.jtm.aggregator.core.domain.entity.Model
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface ModelRepository: ReactiveMongoRepository<Model, UUID> {

    fun findByModelAndMake(model: String, make: String): Mono<Model>
}