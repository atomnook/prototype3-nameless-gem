package controllers

import java.io.ByteArrayInputStream
import javax.inject.Inject

import akka.stream.scaladsl.StreamConverters
import domain.service.DatabaseService
import play.api.mvc.{Action, Controller}

class DatabaseController @Inject() (service: DatabaseService) extends Controller {
  val raw = Action(Ok(service.write()))

  val file = Action {
    val CHUNK_SIZE = 100
    val data = () => new ByteArrayInputStream(service.write().getBytes("UTF-8"))
    val source = StreamConverters.fromInputStream(data, CHUNK_SIZE)
    Ok.chunked(source).withHeaders("Content-Disposition" -> "attachment; filename=database.txt")
  }
}
