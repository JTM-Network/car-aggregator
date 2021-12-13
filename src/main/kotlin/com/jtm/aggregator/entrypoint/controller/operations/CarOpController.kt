package com.jtm.aggregator.entrypoint.controller.operations

import com.jtm.aggregator.core.domain.model.OperationEvent
import com.jtm.aggregator.data.manager.QueueManager
import com.jtm.aggregator.data.operation.SearchOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/car/op")
class CarOpController @Autowired constructor(private val queueManager: QueueManager) {

    @GetMapping("/search", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun requestInformation(): Flux<ServerSentEvent<OperationEvent>> {
        return queueManager.publish(SearchOperation(queueManager))
    }
}