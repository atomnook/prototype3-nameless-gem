package browser.helper

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.scalatestplus.play.{AllBrowsersPerSuite, OneServerPerSuite, PlaySpec}
import play.api.mvc.Call

import scala.concurrent.duration._

trait GoTo extends PlaySpec with OneServerPerSuite with AllBrowsersPerSuite {
  def goTo(call: Call): Unit = {
    go to s"http://localhost:$port" + call.url
  }

  def explicitlyWait(query: Query, timeout: FiniteDuration = 10.seconds): Unit = {
    new WebDriverWait(webDriver, timeout.toSeconds).until(ExpectedConditions.presenceOfElementLocated(query.by))
  }
}
