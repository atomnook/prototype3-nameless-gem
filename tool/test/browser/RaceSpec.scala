package browser

import org.scalatestplus.play._

class RaceSpec extends BrowserSpec {
  def sharedTests(browser: BrowserInfo) = {
    "/races" must {
      "create " + browser.name in {
        val prerequisite = newMultipleAttack(Nil)

        service.multipleAttackSkills().create(prerequisite)

        val expected = newRace.update(_.skillTree := prerequisite.getSkill.getSkill.getId :: Nil)

        go to s"http://localhost:$port/races"

        textField("id").value = expected.getId.id
        textField("name").value = expected.name
        multiSel("skill-tree").values = expected.skillTree.map(_.id)

        click on id("create")

        eventually {
          assert(service.races().read() === Set(expected))
        }
      }
    }
  }
}
