package browser.arbitrary

import model.skill.AttackLevel

object ArbitraryAttackLevel {
  private[this] val uint = ArbitraryUint

  def arbitrary: AttackLevel = AttackLevel().update(_.base := uint.arbitrary, _.increasingPct := uint.arbitrary)
}
