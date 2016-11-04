package domain.formula

case class TilNextLevel(nextLevel: Long) {
  def xp: Long = 250 + 250 * nextLevel
}
