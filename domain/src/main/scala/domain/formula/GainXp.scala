package domain.formula

import model.{Chara, CharaLevel}

object GainXp {
  implicit class GainXpLevel(level: CharaLevel) {
    def gainXp(xp: Long): CharaLevel = {
      def rec(xp: Long, level: CharaLevel): CharaLevel = {
        val tnl = TilNextLevel(level.level + 1).xp

        if (xp < tnl) {
          level.update(_.xp.modify(_ + xp))
        } else {
          rec(xp - tnl, level.update(_.level.modify(_ + 1), _.xp := 0, _.cp.modify(_ + 1)))
        }
      }

      rec(xp, level)
    }
  }

  implicit class GainXpChara(chara: Chara) {
    def gainXp(xp: Long): Chara = {
      val distributed = xp / (1 + chara.classes.size)
      chara.update(
        _.raceLevel.modify(_.gainXp(distributed)),
        _.classes.modify(_.map(_.update(_.level.modify(_.gainXp(distributed))))))
    }
  }
}
