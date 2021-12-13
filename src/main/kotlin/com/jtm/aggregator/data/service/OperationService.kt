package com.jtm.aggregator.data.service

import com.jtm.aggregator.core.domain.entity.OperationStats
import com.jtm.aggregator.core.usecase.repository.OperationStatsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class OperationService @Autowired constructor(private val statsRepository: OperationStatsRepository) {

    fun getStat(id: UUID): Mono<OperationStats> {
        return Mono.empty()
    }

    fun getStats(): Flux<OperationStats> {
        return Flux.empty()
    }

    fun deleteStat(id: UUID): Mono<OperationStats> {
        return Mono.empty()
    }
}