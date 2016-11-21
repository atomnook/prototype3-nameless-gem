package domain.service

import domain.formula._
import model._

import scala.concurrent.{ExecutionContext, Future}

class CharaService(service: DatabaseService)(implicit context: ExecutionContext) {
  private[this] val raceService = new RaceService(service)

  private[this] val classService = new ClassService(service)

  /**
    * @return [[domain.service.RaceService.RaceNotFoundException]]
    *         [[domain.service.ClassService.ClassNotFoundException]]
    */
  def attributes(c: Chara): Future[Attributes] = {
    for {
      race <- raceService.get(c.getRace)
      classes <- Future.sequence(c.classes.map(cls => classService.get(cls.getId).map((_, cls.getLevel))))
    } yield {
      classes.foldLeft(AttributesAtLevel(race.getAttributes, c.getRaceLevel).attributes) {
        case (attributes, (cls, level)) =>
          attributes.add(AttributesAtLevel(cls.getAttributes, level).attributes)
      }
    }
  }
}
