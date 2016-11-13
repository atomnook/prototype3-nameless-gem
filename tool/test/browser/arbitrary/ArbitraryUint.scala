package browser.arbitrary

import scala.util.Random

object ArbitraryUint {
  def arbitrary: Int = Random.nextInt(Int.MaxValue)
}
