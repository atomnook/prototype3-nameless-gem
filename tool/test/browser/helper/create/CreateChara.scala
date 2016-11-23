package browser.helper.create

import browser.arbitrary.{ArbitraryChara, ArbitraryClass, ArbitraryRace}
import browser.helper.BrowserSpec
import model.Chara

trait CreateChara extends BrowserSpec {
  protected[this] def create(): Chara = {
    val race = ArbitraryRace.arbitrary
    val classes = Seq(ArbitraryClass.arbitrary, ArbitraryClass.arbitrary)
    service.races().create(race)
    classes.foreach(service.classes().create)
    ArbitraryChara.arbitrary(race, classes)
  }
}
