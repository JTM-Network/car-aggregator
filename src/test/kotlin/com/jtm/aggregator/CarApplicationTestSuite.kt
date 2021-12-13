package com.jtm.aggregator

import com.jtm.aggregator.data.manager.QueueManagerTest
import com.jtm.aggregator.data.service.MakeServiceTest
import com.jtm.aggregator.data.service.ModelServiceTest
import com.jtm.aggregator.data.service.TrimServiceTest
import com.jtm.aggregator.data.worker.QueueWorkerTest
import com.jtm.aggregator.entrypoint.controller.MakeControllerTest
import com.jtm.aggregator.entrypoint.controller.ModelControllerTest
import com.jtm.aggregator.entrypoint.controller.TrimControllerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(value = [
    MakeServiceTest::class,
    ModelServiceTest::class,
    TrimServiceTest::class,

    MakeControllerTest::class,
    ModelControllerTest::class,
    TrimControllerTest::class,

    QueueManagerTest::class,
    QueueWorkerTest::class
])
class CarApplicationTestSuite