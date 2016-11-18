package browser.arbitrary

import model.item.{Armor, ArmorType}

object ArbitraryArmor {
  private[this] val item = ArbitraryItem

  private[this] val uint = ArbitraryUint

  private[this] val enum = ArbitraryEnum

  def arbitrary: Armor = {
    Armor().update(
      _.item := item.arbitrary,
      _.`def` := uint.arbitrary,
      _.mdf := uint.arbitrary,
      _.`type` := enum.arbitrary(ArmorType))
  }
}

