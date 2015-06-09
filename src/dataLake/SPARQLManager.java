package dataLake;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.jena.atlas.lib.StrUtils;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

import dataLake.QueryBuilder;

public class SPARQLManager {
	public TBDManager datalake;
	public ArrayList<String> movies;
	public Model overallModel;
	public static final String PATH = "data/linkedMDB/";
	private static final boolean TEST_ENV = false;

	public final String ACTOR;
	public final String DIRECTOR;
	public final String GENRE;
	public final String ACTOR_NAME;
	public final String DIRECTOR_NAME;
	public final String GENRE_NAME;
	public final String MOVIE;
	public final String MOVIEDBP;
	public final String MOVIE_NAME;
	public final String RATING;
	public static final String CONN = ":";
	public static final String subject = " ?subject ";
	public static final String object = " ?object ";
	public static final String predicate = " ?predicate ";
	public static final String a = " a ";
	public static final String ws = " ";
	public static final String p = ".";
	public static final String sc = ";";

	public SPARQLManager() {
		datalake = new TBDManager();
		movies = new ArrayList<String>();
		ACTOR = QueryBuilder.LINKEMDB + CONN + getActorEntity();
		DIRECTOR = QueryBuilder.LINKEMDB + CONN + getDirectorEntity();
		GENRE = QueryBuilder.LINKEMDB + CONN + getGenreEntity();
		ACTOR_NAME = QueryBuilder.LINKEMDB + CONN + getActorNameEntity();
		DIRECTOR_NAME = QueryBuilder.LINKEMDB + CONN + getDirectorNameEntity();
		GENRE_NAME = QueryBuilder.LINKEMDB + CONN + getGenreNameEntity();
		MOVIE = QueryBuilder.LINKEMDB + CONN + getMovieEntity();
		MOVIEDBP = QueryBuilder.ONTOLOGY + CONN + getMovieDBPEntity();
		MOVIE_NAME = QueryBuilder.DCTITLE + CONN + getMovieNameEntity();
		RATING = QueryBuilder.ONTOLOGY + CONN + getRatingEntity();
	}

	public String getActorEntity() {
		return "actor";
	}

	public String getDirectorEntity() {
		return "director";
	}

	public String getGenreEntity() {
		return "genre";
	}

	public String getActorNameEntity() {
		return "actor_name";
	}

	public String getDirectorNameEntity() {
		return "director_name";
	}

	public String getGenreNameEntity() {
		return "film_genre_name";
	}

	public String getMovieEntity() {
		return "film";
	}

	public String getMovieDBPEntity() {
		return "Film";
	}

	public String getMovieNameEntity() {
		return "title";
	}

	public String getRatingEntity() {
		return "rating";
	}

	public Model execQuery(String query) {
		if (!TEST_ENV)
			return datalake.store(query);
		else
			return datalake.execConstQuery(query);
	}

