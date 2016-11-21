package domain.service

import domain.service.ClassService.ClassNotFoundException
import model.{Class, ClassId}

import scala.concurrent.{ExecutionContext, Future}

class ClassService(service: DatabaseService)(implicit context: ExecutionContext) {
  /**
    * @return [[ClassNotFoundException]]
    */
  def get(id: ClassId): Future[Class] = {
    service.classes().read().find(_.getId == id).map(Future.successful).getOrElse(Future.failed(ClassNotFoundException(id)))
  }
}

object ClassService {
  case class ClassNotFoundException(id: ClassId) extends RuntimeException(s"$id not found")
}
