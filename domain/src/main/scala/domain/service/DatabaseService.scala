package domain.service

import java.util.Base64

import com.trueaccord.lenses.Lens
import model.Database.DatabaseLens
import model.{Chara, Database, Race}

class DatabaseService {
  private[this] var database = Database()

  private[this] val databaseLens = new Database.DatabaseLens(Lens.unit[Database])

  trait CRUD[A] {
    protected[this] def get(): Seq[A]

    protected[this] def len(): Lens[Database, Seq[A]]

    protected[this] def identity(a: A, b: A): Boolean

    def read(): Set[A] = synchronized(get().toSet)

    def create(a: A): Boolean = synchronized {
      val res = get().exists(identity(a, _))
      if (!res) {
        database = len().modify(_ :+ a)(database)
      }
      !res
    }

    def update(a: A): Boolean = synchronized {
      get().find(identity(a, _)) match {
        case Some(found) =>
          database = len().set(get().filter(!identity(a, _)) :+ a)(database)
          true

        case None => false
      }
    }

    def delete(a: A): Boolean = synchronized {
      get().find(identity(a, _)) match {
        case Some(found) =>
          database = len().set(get().filter(!identity(a, _)))(database)
          true

        case None => false
      }
    }
  }

  private[this] object CRUD {
    def apply[A](g: Database => Seq[A],
                 l: DatabaseLens[Database] => Lens[Database, Seq[A]],
                 i: (A, A) => Boolean): CRUD[A] = {
      new CRUD[A] {
        override protected[this] def get(): Seq[A] = g(database)

        override protected[this] def len(): Lens[Database, Seq[A]] = l(databaseLens)

        override protected[this] def identity(a: A, b: A): Boolean = i(a, b)
      }
    }
  }

  def read(s: String): Unit = synchronized(database = Database.parseFrom(Base64.getDecoder.decode(s)))

  def write(): String = synchronized(Base64.getEncoder.encodeToString(Database.toByteArray(database)))

  def races(): CRUD[Race] = CRUD[Race](_.races, _.races, _.getId == _.getId)

  def classes(): CRUD[model.Class] = CRUD[model.Class](_.classes, _.classes, _.getId == _.getId)

  def characters(): CRUD[Chara] = CRUD[Chara](_.charas, _.charas, _.getId == _.getId)
}

object DatabaseService {
  def apply(): DatabaseService = new DatabaseService()
}
