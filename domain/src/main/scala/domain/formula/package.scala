package domain

import model.Attributes

package object formula {
  implicit class AttributeOps(a: Attributes) {
    private[this] val unused = Attributes()

    private[this] def modifyAll(b: Attributes, f: (Long, Long) => Long): Attributes = {
      a.update(
        _.hp.modify(f(_, b.hp)),
        _.tp.modify(f(_, b.tp)),
        _.str.modify(f(_, b.str)),
        _.vit.modify(f(_, b.vit)),
        _.int.modify(f(_, b.int)),
        _.wis.modify(f(_, b.wis)),
        _.agi.modify(f(_, b.agi)),
        _.luc.modify(f(_, b.luc)))
    }

    def add(b: Attributes): Attributes = modifyAll(b, (x, y) => x + y)

    def multiply(b: Long): Attributes = modifyAll(unused, (x, _) => x * b)
  }
}
