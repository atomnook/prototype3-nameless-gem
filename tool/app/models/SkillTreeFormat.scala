package models

import model.skill.SkillId
import play.api.libs.json.Json

case class SkillTreeFormat(skills: Set[Identifier]) {
  def asModel: Seq[SkillId] = skills.map(id => SkillId(id.id)).toList
}

object SkillTreeFormat {
  implicit val format = Json.format[SkillTreeFormat]
}
