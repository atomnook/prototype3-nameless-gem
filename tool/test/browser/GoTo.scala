package browser

import play.api.mvc.Call

trait GoTo[A] extends BrowserSpec {
  protected[this] def reverseController: A

  def goTo(call: Call): Unit = {
    go to s"http://localhost:$port" + call.url
  }

  def goTo(f: A => Call): Unit = {
    goTo(f(reverseController))
  }
}
