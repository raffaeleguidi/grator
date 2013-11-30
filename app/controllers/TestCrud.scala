
package controllers

import play.api._
import play.api.mvc._

import models.DB._

import play.api.data._
import play.api.data.Forms._

import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._
import slick.lifted.{Join, MappedTypeMapper}

object TestCrud extends Controller {
  def index = Action {
    val tests = Test.findAll

    Ok(views.html.test.index(tests))
  }

  val TestForm = Form(
      mapping(
					"id" -> optional(longNumber),
					"name" -> optional(text),
					"foreign_id" -> optional(text)

      )(Test.apply)(Test.unapply)
  )

  def insert = Action {
    Ok(views.html.test.insert(TestForm))
  }
  

  def detail(id: Long) = Action {
    Test.findById(id).map{
      test => Ok(views.html.test.detail(test))
    }.getOrElse(NotFound)
  }
  
  def save = Action { implicit request =>
    TestForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.test.insert(formWithErrors)),
      test => {
        val id = Test.save(test)
        Redirect(routes.TestCrud.detail(id))
      }
    )
  }
  
  def edit(id: Long) = Action{
    Test.findById(id).map{
      test:Test => Ok(views.html.test.edit(TestForm.fill(test),test))
    }.getOrElse(NotFound)
  }
  
  def delete(id: Long) = Action{
    implicit request =>
    Test.findById(id).map{
      test => {
          Test.delete(test)
          Redirect(routes.TestCrud.index())
    }
    }.getOrElse(NotFound)
  }
  
  def update(id: Long) = Action{ implicit request =>
    Test.findById(id).map{
      test => {
      TestForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.test.edit(formWithErrors,test)),
        test => {
          Test.update(test)
          Redirect(routes.TestCrud.detail(test.id.get))
        }
      )
    }
    }.getOrElse(NotFound)
  }
}