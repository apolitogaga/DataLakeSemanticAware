package dataLake;

import dataAmazonType.*;

public class Main {

	public static void main(String[] args) {
		// populateXML();
		
		dataLakeTest();
		System.out.println("\n\n\n\t\t\t <<<<<<<<FFFFIIINNNNIIISSSSHHHHEEEDD>>>>>>>>> ");
	}

	public static void dataLakeTest() {
		// TODO Auto-generated method stub
		SPARQLManager test = new SPARQLManager();
		//test.datalake.queryTBD();
		test.getMov();
		//test.getMovName();
		//test.getActorMov();
//		test.getActorName();
//		test.getActorClass();
//		test.getDirectorMov();
//		test.getDirectorName();
//		test.getDirectorClass();
//		test.getGenreMov();
//		test.getGenreName();
//		test.getGenreClass();
		
		
//		
//		test.getMovieNames();
//		test.getMovieRDFNames();
//		test.getGenre();
//		test.getArtists();
		//test.getArtistName();
		//test.getGenreName();
		//test.getDirectorName();
		//test.getMoviesURI();
		//test.getDirectors();
		//test.getActors();
//		test.getActorClass();
		//test.getDirectors();
		//test.wturtleF("tbdStatus2", test.datalake.getModel());
		
		
	}

	public static void populateXML() {
		ProcessReviews proc = new ProcessReviews();
		LoadXML.parseXML(proc);
		proc.testAll();

	}

}
