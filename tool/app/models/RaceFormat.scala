package models

import model.Race
import model.skill.SkillId
import play.api.libs.json.Json

case class RaceFormat(id: Identifier, name: Name, skills: Set[Identifier]) extends ModelFormat[Race] {
  def asModel: Race = {
    Race().update(_.id.id := id.id, _.name := name.name, _.skillTree := skills.map(id => SkillId(id.id)).toList)
  }
}

object RaceFormat {
  implicit val format = Json.format[RaceFormat]
}
