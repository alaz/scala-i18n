package com.osinka.i18n

import org.scalatest.{Matchers, FunSpec}

class messagesSpec extends FunSpec with Matchers {
  val EN = Lang("en")
  val RU = Lang("ru")

  describe("Messages") {
    it("should return localized message") {
      Messages("hello")(EN) should equal("Hello")
      Messages("hello")(RU) should equal("Привет")
    }
    it("should fallback to default") {
      Messages("world")(EN) should equal("World")
      Messages("world")(RU) should equal("World")
    }
    it("should format") {
      Messages("greet", "world")(EN) should equal("Hello, world")
      Messages("greet", "world")(RU) should equal("Привет, world")
    }
    it("should throw when no default") {
      intercept[java.util.MissingResourceException] {
        Messages("nokey")(RU)
      }
    }
  }
}
