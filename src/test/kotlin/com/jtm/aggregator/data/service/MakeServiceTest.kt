package com.jtm.aggregator.data.service

import com.jtm.aggregator.core.domain.entity.Make
import com.jtm.aggregator.core.domain.exceptions.MakeFound
import com.jtm.aggregator.core.domain.exceptions.MakeNotFound
import com.jtm.aggregator.core.usecase.repository.MakeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
class MakeServiceTest {

    private val makeRepository: MakeRepository = mock()
    private val makeService = MakeService(makeRepository)
    private val make = Make(make = "BMW")

    @Test
    fun insertMake_thenFound() {
        `when`(makeRepository.findByMake(anyString())).thenReturn(Mono.just(make))

        val returned = makeService.insertMake("TEST")

        verify(makeRepository, times(1)).findByMake(anyString())
        verifyNoMoreInteractions(makeRepository)

        StepVerifier.create(returned)
                .expectError(MakeFound::class.java)
                .verify()
    }

    @Test
    fun insertMake() {
        `when`(makeRepository.findByMake(anyString())).thenReturn(Mono.empty())
        `when`(makeRepository.save(any())).thenReturn(Mono.just(make))

        val returned = makeService.insertMake("ASD")

        verify(makeRepository, times(1)).findByMake(anyString())
        verifyNoMoreInteractions(makeRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.make).isEqualTo("BMW")
                }
                .verifyComplete()
    }

    @Test
    fun getMakes() {
        `when`(makeRepository.findAll()).thenReturn(Flux.just(make))

        val returned = makeService.getMakes()

        verify(makeRepository, times(1)).findAll()
        verifyNoMoreInteractions(makeRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.make).isEqualTo("BMW")
                }
                .verifyComplete()
    }

    @Test
    fun deleteMake_thenNotFound() {
        `when`(makeRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = makeService.deleteMake(UUID.randomUUID())

        verify(makeRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(makeRepository)

        StepVerifier.create(returned)
                .expectError(MakeNotFound::class.java)
                .verify()
    }

    @Test
    fun deleteMake() {
        `when`(makeRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(make))
        `when`(makeRepository.delete(any())).thenReturn(Mono.empty())

        val returned = makeService.deleteMake(UUID.randomUUID())

        verify(makeRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(makeRepository)

        StepVerifier.create(returned)
                .assertNext { assertThat(it.make).isEqualTo("BMW") }
                .verifyComplete()
    }
}