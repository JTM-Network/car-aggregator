package com.jtm.aggregator.core.usecase.queue

import reactor.core.publisher.Mono

interface Operation: Comparable<Operation> {

    fun init()

    fun post()

    fun run(): Mono<Void>

    fun name(): String

    fun timeTaken(): Long

    fun startTime(): Long

    fun priority(): Int
}