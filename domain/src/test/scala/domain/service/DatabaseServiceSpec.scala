package domain.service

import model.item.{Accessory, Armor, Weapon}
import model.skill.{ChainAttackSkill, MultipleAttackSkill}
import model.{Chara, Class, Race}
import org.scalatest.FlatSpec

class DatabaseServiceSpec extends FlatSpec {
  def testCrud[A](Crud: DatabaseService => Crud[A], first: A, updated: A, created: A) = {
    val service = DatabaseService()
    val sut = Crud(service)

    assert(sut.read() === List())

    assert(sut.create(first))
    assert(sut.read() === List(first))

    assert(sut.update(updated))
    assert(sut.read() === List(updated))

    assert(sut.create(first) === false)
    assert(sut.read() === List(updated))

    assert(sut.create(created))
    assert(sut.read() === List(updated, created))

    assert(sut.delete(first))
    assert(sut.read() === List(created))

    assert(sut.delete(first) === false)
    assert(sut.read() === List(created))
  }

  it should "have Race Crud" in {
    val first = Race().update(_.id.id := "idA", _.name := "nameA")
    val updated = first.update(_.name.modify(_ + "-updated"))
    val created = Race().update(_.id.id := "idB", _.name := "nameB")

    testCrud(Crud = _.races(), first = first, updated = updated, created = created)
  }

  it should "have Class Crud" in {
    val first = Class().update(_.id.id := "idA", _.name := "nameA")
    val updated = first.update(_.name.modify(_ + "-updated"))
    val created = Class().update(_.id.id := "idB", _.name := "nameB")

    testCrud(Crud = _.classes(), first = first, updated = updated, created = created)
  }

  it should "have Chara Crud" in {
    val first = Chara().update(_.id.id := "idA", _.name := "nameA")
    val updated = first.update(_.name.modify(_ + "-updated"))
    val created = Chara().update(_.id.id := "idB", _.name := "nameB")

    testCrud(Crud = _.characters(), first = first, updated = updated, created = created)
  }

  it should "have MultipleAttackSkill Crud" in {
    val first = MultipleAttackSkill().update(_.skill.skill.id.id := "idA", _.skill.skill.name := "nameA")
    val updated = first.update(_.skill.skill.name.modify(_ + "-updated"))
    val created = MultipleAttackSkill().update(_.skill.skill.id.id := "idB", _.skill.skill.name := "nameB")

    testCrud(Crud = _.multipleAttackSkills(), first = first, updated = updated, created = created)
  }

  it should "have ChainAttackSkill Crud" in {
    val first = ChainAttackSkill().update(_.skill.skill.id.id := "idA", _.skill.skill.name := "nameA")
    val updated = first.update(_.skill.skill.name.modify(_ + "-updated"))
    val created = ChainAttackSkill().update(_.skill.skill.id.id := "idB", _.skill.skill.name := "nameB")

    testCrud(Crud = _.chainAttackSkills(), first = first, updated = updated, created = created)
  }
  
  it should "have Weapon Crud" in {
    val first = Weapon().update(_.item.id.id := "idA", _.item.name := "nameA")
    val updated = first.update(_.item.name.modify(_ + "-updated"))
    val created = Weapon().update(_.item.id.id := "idB", _.item.name := "nameB")
    
    testCrud(Crud = _.weapons(), first = first, updated = updated, created = created)
  }
  
  it should "have Armor Crud" in {
    val first = Armor().update(_.item.id.id := "idA", _.item.name := "nameA")
    val updated = first.update(_.item.name.modify(_ + "-updated"))
    val created = Armor().update(_.item.id.id := "idB", _.item.name := "nameB")

    testCrud(Crud = _.armors(), first = first, updated = updated, created = created)
  }

  it should "have Accessory Crud" in {
    val first = Accessory().update(_.item.id.id := "idA", _.item.name := "nameA")
    val updated = first.update(_.item.name.modify(_ + "-updated"))
    val created = Accessory().update(_.item.id.id := "idB", _.item.name := "nameB")

    testCrud(Crud = _.accessories(), first = first, updated = updated, created = created)
  }

  it should "read/write base64 encoded bytes" in {
    val service = DatabaseService()
    val race = Race().update(_.id.id := "race-id")
    val cls = model.Class().update(_.id.id := "class-id")
    val chara = Chara().update(_.id.id := "chara-id")

    service.races().create(race)
    service.classes().create(cls)
    service.characters().create(chara)

    val database = service.write()
    val restored = DatabaseService()

    assert(restored.races().read() === List())
    assert(restored.classes().read() === List())
    assert(restored.characters().read() === List())

    restored.read(database)

    assert(restored.races().read() === List(race))
    assert(restored.classes().read() === List(cls))
    assert(restored.characters().read() === List(chara))
  }
}
