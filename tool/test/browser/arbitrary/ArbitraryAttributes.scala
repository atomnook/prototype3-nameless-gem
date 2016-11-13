package browser.arbitrary

import model.Attributes

object ArbitraryAttributes {
  private[this] val uint = ArbitraryUint

  def arbitrary: Attributes = {
    Attributes().update(
      _.hp := uint.arbitrary,
      _.tp := uint.arbitrary,
      _.str := uint.arbitrary,
      _.vit := uint.arbitrary,
      _.int := uint.arbitrary,
      _.wis := uint.arbitrary,
      _.agi := uint.arbitrary,
      _.luc := uint.arbitrary)
  }
}
