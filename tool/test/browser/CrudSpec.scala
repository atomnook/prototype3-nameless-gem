package browser

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

        goTo(list)

        fill(expected, hasIdField = true)

        click on id("create")

        eventually {
          assert(crud.read() === List(expected))
        }
      }
    }

    get(":id").url must {
      "delete " + browser.name in {
        val deleted = create()
        crud.create(deleted)

        goTo(get(id(deleted)))

        click on id("delete")

        eventually {
          assert(crud.read() === List.empty)
        }
      }

      "update " + browser.name in {
        val last = create()
        val updated = update(last, create())
        crud.create(last)

        goTo(get(id(last)))

        fill(updated, hasIdField = false)

        click on id("update")

        eventually {
          assert(crud.read() === List(updated))
        }
      }
    }
  }
}
