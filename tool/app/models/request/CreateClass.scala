package models.request

import model.skill.SkillId
import models.core.{AsLevelAttributes, AsModel, Identifier, Name}
import play.api.libs.json.{Json, OFormat}

case class CreateClass(id: Identifier, name: Name, skills: Set[Identifier], attributes: AsLevelAttributes)
  extends AsModel[model.Class] {

  def asModel: model.Class = {
    model.Class().update(
      _.id.id := id.id,
      _.name := name.name,
      _.skillTree := skills.map(id => SkillId(id.id)).toList,
      _.attributes := attributes.asLevelAttributes)
  }
}

object CreateClass {
  implicit val format: OFormat[CreateClass] = Json.format[CreateClass]
}
