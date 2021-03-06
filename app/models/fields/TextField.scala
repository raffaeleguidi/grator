package models.fields

//import models.DB.FieldRow

case class TextField(val name: String, val moduleName: String, val required: Boolean) extends Field{
	def controllerForm: String = {
		val formType = if(required) "nonEmptyText" else "optional(text)"
		"\""+name+"\" -> "+formType
	}

	def fieldTable: String = {
		//val name = field.name
		val requiredClause = if(required){", O.NotNull"} else {""}
		s"""def $name = column[String]("$name"$requiredClause)"""
	}

	def fieldType: String = "String"
}