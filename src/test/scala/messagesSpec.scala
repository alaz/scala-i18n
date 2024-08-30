package com.osinka.i18n

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers._

class messagesSpec extends AnyFunSpec with Matchers {
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
    it("should throw if no resource") {
      object NoResourceMessage extends Messages {
        override val FileName = "nonexistant"
      }

      intercept[java.util.MissingResourceException] {
        NoResourceMessage("nokey")(EN)
      }
    }
  }
}
