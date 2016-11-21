package browser.crud

import browser.{BrowserSpec, GoTo}
import domain.service.Crud
import org.scalatestplus.play.BrowserInfo
import play.api.mvc.Call

trait CrudSpec[A, B] extends BrowserSpec with GoTo[B] {
  protected[this] def list: Call

  protected[this] def get(id: String): Call

  protected[this] def fill(a: A, hasIdField: Boolean): Unit

  protected[this] def id(a: A): String

  protected[this] def create(): A

  protected[this] def crud: Crud[A]

  protected[this] def update(id: A, data: A): A

  def sharedTests(browser: BrowserInfo) = {
    list.url must {
      "create " + browser.name in {
        val expected = create()
        val button = id("create")

        goTo(list)

        explicitlyWait(button)

        fill(expected, hasIdField = true)

        click on button

        eventually {
          assert(crud.read() === List(expected))
        }
      }
    }

    get(":id").url must {
      "delete " + browser.name in {
        val deleted = create()
        crud.create(deleted)

        val button = id("delete")

        goTo(get(id(deleted)))

        explicitlyWait(button)

        click on button

        eventually {
          assert(crud.read() === List.empty)
        }
      }

      "update " + browser.name in {
        val last = create()
        val updated = update(last, create())
        crud.create(last)

        val button = id("update")

        goTo(get(id(last)))

        explicitlyWait(button)

        fill(updated, hasIdField = false)

        click on button

        eventually {
          assert(crud.read() === List(updated))
        }
      }
    }
  }
}
