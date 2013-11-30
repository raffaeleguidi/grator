package models.fields

//import models.CrudModule

case class IdField(val name: String, val moduleName: String) extends Field {
	val required: Boolean = true
	
	def controllerForm: String = {
		"\"id\" -> optional(longNumber)"
	}

	override def htmlForm: String = {
 """@row match {
    	case Some(row) => {
    		<input type="hidden" name="id" value="@row.id.get">
    	}
    	case None => {}
	  }"""
	}

	def fieldTable: String = {
		"def id = column[Long](\"id\", O.PrimaryKey, O.AutoInc)"
	}

	override def nameInTable: String = {
		"id.?"	
	}

	override def list: String = {
	  ""
//		val moduleName = module.name
//		val controllerName = module.controllerName
//		s"""<a href="@controllers.routes.$controllerName.detail(row.id.get)">@row.id</a>"""
	}

	def fieldType: String = "Long"

	override def classDefinition: String = {
		"id: Option[Long] = None"
	}
}