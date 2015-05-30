package dataLake;

import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import dataAmazonType.*;
public class Main {

	public static void main(String[] args) {
		populateXML();
	}
	public static void dataLakeTest()
	{
		// TODO Auto-generated method stub
		TBDManager datalake = new TBDManager();
		Query testQuery = QueryBuilder.getTestQuery();
		Model movies = datalake.store(testQuery);
		
		List<Resource> subjects = movies.listSubjects().toList();
		for(int i = 0; i<subjects.size();i++)
		{
			String mov = subjects.get(i).getURI();
			QueryBuilder.getActors(mov);
		}
	}
	public static void populateXML(){
		ProcessReviews proc= new ProcessReviews();
		LoadXML.parseXML(proc);
		proc.testAll();
		
	}

}
