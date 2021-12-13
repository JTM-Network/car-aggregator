package com.jtm.aggregator.entrypoint.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Configuration
open class AppConfiguration {

    @Bean
    open fun executor(): ExecutorService {
        return Executors.newSingleThreadScheduledExecutor()
    }
}