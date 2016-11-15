package browser.arbitrary

import model.{Chara, ClassLevel, Race}

object ArbitraryChara {
  private[this] val id = ArbitraryId

  private[this] val name = ArbitraryName

  def arbitrary(race: Race, classes: Seq[model.Class]): Chara = {
    Chara().update(
      _.id.id := id.arbitrary,
      _.name := name.arbitrary,
      _.race := race.getId,
      _.classes := classes.map(c => ClassLevel().update(_.id := c.getId)))
  }
}
