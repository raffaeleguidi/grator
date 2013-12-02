package controllers

import play.api._
import play.api.mvc._
import models._
import models.fields._
import views.html.defaultpages.notFound

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def generate = Action {
     if (!Play.isDev(play.api.Play.current)) {
       NotFound
     } else {
    	CrudModules.generate("GATOR Crud Generator",
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
}