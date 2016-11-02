package controllers

import java.io.{ByteArrayInputStream, File}
import javax.inject.Inject

import akka.stream.scaladsl.StreamConverters
import com.google.inject.AbstractModule
import domain.service.DatabaseService
import play.api.{Configuration, Environment, Logger}
import play.api.mvc.{Action, Controller}

import scala.io.Source

class DatabaseController @Inject() (service: DatabaseService) extends Controller {
  val raw = Action(Ok(service.write()))

  val file = Action {
    val CHUNK_SIZE = 100
    val data = () => new ByteArrayInputStream(service.write().getBytes("UTF-8"))
    val source = StreamConverters.fromInputStream(data, CHUNK_SIZE)
    Ok.chunked(source).withHeaders("Content-Disposition" -> "attachment; filename=database.txt")
  }
}
