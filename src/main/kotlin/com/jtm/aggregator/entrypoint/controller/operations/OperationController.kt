package com.jtm.aggregator.entrypoint.controller.operations

import com.jtm.aggregator.core.domain.entity.OperationStats
import com.jtm.aggregator.data.service.OperationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/ops")
class OperationController @Autowired constructor(private val operationService: OperationService) {

    @GetMapping("/stat/{id}")
    fun getStat(@PathVariable id: UUID): Mono<OperationStats> {
        return operationService.getStat(id)
    }

    @GetMapping("/stat/all")
    fun getStats(): Flux<OperationStats> {
        return operationService.getStats()
    }

    @DeleteMapping("/stat/{id}")
    fun deleteState(@PathVariable id: UUID): Mono<OperationStats> {
        return operationService.deleteStat(id)
    }
}