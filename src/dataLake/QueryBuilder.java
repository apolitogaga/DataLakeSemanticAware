package dataLake;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

public class QueryBuilder {

	
	public static Query getTestQuery()
	{
		String qString =  "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http://data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?SUBJ a dbpedia-owl:Film } "
				+ "WHERE {service <http://linkedmdb.org/sparql> { ?SUBJ a linkedmdb:film }} LIMIT 15";
		return QueryFactory.create(qString) ;
	}
	public static Query getMovieActors()
	{
		String qstring = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX linkedmdb: <http:/data.linkedmdb.org/resource/movie/> "
				+ "CONSTRUCT { ?SUBJ a dbpedia-owl:Film } "
				+ "WHERE {service <http://linkedmdb.org/sparql> { http://data.linkedmdb.org/resource/film/38072  a linkedmdb:actor}} LIMIT 15";
		return QueryFactory.create(qstring) ;
	}
	public static void getActors(String actors){
		System.out.println("This is a movie URI "+actors);
	}
	
}
