package browser.arbitrary

import model.item.{Weapon, WeaponType}

object ArbitraryWeapon {
  private[this] val item = ArbitraryItem

  private[this] val uint = ArbitraryUint

  private[this] val enum = ArbitraryEnum

  def arbitrary: Weapon = {
    Weapon().update(
      _.item := item.arbitrary,
      _.atk := uint.arbitrary,
      _.mat := uint.arbitrary,
      _.`type` := enum.arbitrary(WeaponType))
  }
}
