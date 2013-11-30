package models.fields

class NameField(val name: String, val moduleName: String, val required: Boolean) extends Field{
	def controllerForm: String = {
		"\"name\" -> nonEmptyText"
	}

	override def htmlForm: String = {
		val moduleName = "field.module.name"
		s"""<legend>
				@Messages("$moduleName.item_name")
		   </legend>
		    @helper.inputText(rowForm("name"))"""
	}

	def fieldTable: String = {
		"def name = column[String](\"name\",O.NotNull)"
	}

	def fieldType: String = "String"
}