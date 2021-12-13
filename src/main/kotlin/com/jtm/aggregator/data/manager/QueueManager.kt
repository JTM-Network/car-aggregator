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
class QueueManager @Autowired constructor(private val executor: ExecutorService, private val statsRepository: OperationStatsRepository) {

    constructor(executor: ExecutorService, queue: PriorityQueue<Operation>, statsRepository: OperationStatsRepository): this(executor, statsRepository) {
        this.queue = queue
    }

    private var queue: PriorityQueue<Operation> = PriorityQueue(1000)
    private val sinks: MutableMap<UUID, Sinks.Many<OperationEvent>> = HashMap()
    private val logger = LoggerFactory.getLogger(QueueManager::class.java)

    @PostConstruct
    fun init() {
        executor.submit(QueueWorker(this, statsRepository))
    }

    fun publish(operation: Operation): Flux<ServerSentEvent<OperationEvent>> {
        queue.add(operation)
        val sink: Sinks.Many<OperationEvent> = Sinks.many().replay().all()
        sinks[operation.id()] = sink
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

    fun getOperation(): Operation? {
        if (queue.isEmpty()) return null
        return queue.poll()
    }
}