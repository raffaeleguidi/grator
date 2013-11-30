package models.fields

//import models.DB.FieldRow

class BooleanField(val name: String, val moduleName: String, val required: Boolean) extends Field{
	def controllerForm: String = {
		//val formType = if(field.required) "boolean" else "optional(boolean).getOrElse(false)"
		val formType = "boolean"
		"\""+name+"\" -> "+formType
	}

	override def htmlForm: String = {
		//val name = field.name
		val moduleName = "field.module.name"
		s"""<legend>
				@Messages("$moduleName.$name")
		   </legend>
		 @helper.checkbox(
	      rowForm("$name")
	    )"""
	}

	def fieldTable: String = {
		//val name = field.name
		//val required = if(field.required){", O.NotNull"} else {""}
		s"""def $name = column[Boolean]("$name")"""
	}

	override def nameInTable: String = {
		name
	}

	def fieldType: String = "Boolean"

	override def classDefinition: String = {
		//val name = this.field.name
		//val fieldType = fieldType
		s"""$name: $fieldType"""
	}
}