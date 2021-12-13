package com.jtm.aggregator.data.manager

import com.jtm.aggregator.core.domain.entity.OperationStats
import com.jtm.aggregator.core.usecase.queue.Operation
import com.jtm.aggregator.core.usecase.repository.OperationStatsRepository
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.kotlin.*
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import java.util.*
import java.util.concurrent.ExecutorService

@RunWith(SpringRunner::class)
class QueueManagerTest {

    private val executor: ExecutorService = mock()
    private val queue: PriorityQueue<Operation> = mock()
    private val statsRepository: OperationStatsRepository = mock()
    private val queueManager = QueueManager(executor, queue, statsRepository)
    private val operation: Operation = mock()

    @Test
    fun publish() {
        queueManager.publish(operation)

        verify(queue, times(1)).add(any())
        verifyNoMoreInteractions(queue)
    }

    @Test
    fun getOperation() {
        `when`(queue.isEmpty()).thenReturn(false)
        `when`(queue.poll()).thenReturn(operation)
        val returned = queueManager.getOperation()

        verify(queue, times(1)).isEmpty()
        verify(queue, times(1)).poll()
        verifyNoMoreInteractions(queue)


        assertNotNull(returned)
    }
}