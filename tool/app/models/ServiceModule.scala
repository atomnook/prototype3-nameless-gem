package models

import java.io.File

import com.google.inject.AbstractModule
import domain.service.DatabaseService
import play.api.{Configuration, Environment, Logger}

import scala.io.Source

class ServiceModule(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    val txt = configuration.getConfig("app.database").getOrElse(Configuration.empty)
    val service = DatabaseService()
    val file = new File(txt.getString("txt").getOrElse("database.txt"))

    if (file.isFile) {
      Logger.info(s"${file.getAbsolutePath} found")

      val base64 = Source.fromFile(file).mkString

      Logger.debug(base64)

      service.read(base64)
    } else {
      Logger.info(s"${file.getAbsolutePath} not found")
    }

    bind(classOf[DatabaseService]).toInstance(service)
  }
}
