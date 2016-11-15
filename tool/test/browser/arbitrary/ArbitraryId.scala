package browser.arbitrary

import java.util.UUID

object ArbitraryId {
  def arbitrary: String = "i-" + UUID.randomUUID().toString
}
