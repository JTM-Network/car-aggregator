package com.jtm.aggregator.data.operation

import com.jtm.aggregator.core.domain.model.OperationEvent
import com.jtm.aggregator.core.usecase.queue.OperationImpl
import com.jtm.aggregator.data.manager.QueueManager
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

class SearchOperation(queueManager: QueueManager): OperationImpl(queueManager, name = "search", priority = 1) {

    private val logger = LoggerFactory.getLogger(SearchOperation::class.java)

    override fun run(): Mono<Void> {
        logger.info("Searching for stuff.")
        sendEvent(OperationEvent("SEARCHING"))
        Thread.sleep(1000)
        sendEvent(OperationEvent("COMPLETED"))
        logger.info("Completed operation...")
        return Mono.empty()
    }
}