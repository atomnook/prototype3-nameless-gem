package browser.arbitrary

import model.item.Accessory

object ArbitraryAccessory {
  private[this] val item = ArbitraryItem

  def arbitrary: Accessory = {
    Accessory().update(_.item := item.arbitrary)
  }
}
