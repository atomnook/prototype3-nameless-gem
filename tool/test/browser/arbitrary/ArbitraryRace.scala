package browser.arbitrary

import model.Race

object ArbitraryRace {
  private[this] val id = ArbitraryId

  private[this] val name = ArbitraryName

  private[this] val levelAttributes = ArbitraryLevelAttributes

  def arbitrary: Race = {
    Race().update(_.id.id := id.arbitrary, _.name := name.arbitrary, _.attributes := levelAttributes.arbitrary)
  }
}
