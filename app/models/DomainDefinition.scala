package models

import utils.{TextUtils => tu}
import play.Logger
import utils.FileUtils
import models.fields._


object DomainDefinition {
	val modules = List(
	  				CrudModule("test", 
	  				    List(
	  				        models.fields.IdField(),
	  				        TextField("name"),
	  				        TextField("foreign_id")
	  				    )
	  				)
		  		)
		  		
  def generateAll(): Unit = {
    for(module <- modules){
      module.generateAll
    }

    this.generateRoutes(modules)
    this.generateMessages(modules)
    this.generateMenu(modules)
  }

  def generateMessages(modules: List[CrudModule]): Unit = {
    val path = "conf/messages"

    FileUtils.writeToFile(path,views.html.application.template.messages("Crud", modules).toString)
    FileUtils.writeToFile(path+".es",views.html.application.template.messages_es("Crud", modules).toString)
  }

  def generateRoutes(modules: List[CrudModule]): Unit = {
    val path = "conf/routes"
    FileUtils.writeToFile(path,views.html.application.template.routes(modules).toString)
  }

  def generateMenu(modules: List[CrudModule]): Unit = {
    val path = "app/views/main.scala.html"
    FileUtils.writeToFile(path,views.html.application.template.main(modules).toString)
  }
}


case class CrudModule (name: String, fields: List[Field]){
	val varName = tu.underscoreToCamel(name)
	val upName = varName.capitalize
	val controllerName = upName + "Crud"
	val rowName = upName
	val tableName = upName+"Table"
	val formName = upName+"Form"
	val pluralName = varName+"s" //TODO add plural name field to module and update this method
	
  def generateAll(): Unit = {
    this.generateController()
    this.generateTable()
    this.generateRow()
    this.generateViews()
  }
	
  lazy val relatedFields: List[RelatedField] = for{
    field <- this.fields
    if(field.fieldType == "Related")
  }  yield(new RelatedField(field))
	
  lazy val renderFields = for (field <- fields) yield (FieldFactory.get(field))
  
  def getPath(folder: String, fileTermination: String): String = {
    val basePath = "./"

    val path = basePath+folder+name.capitalize+fileTermination
    Logger.debug(path)
    path
  }

  def generateController(): Unit = {
    val path = this.getPath("app/controllers/","Crud.scala")
    FileUtils.writeToFile(path,views.html.module.template.controller(this,renderFields,relatedFields).toString)
  }

  def generateTable(): Unit = {
    val path = this.getPath("app/models/DB/","Table.scala")
    FileUtils.writeToFile(path,views.html.module.template.table(this.module,this.renderFields, this.relatedFields).toString)
  }

  def generateRow(): Unit = {
    val path = this.getPath("app/models/DB/",".scala")
    FileUtils.writeToFile(path,views.html.module.template.row(this.module,this.renderFields).toString)
  }

  def generateDetailView(): Unit = {
    val path = this.getViewPath("detail")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.detail(this.module).toString)
  }

  def generateEditView(): Unit = {
    val path = this.getViewPath("edit")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.edit(this.module).toString)
  }

  def generateFormView(): Unit = {
    val path = this.getViewPath("form")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.form(this.module,this.renderFields).toString)
  }

  def generateIndexView(): Unit = {
    val path = this.getViewPath("index")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.index(this.module).toString)
  }

  def generateInsertView(): Unit = {
    val path = this.getViewPath("insert")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.insert(this.module).toString)
  }

  def generateListView(): Unit = {
    val path = this.getViewPath("list")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.list(this.module,this.renderFields).toString)
  }

  def generateViews(): Unit = {
    this.generateDetailView()
    this.generateEditView()
    this.generateFormView()
    this.generateIndexView()
    this.generateInsertView()
    this.generateListView()
  }
}

//case class Field (name: String,typeOf: String) 


