package domain.service

import java.util.Base64
import java.util.concurrent.atomic.AtomicReference

import model.item._
import model.skill.{ChainAttackSkill, MultipleAttackSkill, Skill}
import model.{Chara, Class, Database, Race}

class DatabaseService {
  private[this] val database = new AtomicReference[Database](Database())

  private[this] def skills(d: Database): List[Skill] = {
    val res = d.multipleAttackSkills.map(_.getSkill.getSkill) ++ d.chainAttackSkills.map(_.getSkill.getSkill)
    res.toList
  }

  private[this] def items(d: Database): List[Item] = {
    val res = d.weapons.map(_.getItem) ++ d.armors.map(_.getItem) ++ d.accessories.map(_.getItem)
    res.toList
  }

  def read(s: String): Unit = database.set(Database.parseFrom(Base64.getDecoder.decode(s)))

  def write(): String = Base64.getEncoder.encodeToString(Database.toByteArray(database.get()))

  def races(): Crud[Race] = {
    DatabaseCrud.apply(database = database, all = _.races, lens = _.races, equal = _.getId == _.getId)
  }

  def classes(): Crud[Class] = {
    DatabaseCrud.apply(database = database, all = _.classes, lens = _.classes, equal = _.getId == _.getId)
  }

  def characters(): Crud[Chara] = {
    DatabaseCrud.apply(database = database, all = _.charas, lens = _.charas, equal = _.getId == _.getId)
  }

  def skills(): List[Skill] = skills(database.get())

  def multipleAttackSkills(): Crud[MultipleAttackSkill] = {
    DatabaseCrud.apply[MultipleAttackSkill, Skill](
      database = database,
      all = _.multipleAttackSkills,
      lens = _.multipleAttackSkills,
      ids = skills,
      equal = _.getSkill.getSkill.getId == _.getSkill.getSkill.getId,
      equal2id = _.getSkill.getSkill.getId == _.getId)
  }

  def chainAttackSkills(): Crud[ChainAttackSkill] = {
    DatabaseCrud.apply[ChainAttackSkill, Skill](
      database = database,
      all = _.chainAttackSkills,
      lens = _.chainAttackSkills,
      ids = skills,
      equal = _.getSkill.getSkill.getId == _.getSkill.getSkill.getId,
      equal2id = _.getSkill.getSkill.getId == _.getId)
  }

  def items(): List[Item] = items(database.get())

  def weapons(): Crud[Weapon] = {
    DatabaseCrud.apply[Weapon, Item](
      database = database,
      all = _.weapons,
      lens = _.weapons,
      ids = items,
      equal = _.getItem.getId == _.getItem.getId,
      equal2id = _.getItem.getId == _.getId)
  }

  def armors(): Crud[Armor] = {
    DatabaseCrud.apply[Armor, Item](
      database = database,
      all = _.armors,
      lens = _.armors,
      ids = items,
      equal = _.getItem.getId == _.getItem.getId,
      equal2id = _.getItem.getId == _.getId)
  }

  def accessories(): Crud[Accessory] = {
    DatabaseCrud.apply[Accessory, Item](
      database = database,
      all = _.accessories,
      lens = _.accessories,
      ids = items,
      equal = _.getItem.getId == _.getItem.getId,
      equal2id = _.getItem.getId == _.getId)
  }
}

object DatabaseService {
  def apply(): DatabaseService = new DatabaseService()
}
