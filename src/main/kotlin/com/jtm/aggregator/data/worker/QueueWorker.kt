package com.jtm.aggregator.data.worker

import com.jtm.aggregator.core.domain.entity.OperationStats
import com.jtm.aggregator.core.usecase.repository.OperationStatsRepository
import com.jtm.aggregator.data.manager.QueueManager
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QueueWorker @Autowired constructor(private val queueManager: QueueManager, private val statsRepository: OperationStatsRepository): Runnable {

    private val active: Boolean = true
    private val logger = LoggerFactory.getLogger(QueueWorker::class.java)

    override fun run() {
        logger.info("Starting queue worker in 10 seconds...")
        Thread.sleep(10 * 1000)
        logger.info("Queue worker started.")
        while (active) {
            logger.debug("TEST...")
            val operation = queueManager.queue.poll() ?: continue
            logger.info("Operation found ${operation.name()}...")
            operation.init()
            operation.run().block()
            operation.post()
            statsRepository.save(OperationStats(id = operation.id(), name = operation.name(), startTime = operation.startTime(), timeTaken = operation.timeTaken()))
                    .thenReturn(Unit::class).block()
        }
    }
}