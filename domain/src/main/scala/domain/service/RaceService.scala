package domain.service

import domain.service.RaceService.RaceNotFoundException
import model.{Race, RaceId}

import scala.concurrent.{ExecutionContext, Future}

class RaceService(service: DatabaseService)(implicit context: ExecutionContext) {
  /**
    * @return [[RaceNotFoundException]]
    */
  def get(id: RaceId): Future[Race] = {
    service.races().read().find(_.getId == id).
      map(Future.successful).getOrElse(Future.failed(RaceNotFoundException(id)))
  }
}

object RaceService {
  case class RaceNotFoundException(id: RaceId) extends RuntimeException(s"$id not found")
}
