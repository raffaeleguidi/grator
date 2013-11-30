package models.fields
import models.FieldUtils

trait Field {
	//def field: FieldRow
	def controllerForm: String

	val name: String // = lazy FieldUtils.underscoreToCamel(this.field.name)
	val required: Boolean
    val moduleName: String
    
	def htmlForm: String = {
		//val moduleName = module.name
		//val name = name
		s"""<legend>
				@Messages("$moduleName.$name")
		   </legend>
		    @helper.inputText(rowForm("$name"))"""
	}
	
	def fieldTable: String

	def nameInTable: String = {
		if(required){
			name	
		} else {
			name+".?"
		}
		
	}

	def list: String = {
		val name = this.name
		s"@row.$name"
	}

	def fieldType: String

	def classDefinition: String = {
		val name = this.name
		val fieldType = this.fieldType
		if(required){
			s"""$name: $fieldType"""
		} else {
			s"""$name: Option[$fieldType]"""
		}
	}
}