package dataLake;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

public final class QueryBuilder {

	public static final String OWL = "PREFIX owl: <http://www.w3.org/2002/07/owl#>";
	public static final String XSD = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
	public static final String RDFS = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
	public static final String RDF = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
	public static final String FOAF = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>";
	public static final String ODDLINKER = "PREFIX oddlinker: <http://data.linkedmdb.org/resource/oddlinker/>";
	public static final String MAP = "PREFIX map: <file:/C:/d2r-server-0.4/mapping.n3#>";
	public static final String DB = "PREFIX db:<http://data.linkedmdb.org/resource/>";
	public static final String SKOS = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>";
	
	public static final String LINKEMDB ="movie";
	public static final String ONTOLOGY ="dbpedia";
	public static final String DCTITLE ="dc";
	public static final String MOVIE = " PREFIX "+LINKEMDB+": <http://data.linkedmdb.org/resource/movie/>";
	public static final String DBPEDIA = " PREFIX "+ONTOLOGY+": <http://dbpedia.org/ontology/>";
	public static final String DC = " PREFIX "+DCTITLE+": <http://purl.org/dc/terms/>";
	public static final String PREFIXES =  DBPEDIA + MOVIE+ DC;
	public static final String FILM_LIMIT= "666";
	public static final boolean EXTERNAL_QUERY=true;
	public static final String SERVICE = EXTERNAL_QUERY ? "{service <http://linkedmdb.org/sparql>" : "" ;
	public static final String CLOSE_SERVICE = EXTERNAL_QUERY ? "}" : "" ;

	
	//this class is not suppossed to be instantiated
	private QueryBuilder(){throw new AssertionError();}
	
	
	public static String buildHeaderQuery(){
		return PREFIXES;
				
	}
	public static String buildConstruct(String subject, String predicate, String object){
		return " CONSTRUCT { "+ subject +" "+predicate +" "+ object +" }";
	}
	public static String buildWhere(String subject, String predicate, String object){
		return buildWhere(subject +" "+predicate +" "+ object);
	}
	public static String buildWhere(String clause){
		return " WHERE "+SERVICE+"{ "+clause+" }"+CLOSE_SERVICE;
	}
	public static String buildOrderBy(String instance){
		return " ORDER BY "+instance;
	}
	
	public static String buildLimit(){
		return " LIMIT "+FILM_LIMIT;
	}
	
