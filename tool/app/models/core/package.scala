package models

import com.trueaccord.scalapb.{GeneratedEnum, GeneratedEnumCompanion}
import model.item.{ArmorType, WeaponType}
import model.skill.SkillType
import model.{Attribute, Element, Range, Target}
import play.api.libs.json.{Format, JsNumber, JsResult, JsValue}

package object core {
  private[this] def protoFormat[A <: GeneratedEnum](c: GeneratedEnumCompanion[A]): Format[A] = {
    new Format[A] {
      override def writes(o: A): JsValue = JsNumber(o.value)

      override def reads(json: JsValue): JsResult[A] = json.validate[Int].map(c.fromValue)
    }
  }

  implicit val rangeFormat: Format[Range] = protoFormat(model.Range)

  implicit val targetFormat: Format[Target] = protoFormat(Target)

  implicit val skillTypeFormat: Format[SkillType] = protoFormat(SkillType)

  implicit val elementFormat: Format[Element] = protoFormat(Element)

  implicit val armorTypeFormat: Format[ArmorType] = protoFormat(ArmorType)

  implicit val weaponTypeFormat: Format[WeaponType] = protoFormat(WeaponType)

  implicit val attributeFormat: Format[Attribute] = protoFormat(Attribute)
}
