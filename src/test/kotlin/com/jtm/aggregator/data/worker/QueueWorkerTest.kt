package com.jtm.aggregator.data.worker

import com.jtm.aggregator.core.domain.entity.OperationStats
import com.jtm.aggregator.core.usecase.queue.Operation
import com.jtm.aggregator.core.usecase.repository.OperationStatsRepository
import com.jtm.aggregator.data.manager.QueueManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import java.util.*

@RunWith(SpringRunner::class)
class QueueWorkerTest {

    private val queueManager: QueueManager = mock()
    private val statsRepository: OperationStatsRepository = mock()
    private val queueWorker = QueueWorker(queueManager, statsRepository)
    private val operation: Operation = mock()

    @Before
    fun setup() {
        `when`(operation.id()).thenReturn(UUID.randomUUID())
        `when`(operation.name()).thenReturn("test")
        `when`(operation.startTime()).thenReturn(System.currentTimeMillis())
        `when`(operation.timeTaken()).thenReturn(System.currentTimeMillis())
    }

    @Test
    fun process_thenOperationNull() {
        `when`(queueManager.getOperation()).thenReturn(null)

        queueWorker.process()

        verify(queueManager, times(1)).getOperation()
        verifyNoMoreInteractions(queueManager)
    }

    @Test
    fun process() {
        `when`(queueManager.getOperation()).thenReturn(operation)
        `when`(operation.run()).thenReturn(Mono.empty())
        `when`(statsRepository.save(any())).thenReturn(Mono.just(OperationStats(id = UUID.randomUUID(), name = "test", startTime = System.currentTimeMillis(), timeTaken = System.currentTimeMillis())))

        queueWorker.process()

        verify(operation, times(1)).id()
        verify(operation, times(1)).init()
        verify(operation, times(1)).run()
        verify(operation, times(1)).post()
        verify(operation, times(1)).name()
        verify(operation, times(1)).startTime()
        verify(operation, times(1)).timeTaken()
        verifyNoMoreInteractions(operation)

        verify(statsRepository, times(1)).save(any())
        verifyNoMoreInteractions(statsRepository)
    }
}