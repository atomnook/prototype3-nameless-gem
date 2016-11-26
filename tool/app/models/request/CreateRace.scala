package models.request

import model.Race
import model.skill.SkillId
import models.core.{AsLevelAttributes, AsModel, Identifier, Name}
import play.api.libs.json.{Json, OFormat}

case class CreateRace(id: Identifier, name: Name, skills: Set[Identifier], attributes: AsLevelAttributes)
  extends AsModel[Race] {

  def asModel: Race = {
    Race().update(
      _.id.id := id.id,
      _.name := name.name,
      _.skillTree := skills.map(id => SkillId(id.id)).toList,
      _.attributes := attributes.asLevelAttributes)
  }
}

object CreateRace {
  implicit val format: OFormat[CreateRace] = Json.format[CreateRace]
}
