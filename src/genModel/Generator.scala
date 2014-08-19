package genModel

import scala.slick.model.codegen.SourceCodeGenerator
import scala.slick.driver.JdbcProfile
import scala.reflect.runtime.currentMirror

object Generator extends App {

  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost/freedb"
  val username = "root"
  val password = "123qwe"

  execute(url, driver, username, password, "scala.slick.driver.MySQLDriver", "./src", "gen")

  def execute(url: String, jdbcDriver: String, user: String, password: String, slickDriver: String, outputFolder: String, pkg: String) = {
    val driver: JdbcProfile = currentMirror.reflectModule(currentMirror.staticModule(slickDriver)).instance.asInstanceOf[JdbcProfile]

    driver.simple.Database.forURL(url, driver = jdbcDriver, user = user, password = password).withSession {
      implicit session =>
        new SourceCodeGenerator(driver.createModel).writeToFile(slickDriver, outputFolder, pkg)
    }
  }
}