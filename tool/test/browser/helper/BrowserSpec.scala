package browser.helper

import domain.service.DatabaseService
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

trait BrowserSpec extends PlaySpec with OneServerPerSuite with AllBrowsersPerSuite with BeforeAndAfterEach {
  override lazy val browsers = Vector(ChromeInfo)

  implicit override lazy val app: Application = {
    new GuiceApplicationBuilder().configure("database.init.enable " -> false).build()
  }

  protected[this] def service: DatabaseService = app.injector.instanceOf(classOf[DatabaseService])

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    service.read("")
  }
}
