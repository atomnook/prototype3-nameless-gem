package browser.arbitrary

import model.LevelAttributes

object ArbitraryLevelAttributes {
  private[this] val attributes = ArbitraryAttributes

  def arbitrary: LevelAttributes = {
    LevelAttributes().update(_.base := attributes.arbitrary, _.increasing := attributes.arbitrary)
  }
}
