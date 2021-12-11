package com.jtm.aggregator.data.service

import com.jtm.aggregator.core.domain.entity.Trim
import com.jtm.aggregator.core.domain.exceptions.TrimFound
import com.jtm.aggregator.core.domain.exceptions.TrimNotFound
import com.jtm.aggregator.core.usecase.repository.TrimRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
class TrimServiceTest {

    private val trimRepository: TrimRepository = mock()
    private val trimService = TrimService(trimRepository)
    private val trim = Trim(trim = "M Sport", model = "3 Series")

    @Test
    fun insertTrim_thenFound() {
        `when`(trimRepository.findByTrimAndModel(anyString(), anyString())).thenReturn(Mono.just(trim))

        val returned = trimService.insertTrim("M Sport", "3 Series")

        verify(trimRepository, times(1)).findByTrimAndModel(anyString(), anyString())
        verifyNoMoreInteractions(trimRepository)

        StepVerifier.create(returned)
                .expectError(TrimFound::class.java)
                .verify()
    }

    @Test
    fun insertTrim() {
        `when`(trimRepository.findByTrimAndModel(anyString(), anyString())).thenReturn(Mono.empty())
        `when`(trimRepository.save(any())).thenReturn(Mono.just(trim))

        val returned = trimService.insertTrim("M Sport","3 Series")

        verify(trimRepository, times(1)).findByTrimAndModel(anyString(), anyString())
        verifyNoMoreInteractions(trimRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.model).isEqualTo("3 Series")
                    assertThat(it.trim).isEqualTo("M Sport")
                }
                .verifyComplete()
    }

    @Test
    fun getTrims() {
        `when`(trimRepository.findAll()).thenReturn(Flux.just(trim))

        val returned = trimService.getTrims( "3 Series")

        verify(trimRepository, times(1)).findAll()
        verifyNoMoreInteractions(trimRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.model).isEqualTo("3 Series")
                    assertThat(it.trim).isEqualTo("M Sport")
                }
                .verifyComplete()
    }

    @Test
    fun deleteTrim_thenNotFound() {
        `when`(trimRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = trimService.deleteTrim(UUID.randomUUID())

        verify(trimRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(trimRepository)

        StepVerifier.create(returned)
                .expectError(TrimNotFound::class.java)
                .verify()
    }

    @Test
    fun deleteTrim() {
        `when`(trimRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(trim))
        `when`(trimRepository.delete(any())).thenReturn(Mono.empty())

        val returned = trimService.deleteTrim(UUID.randomUUID())

        verify(trimRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(trimRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.model).isEqualTo("3 Series")
                    assertThat(it.trim).isEqualTo("M Sport")
                }
                .verifyComplete()
    }
}