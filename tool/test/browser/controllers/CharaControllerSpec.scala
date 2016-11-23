package browser.controllers

import browser.arbitrary._
import browser.helper.create.CreateChara
import browser.helper.{BrowserSpec, GoTo, ImplicitId}
import controllers.ReverseCharaController
import domain.service.Crud
import model.item.ArmorType
import model.{Chara, Equipments}
import org.scalatestplus.play.BrowserInfo
import play.api.mvc.Call

class CharaControllerSpec extends BrowserSpec with GoTo with CreateChara with ImplicitId {
  private[this] def crud: Crud[Chara] = service.characters()

  private[this] def controller = controllers.routes.CharaController

  private[this] def goTo(f: ReverseCharaController => Call): Unit = goTo(f(controller))

  override def sharedTests(browser: BrowserInfo): Unit = {
    controller.gainXp(":id").url must {
      "gain xp " + browser.name in {
        val button = id("gain-xp")
        val xp = 100
        val last = create()
        val expected = last.update(
          _.raceLevel.xp.modify(_ + xp),
          _.classes.modify(_.map(_.update(_.level.xp.modify(_ + xp)))))
        crud.create(last)

        goTo(_.get(last.getId.id))

        explicitlyWait(button)

        numberField("xp").value = ((1 + last.classes.length) * xp).toString

        click on button

        eventually {
          assert(crud.read() === List(expected))
        }
      }
    }

    controller.equip(":id").url must {
      def equipments(): Equipments = {
        val bodyTypes = Set(ArmorType.HEAVY, ArmorType.LIGHT, ArmorType.CLOTH)

        val primary = ArbitraryWeapon.arbitrary
        val secondary = ArbitraryArmor.arbitrary.update(_.`type` := ArmorType.SHIELD)
        val head = ArbitraryArmor.arbitrary.update(_.`type` := ArmorType.HEAD)
        val arm = ArbitraryArmor.arbitrary.update(_.`type` := ArmorType.ARM)
        val body = Stream.continually(ArbitraryArmor.arbitrary).filter(a => bodyTypes.contains(a.`type`)).head
        val foot = ArbitraryArmor.arbitrary.update(_.`type` := ArmorType.FOOT)
        val acc1 = ArbitraryAccessory.arbitrary
        val acc2 = ArbitraryAccessory.arbitrary
        val acc3 = ArbitraryAccessory.arbitrary

        service.weapons().create(primary)
        Seq(secondary, head, arm, body, foot).foreach(service.armors().create)
        Seq(acc1, acc2, acc3).foreach(service.accessories().create)

        Equipments().update(
          _.primary := primary.id(),
          _.secondary := secondary.id(),
          _.head := head.id(),
          _.arm := arm.id(),
          _.body := body.id(),
          _.foot := foot.id(),
          _.accessory1 := acc1.id(),
          _.accessory2 := acc2.id(),
          _.accessory3 := acc3.id())
      }

      "equip equipments " + browser.name in {
        val button = id("equip")
        val last = create()
        val expected = last.update(_.equipments := equipments())
        crud.create(last)

        goTo(_.get(last.getId.id))

        explicitlyWait(button)

        singleSel("primary").value = expected.getEquipments.getPrimary.id
        singleSel("secondary").value = expected.getEquipments.getSecondary.id
        singleSel("head").value = expected.getEquipments.getHead.id
        singleSel("arm").value = expected.getEquipments.getArm.id
        singleSel("body").value = expected.getEquipments.getBody.id
        singleSel("foot").value = expected.getEquipments.getFoot.id
        singleSel("accessory1").value = expected.getEquipments.getAccessory1.id
        singleSel("accessory2").value = expected.getEquipments.getAccessory2.id
        singleSel("accessory3").value = expected.getEquipments.getAccessory3.id

        click on button

        eventually {
          assert(service.characters().read() === List(expected))
        }
      }

      "unequip equipments " + browser.name in {
        val button = id("equip")
        val last = create().update(_.equipments := equipments())
        val expected = last.update(_.equipments := Equipments())
        crud.create(last)

        goTo(_.get(last.getId.id))

        explicitlyWait(button)

        Seq("primary", "secondary", "head", "arm", "body", "foot", "accessory1", "accessory2", "accessory3").
          foreach(id => singleSel(id).value = "")

        click on button

        eventually {
          assert(service.characters().read() === List(expected))
        }
      }
    }
  }
}
