package com.yammer.metrics.logback

import ch.qos.logback.classic.{Level, LoggerContext}
import com.codahale.simplespec.Spec

class LogbackInstrumentedAppenderSpec extends Spec {
  class `A Logback InstrumentedAppender` {
    private val lc = new LoggerContext()
    private val logger = lc.getLogger("abc.def")

    private val appender = new InstrumentedAppender()
    appender.setContext(lc)
    appender.start()
    logger.addAppender(appender)
    logger.setLevel(Level.TRACE)

    def `should maintain accurate counts` = {
      InstrumentedAppender.ALL_METER.count must beEqualTo(0)
      InstrumentedAppender.TRACE_METER.count must beEqualTo(0)
      InstrumentedAppender.DEBUG_METER.count must beEqualTo(0)
      InstrumentedAppender.INFO_METER.count must beEqualTo(0)
      InstrumentedAppender.WARN_METER.count must beEqualTo(0)
      InstrumentedAppender.ERROR_METER.count must beEqualTo(0)

      logger.trace("Test")

      InstrumentedAppender.ALL_METER.count must beEqualTo(1)
      InstrumentedAppender.TRACE_METER.count must beEqualTo(1)
      InstrumentedAppender.DEBUG_METER.count must beEqualTo(0)
      InstrumentedAppender.INFO_METER.count must beEqualTo(0)
      InstrumentedAppender.WARN_METER.count must beEqualTo(0)
      InstrumentedAppender.ERROR_METER.count must beEqualTo(0)

      logger.trace("Test")
      logger.debug("Test")

      InstrumentedAppender.ALL_METER.count must beEqualTo(3)
      InstrumentedAppender.TRACE_METER.count must beEqualTo(2)
      InstrumentedAppender.DEBUG_METER.count must beEqualTo(1)
      InstrumentedAppender.INFO_METER.count must beEqualTo(0)
      InstrumentedAppender.WARN_METER.count must beEqualTo(0)
      InstrumentedAppender.ERROR_METER.count must beEqualTo(0)

      logger.info("Test")

      InstrumentedAppender.ALL_METER.count must beEqualTo(4)
      InstrumentedAppender.TRACE_METER.count must beEqualTo(2)
      InstrumentedAppender.DEBUG_METER.count must beEqualTo(1)
      InstrumentedAppender.INFO_METER.count must beEqualTo(1)
      InstrumentedAppender.WARN_METER.count must beEqualTo(0)
      InstrumentedAppender.ERROR_METER.count must beEqualTo(0)

      logger.warn("Test")

      InstrumentedAppender.ALL_METER.count must beEqualTo(5)
      InstrumentedAppender.TRACE_METER.count must beEqualTo(2)
      InstrumentedAppender.DEBUG_METER.count must beEqualTo(1)
      InstrumentedAppender.INFO_METER.count must beEqualTo(1)
      InstrumentedAppender.WARN_METER.count must beEqualTo(1)
      InstrumentedAppender.ERROR_METER.count must beEqualTo(0)

      logger.error("Test")

      InstrumentedAppender.ALL_METER.count must beEqualTo(6)
      InstrumentedAppender.TRACE_METER.count must beEqualTo(2)
      InstrumentedAppender.DEBUG_METER.count must beEqualTo(1)
      InstrumentedAppender.INFO_METER.count must beEqualTo(1)
      InstrumentedAppender.WARN_METER.count must beEqualTo(1)
      InstrumentedAppender.ERROR_METER.count must beEqualTo(1)
    }
  }
}
