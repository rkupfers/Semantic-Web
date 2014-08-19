package JSON

import java.util.Scanner
import java.net.URL
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import com.hp.hpl.jena.rdf.model.ModelFactory
import java.io.FileWriter

object JSONImporter extends App {

  val prefix = "http://www.semanticweb.org/u410/ontologies/2014/5/music#"

  val blubb = (for (i <- 1 to 10) yield (i, (i - 1) * 10000, i * 10000)).par
  blubb.foreach(x => makeTriple(x._1, get(x._2, x._3)))

  def makeTriple(number: Int, list: List[Option[ressource]]) {
    val model = ModelFactory.createDefaultModel();
    val genremodel = model.createResource(prefix + "Genre")
    for (i <- list) {
      i match {
        case Some(ressorce) => {
          val artist = model.createResource(ressorce.artist.replaceAll("http://dbpedia.org/resource/", "http://www.dbpedia.org/resource/"))
          genremodel.addProperty(com.hp.hpl.jena.vocabulary.RDF.`type`, ressorce.genre)
          artist.addProperty(model.createProperty(prefix + "hasGenre"), ressorce.genre)
        }
        case None =>
      }
    }
    model.write(new FileWriter("./data/dbpeadia/data" + number+".ttl"), "Turtle")
  }

  def get(from: Int, to: Int): List[Option[ressource]] = {

    val url = new URL("http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=select+distinct+%3Fartist+%3Fgenre%0D%0A%7B%0D%0A+%3Fartist+dbpprop%3Agenre+%3Fgenre.%0D%0A+Filter%28+contains%28str%28%3Fgenre%29%2C%22rock%22%29+%29.%0D%0A%7D+%0D%0Aoffset+" + from + "%0D%0ALIMIT+" + to + "&format=application%2Fsparql-results%2Bjson&timeout=30000&debug=on").openStream()
    val b = scala.io.Source.fromInputStream(url).getLines
    for (line <- b.toList) yield { getJsonObject(line) }
  }

  case class ressource(artist: String, genre: String)
  
  def getJsonObject(line: String): Option[ressource] = {
    val gson = new GsonBuilder().create();
    try {
      val p = gson.fromJson(line.dropRight(1), classOf[resolve]);
      return Some(new ressource(p.artist.value, p.genre.value));
    } catch {
      case x: com.google.gson.JsonSyntaxException => {
        try {
          val p = gson.fromJson(line.dropRight(5), classOf[resolve]);
          return Some(new ressource(p.artist.value, p.genre.value));
        } catch { case _ => None }
      }
      case _ => None
    }
  }
  
  case class b(bindings: List[resolve])
  case class t(val typ: String, value: String)
  case class resolve(val artist: t, val genre: t)
}