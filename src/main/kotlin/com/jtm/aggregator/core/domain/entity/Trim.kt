package com.jtm.aggregator.core.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("trims")
class Trim(@Id val id: UUID = UUID.randomUUID(), val trim: String, val model: String)