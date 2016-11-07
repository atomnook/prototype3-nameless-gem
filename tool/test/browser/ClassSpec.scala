package browser

import org.scalatestplus.play.BrowserInfo

class ClassSpec extends BrowserSpec {
  def sharedTests(browser: BrowserInfo) = {
    "/classes" must {
      "create " + browser.name in {
        val expected = newClass

        go to s"http://localhost:$port/classes"

        textField("id").value = expected.getId.id
        textField("name").value = expected.name

        click on id("create")

        eventually {
          assert(service.classes().read() === Set(expected))
        }
      }
    }
  }
}
