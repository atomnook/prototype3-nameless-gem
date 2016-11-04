package models

import play.api.data.validation.ValidationError
import play.api.libs.json._

case class Xp(xp: Long)

object Xp {
  implicit val format = new Format[Xp] {
    override def reads(json: JsValue): JsResult[Xp] = {
      json.validate[Long].flatMap {
        case xp if xp < 0 => JsError(ValidationError("negative xp", xp))
        case xp => JsSuccess(Xp(xp))
      }
    }

    override def writes(o: Xp): JsValue = JsNumber(o.xp)
  }
}
