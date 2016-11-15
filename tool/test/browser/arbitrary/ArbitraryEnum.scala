package browser.arbitrary

import com.trueaccord.scalapb.{GeneratedEnum, GeneratedEnumCompanion}

import scala.util.Random

object ArbitraryEnum {
  def arbitrary[A <: GeneratedEnum](c: GeneratedEnumCompanion[A]): A = c.values(Random.nextInt(c.values.size))
}
