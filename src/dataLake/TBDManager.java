package dataLake;

import java.io.IOException;
import java.util.List;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;

public class TBDManager {
	private Dataset datalake;
	private String directory="DataLake";
	private Model tbd;
	public TBDManager(){
		this.datalake = TDBFactory.createDataset(directory) ;		
		tbd =  datalake.getDefaultModel();
	}
	public void create(){
		try{
			// must add the metadata thing around here somewhere
			
			//create TBD dataset
			this.datalake = TDBFactory.createDataset(directory) ;
			
			tbd =  datalake.getDefaultModel();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println (e.toString());
		}
	}
	public void store(Query qResult){
		datalake.begin(ReadWrite.WRITE);
		Model model = datalake.getDefaultModel();		
		execConstQuery(qResult);
		//model.add(qResult); // would be the best way to use this query but there's an error while executing it.
		datalake.commit();
	}
	private void execConstQuery(Query cQuery){
		//would much rather get rid of this sentence but I couldn't find another way around it
		QueryExecution qexec = QueryExecutionFactory.create(cQuery, tbd);
		Model results = qexec.execConstruct(tbd);
	}
	
	
	public void read(){
		datalake.begin(ReadWrite.READ);
		Model model = datalake.getDefaultModel();
		System.out.println(model.listSubjects().toList());
		
		List<Resource> subjects = model.listSubjects().toList();
		System.out.println(subjects.size());
		for(int i = 0; i<subjects.size();i++)
		{
			System.out.println( subjects.get(i).toString() );
			System.out.println(subjects.get(i).getURI());
			
		}	
		miguelmossa mi usuario gibhub
		github
		
		datalake.end();
	}
	
	public void close(){
		this.datalake.close();
		
	}
}
