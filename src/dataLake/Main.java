package dataLake;

import com.hp.hpl.jena.query.Query;
 
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TBDManager datalake = new TBDManager();
		Query testQuery = QueryBuilder.getTestQuery();
		datalake.store(testQuery);
		datalake.read();
	}

}
