package models.request

import model.skill.SkillId
import models.core.{Identifier, AsModel, Name}
import play.api.libs.json.Json

case class CreateClass(id: Identifier, name: Name, skills: Set[Identifier]) extends AsModel[model.Class] {
  def asModel: model.Class = {
    model.Class().update(_.id.id := id.id, _.name := name.name, _.skillTree := skills.map(id => SkillId(id.id)).toList)
  }
}

object CreateClass {
  implicit val format = Json.format[CreateClass]
}
