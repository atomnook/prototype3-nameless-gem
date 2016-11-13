package browser.arbitrary

import java.util.UUID

object ArbitraryName {
  def arbitrary: String = "n-" + UUID.randomUUID().toString
}
