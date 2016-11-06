package models

trait ModelFormat[A] {
  def asModel: A
}
