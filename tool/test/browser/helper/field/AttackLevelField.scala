package browser.helper.field

import model.skill.AttackLevel
import org.scalatestplus.play.{AllBrowsersPerSuite, OneServerPerSuite, PlaySpec}

trait AttackLevelField extends PlaySpec with OneServerPerSuite with AllBrowsersPerSuite {
  protected[this] case class Field(base: String, increasing: String) {
    def :=(value: AttackLevel): Unit = {
      numberField(base).value = value.base.toString
      numberField(increasing).value = value.increasingPct.toString
    }
  }

  protected[this] def attackLevelField(base: String, increasing: String): Field = {
    Field(base = base, increasing = increasing)
  }
}
