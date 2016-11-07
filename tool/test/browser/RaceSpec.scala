package browser

import model.Race
import org.scalatestplus.play._

class RaceSpec extends BrowserSpec {
  def sharedTests(browser: BrowserInfo) = {
    "/races" must {
      "create " + browser.name in {
        val expected = newRace

        go to s"http://localhost:$port/races"

        textField("id").value = expected.getId.id
        textField("name").value = expected.name

        click on id("create")

        eventually {
          assert(service.races().read() === Set(expected))
        }
      }
    }
  }
}
