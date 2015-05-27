package dataLake;

import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TBDManager datalake = new TBDManager();
		Query testQuery = QueryBuilder.getTestQuery();
		Model movies = datalake.store(testQuery);
		
		List<Resource> subjects = movies.listSubjects().toList();
		for(int i = 0; i<subjects.size();i++)
		{
			//System.out.println( subjects.get(i).toString() );
			//System.out.println(subjects.get(i).getURI());
			String mov = subjects.get(i).getURI();
			QueryBuilder.getActors(mov);
		}
		//datalake.read();
	}

}
