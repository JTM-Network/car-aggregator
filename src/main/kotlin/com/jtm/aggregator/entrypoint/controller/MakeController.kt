package com.jtm.aggregator.entrypoint.controller

import com.jtm.aggregator.core.domain.entity.Make
import com.jtm.aggregator.core.usecase.repository.MakeRepository
import com.jtm.aggregator.data.service.MakeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/make")
class MakeController @Autowired constructor(private val makeService: MakeService) {

    @GetMapping("/all")
    fun getMakes(): Flux<Make> {
        return makeService.getMakes()
    }

    @DeleteMapping("/{id}")
    fun deleteMake(@PathVariable id: UUID): Mono<Make> {
        return makeService.deleteMake(id)
    }
}