	public static Query getMovies() {
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?SUBJ a dbpedia-owl:Film } "
				+ "WHERE {service <http://linkedmdb.org/sparql> "
				+ "{ ?SUBJ a linkedmdb:film "
				+ "}} "+FILM_LIMIT +" ";
		return QueryFactory.create(qString);
	}
	
	public static Query getMovieNames() {
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?SUBJ <http://purl.org/dc/terms/title> ?name } "
				+ "WHERE {service <http://linkedmdb.org/sparql> "
				+ "{  ?SUBJ a linkedmdb:film;"
				+ " <http://purl.org/dc/terms/title> ?name. "
				+ "}} order by ?subj";
		return QueryFactory.create(qString);
	}
	public static Query getMovieRDFNames() {
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?SUBJ <http://purl.org/dc/terms/title> ?name } "
				+ "WHERE {service <http://linkedmdb.org/sparql> "
				+ "{  ?SUBJ a linkedmdb:film;"
				+ " <http://www.w3.org/2000/01/rdf-schema#label> ?name. "
				+ "}}";
		return QueryFactory.create(qString);
	}
	
	public static Query getGenreName(){
		String subjProperty = "<http://data.linkedmdb.org/resource/movie/genre>";
		String nameProperty = "<http://data.linkedmdb.org/resource/movie/film_genre_name>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?actor "+nameProperty+" ?name } "
				+ "WHERE {service <http://linkedmdb.org/sparql>"
				+ "{ ?instance a linkedmdb:film."
				+ "?instance "+subjProperty+" ?actor."
				+ "?actor "+nameProperty+" ?name.}}"
				+ "ORDER BY ?instance";
		return QueryFactory.create(qString);
	}
	
	public static Query getMovieGenre() {
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?SUBJ <http://data.linkedmdb.org/resource/movie/genre> ?name } "
				+ "WHERE {service <http://linkedmdb.org/sparql> "
				+ "{?SUBJ <http://data.linkedmdb.org/resource/movie/genre> ?name. }"
				+ "} order by ?subj";
		return QueryFactory.create(qString);
	}
	
	
	// deprecated code.
	public static Query getArtistName(){
		String actorProperty = "<http://data.linkedmdb.org/resource/movie/actor>";
		String nameProperty = "<http://data.linkedmdb.org/resource/movie/actor_name>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?actor "+nameProperty+" ?name } "
				+ "WHERE {service <http://linkedmdb.org/sparql>"
				+ "{ ?instance a linkedmdb:film."
				+ "?instance "+actorProperty+" ?actor."
				+ "?actor "+nameProperty+" ?name.}}"
				+ "ORDER BY ?instance";
		return QueryFactory.create(qString);
	}
	
		
	public static Query getArtist() {
		String actorProperty = "<http://data.linkedmdb.org/resource/movie/actor>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?instance "+actorProperty+" ?name } "
				+ "WHERE {service <http://linkedmdb.org/sparql> "
				+ "{{?instance a <http://data.linkedmdb.org/resource/movie/film>.}"
				+ "UNION"
				+ "{   ?instance "+actorProperty+" ?name.}}}";
		return QueryFactory.create(qString);
	}
	
	public static Query getDirectorName(){
		String subjProperty = "<http://data.linkedmdb.org/resource/movie/director>";
		String nameProperty = "<http://data.linkedmdb.org/resource/movie/director_name>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?actor "+nameProperty+" ?name } "
				+ "WHERE {service <http://linkedmdb.org/sparql>"
				+ "{ ?instance a linkedmdb:film."
				+ "?instance "+subjProperty+" ?actor."
				+ "?actor "+nameProperty+" ?name.}}"
				+ "ORDER BY ?instance";
		return QueryFactory.create(qString);
	}
	public static Query getDirectors() {
		String dirProperty = "<http://data.linkedmdb.org/resource/movie/director>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?SUBJ "+dirProperty+" ?name } "
				+ "WHERE {service <http://linkedmdb.org/sparql> "
				+ "{  ?SUBJ a linkedmdb:film;"
				+ dirProperty + " ?name. "
				+ "}}";
		return QueryFactory.create(qString);
	}
	
	
	public static Query selectMovies(){
		String qString = "SELECT ?instance "
		+ "WHERE {service <http://linkedmdb.org/sparql>"
		+ "{ ?instance a <http://data.linkedmdb.org/resource/movie/film> }} "
		+ "ORDER BY ?instance LIMIT "+FILM_LIMIT ;
		return QueryFactory.create(qString);
	}
	public static Query getActorClass() {
		String actorProperty = "<http://data.linkedmdb.org/resource/movie/actor>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?SUBJ a dbpedia-owl:Actor } "
				+ "WHERE {service <http://linkedmdb.org/sparql> "
				+ "{ ?SUBJ a linkedmdb:actor "
				+ "}} "+FILM_LIMIT +" ";
	
		return QueryFactory.create(qString);
	}
	public static Query getDirectorClass() {
		String actorProperty = "<http://data.linkedmdb.org/resource/movie/director>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?SUBJ a linkedmdb:director } "
				+ "WHERE {service <http://linkedmdb.org/sparql> "
				+ "{ ?SUBJ a linkedmdb:director "
				+ "}} "+FILM_LIMIT +" ";
		return QueryFactory.create(qString);
	}
	
	public static Query getActorfromMovie(String mov) {
		String actorProperty = "<http://data.linkedmdb.org/resource/movie/actor>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ " CONSTRUCT "
				+ "{  <" + mov + "> <http://data.linkedmdb.org/resource/movie/actor> ?value } "
				+ "WHERE {service <http://linkedmdb.org/sparql> " + "{ "
				+ "<" + mov + "> " + actorProperty + " ?value."
				+ "}}";
		return QueryFactory.create(qString);
	}
	
	public static Query getDirectorClass(String mov) {
		String subjectProperty = "<http://data.linkedmdb.org/resource/movie/director>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ " CONSTRUCT "
				+ "{  ?value a  "+subjectProperty+" } "
				+ "WHERE {service <http://linkedmdb.org/sparql> " + "{ "
				+ "<" + mov + "> " + subjectProperty + " ?value."
				+ "}}";
		return QueryFactory.create(qString);
	}

	public static Query getDirectorfromMovie(String mov) {
		String subjectProperty = "<http://data.linkedmdb.org/resource/movie/director>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ " CONSTRUCT "
				+ "{  <" + mov + ">  "+subjectProperty+" ?object } "
				+ "WHERE {service <http://linkedmdb.org/sparql> " + "{ "
				+ "<" + mov + "> " + subjectProperty + " ?object."
				+ "}}";
		return QueryFactory.create(qString);
	}
	
	public static Query getGenrefromMovie(String mov) {
		String subjectProperty = "<http://data.linkedmdb.org/resource/movie/film_genre>";
		String qString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ " CONSTRUCT "
				+ "{  ?value a  "+subjectProperty+" } "
				+ "WHERE {service <http://linkedmdb.org/sparql> " + "{ "
				+ "<" + mov + "> " + subjectProperty + " ?value."
				+ "}}";
		return QueryFactory.create(qString);
	}

	public static Query selectActors(String mov) {
		String qstring = "PREFIX movie: <http:/data.linkedmdb.org/resource/movie/> "
				+ "SELECT ?instance ?actor_name ?actor_id "
				+ "WHERE { service <http://linkedmdb.org/sparql> {"
				+"<"+ mov + "> movie:actor ?instance."
				+ "?instance movie:actor_name ?actor_name."
				+ "?instance movie:actor_actorid ?actor_id.}}";
		System.out.println(qstring);
		return QueryFactory.create(qstring);
	}

	public static Query getDirectors(String mov) {
		String qstring = "PREFIX linkedmdb: <http:/data.linkedmdb.org/resource/movie/> "
				+ "SELECT ?director ?director_name ?director_id "
				+ "WHERE {"
				+ "<"+mov+">"
				+ " linkedmdb:director ?director."
				+ "?director linkedmdb:director_name ?director_name."
				+ "?director linkedmdb:director_directorid ?director_id.}";
		return QueryFactory.create(qstring);
	}

	public static Query getGenre(String mov) {

		String qstring = "PREFIX linkedmdb: <http:/data.linkedmdb.org/resource/movie/> "
				+ "SELECT ?genre ?genre_name ?genre_id "
				+ "WHERE {"
				+ "<"+mov+">"
				+ " linkedmdb:genre ?genre."
				+ "?genre linkedmdb:film_genre_name ?genre_name."
				+ "?genre linkedmdb:film_genre_film_genreid ?genre_id.}";

		return QueryFactory.create(qstring);

	}

}
