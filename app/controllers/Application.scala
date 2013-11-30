package controllers

import play.api._
import play.api.mvc._
import models._
import models.fields._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def generate = Action {
    CrudModules.generate(
        List(
  				CrudModule("test", 
  				    List(
  				        IdField("id", "test"),
  				        TextField("name", "test", false),
  				        TextField("foreign_id", "test", false)
  				    )
  				)
	  		)
    )
    Ok("crud generated")
  }
}