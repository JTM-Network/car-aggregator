package com.jtm.aggregator.data.service

import com.jtm.aggregator.core.domain.entity.Model
import com.jtm.aggregator.core.domain.exceptions.ModelFound
import com.jtm.aggregator.core.domain.exceptions.ModelNotFound
import com.jtm.aggregator.core.usecase.repository.ModelRepository
import org.assertj.core.api.Assertions.`as`
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
class ModelServiceTest {

    private val modelRepository: ModelRepository = mock()
    private val modelService = ModelService(modelRepository)
    private val model = Model(make = "BMW", model = "3 Series")

    @Test
    fun insertModel_thenFound() {
        `when`(modelRepository.findByModelAndMake(anyString(), anyString())).thenReturn(Mono.just(model))

        val returned = modelService.insertModel("3 Series", "BMW")

        verify(modelRepository, times(1)).findByModelAndMake(anyString(), anyString())
        verifyNoMoreInteractions(modelRepository)

        StepVerifier.create(returned)
                .expectError(ModelFound::class.java)
                .verify()
    }

    @Test
    fun insertModel() {
        `when`(modelRepository.findByModelAndMake(anyString(), anyString())).thenReturn(Mono.empty())
        `when`(modelRepository.save(any())).thenReturn(Mono.just(model))

        val returned = modelService.insertModel("3 Series", "BMW")

        verify(modelRepository, times(1)).findByModelAndMake(anyString(), anyString())
        verifyNoMoreInteractions(modelRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.make).isEqualTo("BMW")
                    assertThat(it.model).isEqualTo("3 Series")
                }
                .verifyComplete()
    }

    @Test
    fun getModels() {
        `when`(modelRepository.findAll()).thenReturn(Flux.just(model))

        val returned = modelService.getModels("BMW")

        verify(modelRepository, times(1)).findAll()
        verifyNoMoreInteractions(modelRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.make).isEqualTo("BMW")
                    assertThat(it.model).isEqualTo("3 Series")
                }
                .verifyComplete()
    }

    @Test
    fun deleteModel_thenNotFound() {
        `when`(modelRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = modelService.deleteModel(UUID.randomUUID())

        verify(modelRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(modelRepository)

        StepVerifier.create(returned)
                .expectError(ModelNotFound::class.java)
                .verify()
    }

    @Test
    fun deleteModel() {
        `when`(modelRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(model))
        `when`(modelRepository.delete(any())).thenReturn(Mono.empty())

        val returned = modelService.deleteModel(UUID.randomUUID())

        verify(modelRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(modelRepository)

        StepVerifier.create(returned)
                .assertNext {
                    assertThat(it.make).isEqualTo("BMW")
                    assertThat(it.model).isEqualTo("3 Series")
                }
                .verifyComplete()
    }
}