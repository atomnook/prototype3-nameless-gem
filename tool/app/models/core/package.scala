package models

import com.trueaccord.scalapb.{GeneratedEnum, GeneratedEnumCompanion}
import model.skill.SkillType
import model.{Element, Target}
import play.api.libs.json.{Format, JsNumber, JsResult, JsValue}

package object core {
  private[this] def protoFormat[A <: GeneratedEnum](c: GeneratedEnumCompanion[A]): Format[A] = {
    new Format[A] {
      override def writes(o: A): JsValue = JsNumber(o.value)

      override def reads(json: JsValue): JsResult[A] = json.validate[Int].map(c.fromValue)
    }
  }

  implicit val rangeFormat = protoFormat(model.Range)

  implicit val targetFormat = protoFormat(Target)

  implicit val skillTypeFormat = protoFormat(SkillType)

  implicit val elementFormat = protoFormat(Element)
}
