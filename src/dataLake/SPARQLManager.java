package dataLake;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import dataLake.QueryBuilder;


public class SPARQLManager {
	public TBDManager datalake;
	public ArrayList<String> movies;
	public Model overallModel;
	public static final String PATH="data/linkedMDB/"; 
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
		ACTOR = QueryBuilder.LINKEMDB+CONN+ getActorEntity();
		DIRECTOR =  QueryBuilder.LINKEMDB+CONN+ getDirectorEntity();
		GENRE =  QueryBuilder.LINKEMDB+CONN+ getGenreEntity();
		ACTOR_NAME =  QueryBuilder.LINKEMDB+CONN+ getActorNameEntity();
		DIRECTOR_NAME =  QueryBuilder.LINKEMDB+CONN+ getDirectorNameEntity();
		GENRE_NAME =  QueryBuilder.LINKEMDB+CONN+ getGenreNameEntity();
		MOVIE =  QueryBuilder.LINKEMDB+CONN+ getMovieEntity();
		MOVIEDBP = QueryBuilder.ONTOLOGY+CONN+ getMovieDBPEntity();
		MOVIE_NAME =  QueryBuilder.DCTITLE+CONN+ getMovieNameEntity();
	}
	public String getActorEntity(){return "actor";}
	public String getDirectorEntity(){return "director";}
	public String getGenreEntity(){return "genre";}
	public String getActorNameEntity(){return "actor_name";}
	public String getDirectorNameEntity(){return "director_name";}
	public String getGenreNameEntity(){return "film_genre_name";}
	public String getMovieEntity(){return "film";}
	public String getMovieDBPEntity(){return "Film";}
	public String getMovieNameEntity(){return "title";}
	public Model execQuery(String query){
		if(!TEST_ENV) return datalake.store(query);
		else return datalake.execConstQuery(query);
	}
	
	public void getMov(){
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(subject, a, MOVIEDBP)
				+ QueryBuilder.buildWhere(subject, a, MOVIE)
				+ QueryBuilder.buildOrderBy(subject)
				+ QueryBuilder.buildLimit();
		Model movs =execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
		System.out.println("getMov <> "+subjects.size());
	}
	public void getMovName(){
		String whereString = subject+a+MOVIE+p+subject+ MOVIE_NAME+object+p;
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(subject, MOVIE_NAME, object)
				+ QueryBuilder.buildWhere(whereString);
		Model movs =execQuery(query);
		System.out.println(query);
		List<Statement> subjects = movs.listStatements().toList();
		for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geMovName <> "+subjects.size());
	}
	public void getActorMov(){
		String whereString = subject + a + MOVIE+p +subject+ACTOR+object+p ;
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(subject, ACTOR, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(subject)
				+ QueryBuilder.buildLimit();
		Model movs =execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();

		System.out.println("geActorMov <> "+subjects.size());
	}
	public void getActorName(){
		String whereString = subject+a+ACTOR+p+subject+ ACTOR_NAME+object+p;
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(subject, ACTOR_NAME, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(predicate);
		Model movs =execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
//		for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geActorName <> "+subjects.size());
	}
	public void getActorClass(){
		String whereString = subject+a+ACTOR;
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(subject, a, ACTOR)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(subject);
		Model movs =execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
//		for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geActorClass <> "+subjects.size());
	}
	
	///// DIRECTOR
	
	public void getDirectorMov(){
		String whereString = subject + a + MOVIE+p +subject+DIRECTOR+object+p ;
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(subject, DIRECTOR, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(subject)
				+ QueryBuilder.buildLimit();
		Model movs =execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
//		for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geDirectorMov <> "+subjects.size());
	}
	public void getDirectorName(){
		String whereString = subject+a+DIRECTOR+p+subject+ DIRECTOR_NAME+object+p;
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(subject, DIRECTOR_NAME, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(predicate)
				+ QueryBuilder.buildLimit();
		Model movs =execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
//		for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geDirectorNameMov <> "+subjects.size());
	}
	public void getDirectorClass(){
		String whereString = subject+a+DIRECTOR;
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(subject, a, DIRECTOR)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(subject)
				+ QueryBuilder.buildLimit();
		Model movs =execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
//		for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geDirectorClass <> "+subjects.size());
	}
	
	//// GENRE
	
	public void getGenreMov(){
		String whereString = subject + a + MOVIE+p +subject+GENRE+object+p ;
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(subject, GENRE, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(subject);
		Model movs =execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
//		for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geGenreMov <> "+subjects.size());
	}
	public void getGenreName(){
		String whereString = subject+GENRE+predicate+p+predicate+ GENRE_NAME+object+p;
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(predicate, GENRE_NAME, object)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(predicate);
//		System.out.println(query);
		Model movs =execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
//		for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geGenreNameMov <> "+subjects.size());
	}
	public void getGenreClass(){
		String whereString = subject+GENRE+object+p;
		String query = QueryBuilder.buildHeaderQuery() 
				+ QueryBuilder.buildConstruct(object, a, GENRE)
				+ QueryBuilder.buildWhere(whereString)
				+ QueryBuilder.buildOrderBy(object);
		//System.out.println(query);
		Model movs =execQuery(query);
		List<Statement> subjects = movs.listStatements().toList();
//		for(Statement stat:subjects){System.out.println(stat.toString());}
		System.out.println("geGenreClass <> "+subjects.size());
	}
	
	
//// DEPRECATED	
	
	public void getMoviesURI() {
	//	TBDManager dataLake = new TBDManager();
		Query testQuery = QueryBuilder.getMovies();
		List<Resource> subjects = datalake.store(testQuery).listSubjects().toList();
		for (Resource res: subjects)
			movies.add(res.getURI());
		//System.out.println("Size:" + movies.size()+ " Example" + movies.get(200));
		System.out.println("Finished getting Movies\n Size:" + movies.size());
//		dataLake.close();
	}
	public void getMovieNames(){
		Query testQuery = QueryBuilder.getMovieNames();
		List<Resource> subjects = datalake.store(testQuery).listSubjects().toList();
	}
	public void getMovieRDFNames(){
		Query testQuery = QueryBuilder.getMovieRDFNames();
		List<Resource> subjects = datalake.store(testQuery).listSubjects().toList();
	}
	public void getArtists(){
		Query testQuery = QueryBuilder.getArtist();
		List<Resource> subjects = datalake.store(testQuery).listSubjects().toList();

	}
	public void getArtistName(){
		Query testQuery = QueryBuilder.getArtistName();
		List<Statement> subjects = datalake.store(testQuery).listStatements().toList();
//		int i=0;
//		for(Statement stat:subjects){
//			System.out.println(stat.toString());
//			i++;
//		}
//		System.out.println(" <> "+i);
	}
 	public void getDirectors(){
		Query testQuery = QueryBuilder.getDirectors();
		List<Resource> subjects = datalake.store(testQuery).listSubjects().toList();
	}
	public void getDirectorName2(){
		Query testQuery = QueryBuilder.getDirectorName();
		List<Statement> subjects = datalake.store(testQuery).listStatements().toList();
		int i=0;
		for(Statement stat:subjects){
			System.out.println(stat.toString());
			i++;
		}
		System.out.println(" <> "+i);
	}
	public void getGenre(){
		Query testQuery = QueryBuilder.getMovieGenre();
		datalake.store(testQuery).listSubjects().toList();
	}
	public void getGenreName2(){
		Query testQuery = QueryBuilder.getGenreName();
		List<Statement> subjects = datalake.store(testQuery).listStatements().toList();
	}	
	public void getActorClass2(){
		for(String film: movies){
			Query testQuery = 
					QueryBuilder.getActorClass();		
			 datalake.store(testQuery);
			
		}
		System.out.println("finished getting actors");
	}	
	public void wturtleF(String name, Model queryResult){
		FileOutputStream out = null;
		String fileDir=PATH + name+".ttl";
		try {
			File f = new File(fileDir);
			if(!(f.exists() && !f.isDirectory())) {
				new PrintWriter(fileDir, "UTF-8");
			}
			out = new FileOutputStream(fileDir );
			queryResult.write( out, "Turtle" );
		}
		catch (IOException ignore) {
			System.out.println(ignore);
		}
		finally {
			if (out != null) {
				try {out.close();} 
				catch (IOException ignore) {}
			}
		}
		
	}
////////// DEPRECATED FUNCTIONS //////////////////
	
	
	
	public ArrayList<String> getMoviesURI2() {
		Query testQuery = QueryBuilder.getMovies();
		Model movs = datalake.store(testQuery);
		List<Resource> subjects = movs.listSubjects().toList();
		for (Resource res : subjects){
			movies.add(res.getURI()); 
			System.out.println(res.toString());
		}
		return this.movies;
	}

	public void selectMovie(){
		Query query = QueryBuilder.selectMovies();
		movies = datalake.selectQuery(query);
		//int i=0;
		for(String mov: movies){
			System.out.println(mov);
			//selectActors(mov);
		}
	}
	public void getDirector() {
		for(String film: movies){
			Query testQuery = 
					QueryBuilder.getDirectorfromMovie(film);
			datalake.store(testQuery);
			
		}
	}
	
	public void getMoviesURI22() {
		Query testQuery = QueryBuilder.getMovies();
		List<Resource> subjects = datalake.store(testQuery).listSubjects().toList();
		for (Resource res: subjects)
			movies.add(res.getURI());
		//System.out.println("Size:" + movies.size()+ " Example" + movies.get(200));
		//this.wturtleF("movies", movs);
	}
	
	public void getActors(String film) {
		Query testQuery = 
				QueryBuilder.getActorfromMovie(film);
		Model movs = datalake.store(testQuery);
		List<Resource> subjects = movs.listSubjects().toList();
		List<Statement> statements = movs.listStatements().toList();
		for (Statement res : statements){
			//System.out.println("      "+res.toString());
			System.out.println(" "+res.toString());
		}	 	
	}
	
	public void getActors() {
		System.out.println("Getting actors...");
		//TBDManager dataLake = new TBDManager();
		for(String film: movies){
			Query testQuery = 
					QueryBuilder.getActorfromMovie(film);		
			 datalake.store(testQuery);
			
		}
		System.out.println("finished getting actors");
	//	dataLake.close();
	}
	
	public void selectActors(String mov){
		System.out.println(mov);
		Query query = QueryBuilder.selectActors(mov);
		ArrayList<String> actors = datalake.selectQuery(query);
		int i=0;
		System.out.println(mov + " <>" + actors.size());
		for(String movie: actors){
			System.out.println(i++ +": "+ movie);	
		}	
	}
	
	public void getDirectors(String film) {
		Query testQuery = 
				QueryBuilder.getDirectorfromMovie(film);
		Model movs = datalake.store(testQuery);
		List<Resource> subjects = movs.listSubjects().toList();
		List<Statement> statements = movs.listStatements().toList();
		for (Statement res : statements)
			System.out.println("      "+res.toString());
	}
	
	public void getGenre(String film) {
		Query testQuery = 
				QueryBuilder.getGenrefromMovie(film);
		Model movs = datalake.store(testQuery);
		List<Resource> subjects = movs.listSubjects().toList();
		List<Statement> statements = movs.listStatements().toList();
		for (Resource res : subjects){
			//System.out.println("      "+res.toString());
			System.out.print(""+res.getURI());
		}
		movs.write(System.out, "TURTLE");
		//PATH+
	}
}