	public void initialize()
	{
		this.getMovName();
		this.getActorMov();
		this.getActorMov2();
		this.getActorName();
		this.getActorClass();
		this.getDirectorMov();
		this.getDirectorName();
		this.getDirectorClass();
		this.getGenreMov();
		this.getGenreName();
		this.getGenreClass();
		this.getGenreName();
		this.getDirectorName();
		this.getActorClass();
	}
	public void selectMov() {
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildSelect(subject, predicate, object)
				+ QueryBuilder.buildWhere(subject, a, MOVIEDBP);
		System.out.println(query);
		ArrayList<ArrayList<String>> res = datalake.getSelectQuery(query,
				subject.trim(), predicate.trim(), object.trim());
		ArrayList<String> subjects = res.get(0);
		ArrayList<String> predicates = res.get(1);
		ArrayList<String> objects = res.get(2);

		System.out.println(subjects.size() + " " + predicates.size() + " "
				+ objects.size());
	}
	public ArrayList<ArrayList<String>> selectMovNames() {
		String whereString = subject + a + MOVIEDBP + p + subject + MOVIE_NAME
				+ object + p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildSelect(subject, predicate, object)
				+ QueryBuilder.buildWhere(whereString);
		// System.out.println(query);
		ArrayList<ArrayList<String>> res = datalake.getSelectQuery(query,
				subject.trim(), predicate.trim(), object.trim());
		ArrayList<String> subjects = res.get(0);
		ArrayList<String> predicates = res.get(1);
		ArrayList<String> objects = res.get(2);
		ArrayList<String> literals = res.get(3);
		// System.out.println(subjects.size() + " " + predicates.size() + " " +
		// objects.size()+" " + literals.size());
		res.remove(2);
		res.remove(1);
		return res;
	}
	public void selectActorMov() {
		String whereString = subject + a + MOVIEDBP + p + subject + ACTOR
				+ predicate + p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildSelect(subject, predicate, object)
				+ QueryBuilder.buildWhere(whereString);
		System.out.println(query);
		ArrayList<ArrayList<String>> res = datalake.getSelectQuery(query,
				subject.trim(), predicate.trim(), object.trim());
		ArrayList<String> subjects = res.get(0);
		ArrayList<String> predicates = res.get(1);
		ArrayList<String> objects = res.get(2);
	}
	public void selectActorFromMov(String mov) {
		String whereString = mov + ACTOR + subject + p + subject + ACTOR_NAME
				+ object + p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildSelect(subject, predicate, object)
				+ QueryBuilder.buildWhere(whereString);
		System.out.println(query);
		ArrayList<ArrayList<String>> res = datalake.getSelectQuery(query,
				subject.trim(), predicate.trim(), object.trim());
		ArrayList<String> subjects = res.get(0);
		ArrayList<String> predicates = res.get(1);
		ArrayList<String> objects = res.get(2);
		ArrayList<String> literals = res.get(3);
		// System.out.println(subjects.size() + " " + predicates.size() + " " +
		// objects.size()+ " " + literals.size());
	}
	public ArrayList<ArrayList<String>> selectMovieFromTitle(String mov) {
		String whereString = subject + MOVIE_NAME + " '" + mov + "'" + p
				+ subject + MOVIE_NAME + object;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildSelect(subject, predicate, object)
				+ QueryBuilder.buildWhere(whereString);
		// System.out.println(query);
		ArrayList<ArrayList<String>> res = datalake.getSelectQuery(query,
				subject.trim(), predicate.trim(), object.trim());
		ArrayList<String> subjects = res.get(0);
		ArrayList<String> predicates = res.get(1);
		ArrayList<String> objects = res.get(2);
		ArrayList<String> literals = res.get(3);
		// System.out.println(subjects.size() + " " + predicates.size() + " " +
		// objects.size()+ " " + literals.size());
		// for(int i=0;i<literals.size();i++)
		// System.out.println(subjects.get(i)+" "+ literals.get(i));// + "  " +
		// predicates.get(i));
		res.remove(2);
		res.remove(1);
		return res;

	}
	public void insertMovieRating(String subject, String uri) {
		insertInstance("<" + subject + ">", RATING,
				"<http://dbpedia.org/ontology/rating/" + uri + ">");
	}	
	public void insertMovieAmazon(String subjectURI) {
		insertInstance("<http://www.myexample.com/myMovie/"+subjectURI+">", a, MOVIEDBP);
	}
	public void insertMovieAmazonName(String name,String subjectURI) {
		insertInstance("<http://www.myexample.com/mySchema/"+subjectURI+">", MOVIE_NAME, "'"+name+"'");
	}	
	public void insertMovieAmazonRating(String subjectURI, String uriRating) {
		insertMovieRating("http://www.myexample.com/myMovie/"+subjectURI,uriRating);
	}	
	public void insertRatingInstance(String uri) {
		insertInstance("<http://dbpedia.org/ontology/rating/" + uri + ">", a, RATING);
	}
	private void insertInstance(String subject, String predicate,
			String object) {
		String insertString = createInsertString(subject, predicate,object);
//		System.out.println(insertString);
		String sparqlInsertString = createSparqlString(insertString);
//		System.out.print("\t"+sparqlInsertString);
		datalake.updateTriple(sparqlInsertString);
	}

	public String createInsertString(String subject, String predicate,
			String object) {
		String insertString = new StringBuffer("INSERT DATA { ")
				.append(subject).append(" ").append(predicate).append(" ")
				.append(object).append(" }").toString();

		return insertString;
	}

	public String createSparqlString(String str) {
		String sparqlString = StrUtils.strjoinNL(QueryBuilder.XSD,
				QueryBuilder.RDF, QueryBuilder.MYPrefix, QueryBuilder.MOVIE , QueryBuilder.DBPEDIA, QueryBuilder.DC, str);

//		System.out.println(sparqlString);
		return sparqlString;
	}

	public void getMov(int offset) {
		getMov(offset, false);
	}

