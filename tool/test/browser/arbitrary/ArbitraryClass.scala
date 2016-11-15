package browser.arbitrary

object ArbitraryClass {
  private[this] val id = ArbitraryId

  private[this] val name = ArbitraryName

  private[this] val levelAttributes = ArbitraryLevelAttributes

  def arbitrary: model.Class = {
    model.Class().update(_.id.id := id.arbitrary, _.name := name.arbitrary, _.attributes := levelAttributes.arbitrary)
  }
}
