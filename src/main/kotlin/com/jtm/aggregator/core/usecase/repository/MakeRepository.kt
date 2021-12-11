package com.jtm.aggregator.core.usecase.repository

import com.jtm.aggregator.core.domain.entity.Make
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface MakeRepository: ReactiveMongoRepository<Make, UUID> {

    fun findByMake(make: String): Mono<Make>
}