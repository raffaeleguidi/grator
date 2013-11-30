
package models.DB

import play.api.Play.current

import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._
import scala.slick.driver.MySQLDriver.DeleteInvoker
import slick.lifted.{Join, MappedTypeMapper}

case class Test(
  
    id: Option[Long] = None,
  
    name: Option[String],
  
    foreign_id: Option[String]
  
){
  
}

object Test{
  def save(test: Test):Long = {
    DB.withTransaction { implicit session =>
      tests.returning(tests.id).insert(test)
    }
  }
  
  def update(test: Test):Int = {
    DB.withTransaction { implicit session =>
      val q = for {
        s <- tests
        if s.id === test.id
      } yield(s)
      q.update(test)
    }
  }
  
  def delete(test: Test):Int = {
    DB.withTransaction { implicit session =>
       val q = for {
        s <- tests
        if s.id === test.id.get
      } yield(s)
      (new DeleteInvoker(q)).delete
    }
  }

  
  def findAll: List[Test] = {
    DB.withSession { implicit session =>
      Query(tests).list
    }
  }
  
  def findById(id: Long):Option[Test] = {
    DB.withSession { implicit session =>
      val q = for{
        s <- tests if s.id === id
      } yield (s)
      q.firstOption
    }
  }

  /* gotta look into it
  def getOptions(): Seq[(String,String)] = {
    DB.withSession { implicit session =>
      val tests = for {
        p <- tests
      } yield(p)
      for(test <- tests.list) yield(test.id.get.toString,test.name) 
    }
  }
  */
}