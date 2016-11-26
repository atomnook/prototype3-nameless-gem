package models.request

import model.Attribute
import model.skill.AttributeBoostSkill
import models.core._
import play.api.libs.json.{Json, OFormat}

case class CreateAttributeBoostSkill(id: Identifier,
                                     name: Name,
                                     prerequisites: Set[Identifier],
                                     attribute: Attribute) extends AsModel[AttributeBoostSkill] with AsSkill {
  override def asModel: AttributeBoostSkill = {
    AttributeBoostSkill().update(_.skill := asSkill, _.attribute := attribute)
  }
}

object CreateAttributeBoostSkill {
  implicit val format: OFormat[CreateAttributeBoostSkill] = Json.format[CreateAttributeBoostSkill]
}
