package com.jtm.aggregator.entrypoint.controller

import com.jtm.aggregator.core.domain.entity.Trim
import com.jtm.aggregator.data.service.TrimService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/trim")
class TrimController @Autowired constructor(private val trimService: TrimService) {

    @GetMapping("/{model}/all")
    fun getTrims(@PathVariable model: String): Flux<Trim> {
        return trimService.getTrims(model)
    }

    @DeleteMapping("/{id}")
    fun deleteTrim(@PathVariable id: UUID): Mono<Trim> {
        return trimService.deleteTrim(id)
    }
}