package JSON

import java.io.FileReader
import java.io.BufferedReader
import java.io.StringReader
import com.google.gson.GsonBuilder

case class b(bindings: List[resolve])
case class t(val typ: String, value: String)
case class resolve(val artist: t, val genre: t)

object JSON extends App {
  val blubb = """{ "artist": { "type": "uri", "value": "http://dbpedia.org/resource/!Action_Pact!" }	, "genre": { "type": "uri", "value": "http://dbpedia.org/resource/Punk_rock" }}""";
  val b = new BufferedReader(new FileReader("json.js"))
  val gson = new GsonBuilder().create();
  val c = scala.io.Source.fromFile("json.js").getLines
  //  println(c);
  for (a <- c; if (a.contains("artist\":"))) {
    //    println(a);
    try {
      val p = gson.fromJson(a.dropRight(1), classOf[resolve]);
      System.out.println(p.artist.value + "\t" + p.genre.value);
    } catch {
      case x: com.google.gson.JsonSyntaxException => {
        val p = gson.fromJson(a.dropRight(5), classOf[resolve]);
        System.out.println(p.artist.value + "\t" + p.genre.value);
      }
      case _ => System.err.println("Error");
    }
  }
}