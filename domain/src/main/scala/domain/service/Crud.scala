package domain.service

trait Crud[A] {
  def read(): List[A]
  def create(a: A): Boolean
  def update(a: A): Boolean
  def delete(a: A): Boolean
}
