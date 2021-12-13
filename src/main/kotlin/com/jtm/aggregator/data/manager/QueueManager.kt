package com.jtm.aggregator.data.manager

import com.jtm.aggregator.core.usecase.queue.Operation
import com.jtm.aggregator.core.usecase.repository.OperationStatsRepository
import com.jtm.aggregator.data.worker.QueueWorker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ExecutorService
import javax.annotation.PostConstruct

@Component
class QueueManager @Autowired constructor(private val executor: ExecutorService, private val statsRepository: OperationStatsRepository) {

    constructor(executor: ExecutorService, queue: PriorityQueue<Operation>, statsRepository: OperationStatsRepository): this(executor, statsRepository) {
        this.queue = queue
    }

    private var queue: PriorityQueue<Operation> = PriorityQueue(1000)

    @PostConstruct
    fun init() {
        executor.submit(QueueWorker(this, statsRepository))
    }

    fun publish(operation: Operation) {
        queue.add(operation)
    }

    fun getOperation(): Operation? {
        if (queue.isEmpty()) return null
        return queue.poll()
    }
}