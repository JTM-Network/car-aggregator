package com.jtm.aggregator.data.manager

import com.jtm.aggregator.core.domain.model.OperationEvent
import com.jtm.aggregator.core.usecase.queue.Operation
import com.jtm.aggregator.core.usecase.repository.OperationStatsRepository
import com.jtm.aggregator.data.worker.QueueWorker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.util.*
import java.util.concurrent.ExecutorService
import javax.annotation.PostConstruct
import kotlin.collections.HashMap

@Component
class QueueManager(var queue: PriorityQueue<Operation> = PriorityQueue(1000)) {

    private val sinks: MutableMap<UUID, Sinks.Many<OperationEvent>> = HashMap()
    private val logger = LoggerFactory.getLogger(QueueManager::class.java)

    fun publish(operation: Operation): Flux<ServerSentEvent<OperationEvent>> {
        queue.add(operation)
        val sink: Sinks.Many<OperationEvent> = Sinks.many().replay().all()
        sinks[operation.id()] = sink
        logger.info("Successfully added ${operation.name()} operation..")
        sendEvent(operation.id(), OperationEvent("QUEUED"))
        return sink.asFlux().map { ServerSentEvent.builder(it).build() }
    }

    fun sendEvent(id: UUID, event: OperationEvent) {
        val sink = sinks[id]
        if (sink == null) {
            logger.info("Sink not found.")
            return
        }
        val result = sink.tryEmitNext(event)
        if (result.isFailure) logger.info("Failed to send event.")
    }
}