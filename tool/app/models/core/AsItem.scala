package models.core

import model.item.Item

trait AsItem {
  val id: Identifier
  val name: Name
  val price: Long

  def asItem: Item = Item().update(_.id.id := id.id, _.name := name.name, _.price := price)
}
