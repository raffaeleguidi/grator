package utils

import java.io.File

object FileUtils{
  def createPath(path: String){
    val folder = new java.io.File(path)
    try{
      if(!folder.exists){
        folder.getParentFile().mkdirs();
      }
    }
  } 

  def writeToFile(path: String, fileName: String) {
    createPath(path)
    val file = new java.io.PrintWriter(new File(path))
    try {
      file.write(fileName)
    } finally {
      file.close()
    }
  }
  def appendToFile(path: String, fileName: String) {
    createPath(path)
    val file = new java.io.PrintWriter(new File(path))
    try {
      file.append(fileName)
    } finally {
      file.close()
    }
  }

}