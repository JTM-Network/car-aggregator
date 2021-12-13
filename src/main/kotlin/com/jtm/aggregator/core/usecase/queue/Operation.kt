package com.jtm.aggregator.core.usecase.queue

import com.jtm.aggregator.core.domain.model.OperationEvent
import reactor.core.publisher.Mono
import java.util.*

interface Operation: Comparable<Operation> {

    fun init()

    fun post()

    fun run(): Mono<Void>

    fun sendEvent(event: OperationEvent)

    fun name(): String

    fun id(): UUID

    fun timeTaken(): Long

    fun startTime(): Long

    fun priority(): Int
}