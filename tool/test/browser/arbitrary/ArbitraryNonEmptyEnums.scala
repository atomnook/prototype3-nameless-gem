package browser.arbitrary

import com.trueaccord.scalapb.{GeneratedEnum, GeneratedEnumCompanion}

import scala.util.Random

object ArbitraryNonEmptyEnums {
  private[this] val enum = ArbitraryEnum

  def arbitrary[A <: GeneratedEnum](c: GeneratedEnumCompanion[A]): Seq[A] = {
    Seq.fill(1 + Random.nextInt(c.values.size))(enum.arbitrary(c)).toSet[A].toList.sortBy(_.value)
  }
}
