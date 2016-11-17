package domain.formula

import model.{Attributes, CharaLevel, LevelAttributes}

case class AttributesAtLevel(levelAttributes: LevelAttributes, level: CharaLevel) {
  def attributes: Attributes = {
    val base = levelAttributes.getBase
    val increasing = levelAttributes.getIncreasing.multiply(level.level)
    base.add(increasing)
  }
}
