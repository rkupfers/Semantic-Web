package jena

import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.vocabulary.VCARD
import com.hp.hpl.jena.rdf.model.Resource
import com.hp.hpl.jena.util.FileManager
import com.hp.hpl.jena.vocabulary.DB

object RdfPlay {

  def read {
    val model = ModelFactory.createDefaultModel();

    // use the FileManager to find the input file
    val in = FileManager.get().open("./towrite.rdf");
    if (in == null) {
      throw new IllegalArgumentException(
        "File: " + "./towrite.rdf" + " not found");
    }

    // read the RDF/XML file
    model.read(in, null);

    // write it to standard out
    model.write(System.out,"RDF/XML");
  }
  
  def test{
    // some definitions
    val personURI = "http://somewhere/JohnSmith";
    val givenName = "John";
    val familyName = "Smith";
    val fullName = givenName + " " + familyName;

    // create an empty Model
    val model = ModelFactory.createDefaultModel();

    // create the resource
    //   and add the properties cascading style
    val johnSmith = model.createResource(personURI)
      .addProperty(VCARD.FN, fullName)
      .addProperty(VCARD.N,
        model.createResource()
          .addProperty(VCARD.Given, givenName)
          .addProperty(VCARD.Family, familyName)
          .addProperty(VCARD.CLASS, "Blubb")
          .addLiteral(VCARD.CATEGORIES, "Blah"));

    // list the statements in the Model
    val iter = model.listStatements();

    // print out the predicate, subject and object of each statement
    while (iter.hasNext()) {
      val stmt = iter.nextStatement(); // get next statement
      val subject = stmt.getSubject(); // get the subject
      val predicate = stmt.getPredicate(); // get the predicate
      val objekt = stmt.getObject(); // get the object

      System.out.print(subject.toString());
      System.out.print(" " + predicate.toString() + " ");
      objekt match {
        case value: Resource => println(value)
        case _ => System.out.print(" \"" + objekt.toString() + "\"");
      }
      System.out.println(" .");
      }

    println
    model.write(System.out, "Turtle");
  }

  def main(args: Array[String]): Unit = {
//    println(DB.getURI());
    read
  }
}