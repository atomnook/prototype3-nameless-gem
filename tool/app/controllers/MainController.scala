package controllers

import play.api.mvc.{Action, Controller}

class MainController extends Controller {
  val index = Action(_ => Ok(views.html.MainController.index()))
}
