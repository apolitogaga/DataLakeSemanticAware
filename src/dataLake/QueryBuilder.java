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
	
	public static Query getActors(String mov){
				
		String qstring = "PREFIX linkedmdb: <http:/data.linkedmdb.org/resource/movie/> "
				+ "SELECT ?actor ?actor_name ?actor_id "
				+ "WHERE {"+mov+" linkedmdb:actor ?actor."
				+ "?actor linkedmdb:actor_name ?actor_name."
				+"?actor linkedmdb:actor_actorid ?actor_id.}";	
		return QueryFactory.create(qstring);
			
	}
	
	public static Query getDirectors(String mov){
		
		String qstring = "PREFIX linkedmdb: <http:/data.linkedmdb.org/resource/movie/> "
				+ "SELECT ?director ?director_name ?director_id "
				+ "WHERE {"+mov+" linkedmdb:director ?director."
				+ "?director linkedmdb:director_name ?director_name."
				+"?director linkedmdb:director_directorid ?director_id.}";
								
		return QueryFactory.create(qstring);
			
	}


	
}
