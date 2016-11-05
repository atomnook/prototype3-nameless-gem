package domain.service

import domain.service.DatabaseService.CRUD
import model.{Chara, Race}
import org.scalatest.FlatSpec

class DatabaseServiceSpec extends FlatSpec {
  def testCRUD[A](crud: CRUD[A], first: A, updated: A, created: A) = {
    assert(crud.read() === Set())

    assert(crud.create(first))
    assert(crud.read() === Set(first))

    assert(crud.update(updated))
    assert(crud.read() === Set(updated))

    assert(crud.create(first) === false)
    assert(crud.read() === Set(updated))

    assert(crud.create(created))
    assert(crud.read() === Set(updated, created))

    assert(crud.delete(first))
    assert(crud.read() === Set(created))

    assert(crud.delete(first) === false)
    assert(crud.read() === Set(created))
  }

  "DatabaseService" should "have Race CRUD" in {
    val first = Race().update(_.id.id := "idA", _.name := "nameA")
    val updated = first.update(_.name.modify(_ + "-updated"))
    val created = Race().update(_.id.id := "idB", _.name := "nameB")

    testCRUD(crud = DatabaseService().races(), first = first, updated = updated, created = created)
  }

  "DatabaseService" should "have Class CRUD" in {
    val first = model.Class().update(_.id.id := "idA", _.name := "nameA")
    val updated = first.update(_.name.modify(_ + "-updated"))
    val created = model.Class().update(_.id.id := "idB", _.name := "nameB")

    testCRUD(crud = DatabaseService().classes(), first = first, updated = updated, created = created)
  }

  "DatabaseService" should "have Chara CRUD" in {
    val first = Chara().update(_.id.id := "idA", _.name := "nameA")
    val updated = first.update(_.name.modify(_ + "-updated"))
    val created = Chara().update(_.id.id := "idB", _.name := "nameB")

    testCRUD(crud = DatabaseService().characters(), first = first, updated = updated, created = created)
  }

  "DatabaseService" should "write/read base64 encoded bytes" in {
    val service = DatabaseService()
    val race = Race().update(_.id.id := "race-id")
    val cls = model.Class().update(_.id.id := "class-id")
    val chara = Chara().update(_.id.id := "chara-id")

    service.races().create(race)
    service.classes().create(cls)
    service.characters().create(chara)

    val database = service.write()
    val restored = DatabaseService()

    assert(restored.races().read() === Set())
    assert(restored.classes().read() === Set())
    assert(restored.characters().read() === Set())

    restored.read(database)

    assert(restored.races().read() === Set(race))
    assert(restored.classes().read() === Set(cls))
    assert(restored.characters().read() === Set(chara))
  }
}
