package com.jtm.aggregator.core.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("models")
data class Model(@Id val id: UUID = UUID.randomUUID(), val model: String, val make: String)