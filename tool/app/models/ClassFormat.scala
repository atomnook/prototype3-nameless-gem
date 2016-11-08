package models

import model.skill.SkillId
import play.api.libs.json.Json

case class ClassFormat(id: Identifier, name: Name, skills: Set[Identifier]) extends ModelFormat[model.Class] {
  def asModel: model.Class = {
    model.Class().update(_.id.id := id.id, _.name := name.name, _.skillTree := skills.map(id => SkillId(id.id)).toList)
  }
}

object ClassFormat {
  implicit val format = Json.format[ClassFormat]
}
