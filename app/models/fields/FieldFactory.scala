package models.fields

import models.fields._
import models._

case class customException(smth:String)  extends Exception

object FieldFactory{
//	def get(field: Field): Field = {
//		field.fieldType match{
//			case "Id" 		=> new IdField(field)
//			case "Name" 	=> new NameField(field)
//			case "Text" 	=> new TextField(field)
//			case "Integer" 	=> new IntegerField(field)
//			case "Boolean" 	=> new BooleanField(field)
//			case "Related"  => new RelatedField(field)
//			case x:String	=> throw new customException("Field type "+x+" not found")
//		}
//	}
}