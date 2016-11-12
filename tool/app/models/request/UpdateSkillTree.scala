package models.request

import model.skill.SkillId
import models.core.Identifier
import play.api.libs.json.Json

case class UpdateSkillTree(skills: Set[Identifier]) {
  def asModel: Seq[SkillId] = skills.map(id => SkillId(id.id)).toList
}

object UpdateSkillTree {
  implicit val format = Json.format[UpdateSkillTree]
}
