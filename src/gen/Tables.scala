package gen
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = scala.slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: scala.slick.driver.JdbcProfile
  import profile.simple._
  import scala.slick.model.ForeignKeyAction
  import scala.slick.jdbc.{GetResult => GR}
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  
  /** Entity class storing rows of table Discs
   *  @param disc Database column disc AutoInc, PrimaryKey
   *  @param length Database column length Default(-1)
   *  @param artist Database column artist 
   *  @param title Database column title 
   *  @param dtitle Database column dtitle 
   *  @param year Database column year Default(-1)
   *  @param genre Database column genre Default(-1)
   *  @param extra Database column extra  */
  case class DiscsRow(disc: Int, length: Short= -1, artist: Option[String], title: String, dtitle: Option[String], year: Short= -1, genre: Short= -1, extra: String)
  /** GetResult implicit for fetching DiscsRow objects using plain SQL queries */
  implicit def GetResultDiscsRow(implicit e0: GR[Int], e1: GR[Short], e2: GR[String]): GR[DiscsRow] = GR{
    prs => import prs._
    DiscsRow.tupled((<<[Int], <<[Short], <<?[String], <<[String], <<?[String], <<[Short], <<[Short], <<[String]))
  }
  /** Table description of table discs. Objects of this class serve as prototypes for rows in queries. */
  class Discs(tag: Tag) extends Table[DiscsRow](tag, "discs") {
    def * = (disc, length, artist, title, dtitle, year, genre, extra) <> (DiscsRow.tupled, DiscsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (disc.?, length.?, artist, title.?, dtitle, year.?, genre.?, extra.?).shaped.<>({r=>import r._; _1.map(_=> DiscsRow.tupled((_1.get, _2.get, _3, _4.get, _5, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column disc AutoInc, PrimaryKey */
    val disc: Column[Int] = column[Int]("disc", O.AutoInc, O.PrimaryKey)
    /** Database column length Default(-1) */
    val length: Column[Short] = column[Short]("length", O.Default(-1))
    /** Database column artist  */
    val artist: Column[Option[String]] = column[Option[String]]("artist")
    /** Database column title  */
    val title: Column[String] = column[String]("title")
    /** Database column dtitle  */
    val dtitle: Column[Option[String]] = column[Option[String]]("dtitle")
    /** Database column year Default(-1) */
    val year: Column[Short] = column[Short]("year", O.Default(-1))
    /** Database column genre Default(-1) */
    val genre: Column[Short] = column[Short]("genre", O.Default(-1))
    /** Database column extra  */
    val extra: Column[String] = column[String]("extra")
    
    /** Index over (artist) (database name artist) */
    val index1 = index("artist", artist)
    /** Index over (genre) (database name genres) */
    val index2 = index("genres", genre)
    /** Index over (dtitle) (database name title) */
    val index3 = index("title", dtitle)
  }
  /** Collection-like TableQuery object for table Discs */
  lazy val Discs = new TableQuery(tag => new Discs(tag))
  
  /** Entity class storing rows of table Genres
   *  @param id Database column id AutoInc, PrimaryKey
   *  @param name Database column name  */
  case class GenresRow(id: Short, name: Option[String])
  /** GetResult implicit for fetching GenresRow objects using plain SQL queries */
  implicit def GetResultGenresRow(implicit e0: GR[Short], e1: GR[String]): GR[GenresRow] = GR{
    prs => import prs._
    GenresRow.tupled((<<[Short], <<?[String]))
  }
  /** Table description of table genres. Objects of this class serve as prototypes for rows in queries. */
  class Genres(tag: Tag) extends Table[GenresRow](tag, "genres") {
    def * = (id, name) <> (GenresRow.tupled, GenresRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, name).shaped.<>({r=>import r._; _1.map(_=> GenresRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id AutoInc, PrimaryKey */
    val id: Column[Short] = column[Short]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name  */
    val name: Column[Option[String]] = column[Option[String]]("name")
    
    /** Uniqueness Index over (name) (database name name) */
    val index1 = index("name", name, unique=true)
  }
  /** Collection-like TableQuery object for table Genres */
  lazy val Genres = new TableQuery(tag => new Genres(tag))
}