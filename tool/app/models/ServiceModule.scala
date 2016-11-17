package models

import com.google.inject.AbstractModule
import domain.service.DatabaseService
import play.api.{Configuration, Environment, Logger}

import scala.io.Source

class ServiceModule(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    val service = DatabaseService()
    val enable = configuration.getBoolean("database.init.enable").getOrElse(false)

    if (enable) {
      val database = "database"

      environment.resourceAsStream(database) match {
        case Some(stream) =>
          val base64 = Source.fromInputStream(stream, "UTF-8").mkString
          val head = base64.take(100)
          val ellipsis = if (base64.length > 100) "..." else ""
          Logger.info(s"read($head$ellipsis) (${base64.length})")
          service.read(base64)

        case None =>
          Logger.info(s"$database not found")
      }
    }

    bind(classOf[DatabaseService]).toInstance(service)
  }
}
