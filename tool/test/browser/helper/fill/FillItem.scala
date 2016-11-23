package browser.helper.fill

import model.item.Item
import org.scalatestplus.play.{AllBrowsersPerSuite, OneServerPerSuite, PlaySpec}

trait FillItem extends PlaySpec with OneServerPerSuite with AllBrowsersPerSuite {
  protected[this] def fill(item: Item, hasIdField: Boolean): Unit = {
    if (hasIdField) {
      textField("id").value = item.getId.id
    }

    textField("name").value = item.name
    numberField("price").value = item.price.toString
  }
}
