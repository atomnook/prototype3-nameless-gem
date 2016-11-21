package browser.crud

import controllers.ReverseCharaController
import domain.service.Crud
import model.Chara
import org.scalatestplus.play.BrowserInfo
import play.api.mvc.Call

class CharaCrudSpec extends CrudSpec[Chara, ReverseCharaController] {
  override protected[this] def list: Call = reverseController.list()

  override protected[this] def get(id: String): Call = reverseController.get(id)

  override protected[this] def fill(a: Chara, hasIdField: Boolean): Unit = {
    if (hasIdField) {
      textField("id").value = a.getId.id
    }

    textField("name").value = a.name
    singleSel("race").value = a.getRace.id
    multiSel("classes").values = a.classes.map(_.getId.id)
  }

  override protected[this] def id(a: Chara): String = a.getId.id

  override protected[this] def create(): Chara = {
    val race = arbRace.arbitrary
    val classes = Seq(arbClass.arbitrary, arbClass.arbitrary)
    service.races().create(race)
    classes.foreach(service.classes().create)
    arbChara.arbitrary(race, classes)
  }

  override protected[this] def crud: Crud[Chara] = service.characters()

  override protected[this] def update(id: Chara, data: Chara): Chara = data.update(_.id := id.getId)

  override protected[this] def reverseController: ReverseCharaController = controllers.routes.CharaController

  override def sharedTests(browser: BrowserInfo): Unit = {
    super.sharedTests(browser)

    get(":id").url must {
      "gain xp " + browser.name in {
        val xp = 100
        val last = create()
        val expected = last.update(
          _.raceLevel.xp.modify(_ + xp),
          _.classes.modify(_.map(_.update(_.level.xp.modify(_ + xp)))))
        crud.create(last)

        goTo(_.get(last.getId.id))

        numberField("xp").value = ((1 + last.classes.length) * xp).toString

        click on id("gain-xp")

        eventually {
          assert(crud.read() === List(expected))
        }
      }
    }
  }
}
