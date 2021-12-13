package com.jtm.aggregator.core.usecase.repository

import com.jtm.aggregator.core.domain.entity.OperationStats
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.*

@Repository
interface OperationStatsRepository: ReactiveMongoRepository<OperationStats, UUID> {

    fun findByName(name: String): Flux<OperationStats>
}