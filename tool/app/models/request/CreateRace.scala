package models.request

import model.Race
import model.skill.SkillId
import models.core.{Identifier, AsModel, Name}
import play.api.libs.json.Json

case class CreateRace(id: Identifier, name: Name, skills: Set[Identifier]) extends AsModel[Race] {
  def asModel: Race = {
    Race().update(_.id.id := id.id, _.name := name.name, _.skillTree := skills.map(id => SkillId(id.id)).toList)
  }
}

object CreateRace {
  implicit val format = Json.format[CreateRace]
}
