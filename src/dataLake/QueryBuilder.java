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
	public static final String MYPrefix = "PREFIX myprefix: <http://www.myexample.com/mySchema#>";
	public static final String LINKEMDB ="linkedmdb";
	public static final String ONTOLOGY ="movie";
	public static final String DCTITLE ="dc";
	public static final String MOVIE = " PREFIX "+LINKEMDB+": <http://data.linkedmdb.org/resource/movie/>";
	public static final String DBPEDIA = " PREFIX "+ONTOLOGY+": <http://dbpedia.org/ontology/>";
	public static final String DC = " PREFIX "+DCTITLE+": <http://purl.org/dc/terms/>";
	public static final String PREFIXES =  DBPEDIA + MOVIE + DC;
	public static final String FILM_LIMIT= "666";
	public static final boolean EXTERNAL_QUERY=false;
	public static final String SERVICE = EXTERNAL_QUERY ? "{service <http://linkedmdb.org/sparql>" : "" ;
	public static final String CLOSE_SERVICE = EXTERNAL_QUERY ? "}" : "" ;

	
	//this class is not suppossed to be instantiated
	private QueryBuilder(){throw new AssertionError();}
	
	
	public static String buildHeaderQuery(){
		return PREFIXES;			
	}	
	public static String buildOffset(int offset){
		return " OFFSET "+offset;			
	}
	public static String buildSelect(String subject, String predicate, String object){
		return " SELECT "+ subject +" "+predicate +" "+ object;
	}
	public static String buildSelectWhereWrapper(String whereClause){
		return " { " + whereClause + " } "; 
	}
	public static String buildSelect(String subject, String predicate){
		return buildSelect(subject,predicate,"");
	}
	public static String buildSelect(String subject){
		return buildSelect(subject,"","");
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
	public static String buildWhere(String clause, String option){
		return " WHERE "+SERVICE+"{ "+clause+"  FILTER (BOUND("+option+")) }"+CLOSE_SERVICE; 
	}
	public static String buildWhereRegex(String clause, String var, String regex, String option){
		return " WHERE "+SERVICE+"{ "+clause+"   FILTER regex("+var+", '"+regex+"' , '"+option+"') }"+CLOSE_SERVICE; 
	}
	public static String buildOrderBy(String instance){
		return " ORDER BY "+instance;
	}
	public static String buildLimit(int limit){
		return " LIMIT "+limit;
	}
	public static String buildLimit(){
		return " LIMIT "+FILM_LIMIT;
	}
}
