package com.jtm.aggregator.data.worker

import com.jtm.aggregator.core.domain.entity.OperationStats
import com.jtm.aggregator.core.usecase.repository.OperationStatsRepository
import com.jtm.aggregator.data.manager.QueueManager
import org.slf4j.LoggerFactory
import kotlin.math.log

class QueueWorker(private val queueManager: QueueManager, private val statsRepository: OperationStatsRepository): Runnable {

    private val logger = LoggerFactory.getLogger(QueueWorker::class.java)

    override fun run() {
        logger.info("Starting queue worker...")
        while (true) { process() }
    }

    fun process() {
        val operation = queueManager.getOperation()
        if (operation == null) {
            logger.info("Operation not found.")
            return
        }

        logger.info("Operation found ${operation.name()}...")
        operation.init()
        operation.run().block()
        operation.post()
        statsRepository.save(OperationStats(id = operation.id(), name = operation.name(), startTime = operation.startTime(), timeTaken = operation.timeTaken())).block()
    }
}