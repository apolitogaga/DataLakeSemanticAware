package dataLake;

import dataAmazonType.*;

public class Main {

	public static void main(String[] args) {
		// populateXML();
		
		Integrator integrator =  new Integrator();
//		integrator.integrate();
		
//		dataLakeTest();
		
		Recommendator rec = new Recommendator();
		rec.waitForEnter();
		
		System.out.println("\n\n\n\t\t\t <<<<<<<<FFFFIIINNNNIIISSSSHHHHEEEDD>>>>>>>>> ");
		
	}

	public static void dataLakeTest() {
		// TODO Auto-generated method stub
		SPARQLManager test = new SPARQLManager();
		test.initialize();
		test.selectMovieBestRated();
//		test.wturtleF("tbdStatus2", test.datalake.getModel());
//		for(int i=0; i<10;i++) 	test.getMov(i*2500);
//		test.getMov(2500);
		//test.selectMov();
		//test.selectActorMov();
		//test.selectActorFromMov("<http://data.linkedmdb.org/resource/film/1015> ");
		//test.selectMovieFromTitle("ana");
		//test.datalake.queryTBD();
;

		
		
		
	}

	public static void populateXML() {
		ProcessReviews proc = new ProcessReviews();
		LoadXML.parseXML(proc);
		proc.testAll();

	}

}
