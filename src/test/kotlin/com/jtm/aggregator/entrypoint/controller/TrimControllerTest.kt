package com.jtm.aggregator.entrypoint.controller

import com.jtm.aggregator.core.domain.entity.Trim
import com.jtm.aggregator.data.service.TrimService
import com.jtm.aggregator.entrypoint.controller.car.TrimController
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
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
@WebFluxTest(TrimController::class)
@AutoConfigureWebTestClient
class TrimControllerTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var trimService: TrimService

    private val trim = Trim(model = "3 Series", trim = "M Sport")

    @Test
    fun getTrims() {
        `when`(trimService.getTrims(anyString())).thenReturn(Flux.just(trim))

        testClient.get()
                .uri("/trim/'3 Series'/all")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$[0].model").isEqualTo("3 Series")
                .jsonPath("$[0].trim").isEqualTo("M Sport")

        verify(trimService, times(1)).getTrims(anyString())
        verifyNoMoreInteractions(trimService)
    }

    @Test
    fun deleteTrim() {
        `when`(trimService.deleteTrim(anyOrNull())).thenReturn(Mono.just(trim))

        testClient.delete()
                .uri("/trim/${UUID.randomUUID()}")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.model").isEqualTo("3 Series")
                .jsonPath("$.trim").isEqualTo("M Sport")

        verify(trimService, times(1)).deleteTrim(anyOrNull())
        verifyNoMoreInteractions(trimService)
    }
}