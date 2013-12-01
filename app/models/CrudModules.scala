package models

import utils.{TextUtils => tu}
import play.Logger
import utils.FileUtils
import models.fields._


object CrudModules {
  def generate(modules: List[CrudModule]): Unit = {
    for(module <- modules){
      module.generateAll
    }

    CrudModules.this.generateRoutes(modules)
    CrudModules.this.generateMessages(modules)
    //this.generateMenu(modules)
  }

  def generateMessages(modules: List[CrudModule]): Unit = {
    val path = "conf/messages"

    FileUtils.writeToFile(path,views.html.application.template.messages("Crud", modules).toString)
    FileUtils.writeToFile(path+".es",views.html.application.template.messages_es("Crud", modules).toString)
  }

  def generateRoutes(modules: List[CrudModule]): Unit = {
    val path = "conf/routes"
    FileUtils.appendToFile(path,views.html.application.template.routes(modules).toString)
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
	val formName = upName+"Form"
	val pluralName = varName+"s" //TODO add plural name field to module and update this method
	//val tableName = upName+"Table"
	val tableName = pluralName.capitalize
	
  def generateAll(): Unit = {
    this.generateController()
    this.generateTable()
    this.generateRow()
    this.generateViews()
  }
	
  lazy val relatedFields: List[RelatedField] = for{
    field <- this.fields
    if(field.fieldType == "Related")
  }  yield(new RelatedField(field.name, name, false))
	
  lazy val renderFields = for (field <- fields) yield (field)
  
  def getPath(folder: String, fileTermination: String): String = {
    val basePath = "./"

    val path = basePath+folder+name.capitalize+fileTermination
    Logger.debug(path)
    path
  }
  
  def getViewPath(viewName: String): String = {
    val basePath = "./"

    basePath+"app/views/"+this.name+"/"+viewName+".scala.html"
  }

  def generateController(): Unit = {
    val path = this.getPath("app/controllers/","Crud.scala")
    FileUtils.writeToFile(path,views.html.module.template.controller(this,renderFields,relatedFields).toString)
  }

  def generateTable(): Unit = {
    val path = this.getPath("app/models/DB/","Table.scala")
    FileUtils.writeToFile(path,views.html.module.template.table(this,this.renderFields, this.relatedFields).toString)
  }

  def generateRow(): Unit = {
    val path = this.getPath("app/models/DB/",".scala")
    FileUtils.writeToFile(path,views.html.module.template.row(this,this.renderFields).toString)
  }

  def generateDetailView(): Unit = {
    val path = this.getViewPath("detail")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.detail(this).toString)
  }

  def generateEditView(): Unit = {
    val path = this.getViewPath("edit")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.edit(this).toString)
  }

  def generateFormView(): Unit = {
    val path = this.getViewPath("form")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.form(this,this.renderFields).toString)
  }

  def generateIndexView(): Unit = {
    val path = this.getViewPath("index")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.index(this).toString)
  }

  def generateInsertView(): Unit = {
    val path = this.getViewPath("insert")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.insert(this).toString)
  }

  def generateListView(): Unit = {
    val path = this.getViewPath("list")
    FileUtils.writeToFile(path,views.html.module.template.moduleviews.list(this,this.renderFields).toString)
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

