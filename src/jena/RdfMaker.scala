package jena

import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.rdf.model.Resource
import scala.collection.mutable.HashMap
import com.hp.hpl.jena.vocabulary.VCARD
import com.hp.hpl.jena.vocabulary.DB
import java.io.FileWriter

class RdfMaker(name:String) {

  val model = ModelFactory.createDefaultModel();
  val artists = HashMap.empty[String, Resource]
  val prefix = "http://www.semanticweb.org/u410/ontologies/2014/5/music#"
  val dbpedia = "http://www.dbpedia.org/resource/"
  val uri = prefix + "Artist"
  val artistmodel = model.createResource(uri)
  val albumUri = prefix + "Album"
  val albummodel = model.createResource(albumUri)
  
  def addArtist(artist: String): Resource = {
    if (!artists.contains(artist)) {
      val theartist = model.createResource(dbpedia + artist.replaceAll(" ", "_"))
      artistmodel.addProperty(model.createProperty(prefix + "hasArtist"), theartist)
      artistmodel.addProperty(com.hp.hpl.jena.vocabulary.RDF.`type`, theartist)
      theartist.addProperty(model.createProperty(prefix + "hasName"), artist)
      artists.+=(artist -> theartist)
      
    }
    
    artists(artist)
  }

  def addAlbum(album: String, placement: Int, artist: String, year:Int = 0) {
    val artistmodel = addArtist(artist)
    val thealbum = model.createResource(dbpedia + album.replaceAll(" ", "_"))
    thealbum.addProperty(model.createProperty(prefix + "hasName"), album)
    thealbum.addProperty(model.createProperty(prefix + "chartplacement"), placement.toString)
    thealbum.addProperty(model.createProperty(prefix + "year"), year.toString)
    albummodel.addProperty(model.createProperty(prefix + "isAlbum" ), thealbum)
    albummodel.addProperty(com.hp.hpl.jena.vocabulary.RDF.`type`, thealbum)
    artistmodel.addProperty(model.createProperty(prefix + "hasAlbum"), thealbum)
    println(artist + "\tAlbum: " + album + "\tYear: " + year + "\tChartplacement: " + placement)
  }

  def print {
    model.write(new FileWriter(name),"Turtle")
  }
}