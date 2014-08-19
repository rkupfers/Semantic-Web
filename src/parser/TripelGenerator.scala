package parser

import scala.slick.driver.MySQLDriver.simple._
import gen.Tables
import gen.Tables.Discs
import java.util.Scanner
import java.net.URL
import java.io.IOException
import gen.Tables.Genres
import jena.RdfMaker

object TripelGenerator extends App {
  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost/freedb"
  val username = "root"
  val password = "123qwe"

  val parrallelParser = (for(i<-39 to 50) yield (i*10000,"Data"+i+".ttl")).par
//  println(parrallelParser);
  parrallelParser.foreach( tupel => toRDF(tupel._1, tupel._2))

  def toRDF(from: Int, name: String) {
    val rdf = new RdfMaker(name)
    val db = Database.forURL(url, username, password, null, driver)
    db withSession {
      implicit session =>
        val dis = for (d <- Discs.drop(from).take(10000)) yield (d.artist, d.title, d.year)

        dis.foreach {
          case (Some(artist), album, year) =>
            getChartPlacement(artist, album) match {
              case Some(triple) =>rdf.addAlbum(triple.album, triple.chart, triple.artist, year)
              case _ =>
            }
          case _ =>
        }
    }
    println(name + " ready...")
    rdf.print
  }

  case class triple(artist: String, album: String, chart: Int)

  def getChartPlacement(artist: String, album: String): Option[triple] = {
    val url = new URL("???")
    try {
      val scanner = new Scanner(url.openStream());
      while (scanner.hasNextLine()) {
        val line = scanner.nextLine()
        if (line.contains("chstposition")) {
          scanner.close();
          val index = line.indexOf("chstposition") + 43
          return Some(triple(artist, album, Integer.parseInt(line.substring(index, index + 2).trim)))
        }
      }
      scanner.close();
      None
    } catch {
      case e: IOException => None
    }
  }
}