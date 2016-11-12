package browser

import org.scalatestplus.play.BrowserInfo

class SkillSpec extends BrowserSpec {
  def sharedTests(browser: BrowserInfo) = {
    "/skills/multiple-attack" must {
      "create multiple attack skill " + browser.name in {
        val prerequisite = newMultipleAttack(Nil)

        service.multipleAttackSkills().create(prerequisite)

        val expected = newMultipleAttack(prerequisite.getSkill.getSkill :: Nil)
        val attackSkill = expected.getSkill
        val skill = attackSkill.getSkill

        go to s"http://localhost:$port/skills/multiple-attack"

        textField("id").value = skill.getId.id
        textField("name").value = skill.name
        multiSel("prerequisites").values = skill.prerequisites.map(_.id)

        multiSel("types").values = attackSkill.types.map(_.value.toString)
        multiSel("elements").values = attackSkill.elements.map(_.value.toString)
        textLevel("base-power", "incr-power", attackSkill.getPower)
        textLevel("base-tp", "incr-tp", attackSkill.getTpCost)

        textLevel("base-hit", "incr-hit", expected.getHit)
        singleSel("range").value = expected.range.value.toString
        singleSel("target").value = expected.target.value.toString

        click on id("create")

        eventually {
          assert(service.multipleAttackSkills().read() === Set(prerequisite, expected))
        }
      }
    }

    "/skills/chain-attack" must {
      "create chain attack skill " + browser.name in {
        val prerequisite = newChainAttack(Nil)

        service.chainAttackSkills().create(prerequisite)

        val expected = newChainAttack(prerequisite.getSkill.getSkill :: Nil)
        val attackSkill = expected.getSkill
        val skill = attackSkill.getSkill

        go to s"http://localhost:$port/skills/chain-attack"

        textField("id").value = skill.getId.id
        textField("name").value = skill.name
        multiSel("prerequisites").values = skill.prerequisites.map(_.id)

        multiSel("types").values = attackSkill.types.map(_.value.toString)
        multiSel("elements").values = attackSkill.elements.map(_.value.toString)
        textLevel("base-power", "incr-power", attackSkill.getPower)
        textLevel("base-tp", "incr-tp", attackSkill.getTpCost)

        multiSel("follow").values = expected.follow.map(_.value.toString)
        textLevel("base-chain", "incr-chain", expected.getChain)
        singleSel("range").value = expected.range.value.toString

        click on id("create")

        eventually {
          assert(service.chainAttackSkills().read() === Set(prerequisite, expected))
        }
      }
    }
  }
}
