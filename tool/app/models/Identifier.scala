package models

import play.api.data.validation.ValidationError
import play.api.libs.json._

case class Identifier(id: String)

object Identifier {
  private[this] val regex = "([a-zA-Z0-9\\-]+)".r

  implicit val format: Format[Identifier] = new Format[Identifier] {
    override def writes(o: Identifier): JsValue = JsString(o.id)

    override def reads(json: JsValue): JsResult[Identifier] = {
      json.validate[String].flatMap {
        case regex(s) => JsSuccess(Identifier(s))
        case s => JsError(ValidationError("not match [a-zA-Z0-9\\-]+", s))
      }
    }
  }
}
