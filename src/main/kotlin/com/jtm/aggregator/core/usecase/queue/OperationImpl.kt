package com.jtm.aggregator.core.usecase.queue

abstract class OperationImpl(val name: String, private val priority: Int): Operation {

    private var startTime: Long = 0
    private var taken: Long = 0

    override fun init() {
        this.startTime = System.currentTimeMillis()
    }

    override fun post() {
        this.taken = (System.currentTimeMillis() - startTime)
    }

    override fun timeTaken(): Long {
        return taken
    }

    override fun startTime(): Long {
        return startTime
    }

    override fun compareTo(other: Operation): Int {
        return priority - other.priority()
    }
}