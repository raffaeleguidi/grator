
package models.DB

import play.api.Play.current

import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._
import slick.lifted.{Join, MappedTypeMapper}

object tests extends Table[Test]("test"){
  


	def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

	def name = column[String]("name")

	def foreign_id = column[String]("foreign_id")


  def * = id.? ~ name.? ~ foreign_id.? <> (Test.apply _, Test.unapply _)

  


}