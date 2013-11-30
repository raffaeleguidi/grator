package models.fields

class IntegerField(val name: String, val moduleName: String, val required: Boolean) extends Field {
	def controllerForm: String = {
		val formType = if(required) "number" else "optional(number)"
		"\""+name+"\" -> "+formType
	}

	def fieldTable: String = {
		//val name = field.name
		//val required = if(required){", O.NotNull"} else {""}
		s"""def $name = column[Int]("$name"$required)"""
	}

	def fieldType: String = "Int"
}