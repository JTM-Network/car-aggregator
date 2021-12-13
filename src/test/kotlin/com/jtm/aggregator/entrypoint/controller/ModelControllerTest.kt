package com.jtm.aggregator.entrypoint.controller

import com.jtm.aggregator.core.domain.entity.Model
import com.jtm.aggregator.data.service.ModelService
import com.jtm.aggregator.entrypoint.controller.car.ModelController
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RunWith(SpringRunner::class)
@WebFluxTest(ModelController::class)
@AutoConfigureWebTestClient
class ModelControllerTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var modelService: ModelService

    private val model = Model(make = "BMW", model = "3 Series")

    @Test
    fun getModels() {
        `when`(modelService.getModels(anyString())).thenReturn(Flux.just(model))

        testClient.get()
                .uri("/model/BMW/all")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$[0].make").isEqualTo("BMW")
                .jsonPath("$[0].model").isEqualTo("3 Series")

        verify(modelService, times(1)).getModels(anyString())
        verifyNoMoreInteractions(modelService)
    }

    @Test
    fun deleteModel() {
        `when`(modelService.deleteModel(anyOrNull())).thenReturn(Mono.just(model))

        testClient.delete()
                .uri("/model/${UUID.randomUUID()}")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.make").isEqualTo("BMW")
                .jsonPath("$.model").isEqualTo("3 Series")

        verify(modelService, times(1)).deleteModel(anyOrNull())
        verifyNoMoreInteractions(modelService)
    }
}