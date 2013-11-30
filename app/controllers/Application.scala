package controllers

import play.api._
import play.api.mvc._
import models.DomainDefinition

object Application extends Controller {

  def index = Action {
    DomainDefinition.generateAll
    Ok("crud generated")
  }
}