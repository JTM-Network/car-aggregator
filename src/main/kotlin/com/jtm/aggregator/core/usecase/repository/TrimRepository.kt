package com.jtm.aggregator.core.usecase.repository

import com.jtm.aggregator.core.domain.entity.Trim
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface TrimRepository: ReactiveMongoRepository<Trim, UUID> {

    fun findByTrimAndModel(trim: String, model: String): Mono<Trim>
}