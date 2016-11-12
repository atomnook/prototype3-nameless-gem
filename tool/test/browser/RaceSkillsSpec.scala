package browser

import org.scalatestplus.play.BrowserInfo

class RaceSkillsSpec extends BrowserSpec {
  def sharedTests(browser: BrowserInfo) = {
    "/races/:id/skills.json" must {
      "update skill tree " + browser.name in {
        val prerequisite = newMultipleAttack(Nil)
        val last = newRace

        service.multipleAttackSkills().create(prerequisite)
        service.races().create(last)

        val expected = last.update(_.skillTree := prerequisite.getSkill.getSkill.getId :: Nil)

        go to s"http://localhost:$port/races/${expected.getId.id}"

        multiSel("skill-tree").values = expected.skillTree.map(_.id)

        click on id("update-skills")

        eventually {
          assert(service.races().read() === Set(expected))
        }
      }
    }
  }
}
