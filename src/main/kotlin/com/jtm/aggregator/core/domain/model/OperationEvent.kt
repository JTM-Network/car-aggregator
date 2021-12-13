package com.jtm.aggregator.core.domain.model

data class OperationEvent(val message: String, val timestamp: Long = System.currentTimeMillis())