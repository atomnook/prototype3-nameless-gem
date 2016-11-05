package domain.service

import java.util.Base64

import com.trueaccord.lenses.Lens
import domain.service.DatabaseService.CRUD
import model.Database.DatabaseLens
import model.skill.{ChainAttackSkill, MultipleAttackSkill, Skill}
import model.{Chara, Database, Race}

class DatabaseService {
  private[this] var database = Database()

  private[this] val databaseLens = new Database.DatabaseLens(Lens.unit[Database])

  private[this] trait DatabaseCRUD[A, B] extends CRUD[A] {
    protected[this] def get(): Seq[A]

    protected[this] def len(): Lens[Database, Seq[A]]

    protected[this] def identity(a: A, b: A): Boolean

    protected[this] def identities(): Seq[B]

    protected[this] def altIdentity(a: A, b: B): Boolean

    def read(): Set[A] = synchronized(get().toSet)

    def create(a: A): Boolean = synchronized {
      val res = identities().exists(altIdentity(a, _))
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

  private[this] object DatabaseCRUD {
    def apply[A](g: Database => Seq[A],
                 l: DatabaseLens[Database] => Lens[Database, Seq[A]],
                 i: (A, A) => Boolean): DatabaseCRUD[A, A] = {
      new DatabaseCRUD[A, A] {
        override protected[this] def get(): Seq[A] = g(database)

        override protected[this] def len(): Lens[Database, Seq[A]] = l(databaseLens)

        override protected[this] def identity(a: A, b: A): Boolean = i(a, b)

        override protected[this] def identities(): Seq[A] = get()

        override protected[this] def altIdentity(a: A, b: A): Boolean = identity(a, b)
      }
    }

    def apply[A, B](g: Database => Seq[A],
                    l: DatabaseLens[Database] => Lens[Database, Seq[A]],
                    i: (A, A) => Boolean,
                    is: Database => Seq[B],
                    ai: (A, B) => Boolean): DatabaseCRUD[A, B] = {
      new DatabaseCRUD[A, B] {
        override protected[this] def get(): Seq[A] = g(database)

        override protected[this] def len(): Lens[Database, Seq[A]] = l(databaseLens)

        override protected[this] def identity(a: A, b: A): Boolean = i(a, b)

        override protected[this] def identities(): Seq[B] = is(database)

        override protected[this] def altIdentity(a: A, b: B): Boolean = ai(a, b)
      }
    }
  }

  private[this] def skillIdentities(d: Database): Seq[Skill] = {
    d.multipleAttackSkills.map(_.getSkill.getSkill) ++ d.chainAttackSkills.map(_.getSkill.getSkill)
  }

  def read(s: String): Unit = synchronized(database = Database.parseFrom(Base64.getDecoder.decode(s)))

  def write(): String = synchronized(Base64.getEncoder.encodeToString(Database.toByteArray(database)))

  def races(): CRUD[Race] = DatabaseCRUD.apply[Race](_.races, _.races, _.getId == _.getId)

  def classes(): CRUD[model.Class] = DatabaseCRUD.apply[model.Class](_.classes, _.classes, _.getId == _.getId)

  def characters(): CRUD[Chara] = DatabaseCRUD.apply[Chara](_.charas, _.charas, _.getId == _.getId)

  def multipleAttackSkills(): CRUD[MultipleAttackSkill] = {
    DatabaseCRUD.apply[MultipleAttackSkill, Skill](
      _.multipleAttackSkills,
      _.multipleAttackSkills,
      _.getSkill.getSkill.getId == _.getSkill.getSkill.getId,
      skillIdentities,
      _.getSkill.getSkill.getId == _.getId)
  }

  def chainAttackSkills(): CRUD[ChainAttackSkill] = {
    DatabaseCRUD.apply[ChainAttackSkill, Skill](
      _.chainAttackSkills,
      _.chainAttackSkills,
      _.getSkill.getSkill.getId == _.getSkill.getSkill.getId,
      skillIdentities,
      _.getSkill.getSkill.getId == _.getId)
  }
}

object DatabaseService {
  def apply(): DatabaseService = new DatabaseService()

  trait CRUD[A] {
    def read(): Set[A]
    def create(a: A): Boolean
    def update(a: A): Boolean
    def delete(a: A): Boolean
  }
}
