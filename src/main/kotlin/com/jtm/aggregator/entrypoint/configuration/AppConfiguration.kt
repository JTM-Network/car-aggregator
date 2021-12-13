package com.jtm.aggregator.entrypoint.configuration

import com.jtm.aggregator.data.worker.QueueWorker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Configuration
open class AppConfiguration @Autowired constructor(private val queueWorker: QueueWorker) {

    @Bean
    open fun executor(): ExecutorService {
        val executor = Executors.newSingleThreadScheduledExecutor()
        executor.submit(queueWorker)
        return executor
    }
}