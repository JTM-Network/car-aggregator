package com.jtm.aggregator.entrypoint.controller.car

import com.jtm.aggregator.core.domain.entity.Model
import com.jtm.aggregator.data.service.ModelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/model")
class ModelController @Autowired constructor(private val modelService: ModelService) {

    @GetMapping("/{make}/all")
    fun getModels(@PathVariable make: String): Flux<Model> {
        return modelService.getModels(make)
    }

    @DeleteMapping("/{id}")
    fun deleteModel(@PathVariable id: UUID): Mono<Model> {
        return modelService.deleteModel(id)
    }
}