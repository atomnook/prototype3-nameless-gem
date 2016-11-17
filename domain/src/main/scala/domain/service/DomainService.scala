package domain.service

import domain.formula._
import domain.service.DomainService.{ClassNotFoundException, RaceNotFoundException}
import model._

import scala.concurrent.{ExecutionContext, Future}

class DomainService(service: DatabaseService)(implicit context: ExecutionContext) {
  /**
    * @return [[RaceNotFoundException]]
    */
  def get(id: RaceId): Future[Race] = {
    service.races().read().find(_.getId == id).map(Future.successful).getOrElse(Future.failed(RaceNotFoundException(id)))
  }

  /**
    * @return [[ClassNotFoundException]]
    */
  def get(id: ClassId): Future[Class] = {
    service.classes().read().find(_.getId == id).map(Future.successful).getOrElse(Future.failed(ClassNotFoundException(id)))
  }

  /**
    * @return [[RaceNotFoundException]]
    *         [[ClassNotFoundException]]
    */
  def attributes(c: Chara): Future[Attributes] = {
    for {
      race <- get(c.getRace)
      classes <- Future.sequence(c.classes.map(cls => get(cls.getId).map(c => AttributesAtLevel(c.getAttributes, cls.getLevel))))
    } yield {
      classes.foldLeft(AttributesAtLevel(race.getAttributes, c.getRaceLevel).attributes) { case (attributes, atLevel) =>
          attributes.add(atLevel.attributes)
      }
    }
  }
}

object DomainService {
  case class RaceNotFoundException(id: RaceId) extends RuntimeException(s"$id not found")

  case class ClassNotFoundException(id: ClassId) extends RuntimeException(s"$id not found")
}
