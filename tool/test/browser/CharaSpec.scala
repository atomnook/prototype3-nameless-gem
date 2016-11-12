package browser

import org.scalatestplus.play.BrowserInfo

class CharaSpec extends BrowserSpec {
  def sharedTests(browser: BrowserInfo) = {
    "/characters" must {
      "create character " + browser.name in {
        val race = newRace
        val classes = Seq(newClass, newClass)

        service.races().create(race)
        classes.foreach(service.classes().create)

        val expected = newChara(race, classes)

        go to s"http://localhost:$port/characters"

        textField("id").value = expected.getId.id
        textField("name").value = expected.name
        singleSel("race").value = race.getId.id
        multiSel("classes").values = classes.map(_.getId.id)

        click on id("create")

        eventually {
          assert(service.characters().read() === Set(expected))
        }
      }
    }
  }
}
