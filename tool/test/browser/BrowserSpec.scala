package browser

import java.util.UUID

import domain.service.DatabaseService
import model.skill._
import model._
import org.scalatestplus.play._

import scala.util.Random

trait BrowserSpec extends PlaySpec with OneServerPerSuite with AllBrowsersPerSuite {
  override lazy val browsers = Vector(ChromeInfo)

  protected[this] def service: DatabaseService = app.injector.instanceOf(classOf[DatabaseService])

  private[this] def arbitrary: String = UUID.randomUUID().toString

  private[this] def arbitrary[A](a: Seq[A]): A = a(Random.nextInt(a.size))

  protected[this] def newRace: Race = Race().update(_.id.id := arbitrary, _.name := arbitrary)

  protected[this] def newClass: model.Class = model.Class().update(_.id.id := arbitrary, _.name := arbitrary)

  protected[this] def newChara(race: Race, classes: Seq[model.Class]): Chara = {
    Chara().update(
      _.id.id := arbitrary,
      _.name := arbitrary,
      _.race := race.getId,
      _.classes := classes.map(c => ClassLevel().update(_.id := c.getId)))
  }

  protected[this] def newSkill(prerequisites: Seq[Skill]): Skill = {
    Skill().update(_.id.id := arbitrary, _.name := arbitrary, _.prerequisites := prerequisites.map(_.getId))
  }

  protected[this] def newAttackLevel: AttackLevel = {
    AttackLevel().update(_.base := Random.nextInt(Int.MaxValue), _.increasingPct := Random.nextInt(Int.MaxValue))
  }

  protected[this] def newAttackSkill(prerequisites: Seq[Skill]): AttackSkill = {
    AttackSkill().update(
      _.skill := newSkill(prerequisites),
      _.types := arbitrary(SkillType.values) :: Nil,
      _.elements := arbitrary(Element.values) :: Nil,
      _.power := newAttackLevel,
      _.tpCost := newAttackLevel)
  }

  protected[this] def newMultipleAttack(prerequisites: Seq[Skill]): MultipleAttackSkill = {
    MultipleAttackSkill().update(
      _.skill := newAttackSkill(prerequisites),
      _.hit := newAttackLevel,
      _.range := arbitrary(model.Range.values),
      _.target := arbitrary(Target.values))
  }

  protected[this] def newChainAttack(prerequisites: Seq[Skill]): ChainAttackSkill = {
    ChainAttackSkill().update(
      _.skill := newAttackSkill(prerequisites),
      _.follow := arbitrary(Element.values) :: Nil,
      _.chain := newAttackLevel,
      _.range := arbitrary(model.Range.values))
  }

  protected[this] def textLevel(base: String, increasing: String, level: AttackLevel) = {
    numberField(base).value = level.base.toString
    numberField(increasing).value = level.increasingPct.toString
  }
}
