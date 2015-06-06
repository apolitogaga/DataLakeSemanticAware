package dataLake;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.*;

public class TBDManager {
	private Dataset datalake;

	private String directory = "DataLake";
	public Model tdb;
	

	public TBDManager() {
		this.datalake = TDBFactory.createDataset(directory);
		tdb = datalake.getDefaultModel();
	}

	public void create() {
		try {
			// must add the metadata thing around here somewhere

			// create TBD dataset
			this.datalake = TDBFactory.createDataset(directory);

			tdb = datalake.getDefaultModel();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

	public Model store(String query) {
		Query qResult = QueryFactory.create(query);
		datalake.begin(ReadWrite.WRITE);
		Model result;
		QueryExecution qexec = QueryExecutionFactory.create(qResult, tdb);
		// model.add(qResult, service); // would be the best way to use this
		// query but there's an error while executing it.
		// tbd.add(result);
		result = qexec.execConstruct(tdb);
		tdb.commit();
		datalake.commit();
		return result;
	}
	public Model store(Query query) {return tdb;} ///deleeeeteeeeeeee
	
	public void queryTBD(){
		System.out.println("Query this shite");
		String qs1 = "SELECT * {?s ?p ?o} LIMIT 10" ;

		 try(QueryExecution qExec = QueryExecutionFactory.create(qs1, datalake)) {
		     ResultSet rs = qExec.execSelect() ;
		     ResultSetFormatter.out(rs) ;
		 }
	}

	public Model execConstQuery(String query) {
		Query qResult = QueryFactory.create(query);
		// would much rather get rid of this sentence but I couldn't find
		// another way around it
		QueryExecution qexec = QueryExecutionFactory.create(qResult, tdb);
		return qexec.execConstruct();
	}
	public Model execConstQuery(Query query) {return tdb;}///deleeeeteeeeeeee

	public ArrayList<String> selectQuery(Query query) {
		ArrayList<String> objects = new ArrayList<String>();
		try (QueryExecution qexec = QueryExecutionFactory.create(query, tdb)) {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				// System.out.println(soln.toString());
				// RDFNode x = soln.get("actor"); // Get a result variable by
				// name.
				Resource r = soln.getResource("?instance"); // Get a result
															// variable - must
															// be a resource
				// Literal l = soln.getLiteral("?instance"); // Get a result
				// variable - must be a literal

				objects.add(r.getURI());

				// System.out.println(r.toString());
			}
		}
		return objects;
	}

	private void writeFileDB(Model results, String format) {
		FileWriter fostream;
		try {
			fostream = new FileWriter(LoadXML.DATA_DIRECTORY
					+ "dbpediafilmdataset.nt");
			BufferedWriter out = new BufferedWriter(fostream);
			results.write(out, format);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeFileDB(Model results) {
		writeFileDB(results, "TURTLE");
	}

	public Model getModel() {
		return datalake.getDefaultModel();
	}

	public void read() {
		datalake.begin(ReadWrite.READ);
		Model model = datalake.getDefaultModel();
		System.out.println(model.listSubjects().toList());

		List<Resource> subjects = model.listSubjects().toList();
		System.out.println(subjects.size());
		for (int i = 0; i < subjects.size(); i++) {
			// System.out.println(subjects.get(i).toString() );
			System.out.println(subjects.get(i).getURI());

		}
		datalake.end();
	}

	public void close() {
		if (tdb != null && datalake != null) {
			tdb.commit();
			tdb.close();
			datalake.close();
		}
	}

	/**
	 * save the given model
	 * 
	 * @param model
	 */
	public void saveModel(Model model) {
		if (tdb != null && datalake != null) {
			tdb.commit();
			tdb.close();
			datalake.close();
		}
	}
}