	public void getMov(int offset, boolean order) {
		String orderString = order ? QueryBuilder.buildOrderBy(subject) : "";
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(subject, a, MOVIEDBP)
				+ QueryBuilder.buildWhere(subject, a, MOVIE) + orderString
				+ QueryBuilder.buildOffset(offset);
		System.out.println(query);
		// Model movs =execQuery(query);
		// List<Statement> subjects = movs.listStatements().toList();
		// for(Statement stat:subjects){System.out.println(stat.toString());}
		// System.out.println("getMov <> "+subjects.size());
	}

	public void getMov() {
		getMov(0, false);
	}

	public void getMovName() {
		String whereString = subject + a + MOVIE + p + subject + MOVIE_NAME
				+ object + p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(subject, MOVIE_NAME, object)
				+ QueryBuilder.buildWhere(whereString);
		Model movs = execQuery(query);
		System.out.println(query);
		List<Statement> subjects = movs.listStatements().toList();
		for (Statement stat : subjects) {
			System.out.println(stat.toString());
		}
		System.out.println("geMovName <> " + subjects.size());
	}

	public void getActorMov() {
		String whereString = subject + a + MOVIE + p + subject + ACTOR + object
				+ p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(subject, ACTOR, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(subject);
		Model movs = execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		for (Statement stat : subjects) {
			System.out.println(stat.toString());
		}
		System.out.println("geActorMov <> " + subjects.size());
	}

	public void getActorMov2() {
		String whereString = subject + a + MOVIE + p + subject + ACTOR + object
				+ p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(subject, ACTOR, object)
				+ QueryBuilder.buildWhere(whereString);
		Model movs = execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		for (Statement stat : subjects) {
			System.out.println(stat.toString());
		}
		System.out.println("geActorMov2 <> " + subjects.size());
	}

	public void getActorName() {
		String whereString = subject + a + ACTOR + p + subject + ACTOR_NAME
				+ object + p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(subject, ACTOR_NAME, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(subject + " " + object);
		System.out.println(query);
		Model movs = execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		// for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geActorName <> " + subjects.size());
	}

	public void getActorClass() {
		String whereString = subject + a + ACTOR;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(subject, a, ACTOR)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(subject);
		Model movs = execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		// for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geActorClass <> " + subjects.size());
	}

	// /// DIRECTOR

	public void getDirectorMov() {
		String whereString = subject + a + MOVIE + p + subject + DIRECTOR
				+ object + p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(subject, DIRECTOR, object)
				+ QueryBuilder.buildWhere(whereString);
		Model movs = execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		// for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geDirectorMov <> " + subjects.size());
	}

	public void getDirectorName() {
		String whereString = subject + a + DIRECTOR + p + subject
				+ DIRECTOR_NAME + object + p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(subject, DIRECTOR_NAME, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(predicate)
				+ QueryBuilder.buildLimit();
		Model movs = execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		// for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geDirectorNameMov <> " + subjects.size());
	}

	public void getDirectorClass() {
		String whereString = subject + a + DIRECTOR;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(subject, a, DIRECTOR)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(subject)
				+ QueryBuilder.buildLimit();
		Model movs = execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		// for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geDirectorClass <> " + subjects.size());
	}

	// // GENRE

	public void getGenreMov() {
		String whereString = subject + a + MOVIE + p + subject + GENRE + object
				+ p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(subject, GENRE, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(subject);
		Model movs = execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		// for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geGenreMov <> " + subjects.size());
	}

	public void getGenreName() {
		String whereString = subject + GENRE + predicate + p + predicate
				+ GENRE_NAME + object + p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(predicate, GENRE_NAME, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(predicate);
		// System.out.println(query);
		Model movs = execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		// for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geGenreNameMov <> " + subjects.size());
	}

	public void getGenreClass() {
		String whereString = subject + GENRE + object + p;
		String query = QueryBuilder.buildHeaderQuery()
				+ QueryBuilder.buildConstruct(object, a, GENRE)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(object);
		// System.out.println(query);
		Model movs = execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		// for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geGenreClass <> " + subjects.size());
	}

	public void wturtleF(String name, Model queryResult) {
		FileOutputStream out = null;
		String fileDir = PATH + name + ".ttl";
		try {
			File f = new File(fileDir);
			if (!(f.exists() && !f.isDirectory())) {
				new PrintWriter(fileDir, "UTF-8");
			}
			out = new FileOutputStream(fileDir);
			queryResult.write(out, "Turtle");
		} catch (IOException ignore) {
			System.out.println(ignore);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ignore) {
				}
			}
		}
	}
}