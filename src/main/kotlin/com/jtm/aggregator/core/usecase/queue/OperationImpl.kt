package com.jtm.aggregator.core.usecase.queue

import com.jtm.aggregator.core.domain.model.OperationEvent
import com.jtm.aggregator.data.manager.QueueManager
import org.slf4j.LoggerFactory
import java.util.*

abstract class OperationImpl(private val queueManager: QueueManager, val id: UUID = UUID.randomUUID(), val name: String, private val priority: Int): Operation {

    private val logger = LoggerFactory.getLogger(OperationImpl::class.java)
    private var startTime: Long = 0
    private var taken: Long = 0

    override fun init() {
        this.startTime = System.currentTimeMillis()
        logger.info("Operation started: $startTime")
    }

    override fun post() {
        this.taken = (System.currentTimeMillis() - startTime)
        logger.info("$name operation took: $taken ms")
    }

    override fun sendEvent(event: OperationEvent) {
        queueManager.sendEvent(id, event)
    }

    override fun id(): UUID {
        return id
    }

    override fun name(): String {
        return name
    }

    override fun priority(): Int {
        return priority
    }

    override fun timeTaken(): Long {
        return taken
    }

    override fun startTime(): Long {
        return startTime
    }

    override fun compareTo(other: Operation): Int {
        return priority - other.priority()
    }
}