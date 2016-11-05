package models

import play.api.libs.json.Json

case class BaseAndIncreasing(base: Long, increasing: Long)

object BaseAndIncreasing {
  implicit val format = Json.format[BaseAndIncreasing]
}
