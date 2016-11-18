package domain.service

import java.util.concurrent.atomic.AtomicReference

import com.trueaccord.lenses.Lens
import model.Database
import model.Database.DatabaseLens

private[service] abstract class DatabaseCrud[A, B](database: AtomicReference[Database]) extends Crud[A] {
  protected[this] def all_(database: Database): Seq[A]

  protected[this] def lens_(): Lens[Database, Seq[A]]

  protected[this] def ids_(database: Database): Seq[B]

  protected[this] def equal_(a: A, b: A): Boolean

  protected[this] def equal2id_(a: A, b: B): Boolean

  override def read(): List[A] = all_(database.get()).toList

  override def create(a: A): Boolean = {
    val db = database.get()
    val res = ids_(db).exists(equal2id_(a, _))
    if (!res) {
      database.set(lens_().modify(_ :+ a)(db))
    }
    !res
  }

  override def update(a: A): Boolean = {
    val db = database.get()
    val res = all_(db).exists(equal_(a, _))
    if (res) {
      database.set(lens_().set(all_(db).filter(!equal_(a, _)) :+ a)(db))
    }
    res
  }

  override def delete(a: A): Boolean = {
    val db = database.get()
    val res = all_(db).exists(equal_(a, _))
    if (res) {
      database.set(lens_().set(all_(db).filter(!equal_(a, _)))(db))
    }
    res
  }
}

private[service] object DatabaseCrud {
  private[this] val databaseLens = Database.DatabaseLens(Lens.unit[Database])

  def apply[A](database: AtomicReference[Database],
               all: Database => Seq[A],
               lens: DatabaseLens[Database] => Lens[Database, Seq[A]],
               equal: (A, A) => Boolean): DatabaseCrud[A, A] = {
    new DatabaseCrud[A, A](database) {
      override protected[this] def all_(database: Database): Seq[A] = all(database)

      override protected[this] def lens_(): Lens[Database, Seq[A]] = lens(databaseLens)

      override protected[this] def ids_(database: Database): Seq[A] = all_(database)

      override protected[this] def equal_(a: A, b: A): Boolean = equal(a, b)

      override protected[this] def equal2id_(a: A, b: A): Boolean = equal(a, b)
    }
  }

  def apply[A, B](database: AtomicReference[Database],
                  all: Database => Seq[A],
                  lens: DatabaseLens[Database] => Lens[Database, Seq[A]],
                  ids: Database => Seq[B],
                  equal: (A, A) => Boolean,
                  equal2id: (A, B) => Boolean): DatabaseCrud[A, B] = {
    new DatabaseCrud[A, B](database) {
      override protected[this] def all_(database: Database): Seq[A] = all(database)

      override protected[this] def lens_(): Lens[Database, Seq[A]] = lens(databaseLens)

      override protected[this] def ids_(database: Database): Seq[B] = ids(database)

      override protected[this] def equal_(a: A, b: A): Boolean = equal(a, b)

      override protected[this] def equal2id_(a: A, b: B): Boolean = equal2id(a, b)
    }
  }
}
