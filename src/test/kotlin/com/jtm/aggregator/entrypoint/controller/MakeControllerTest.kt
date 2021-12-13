package com.jtm.aggregator.entrypoint.controller

import com.jtm.aggregator.core.domain.entity.Make
import com.jtm.aggregator.data.service.MakeService
import com.jtm.aggregator.entrypoint.controller.car.MakeController
import org.junit.Test
import org.junit.runner.RunWith
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
@WebFluxTest(MakeController::class)
@AutoConfigureWebTestClient
class MakeControllerTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var makeService: MakeService

    private val make = Make(make = "BMW")

    @Test
    fun getMakes() {
        `when`(makeService.getMakes()).thenReturn(Flux.just(make))

        testClient.get()
                .uri("/make/all")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$[0].make").isEqualTo("BMW")

        verify(makeService, times(1)).getMakes()
        verifyNoMoreInteractions(makeService)
    }

    @Test
    fun deleteMake() {
        `when`(makeService.deleteMake(anyOrNull())).thenReturn(Mono.just(make))

        testClient.delete()
                .uri("/make/${UUID.randomUUID()}")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.make").isEqualTo("BMW")

        verify(makeService, times(1)).deleteMake(anyOrNull())
        verifyNoMoreInteractions(makeService)
    }
}