package browser.helper.field

import model.{Attributes, LevelAttributes}
import org.scalatestplus.play.{AllBrowsersPerSuite, OneServerPerSuite, PlaySpec}

trait LevelAttributesField extends PlaySpec with OneServerPerSuite with AllBrowsersPerSuite {
  protected[this] case class Field(id: String) {
    private[this] def set(prefix: String, label: String, postfix: String, value: Long): Unit = {
      numberField(s"$prefix-$label-$postfix").value = value.toString
    }

    private[this] def set(prefix: String, value: Attributes): Unit = {
      set(prefix, "hp", id, value.hp)
      set(prefix, "tp", id, value.tp)
      set(prefix, "str", id, value.str)
      set(prefix, "vit", id, value.vit)
      set(prefix, "int", id, value.int)
      set(prefix, "wis", id, value.wis)
      set(prefix, "agi", id, value.agi)
      set(prefix, "luc", id, value.luc)
    }

    def :=(value: LevelAttributes): Unit = {
      set("base", value.getBase)
      set("increasing", value.getIncreasing)
    }
  }

  protected[this] def levelAttributesField(id: String): Field = Field(id)
}
