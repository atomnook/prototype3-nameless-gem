package models

import play.api.data.validation.ValidationError
import play.api.libs.json._

case class Name(name: String)

object Name {
  implicit val format = new Format[Name] {
    override def reads(json: JsValue): JsResult[Name] = {
      json.validate[String].flatMap {
        case s if s.isEmpty => JsError(ValidationError("empty string"))
        case s => JsSuccess(Name(s))
      }
    }

    override def writes(o: Name): JsValue = JsString(o.name)
  }
}
