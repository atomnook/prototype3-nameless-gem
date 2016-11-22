package browser

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import play.api.mvc.Call

import scala.concurrent.duration._

trait GoTo[A] extends BrowserSpec {
  protected[this] def reverseController: A

  def goTo(call: Call): Unit = {
    go to s"http://localhost:$port" + call.url
  }

  def goTo(f: A => Call): Unit = {
    goTo(f(reverseController))
  }

  def explicitlyWait(query: Query, timeout: FiniteDuration = 5.seconds): Unit = {
    new WebDriverWait(webDriver, timeout.toSeconds).until(ExpectedConditions.presenceOfElementLocated(query.by))
  }
}
