package browser

import org.scalatestplus.play.BrowserInfo

class ClassSpec extends BrowserSpec {
  def sharedTests(browser: BrowserInfo) = {
    "/classes" must {
      "create " + browser.name in {
        val prerequisite = newMultipleAttack(Nil)

        service.multipleAttackSkills().create(prerequisite)

        val expected = newClass.update(_.skillTree := prerequisite.getSkill.getSkill.getId :: Nil)

        go to s"http://localhost:$port/classes"

        textField("id").value = expected.getId.id
        textField("name").value = expected.name
        multiSel("skill-tree").values = expected.skillTree.map(_.id)

        click on id("create")

        eventually {
          assert(service.classes().read() === Set(expected))
        }
      }
    }
  }
}
