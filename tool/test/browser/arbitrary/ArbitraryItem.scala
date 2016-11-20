package browser.arbitrary

import model.item.Item

object ArbitraryItem {
  private[this] val id = ArbitraryId

  private[this] val name = ArbitraryName

  private[this] val uint = ArbitraryUint

  def arbitrary: Item = {
    Item().update(_.id.id := id.arbitrary, _.name := name.arbitrary, _.price := uint.arbitrary)
  }
}
