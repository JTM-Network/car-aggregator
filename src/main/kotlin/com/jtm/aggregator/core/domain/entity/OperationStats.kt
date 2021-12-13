package com.jtm.aggregator.core.domain.entity

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("operation_stats")
data class OperationStats(val id: UUID = UUID.randomUUID(), val name: String, val startTime: Long, val timeTaken: Long)