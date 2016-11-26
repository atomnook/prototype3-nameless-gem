package browser.helper

import model.item.{Accessory, Armor, ItemId, Weapon}
import model.skill._

trait ImplicitId {
  protected[this] implicit class ChainAttackSkillId(s: ChainAttackSkill) {
    def id(): SkillId = s.getSkill.getSkill.getId
  }

  protected[this] implicit class MultipleAttackSkillId(s: MultipleAttackSkill) {
    def id(): SkillId = s.getSkill.getSkill.getId
  }

  protected[this] implicit class MasterySkillId(s: MasterySkill) {
    def id(): SkillId = s.getSkill.getId
  }

  protected[this] implicit class AttributeBoostSkillId(s: AttributeBoostSkill) {
    def id(): SkillId = s.getSkill.getId
  }

  protected[this] implicit class WeaponId(i: Weapon) {
    def id(): ItemId = i.getItem.getId
  }

  protected[this] implicit class ArmorId(i: Armor) {
    def id(): ItemId = i.getItem.getId
  }

  protected[this] implicit class AccessoryId(i: Accessory) {
    def id(): ItemId = i.getItem.getId
  }
}
