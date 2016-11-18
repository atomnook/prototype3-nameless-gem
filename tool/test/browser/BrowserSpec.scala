package browser

import browser.arbitrary._
import domain.service.DatabaseService
import model.{Attributes, LevelAttributes}
import model.skill._
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

trait BrowserSpec extends PlaySpec with OneServerPerSuite with AllBrowsersPerSuite with BeforeAndAfterEach {
  override lazy val browsers = Vector(ChromeInfo)

  implicit override lazy val app: Application = {
    new GuiceApplicationBuilder().configure("database.init.enable " -> false).build()
  }

  protected[this] implicit class ChainAttackSkillId(s: ChainAttackSkill) {
    def id(): SkillId = s.getSkill.getSkill.getId
  }

  protected[this] implicit class MultipleAttackSkillId(s: MultipleAttackSkill) {
    def id(): SkillId = s.getSkill.getSkill.getId
  }

  protected[this] def service: DatabaseService = app.injector.instanceOf(classOf[DatabaseService])

  protected[this] def textLevel(base: String, increasing: String, level: AttackLevel): Unit = {
    numberField(base).value = level.base.toString
    numberField(increasing).value = level.increasingPct.toString
  }

  private[this] def fill(prefix: String, a: Attributes): Unit = {
    def set(k: String, v: Long): Unit = numberField(prefix + k).value = v.toString

    set("hp", a.hp)
    set("tp", a.tp)
    set("str", a.str)
    set("vit", a.vit)
    set("int", a.int)
    set("wis", a.wis)
    set("agi", a.agi)
    set("luc", a.luc)
  }

  protected[this] def fill(a: LevelAttributes): Unit = {
    fill("base-", a.getBase)
    fill("increasing-", a.getIncreasing)
  }

  protected[this] val arbMultipleAttackSkill = ArbitraryMultipleAttackSkill

  protected[this] val arbChainAttackSkill = ArbitraryChainAttackSkill

  protected[this] val arbRace = ArbitraryRace

  protected[this] val arbClass = ArbitraryClass

  protected[this] val arbChara = ArbitraryChara

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    service.read("")
  }
}
